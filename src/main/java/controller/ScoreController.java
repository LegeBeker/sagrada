package main.java.controller;

import main.java.model.Game;
import main.java.model.Player;

public class ScoreController {
    private int playerId;

    public void calculateScore(final Player player, final Game game, final String privateObjCardColor,
            final int favorTokens) {
        int score = -20;
        System.out.println("Calculating score for player " + player.getId() + " in game " + game.getId()
                + " with private objective card "
                + privateObjCardColor + " and " + favorTokens + " favor tokens.");

        score += favorTokens;
        System.out.println("current score: " + score);
        score += getAmountOfDice(player);
        System.out.println("Adding: " + getAmountOfDice(player));
        System.out.println("current score: " + score);
        System.out.println("Adding: " + getPrivateObjectiveScore(player));
        score += getPrivateObjectiveScore(player);
        System.out.println("current score: " + score);
    }

    private int getAmountOfDice(final Player player) {
        System.out.println("Amount of dice: " + player.getBoard().getAmountOfDice());
        return player.getBoard().getAmountOfDice();
    }

    private int getPrivateObjectiveScore(final Player player) {
        System.out.println("Private objective score: "
                + player.getBoard().getPrivateObjectiveCardScore(player.getPrivateObjCardColor().toUpperCase()));
        return player.getBoard().getPrivateObjectiveCardScore(player.getPrivateObjCardColor().toUpperCase());
    }

    private int getPublicObjectiveScore(final Player player) {
        System.out.println("Public objective score: " + player.getBoard().getPublicObjectiveCardScore());
        return player.getBoard().getPublicObjectiveCardScore(player.getGame().getObjectiveCardsIds());
    }

}
