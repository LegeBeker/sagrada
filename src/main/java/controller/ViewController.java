package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import main.java.model.Die;
import main.java.model.Game;
import main.java.model.Player;
import main.java.pattern.Observable;
import main.java.view.GameView;
import main.java.view.GamesView;
import main.java.view.LoginView;
import main.java.view.MenuView;
import main.java.view.NewGameView;
import main.java.view.PatternCardSelectionView;
import main.java.view.RegisterView;
import main.java.view.StatView;
import main.java.view.StatsView;

public class ViewController extends Scene {

    private StackPane rootPane;

    private Label messageBox;

    private Timer timer;

    private AccountController accountController;
    private GameController gameController;
    private PatternCardController patternCardController;
    private MessageController messageController;

    private EffectsController effectsController;

    private final Background background;
    private final ImageView logo = new ImageView(new Image("file:resources/img/logo.png"));

    private static final int LOGOWIDTH = 300;

    private static final int REFRESHRATE = 3000;

    public ViewController() {
        super(new Pane());

        this.rootPane = new StackPane();
        this.setRoot(this.rootPane);

        Color startColor = Color.web("#5897d6");
        Color endColor = Color.web("#0d4e8f");

        Stop[] stops = new Stop[] {new Stop(0, startColor), new Stop(1, endColor)};
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        this.background = new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY));

        this.getStylesheets().add("file:resources/css/style.css");

        this.messageBox = new Label();
        this.messageBox.setVisible(false);

        StackPane.setMargin(this.messageBox, new Insets(0, 0, 0, 0));
        StackPane.setAlignment(this.messageBox, Pos.TOP_LEFT);
        this.messageBox.setMaxWidth(Double.MAX_VALUE);

        this.accountController = new AccountController();
        this.gameController = new GameController(this);
        this.patternCardController = new PatternCardController(this);
        this.messageController = new MessageController(this);
        this.effectsController = new EffectsController();

        this.openLoginView();
    }

    public String getUsername() {
        return this.accountController.getUsername();
    }

    public int getPlayerId() {
        Player player = this.gameController.getCurrentPlayer();
        return player.getId();
    }

    public void changeView(final Pane pane) {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().addAll(pane, this.messageBox);
    }

    public void displayError(final String message) {
        this.effectsController.displayMessageBox(this.messageBox, message, true);
    }

    public void displayMessage(final String message) {
        this.effectsController.displayMessageBox(this.messageBox, message, false);
    }

    public EffectsController effects() {
        return this.effectsController;
    }

    public Background getBackground() {
        return this.background;
    }

    public ImageView getLogo() {
        this.logo.setFitWidth(LOGOWIDTH);
        this.logo.setPreserveRatio(true);
        return this.logo;
    }

    public void openLoginView() {
        LoginView loginView = new LoginView(this);
        changeView(loginView);
    }

    public void openRegisterView() {
        RegisterView registerView = new RegisterView(this);
        changeView(registerView);
    }

    public void openMenuView() {
        MenuView menuView = new MenuView(this);
        changeView(menuView);
    }

    public void openGamesView() {
        GamesView gamesView = new GamesView(this);
        changeView(gamesView);
    }

    public void openStatsView() {
        StatsView statsView = new StatsView(this);
        changeView(statsView);
    }

    public void openNewGameView() {
        NewGameView newGameView = new NewGameView(this);
        changeView(newGameView);
    }

    public ArrayList<String> getInviteableAccountsUsernames() {
        return this.accountController.getInviteableAccountsUsernames();
    }

    public Boolean doMove(final int patternCardId, final int eyes, final Color dieColor, final int dieNumber,
            final int columnIndex, final int rowIndex) {
        return this.patternCardController.doMove(patternCardId, eyes, dieColor, dieNumber, columnIndex, rowIndex);
    }

    public Boolean isTurnPlayer() {
        return this.gameController.isTurnPlayer(getUsername());
    }

    public Boolean isTurnPlayer(final int gameId) {
        return this.gameController.isTurnPlayer(gameId, getUsername());
    }

    public ArrayList<Player> getPlayers() {
        return this.gameController.getPlayers(getUsername());
    }

    public ArrayList<Integer> getPlayerIds() {
        return this.gameController.getPlayerIds();
    }

    public Player getCurrentPlayer() {
        return this.gameController.getCurrentPlayer();
    }

    public ArrayList<int[]> getPossibleMoves(final int eyes, final Color color) {
        return this.patternCardController.getPossibleMoves(eyes, color);
    }

    public void getNewOffer() {
        this.gameController.getNewOffer();
    }

    public void setHelpFunction() {
        this.gameController.setHelpFunction();
    }

    public boolean sendMessage(final String message) {
        return this.messageController.sendMessage(message);
    }

    public String getPatternCardName(final int id) {
        return this.patternCardController.getPatternCard(id).getName();
    }

    public int getPatternCardDifficulty(final int id) {
        return this.patternCardController.getPatternCard(id).getDifficulty();
    }

    public Integer getPlayerPatternCardId(final int playerId) {
        return this.gameController.getPlayer(playerId).getPatternCardId();
    }

    public String getPlayerUsername(final int id) {
        return this.gameController.getPlayer(id).getUsername();
    }

    public int getPlayerGameTokens(final int id) {
        return this.gameController.getPlayer(id).getFavorTokensLeft();
    }

    public Color getPlayerColor(final int id) {
        return this.gameController.getPlayerColor(id, getUsername());
    }

    public Boolean getHelpFunction() {
        return this.gameController.getHelpFunction();
    }

    public ArrayList<Integer> getObjectiveCardsIds() {
        return this.gameController.getObjectiveCardsIds();
    }

    public String getObjectiveCardName(final int id) {
        return this.gameController.getObjectiveCard(id).getName();
    }

    public String getPrivateObjCardColor() {
        return getCurrentPlayer().getPrivateObjCardColor();
    }

    public ArrayList<Map<String, String>> getGames() {
        return this.gameController.getGames();
    }

    public int getPatternCardId() {
        return getCurrentPlayer().getPatternCard().getIdPatternCard();
    }

    public ArrayList<Map<String, String>> getOffer() {
        return this.gameController.getOffer();
    }

    public ArrayList<Map<String, String>> getRoundTrack() {
        return this.gameController.getRoundTrack();
    }

    public Map<String, String> getPlayerBoardField(final Integer playerId, final int row, final int col) {
        Player player = this.gameController.getPlayer(playerId);
        Die die = player.getBoard().getField(row, col);

        if (die != null) {
            Map<String, String> dieMap = new HashMap<>();
            dieMap.put("color", die.getColor().toString());
            dieMap.put("eyes", Integer.toString(die.getEyes()));
            dieMap.put("number", Integer.toString(die.getNumber()));
            return dieMap;
        }
        return null;
    }

    public ArrayList<String> getToolCardsNames() {
        return this.gameController.getToolCardsNames();
    }

    public Boolean loginAccount(final String username, final String password) {
        return this.accountController.loginAccount(username, password);
    }

    public void logoutAccount() {
        this.accountController.logoutAccount();
    }

    public void endTurn() {
        this.gameController.endTurn();
    }

    public void createGame(final ArrayList<String> accounts, final Boolean useDefaultCards) {
        openPatternCardSelectionView(this.gameController.createGame(accounts, getUsername(), useDefaultCards));
    }

    public void choosePatternCard(final int idPatternCard) {
        this.gameController.choosePatternCard(idPatternCard);
        openGamesView();
    }

    public void openGameView(final int gameId) {
        Game game = this.gameController.getGame(gameId);
        this.gameController.setGame(game);
        if (game.playerHasChosenPatternCard(getUsername())) {
            GameView gameView = new GameView(this);
            changeView(gameView);

            this.timer = new Timer();
            this.timer.schedule(new TimerTask() {
                public void run() {
                    Platform.runLater(() -> {
                        Observable.notifyObservers(Game.class);
                    });
                }
            }, 0, REFRESHRATE);
        } else {
            openPatternCardSelectionView(game);
        }
    }

    public void openPatternCardSelectionView(final Game game) {
        this.gameController.setGame(game);
        PatternCardSelectionView patternCardSelectionView = new PatternCardSelectionView(this);
        changeView(patternCardSelectionView);
    }

    public void openStatView(final String username) {
        StatView statView = new StatView(this, username);
        changeView(statView);
    }

    public Map<String, String> getStats(final String username) {
        return this.accountController.getStats(username);
    }

    public Map<Integer, List<Integer>> getPatternCardOptions() {
        return this.gameController.getPatternCardOptions();
    }

    public Boolean createAccount(final String username, final String password) {
        return this.accountController.createAccount(username, password);
    }

    public boolean isCurrentPlayer(final Integer playerId) {
        return getCurrentPlayer().getId() == playerId;
    }

    public Integer getPatternCardFieldValue(final int patternCardId, final int col, final int row) {
        return this.patternCardController.getPatternCardField(patternCardId, row, col).getValue();
    }

    public Color getPatternCardFieldColor(final int patternCardId, final int col, final int row) {
        return this.patternCardController.getPatternCardField(patternCardId, row, col).getColor();
    }

    public ArrayList<Map<String, String>> getMessages() {
        return this.messageController.getMessages(gameController.getGame().getId());
    }

    public void acceptInvite(final int gameId) {
        this.gameController.acceptInvite(gameId);
    }

    public void refuseInvite(final int gameId) {
        this.gameController.refuseInvite(gameId);
    }

    public boolean hasOpenInvite(final int gameId, final String playerName) {
        Game game = Game.get(gameId);
        return game.getPlayerNames().contains(playerName) && game.playerHasNotReplied(playerName);
    }

    public boolean gameHasOpenInvites(final int gameId) {
        Game game = Game.get(gameId);
        return game.hasOpenInvites();
    }

    public ArrayList<String> getAccountsUsernames() {
        return this.accountController.getAccountsUsernames();
    }

    public String getAccountWonGames(final String username) {
        return this.accountController.getAccountWonGames(username);
    }

    public boolean playerHasChosenPatternCard(final int gameId, final String username) {
        return this.gameController.playerHasChosenPatternCard(gameId, username);
    }
}
