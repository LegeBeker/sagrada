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

public class PatternCardView extends BorderPane {

    private static final int ROWS = 5;
    private static final int COLUMNS = 4;
    private static final int RECTANGLE = 50;
    private static final int GRIDGAP = 10;
    private static final int DOTRADIUS = 3;
    private static final Color BLACK = Color.BLACK;

    private final int width = 300;
    private final int height = 300;
    private static final int POSITIONLOW = 15;
    private static final int POSITIONMEDIUM = 25;
    private static final int POSITIONHIGH = 35;

    private final Background backgroundCard = new Background(new BackgroundFill(Color.BLACK, null, null));
    private final GridPane grid = new GridPane();
    private final Text cardName = new Text();
    private final Text cardDifficulty = new Text();

    public PatternCardView(final ViewController view) {
        this.setPrefSize(width, height);
        this.setBackground(backgroundCard);
        this.getStyleClass().add("background");

        Random random = new Random();
        int randomNumber = random.nextInt(24) + 1;
        PatternCard patternCard = view.getPatternCardController().getPatternCard(randomNumber);

        cardName.setText(patternCard.getName());
        TextFlow cardNameFlow = new TextFlow(cardName);
        cardNameFlow.setPadding(new Insets(10, 10, 0, 10));
        cardName.setFill(Color.WHITE);

        this.setTop(cardNameFlow);

        drawPatternCard(patternCard);
        grid.setPadding(new Insets(10));
        this.setCenter(grid);

        cardDifficulty.setText("Difficulty: " + patternCard.getDifficulty());
        TextFlow cardDifficultyFlow = new TextFlow(cardDifficulty);
        cardDifficultyFlow.setPadding(new Insets(0, 10, 10, 10));
        cardDifficulty.setFill(Color.WHITE);

        this.setBottom(cardDifficultyFlow);
    }

    private void drawPatternCard(final PatternCard patternCard) {
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                Rectangle rectangle = new Rectangle(RECTANGLE, RECTANGLE);
                rectangle.setFill(patternCard.getField(row, col).getColor());
                if (patternCard.getField(row, col).getValue() > 0) {
                    Group rectangleWithDots = addDotsToRectangle(rectangle, patternCard.getField(row, col).getValue());
                    grid.add(rectangleWithDots, row, col);
                } else {
                    grid.add(rectangle, row, col);
                }

                grid.setHgap(GRIDGAP);
                grid.setVgap(GRIDGAP);
            }
        }
    }

    private Group addDotsToRectangle(final Rectangle rectangle, final int value) {
        Circle[] dots = new Circle[value];

        switch (value) {
            case 1:
                dots[0] = new Circle(POSITIONMEDIUM, POSITIONMEDIUM, DOTRADIUS, BLACK);
                break;
            case 2:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, BLACK);
                break;
            case 3:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, BLACK);
                dots[1] = new Circle(POSITIONMEDIUM, POSITIONMEDIUM, DOTRADIUS, BLACK);
                dots[2] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, BLACK);
                break;
            case 4:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONLOW, DOTRADIUS, BLACK);
                dots[2] = new Circle(POSITIONLOW, POSITIONHIGH, DOTRADIUS, BLACK);
                dots[3] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, BLACK);
                break;
            case 5:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONLOW, DOTRADIUS, BLACK);
                dots[2] = new Circle(POSITIONMEDIUM, POSITIONMEDIUM, DOTRADIUS, BLACK);
                dots[3] = new Circle(POSITIONLOW, POSITIONHIGH, DOTRADIUS, BLACK);
                dots[4] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, BLACK);
                break;
            case 6:
                dots[0] = new Circle(POSITIONLOW, POSITIONLOW, DOTRADIUS, BLACK);
                dots[1] = new Circle(POSITIONHIGH, POSITIONLOW, DOTRADIUS, BLACK);
                dots[2] = new Circle(POSITIONLOW, POSITIONMEDIUM, DOTRADIUS, BLACK);
                dots[3] = new Circle(POSITIONHIGH, POSITIONMEDIUM, DOTRADIUS, BLACK);
                dots[4] = new Circle(POSITIONLOW, POSITIONHIGH, DOTRADIUS, BLACK);
                dots[5] = new Circle(POSITIONHIGH, POSITIONHIGH, DOTRADIUS, BLACK);
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
