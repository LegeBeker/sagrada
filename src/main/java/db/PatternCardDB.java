package main.java.db;

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

    public static List<Map<String, String>> getAllStandard() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncard WHERE standard = 1 ORDER BY RAND();";

        return db.exec(sql, null);
    }

    public static List<Map<String, String>> getAllFieldsForCard(final int idPatternCard) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncardfield WHERE idpatterncard = ? ORDER BY position_x ASC, position_y ASC";
        String[] params = {Integer.toString(idPatternCard)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> setPatternCardOptions(final int idPatternCard, final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO patterncardoption VALUE (?, ?);";
        String[] params = {Integer.toString(idPatternCard), Integer.toString(idPlayer)};

        return db.exec(sql, params);
    }
}
