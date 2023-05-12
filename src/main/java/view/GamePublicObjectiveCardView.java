package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;
import main.java.model.ObjectiveCard;

public class GamePublicObjectiveCardView extends ImageView {
    private Image imageToolCard;

    private final int width = 150;
    private final int height = 200;

    private final double scaleIncrease = 1.75;
    private final int offset = 100;

    public GamePublicObjectiveCardView(final ViewController view, final ObjectiveCard objCard) {
        // this.imageToolCard = new Image("file:resources/img/toolcards/"
        //         + objCard.getName().toLowerCase().replace(" ", "-") + ".png");

        this.setFitWidth(this.width);
        this.setFitHeight(this.height);

        this.setImage(this.imageToolCard);

        // view.effects().add3DHoverEffect(this, width, height, scaleIncrease, offset);
    }
}
