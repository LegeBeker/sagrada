package main.java.db;

import java.util.List;
import java.util.Map;

public final class GameFavorTokenDB {
    private GameFavorTokenDB() {
    }

    public static List<Map<String, String>> getFromGame(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM gamefavortoken WHERE idgame = ?";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getFromPlayer(final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM gamefavortoken WHERE idplayer = ?";
        String[] params = {Integer.toString(idPlayer)};

        return db.exec(sql, params);
    }

  public static int getHighestIdFromGame(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT idfavortoken FROM gamefavortoken WHERE idgame = ? ORDER BY idfavortoken ASC;";
        String[] params = {Integer.toString(idGame)};

        List<Map<String, String>> result = db.exec(sql, params);

        if (result.size() == 0) {
            return 0;
        }

        return Integer.parseInt(result.get(0).get("idfavortoken"));
    }

    public static List<Map<String, String>> createGameFavorToken(final int idGame) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO gamefavortoken (idfavortoken, idgame) "
        + "SELECT temp.idfavortoken, 1 "
        + "FROM (SELECT IFNULL(MAX(idfavortoken), 0) + 1 AS idfavortoken FROM gamefavortoken) AS temp;";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> assignGameFavorToken(final int idFavorToken,
            final int idGame, final int idGameToolCard, final int roundID) {
        Database db = Database.getInstance();

        String sql = "UPDATE gamefavortoken SET gametoolcard = ?, roundID = ? WHERE idfavortoken = ? AND idgame = ?;";
        String[] params = {Integer.toString(idGameToolCard), Integer.toString(roundID), Integer.toString(idFavorToken),
                Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> assignGameFavorTokenToPlayer(final int idGame, final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "UPDATE gamefavortoken SET idplayer = ? WHERE idfavortoken = "
        + "(SELECT temp.idfavortoken "
        + "FROM (SELECT MIN(idfavortoken) AS idfavortoken FROM gamefavortoken WHERE idplayer IS NULL AND idgame = ?) AS temp);";
        String[] params = {Integer.toString(idPlayer), Integer.toString(idGame)};

        return db.exec(sql, params);
    }
}
