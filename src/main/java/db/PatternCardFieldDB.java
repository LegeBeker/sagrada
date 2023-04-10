package main.java.db;

import java.util.List;
import java.util.Map;

public final class PatternCardFieldDB {

    private PatternCardFieldDB() {
    }

    public static List<Map<String, String>> getAllFieldsForCard(final int idPatternCard) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncardfield WHERE idpatterncard = ? ORDER BY position_x ASC, position_y ASC";
        String[] params = {Integer.toString(idPatternCard) };

        return db.exec(sql, params);
    }
}
