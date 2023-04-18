package main.java.controller;

import java.util.ArrayList;

import main.java.model.Invite;

public class InviteController {

    public ArrayList<Invite> getInvites(final String player) {
        return Invite.getInvites(player);
    }
}
