package main.java.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameBoardView extends BorderPane {

    private static final int GRIDGAP = 20;

    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));
    private final GridPane boards = new GridPane();

    public GameBoardView(final ViewController view, final Game game) {
        this.setBackground(background);

        boards.add(new PatternCardView(view), 1, 1);
        boards.add(new PatternCardView(view), 1, 2);
        boards.add(new PatternCardView(view), 2, 1);
        boards.add(new PatternCardView(view), 2, 2);

        boards.setHgap(GRIDGAP);
        boards.setVgap(GRIDGAP);

        this.setTop(new Text("hier komt de huidige speler"));
        this.setCenter(boards);
    }
}