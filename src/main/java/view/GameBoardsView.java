package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.java.controller.ViewController;
import main.java.model.Player;

public class GameBoardsView extends HBox {

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

        this.getChildren().add(grid);
    }

    private void showPlayerGameboards() {
        int cardCount = 0;
        for (Player player : view.getPlayers()) {
            if (player.getPatternCard() == null) {
                continue; // skip players without a pattern card
            }

            if (cardCount >= MAXROWS * MAXCOLS) {
                break; // exit the loop once max grid size is reached
            }

            PatternCardView patternCardView = new PatternCardView(this.view, player.getPatternCardId(), player.getId());

            grid.add(patternCardView, cardCount % MAXCOLS, cardCount / MAXCOLS);
            cardCount++;
        }
    }

    public void showPossibleMoves(final ArrayList<int[]> moves) {
        PatternCardView patternCardView = (PatternCardView) grid.getChildren().get(0);
        moves.forEach((move) -> {
            patternCardView.getGrid().getChildren().forEach((cell) -> {
                int[] location = { GridPane.getColumnIndex(cell), GridPane.getRowIndex(cell) };
                if (move[0] == location[0] && move[1] == location[1]) {
                    cell.setStyle("-fx-border-color: #00FFBF;");
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
}
