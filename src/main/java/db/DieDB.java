package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.enums.ColorEnum;

public final class DieDB {
    private DieDB() {
    }

    public static boolean createGameOffer(final int idGame) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO gamedie (idgame, diecolor, dienumber, eyes) "
                + "SELECT ?, d.color, d.number, FLOOR(RAND()* 6) + 1 " + "FROM die d " + "LEFT JOIN gamedie g "
                + "ON d.color = g.diecolor AND d.number = g.dienumber AND g.idgame = ? " + "WHERE g.idgame IS NULL";
        String[] params = {Integer.toString(idGame), Integer.toString(idGame)};

        db.exec(sql, params);

        return true;
    }

    public static List<Map<String, String>> getOffer(final int idGame, final int roundID) {
        Database db = Database.getInstance();

        String sql = "SELECT gd.* FROM gamedie gd"
                + " LEFT JOIN playerframefield pff ON gd.idgame = pff.idgame AND gd.dienumber = pff.dienumber AND gd.diecolor = pff.diecolor"
                + " WHERE gd.idgame = ? AND gd.roundtrack IS NULL AND gd.roundID = ? AND pff.idgame IS NULL";
        String[] params = {Integer.toString(idGame), Integer.toString(roundID)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getNewOffer(final int idGame, final int roundID, final int dieAmount) {
        Database db = Database.getInstance();

        String sql = "UPDATE gamedie SET roundID = ? "
                + "WHERE idgame = ? AND roundtrack IS NULL AND roundID IS NULL ORDER BY RAND() LIMIT "
                + Integer.toString(dieAmount);

        String[] params = {Integer.toString(roundID), Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getRoundTrack(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM gamedie WHERE idgame = ? AND eyes IS NOT NULL AND roundtrack IS NOT NULL";

        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static boolean putRoundTrack(final int idGame, final int roundID, final int dieNumber,
            final String dieColor) {
        Database db = Database.getInstance();

        String sql = "UPDATE gamedie SET roundtrack = ? WHERE idgame = ? AND dienumber = ? AND diecolor = ?;";

        String[] params = {Integer.toString(roundID), Integer.toString(idGame), Integer.toString(dieNumber),
                dieColor};

        db.exec(sql, params);

        return true;
    }

    public static int getGameDieEyes(final int idGame, final int dieNumber, final String dieColor) {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM gamedie WHERE idgame = ? AND dienumber = ? AND diecolor = ?;";
        String[] params = {Integer.toString(idGame), Integer.toString(dieNumber),
                ColorEnum.fromString(dieColor).getName()};
        if (db.exec(sql, params).size() > 0) {
            return Integer.valueOf(db.exec(sql, params).get(0).get("eyes"));
        } else {
            System.out.println("Iets is er fout gegaan bij de query");
            return 0;
        }

    }

}
