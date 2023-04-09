package main.java.model;

import java.util.ArrayList;
import java.util.List;
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

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public static Game get(final int idGame) {
        Game game = new Game();

        Map<String, String> gameMap = GameDB.get(idGame);

        game.idGame = Integer.parseInt(gameMap.get("idgame"));
        game.turnIdPlayer = Integer.parseInt(gameMap.get("turn_idplayer"));
        game.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
        game.creationDate = gameMap.get("creationdate");

        List<Map<String, String>> playersMap = GameDB.getPlayers(idGame);

        for (Map<String, String> map : playersMap) {
            Player player = new Player();

            player.setId(Integer.parseInt(map.get("idplayer")));
            player.setUsername(map.get("username"));
            player.setIdGame(Integer.parseInt(map.get("idgame")));
            player.setPlayStatus(map.get("playstatus"));
            player.setSeqnr(Integer.parseInt(map.get("seqnr")));
            player.setPrivateObjCardColor(map.get("privateobjcardcolor"));
            player.setIdPatternCard(Integer.parseInt(map.get("idpatterncard")));
            player.setScore(Integer.parseInt(map.get("score")));

            game.players.add(player);
        }

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

            List<Map<String, String>> playersMap = GameDB.getPlayers(game.idGame);

            for (Map<String, String> map : playersMap) {
                Player player = new Player();

                player.setId(Integer.parseInt(map.get("idplayer")));
                player.setUsername(map.get("username"));
                player.setIdGame(Integer.parseInt(map.get("idgame")));
                player.setPlayStatus(map.get("playstatus"));
                player.setSeqnr(Integer.parseInt(map.get("seqnr")));
                player.setPrivateObjCardColor(map.get("private_objectivecard_color"));
                if (map.get("idpatterncard") != null) {
                    player.setIdPatternCard(Integer.parseInt(map.get("idpatterncard")));
                }
                player.setScore(Integer.parseInt(map.get("score")));

                game.players.add(player);
            }

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
