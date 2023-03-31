package db;

import java.util.List;
import java.util.Map;

public final class GameDB {
    private GameDB() {
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM game";

        return db.exec(sql, null);
    }
}
