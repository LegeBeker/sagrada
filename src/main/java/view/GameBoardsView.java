package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameBoardsView extends HBox implements Observer {

    private static final int GRIDGAP = 20;
    private static final int MAXROWS = 2;
    private static final int MAXCOLS = 2;

    private ViewController view;

    private GridPane grid = new GridPane();

    public GameBoardsView(final ViewController view) {
        this.view = view;

        showPlayerGameboards();

        grid.setHgap(GRIDGAP);
        grid.setVgap(GRIDGAP);

        grid.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);

        Observable.addObserver(Game.class, this);

        this.getChildren().add(grid);
    }

    private void showPlayerGameboards() {
        int cardCount = 0;
        // -- Add the player to the first index so it always shows on the left side of
        // the screen.
        ArrayList<Integer> playersList = view.getPlayerIds();
        int index = playersList.indexOf(view.getPlayerId());
        playersList.remove(index);
        playersList.add(0, view.getPlayerId());

        for (Integer playerId : playersList) {
            if (view.getPlayerPatternCardId(playerId) == null) {
                continue; // skip players without a pattern card
            }

            if (cardCount >= MAXROWS * MAXCOLS) {
                break; // exit the loop once max grid size is reached
            }

            PatternCardView patternCardView = new PatternCardView(this.view,
                    view.getPlayerPatternCardId(playerId), playerId);

            grid.add(patternCardView, cardCount % MAXCOLS, cardCount / MAXCOLS);
            cardCount++;
        }
    }

    public void showPossibleMoves(final ArrayList<int[]> moves, final ArrayList<int[]> bestMoves) {
        PatternCardView patternCardView = (PatternCardView) grid.getChildren().get(0);
        patternCardView.getGrid().getChildren().forEach((cell) -> {
            moves.forEach((move) -> {
                int[] location = {GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)};
                if (move[0] == location[0] && move[1] == location[1]) {
                    cell.setStyle("-fx-border-color: #00FFBF;");
                }
            });
            bestMoves.forEach((move) -> {
                int[] location = {GridPane.getRowIndex(cell), GridPane.getColumnIndex(cell)};
                if (move[0] == location[0] && move[1] == location[1]) {
                    cell.setStyle("-fx-border-color: #FF1500;");
                }
            });
        });
    }

    public void cleanTargets() {
        PatternCardView patternCardView = (PatternCardView) grid.getChildren().get(0);
        patternCardView.getGrid().getChildren().forEach((cell) -> {
            cell.setStyle("-fx-border-color: transparent;");
        });
    }

    @Override
    public void update() {
        grid.getChildren().clear();
        showPlayerGameboards();
    }
}
