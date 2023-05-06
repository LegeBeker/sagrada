package main.java.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import main.java.db.MessageDB;
import main.java.pattern.Observable;

public class Message extends Observable {
    private String message;
    private String username;
    private Timestamp time;
    private boolean senderIsLoggedInPlayer;

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public Timestamp getTime() {
        return time;
    }

    public static ArrayList<Message> getChatMessages(final int idGame) {
        ArrayList<Message> messages = new ArrayList<Message>();

        for (Map<String, String> messageMap : MessageDB.getChatMessages(idGame)) {
            Message message = new Message();
            message.message = messageMap.get("message");
            message.time = Timestamp.valueOf(messageMap.get("time"));
            message.username = messageMap.get("username");
            messages.add(message);
        }

        return messages;

    }

    public static boolean createMessage(final String message, final Player player, final Timestamp time) {
        return MessageDB.createMessage(message, player, time);
    }

}
