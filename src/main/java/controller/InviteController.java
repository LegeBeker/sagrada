package main.java.controller;

import java.util.ArrayList;

import main.java.model.Invite;
import main.java.model.PatternCard;

public class InviteController {

    public ArrayList<Invite> getInvites(final int cardId) {
        return Invite.getInvites();
    }
}
