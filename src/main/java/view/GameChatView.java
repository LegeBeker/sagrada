package main.java.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameChatView extends VBox {

    private final Background background = new Background(new BackgroundFill(Color.web("#B00322"), null, null));

    private VBox chatMessageBox = new VBox();
    private HBox chatInput = new HBox();

    private String username;

    public GameChatView(final ViewController view, final Game game) {
        this.setBackground(background);

        this.setAlignment(Pos.BOTTOM_CENTER);

        // Chat input
        TextField textInput = new TextField();
        textInput.setPromptText("Typ je geweldige bericht hier");
        HBox.setHgrow(textInput, Priority.ALWAYS);
        Button sendButton = new Button("Verstuur");

        // Input handling
        textInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                view.getMessageController().sendMessage(textInput.getText(), view, game);
                textInput.setText("");
            }
        });

        sendButton.setOnAction(e -> {
            view.getMessageController().sendMessage(textInput.getText(), view, game);

            textInput.setText("");
        });

        chatInput.getChildren().addAll(textInput, sendButton);
        chatInput.setAlignment(Pos.CENTER_RIGHT);

        ScrollPane chatMessageScrollPane = new ScrollPane(chatMessageBox);
        chatMessageScrollPane.setBackground(null);
        chatMessageScrollPane.setMaxWidth(view.getWidth());
        // Remove the color of the chatMessageScrollPane

        this.getChildren().add(0, chatMessageScrollPane);
        this.getChildren().add(1, chatInput);
    }

    private void setupListener(final ViewController view) {
        view.getMessageController().
    }

    public void addMessage(final String message, final String username, final Boolean sender) {
        FlowPane chatMessage;
        chatMessage = new FlowPane();
        chatMessage.setBackground(null);
        chatMessage.getChildren().add(new Text(username));
        chatMessage.getChildren().add(new Text(message));

        if (sender) {
            chatMessage.setAlignment(Pos.CENTER_RIGHT);
        } else {
            chatMessage.setAlignment(Pos.CENTER_LEFT);
        }
        System.out.println(this.getWidth());

        chatMessageBox.getChildren().add(chatMessage);
    }
}
