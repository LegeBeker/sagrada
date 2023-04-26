package main.java.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.java.controller.GameController;
import main.java.controller.ViewController;
import main.java.model.Account;

public class NewGameView extends HBox {

    private ViewController view;
    private ToggleGroup cards;
    private ArrayList<Account> accounts;

    private final int spacing = 10;
    private final int maxPlayers = 4;

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

        // TODO choose players here, I gave you the space
        ScrollPane playerList = new ScrollPane();
        final double scrollPaneWidth = view.getWidth() * 0.65; // TODO something like (view.getWidth() -
                                                               // buttonPaneWidth* - spacing * 2). *bPW is 0 at the
                                                               // moment
        playerList.setPrefWidth(scrollPaneWidth);
        accounts = new ArrayList<Account>();

        buttonPane.setSpacing(spacing);
        buttonPane.setAlignment(Pos.CENTER);

        this.getChildren().addAll(buttonPane, playerList);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(spacing));
        this.setSpacing(spacing);
    }

    private void createGame() {
        RadioButton setting = (RadioButton) this.cards.getSelectedToggle();
        String settingText = setting.getText();

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

        // TODO add current account to accounts

        new GameController().createGame(this.accounts, useDefaultCards);
        // TODO go to the newly made game
    }

    private void goBack() {
        this.view.openGamesView();
    }
}
