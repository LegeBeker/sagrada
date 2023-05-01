package main.java.controller;

import javafx.scene.paint.Color;
import main.java.model.PatternCard;

public class PatternCardController {
    public PatternCard getPatternCard(final int cardId) {
        return PatternCard.get(cardId);
    }

    public boolean validateMove(final PatternCard patternCard, final int dieValue, final Color dieColor,
            final int columnIndex, final int rowIndex) {
        return patternCard.validateMove(dieValue, dieColor, columnIndex, rowIndex);
    }
}
