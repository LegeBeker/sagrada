package main.java.view;

import javafx.scene.layout.VBox;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameView extends VBox {

    private ViewController view;
    private Game game;

    public GameView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;
        this.setBackground(view.getBackground());
    }
}
