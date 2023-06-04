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
import main.java.view.DieDropTarget;
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
    private ScoreController scoreController;
    private ToolcardController toolCardController;

    private EffectsController effectsController;

    private final Background background;
    private final ImageView logo = new ImageView(new Image(getClass().getResource("/img/logo.png").toExternalForm()));

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

        this.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

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
        this.scoreController = new ScoreController();
        this.toolCardController = new ToolcardController(this);

        this.openLoginView();
    }

    public String getUsername() {
        return this.accountController.getUsername();
    }

    public int getPlayerId() {
        Player player = this.gameController.getCurrentPlayer();
        return player.getId();
    }

    public int getCurrentRound() {
        return this.gameController.getRound();
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

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    gamesView.update();
                });
            }
        }, 0, REFRESHRATE);
    }

    public void openStatsView() {
        StatsView statsView = new StatsView(this);
        changeView(statsView);
    }

    public void openNewGameView() {
        NewGameView newGameView = new NewGameView(this);
        changeView(newGameView);

        this.timer = new Timer();
        this.timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    newGameView.updateInvites();
                });
            }
        }, 0, REFRESHRATE);
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

    public String getTurnPlayerUsername() {
        return this.gameController.getTurnPlayerUsername();
    }

    public Boolean isCardOwnerTurnPlayer(final int playerId) {
        return this.gameController.isTurnPlayer(playerId);
    }

    public List<Map<String, String>> getFavorTokensForToolCard(final String toolCardName) {
        return this.toolCardController.getFavorTokensForToolCard(
                Integer.parseInt(
                        ToolcardController.getToolCard(gameController.getGameId(), toolCardName).get("idtoolcard")),
                gameController.getGameId());
    }

    public List<Map<String, String>> getPlayers() {
        return this.gameController.getPlayers(getUsername());
    }

    public ArrayList<Integer> getPlayerIds() {
        return this.gameController.getPlayerIds();
    }

    public Player getCurrentPlayer() {
        return this.gameController.getCurrentPlayer();
    }

    public List<Map<String, String>> getScores() {
        return this.scoreController.getScores(getCurrentPlayer());
    }

    public ArrayList<int[]> getPossibleMoves(final int eyes, final Color color) {
        return this.patternCardController.getPossibleMoves(eyes, color);
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

    public List<Map<String, String>> getGamesList() {
        return this.gameController.getGamesList();
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
        Boolean gameFinished = this.gameController.endTurn();
        DieDropTarget.resetAmountPlacedDie();
        if (gameFinished) {
            this.scoreController.updateScores(getCurrentPlayer());
        }
    }

    public void createGame(final ArrayList<String> accounts, final Boolean useDefaultCards) {
        openPatternCardSelectionView(this.gameController.createGame(accounts, getUsername(), useDefaultCards));
    }

    public void choosePatternCard(final int idPatternCard, final boolean defaultCards) {
        this.gameController.choosePatternCard(this.patternCardController.getPatternCard(idPatternCard), defaultCards);

        if (this.gameController.gameHasOpenInvites()) {
            openGamesView();
        } else {
            openGameView(this.gameController.getGameId());
        }
    }

    public void openGameView(final int gameId) {
        Game game = this.gameController.getGame(gameId);
        this.gameController.setGame(game);
        Observable.removeAllObservers(Game.class);
        Observable.addObserver(Game.class, this.gameController);
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

    public Map<Integer, List<Integer>> generatePatternCardOptions() {
        return this.patternCardController.generatePatternCardOptions();
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

    public ArrayList<String> getAccountsUsernames() {
        return this.accountController.getAccountsUsernames();
    }

    public String getAccountWonGames(final String username) {
        return this.accountController.getAccountWonGames(username);
    }

    public boolean playerHasChosenPatternCard(final int gameId, final String username) {
        return this.gameController.playerHasChosenPatternCard(gameId, username);
    }

    public Map<Integer, Boolean> getGamesWithOpenInvites() {
        return this.gameController.getGamesWithOpenInvites();
    }

    public Boolean getGameClockwise() {
        return gameController.getGame().getClockwise();
    }

    public boolean buyToolCard(final String toolcardName) {
        int amountFavorTokens = gameController.getCurrentPlayer().getFavorTokensLeft();
        int amountTokensAssigned = toolCardController.getToolCardPrice(toolcardName, gameController.getGameId());

        int toolCardPrice = 1;
        if (amountTokensAssigned > 0) {
            toolCardPrice = 2;
        }

        if (amountFavorTokens >= toolCardPrice) {
            toolCardController.buyToolCard(toolcardName, gameController.getGameId(),
                    gameController.getCurrentPlayer().getId(), toolCardPrice, gameController.getGame().getRoundID());
            return true;
        }
        return false;
    }

    public String getSelectedToolcardName() {
        return gameController.getSelectedToolcardName();
    }

    public void setToolCardSelection(final String selectedToolcardName) {
        gameController.setSelectedToolcardName(selectedToolcardName);
    }

    public void grozingPliers(final int dieNumber, final String dieColor, final String actionChoice) {
        ToolcardController.grozingPliers(gameController.getGameId(), dieNumber, dieColor, actionChoice);
    }

    public void grindingStone(final int dieNumber, final String dieColor) {
        ToolcardController.grindingStone(gameController.getGameId(), dieNumber, dieColor);
    }

    public void fluxBrush(final int dieNumber, final String dieColor) {
        ToolcardController.fluxBrush(gameController.getGameId(), dieNumber, dieColor);
    }

    public Boolean glazingHammer() {
        int turnCount = 1;
        if (!gameController.getGame().getClockwise()) {
            turnCount = 2;
        }
        return ToolcardController.glazingHammer(turnCount, gameController.getGameId(),
                gameController.getGame().getRoundID());
    }

    public void fluxRemover(final int dieNumber, final String dieColor) {
        ToolcardController.fluxRemover(gameController.getGameId(), dieNumber, dieColor,
                gameController.getGame().getRoundID());
    }

    public void eglomiseBrush() {
        this.toolCardController.eglomiseBrush();
    }

    public void copperFoilBurnisher() {
        this.toolCardController.copperFoilBurnisher();
    }

    public void getNewOffer() {
        this.gameController.getGame().getNewOffer();
    }

    public int getAmountPlacedDieInRound() {
        return gameController.getAmountPlacedDieInRound();
    }

    public void lensCutter(final int dieNumberOffer, final String dieColorOffer, final int dieNumberRoundTrack,
            final String dieColorRoundTrack) {
        this.toolCardController.lensCutter(gameController.getGameId(), gameController.getGame().getRoundID(),
                dieNumberOffer, dieColorOffer, dieNumberRoundTrack, dieColorRoundTrack);
    }
}
