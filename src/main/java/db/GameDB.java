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

        String sql = "INSERT INTO game (creationDate, current_roundID) VALUE(?, 0);";
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
        
        String sql = "UPDATE game SET current_roundID = ? WHERE idgame = " + Integer.toString(gameID) + ";";
        String[] params = {Integer.toString(playerID)};
        
        return db.exec(sql, params);
    }
    
    public static List<Map<String, String>> setRound(final int gameID, final int round) {
        Database db = Database.getInstance();
        
        String sql = "UPDATE game SET turn_idplayer = ? WHERE idgame = " + Integer.toString(gameID) + ";";
        String[] params = {Integer.toString(round)};
        
        return db.exec(sql, params);
    }

}
