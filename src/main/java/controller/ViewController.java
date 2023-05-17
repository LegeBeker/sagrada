package main.java.controller;

import java.util.ArrayList;
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
import main.java.model.Account;
import main.java.model.Die;
import main.java.model.Game;
import main.java.model.ObjectiveCard;
import main.java.model.PatternCard;
import main.java.model.Player;
import main.java.model.ToolCard;
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

    public Game getGame() {
        return this.gameController.getGame();
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

    public ArrayList<Account> getInviteableAccounts() {
        return this.accountController.getInviteableAccounts();
    }

    public Boolean doMove(final PatternCard patternCard, final int eyes, final Color dieColor, final int dieNumber,
            final int columnIndex, final int rowIndex) {
        return this.patternCardController.doMove(patternCard, eyes, dieColor, dieNumber, columnIndex, rowIndex);
    }

    public Boolean isTurnPlayer() {
        return this.gameController.isTurnPlayer(getUsername());
    }

    public ArrayList<Player> getPlayers() {
        return this.gameController.getPlayers(getUsername());
    }

    public Player getCurrentPlayer() {
        return this.gameController.getCurrentPlayer(getGame().getId());
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

    public Boolean getHelpFunction() {
        return this.gameController.getHelpFunction();
    }

    public ArrayList<ObjectiveCard> getObjectiveCards() {
        return this.gameController.getObjectiveCards();
    }

    public String getPrivateObjCardColor() {
        return getCurrentPlayer().getPrivateObjCardColor();
    }

    public ArrayList<Game> getGames() {
        return this.gameController.getGames();
    }

    public PatternCard getPatternCard() {
        return getCurrentPlayer().getPatternCard();
    }

    public ArrayList<Die> getOffer() {
        return this.gameController.getOffer();
    }

    public ArrayList<ToolCard> getToolCards() {
        return this.gameController.getToolCards();
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

    public Game createGame(final ArrayList<Account> accounts, final Boolean useDefaultCards) {
        return this.gameController.createGame(accounts, getUsername(), useDefaultCards);
    }

    public void choosePatternCard(final PatternCard patternCard) {
        this.gameController.choosePatternCard(patternCard);
    }

    public void openGameView(final Game game) {
        this.gameController.setGame(game);
        if (game.playerHasChoosenPatternCard(getUsername())) {
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
}
