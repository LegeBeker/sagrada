package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
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

    public List<Map<String, String>> getGamesList() {
        return Game.getGamesList();
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

    public ArrayList<Map<String, String>> getOffer() {
        ArrayList<Die> dice = this.game.getOffer();
        ArrayList<Map<String, String>> diceMap = new ArrayList<Map<String, String>>();

        for (Die die : dice) {
            Map<String, String> dieMap = new HashMap<String, String>();
            dieMap.put("eyes", Integer.toString(die.getEyes()));
            dieMap.put("color", die.getColor().toString());
            dieMap.put("number", Integer.toString(die.getNumber()));
            diceMap.add(dieMap);
        }

        return diceMap;
    }

    public ArrayList<Map<String, String>> getRoundTrack() {
        ArrayList<Die> dice = Game.getRoundTrack(this.game.getId());
        ArrayList<Map<String, String>> diceMap = new ArrayList<Map<String, String>>();

        for (Die die : dice) {
            Map<String, String> dieMap = new HashMap<String, String>();
            dieMap.put("eyes", Integer.toString(die.getEyes()));
            dieMap.put("color", die.getColor().toString());
            dieMap.put("number", Integer.toString(die.getNumber()));
            dieMap.put("roundtrack", Integer.toString(die.getRoundTrack()));
            diceMap.add(dieMap);
        }

        return diceMap;
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

    public Color getPlayerColor(final int playerId, final String username) {
        return game.getPlayerColor(playerId, username);
    }

    public Boolean isTurnPlayer(final String username) {
        return this.game.getTurnPlayerUsername().equals(username);
    }

    public Boolean isTurnPlayer(final int gameId, final String username) {
        return Game.get(gameId).getTurnPlayerUsername().equals(username);
    }

    public Boolean isTurnPlayer(final int playerId) {
        return this.game.getTurnPlayerId() == playerId;
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

    public void choosePatternCard(final int idPatternCard) {
        getCurrentPlayer().choosePatternCard(idPatternCard, this.game.getId());
        getCurrentPlayer().createGameFavorTokens();
    }

    public void endTurn() {
        getGame().endTurn();
    }

    public void setGame(final Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    public Player getPlayer(final Integer playerId) {
        return game.getPlayer(playerId);
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

    public boolean playerHasChosenPatternCard(final int gameId, final String username) {
        Game game = Game.get(gameId);
        return game.playerHasChosenPatternCard(username);
    }

    public Map<Integer, Boolean> getGamesWithOpenInvites() {
        return Game.getGamesWithOpenInvites(view.getUsername());
    }

    public void updateGame() {
        this.game = Game.get(this.game.getId());
    }
}
