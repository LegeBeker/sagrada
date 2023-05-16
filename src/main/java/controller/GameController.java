package main.java.controller;

import java.util.ArrayList;

import main.java.model.Account;
import main.java.model.Game;
import main.java.model.PatternCard;
import main.java.model.Player;

public final class GameController {

    private final ViewController view;

    private Game game;

    public GameController(final ViewController view) {
        this.view = view;
    }

    public ArrayList<Game> getGames() {
        return Game.getAll();
    }

    public Game getGame(final int gameId) {
        this.game = Game.get(gameId);
        return this.game;
    }

    public ArrayList<Player> getPlayers(final String username) {
        return game.getPlayers(username);
    }

    public Game createGame(final ArrayList<Account> accounts, final Account currAccount,
            final boolean useDefaultCards) {
        return Game.createGame(accounts, currAccount, useDefaultCards);
    }

    public Player getCurrentPlayer(final int idGame) {
        Game game = Game.get(idGame);
        return game.getCurrentPlayer(idGame, view.getUsername());
    }

    public Boolean isTurnPlayer(final String username) {
        return this.game.getTurnPlayerUsername().equals(username);
    }

    public void setHelpFunction(){
        this.game.setHelpFunction();
    }

    public boolean getHelpFunction(){
        return this.game.getHelpFunction();
    }

    public void getNewOffer() {
        game.getNewOffer();
    }

    public void endTurn() {
        game.endTurn();
    }

    public void choosePatternCard(final PatternCard patternCard) {
        getCurrentPlayer(this.game.getId()).choosePatternCard(patternCard, this.game.getId());
        getCurrentPlayer(this.game.getId()).createGameFavorTokens();
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }
}
