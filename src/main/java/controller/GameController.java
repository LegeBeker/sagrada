package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.model.Die;
import main.java.model.Game;
import main.java.model.ObjectiveCard;
import main.java.model.Player;

public final class GameController {

    private final ViewController view;

    private Game game;

    public GameController(final ViewController view) {
        this.view = view;
    }

    public ArrayList<Map<String, String>> getGames() {
        ArrayList<Game> games = Game.getAll();
        ArrayList<Map<String, String>> gamesMap = new ArrayList<Map<String, String>>();

        for (Game game : games) {
            Map<String, String> gameMap = new HashMap<String, String>();
            gameMap.put("id", Integer.toString(game.getId()));
            gameMap.put("turnPlayerUsername", game.getTurnPlayerUsername());
            gameMap.put("currentRound", Integer.toString(game.getCurrentRound()));
            gameMap.put("creationDateShow", game.getCreationDate());
            gamesMap.add(gameMap);
        }

        return gamesMap;
    }

    public Game getGame(final int gameId) {
        this.game = Game.get(gameId);
        return this.game;
    }

    public ArrayList<Player> getPlayers(final String username) {
        return game.getPlayers(username);
    }

    public ArrayList<Integer> getPlayerIds() {
        return game.getPlayerIds();
    }

    public ArrayList<Die> getOffer() {
        return this.game.getOffer();
    }

    public ArrayList<Integer> getObjectiveCardsIds() {
        return this.game.getObjectiveCardsIds();
    }

    public ObjectiveCard getObjectiveCard(final int id) {
        return ObjectiveCard.get(id);
    }

    public ArrayList<String> getToolCardsNames() {
        return this.game.getToolCardsNames();
    }

    public Game createGame(final ArrayList<String> accounts, final String currAccount, final boolean useDefaultCards) {
        return Game.createGame(accounts, currAccount, useDefaultCards);
    }

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer(game.getId(), view.getUsername());
    }

    public Boolean isTurnPlayer(final String username) {
        return this.game.getTurnPlayerUsername().equals(username);
    }

    public Boolean isTurnPlayer(final int gameId, final String username) {
        return Game.get(gameId).getTurnPlayerUsername().equals(username);
    }

    public void setHelpFunction() {
        this.game.setHelpFunction();
    }

    public boolean getHelpFunction() {
        return this.game.getHelpFunction();
    }

    public void getNewOffer() {
        game.getNewOffer();
    }

    public void endTurn() {
        game.endTurn();
    }

    public void choosePatternCard(final int idPatternCard) {
        getCurrentPlayer().choosePatternCard(idPatternCard, this.game.getId());
        getCurrentPlayer().createGameFavorTokens();
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public ArrayList<Die> getRoundTrack() {
        return Game.getRoundTrack(this.game.getId());
    }

    public Player getPlayer(final Integer playerId) {
        return Player.get(playerId);
    }

    public HashMap<Integer, List<Integer>> getPatternCardOptions() {
        HashMap<Integer, List<Integer>> patternCardOptions = new HashMap<Integer, List<Integer>>();

        patternCardOptions.put(getCurrentPlayer().getId(),
                getCurrentPlayer().getPatternCardOptions());

        return patternCardOptions;
    }

    public void acceptInvite(final int gameId) {
        Player.acceptInvite(gameId, view.getUsername());
    }

    public void refuseInvite(final int gameId) {
        Player.refuseInvite(gameId, view.getUsername());
    }
}
