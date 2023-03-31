package view;

import controller.ViewController;
import javafx.scene.layout.VBox;

public class StatsView extends VBox {

    private ViewController view;

    public StatsView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
    }
}
