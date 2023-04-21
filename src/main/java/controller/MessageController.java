package main.java.controller;

import main.java.model.Game;
import main.java.model.Message;

public class MessageController {
    private String username;
    private int idGame;

    public void sendMessage(final String message, final ViewController view, final Game game) {
        Message.sendMessage(message, game.getIdGame(), view.getAccount().getIdPlayer());
    }

}
