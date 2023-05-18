package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.PatternCardDB;

public class PatternCard {
    private int idPatternCard;

    private String name;
    private Integer difficulty;

    private Boolean standard;

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private PatternCardField[][] fields = new PatternCardField[ROWS][COLUMNS];

    public static PatternCard get(final int idPatternCard) {
        return mapToPatternCard(PatternCardDB.get(idPatternCard));
    }

    public static ArrayList<PatternCard> getDefaultCards() {
        ArrayList<PatternCard> defaultCards = new ArrayList<PatternCard>();
        for (Map<String, String> cardInfo : PatternCardDB.getAllStandard()) {
            PatternCard card = mapToPatternCard(cardInfo);
            defaultCards.add(card);
        }
        return defaultCards;
    }

    public static PatternCard mapToPatternCard(final Map<String, String> patternCardMap) {
        PatternCard patternCard = new PatternCard();

        patternCard.idPatternCard = Integer.parseInt(patternCardMap.get("idpatterncard"));
        patternCard.name = patternCardMap.get("name");
        patternCard.difficulty = Integer.parseInt(patternCardMap.get("difficulty"));
        patternCard.standard = Boolean.parseBoolean(patternCardMap.get("standard"));

        for (Map<String, String> map : PatternCardDB.getAllFieldsForCard(patternCard.idPatternCard)) {
            int row = Integer.parseInt(map.get("position_y"));
            int col = Integer.parseInt(map.get("position_x"));

            patternCard.fields[row - 1][col - 1] = PatternCardField.mapToPatternCardField(map);
        }

        return patternCard;
    }

    public int getIdPatternCard() {
        return this.idPatternCard;
    }

    public String getName() {
        return this.name;
    }

    public Integer getDifficulty() {
        return this.difficulty;
    }

    public Boolean getStandard() {
        return this.standard;
    }

    public PatternCardField getField(final int row, final int column) {
        return fields[row - 1][column - 1];
    }

    public ArrayList<int[]> getPossibleMoves(final Board board, final int dieValue, final Color dieColor) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                if (validateMove(board, dieValue, dieColor, col, row)) {
                    possibleMoves.add(new int[]{row, col});
                }
            }
        }

        return possibleMoves;
    }

    public boolean validateMove(final Board board, final int dieValue, final Color dieColor, final int columnIndex,
            final int rowIndex) {

        if (board.getField(rowIndex, columnIndex) != null) {
            return false;
        }

        if (board.isEmpty() && !isOnSideOrCorner(rowIndex, columnIndex)) {
            return false;
        }

        if (this.getField(rowIndex, columnIndex).getColor() == null
                && this.getField(rowIndex, columnIndex).getValue() == null) {
            if (!neighborsEmpty(rowIndex, columnIndex, board)
                    && !validateAgainstAdjacentFields(rowIndex, columnIndex, dieValue,
                            dieColor, board)
                    && !board.isEmpty()) {

                return false;
            }
            return true;
        }

        if (!board.isEmpty() && this.neighborsEmpty(rowIndex, columnIndex, board)) {
            return false;
        }

        if (this.getField(rowIndex, columnIndex).getColor() != null
                && !dieColor.equals(this.getField(rowIndex, columnIndex).getColor())) {
            return false;
        }

        if (this.getField(rowIndex, columnIndex).getValue() != null
                && dieValue != this.getField(rowIndex, columnIndex).getValue()) {

            return false;
        }

        if (!validateAgainstAdjacentFields(rowIndex, columnIndex, dieValue,
                dieColor, board)) {
            return false;
        }

        return true;
    }

    private boolean isOnSideOrCorner(final int row, final int col) {
        return row == 1 || row == ROWS || col == 1 || col == COLUMNS;
    }

    private boolean validateAgainstAdjacentFields(
            final int rowIndex,
            final int columnIndex,
            final int dieValue,
            final Color dieColor,
            final Board board) {
        ArrayList<int[]> neighbors = getNeighbors(rowIndex, columnIndex, false);
        for (int[] neighbor : neighbors) {
            Die neighborDie = board.getField(neighbor[0], neighbor[1]);

            if (neighborDie != null && dieColor.equals(neighborDie.getColor())) {
                return false;
            }

            if (neighborDie != null && dieValue == neighborDie.getEyes()) {
                return false;
            }
        }

        return true;
    }

    private boolean neighborsEmpty(final int row, final int col, final Board board) {
        ArrayList<int[]> neighbors = getNeighbors(row, col, true);

        for (int[] neighbor : neighbors) {
            if (board.getField(neighbor[0], neighbor[1]) != null) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<int[]> getNeighbors(final int row, final int col, final boolean includeDiagonals) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[][] offsets;

        if (includeDiagonals) {
            offsets = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1},{1, 1}};
        } else {
            offsets = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        }

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
