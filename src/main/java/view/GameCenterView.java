package main.java.view;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameCenterView extends BorderPane {

    private GameBoardsView gameBoardsView;

    public GameCenterView(final ViewController view, final Game game) {
        this.setTop(new GameOfferView(view, game));
        this.gameBoardsView = new GameBoardsView(view, game);
        this.setCenter(this.gameBoardsView);
        this.setBottom(new GameToolBarView(view, game));
    }

    public void showPossibleMoves(final ArrayList<int[]> moves) {
        this.gameBoardsView.showPossibleMoves(moves);
    }

    public void cleanTargets() {
        this.gameBoardsView.cleanTargets();
    }
}
