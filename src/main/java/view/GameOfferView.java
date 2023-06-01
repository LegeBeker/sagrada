package main.java.view;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameOfferView extends FlowPane implements Observer {

    private ViewController view;

    private ArrayList<DieView> dieViews = new ArrayList<DieView>();

    public GameOfferView(final ViewController view) {
        this.view = view;
        Observable.addObserver(Game.class, this);

        this.update();
    }

    @Override
    public void update() {
        this.getChildren().clear();
        Boolean isDraggable = view.isTurnPlayer();

        if (view.getSelectedToolcardName() != null) {
            if (view.getSelectedToolcardName().equals("eglomiseBrush")
                    || view.getSelectedToolcardName().equals("copperFoilBurnisher")) {
                isDraggable = false;
            }
        }

        for (Map<String, String> die : view.getOffer()) {
            DieView dieView = new DieView(this.view, Integer.parseInt(die.get("eyes")), Color.web(die.get("color")),
                    Integer.parseInt(die.get("number")), isDraggable);
            this.getChildren().add(dieView);
        }

        this.getChildren().addAll(dieViews);
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
