package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.GameDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Game {
    private int idGame;

    private int turnIdPlayer;
    private int currentRound;

    private String creationDate;

    public int getId() {
        return this.idGame;
    }

    public Player getTurnPlayer() {
        return Player.get(this.turnIdPlayer);
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public String getCreationDate() {
        return this.creationDate;
    }

    public static Game get(final int idGame) {
        Game game = new Game();

        Map<String, String> gameMap = GameDB.get(idGame);

        game.idGame = Integer.parseInt(gameMap.get("idgame"));
        game.turnIdPlayer = Integer.parseInt(gameMap.get("turn_idplayer"));
        game.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
        game.creationDate = gameMap.get("creationdate");

        return game;
    }

    public static ArrayList<Game> getAll() {
        List<Map<String, String>> gamesDB = GameDB.getAll();

        ArrayList<Game> games = new ArrayList<Game>();

        for (Map<String, String> gameMap : gamesDB) {
            Game game = new Game();

            game.idGame = Integer.parseInt(gameMap.get("idgame"));
            game.turnIdPlayer = Integer.parseInt(gameMap.get("turn_idplayer"));
            game.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
            game.creationDate = gameMap.get("creationdate");

            games.add(game);
        }

        return games;
    }

    public StringProperty turnPlayerUsernameProperty() {
        return new SimpleStringProperty(getTurnPlayer().getUsername());
    }

    public StringProperty creationDateShowProperty() {
        final int year = 5;
        return new SimpleStringProperty(getCreationDate().substring(year));
    }
}
