package main.java.model;

import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.PatternCardDB;

public class PatternCard {
    private int idPatternCard;

    private String name;
    private int difficulty;

    private Boolean standard;

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private PatternCardField[][] fields = new PatternCardField[ROWS][COLUMNS];

    public static PatternCard get(final int idPatternCard) {
        return mapToPatternCard(PatternCardDB.get(idPatternCard));
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

    public int getDifficulty() {
        return this.difficulty;
    }

    public Boolean getStandard() {
        return this.standard;
    }

    public PatternCardField getField(final int row, final int column) {
        return fields[row - 1][column - 1];
    }

    public boolean validateMove(final Board board, final int dieValue, final Color dieColor, final int columnIndex,
            final int rowIndex) {

        // check if position not empty
        if (board.getField(rowIndex, columnIndex) != null) {
            return false;
        }
        // check if die is on side or corner
        if (board.isEmpty() && !isOnSideOrCorner(rowIndex, columnIndex)) {
            return false;
        }

        return true;
    }

    private boolean isOnSideOrCorner(int row, int col) {
        return row == 1 || row == 4 || col == 1 || col == 5;
    }


}
