package main.java.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import main.java.db.MessageDB;
import main.java.pattern.Observable;

public class Message extends Observable {
    private String message;
    private int idPlayer;
    private String time;

    public String getMessage() {
        return message;
    }

    public Player getPlayer() {
        return Player.get(idPlayer);
    }

    public String getTime() {
        String inputDateTime = time;

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);
        String outputDateTime = dateTime.format(outputFormatter);

        return outputDateTime;
    }

    public static ArrayList<Message> getChatMessages(final int idGame) {
        ArrayList<Message> messages = new ArrayList<Message>();

        for (Map<String, String> messageMap : MessageDB.getChatMessages(idGame)) {
            Message message = new Message();
            message.message = messageMap.get("message");
            message.time = messageMap.get("time");
            message.idPlayer = Integer.parseInt(messageMap.get("idplayer"));
            messages.add(message);
        }

        return messages;
    }

    public static boolean createMessage(final String message, final Player player, final String time) {
        return MessageDB.createMessage(message, player, time);
    }

}
