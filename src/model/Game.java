package model;

import java.util.ArrayList;

import db.GameDB;

public class Game {
    private int idGame;

    // private Player turnPlayer;
    private int turnPlayer;
    private int currentRound;

    private String creationDate;

    public static ArrayList<Game> getAll() {
        ArrayList<ArrayList<String>> gamesDB = GameDB.getAll();

        ArrayList<Game> games = new ArrayList<Game>();

        for (ArrayList<String> arrayList : gamesDB) {
            Game game = new Game();

            game.idGame = Integer.parseInt(arrayList.get(0));
            // game.turnPlayer = new Player.get(Integer.parseInt(arrayList.get(1)));
            game.turnPlayer = Integer.parseInt(arrayList.get(1));
            game.currentRound = Integer.parseInt(arrayList.get(2));
            game.creationDate = arrayList.get(3);

            games.add(game);
        }

        return games;
    }

    public int getId() {
        return this.idGame;
    }

    // public Player getTurnPlayer() {
    public int getTurnPlayer() {
        return this.turnPlayer;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public String getCreationDate() {
        return this.creationDate;
    }
}
