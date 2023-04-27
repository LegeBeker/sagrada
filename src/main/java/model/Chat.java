package main.java.model;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Chat {
    private List<GameMessage> messages = new ArrayList<GameMessage>();
    private ObservableList<GameMessage> observableList = FXCollections.observableList(messages);

    public ArrayList<GameMessage> getMessages() {
        return (ArrayList<GameMessage>) messages;
    }

    public void addMessage(final GameMessage message) {
        if (message == null) {
            return;
        }
        observableList.add(message);
    }

    public void clear() {
        observableList.clear();
    }

    public final ObservableList<GameMessage> getObservableList() {
        return observableList;
    }
}
