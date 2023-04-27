package main.java.model;

import java.sql.Timestamp;

public class GameMessage {
    private String message;
    private int idPlayer;
    private Timestamp time;

    GameMessage(final String message, final int idPlayer, final Timestamp time) {
        this.message = message;
        this.idPlayer = idPlayer;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public Timestamp getTime() {
        return time;
    }

}
