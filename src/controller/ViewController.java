package controller;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view.LoginView;
import view.RegisterView;
import view.MenuView;
import view.NewGameView;
import view.GamesView;
import view.StatsView;

public class ViewController extends Scene {

    private AccountController accountController;
    private GameController gameController;

    private final Font font = new Font("Arial", 20);
    private final Background background = new Background(new BackgroundFill(Color.web("#4483c2"), null, null));
    private final ImageView logo = new ImageView(new Image("file:resources/img/logo.png"));

    private final int logoWidth = 300;

    public ViewController() {
        super(new Pane());

        this.accountController = new AccountController();
        this.gameController = new GameController();

        this.openLoginView();
    }

    public Font getFont() {
        return this.font;
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
        setRoot(loginView);
    }

    public void openRegisterView() {
        RegisterView registerView = new RegisterView(this);
        setRoot(registerView);
    }

    public void openMenuView() {
        MenuView menuView = new MenuView(this);
        setRoot(menuView);
    }

    public void openGamesView() {
        GamesView gamesView = new GamesView(this);
        setRoot(gamesView);
    }

    public void openStatsView() {
        StatsView statsView = new StatsView(this);
        setRoot(statsView);
    }

    public void openNewGameView() {
        NewGameView newGameView = new NewGameView(this);
        setRoot(newGameView);
    }

    public AccountController getAccountController() {
        return accountController;
    }

    public GameController getGameController() {
        return gameController;
    }
}
