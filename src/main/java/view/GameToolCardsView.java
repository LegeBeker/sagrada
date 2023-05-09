package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.ToolCard;

public class GameToolCardsView extends FlowPane {

    private final int gap = 10;
    private final int padding = 5;

    public GameToolCardsView(final ViewController view, final Game game) {

        for (ToolCard toolCard : game.getToolCards()) {
            this.getChildren().add(new GameToolCardView(view, toolCard));
        }

        this.setPadding(new Insets(0, 0, padding, padding));
        this.setHgap(gap);
    }
}
