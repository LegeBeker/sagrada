package view;

import controller.ViewController;
import javafx.scene.layout.VBox;

public class NewGameView extends VBox {

    private ViewController view;

    public NewGameView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
    }
}
