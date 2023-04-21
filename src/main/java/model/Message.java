package main.java.model;

import java.sql.Timestamp;

import com.mysql.cj.conf.StringProperty;

import javafx.beans.property.SimpleStringProperty;
import main.java.db.MessageDB;

public class Message {
    private SimpleStringProperty message;

    public Message(final String message) {
        this.message = new SimpleStringProperty(message);
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
}
