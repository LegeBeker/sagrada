package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;
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

    private static Map<Integer, PatternCard> cachedCards = new HashMap<Integer, PatternCard>();

    private boolean validateNeighbors = true;
    private boolean validateColors = true;
    private boolean validateEyes = true;

    private PatternCardField[][] fields = new PatternCardField[ROWS][COLUMNS];

    public static PatternCard get(final int idPatternCard) {
        if (!cachedCards.containsKey(idPatternCard)) {
            cachedCards.put(idPatternCard, mapToPatternCard(PatternCardDB.get(idPatternCard)));
        }
        return cachedCards.get(idPatternCard);
    }

    public static ArrayList<PatternCard> getDefaultCards() {
        ArrayList<PatternCard> defaultCards = new ArrayList<PatternCard>();
        for (Map<String, String> cardInfo : PatternCardDB.getAllStandard()) {
            if (!cachedCards.containsKey(Integer.parseInt(cardInfo.get("idpatterncard")))) {
                cachedCards.put(Integer.parseInt(cardInfo.get("idpatterncard")),
                        mapToPatternCard(cardInfo));
            }

            defaultCards.add(cachedCards.get(Integer.parseInt(cardInfo.get("idpatterncard"))));
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

    public void setValidateNeighbors(final boolean validateNeighbors) {
        this.validateNeighbors = validateNeighbors;
    }

    public void setValidateColors(final boolean validateColor) {
        this.validateColors = validateColor;
    }

    public void setValidateEyes(final boolean validateEyes) {
        this.validateEyes = validateEyes;
    }

    public static PatternCard mapCustomPatternCard(final int id, final int difficulty,
            final PatternCardField[][] fields) {
        PatternCard patternCard = new PatternCard();

        patternCard.idPatternCard = id;
        patternCard.name = "Gegenereerde kaart " + id;
        patternCard.difficulty = difficulty;
        patternCard.standard = false;
        patternCard.fields = fields;

        cachedCards.put(id, patternCard);

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

    public static int createPatternCard(final PatternCard patternCard) {
        return PatternCardDB.createPatternCard(patternCard);
    }

    public ArrayList<int[]> getPossibleMoves(final Board board, final int dieValue, final Color dieColor) {
        ArrayList<int[]> possibleMoves = new ArrayList<int[]>();
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                if (validateMove(board, dieValue, dieColor, col, row)) {
                    possibleMoves.add(new int[] {row, col});
                }
            }
        }

        return possibleMoves;
    }

    public ArrayList<int[]> getBestMoves(final Board board, final ArrayList<int[]> possibleMoves, final int dieValue,
            final Color dieColor) {
        ArrayList<int[]> bestMoves = new ArrayList<int[]>();
        boolean thereAreNoColorMoves = true;
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                for (int[] move : possibleMoves) {
                    if (this.getField(move[0], move[1]).getColor() != null && this.getField(move[0], move[1]).getColor().equals(dieColor)) {
                        bestMoves.add(move);
                        thereAreNoColorMoves = false;
                    }
                }
                if (thereAreNoColorMoves) {
                    for (int[] move : possibleMoves) {
                        if (this.getField(move[0], move[1]).getValue() != null && this.getField(move[0], move[1]).getValue() == dieValue) {
                            bestMoves.add(move);
                        }
                    }
                }
            }
        }
        return bestMoves;
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

            if (this.validateNeighbors && !board.isEmpty() && this.neighborsEmpty(rowIndex, columnIndex, board)) {
                return false;
            }

            if (!validateAgainstAdjacentFields(rowIndex, columnIndex, dieValue, dieColor, board)) {
                return false;
            }

            return true;
        }

        if (this.validateNeighbors && !board.isEmpty() && this.neighborsEmpty(rowIndex, columnIndex, board)) {
            return false;
        }

        if (this.validateColors && this.getField(rowIndex, columnIndex).getColor() != null
                && !dieColor.equals(this.getField(rowIndex, columnIndex).getColor())) {
            return false;
        }

        if (this.validateEyes && this.getField(rowIndex, columnIndex).getValue() != null
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

    public static ArrayList<int[]> getNeighbors(final int row, final int col, final boolean includeDiagonals) {
        ArrayList<int[]> neighbors = new ArrayList<>();
        int[][] offsets;

        if (includeDiagonals) {
            offsets = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1},
                    {1, 1}};
        } else {
            offsets = new int[][] {{-1, 0 }, {1, 0}, {0, -1}, {0, 1}};
        }

        for (int[] offset : offsets) {
            int neighborRow = row + offset[0];
            int neighborCol = col + offset[1];

            if (neighborRow >= 1 && neighborRow <= ROWS && neighborCol >= 1 && neighborCol <= COLUMNS) {
                neighbors.add(new int[] {neighborRow, neighborCol });
            }
        }

        return neighbors;
    }

    public static void clearCache() {
        cachedCards.clear();
    }

    public void resetValidation() {
        this.validateColors = true;
        this.validateEyes = true;
        this.validateNeighbors = true;
    }
}
