package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.factories.PatternCardFieldsFactory;
import main.java.model.PatternCard;
import main.java.model.PatternCardField;
import main.java.model.Player;

public class PatternCardController {

    private ViewController view;
    private static final int PATTERN_CARD_GENERATE_AMOUNT = 4;

    public PatternCardController(final ViewController view) {
        this.view = view;
    }

    public PatternCard getPatternCard(final int cardId) {
        return PatternCard.get(cardId);
    }

    public boolean doMove(final int patternCardId, final int eyes, final Color dieColor, final int dieNumber,
            final int columnIndex, final int rowIndex, final String selectedToolCardName) {

        Player player = view.getCurrentPlayer();

        boolean valid = false; // -- Default value set to false
        if (selectedToolCardName != null) {
            valid = getPatternCard(patternCardId).validateMove(player.getBoard(), eyes, dieColor, columnIndex,
                    rowIndex, selectedToolCardName);
        } else {
            valid = getPatternCard(patternCardId).validateMove(player.getBoard(), eyes, dieColor, columnIndex,
                    rowIndex);
        }

        if (valid) {
            return player.getBoard().placeDie(dieColor, dieNumber, rowIndex, columnIndex);
        }

        return false;
    }

    public ArrayList<int[]> getPossibleMoves(final int eyes, final Color color) {
        Player player = view.getCurrentPlayer();

        return player.getPatternCard().getPossibleMoves(player.getBoard(), eyes, color);
    }

    public PatternCardField getPatternCardField(final int patternCardId, final int col, final int row) {
        return PatternCard.get(patternCardId).getField(col, row);
    }

    public Map<Integer, List<Integer>> generatePatternCardOptions() {
        PatternCard.clearCache();

        Map<Integer, List<Integer>> patternCardOptions = new HashMap<>();
        List<Integer> patternCardNumbers = new ArrayList<>();

        for (int i = 1; i <= PATTERN_CARD_GENERATE_AMOUNT; i++) {
            PatternCard patternCard = PatternCardFieldsFactory.generatePatternCard(i);
            patternCardNumbers.add(patternCard.getIdPatternCard());
        }

        patternCardOptions.put(view.getPlayerId(), patternCardNumbers);
        return patternCardOptions;
    }
}
