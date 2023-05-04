package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.model.PatternCard;
import main.java.model.Player;

public class GameBoardsView extends HBox {

    private static final int GRIDGAP = 20;
    private final int maxRows = 2;
    private final int maxCols = 2;

    private ViewController view;
    private Game game;

    private GridPane grid = new GridPane();

    private final Background background = new Background(new BackgroundFill(Color.web("#334564"), null, null));

    public GameBoardsView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;
        Player currentUser = null;
        this.setBackground(background);

        boolean thisPlayerHasChosen = true;
        for (Player player : this.game.getPlayers()) {
            if (player.getUsername().equals(view.getAccountController().getAccount().getUsername())) {
                if (player.getPatternCard() == null) {
                    thisPlayerHasChosen = false;
                }
                currentUser = player;
                break;
            }
        }
        if (thisPlayerHasChosen) {
            showPlayerGameboards();
        } else {
            showPatternCardOptions(currentUser);
        }

        grid.setHgap(GRIDGAP);
        grid.setVgap(GRIDGAP);

        grid.setAlignment(Pos.CENTER);
        this.setAlignment(Pos.CENTER);

        this.getChildren().add(grid);
    }

    private void showPlayerGameboards() {
        int cardCount = 0;
        for (Player player : this.game.getPlayers()) {
            if (player.getPatternCard() == null) {
                continue; // skip players without a pattern card
            }

            if (cardCount >= maxRows * maxCols) {
                break; // exit the loop once max grid size is reached
            }

            grid.add(new PatternCardView(this.view, player.getPatternCard(), player), cardCount % maxCols,
                    cardCount / maxCols);
            cardCount++;
        }
    }

    private void showPatternCardOptions(final Player player) {
        int cardCount = 0;
        for (PatternCard patternCardOption : player.getPatternCardOptions()) {
            if (cardCount >= maxRows * maxCols) {
                break; // exit the loop once max grid size is reached
            }

            grid.add(new PatternCardView(this.view, patternCardOption, player), cardCount % maxCols,
                    cardCount / maxCols);
            cardCount++;
        }
    }

    public void showPossibleMoves(final ArrayList<int[]> moves) {
        PatternCardView patternCardView = (PatternCardView) grid.getChildren().get(0);
        moves.forEach((move) -> {
            patternCardView.getGrid().getChildren().forEach((cell) -> {
                int[] location = { patternCardView.getGrid().getColumnIndex(cell),
                        patternCardView.getGrid().getRowIndex(cell) };
                if (move[0] == location[0] && move[1] == location[1]) {
                    cell.setStyle("-fx-border-color: blue;");
                }

            });
        });
    }

    public void cleanTargets() {
        PatternCardView patternCardView = (PatternCardView) grid.getChildren().get(0);
        patternCardView.getGrid().getChildren().forEach((cell) -> {
            cell.setStyle(null);
        });
    }
}
