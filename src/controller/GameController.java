package controller;

import java.util.ArrayList;

import model.Game;

public class GameController {
    public ArrayList<Game> getGames() {
        return Game.getAll();
    }
}
