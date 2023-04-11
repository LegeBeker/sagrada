package main.java.view;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import main.java.controller.ViewController;

public class StatsView extends VBox {

    private ViewController view;
    private final Button buttonBack = new Button("Terug");

    public StatsView(final ViewController view) {
        this.view = view;

        this.buttonBack.setOnAction(e -> {
            view.openMenuView();
        });

        this.getChildren().add(buttonBack);
        this.setBackground(view.getBackground());
    }
}
