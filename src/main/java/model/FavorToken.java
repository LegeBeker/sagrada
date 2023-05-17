package main.java.model;

import java.util.ArrayList;
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

    public static ArrayList<FavorToken> getFavorTokensForToolCard(final int toolCardId, final int gameId){

        ArrayList<FavorToken> favorTokens = new ArrayList<>();

        for (Map<String, String> row : GameFavorTokenDB.getFavorTokensForToolCard(toolCardId, gameId)) {
            FavorToken ft = new FavorToken();

            ft.idFavorToken = Integer.parseInt(row.get("idfavortoken"));
            ft.idGame = Integer.parseInt(row.get("idgame"));
            ft.idPlayer = Integer.parseInt(row.get("idplayer"));
            ft.gameToolCardId = Integer.parseInt(row.get("gametoolcard"));
            ft.roundId = Integer.parseInt(row.get("roundID"));

            favorTokens.add(ft);
        }

        return favorTokens;

    };
}