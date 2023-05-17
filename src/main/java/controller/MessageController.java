package main.java.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.model.Message;
import main.java.model.Player;

public class MessageController {

    private ViewController view;
    private String prefTimestamp;

    public MessageController(final ViewController view) {
        this.view = view;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.prefTimestamp = currentDateTime.format(formatter);
    }

    public boolean sendMessage(final String message) {
        boolean messageSent = false;
        Player player = view.getCurrentPlayer();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        if (!this.prefTimestamp.equals(formattedDateTime)) {
            messageSent = Message.createMessage(message, player.getId(), formattedDateTime);
        }

        this.prefTimestamp = formattedDateTime;
        return messageSent;
    }

    public ArrayList<Map<String, String>> getMessages(final int idGame) {
        ArrayList<Message> messages = Message.getChatMessages(idGame);
        ArrayList<Map<String, String>> messageList = new ArrayList<>();

        for (Message message : messages) {
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("username", message.getPlayerUsername());
            messageMap.put("message", message.getMessage());
            messageMap.put("time", message.getTime());
            messageList.add(messageMap);
        }

        return messageList;
    }
}
