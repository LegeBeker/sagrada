package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.db.GameDB;

public class Game {
    private int idGame;

    private int turnIdPlayer;
    private int currentRound;

    private String creationDate;

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Die> offer = new ArrayList<>();

    public int getId() {
        return this.idGame;
    }
    
    public ArrayList<Die> getOffer() {
    	return this.offer;
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

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public void endTurn() {
        // TODO: implement
    }

    public static Game get(final int idGame) {
        return mapToGame(GameDB.get(idGame));
    }

    public static ArrayList<Game> getAll() {
        ArrayList<Game> games = new ArrayList<Game>();

        for (Map<String, String> gameMap : GameDB.getAll()) {
            Game game = mapToGame(gameMap);
            games.add(game);
        }

        return games;
    }

    public static Game mapToGame(final Map<String, String> gameMap) {
        Game game = new Game();

        game.idGame = Integer.parseInt(gameMap.get("idgame"));
        game.turnIdPlayer = Integer.parseInt(gameMap.get("turn_idplayer"));
        game.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
        game.creationDate = gameMap.get("creationdate");

        for (Map<String, String> map : GameDB.getPlayers(game.idGame)) {
            game.players.add(Player.mapToPlayer(map));
        }

        return game;
    }

    public StringProperty turnPlayerUsernameProperty() {
        return new SimpleStringProperty(getTurnPlayer().getUsername());
    }

    public StringProperty creationDateShowProperty() {
        final int year = 5;
        return new SimpleStringProperty(getCreationDate().substring(year));
    }

	public void getNewOffer() {
		this.offer = Die.getOffer(idGame, players.size());
		
	}
}
