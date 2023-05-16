package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;

public class GamePrivateObjectiveCardView extends ImageView {
    private Image imagePrivateObjectiveCard;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    public GamePrivateObjectiveCardView(final ViewController view, final String privateObjectiveCardColor) {
        this.imagePrivateObjectiveCard = new Image(
                "file:resources/img/objectivecards/" + privateObjectiveCardColor + "-objectivecard.png");

        this.setFitWidth(WIDTH);
        this.setFitHeight(HEIGHT);

        this.setImage(this.imagePrivateObjectiveCard);

        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, OFFSET);
    }
}
