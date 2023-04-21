package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.model.GameMessage;

public final class MessageDB {

    private MessageDB() {

    }

    // Create methods to add messages to the database
    public static List<Map<String, String>> createMessage(final GameMessage gameMessage) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO chatline (idPlayer, time, message) VALUES (?, ?, ?);";
        String[] params = {
                String.valueOf(gameMessage.getIdPlayer()),
                String.valueOf(gameMessage.getTime()),
                gameMessage.getMessage()
        };

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getMessages(final int playerId) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM chatline WHERE idplayer = ?";
        String[] params = { Integer.toString(playerId) };

        return db.exec(sql, params);
    }

}
