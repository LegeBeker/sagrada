package main.java.controller;

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
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.util.Duration;
import main.java.model.Game;
import main.java.view.GameView;
import main.java.view.GamesView;
import main.java.view.LoginView;
import main.java.view.MenuView;
import main.java.view.NewGameView;
import main.java.view.RegisterView;
import main.java.view.StatsView;

public class ViewController extends Scene {

    private StackPane rootPane;

    private Label errorBox;

    private AccountController accountController;
    private GameController gameController;
    private PatternCardController patternCardController;
    private MessageController messageController;

    private final Background background;
    private final ImageView logo = new ImageView(new Image("file:resources/img/logo.png"));

    private final double errorTimeout = 2.5;
    private final double errorAnimation = 0.5;

    private final int logoWidth = 300;

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

        this.errorBox = new Label();
        this.errorBox.getStyleClass().add("error-box");
        this.errorBox.setVisible(false);

        StackPane.setMargin(this.errorBox, new Insets(0, 0, 0, 0));
        StackPane.setAlignment(this.errorBox, Pos.TOP_LEFT);
        this.errorBox.setMaxWidth(Double.MAX_VALUE);

        this.accountController = new AccountController();
        this.gameController = new GameController();
        this.patternCardController = new PatternCardController();
        this.messageController = new MessageController();

        this.openLoginView();
    }

    public void changeView(final Pane pane) {
        this.rootPane.getChildren().clear();
        this.rootPane.getChildren().addAll(pane, this.errorBox);
    }

    public void displayError(final String message) {
        this.errorBox.setText(message);
        this.errorBox.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(this.errorTimeout));
        pause.setOnFinished(e -> {
            TranslateTransition transition = new TranslateTransition(Duration.seconds(errorAnimation), this.errorBox);
            transition.setFromY(0);
            transition.setToY(-this.errorBox.getHeight());

            PauseTransition fullAnimation = new PauseTransition(Duration.seconds(errorAnimation));
            fullAnimation.setOnFinished(ee -> this.errorBox.setVisible(false));

            transition.play();
        });

        TranslateTransition transition = new TranslateTransition(Duration.seconds(errorAnimation), this.errorBox);
        transition.setFromY(-this.errorBox.getHeight());
        transition.setToY(0);

        pause.play();
        transition.play();
    }

    public Background getBackground() {
        return this.background;
    }

    public ImageView getLogo() {
        this.logo.setFitWidth(logoWidth);
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
        GameView gameView = new GameView(this, game);
        changeView(gameView);
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

    public MessageController getMessageController() {
        return messageController;
    }
}
