package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.db.GameFavorTokenDB;
import javafx.scene.paint.Color;
import main.java.db.PatternCardDB;
import main.java.db.PlayerDB;

public class Player {
    private int idPlayer;

    private String username;
    private Game game;

    private String playStatus;
    private int seqnr;

    private Color color;

    private String privateObjCardColor;
    private PatternCard patternCard;
    private int unassignedFavortokensLeft;

    private Board board;

    private int score;

    public static Player createPlayer(final Game game, final String username, final String playerStatus,
            final String privateColor) {
        Player newPlayer = new Player();
        newPlayer.setGame(game);
        newPlayer.setUsername(username);
        newPlayer.setPlayStatus(playerStatus);
        newPlayer.setPrivateObjCardColor(privateColor);

        Map<String, String> idplayer = PlayerDB.createPlayer(username, game.getId(), playerStatus, privateColor).get(0);
        newPlayer.setId(Integer.parseInt(idplayer.get("idplayer")));

        return newPlayer;
    }

    public void assignGameFavorTokensToPlayer() {
        Integer patternCardDifficulty = getPatternCard().getDifficulty();
        for (int tokenNumber = 1; patternCardDifficulty >= tokenNumber; tokenNumber++) {
            GameFavorTokenDB.assignGameFavorTokenToPlayer(this.game.getId(), this.idPlayer);
        }

        this.unassignedFavortokensLeft = patternCardDifficulty;
    }

    public void addPlayerToDB() {
        PlayerDB.createPlayer(this.username, this.game.getId(), this.playStatus, this.privateObjCardColor);
        Map<String, String> addedPlayer = PlayerDB.getRecentPlayerFromGame(this.game.getId()).get(0);
        this.setId(Integer.parseInt(addedPlayer.get("idplayer")));
    }

    public int getId() {
        return this.idPlayer;
    }

    public String getUsername() {
        return this.username;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public Game getGame() {
        return this.game;
    }

    public String getPlayStatus() {
        return this.playStatus;
    }

    public int getSeqnr() {
        return this.seqnr;
    }

    public Board getBoard() {
        return this.board;
    }

    public String getPrivateObjCardColor() {
        return this.privateObjCardColor;
    }

    public Integer getPatternCardId() {
        if (this.patternCard != null) {
            return this.patternCard.getIdPatternCard();
        }
        return null;
    }

    public PatternCard getPatternCard() {
        return this.patternCard;
    }

    public int getFavorTokensLeft() {
        unassignedFavortokensLeft = 0;
        for (Map<String, String> token : GameFavorTokenDB.getFromPlayer(getId())) {
            if (token.get("gametoolcard") == null) {
                unassignedFavortokensLeft++;
            }
        }
        return unassignedFavortokensLeft;
    }

    public int getScore() {
        return this.score;
    }

    public void setId(final int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPlayStatus(final String playStatus) {
        this.playStatus = playStatus;
    }

    public void setSeqnr(final int seqnr) {
        this.seqnr = seqnr;
        PlayerDB.setSeqnr(getId(), seqnr);
    }

    public void setPrivateObjCardColor(final String privateObjCardColor) {
        this.privateObjCardColor = privateObjCardColor;
    }

    public void setPatternCard(final int idPatternCard) {
        this.patternCard = PatternCard.get(idPatternCard);
    }

    public void setScore(final int score) {
        this.score = score;
    }

    public boolean updateScore(final String score) {
        return PlayerDB.updateScore(getId(), score);
    }

    public static ArrayList<Player> getInvites(final String username) {
        ArrayList<Player> players = new ArrayList<Player>();

        for (Map<String, String> playerMap : PlayerDB.getAll(username)) {
            Player player = mapToPlayer(null, playerMap);
            players.add(player);
        }

        return players;
    }

    public static boolean acceptInvite(final int idGame, final String username) {
        return PlayerDB.acceptInvite(idGame, username);
    }

    public static boolean refuseInvite(final int idGame, final String username) {
        return PlayerDB.refuseInvite(idGame, username);
    }

    public boolean hasPatternCard() {
        return this.patternCard != null;
    }

    public boolean choosePatternCard(final int idPatternCard, final int idgame) {
        setPatternCard(idPatternCard);
        return PlayerDB.updatePatternCard(idPatternCard, idgame, this.username);
    }

    public static Player mapToPlayer(final Game game, final Map<String, String> playerMap) {
        Player player = new Player();

        player.idPlayer = Integer.parseInt(playerMap.get("idplayer"));
        player.username = playerMap.get("username");
        player.game = game;
        player.playStatus = playerMap.get("playstatus");
        player.score = Integer.parseInt(playerMap.get("score"));
        if (playerMap.get("seqnr") != null) {
            player.seqnr = Integer.parseInt(playerMap.get("seqnr"));
        }
        player.privateObjCardColor = playerMap.get("private_objectivecard_color");
        if (playerMap.get("idpatterncard") != null) {
            player.patternCard = PatternCard.get(Integer.parseInt(playerMap.get("idpatterncard")));
        }

        player.board = Board.get(player);

        return player;
    }

    public static Player mapToPlayer(final Map<String, String> playerMap, final Player player) {
        if (playerMap.get("playstatus") != null) {
            player.playStatus = playerMap.get("playstatus");
        }
        if (playerMap.get("score") != null) {
            player.score = Integer.parseInt(playerMap.get("score"));
        }
        if (playerMap.get("seqnr") != null) {
            player.seqnr = Integer.parseInt(playerMap.get("seqnr"));
        }
        if (playerMap.get("idpatterncard") != null && !player.hasPatternCard()) {
            player.patternCard = PatternCard.get(Integer.parseInt(playerMap.get("idpatterncard")));
        }

        player.board = Board.update(player.board);

        return player;
    }

    public List<Integer> getPatternCardOptions() {
        List<Map<String, String>> patternCardNumbers = PatternCardDB.getPatternCardOptions(getId());

        ArrayList<Integer> patternCardOptions = new ArrayList<Integer>();
        for (Map<String, String> patternCardMap : patternCardNumbers) {
            patternCardOptions.add(Integer.parseInt(patternCardMap.get("idpatterncard")));
        }
        return patternCardOptions;
    }
}
