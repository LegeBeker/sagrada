package main.java.db;

import java.util.List;
import java.util.Map;

public final class ToolCardDB {
    private ToolCardDB() {
    }

    public static List<Map<String, String>> getToolCards(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM toolcard tc JOIN gametoolcard gtc ON tc.idtoolcard = gtc.idtoolcard WHERE gtc.idgame = ?";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static boolean updateGameDieValue(final int idgame, final int value) {
        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET value = ? WHERE idgame = ?";
        String[] params = {Integer.toString(value), Integer.toString(idgame)};
        db.exec(sql, params);
        return true;
    }

}
