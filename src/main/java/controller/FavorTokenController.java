package main.java.controller;

import java.util.ArrayList;

import main.java.model.FavorToken;

public class FavorTokenController {
    private ViewController view;

    public FavorTokenController(final ViewController view) {
        this.view = view;
    }


    public ArrayList<FavorToken> getFavorTokensForToolCard(final int toolCardId, final int gameId){
        
        return FavorToken.getFavorTokensForToolCard(toolCardId, gameId);

    }

}
