package main.java.view;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.java.controller.ViewController;

public class GameToolBarView extends HBox {

    public GameToolBarView(final ViewController view) {
        this.getChildren().addAll(new GameToolCardsView(view), new GamePublicObjectiveCardsView(view),
                new GamePrivateObjectiveCardView(view));

        this.getChildren().forEach(child -> {
            HBox.setHgrow(child, Priority.ALWAYS);
        });

        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
    }
}
