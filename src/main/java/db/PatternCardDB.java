package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.model.PatternCard;
import main.java.model.PatternCardField;

public final class PatternCardDB {

    private static final int ROWS = 4;
    private static final int COLUMNS = 5;

    private PatternCardDB() {
    }

    public static Map<String, String> get(final int idPatternCard) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncard WHERE idpatterncard = ? LIMIT 1";
        String[] params = {Integer.toString(idPatternCard) };

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
        String[] params = {Integer.toString(idPatternCard) };

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> setPatternCardOptions(final int idPatternCard, final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO patterncardoption VALUE (?, ?);";
        String[] params = { Integer.toString(idPatternCard), Integer.toString(idPlayer) };

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getPatternCardOptions(final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncardoption WHERE idplayer = ?;";
        String[] params = {Integer.toString(idPlayer) };

        return db.exec(sql, params);
    }

    public static int createPatternCard(final PatternCard patternCard) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO patterncard (name, difficulty, standard) VALUES (?, ?, ?);";
        String[] params = {patternCard.getName(), Integer.toString(patternCard.getDifficulty()),
                patternCard.getStandard() ? "1" : "0" };

        List<Map<String, String>> id = db.exec(sql, params);

        String lastId = id.get(0).get("id");

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO patterncardfield (idpatterncard, position_x, position_y, color, value) VALUES ");

        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                PatternCardField field = patternCard.getField(row, col);
                sqlBuilder.append("(" + lastId + ", " + col + ", " + row + ", "
                        + (field.getColorName() != null ? "'" + field.getColorName() + "'" : "null") + ", "
                        + (field.getValue() != null ? field.getValue().toString() : "null") + "),");
            }
        }

        String sql2 = sqlBuilder.toString();
        sql2 = sql2.substring(0, sql2.length() - 1);

        db.exec(sql2, null);

        return Integer.parseInt(lastId);
    }
}
