package main.java.model;

public class Board {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private Die[][] board = new Die[ROWS][COLUMNS];

    public Board() {

    }

    public Die getField(final int row, final int column) {
        return board[row - 1][column - 1];
    }

    public Boolean isEmpty() {
        boolean containsItems = false;

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (board[i][j] != null) {
                    containsItems = true;
                    break;
                }
            }
            if (containsItems) {
                break;
            }
        }

        if (containsItems) {
            return false;
        }

        return true;
    }
}
