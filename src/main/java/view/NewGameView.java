package main.java.view;

import javafx.scene.layout.VBox;
import main.java.controller.ViewController;

public class NewGameView extends VBox {

    private ViewController view;

    public NewGameView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
    }
}
