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
    
    public static List<Map<String, String>> createGame(final String time){
        Database db = Database.getInstance();
        
        String sql = "INSERT INTO game (creationDate) VALUE(?);";
        String[] params = {time};
        
        return db.exec(sql, params);
    }
    
    public static List<Map<String, String>> getGameByTimestamp(final String time){
        Database db = Database.getInstance();
        
        String sql = "SELECT * FROM game WHERE creationdate = ?;";
        String[] params = {time};
        
        return db.exec(sql, params);
    }
    
}
