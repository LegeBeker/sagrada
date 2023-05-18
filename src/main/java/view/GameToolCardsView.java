package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;

public class GameToolCardsView extends FlowPane {

    private static final int GAP = 10;
    private static final int PADDING = 5;

    public GameToolCardsView(final ViewController view) {

        for (String toolCardName : view.getToolCardsNames()) {
            this.getChildren().add(new GameToolCardView(view, toolCardName));
        }

        this.setPadding(new Insets(0, 0, PADDING, PADDING));
        this.setHgap(GAP);
    }
}
