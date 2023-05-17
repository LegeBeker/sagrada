package main.java.db;

import java.util.List;
import java.util.Map;

public class FavorTokenDB {
    private FavorTokenDB() {

    };

    public static List<Map<String, String>> getFavorTokensForToolCard(final int idToolCard, final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM gamefavortoken WHERE idgame = ? AND gametoolcard = ?;";
        String[] params = {Integer.toString(idToolCard), Integer.toString(idGame)};

        return db.exec(sql, params);
    }


}
