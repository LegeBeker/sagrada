package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import main.java.db.PlayerDB;

public class Player {
    private int idPlayer;

    private String username;
    private int idGame;

    private String playStatus;
    private int seqnr;

    private String privateObjCardColor;
    private Integer idPatternCard;

    private int score;

    public Player createPlayer(final int gameID, final String username) {
        Player newPlayer = new Player();
        newPlayer.setIdGame(gameID);
        newPlayer.setUsername(username);

        // TODO colors and refine the status
        newPlayer.setPlayStatus("CHALLENGEE");
        newPlayer.setPrivateObjCardColor("red");

        return newPlayer;
    }

    public void addPlayerToDB() {
        PlayerDB.createPlayer(this.username, this.idGame, this.playStatus, this.privateObjCardColor);
        Map<String, String> addedPlayer = PlayerDB.getRecentPlayerFromGame(this.idGame).get(0);
        this.setId(Integer.parseInt(addedPlayer.get("idplayer")));
    }

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

    public Die[][] getBoard() {
        return this.board;
    }

    public String getPrivateObjCardColor() {
        return this.privateObjCardColor;
    }

    public PatternCard getPatternCard() {
        if (this.idPatternCard == null) {
            return null;
        }
        return PatternCard.get(this.idPatternCard);
    }

    public int getScore() {
        return this.score;
    }

    public void setId(final int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setIdGame(final int idGame) {
        this.idGame = idGame;
    }

    public void setPlayStatus(final String playStatus) {
        this.playStatus = playStatus;
    }

    public void setSeqnr(final int seqnr) {
        this.seqnr = seqnr;
    }

    public void setPrivateObjCardColor(final String privateObjCardColor) {
        this.privateObjCardColor = privateObjCardColor;
    }

    public void setIdPatternCard(final int idPatternCard) {
        this.idPatternCard = idPatternCard;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public static Player get(final int idPlayer) {
        return mapToPlayer(PlayerDB.get(idPlayer));
    }

    public boolean acceptInvite() {
        return PlayerDB.acceptInvite(this.idGame, this.username);
    }

    public boolean refuseInvite() {
        return PlayerDB.refuseInvite(this.idGame, this.username);
    }

    public static ArrayList<Player> getAll() {
        ArrayList<Player> players = new ArrayList<Player>();

        for (Map<String, String> playerMap : PlayerDB.getAll()) {
            Player player = mapToPlayer(playerMap);
            players.add(player);
        }

        return players;
    }

    public static Player mapToPlayer(final Map<String, String> playerMap) {
        Player player = new Player();

        player.idPlayer = Integer.parseInt(playerMap.get("idplayer"));
        player.username = playerMap.get("username");
        player.idGame = Integer.parseInt(playerMap.get("idgame"));
        player.playStatus = playerMap.get("playstatus");
        player.seqnr = Integer.parseInt(playerMap.get("seqnr"));
        player.privateObjCardColor = playerMap.get("private_objectivecard_color");
        if (playerMap.get("idpatterncard") != null) {
            player.idPatternCard = Integer.parseInt(playerMap.get("idpatterncard"));
        }
        player.score = Integer.parseInt(playerMap.get("score"));

        return player;
    }
}
