package main.java.view;

import java.util.ArrayList;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Die;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameOfferView extends FlowPane implements Observer {

    private ViewController view;

    private Game game;

    public GameOfferView(final ViewController view) {
        this.view = view;

        Observable.addObserver(Game.class, this);

        this.update();
    }

    @Override
    public void update() {
        this.getChildren().clear();

        for (Die die : view.getOffer()) {
            DieView dieView = new DieView(die.getEyes(), die.getColor(), die.getNumber(), true);

            this.getChildren().add(dieView);
        }
    }

    public boolean getHelpFunction() {
        return this.game.getHelpFunction();
    }

    public void showPossibleMoves(final int eyes, final Color color) {
        ArrayList<int[]> moves = this.view.getPossibleMoves(eyes, color);
        GameCenterView gameCenterView = (GameCenterView) this.getParent();
        gameCenterView.showPossibleMoves(moves);
    }

    public void cleanTargets() {
        GameCenterView gameCenterView = (GameCenterView) this.getParent();
        gameCenterView.cleanTargets();
    }
}
