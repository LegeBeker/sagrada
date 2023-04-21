package main.java.controller;

import java.sql.Timestamp;
import java.util.List;

import main.java.model.Game;
import main.java.model.GameMessage;
import main.java.model.Player;
import main.java.pattern.Observer;

public class MessageController {
    private GameMessage message;

    public MessageController() {
        message.addObserver(this);
    }

    public void sendMessage(final String message, final ViewController view, final Game game) {
        String username = view.getAccountController().getAccount().getUsername();
        Player player = game.getPlayer(username);
        Timestamp time = new Timestamp(System.currentTimeMillis());

        this.message = new GameMessage(message, player, time);

    }

    public List<GameMessage> getMessages(final Game game) {
        return game.getMessages();
    }

}
