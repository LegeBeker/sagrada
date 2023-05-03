package main.java.view;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameView extends BorderPane {

    private ViewController view;
    private Game game;

    public GameView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;

        this.setLeft(new GameScoreView(this.view, this.game));
        this.setCenter(new GameCenterView(this.view, this.game));
        this.setRight(new GameChatView(this.view, this.game));

        this.setBackground(this.view.getBackground());
    }
}
