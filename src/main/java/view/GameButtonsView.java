package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.FlowPane;
import main.java.controller.ViewController;
import main.java.model.Game;

public class GameButtonsView extends FlowPane {

    private ViewController view;

    private Button buttonBack;
    private Button buttonEndTurn;
    private Button buttonGetDice;
    private ToggleButton helpToggle;

    private final int padding = 10;

    public GameButtonsView(final ViewController view, final Game game) {
        this.view = view;

        this.buttonBack = new Button("Terug");

        this.buttonEndTurn = new Button("Einde beurt");

        this.buttonGetDice = new Button("Pak dobbelstenen");

        this.helpToggle = new ToggleButton("Help!");

        this.buttonEndTurn.setOnAction(e -> game.endTurn());

        this.buttonBack.setOnAction(e -> view.openGamesView());

        this.buttonGetDice.setOnAction(e -> game.getNewOffer());

        this.helpToggle.setOnAction(e -> {
            game.setHelpFunction();
        });

        this.getChildren().addAll(this.buttonBack, this.buttonGetDice, this.helpToggle);

        if (game.getTurnPlayer().getUsername().equals(view.getAccountController().getAccount().getUsername())) {
            this.getChildren().addAll(buttonEndTurn);
        }

        this.setPadding(new Insets(padding, padding, padding, padding));
        this.setHgap(padding);
        this.setVgap(padding);
    }

    public void getDice(final Game game) {
        try {
            game.getNewOffer();
        } catch (RuntimeException e) {
            this.view.displayError(e.getMessage());
        }
    }

}
