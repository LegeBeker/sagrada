package main.java.controller;

import javafx.scene.paint.Color;
import main.java.model.PatternCard;
import main.java.model.Player;

public class PatternCardController {

    private ViewController view;

    public PatternCardController(final ViewController view) {
        this.view = view;
    }

    public PatternCard getPatternCard(final int cardId) {
        return PatternCard.get(cardId);
    }

    public boolean doMove(final PatternCard patternCard, final int dieValue, final Color dieColor,
            final int columnIndex, final int rowIndex) {
        GameController gameController = view.getGameController();
        Player player = gameController.getCurrentPlayer(gameController.getGame().getId());

        boolean valid = patternCard.validateMove(player.getBoard(), dieValue, dieColor, columnIndex, rowIndex);

        if (valid) {
            return player.getBoard().placeDie(dieValue, dieColor, rowIndex, columnIndex);
        }

        return false;
    }
}
