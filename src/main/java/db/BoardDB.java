package main.java.db;

import java.util.List;
import java.util.Map;

public final class BoardDB {
    private BoardDB() {
    }

    public static Boolean setField(final int idGame, final int roundID, final int idPlayer, final int row,
            final String color,
            final int column,
            final int value) {
        Database db = Database.getInstance();

        String sql = "UPDATE playerframefield SET dienumber = ? AND diecolor = ?"
                + " WHERE idgame = ? AND idplayer = ? AND position_x = ? AND position_y = ?";
        String[] params = { String.valueOf(value), color, String.valueOf(idGame), String.valueOf(idPlayer),
                String.valueOf(column), String.valueOf(row) };

        db.exec(sql, params);

        sql = "UPDATE gamedie SET roundID = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        params = new String[] { String.valueOf(roundID), String.valueOf(idGame), String.valueOf(value), color };

        db.exec(sql, params);

        return true;
    }

    public static List<Map<String, String>> getBoard(final int idGame, final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM playerframefield WHERE idgame = ? AND idplayer = ?";
        String[] params = { String.valueOf(idGame), String.valueOf(idPlayer) };

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getBoards(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM playerframefield WHERE idgame = ?";
        String[] params = { String.valueOf(idGame) };

        return db.exec(sql, params);
    }
}
