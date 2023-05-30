package main.java.controller;

import java.util.ArrayList;

import main.java.model.Game;
import main.java.model.Player;

public class ScoreController {
    private int playerId;

    public void calculateScore(final Player player, final Game game, final String privateObjCardColor,
            final int favorTokens) {
        int score = -20;
        System.out.println("Calculating score for player " + player.getId() + " in game " + game.getId()
                + " with private objective card "
                + privateObjCardColor + " and " + favorTokens + " favor tokens."); // DEBUG

        score += favorTokens;
        System.out.println("Adding favortokens: " + favorTokens + " to score: " + score); // DEBUG
        score += getAmountOfDice(player);
        System.out.println("Adding amount of dice: " + getAmountOfDice(player) + " to score: " + score); // DEBUG
        score += getPrivateObjectiveScore(player);
        System.out.println("Adding privObjScore: " + getPrivateObjectiveScore(player) + " to score: " + score); // DEBUG

        player.setScore(score);
    }

    public int calculateEndScore() {

    }

    private int getAmountOfDice(final Player player) {
        System.out.println("Amount of dice: " + player.getBoard().getAmountOfDice()); // DEBUG remove later
        return player.getBoard().getAmountOfDice();
    }

    private int getPrivateObjectiveScore(final Player player) {
        System.out.println("Private objective score: "
                + player.getBoard().getPrivateObjectiveCardScore(player.getPrivateObjCardColor().toUpperCase())); // DEBUG
                                                                                                                  // remove
                                                                                                                  // later
        return player.getBoard().getPrivateObjectiveCardScore(player.getPrivateObjCardColor().toUpperCase());
    }

    private int getPublicObjectiveScore(final Player player) {
        System.out.println("Public objective score: "
                + player.getBoard().getPublicObjectiveCardScore(player.getGame().getObjectiveCardsIds())); // DEBUG
                                                                                                           // remove
                                                                                                           // later
        return player.getBoard().getPublicObjectiveCardScore(player.getGame().getObjectiveCardsIds());
    }
}
