package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.model.Player;

public class ScoreController {

    public ArrayList<Map<String, String>> getScores(final Player currentPlayer){
        ArrayList<Map<String, String>> scores = new ArrayList<Map<String, String>>();

        for (Player player : currentPlayer.getGame().getPlayers(currentPlayer.getUsername())) {
            Map<String, String> score = new HashMap<String, String>();
            score.put("username", player.getUsername());
            score.put("color", player.getColor().toString());
            score.put("score", calculateScore(player, currentPlayer));
            scores.add(score);
        }

        return scores;
    }    

    public String calculateScore(final Player player, final Player currentPlayer) {
        int favorTokens = player.getFavorTokensLeft();
        int defaultScore = -20;

        if (player.getGame().getCurrentRound() == 10 || player.getId() == currentPlayer.getId()) {
            defaultScore += getPrivateObjectiveCardScore(player);
        } 

        defaultScore += getPublicObjectiveCardScore(player);
        defaultScore += getAmountOfDice(player);
        defaultScore += favorTokens;

        return Integer.toString(defaultScore);
    }

    private int getAmountOfDice(final Player player) {
        return player.getBoard().getAmountOfDice();
    } 

    private int getPrivateObjectiveCardScore(final Player player) {
        return player.getBoard().getPrivateObjectiveCardScore(player.getPrivateObjCardColor().toUpperCase());
    }

    private int getPublicObjectiveCardScore(final Player player) {
        return player.getBoard().getPublicObjectiveCardScore(player.getGame().getObjectiveCardsIds());
    }
}
