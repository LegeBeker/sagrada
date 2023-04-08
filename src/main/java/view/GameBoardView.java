package main.java.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.PatternCard;

public class GameBoardView extends BorderPane {

    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));
    private final VBox vbox = new VBox();
    private final HBox hbox = new HBox();
    private final GridPane grid = new GridPane();

    public GameBoardView(final ViewController view, final Game game) {
        this.setBackground(background);

        PatternCard test = view.getPatternCardController().getPatternCard(1);

        System.out.println(test.patternCardFields);
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                Rectangle square = new Rectangle(50, 50);
                if ((row + col) % 2 == 0) {
                    square.setFill(Color.WHITE);
                } else {
                    square.setFill(Color.BLACK);
                }
                grid.add(square, col, row);
            }
        }

        this.setCenter(grid);
    }
}
