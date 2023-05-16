package main.java.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.java.controller.ViewController;
import main.java.model.ObjectiveCard;

public class GamePublicObjectiveCardView extends StackPane {
    private Image imageToolCard;

    private final int width = 150;
    private final int height = 200;
    private final ImageView imageView;

    private final double scaleIncrease = 1.75;
    private final int offset = 100;

    public GamePublicObjectiveCardView(final ViewController view, final ObjectiveCard objCard) {
        this.imageView = new ImageView();
        this.imageToolCard = new Image("file:resources/img/objectivecards/"
                + objCard.getName().toLowerCase().replace(" ", "-") + "-objectivecard.png");

        this.imageView.setFitWidth(this.width);
        this.imageView.setFitHeight(this.height);

        this.imageView.setImage(this.imageToolCard);

        this.getChildren().add(imageView);

        view.effects().add3DHoverEffect((Node) this, width, height, scaleIncrease, offset, 0);
    }
}
