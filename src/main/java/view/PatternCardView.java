package main.java.view;

import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

    private final int width = 300;
    private final int height = 300;
    private static final int POSITIONLOW = 15;
    private static final int POSITIONMEDIUM = 25;
    private static final int POSITIONHIGH = 35;

    private final int totalCards = 24;

    private final GridPane grid = new GridPane();
    private final Text cardName = new Text();
    private final Text cardDifficulty = new Text();

    public PatternCardView(final ViewController view) {
        this.setPrefSize(width, height);
        this.getStyleClass().add("background");

        Random random = new Random();
        int randomNumber = random.nextInt(totalCards) + 1;
        PatternCard patternCard = view.getPatternCardController().getPatternCard(randomNumber);

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
        Circle[] dots = new Circle[value];

        switch (value) {
            case 1:
                dots[0] = new Circle(POSITIONMEDIUM, POSITIONMEDIUM, DOTRADIUS, Color.BLACK);
                break;
            case 2:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                break;
            case 3:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[1] = new Circle(POSITIONMEDIUM, POSITIONMEDIUM, DOTRADIUS, Color.BLACK);
                dots[2] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                break;
            case 4:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[2] = new Circle(POSITIONLOW, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                dots[3] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                break;
            case 5:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[2] = new Circle(POSITIONMEDIUM, POSITIONMEDIUM, DOTRADIUS, Color.BLACK);
                dots[3] = new Circle(POSITIONLOW, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                dots[4] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                break;
            case 6:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONLOW, DOTRADIUS, Color.BLACK);
                dots[2] = new Circle(POSITIONLOW, POSITIONMEDIUM, DOTRADIUS, Color.BLACK);
                dots[3] = new Circle(POSITIONHIGH, POSITIONMEDIUM, DOTRADIUS, Color.BLACK);
                dots[4] = new Circle(POSITIONLOW, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                dots[5] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, Color.BLACK);
                break;
            default:
                break;
        }

        Group dotsGroup = new Group();
        dotsGroup.getChildren().addAll(dots);

        Group rectangleWithDots = new Group(rectangle, dotsGroup);

        return rectangleWithDots;
    }

}
