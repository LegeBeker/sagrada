package main.java.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    private VBox chatBox = new VBox();
    private FlowPane chatMessage = new FlowPane();
    private HBox chatInput = new HBox();

    private final int buttonRatio = 3;

    public GameChatView(final ViewController view, final Game game) {
        this.setBackground(background);

        System.out.println(this.getPrefHeight());

        TextField textInput = new TextField();
        textInput.setPromptText("Typ je geweldige bericht hier");
        HBox.setHgrow(textInput, Priority.ALWAYS);

        Button sendButton = new Button("Verstuur");

        chatInput.getChildren().addAll(textInput, sendButton);
        chatBox.getChildren().add(0, chatInput);

        this.getChildren().add(chatBox);
    }

}
