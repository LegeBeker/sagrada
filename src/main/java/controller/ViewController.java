package main.java.controller;

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
import main.java.model.Game;
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

    private AccountController accountController;
    private GameController gameController;
    private PatternCardController patternCardController;

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

        this.effectsController = new EffectsController();

        this.openLoginView();
    }

    public void changeView(final Pane pane) {
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

    public void openGameView(final Game game) {
        getGameController().setGame(game);
        if (game.playerHasChoosenPatternCard(getAccountController().getAccount().getUsername())) {
            GameView gameView = new GameView(this, game);
            changeView(gameView);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    Platform.runLater(() -> {
                        game.notifyObservers();
                    });
                }
            }, 0, REFRESHRATE);
        } else {
            openPatternCardSelectionView(game);
        }
    }

    public void openPatternCardSelectionView(final Game game) {
        PatternCardSelectionView patternCardSelectionView = new PatternCardSelectionView(this,
                getGameController().getCurrentPlayer(game.getId()));
        changeView(patternCardSelectionView);
    }

    public void openStatView(final Account account) {
        StatView statView = new StatView(this, account);
        changeView(statView);
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public PatternCardController getPatternCardController() {
        return patternCardController;
    }
}
