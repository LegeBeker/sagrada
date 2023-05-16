package main.java.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameToolBarView extends HBox {

    public GameToolBarView(final ViewController view, final Game game) {
        this.getChildren().addAll(new GameToolCardsView(view, game), new GamePublicObjectiveCardsView(view, game),
                new GamePrivateObjectiveCardView(view,
                        view.getGameController().getCurrentPlayer(game.getId()).getPrivateObjCardColor()),
                new RoundTrackView(view, game));

        this.getChildren().forEach(child -> {
            HBox.setHgrow(child, Priority.ALWAYS);
        });

        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
    }
}
