package view;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuView extends VBox {
	
	private Button buttonGames;
	private Button buttonStats;
	
	public MenuView(ViewController view) {
		this.setAlignment(Pos.CENTER);
		
		this.buttonGames = new Button("Spellen");
		this.buttonGames.setOnAction(e -> view.OpenGamesView());
		
		this.buttonStats = new Button("Statistieken");
		this.buttonGames.setOnAction(e -> view.OpenStatsView());
		
		this.setPadding(new Insets(10));
		
		this.getChildren().addAll(this.buttonGames, this.buttonStats);
	}
}
