package db;

import java.util.ArrayList;

public final class GameDB {
    private GameDB() {
    }

    public static ArrayList<ArrayList<String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM game";

        return db.exec(sql, null);
    }
}
