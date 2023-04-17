package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import main.java.db.PlayerDB;

public class Invite {

    private String username;
    private int idGame;
    private String playStatus;

    public static ArrayList<Invite> getInvites() {
        ArrayList<Invite> invites = new ArrayList<Invite>();

        for (Map<String, String> inviteMap : PlayerDB.getInvites()) {
            Invite game = mapToInvite(inviteMap);
            invites.add(game);
        }

        return invites;
    }

    public static Invite mapToInvite(final Map<String, String> inviteMap) {
        Invite invite = new Invite();

        invite.username = inviteMap.get("username");
        invite.idGame = Integer.parseInt(inviteMap.get("idgame"));
        invite.playStatus = inviteMap.get("playstatus");

        return invite;
    }

}
