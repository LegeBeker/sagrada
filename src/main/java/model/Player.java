package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.db.PlayerDB;

public class Player {
    private int idPlayer;

    private String username;
    private int idGame;

    private String playStatus;
    private int seqnr;

    private String privateObjCardColor;
    private int idPatternCard;

    private int score;

    public int getId() {
        return this.idPlayer;
    }

    public String getUsername() {
        return this.username;
    }

    public Game getGame() {
        return Game.get(this.idGame);
    }

    public String getPlayStatus() {
        return this.playStatus;
    }

    public int getSeqnr() {
        return this.seqnr;
    }

    public String getPrivateObjCardColor() {
        return this.privateObjCardColor;
    }

    public PatternCard getPatternCard() {
        return PatternCard.get(this.idPatternCard);
    }

    public int getScore() {
        return this.score;
    }

    public static Player get(final int idPlayer) {
        Player player = new Player();

        Map<String, String> playerMap = PlayerDB.get(idPlayer);

        player.idPlayer = Integer.parseInt(playerMap.get("idplayer"));
        player.username = playerMap.get("username");
        player.idGame = Integer.parseInt(playerMap.get("idgame"));
        player.playStatus = playerMap.get("playstatus");
        player.seqnr = Integer.parseInt(playerMap.get("seqnr"));
        player.privateObjCardColor = playerMap.get("private_objectivecard_color");
        player.idPatternCard = Integer.parseInt(playerMap.get("idpatterncard"));
        player.score = Integer.parseInt(playerMap.get("score"));

        return player;
    }

    public static ArrayList<Player> getAll() {
        List<Map<String, String>> playerDB = PlayerDB.getAll();

        ArrayList<Player> players = new ArrayList<Player>();

        for (Map<String, String> playerMap : playerDB) {
            Player player = new Player();

            player.idPlayer = Integer.parseInt(playerMap.get("idplayer"));
            player.username = playerMap.get("username");
            player.idGame = Integer.parseInt(playerMap.get("idgame"));
            player.playStatus = playerMap.get("playstatus");
            player.seqnr = Integer.parseInt(playerMap.get("seqnr"));
            player.privateObjCardColor = playerMap.get("private_objectivecard_color");
            player.idPatternCard = Integer.parseInt(playerMap.get("idpatterncard"));
            player.score = Integer.parseInt(playerMap.get("score"));

            players.add(player);
        }

        return players;
    }
}
