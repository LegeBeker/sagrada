package main.java.model;

import main.java.db.MessageDB;

public class Message {

    public void addMessage(final String message, final int playerId) {

    }

    public void getMessages(final int playerId) {
        System.out.println(MessageDB.getMessages(playerId));
    }
}
