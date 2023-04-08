package main.java.db;

import java.util.List;
import java.util.Map;

public class PatternCardFieldDB {
    private PatternCardFieldDB() {
    }

    public static List<Map<String, String>> getAllFieldsForCard(final int idPatternCard) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM patterncardfield WHERE idpatterncard = ?";
        String[] params = {Integer.toString(idPatternCard)};


        return db.exec(sql, params);
    }
}
