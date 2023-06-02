package main.java.model;

import java.util.ArrayList;
import java.util.Arrays;
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
                    emptyPlaces++;
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
            switch (integer) {
                case 1:
                    publicObjectiveScore += sets(5, 1, 2, 3, 4, 5, 6);
                    break;
                case 2:
                    publicObjectiveScore += sets(2, 3, 4);
                    break;
                case 3:
                    publicObjectiveScore += columns(4, "shades");
                    break;
                case 4:
                    publicObjectiveScore += columns(5, "colors");
                    break;
                case 5:
                    publicObjectiveScore += sets(2, 5, 6);
                    break;
                case 6:
                    publicObjectiveScore += sets(4, "red", "blue", "green", "yellow", "purple");
                    break;
                case 7:
                    publicObjectiveScore += rows(5, "colors");
                    break;
                case 8:
                    publicObjectiveScore += diagonallySameColor();
                    break;
                case 9:
                    publicObjectiveScore += sets(2, 1, 2);
                    break;
                case 10:
                    publicObjectiveScore += rows(5, "shades");
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

    public static Board update(final Board board) {
        List<Map<String, String>> fieldValues = BoardDB.getBoard(board.player.getGame().getId(), board.player.getId());

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

    private int sets(int points, Object... values) {
        Set<Object> hashSet = new HashSet<>();
        Set<Object> targetSet = new HashSet<>(Arrays.asList(values));
        for (int i = 0; i < values.length; i++) {
            for (int row = 1; row <= ROWS; row++) {
                for (int col = 1; col <= COLUMNS; col++) {
                    Die die = getField(row, col);
                    if (die != null && valueMatches(die, values[i])) {
                        hashSet.add(values[i]);
                    }
                }
            }
        }

        int completeSets = countSubsetOccurrences(hashSet, targetSet);
        return completeSets * points;
    }

    private static int countSubsetOccurrences(Set<Object> set, Set<Object> targetSet) {
        int count = 0;
        for (Object element : set) {
            if (targetSet.contains(element)) {
                targetSet.remove(element);
                if (targetSet.isEmpty()) {
                    count++;
                    targetSet.addAll(new HashSet<>(targetSet));
                }
            }
        }

        return count;
    }

    private boolean valueMatches(Die die, Object value) {
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
            Die firstDie = getField(row, 1);
            if (firstDie == null) {
                continue;
            }

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
                    if (dices % 4 == 0) {
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
        for (int col = 1; col <= COLUMNS; col++) {
            Die firstDie = getField(1, col);

            if (firstDie == null) {
                continue;
            }

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
                    if (die != null && (ColorEnum.fromString(die.getColor().toString()).toString().equals(compareWith)
                            || (type.equals("shades") && die.getEyes() != Integer.parseInt(compareWith)))) {
                        dices++;
                    }
                    if (dices % 5 == 0) {
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
                if(die == null) {
                    continue;
                }
                ArrayList<int[]> diagonalNeighbors = getDiagonalNeighbors(row, col);
                for (int[] neighbor : diagonalNeighbors) {
                    Die neighborDie = getField(neighbor[0], neighbor[1]);
                    if (neighborDie != null && die.getColor().equals(neighborDie.getColor()) && !visitedDice.contains(neighborDie)) {
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

        int[][] offsets = new int[][] {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};


        for (int[] offset : offsets) {
            int neighborRow = row + offset[0];
            int neighborCol = col + offset[1];

            if (neighborRow >= 1 && neighborRow <= ROWS && neighborCol >= 1 && neighborCol <= COLUMNS) {
                neighbors.add(new int[] {neighborRow, neighborCol});
            }
        }

        return neighbors;
    }

}
