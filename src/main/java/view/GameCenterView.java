package main.java.view;

import java.util.ArrayList;
import java.util.Map;

import javafx.scene.layout.BorderPane;
import main.java.controller.ViewController;

public class GameCenterView extends BorderPane {

    private GameBoardsView gameBoardsView;

    private GameOfferView gameOfferView;
    private GameToolBarView gameToolBarView;

    public GameCenterView(final ViewController view) {
        this.setTop(new GameOfferView(view));
        this.gameBoardsView = new GameBoardsView(view);
        this.setCenter(this.gameBoardsView);
        this.setBottom(new GameToolBarView(view));
    }

    public void showPossibleMoves(final ArrayList<int[]> moves) {
        this.gameBoardsView.showPossibleMoves(moves);
    }

    public void cleanTargets() {
        this.gameBoardsView.cleanTargets();
    }

    public void dieSelectedForToolcard(final Map<String, String> selectedDieMap){
        this.gameToolBarView.dieSelectedForToolcard(selectedDieMap);
    }
}
