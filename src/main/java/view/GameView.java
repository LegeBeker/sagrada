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

        this.setLeft(new GameScoreView(view, game));
        this.setCenter(new GameBoardView(view, game));
        this.setBottom(new GameToolBarView(view, game));
        this.setRight(new GameChatView(view, game));

        this.setBackground(view.getBackground());
    }
}
