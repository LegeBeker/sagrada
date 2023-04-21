package main.java.model;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import main.java.db.MessageDB;
import main.java.pattern.Observable;

public class GameMessage {
    private SimpleStringProperty message;
    private SimpleIntegerProperty idPlayer;
    private Timestamp time;
    private Player player;

    public GameMessage(final String message, final Player player, final Timestamp time) {
        this.message = new SimpleStringProperty(message);
        this.time = time;
        this.idPlayer = new SimpleIntegerProperty(player.getId());
        this.player = player;

        MessageDB.createMessage(this);
    }

    public String getMessage() {
        return message.get();
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    public int getIdPlayer() {
        return idPlayer.get();
    }

    public SimpleIntegerProperty idPlayerProperty() {
        return idPlayer;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public Player getPlayer() {
        return this.player;
    }

}
