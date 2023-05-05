package main.java.db;

import java.util.List;
import java.util.Map;

public final class BoardDB {
    private BoardDB() {
    }

    public static Boolean setField(final int idGame, final int roundID, final int idPlayer, final int row,
            final int column,
            final String color,
            final int number) {
        Database db = Database.getInstance();

        String sql = "UPDATE playerframefield SET dienumber = ?, diecolor = ?"
                + " WHERE idgame = ? AND idplayer = ? AND position_x = ? AND position_y = ?";
        String[] params = {String.valueOf(number), color, String.valueOf(idGame), String.valueOf(idPlayer),
                String.valueOf(column), String.valueOf(row)};

        db.exec(sql, params);

        sql = "UPDATE gamedie SET roundID = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        params = new String[] {String.valueOf(roundID), String.valueOf(idGame), String.valueOf(number), color};

        db.exec(sql, params);

        return true;
    }

    public static List<Map<String, String>> getBoard(final int idGame, final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT DISTINCT pf.*, gd.eyes FROM playerframefield pf"
                + " JOIN gamedie gd ON gd.dienumber = pf.dienumber AND gd.diecolor = pf.diecolor WHERE pf.idgame = ? AND pf.idplayer = ?";
        String[] params = {String.valueOf(idGame), String.valueOf(idPlayer)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getBoards(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM playerframefield WHERE idgame = ?";
        String[] params = {String.valueOf(idGame)};

        return db.exec(sql, params);
    }
}
