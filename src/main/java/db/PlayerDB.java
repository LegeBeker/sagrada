package main.java.db;

import java.util.List;
import java.util.Map;

public final class PlayerDB {
    private PlayerDB() {
    }

    public static Map<String, String> get(final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player WHERE idplayer = ? LIMIT 1";
        String[] params = {Integer.toString(idPlayer)};

        return db.exec(sql, params).get(0);
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player";

        return db.exec(sql, null);
    }
}
