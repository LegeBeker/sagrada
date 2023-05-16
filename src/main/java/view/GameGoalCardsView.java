package main.java.view;

import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameGoalCardsView extends FlowPane {

    public GameGoalCardsView(final ViewController view, final Game game) {
        String goalcardcolor = view.getGameController()
                .getPrivateObjectiveColor(view.getGameController().getCurrentPlayer(game.getId()).getId());
        this.getChildren().add(new GameGoalCardView(view, goalcardcolor));
    }

}
