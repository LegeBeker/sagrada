package main.java.db;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.model.GameMessage;
import main.java.model.Player;

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

    public static List<GameMessage> getMessages(final int idGame) {
        Database db = Database.getInstance();
        List<GameMessage> gameMessages = new ArrayList<>();
        List<Map<String, String>> messageMaps;

        String sql = "SELECT * FROM chatline INNER JOIN player ON player.idplayer = chatline.idplayer WHERE idGame = ?;";
        String[] params = { Integer.toString(idGame) };

        messageMaps = db.exec(sql, params);

        for (Map<String, String> messageMap : messageMaps) {
            int idPlayer = Integer.parseInt(messageMap.get("idPlayer"));
            Timestamp time = Timestamp.valueOf(messageMap.get("time"));
            String message = messageMap.get("message");

            gameMessages.add(new GameMessage(message, Player.get(idPlayer), time));
        }

        return gameMessages;
    }

}
