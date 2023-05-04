package main.java.model;

import javafx.scene.paint.Color;
import main.java.db.BoardDB;
import main.java.enums.ColorEnum;

public class Board {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private Die[][] board = new Die[ROWS][COLUMNS];

    private Player player;

    public Die getField(final int row, final int column) {
        return board[row - 1][column - 1];
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    public Boolean isEmpty() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean placeDie(final int value, final Color color, final int row, final int column) {
        boolean result = BoardDB.setField(
                this.player.getGame().getId(), this.player.getGame().getCurrentRound(), this.player.getId(), row,
                ColorEnum.fromString(color.toString()).getName(), column,
                value);

        if (!result) {
            return false;
        }

        this.player.getGame().notifyObservers();
        return true;
    }
}
