package db;

import java.util.List;
import java.util.Map;

public final class PatternCardDB {
    private PatternCardDB() {
    }

    public static Map<String, String> get(final int idPatternCard) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncard WHERE idpatterncard = ? LIMIT 1";
        String[] params = {Integer.toString(idPatternCard)};

        return db.exec(sql, params).get(0);
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncard";

        return db.exec(sql, null);
    }
}
