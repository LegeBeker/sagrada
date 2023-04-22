package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;
import main.java.model.ToolCard;

public class GameToolCardView extends ImageView {
    private Image imageToolCard;

    private final int width = 150;
    private final int height = 200;

    public GameToolCardView(final ViewController view, final ToolCard toolCard) {
        this.imageToolCard = new Image("file:resources/img/toolcards/"
                + toolCard.getName().toLowerCase().replace(" ", "-") + ".png");

        this.setFitWidth(this.width);
        this.setFitHeight(this.height);

        this.setImage(this.imageToolCard);

        this.setOnMouseEntered(event -> {
            this.setScaleX(1.5);
            this.setScaleY(1.5);
            // bring closer to the user in 3D space
            this.setTranslateY(-10);
        });

        // Add a mouse exited event handler to reset the image scale
        this.setOnMouseExited(event -> {
            this.setScaleX(1.0);
            this.setScaleY(1.0);
        });
    }
}
