package controller;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.LoginView;
import view.RegisterView;
import view.MenuView;
import view.GamesView;
import view.StatsView;

public class ViewController extends Scene {

	private AccountController accountController;

	public ViewController() {
		super(new Pane());

		this.accountController = new AccountController();

		this.OpenLoginView();
	}

	public void OpenLoginView() {
		LoginView loginView = new LoginView(this);
		setRoot(loginView);
	}

	public void OpenRegisterView() {
		RegisterView registerView = new RegisterView(this);
		setRoot(registerView);
	}

	public void OpenMenuView() {
		MenuView menuView = new MenuView(this);
		setRoot(menuView);
	}

	public void OpenGamesView() {
		GamesView gamesView = new GamesView(this);
		setRoot(gamesView);
	}

	public void OpenStatsView() {
		StatsView statsView = new StatsView(this);
		setRoot(statsView);
	}

	public AccountController getAccountController() {
		return accountController;
	}
}
