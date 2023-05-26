package main.java.controller;

import java.util.List;
import java.util.Map;

import main.java.model.FavorToken;

public class FavorTokenController {
    private ViewController view;

    public FavorTokenController(final ViewController view) {
        this.view = view;
    }


    public List<Map<String, String>> getFavorTokensForToolCard(final int toolCardId, final int gameId){

        return FavorToken.getFavorTokensForToolCard(toolCardId, gameId);

    }

}