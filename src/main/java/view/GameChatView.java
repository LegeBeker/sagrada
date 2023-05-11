package main.java.view;

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
import main.java.model.Message;
import main.java.model.Player;
import main.java.pattern.Observer;

public class GameChatView extends VBox implements Observer {

    private VBox chatMessageBox = new VBox();
    private HBox chatInput = new HBox();

    private Game game;

    private static final int WIDTHCHATVIEW = 300;
    private static final int WIDTHMESSAGEBOX = 200;

    public GameChatView(final ViewController view, final Game game) {
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setMaxWidth(WIDTHCHATVIEW);
        this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
        this.game = game;

        chatMessageBox.setMaxWidth(WIDTHMESSAGEBOX);
        chatMessageBox.setStyle("-fx-background-color: transparent;");

        ScrollPane chatMessageScrollPane = new ScrollPane(chatMessageBox);
        chatMessageScrollPane.setMaxWidth(view.getWidth());
        chatMessageScrollPane.setStyle("-fx-background: transparent;");

        // set background to transparent
        chatMessageScrollPane.setFitToWidth(true);
        chatMessageScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatMessageScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        game.addObserver(this);

        TextField textInput = new TextField();
        textInput.setPromptText("Typ hier je bericht");
        HBox.setHgrow(textInput, Priority.ALWAYS);
        Button sendButton = new Button("Verstuur");

        textInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Boolean send = view.getMessageController().sendMessage(textInput.getText(), view, game);
                if (send) {
                    textInput.setText("");
                    textInput.setStyle(null);
                } else {
                    textInput.setStyle("-fx-border-color: red;");
                }
            }
        });

        sendButton.setOnAction(e -> {
            Boolean send = view.getMessageController().sendMessage(textInput.getText(), view, game);
            if (send) {
                textInput.setText("");
                textInput.setStyle(null);
            } else {
                textInput.setStyle("-fx-border-color: red;");
            }
        });

        chatInput.getChildren().addAll(textInput, sendButton);
        chatInput.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(chatMessageScrollPane, chatInput);

        update();
    }

    public void addMessage(final String message, final Player player, final String time) {
        Text username = new Text(player.getUsername());
        chatMessageBox.getChildren().add(new TextFlow(new Text("[" + time + "] "), username, new Text(": " + message)));
    }

    @Override
    public void update() {
        chatMessageBox.getChildren().clear();
        for (Message message : Message.getChatMessages(game.getId())) {
            addMessage(message.getMessage(), message.getPlayer(), message.getTime());
        }
    }
}
