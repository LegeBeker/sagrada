package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.ToolCard;

public class GameToolCardsView extends FlowPane {

    private static final int GAP = 10;
    private static final int PADDING = 5;

    public GameToolCardsView(final ViewController view) {

        for (ToolCard toolCard : view.getToolCards()) {
            this.getChildren().add(new GameToolCardView(view, toolCard));
        }

        this.setPadding(new Insets(0, 0, PADDING, PADDING));
        this.setHgap(GAP);
    }
}
