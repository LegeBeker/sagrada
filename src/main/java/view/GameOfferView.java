package main.java.view;

import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.Die;
import main.java.model.Game;
import main.java.pattern.Observer;

public class GameOfferView extends FlowPane implements Observer {

    private ViewController view;

    private Game game;

    public GameOfferView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;

        game.addObserver(this);

        this.update();
    }

    @Override
    public void update() {
        this.getChildren().clear();

        for (Die die : game.getOffer()) {
            DieView dieView = new DieView(die.getEyes(), die.getColor());

            this.getChildren().add(dieView);
        }
    }
}
