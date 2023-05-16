package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;

public class GamePrivateObjectiveCardView extends ImageView {
    private Image imageGoalCard;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    public GamePrivateObjectiveCardView(final ViewController view, final String goalCardColor) {
        this.imageGoalCard = new Image("file:resources/img/objectivecards/" + goalCardColor + "-objectivecard.png");

        this.setFitWidth(WIDTH);
        this.setFitHeight(HEIGHT);

        this.setImage(this.imageGoalCard);

        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, 0);
    }
}
