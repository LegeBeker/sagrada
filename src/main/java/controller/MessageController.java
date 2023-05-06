package main.java.controller;

import java.sql.Timestamp;

import main.java.model.Game;
import main.java.model.Message;
import main.java.model.Player;

public class MessageController {

    private ViewController view;

    public MessageController(final ViewController view) {
        this.view = view;
    }

    public void sendMessage(final String message, final ViewController view, final Game game) {
        String username = view.getAccountController().getAccount().getUsername();
        Player player = game.getPlayer(username);
        Timestamp time = new Timestamp(System.currentTimeMillis());

        Message.createMessage(message, player, time);
    }
}
