package main.java.view;

import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameChatView extends VBox implements Observer {

    private VBox chatMessageBox = new VBox();
    private HBox chatInput = new HBox();

    private static final int WIDTHCHATVIEW = 300;
    private static final int WIDTHMESSAGEBOX = 200;

    private final ViewController view;
    private TextField textInput = new TextField();

    public GameChatView(final ViewController view) {
        this.view = view;
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setMaxWidth(WIDTHCHATVIEW);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");

        chatMessageBox.setMaxWidth(WIDTHMESSAGEBOX);
        chatMessageBox.setStyle("-fx-background-color: transparent;");

        ScrollPane chatMessageScrollPane = new ScrollPane(chatMessageBox);
        chatMessageScrollPane.setMaxWidth(view.getWidth());
        chatMessageScrollPane.setStyle("-fx-background: transparent;");

        chatMessageScrollPane.setFitToWidth(true);
        chatMessageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatMessageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        Observable.addObserver(Game.class, this);

        textInput.setPromptText("Typ hier je bericht");
        HBox.setHgrow(textInput, Priority.ALWAYS);
        Button sendButton = new Button("Verstuur");

        textInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendMessage(textInput.getText());
            }
        });

        sendButton.setOnAction(e -> {
            sendMessage(textInput.getText());
        });

        chatInput.getChildren().addAll(textInput, sendButton);
        chatInput.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(chatMessageScrollPane, chatInput);

        update();
    }

    public void sendMessage(final String message) {
        if (message.isEmpty() || !message.matches("^[a-zA-Z0-9?!., ]*$")) {
            textInput.setStyle("-fx-border-color: red;");
            view.displayError("Je bericht mag alleen letters, cijfers en leestekens bevatten.");
            return;
        }
        Boolean send = view.sendMessage(message);
        if (send) {
            textInput.setText("");
            textInput.setStyle(null);
        } else {
            textInput.setStyle("-fx-border-color: red;");
        }
        Observable.notifyObservers(Game.class);
    }

    public void addMessage(final String message, final String playerUsername, final String time) {
        chatMessageBox.getChildren()
                .add(new TextFlow(new Text("[" + time + "] "), new Text(playerUsername), new Text(": " + message)));
    }

    @Override
    public void update() {
        chatMessageBox.getChildren().clear();
        for (Map<String, String> message : view.getMessages()) {
            addMessage(message.get("message"), message.get("username"), message.get("time"));
        }
    }
}
