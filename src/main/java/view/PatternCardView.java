package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.PatternCard;
import main.java.model.PatternCardField;

public class PatternCardView extends BorderPane {

    private static final int ROWS = 5;
    private static final int COLUMNS = 4;
    private static final int RECTANGLE = 50;
    private static final int GRIDGAP = 10;
    private static final int PADDING = 10;
    private static final int DOTRADIUS = 3;

    private static final int POSITIONLOW = 15;
    private static final int POSITIONMEDIUM = 25;
    private static final int POSITIONHIGH = 35;

    private final int width = 300;
    private final int height = 300;

    private final GridPane grid = new GridPane();
    private final Text cardName = new Text();
    private final Text cardDifficulty = new Text();

    public PatternCardView(final ViewController view, final PatternCard patternCard) {
        this.setPrefSize(width, height);
        this.getStyleClass().add("background");

        cardName.setText(patternCard.getName());
        TextFlow cardNameFlow = new TextFlow(cardName);
        cardNameFlow.setPadding(new Insets(PADDING, PADDING, 0, PADDING));
        cardName.setFill(Color.WHITE);

        this.setTop(cardNameFlow);

        drawPatternCard(patternCard);
        grid.setPadding(new Insets(0, PADDING, PADDING, 0));
        this.setCenter(grid);

        cardDifficulty.setText("Difficulty: " + patternCard.getDifficulty());
        TextFlow cardDifficultyFlow = new TextFlow(cardDifficulty);
        cardDifficultyFlow.setPadding(new Insets(0, PADDING, PADDING, PADDING));
        cardDifficulty.setFill(Color.WHITE);

        this.setBottom(cardDifficultyFlow);

        grid.setHgap(GRIDGAP);
        grid.setVgap(GRIDGAP);
    }

    private void drawPatternCard(final PatternCard patternCard) {
        for (int col = 1; col <= COLUMNS; col++) {
            for (int row = 1; row <= ROWS; row++) {
                PatternCardField field = patternCard.getField(row, col);

                Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
                rectangle.setFill(field.getColor());

                if (field.getValue() != null) {
                    Group rectangleWithDots = addDotsToRectangle(rectangle, field.getValue());
                    grid.add(rectangleWithDots, row, col);
                } else {
                    grid.add(rectangle, row, col);
                }
            }
        }
    }

    private Group addDotsToRectangle(final Rectangle rectangle, final int value) {
        ArrayList<Circle> dots = new ArrayList<Circle>();

        switch (Integer.toString(value)) {
            case "1":
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                break;
            case "2":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "3":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "4":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "5":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONMEDIUM, POSITIONMEDIUM));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            case "6":
                dots.add(createDot(POSITIONLOW, POSITIONLOW));
                dots.add(createDot(POSITIONHIGH, POSITIONLOW));
                dots.add(createDot(POSITIONLOW, POSITIONMEDIUM));
                dots.add(createDot(POSITIONHIGH, POSITIONMEDIUM));
                dots.add(createDot(POSITIONLOW, POSITIONHIGH));
                dots.add(createDot(POSITIONHIGH, POSITIONHIGH));
                break;
            default:
                break;
        }

        Group dotsGroup = new Group();
        dotsGroup.getChildren().addAll(dots);

        Group rectangleWithDots = new Group(rectangle, dotsGroup);

        return rectangleWithDots;
    }

    private Circle createDot(final double x, final double y) {
        return new Circle(x, y, DOTRADIUS, Color.BLACK);
    }
}
