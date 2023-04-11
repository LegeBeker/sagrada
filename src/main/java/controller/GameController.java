package main.java.controller;

import java.util.ArrayList;

import main.java.model.Game;
import main.java.model.Player;

public class GameController {
    public ArrayList<Game> getGames() {
        return Game.getAll();
    }

    public Game getGame(final int gameId) {
        return Game.get(gameId);
    }
    
    public Game createGame(final ArrayList<Player> players, final boolean useDefaultCards) {
        return Game.createGame(players, useDefaultCards);
    }
}
