package main.java.controller;

import main.java.model.Game;

public class MessageController {
    private String username;
    private int idGame;
    private int idPlayer;

    public void sendMessage(final String message, final ViewController view, final Game game) {
        username = view.getAccountController().getAccount().getUsername();
        idGame = game.getId();
        idPlayer = game.getPlayer(username).getId();

        System.out.println(idPlayer);

        // Message.sendMessage(message, game, idPlayer);
    }

}
