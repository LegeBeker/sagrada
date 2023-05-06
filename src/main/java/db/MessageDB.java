package main.java.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.model.ChatLine;
import main.java.model.Message;
import main.java.model.Player;

public final class MessageDB {

    private MessageDB() {

    }

    // Create methods to add messages to the database
    public static List<Map<String, String>> createMessage(final String message, final Player player,
            final Timestamp time) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO chatline (idPlayer, time, message) VALUES (?, ?, ?);";
        String[] params = {Integer.toString(player.getId()), time.toString(), message};

        return db.exec(sql, params);
    }

    public static List<Map<String, String>> getMessages(final int idGame) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM chatline INNER JOIN player ON player.idplayer = chatline.idplayer WHERE idGame = ?;";
        String[] params = {Integer.toString(idGame)};

        return db.exec(sql, params);
    }

}
