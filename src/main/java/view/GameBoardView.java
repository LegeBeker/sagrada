package main.java.view;


import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameBoardView extends GridPane {


    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));
    private final Text text = new Text("Board");

    public GameBoardView(final ViewController view, final Game game) {
        this.setBackground(background);

        this.getChildren().add(text);
    }
}
