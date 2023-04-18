package main.java.db;

import java.util.List;
import java.util.Map;

public final class PlayerDB {
    private PlayerDB() {
    }

    public static Map<String, String> get(final int idPlayer) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player WHERE idplayer = ? LIMIT 1";
        String[] params = {Integer.toString(idPlayer)};

        return db.exec(sql, params).get(0);
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM player";

        return db.exec(sql, null);
    }
    
    public static List<Map<String, String>> createPlayer(final String username, final int idGame, final String playStatus, final String color){
        Database db = Database.getInstance();
        
        String sql = "INSERT INTO player (username, idgame, playstatus, private_objectivecard_color)"
                + "VALUE (?, ?, ?, ?);";
        String[] params = {username, Integer.toString(idGame), playStatus, color};
        
        return db.exec(sql, params);
    }
    
    public static List<Map<String, String>> getRecentPlayerFromGame(final int idGame){
        Database db = Database.getInstance();
        
        String sql = "SELECT * FROM player WHERE idplayer = (SELECT MAX(idplayer) FROM player WHERE idgame = ? LIMIT 1);";
        String[] params = {Integer.toString(idGame)};
        
        return db.exec(sql, params);
    }
    
}
