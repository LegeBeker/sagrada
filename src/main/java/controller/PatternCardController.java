package main.java.controller;

import main.java.model.PatternCard;

public class PatternCardController {
    public PatternCard getPatternCard(final int cardId) {
        return PatternCard.get(cardId);
    }
}
