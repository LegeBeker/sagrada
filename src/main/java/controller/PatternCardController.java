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

    public boolean validateMove(final PatternCard patternCard, final int dieValue, final Color dieColor,
            final int columnIndex, final int rowIndex) {
        GameController gameController = view.getGameController();
        Player player = gameController.getCurrentPlayer(gameController.getGame().getId());

        return patternCard.validateMove(player.getBoard(), dieValue, dieColor, columnIndex, rowIndex);
    }

    public boolean showPossibleMoves(final int value, final Color color) {
        GameController gameController = view.getGameController();
        Player player = gameController.getCurrentPlayer(gameController.getGame().getId());

        return player.getPatternCard().showPossibleMoves(player.getBoard(), value, color);
    }
}
