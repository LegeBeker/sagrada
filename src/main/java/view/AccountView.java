package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Account;
import main.java.model.Game;

public class AccountView extends BorderPane {
    private Account acc;
    private ViewController view;
    private Game game;

    private final double rowHeight = 50;
    private final double rowWidth = 150;
    private final double paddingInsetValue = 8;
    private final double minWidth = 200;

    public AccountView(final ViewController view, final Account account) {
        generateAccountView(view, account);
        generateButton();
    }

    public AccountView(final ViewController view, final Account account, final Game game) {
        generateAccountView(view, account);
        generateButton(game);
    }

    private void generateAccountView(final ViewController view, final Account account) {
        this.setMinHeight(rowHeight);
        this.setMaxWidth(rowWidth);
        this.setMinWidth(minWidth);
        this.view = view;

        this.setStyle("-fx-border-style: solid inside;"
                + "-fx-border-width: 0 0 1 0;"
                + "-fx-border-color: black;");

        // this.setBackground(new Background(new BackgroundFill(Color.RED, null,
        // null)));
        this.setPadding(new Insets(paddingInsetValue));
        this.acc = account;

        String text = acc.getUsername();
        Label l1 = new Label(text);
        this.setAlignment(l1, Pos.CENTER);
        this.setLeft(l1);
    }

    private void generateButton(Game game) {
        this.game = game;
        if (this.game != null) {
            if (acc.getUsername().equals(this.view.getAccountController().getAccount().getUsername())) {
                this.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
            } else {
                Button inviteButton = new Button("Invite");
                inviteButton.setOnAction(e -> invitePlayer(acc));
                this.setRight(inviteButton);
                this.setAlignment(inviteButton, Pos.CENTER);
            }
        }
    }

    private void generateButton() {
        Button statsButton = new Button("Show stats");
        statsButton.setOnAction(e -> showPlayerStats(acc));
        this.setRight(statsButton);
        this.setAlignment(statsButton, Pos.CENTER);
    }

    public void resize(final double width, final double height) {
        super.resize(width, height);
        // this.rowWidth = width;
        this.setWidth(width);
    }

    private void invitePlayer(final Account acc) {
        // -- @Someone, insert invite logic here
    }

    private void showPlayerStats(final Account acc) {
        // -- @Someone, insert player stats logic here
    }

}
