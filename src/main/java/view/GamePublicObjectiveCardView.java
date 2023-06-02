package main.java.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import main.java.controller.ViewController;

public class GamePublicObjectiveCardView extends StackPane {
    private Image imageToolCard;

    private final int width = 150;
    private final int height = 200;
    private final ImageView imageView;

    private final double scaleIncrease = 1.75;
    private final int offset = 100;

    public GamePublicObjectiveCardView(final ViewController view, final int publicObjectiveCardId) {
        this.imageView = new ImageView();
        this.imageToolCard = new Image(getClass().getResource("/img/objectivecards/"
                + view.getObjectiveCardName(publicObjectiveCardId).toLowerCase().replace(" ", "-")
                + "-objectivecard.png").toExternalForm());

        this.imageView.setFitWidth(this.width);
        this.imageView.setFitHeight(this.height);

        this.imageView.setImage(this.imageToolCard);

        this.getChildren().add(imageView);

        view.effects().add3DHoverEffect((Node) this, width, height, scaleIncrease, offset, 0);
    }
}
