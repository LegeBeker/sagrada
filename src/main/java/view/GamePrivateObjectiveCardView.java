package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import main.java.controller.ViewController;

public class GamePrivateObjectiveCardView extends Pane {
    private Image imagePrivateObjectiveCard;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    public GamePrivateObjectiveCardView(final ViewController view) {
        this.imagePrivateObjectiveCard = new Image(
                "file:resources/img/objectivecards/" + view.getPrivateObjCardColor() + "-objectivecard.png");

        ImageView imageView = new ImageView();

        imageView.setImage(this.imagePrivateObjectiveCard);

        this.setPrefHeight(HEIGHT);
        this.setPrefWidth(WIDTH);
        this.getChildren().add(imageView);

        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, OFFSET);
    }
}
