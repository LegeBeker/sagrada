package main.java.db;

import java.awt.Color;
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

    public static boolean updateGameDieValue(final int idgame, final int eyes) {
        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET eyes = ? WHERE idgame = ?";
        String[] params = {Integer.toString(eyes), Integer.toString(idgame)};
        db.exec(sql, params);
        return true;
    }
    
    public static boolean updateGameDieColor(final int idgame, final String color) {
        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET diecolor = ? WHERE idgame = ?";
        String[] params = {color.toString(), Integer.toString(idgame)};
        db.exec(sql, params);
        return true;
    }
}
