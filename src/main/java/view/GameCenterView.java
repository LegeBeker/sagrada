package main.java.view;

import java.util.ArrayList;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Rectangle;
import main.java.controller.ViewController;

public class GameCenterView extends BorderPane {

    private GameBoardsView gameBoardsView;

    public GameCenterView(final ViewController view) {
        HBox onlyDice = new HBox(10);
        onlyDice.getChildren().addAll(new GameOfferView(view), new RoundTrackView(view), new Rectangle(450, 0) );
        onlyDice.getChildren().forEach(child -> {HBox.setHgrow(child, Priority.ALWAYS);});
        this.setTop(onlyDice);
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
}
