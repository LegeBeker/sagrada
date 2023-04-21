package main.java.db;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class MessageDB {
    // Create methods to add messages to the database
    public static void addMessage(final String message, final int playerId, final Timestamp time) {
        // Add message to database
    }

    public static List<Map<String, String>> getMessages(final int playerId) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM chatline WHERE idplayer = ?";
        String[] params = { Integer.toString(playerId) };

        return db.exec(sql, params);
    }

}
