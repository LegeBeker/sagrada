package main.java.view;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameCenterView extends BorderPane {
    public GameCenterView(final ViewController view, final Game game) {
        this.setTop(null);
        this.setCenter(new GameBoardsView(view, game));
        this.setBottom(new GameToolBarView(view, game));
    }
}
