package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.GameDB;

public class Game {
    private int idGame;

    // private Player turnPlayer;
    private int turnPlayer;
    private int currentRound;

    private String creationDate;

    public static ArrayList<Game> getAll() {
        List<Map<String, String>> gamesDB = GameDB.getAll();

        ArrayList<Game> games = new ArrayList<Game>();

        for (Map<String, String> gameMap : gamesDB) {
            Game game = new Game();

            game.idGame = Integer.parseInt(gameMap.get("idgame"));
            // game.turnPlayer = new Player.get(Integer.parseInt(gameMap.get("turn_idplayer")));
            game.turnPlayer = Integer.parseInt(gameMap.get("turn_idplayer"));
            game.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
            game.creationDate = gameMap.get("creationdate");

            games.add(game);
        }

        return games;
    }

    public int getId() {
        return this.idGame;
    }

    // public Player getTurnPlayer() {
    public int getTurnPlayer() {
        return this.turnPlayer;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public String getCreationDate() {
        return this.creationDate;
    }
}
