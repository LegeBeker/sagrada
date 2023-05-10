package main.java.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.java.controller.ViewController;
import main.java.model.ToolCard;

public class GameToolCardView extends ImageView {
    private Image imageToolCard;

    private static final int WIDTH = 150;
    private static final int HEIGHT = 200;

    private static final double SCALEINCREASE = 1.75;
    private static final int OFFSET = 100;

    public GameToolCardView(final ViewController view, final ToolCard toolCard) {
        this.imageToolCard = new Image("file:resources/img/toolcards/"
                + toolCard.getName().toLowerCase().replace(" ", "-") + ".png");

        this.setFitWidth(WIDTH);
        this.setFitHeight(HEIGHT);

        this.setImage(this.imageToolCard);

        view.effects().add3DHoverEffect(this, WIDTH, HEIGHT, SCALEINCREASE, OFFSET, 0);
    }
}
