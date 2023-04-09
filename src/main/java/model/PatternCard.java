package main.java.model;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.PatternCardDB;
import main.java.db.PatternCardFieldDB;

public class PatternCard {
    private int idPatternCard;

    private String name;
    private int difficulty;

    private Boolean standard;

    private static final int ROWS = 5;
    private static final int COLUMNS = 4;

    private PatternCardField[][] fields = new PatternCardField[ROWS][COLUMNS];

    public static PatternCard get(final int idPatternCard) {
        PatternCard patternCard = new PatternCard();

        Map<String, String> patternCardMap = PatternCardDB.get(idPatternCard);

        patternCard.idPatternCard = Integer.parseInt(patternCardMap.get("idpatterncard"));
        patternCard.name = patternCardMap.get("name");
        patternCard.difficulty = Integer.parseInt(patternCardMap.get("difficulty"));
        patternCard.standard = Boolean.parseBoolean(patternCardMap.get("standard"));

        List<Map<String, String>> patternCardFieldMap = PatternCardFieldDB.getAllFieldsForCard(idPatternCard);

        for (Map<String, String> map : patternCardFieldMap) {
            int row = Integer.parseInt(map.get("position_x"));
            int col = Integer.parseInt(map.get("position_y"));
            int value = setValue(map.get("value"));
            Color color = setColorCode(map.get("color"));

            patternCard.fields[row - 1][col - 1] = new PatternCardField(color, value);
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

    public PatternCardField[][] getFields() {
        return this.fields;
    }

    public PatternCardField getField(final int row, final int column) {
        return fields[row][column];
    }

    private static Color setColorCode(final String color) {
        if (color == null) {
            return Color.web("#F8F8F8");
        }

        switch (color) {
            case "red":
                return Color.web("#F44336");
            case "blue":
                return Color.web("#2196F3");
            case "green":
                return Color.web("#4CAF50");
            case "yellow":
                return Color.web("#FFEB3B");
            case "purple":
                return Color.web("#9C27B0");
            default:
                return Color.web("#F8F8F8");
        }
    }

    private static int setValue(final String value) {
        if (value != null) {
            return Integer.parseInt(value);
        }

        return 0;
    }

}
