package main.java.controller;

import java.util.ArrayList;

import main.java.model.Account;
import main.java.model.Game;
import main.java.model.Player;

public final class GameController {

    private final ViewController view;

    public GameController(final ViewController view) {
        this.view = view;
    }

    public ArrayList<Game> getGames() {
        return Game.getAll();
    }

    public Game getGame(final int gameId) {
        return Game.get(gameId);
    }

    public Game createGame(final ArrayList<Account> accounts, final Account currAccount, final boolean useDefaultCards) {
        return Game.createGame(accounts, currAccount, useDefaultCards);
    }

    public Player getCurrentPlayer(final int idGame) {
        Game game = Game.get(idGame);
        return game.getCurrentPlayer(idGame, this.view.getAccountController().getAccount().getUsername());
    }
}
