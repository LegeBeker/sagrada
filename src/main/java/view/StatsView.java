package main.java.view;

import javafx.scene.layout.VBox;
import main.java.controller.ViewController;

public class StatsView extends VBox {

    private ViewController view;

    public StatsView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
    }
}
