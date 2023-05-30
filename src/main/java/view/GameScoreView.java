package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class GameScoreView extends VBox {
    private static final int PADDING = 10;

    public GameScoreView(final ViewController view) {
        view.getPlayers().forEach(player -> {
            StackPane playerScore = new StackPane();

            playerScore
                    .setBackground(new Background(new BackgroundFill(Color.valueOf(player.get("color")), null, null)));
            playerScore.setPadding(new Insets(PADDING));
            playerScore.setStyle("-fx-border-color: black; -fx-border-width: 2px;");

            Text text = new Text(player.get("username") + ": " + player.get("score"));
            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-stroke: black; -fx-stroke-width: 0.5px;");

            playerScore.getChildren().add(text);

            this.getChildren().add(playerScore);
        });

        this.setPadding(new Insets(PADDING));
        this.setSpacing(10);
    }
}
