package main.java.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.java.db.GameDB;
import main.java.db.PatternCardDB;
import main.java.pattern.Observable;

public class Game extends Observable {
    private int idGame;

    private int turnIdPlayer;
    private int currentRound;

    private String creationDate;

    private ArrayList<Player> players = new ArrayList<>();
    private final int uniqueCardsPerPlayer = 4;

    public static Game createGame(final ArrayList<Account> accounts, final boolean useDefaultCards) {
        Game newGame = new Game();

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = dtf.format(time);
        GameDB.createGame(formattedTime);
        List<Map<String, String>> response = GameDB.getGameByTimestamp(formattedTime);

        newGame.idGame = Integer.parseInt(response.get(0).get("idgame"));

        Player playerCreator = new Player();
        final int thisGameID = newGame.getId();
        List<Map<String, String>> colorList = GameDB.getColors(accounts.size());
        boolean isChallenger = true;

        for (Account ac : accounts) {
            String username = ac.getUsername();
            String privateColor = colorList.remove(0).get("color");

            Player newPlayer;
            if(isChallenger) {
                newPlayer = playerCreator.createPlayer(thisGameID, username, "challenger", privateColor);    
                isChallenger = false;
            } else {
                newPlayer = playerCreator.createPlayer(thisGameID, username, "challengee", privateColor);
            }

            newPlayer.addPlayerToDB();
            newGame.addPlayer(newPlayer);
        }

        if (useDefaultCards) {
            newGame.addPatternCards();
        } else {
            newGame.addPatternCards();
            // TODO create random (but valid) cards
            // ArrayList<PatternCard> randomCards = new PatternCard().generateRandomCards();
            // newGame.addPatternCards(randomCards);
        }


        return newGame;
    }

    private void addPatternCards() {
        ArrayList<PatternCard> defaultCards = PatternCard.getDefaultCards();
        addPatternCards(defaultCards);
    }
    
    private void addPatternCards(ArrayList<PatternCard> cards) {
        for(Player pl : players) {
            for(int i = 0; i < uniqueCardsPerPlayer; i++) {
                PatternCardDB.setPatternCardOptions(cards.remove(0).getIdPatternCard(), pl.getId());
            }
        }
    }

    public int getId() {
        return this.idGame;
    }

    public ArrayList<Die> getOffer() {
        return Die.getOffer(idGame);
    }

    public ArrayList<Die> getRoundTrack() {
        return Die.getRoundTrack(idGame);
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

    public Player getCurrentPlayer(final int id, final String username) {
        for (Player player : this.players) {
            if (player.getId() == id || player.getUsername().equals(username)) {
                return player;
            }
        }

        return null;
    }

    public ArrayList<String> getPlayerNames() {
        return (ArrayList<String>) players.stream()
                .map(Player::getUsername)
                .collect(Collectors.toList());
    }

    public boolean playerHasNotReplied(final String username) {
        for (Player player : this.players) {
            if (player.getUsername().equals(username)) {
                if (player.getPlayStatus().equals("challengee")) {
                    return true;
                }
            }
        }

        return false;
    }

    public void addPlayer(final Player player) {
        this.players.add(player);
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
        Die.putOffer(idGame, players.size());
        notifyObservers();
    }

    public ArrayList<ToolCard> getToolCards() {
        return ToolCard.getToolCards(idGame);
    }
}
