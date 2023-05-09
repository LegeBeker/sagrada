package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameScoreView extends VBox {
    private static final int PADDING = 10;

    public GameScoreView(final ViewController view, final Game game) {
        game.getPlayers(view.getAccountController().getAccount().getUsername()).forEach(player -> {
            StackPane playerScore = new StackPane();

            playerScore.setBackground(new Background(new BackgroundFill(player.getColor(), null, null)));
            playerScore.setPadding(new Insets(PADDING));
            playerScore.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

            Text text = new Text(player.getUsername() + ": " + player.getScore());
            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-stroke: black; -fx-stroke-width: 0.5px;");

            playerScore.getChildren().add(text);

            this.getChildren().add(playerScore);
        });

        this.setPadding(new Insets(PADDING));
        this.setSpacing(10);
    }
}
