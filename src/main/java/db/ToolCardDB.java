package main.java.db;

import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import main.java.enums.ColorEnum;

public final class ToolCardDB {
    private ToolCardDB() {
    }

    public static List<Map<String, String>> getToolCards(final int idGame) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM toolcard tc JOIN gametoolcard gtc ON tc.idtoolcard = gtc.idtoolcard WHERE gtc.idgame = ?";
        String[] params = {Integer.toString(idGame)};
        return db.exec(sql, params);
    }

    public static Map<String, String> getToolCard(final int idGame, final String toolCardname) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM toolcard tc JOIN gametoolcard gtc ON tc.idtoolcard = gtc.idtoolcard WHERE gtc.idgame = ? AND tc.name = ?";
        String[] params = {Integer.toString(idGame), toolCardname};
        return db.exec(sql, params).get(0);
    }

    public static boolean updateGameDieValue(final int idgame, final int dieNumber, final String dieColor,
            final int eyes) {
        Database db = Database.getInstance();
        String sql = "UPDATE gamedie SET eyes = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        String[] params = {Integer.toString(eyes), Integer.toString(idgame), Integer.toString(dieNumber),
                ColorEnum.fromString(dieColor).getName()};
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

    public static void addDieToBag(final int idgame, final String dieColor, final int dieNumber, final int roundID) {
        Database db = Database.getInstance();

        // -- Update old die
        String sql = "UPDATE gamedie SET roundID = NULL WHERE idgame = ? AND dienumber = ? AND diecolor = ?";
        String[] params = {Integer.toString(idgame), Integer.toString(dieNumber),
                ColorEnum.fromString(dieColor).getName()};
        db.exec(sql, params);

        // -- Generate new die
        String sql2 = "UPDATE gamedie SET roundID = ? WHERE idgame = ? AND roundID IS NULL ORDER BY RAND() LIMIT 1";
        // -- The roundID is 1 greater than the current round...
        String[] params2 = {Integer.toString(roundID - 1), Integer.toString(idgame)};
        db.exec(sql2, params2);
    }
}
