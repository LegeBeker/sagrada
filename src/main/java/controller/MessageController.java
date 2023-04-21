package main.java.controller;

import java.sql.Timestamp;

import main.java.model.Game;
import main.java.model.GameMessage;

public class MessageController {
    private String username;
    private int idGame;
    private int idPlayer;

    private GameMessage message;

    private Timestamp time;

    public void sendMessage(final String message, final ViewController view, final Game game) {
        username = view.getAccountController().getAccount().getUsername();
        idGame = game.getId();
        idPlayer = game.getPlayer(username).getId();

        time = new Timestamp(System.currentTimeMillis());

        this.message = new GameMessage(message, idPlayer, time);
    }

}
