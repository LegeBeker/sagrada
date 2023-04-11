package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.PatternCard;
import main.java.model.PatternCardField;
import main.java.model.Player;

public class PatternCardView extends BorderPane {

    private static final int ROWS = 5;
    private static final int COLUMNS = 4;

    private static final int RECTANGLE = 50;
    private static final int PADDING = 10;

    private final int width = 300;
    private final int height = 300;

    private final GridPane grid = new GridPane();
    private final Text cardTopText = new Text();
    private final Text cardDifficulty = new Text();

    public PatternCardView(final ViewController view, final PatternCard patternCard, final Player player) {
        this.setPrefSize(width, height);
        this.getStyleClass().add("background");

        drawPatternCard(patternCard);
        grid.setPadding(new Insets(0, PADDING, PADDING, 0));
        this.setCenter(grid);

        TextFlow cardTopTextFlow = new TextFlow(cardTopText);
        cardTopTextFlow.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
        cardTopText.setFill(Color.WHITE);
        this.setTop(cardTopTextFlow);

        if (player == null) {
            cardTopText.setText(patternCard.getName());

            cardDifficulty.setText("Moeilijkheid: " + patternCard.getDifficulty());
            TextFlow cardDifficultyFlow = new TextFlow(cardDifficulty);
            cardDifficultyFlow.setPadding(new Insets(0, PADDING, PADDING, PADDING));
            cardDifficulty.setFill(Color.WHITE);

            this.setBottom(cardDifficultyFlow);
        } else {
            cardTopText.setText(player.getUsername());
        }

        grid.setHgap(PADDING);
        grid.setVgap(PADDING);
    }

    private void drawPatternCard(final PatternCard patternCard) {
        for (int col = 1; col <= COLUMNS; col++) {
            for (int row = 1; row <= ROWS; row++) {
                PatternCardField field = patternCard.getField(row, col);

                if (field.getValue() != null) {
                    grid.add(new DieView(field.getValue()), row, col);
                } else {
                    Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
                    rectangle.setFill(field.getColor());

                    grid.add(rectangle, row, col);
                }
            }
        }
    }
}
