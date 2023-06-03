package main.java.controller;

import java.util.List;
import java.util.Map;

import main.java.model.FavorToken;

public class FavorTokenController {
    private ViewController view;

    public FavorTokenController(final ViewController view) {
        this.view = view;
    }

    public List<Map<String, String>> getFavorTokensForToolCard(final int toolCardId, final int gameId) {
        return FavorToken.getFavorTokensForToolCard(toolCardId, gameId);
    }

    public int getToolCardPrice(final String toolcardName, final int gameId){

        return FavorToken.getToolCardPrice(toolcardName, gameId);

    }

    public void buyToolCard(final String toolcardName, final int gameId, final int playerId, final int amountFavorTokens, final int roundId){
        FavorToken.buyToolCard(toolcardName, gameId, playerId, amountFavorTokens, roundId);
    }
}
