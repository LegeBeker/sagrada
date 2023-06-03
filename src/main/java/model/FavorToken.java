package main.java.model;

import java.util.List;
import java.util.Map;

import main.java.db.GameFavorTokenDB;

public class FavorToken {
    private int idFavorToken;
    private int idGame;
    private int idPlayer;
    private int gameToolCardId;
    private int roundId;

    public int getIdFavorToken() {
        return idFavorToken;
    }

    public int getIdGame() {
        return idGame;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public int getGameToolCardId() {
        return gameToolCardId;
    }

    public int getRoundId() {
        return roundId;
    }

    public static List<Map<String, String>> getFavorTokensForToolCard(final int toolCardId, final int gameId) {
        return GameFavorTokenDB.getFavorTokensForToolCard(toolCardId, gameId);
    };

    public static int getToolCardPrice(final String toolcardName, final int gameId){
        return GameFavorTokenDB.getToolCardPrice(toolcardName, gameId);
    }

    public static void buyToolCard(final String toolCardName, final int gameId, final int playerId, final int amountFavorTokens, final int roundId){
        GameFavorTokenDB.buyToolCard(toolCardName, gameId, playerId, amountFavorTokens, roundId);
    }
}
