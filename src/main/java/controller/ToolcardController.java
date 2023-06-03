package main.java.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import main.java.db.DieDB;
import main.java.db.ToolCardDB;
import main.java.model.Die;
import main.java.model.FavorToken;
import main.java.model.PatternCard;
import main.java.model.ToolCard;

public class ToolcardController {
    private static final int MAXDIEVALUE = 6;
    private static final int TURNCOUNT = 2;
    private static final int MAXVALUEDIESUM = 7;
    private ViewController view;
    private static final Random RANDOM = new Random();

    public ToolcardController(final ViewController view) {
        this.view = view;
    }

    public static Map<String, String> getToolCard(final int gameId, final String toolCardName) {
        return ToolCard.getToolCard(gameId, toolCardName);
    }

    public static String grozingPliers(final int gameId, final int dieNumber, final String dieColor,
            final String choiceAction) {
        // -- choiceaction is a string which can contain "increment" or "decrement".
        // Based on this you can trigger the corresponding values
        String returnValue = null;
        int currentAmountOfEyes = DieDB.getGameDieEyes(gameId, dieNumber, dieColor);
        if (choiceAction.equals("increment")) {
            if (currentAmountOfEyes == MAXDIEVALUE) {
                returnValue = "De waarde is 6, en kan niet 7 worden.";
            } else {
                ToolCardDB.updateGameDieValue(gameId, dieNumber, dieColor, currentAmountOfEyes + 1);
            }
        } else if (choiceAction.equals("decrement")) {
            if (currentAmountOfEyes == 1) {
                returnValue = "De waarde is 1, en kan niet 0 worden.";

            } else {
                ToolCardDB.updateGameDieValue(gameId, dieNumber, dieColor, currentAmountOfEyes - 1);
            }
        } else {
            returnValue = "Invalid choice.";
        }

        return returnValue;
    }

    public static void grindingStone(final int gameId, final int dieNumber, final String dieColor) {
        int dieValue = DieDB.getGameDieEyes(gameId, dieNumber, dieColor);
        int newValue = MAXVALUEDIESUM - dieValue;
        ToolCardDB.updateGameDieValue(gameId, dieNumber, dieColor, newValue);
    }

    public static void fluxBrush(final int gameId, final int dieNumber, final String dieColor) {
        int currentValue = DieDB.getGameDieEyes(gameId, dieNumber, dieColor);
        int newValue;
        do {
            newValue = RANDOM.nextInt(MAXDIEVALUE) + 1;
        } while (newValue == currentValue);

        ToolCardDB.updateGameDieValue(gameId, dieNumber, dieColor, newValue);
    }

    public static Boolean glazingHammer(final int turnCount, final int gameId, final int roundId) {
        if (turnCount == TURNCOUNT) {
            List<Die> gameOffer = Die.getOffer(gameId, roundId);
            for (Die die : gameOffer) {
                int newValue = RANDOM.nextInt(MAXDIEVALUE) + 1;
                ToolCardDB.updateGameDieValue(gameId, die.getNumber(), die.getColor().toString(), newValue);
            }

            return true;
        }
        return false;
    }

    public void lensCutter(final int gameId, final int currentRoundId, final int dieNumberOffer, final String dieColorOffer, final int dieNumberRoundTrack, final String dieColorRoundTrack) {
        ToolCardDB.lensCutter(gameId, currentRoundId, dieNumberOffer, dieColorOffer, dieNumberRoundTrack, dieColorRoundTrack);
    }

    public static void fluxRemover(final int gameId, final int dieNumber, final String dieColor, final int roundId) {
        ToolCardDB.addDieToBag(gameId, dieColor, dieNumber, roundId);
    }

    public void corkBackedStraightEdge() {
        PatternCard patternCard = view.getCurrentPlayer().getPatternCard();
        patternCard.setValidateNeighbors(false);
    }

    public void eglomiseBrush() {
        PatternCard patternCard = view.getCurrentPlayer().getPatternCard();
        patternCard.setValidateColors(false);
    }

    public void copperFoilBurnisher() {
        PatternCard patternCard = view.getCurrentPlayer().getPatternCard();
        patternCard.setValidateEyes(false);
    }

    public List<Map<String, String>> getFavorTokensForToolCard(final int toolCardId, final int gameId) {
        return FavorToken.getFavorTokensForToolCard(toolCardId, gameId);
    }

    public int getToolCardPrice(final String toolcardName, final int gameId) {
        return FavorToken.getToolCardPrice(toolcardName, gameId);
    }

    public void buyToolCard(final String toolcardName, final int gameId, final int playerId,
            final int amountFavorTokens, final int roundId) {
        FavorToken.buyToolCard(toolcardName, gameId, playerId, amountFavorTokens, roundId);
    }
}
