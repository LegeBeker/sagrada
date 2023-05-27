package main.java.view;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;

public class GameCenterView extends BorderPane {

    private GameBoardsView gameBoardsView;

    private GameOfferView gameOfferView;

    public GameCenterView(final ViewController view) {
        this.gameOfferView = new GameOfferView(view);
        this.setTop(gameOfferView);
        this.gameBoardsView = new GameBoardsView(view);
        this.setCenter(this.gameBoardsView);
        this.setBottom(new GameToolBarView(view, this));
    }

    public void showPossibleMoves(final ArrayList<int[]> moves) {
        this.gameBoardsView.showPossibleMoves(moves);
    }

    public void cleanTargets() {
        this.gameBoardsView.cleanTargets();
    }

    public void updateSelectionStatus(final Boolean isSelected){
        this.gameOfferView.updateSelectionStatus(isSelected);
    }
}
