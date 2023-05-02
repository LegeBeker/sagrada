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
}
