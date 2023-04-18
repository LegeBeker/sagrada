package main.java.controller;

import java.util.ArrayList;

import main.java.model.Account;
import main.java.model.Game;

public class GameController {
    public ArrayList<Game> getGames() {
        return Game.getAll();
    }

    public Game getGame(final int gameId) {
        return Game.get(gameId);
    }
    
    public Game createGame(final ArrayList<Account> accounts, final boolean useDefaultCards) {
        return Game.createGame(accounts, useDefaultCards);
    }
}
