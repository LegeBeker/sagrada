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
    private int idGame;

    private String playStatus;
    private int seqnr;

    private Color color;

    private String privateObjCardColor;
    private Integer idPatternCard;
    private int unassignedFavortokensLeft;

    private Board board;

    private int score;

    public static Player createPlayer(final int gameID, final String username, final String playerStatus,
            final String privateColor) {
        Player newPlayer = new Player();
        newPlayer.setIdGame(gameID);
        newPlayer.setUsername(username);
        newPlayer.setPlayStatus(playerStatus);
        newPlayer.setPrivateObjCardColor(privateColor);

        Map<String, String> idplayer = PlayerDB.createPlayer(username, gameID, playerStatus, privateColor).get(0);
        newPlayer.setId(Integer.parseInt(idplayer.get("idplayer")));

        return newPlayer;
    }

    public void createGameFavorTokens() {
        int patternCardDifficulty = Integer.parseInt(PatternCardDB.get(this.idPatternCard).get("difficulty"));
        int highestId = GameFavorTokenDB.getHighestIdFromGame(this.idGame);
        for (int tokenNumber = 1; patternCardDifficulty >= tokenNumber; tokenNumber++) {
            GameFavorTokenDB.createGameFavorToken(tokenNumber + highestId, this.idGame, getId());
        }

        unassignedFavortokensLeft = patternCardDifficulty;
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

    public void setColor(final Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
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

    public Board getBoard() {
        if (this.board == null) {
            this.board = Board.get(this);
        }
        return Board.update(this.board);
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
        PlayerDB.setSeqnr(getId(), seqnr);
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

    public static ArrayList<Player> getInvites(final String username) {
        ArrayList<Player> players = new ArrayList<Player>();

        for (Map<String, String> playerMap : PlayerDB.getAll(username)) {
            Player player = mapToPlayer(playerMap);
            players.add(player);
        }

        return players;
    }

    public boolean acceptInvite() {
        return PlayerDB.acceptInvite(this.idGame, this.username);
    }

    public boolean refuseInvite() {
        return PlayerDB.refuseInvite(this.idGame, this.username);
    }

    public boolean hasPatternCard() {
        return this.idPatternCard != null;
    }

    public boolean choosePatternCard(final PatternCard patternCard, final int idgame) {
        return PlayerDB.updatePatternCard(patternCard.getIdPatternCard(), idgame, this.username);
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
        player.score = Integer.parseInt(playerMap.get("score"));
        if (playerMap.get("seqnr") != null) {
            player.seqnr = Integer.parseInt(playerMap.get("seqnr"));
        }
        player.privateObjCardColor = playerMap.get("private_objectivecard_color");
        if (playerMap.get("idpatterncard") != null) {
            player.idPatternCard = Integer.parseInt(playerMap.get("idpatterncard"));
        }
        return player;
    }

    public List<PatternCard> getPatternCardOptions() {
        List<Map<String, String>> patternCardNumbers = PatternCardDB.getPatternCardOptions(getId());

        ArrayList<PatternCard> patternCardOptions = new ArrayList<PatternCard>();
        for (Map<String, String> patternCardMap : patternCardNumbers) {
            PatternCard patternCard = PatternCard.get(Integer.parseInt(patternCardMap.get("idpatterncard")));
            patternCardOptions.add(patternCard);
        }
        return patternCardOptions;
    }

    public Player update(final Player player) {
        Map<String, String> values = PlayerDB.get(player.getId());

        player.setIdPatternCard(Integer.parseInt(values.get("idpatterncard")));
        player.setScore(Integer.parseInt(values.get("score")));
        player.setSeqnr(Integer.parseInt(values.get("seqnr")));

        return player;
    }

    public String getPrivateObjectiveColor() {
        return PlayerDB.getPrivateObjectiveColor(getId());
    }
}
