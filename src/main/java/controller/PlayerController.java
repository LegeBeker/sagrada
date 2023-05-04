package main.java.controller;

import java.util.ArrayList;

import main.java.model.Player;

public class PlayerController {
    
    public Player getPlayer(final int playerId) {
        return Player.get(playerId);
    }

    public ArrayList<Player> getInvites(final String username){
        return Player.getInvites(username);
    }

}
