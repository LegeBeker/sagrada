package main.java.view;

import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.ObjectiveCard;
import main.java.model.ToolCard;

public class GamePublicObjectiveCardsView extends FlowPane {

    private final int gap = 10;

    public GamePublicObjectiveCardsView(final ViewController view, final Game game) {

        for (ObjectiveCard publicObjectiveCard : game.getPublicToolcards()) {
            this.getChildren().add(new GamePublicObjectiveCardView(view, publicObjectiveCard));
        }

        this.setHgap(gap);
    }
}
