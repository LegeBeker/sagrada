package main.java.view;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;

public class GamesOverview extends BorderPane {

    private ViewController view;

    public GamesOverview(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setCenter(new GamesList(view));
        this.setRight(new InvitesList(view));
    }

}
