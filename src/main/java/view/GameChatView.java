package main.java.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
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

    private ViewController view;
    private Game game;

    private static final int WIDTHCHATVIEW = 300;
    private static final int WIDTHMESSAGEBOX = 200;

    public GameChatView(final ViewController view, final Game game) {
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setMaxWidth(WIDTHCHATVIEW);
        chatMessageBox.setMaxWidth(WIDTHMESSAGEBOX);
        this.view = view;
        this.game = game;

        game.addObserver(this);

        TextField textInput = new TextField();
        textInput.setPromptText("Typ je geweldige bericht hier");
        HBox.setHgrow(textInput, Priority.ALWAYS);
        Button sendButton = new Button("Verstuur");

        textInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                view.getMessageController().sendMessage(textInput.getText(), view, game);
                textInput.setText("");
            }
        });

        sendButton.setOnAction(e -> {
            Boolean send = view.getMessageController().sendMessage(textInput.getText(), view, game);
            if (send) {
                textInput.setText("");
            } else {
                textInput.setStyle("-fx-border-color: red;");
            }
        });

        chatInput.getChildren().addAll(textInput, sendButton);
        chatInput.setAlignment(Pos.CENTER_RIGHT);

        ScrollPane chatMessageScrollPane = new ScrollPane(chatMessageBox);
        chatMessageScrollPane.setBackground(null);
        chatMessageScrollPane.setMaxWidth(view.getWidth());

        this.getChildren().add(0, chatMessageScrollPane);
        this.getChildren().add(1, chatInput);

        update();
    }

    public void addMessage(final String message, final Player player, final String time) {
        FlowPane chatMessage;
        chatMessage = new FlowPane();
        chatMessage.setBackground(null);
        Text username = new Text(player.getUsername());
        username.setFill(player.getColor());
        chatMessageBox.getChildren().add(username);
        // chatMessageBox.getChildren().add(new TextFlow(new Text("[" + time + "]"), username, new Text(": " + message)));
    }

    @Override
    public void update() {
        chatMessageBox.getChildren().clear();
        for (Message message : Message.getChatMessages(game.getId())) {
            addMessage(message.getMessage(), message.getPlayer(), message.getTime());
        }
    }
}
