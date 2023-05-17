package main.java.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.db.BoardDB;
import main.java.enums.ColorEnum;
import main.java.pattern.Observable;

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

    public boolean placeDie(final Color color, final int number, final int row, final int column) {
        boolean result = BoardDB.setField(
                this.player.getGame().getId(), this.player.getGame().getCurrentRound(), this.player.getId(), row,
                column, ColorEnum.fromString(color.toString()).getName(), number);

        if (!result) {
            return false;
        }

        Observable.notifyObservers(Game.class);
        return true;
    }

    public static Board get(final Player player) {
        Board board = new Board();
        board.setPlayer(player);

        List<Map<String, String>> fieldValues = BoardDB.getBoard(player.getGame().getId(), player.getId());

        for (Map<String, String> map : fieldValues) {
            int row = Integer.parseInt(map.get("position_y"));
            int col = Integer.parseInt(map.get("position_x"));

            board.board[row - 1][col - 1] = Die.mapToDie(map);
        }

        return board;
    }

    public static Board update(final Board board) {
        List<Map<String, String>> fieldValues = BoardDB.getBoard(board.player.getGame().getId(), board.player.getId());

        for (Map<String, String> map : fieldValues) {
            int row = Integer.parseInt(map.get("position_y"));
            int col = Integer.parseInt(map.get("position_x"));

            board.board[row - 1][col - 1] = Die.mapToDie(map);
        }

        return board;
    }

    public static void createBoards(final Game game) {
        Map<Player, ArrayList<int[]>> boards = new HashMap<>();
        game.getPlayers().forEach((player) -> {
            boards.put(player, new ArrayList<>());
            for (int row = 1; row <= ROWS; row++) {
                for (int col = 1; col <= COLUMNS; col++) {
                    boards.get(player).add(new int[] {col, row});
                }
            }
        });

        BoardDB.createBoard(boards);
    }
}
