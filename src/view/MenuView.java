package view;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MenuView extends VBox {

	private Text textGreet;
	private Button buttonGames;
	private Button buttonStats;

	public MenuView(ViewController view) {
		this.setAlignment(Pos.CENTER);

		this.textGreet = new Text("Welkom " + view.getAccountController().getAccount().getUsername() + "!");

		this.buttonGames = new Button("Spellen");
		this.buttonGames.setOnAction(e -> view.OpenGamesView());

		this.buttonStats = new Button("Statistieken");
		this.buttonGames.setOnAction(e -> view.OpenStatsView());

		this.setPadding(new Insets(10));

		this.getChildren().addAll(this.textGreet, this.buttonGames, this.buttonStats);
	}
}
