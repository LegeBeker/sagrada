package main.java.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameScoreView extends HBox {

    private final Background background = new Background(new BackgroundFill(Color.web("#666666"), null, null));

    private final Text text = new Text("Huidige score per speler");

    public GameScoreView(final ViewController view, final Game game) {
        this.setBackground(background);

        this.getChildren().add(text);
    }
}
