package main.java.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.Player;

public class GameBoardView extends BorderPane {

    private static final int GRIDGAP = 20;
    private final int maxRows = 2;
    private final int maxCols = 2;

    private int cardCount = 0;

    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));
    private final GridPane boards = new GridPane();

    public GameBoardView(final ViewController view, final Game game) {
        this.setBackground(background);
    
        for (Player player : game.getPlayers()) {
            if (cardCount >= maxRows * maxCols) {
                break; // exit the loop once max grid size is reached
            }
            boards.add(new PatternCardView(view, player.getPatternCard()), cardCount % maxCols, cardCount / maxCols);
            cardCount++;
        }

        boards.setHgap(GRIDGAP);
        boards.setVgap(GRIDGAP);

        this.setTop(new Text("hier komt de huidige speler"));
        this.setCenter(boards);
    }
}