package main.java.model;

import java.sql.Timestamp;

import main.java.db.MessageDB;

public class Message {

    public void sendMessage(final String message, final int idGame, final int playerId) {
        Timestamp time = new Timestamp(System.currentTimeMillis());
        MessageDB.addMessage(message, playerId, time);
    }

    public void addMessage(final String message, final int playerId) {

    }

    public void getMessages(final int playerId) {
        System.out.println(MessageDB.getMessages(playerId));
    }
}
