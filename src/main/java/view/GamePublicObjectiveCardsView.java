package main.java.view;

import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.ObjectiveCard;

public class GamePublicObjectiveCardsView extends FlowPane {

    private static final int HGAP = 25;
    private static final int VGAP = 10;

    public GamePublicObjectiveCardsView(final ViewController view, final Game game) {
        for (ObjectiveCard publicObjectiveCard : game.getPublicToolcards()) {
            this.getChildren().add(new GamePublicObjectiveCardView(view, publicObjectiveCard));
        }

        this.setHgap(HGAP);
        this.setVgap(VGAP);
    }
}
