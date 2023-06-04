package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import main.java.enums.PlayStatusEnum;
import main.java.model.Player;

public class ScoreController {

    ArrayList<PlayStatusEnum> playStatusses = new ArrayList<PlayStatusEnum>();

    public ArrayList<Map<String, String>> getScores(final Player currentPlayer) {
        ArrayList<Map<String, String>> scores = new ArrayList<Map<String, String>>();
        for (Player player : currentPlayer.getGame().getPlayers(currentPlayer.getUsername())) {
            Map<String, String> score = new HashMap<String, String>();
            score.put("username", player.getUsername());
            score.put("color", player.getColor().toString());
            String playerScore = calculateScore(player, currentPlayer, playStatusses);
            score.put("score", playerScore);
            scores.add(score);
            if (currentPlayer.getPlayStatus().equals(PlayStatusEnum.CHALLENGER.toString())
                    && currentPlayer.getPlayStatus().equals(PlayStatusEnum.FINISHED.toString())) {
                player.updateScore(playerScore);
            }
        }

        return scores;
    }

    public String calculateScore(final Player player, final Player currentPlayer,
            final ArrayList<PlayStatusEnum> playStatusses) {
        int favorTokens = player.getFavorTokensLeft();
        int defaultScore = 0;

        if (player.getPlayStatus().equals(PlayStatusEnum.FINISHED.toString())
                || player.getId() == currentPlayer.getId()) {
            defaultScore += getPrivateObjectiveCardScore(player);
        }

        defaultScore += getPublicObjectiveCardScore(player);
        defaultScore += getEmptyPlaces(player);
        defaultScore += favorTokens;

        return Integer.toString(defaultScore);
    }

    public void updateScores(final Player currentPlayer) {
        for (Player player : currentPlayer.getGame().getPlayers(currentPlayer.getUsername())) {
            player.updateScore(calculateScore(player, currentPlayer, playStatusses));
        }
    }

    private int getEmptyPlaces(final Player player) {
        return player.getBoard().getEmptyPlaces();
    }

    private int getPrivateObjectiveCardScore(final Player player) {
        return player.getBoard().getPrivateObjectiveCardScore(player.getPrivateObjCardColor().toUpperCase());
    }

    private int getPublicObjectiveCardScore(final Player player) {
        return player.getBoard().getPublicObjectiveCardScore(player.getGame().getObjectiveCardsIds());
    }
}
