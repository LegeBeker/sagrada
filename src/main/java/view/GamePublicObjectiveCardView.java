package main.java.view;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
        this.imageToolCard = new Image("file:resources/img/objectivecards/"
                + view.getObjectiveCardName(publicObjectiveCardId).toLowerCase().replace(" ", "-")
                + "-objectivecard.png");

        this.imageView.setImage(this.imageToolCard);

        Pane imagePane = new Pane();
        imagePane.setPrefHeight(this.height);
        imagePane.setPrefWidth(this.width);
        imagePane.getChildren().add(imageView);

        this.getChildren().add(imagePane);

        view.effects().add3DHoverEffect((Node) this, width, height, scaleIncrease, offset, 0);
    }
}
