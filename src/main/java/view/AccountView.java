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

public class AccountView extends BorderPane {
    private Account acc;
    private ViewController view;

    private final double rowHeight = 50;
    private double rowWidth = 400;
    private final double paddingInsetValue = 8;

    public AccountView(final ViewController view, final Account account, final String type) {
        this.setMinHeight(rowHeight);
        this.setWidth(rowWidth);
        this.view = view;

        this.setStyle("-fx-border-style: solid inside;"
        + "-fx-border-width: 0 0 1 0;"
        + "-fx-border-color: black;");

        this.setPadding(new Insets(paddingInsetValue));
        this.acc = account;

        String text = acc.getUsername();
        Label l1 = new Label(text);
        this.setAlignment(l1, Pos.CENTER);

        this.setLeft(l1);
        if (type != null) {
            if (type == "invite") {
                if (acc.getUsername().equals(this.view.getAccountController().getAccount().getUsername())) {
                    this.setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
                } else {
                    Button inviteButton = new Button("Invite");
                    inviteButton.setOnAction(e -> invitePlayer(acc));
                    this.setRight(inviteButton);
                    this.setAlignment(inviteButton, Pos.CENTER);
                }

            } else if (type == "stats") {
                Button statsButton = new Button("Show stats");
                statsButton.setOnAction(e -> showPlayerStats(acc));
                this.setRight(statsButton);
                this.setAlignment(statsButton, Pos.CENTER);
            } else {
                System.out.println("Fout: Het meegestuurde type is niet herkend.");
            }
        }
    }

    public void resize(final double width, final double height) {
        super.resize(width, height);
        this.rowWidth = width;
        this.setWidth(this.rowWidth);
    }

    private void invitePlayer(final Account acc) {
        // -- @Someone, insert invite logic here
    }

    private void showPlayerStats(final Account acc) {
        // -- @Someone, insert player stats logic here
    }

}
