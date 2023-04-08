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


    private PatternCardField[][] fields = new PatternCardField[COLUMNS][ROWS];

    public static PatternCard get(final int idPatternCard) {
        PatternCard patternCard = new PatternCard();

        Map<String, String> patternCardMap = PatternCardDB.get(idPatternCard);

        patternCard.idPatternCard = Integer.parseInt(patternCardMap.get("idpatterncard"));
        patternCard.name = patternCardMap.get("name");
        patternCard.difficulty = Integer.parseInt(patternCardMap.get("difficulty"));
        patternCard.standard = Boolean.parseBoolean(patternCardMap.get("standard"));

        List<Map<String, String>> patternCardFieldMap = PatternCardFieldDB.getAllFieldsForCard(idPatternCard);

        for (Map<String,String> map : patternCardFieldMap) {
            int x = Integer.parseInt(map.get("xposition"));
            int y = Integer.parseInt(map.get("yposition"));
            int value = Integer.parseInt(map.get("value"));
            Color color = Color.valueOf(map.get("color"));

            patternCard.fields[x][y] = new PatternCardField(color,value); 
        }

        return patternCard;
    }


    public int getIdPatternCard() {
        return this.idPatternCard;
    }




}
