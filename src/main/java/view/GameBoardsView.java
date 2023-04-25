package main.java.view;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.Player;

public class GameBoardsView extends HBox {

    private static final int GRIDGAP = 20;
    private final int maxRows = 2;
    private final int maxCols = 2;

    private GridPane grid = new GridPane();

    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));

    public GameBoardsView(final ViewController view, final Game game) {
        this.setBackground(background);

        int cardCount = 0;

        for (Player player : game.getPlayers()) {
            if (player.getPatternCard() == null) {
                continue; // skip players without a pattern card
            }

            if (cardCount >= maxRows * maxCols) {
                break; // exit the loop once max grid size is reached
            }

            grid.add(new PatternCardView(view, player.getPatternCard(), player), cardCount % maxCols,
                    cardCount / maxCols);
            cardCount++;
        }

        grid.setHgap(GRIDGAP);
        grid.setVgap(GRIDGAP);

        grid.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(grid);

    }
}
