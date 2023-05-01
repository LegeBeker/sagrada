package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.controller.ViewController;
import main.java.model.Account;

public class NewGameView extends HBox {

    private ViewController view;
    private ToggleGroup cards;
    private ArrayList<Account> accounts;
    private InvitesView playerList;

    private final int spacing = 10;
    private final int maxPlayers = 4;
    private final double minWidth = 400;

    public NewGameView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());
        final String textStyle = "-fx-font-size: 20px";
        final String labelStyle = "-fx-font-weight: bold";

        Label title = new Label(" Nieuw spel aanmaken ");
        title.setStyle(textStyle + "; " + labelStyle);
        title.setTextFill(Color.ALICEBLUE);

        cards = new ToggleGroup();

        RadioButton standardRb = new RadioButton("Standaard");
        standardRb.setText("Standaard");
        standardRb.setToggleGroup(cards);
        standardRb.setSelected(true);
        standardRb.setTextFill(Color.ALICEBLUE);

        RadioButton randomRb = new RadioButton("Willekeurig");
        randomRb.setText("Willekeurig");
        randomRb.setToggleGroup(cards);
        randomRb.setTextFill(Color.ALICEBLUE);

        Button create = new Button("Maak nieuw spel aan");
        create.setStyle(textStyle);
        create.setOnAction(e -> createGame());

        Button back = new Button("Terug");
        back.setStyle(textStyle);
        back.setOnAction(e -> goBack());

        VBox buttonPane = new VBox();
        buttonPane.getChildren().setAll(title, standardRb, randomRb, create, back);

        this.playerList = new InvitesView(this.view);
        this.playerList.setMinWidth(minWidth);

        buttonPane.setSpacing(spacing);
        buttonPane.setAlignment(Pos.CENTER);

        this.getChildren().addAll(buttonPane, this.playerList);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(spacing));
        this.setSpacing(spacing);
    }

    private void createGame() {
        RadioButton setting = (RadioButton) this.cards.getSelectedToggle();
        String settingText = setting.getText();

        accounts = playerList.getSelectedAccounts();
        boolean useDefaultCards = true;
        if (settingText.equals("Willekeurig")) {
            useDefaultCards = false;
        }
        if (accounts.size() <= 0) {
            view.displayError("Selecteer meer spelers");
            return;
        }
        if (accounts.size() > maxPlayers - 1) {
            view.displayError("Selecteer minder spelers");
            return;
        }

        this.view.openGameView(view.getGameController().createGame(this.accounts, useDefaultCards));
    }

    private void goBack() {
        this.view.openGamesView();
    }
}
