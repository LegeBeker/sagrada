package main.java.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameToolBarView extends HBox {

    private final Background background = new Background(new BackgroundFill(Color.web("#777564"), null, null));

    public GameToolBarView(final ViewController view, final Game game) {
        this.setBackground(background);

        this.getChildren().addAll(new GameToolCardsView(view, game), new GameButtonsView(view, game));

        this.getChildren().forEach(child -> {
            HBox.setHgrow(child, Priority.ALWAYS);
        });
        this.getChildren().add(new RoundTrackView());
    }
}
