package main.java.view;

import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;

public class GamePublicObjectiveCardsView extends FlowPane {

    private static final int HGAP = 25;
    private static final int VGAP = 10;

    public GamePublicObjectiveCardsView(final ViewController view) {
        for (Integer publicObjectiveCardId : view.getObjectiveCardsIds()) {
            this.getChildren().add(new GamePublicObjectiveCardView(view, publicObjectiveCardId));
        }

        this.setHgap(HGAP);
        this.setVgap(VGAP);
    }
}
