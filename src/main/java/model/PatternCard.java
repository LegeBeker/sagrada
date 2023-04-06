package main.java.model;

import java.util.Map;

import main.java.db.PatternCardDB;

public class PatternCard {
    private int idPatternCard;

    private String name;
    private int difficulty;

    private Boolean standard;

    public static PatternCard get(final int idPatternCard) {
        PatternCard patternCard = new PatternCard();

        Map<String, String> patternCardMap = PatternCardDB.get(idPatternCard);

        patternCard.idPatternCard = Integer.parseInt(patternCardMap.get("idpatterncard"));
        patternCard.name = patternCardMap.get("name");
        patternCard.difficulty = Integer.parseInt(patternCardMap.get("difficulty"));
        patternCard.standard = Boolean.parseBoolean(patternCardMap.get("standard"));

        return patternCard;
    }
}
