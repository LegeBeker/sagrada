package main.java.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.java.controller.ViewController;

public class GameToolBarView extends HBox {

    private GameCenterView gameCenterView;

    public GameToolBarView(final ViewController view, final GameCenterView gameCenterView) {
        this.gameCenterView = gameCenterView;
        this.getChildren().addAll(new GameToolCardsView(view, gameCenterView), new GamePublicObjectiveCardsView(view),
                new GamePrivateObjectiveCardView(view),
                new RoundTrackView(view));

        this.getChildren().forEach(child -> {
            HBox.setHgrow(child, Priority.ALWAYS);
        });

        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
    }
}
