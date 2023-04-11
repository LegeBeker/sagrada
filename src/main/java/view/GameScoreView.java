package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameScoreView extends VBox {

    private final Background background = new Background(new BackgroundFill(Color.web("#666666"), null, null));
    private final int padding = 10;

    public GameScoreView(final ViewController view, final Game game) {
        this.setBackground(background);

        game.getPlayers().forEach(player -> {
            Text text = new Text(player.getUsername() + ": " + player.getScore());

            text.setFill(Color.WHITE);
            text.setStyle("-fx-font-size: 15px;");

            this.getChildren().add(text);
        });

        this.setPadding(new Insets(padding, padding, padding, padding));
    }
}
