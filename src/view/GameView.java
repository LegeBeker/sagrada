package view;

import controller.ViewController;
import javafx.scene.layout.VBox;
import model.Game;

public class GameView extends VBox {

    private ViewController view;
    private Game game;

    public GameView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;
        this.setBackground(view.getBackground());
    }
}
