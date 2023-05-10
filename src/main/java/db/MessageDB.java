package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.model.Player;

public final class MessageDB {

    private MessageDB() {
    }
    // Create methods to add messages to the database
    public static boolean createMessage(final String message, final Player player,
            final String time) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO chatline (idPlayer, time, message) VALUES (?, ?, ?);";
        String[] params = {Integer.toString(player.getId()), time.toString(), message};

        db.exec(sql, params);

        return true;

    }

    public static List<Map<String, String>> getChatMessages(final int gameId) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM chatline INNER JOIN player ON player.idplayer = chatline.idplayer WHERE player.idgame = ? ORDER BY time DESC;";
        String[] params = {Integer.toString(gameId)};

        return db.exec(sql, params);
    }
}
