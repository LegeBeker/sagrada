package main.java.model;

import java.util.List;
import java.util.Map;
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
            PatternCardField field = new PatternCardField();

            field.setColor(map.get("color"));
            field.setValue(map.get("value"));

            int row = Integer.parseInt(map.get("position_x"));
            int col = Integer.parseInt(map.get("position_y"));

            patternCard.fields[row - 1][col - 1] = field;
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
}
