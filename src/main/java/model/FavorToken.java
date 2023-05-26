package main.java.model;

import java.util.ArrayList;
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

    public int getGameToolCardId(){
        return gameToolCardId;
    }

    public int getRoundId(){
        return roundId;
    }

    public static List<Map<String, String>> getFavorTokensForToolCard(final int toolCardId, final int gameId){
        return GameFavorTokenDB.getFavorTokensForToolCard(toolCardId, gameId);
    };
}