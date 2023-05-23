package main.java.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import main.java.db.DieDB;
import main.java.db.GameDB;
import main.java.db.PatternCardDB;
import main.java.enums.PlayStatusEnum;
import main.java.pattern.Observable;

public class Game extends Observable {
    private int idGame;

    private Player turnPlayer;
    private int currentRound;

    private String creationDate;

    private ArrayList<Die> offer;
    private ArrayList<Die> roundTrack;

    private ArrayList<Player> players = new ArrayList<>();
    private static final int CARDSPERPLAYER = 4;

    private boolean helpFunction = false;

    public static Game createGame(final ArrayList<String> accounts, final String username,
            final boolean useDefaultCards) {
        Game newGame = new Game();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dtf.format(time);
        List<Map<String, String>> response = GameDB.createGame(formattedTime);

        newGame.idGame = Integer.parseInt(response.get(0).get("idgame"));
        final int thisGameID = newGame.getId();

        List<Map<String, String>> colorList = GameDB.getColors(accounts.size() + 1);

        newGame.addPlayer(Player.createPlayer(
                newGame, username, PlayStatusEnum.CHALLENGER.toString(),
                colorList.remove(0).get("color")));
        newGame.setTurnPlayer(newGame.getPlayers().get(0));

        for (String account : accounts) {
            newGame.addPlayer(Player.createPlayer(
                    newGame, account, PlayStatusEnum.CHALLENGEE.toString(),
                    colorList.remove(0).get("color")));
        }

        for (int playerNr = 0; playerNr < newGame.getPlayers().size(); playerNr++) {
            newGame.getPlayers().get(playerNr).setSeqnr(playerNr + 1);
        }

        if (useDefaultCards) {
            newGame.addPatternCards();
        } else {
            newGame.addPatternCards();
            // TODO create random (but valid) cards
            // ArrayList<PatternCard> randomCards = new PatternCard().generateRandomCards();
            // newGame.addPatternCards(randomCards);
        }

        GameDB.assignToolcards(thisGameID);
        GameDB.assignPublicObjectivecards(thisGameID);

        Die.createGameOffer(thisGameID);
        Board.createBoards(newGame);

        return newGame;
    }

    private void addPatternCards() {
        ArrayList<PatternCard> defaultCards = PatternCard.getDefaultCards();
        addPatternCards(defaultCards);
    }

    private void addPatternCards(final ArrayList<PatternCard> cards) {
        for (Player pl : players) {
            for (int i = 0; i < CARDSPERPLAYER; i++) {
                PatternCardDB.setPatternCardOptions(cards.remove(0).getIdPatternCard(), pl.getId());
            }
        }
    }

    public int getId() {
        return this.idGame;
    }

    public ArrayList<Die> getOffer() {
        return this.offer;
    }

    public ArrayList<Die> getRoundTrack() {
        return this.roundTrack;
    }

    public Player getTurnPlayer() {
        return this.turnPlayer;
    }

    public String getTurnPlayerUsername() {
        return this.turnPlayer.getUsername();
    }

    public int getTurnPlayerId() {
        return this.turnPlayer.getId();
    }

    public void setTurnPlayer(final Player player) {
        this.turnPlayer = player;
        GameDB.setTurnPlayer(getId(), player.getId());
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

    public ArrayList<Integer> getPlayerIds() {
        ArrayList<Integer> playerIds = new ArrayList<>();

        for (Player player : this.players) {
            playerIds.add(player.getId());
        }

        return playerIds;
    }

    public boolean hasOpenInvites() {
        return this.players.stream()
                .anyMatch(player -> player.getPlayStatus().equals(PlayStatusEnum.CHALLENGEE.toString()));
    }

    public ArrayList<Player> getPlayers(final String currPlayerUsername) {
        ArrayList<Player> players = this.players;
        ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW));

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getUsername().equals(currPlayerUsername)) {
                Player currentPlayer = players.remove(i);
                players.add(0, currentPlayer);
            }
        }

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setColor(colors.get(i));
        }

        return players;
    }

    public void setHelpFunction() {
        this.helpFunction = !this.helpFunction;
    }

    public boolean getHelpFunction() {
        return this.helpFunction;
    }

    public Player getCurrentPlayer(final int id, final String username) {
        for (Player player : this.players) {
            if (player.getId() == id || player.getUsername().equals(username)) {
                return player;
            }
        }

        return null;
    }

    public boolean isPlayerInGame(final String username){
        return this.players.stream()
                .anyMatch(player -> player.getUsername().equals(username));
    }

    public ArrayList<String> getPlayerNames() {
        return (ArrayList<String>) players.stream()
                .map(Player::getUsername)
                .collect(Collectors.toList());
    }

    public boolean playerHasChosenPatternCard(final String username) {
        Player player = getCurrentPlayer(idGame, username);

        return player.hasPatternCard();
    }

    public void addPlayer(final Player player) {
        this.players.add(player);
    }

    public Player getPlayer(final String username) {
        return this.players.stream()
                .filter(player -> player.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Player getPlayer(final int playerId) {
        return this.players.stream()
                .filter(player -> player.getId() == playerId)
                .findFirst()
                .orElse(null);
    }

    public Color getPlayerColor(final int idPlayer, final String username) {
        return this.getPlayers(username).stream()
                .filter(player -> player.getId() == idPlayer)
                .findFirst()
                .orElse(null)
                .getColor();
    }

    public void endTurn() {
        int nextSeqnr = getTurnPlayer().getSeqnr() + 1;
        if (nextSeqnr > getPlayers().size()) {
            reverseSeqNr();
        } else if (nextSeqnr == 0) {
            endRound();
        } else {
            for (Player player : getPlayers()) {
                if (player.getSeqnr() == nextSeqnr) {
                    setTurnPlayer(player);
                    break;
                }
            }
        }
        notifyObservers(Game.class);
    }

    private void reverseSeqNr() {
        for (Player player : getPlayers()) {
            player.setSeqnr(player.getSeqnr() * -1);
        }
    }

    private void endRound() {
        reverseSeqNr();
        for (Player player : getPlayers()) {
            if (player.getSeqnr() == getPlayers().size()) {
                player.setSeqnr(1);
                setTurnPlayer(player);
            } else {
                player.setSeqnr(player.getSeqnr() + 1);
            }
        }

        for (Map<String, String> dieMap : DieDB.getOffer(getId(), getCurrentRound())) {
            DieDB.putRoundTrack(getId(), getCurrentRound(), Integer.parseInt(dieMap.get("dienumber")),
                    dieMap.get("diecolor"));
        }

        setCurrentRound(getCurrentRound() + 1);
    }

    private void setCurrentRound(final int roundID) {
        GameDB.setRound(getId(), roundID);
        notifyObservers(Game.class);
    }

    public static Game get(final int idGame) {
        Map<String, String> gameMap = GameDB.get(idGame);

        Game game = new Game();

        game.idGame = Integer.parseInt(gameMap.get("idgame"));

        if (gameMap.get("current_roundID") != null) {
            game.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
            game.offer = Die.getOffer(game.idGame, game.currentRound);
        }
        game.creationDate = gameMap.get("creationdate");
        game.helpFunction = false;

        for (Map<String, String> map : GameDB.getPlayers(game.idGame)) {
            game.players.add(Player.mapToPlayer(game, map));
        }

        if (gameMap.get("turn_idplayer") != null) {
            game.turnPlayer = game.getPlayer(Integer.parseInt(gameMap.get("turn_idplayer")));
        }

        game.roundTrack = Die.getRoundTrack(game.idGame);

        return game;
    }

    public void update() {
        Map<String, String> gameMap = GameDB.get(idGame);

        if (gameMap.get("current_roundID") != null) {
            this.offer = Die.getOffer(this.idGame, this.currentRound);
            if (Integer.parseInt(gameMap.get("current_roundID")) != currentRound) {
                this.currentRound = Integer.parseInt(gameMap.get("current_roundID"));
                this.roundTrack = Die.getRoundTrack(this.idGame);
            }
        }

        for (Map<String, String> map : GameDB.getPlayers(this.idGame)) {
            this.players.add(Player.mapToPlayer(map, this.getPlayer(map.get("username"))));
        }

        if (gameMap.get("turn_idplayer") != null
                && Integer.parseInt(gameMap.get("turn_idplayer")) != turnPlayer.getId()) {
            this.turnPlayer = this.getPlayer(Integer.parseInt(gameMap.get("turn_idplayer")));
        }
    }

    public static List<Map<String, String>> getGamesList(final String username) {
        return GameDB.getGamesList(username);
    }

    public StringProperty turnPlayerUsernameProperty() {
        return new SimpleStringProperty(getTurnPlayer().getUsername());
    }

    public StringProperty creationDateShowProperty() {
        final int year = 5;
        return new SimpleStringProperty(getCreationDate().substring(year));
    }

    public void getNewOffer() {
        Die.getNewOffer(idGame, currentRound, players.size());
        notifyObservers(Game.class);
    }

    public ArrayList<String> getToolCardsNames() {
        ArrayList<ToolCard> toolCards = ToolCard.getToolCards(idGame);
        ArrayList<String> toolCardNames = new ArrayList<>();

        for (ToolCard toolCard : toolCards) {
            toolCardNames.add(toolCard.getName());
        }

        return toolCardNames;
    }

    public ArrayList<Integer> getObjectiveCardsIds() {
        ArrayList<ObjectiveCard> objectiveCards = ObjectiveCard.getObjectiveCards(idGame);
        ArrayList<Integer> objectiveCardIds = new ArrayList<>();

        for (ObjectiveCard objectiveCard : objectiveCards) {
            objectiveCardIds.add(objectiveCard.getIdObjectiveCard());
        }

        return objectiveCardIds;
    }

    public static Map<Integer, Boolean> getGamesWithOpenInvites(final String username) {
        return GameDB.getGamesWithOpenInvites(username);
    }
}
