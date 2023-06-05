package main.java.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.scene.paint.Color;
import main.java.db.BoardDB;
import main.java.enums.ColorEnum;
import main.java.pattern.Observable;

public class Board {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;
    private static final int EYESONE = 1;
    private static final int EYESTWO = 2;
    private static final int EYESTHREE = 3;
    private static final int EYESFOUR = 4;
    private static final int EYESFIVE = 5;
    private static final int EYESSIX = 6;
    private static final int POINTSTWO = 2;
    private static final int POINTSFOUR = 4;
    private static final int POINTSFIVE = 5;

    private Die[][] board = new Die[ROWS][COLUMNS];

    private Player player;

    public Die getField(final int row, final int column) {
        return board[row - 1][column - 1];
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Boolean isEmpty() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean placeDie(final Color color, final int number, final int row, final int column) {

        boolean result = BoardDB.setField(
                this.player.getGame().getId(), this.player.getGame().getRoundID(), this.player.getId(), row,
                column, ColorEnum.fromString(color.toString()).getName(), number);

        if (!result) {
            return false;
        }

        Observable.notifyObservers(Game.class);
        return true;
    }

    public int getEmptyPlaces() {
        int emptyPlaces = 0;
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                if (board[row - 1][col - 1] == null) {
                    emptyPlaces--;
                }
            }
        }

        return emptyPlaces;
    }

    public int getPrivateObjectiveCardScore(final String color) {
        int privateObjectiveScore = 0;
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                Die die = getField(row, col);
                String dieColor = die != null ? ColorEnum.fromString(die.getColor().toString()).toString() : "null";
                if (die != null && dieColor.equals(color)) {
                    privateObjectiveScore += die.getEyes();
                }
            }
        }

        return privateObjectiveScore;
    }

    public int getPublicObjectiveCardScore(final ArrayList<Integer> ids) {
        int publicObjectiveScore = 0;
        for (Integer integer : ids) {
            switch (Integer.toString(integer)) {
                case "1":
                    publicObjectiveScore += sets(POINTSFIVE, EYESONE, EYESTWO, EYESTHREE, EYESFOUR, EYESFIVE, EYESSIX);
                    break;
                case "2":
                    publicObjectiveScore += sets(POINTSTWO, EYESTHREE, EYESFOUR);
                    break;
                case "3":
                    publicObjectiveScore += columns(POINTSFOUR, "shades");
                    break;
                case "4":
                    publicObjectiveScore += columns(POINTSFIVE, "colors");
                    break;
                case "5":
                    publicObjectiveScore += sets(POINTSTWO, EYESFIVE, EYESSIX);
                    break;
                case "6":
                    publicObjectiveScore += sets(POINTSFOUR, "red", "blue", "green", "yellow", "purple");
                    break;
                case "7":
                    publicObjectiveScore += rows(POINTSFIVE, "colors");
                    break;
                case "8":
                    publicObjectiveScore += diagonallySameColor();
                    break;
                case "9":
                    publicObjectiveScore += sets(POINTSTWO, EYESONE, EYESTWO);
                    break;
                case "10":
                    publicObjectiveScore += rows(POINTSFIVE, "shades");
                    break;
                default:
                    break;
            }
        }

        return publicObjectiveScore;
    }

    public static Board get(final Player player) {
        Board board = new Board();
        board.setPlayer(player);

        List<Map<String, String>> fieldValues = BoardDB.getBoard(player.getGame().getId(), player.getId());

        for (Map<String, String> map : fieldValues) {
            int row = Integer.parseInt(map.get("position_y"));
            int col = Integer.parseInt(map.get("position_x"));

            board.board[row - 1][col - 1] = Die.mapToDie(map);
        }
        return board;
    }

    public static void createBoards(final Game game) {
        Map<Player, ArrayList<int[]>> boards = new HashMap<>();
        game.getPlayers().forEach((player) -> {
            boards.put(player, new ArrayList<>());
            for (int row = 1; row <= ROWS; row++) {
                for (int col = 1; col <= COLUMNS; col++) {
                    boards.get(player).add(new int[] { col, row });
                }
            }
        });

        BoardDB.createBoard(boards);
    }

    private int sets(final int points, final Object... values) {
        ArrayList<Object> dices = new ArrayList<>();

        for (int i = 0; i < values.length; i++) {
            for (int row = 1; row <= ROWS; row++) {
                for (int col = 1; col <= COLUMNS; col++) {
                    Die die = getField(row, col);
                    if (die != null && valueMatches(die, values[i])) {
                        dices.add(values[i]);
                    }
                }
            }
        }

        int completeSets = countSets(dices, values);
        return completeSets * points;
    }

    public static int countSets(List<Object> diceArray, final Object... values) {
        int amountOfSets = 0;
        ArrayList<Integer> frequencyList = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            frequencyList.add(Collections.frequency(diceArray, values[i]));
        }

        int minCount = Collections.min(frequencyList);

        amountOfSets += minCount;

        return amountOfSets;
    }

    private boolean valueMatches(final Die die, final Object value) {
        if (value instanceof Integer) {
            return die.getEyes() == (int) value;
        } else if (value instanceof String) {
            return ColorEnum.fromString(die.getColor().toString()).toString().equalsIgnoreCase((String) value);
        }
        return false;
    }

    private int rows(final int points, final String type) {
        int totalScore = 0;
        int dices = 0;
        for (int row = 1; row <= ROWS; row++) {
            dices = 0;
            Die firstDie = getField(row, 1);
            if (firstDie == null) {
                continue;
            }
            dices++;
            String compareWith = null;

            if (type.equals("shades")) {
                int eyes = firstDie.getEyes();
                compareWith = String.valueOf(eyes);
            } else {
                Color color = firstDie.getColor();
                compareWith = ColorEnum.fromString(color.toString()).toString();
            }

            if (compareWith != null) {
                for (int col = 1; col <= COLUMNS; col++) {
                    Die die = getField(row, col);
                    if (die != null && (ColorEnum.fromString(die.getColor().toString()).toString().equals(compareWith)
                            || (type.equals("shades") && die.getEyes() != Integer.parseInt(compareWith)))) {
                        dices++;
                    }

                    if (dices >= COLUMNS && dices % COLUMNS == 0) {
                        totalScore += points;
                    }
                }
            }

        }

        return totalScore;
    }

    private int columns(final int points, final String type) {
        int totalScore = 0;
        int dices = 0;
        for (int col = 5; col <= COLUMNS; col++) {
            dices = 0;
            Die firstDie = getField(1, col);

            if (firstDie == null) {
                continue;
            }

            dices++;

            String compareWith = null;
            if (type.equals("shades")) {
                int eyes = firstDie.getEyes();
                compareWith = String.valueOf(eyes);
            } else {
                Color color = firstDie.getColor();
                compareWith = ColorEnum.fromString(color.toString()).toString();
            }

            if (compareWith != null) {
                for (int row = 1; row <= ROWS; row++) {
                    Die die = getField(row, col);

                    if (die != null && (!ColorEnum.fromString(die.getColor().toString()).toString().equals(compareWith)
                            || (type.equals("shades") && die.getEyes() != Integer.parseInt(compareWith)))) {
                        dices++;
                    }

                    if (dices >= ROWS && dices % ROWS == 0) {
                        totalScore += points;
                    }
                }
            }
        }

        return totalScore;
    }

    private int diagonallySameColor() {
        int count = 0;
        ArrayList<Die> visitedDice = new ArrayList<>();

        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                Die die = getField(row, col);
                if (die == null) {
                    continue;
                }
                ArrayList<int[]> diagonalNeighbors = getDiagonalNeighbors(row, col);
                for (int[] neighbor : diagonalNeighbors) {
                    Die neighborDie = getField(neighbor[0], neighbor[1]);
                    if (neighborDie != null && die.getColor().equals(neighborDie.getColor())
                            && !visitedDice.contains(neighborDie)) {
                        count++;
                        visitedDice.add(neighborDie);
                    }
                }
            }
        }

        return count;
    }

    private ArrayList<int[]> getDiagonalNeighbors(final int row, final int col) {
        ArrayList<int[]> neighbors = new ArrayList<>();

        int[][] offsets = new int[][] { { -1, -1 }, { -1, 1 }, { 1, -1 }, { 1, 1 } };

        for (int[] offset : offsets) {
            int neighborRow = row + offset[0];
            int neighborCol = col + offset[1];

            if (neighborRow >= 1 && neighborRow <= ROWS && neighborCol >= 1 && neighborCol <= COLUMNS) {
                neighbors.add(new int[] { neighborRow, neighborCol });
            }
        }

        return neighbors;
    }
}
