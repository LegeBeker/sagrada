package main.java.db;

import java.util.List;
import java.util.Map;

public final class GameDB {
    private GameDB() {
    }

    public static Map<String, String> get(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM game WHERE idgame = ? LIMIT 1";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params).get(0);
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM game";

        return db.exec(sql, null);
    }

    public static List<Map<String, String>> getPlayers(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player WHERE idgame = ?";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> createGame(final String time) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO game (creationDate, current_roundID) VALUE(?, 1);";
        String[] params = {time};

        db.exec(sql, params);

        sql = "SELECT * FROM game WHERE creationdate = ?;";

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getColors(final int playerCount) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM color ORDER BY RAND() LIMIT " + Integer.toString(playerCount) + ";";

        return db.exec(sql, null);
    }

    public static List<Map<String, String>> setTurnPlayer(final int gameID, final int playerID) {
        Database db = Database.getInstance();

        String sql = "UPDATE game SET turn_idplayer = ? WHERE idgame = " + Integer.toString(gameID) + ";";
        String[] params = {Integer.toString(playerID)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> setRound(final int gameID, final int round) {
        Database db = Database.getInstance();

        String sql = "UPDATE game SET current_roundID = ? WHERE idgame = " + Integer.toString(gameID) + ";";
        String[] params = {Integer.toString(round)};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> assignToolcards(final int gameID) {
        Database db = Database.getInstance();

        String sql = "SELECT idtoolcard FROM toolcard ORDER BY RAND() LIMIT 3;";
        List<Map<String, String>> toolcardIDs = db.exec(sql, null);

        String[] params = new String[1];
        sql = "INSERT INTO gametoolcard (idtoolcard, idgame) VALUE (?, " + Integer.toString(gameID) + ");";
        for (Map<String, String> toolcardMap : toolcardIDs) {
            params[0] = toolcardMap.get("idtoolcard");
            db.exec(sql, params);
        }

        sql = "SELECT * FROM gametoolcard WHERE idgame = " + Integer.toString(gameID) + ";";
        List<Map<String, String>> gametoolcardIDs = db.exec(sql, null);

        return gametoolcardIDs;
    }

    public static List<Map<String, String>> assignPublicObjectivecards(final int gameID) {
        Database db = Database.getInstance();

        String sql = "SELECT idpublic_objectivecard FROM public_objectivecard ORDER BY RAND() LIMIT 3;";
        List<Map<String, String>> publicObjectivecardIDs = db.exec(sql, null);

        String[] params = new String[1];
        sql = "INSERT INTO gameobjectivecard_public VALUE (" + Integer.toString(gameID) + ", ?);";
        for (Map<String, String> publicObjectivecardMap : publicObjectivecardIDs) {
            params[0] = publicObjectivecardMap.get("idpublic_objectivecard");
            db.exec(sql, params);
        }
        sql = "SELECT * FROM gameobjectivecard_public WHERE idgame = " + Integer.toString(gameID) + ";";
        return db.exec(sql, null);
    }
}
