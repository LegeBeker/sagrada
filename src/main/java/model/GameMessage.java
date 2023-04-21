package main.java.model;

import java.sql.Timestamp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import main.java.db.MessageDB;

public class GameMessage {
    private SimpleStringProperty message;
    private SimpleIntegerProperty idPlayer;
    private Timestamp time;

    public GameMessage(final String message, final int idPlayer, final Timestamp time) {
        this.message = new SimpleStringProperty(message);
        this.time = time;
        this.idPlayer = new SimpleIntegerProperty(idPlayer);

        MessageDB.createMessage(this);
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(final String message) {
        this.message.set(message);
    }

    public SimpleStringProperty messageProperty() {
        return message;
    }

    public int getIdPlayer() {
        return idPlayer.get();
    }

    public void setIdPlayer(final int idPlayer) {
        this.idPlayer.set(idPlayer);
    }

    public SimpleIntegerProperty idPlayerProperty() {
        return idPlayer;
    }

    public Timestamp getTime() {
        return this.time;
    }

    public void setTime(final Timestamp time) {
        this.time = time;
    }

}
