package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import main.java.controller.ViewController;

public class GameButtonsView extends VBox {

    private ViewController view;

    private Button buttonBack;
    private Button buttonEndTurn;
    private Button buttonGetDice;
    private ToggleButton helpToggle;

    private static final int BUTTONWIDTH = 150;

    private static final int PADDING = 10;

    public GameButtonsView(final ViewController view) {
        this.view = view;

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefWidth(BUTTONWIDTH);
        this.buttonBack.setOnAction(e -> view.openGamesView());

        this.buttonGetDice = new Button("Pak dobbelstenen");
        this.buttonGetDice.setPrefWidth(BUTTONWIDTH);
        this.buttonGetDice.setOnAction(e -> view.getNewOffer());

        this.helpToggle = new ToggleButton("Help!");
        this.helpToggle.setPrefWidth(BUTTONWIDTH);
        this.helpToggle.setOnAction(e -> {
            view.setHelpFunction();
            this.view.displayMessage("Help functie is " + (view.getHelpFunction() ? "aan" : "uit"));
        });

        this.buttonEndTurn = new Button("Einde beurt");
        this.buttonEndTurn.setPrefWidth(BUTTONWIDTH);
        this.buttonEndTurn.setOnAction(e -> view.getGameController().requestEndTurn());

        this.getChildren().addAll(this.buttonBack, this.buttonGetDice, this.helpToggle);

        if (view.isTurnPlayer()) {
            this.getChildren().addAll(buttonEndTurn);
        }

        this.setPadding(new Insets(PADDING));
        this.setSpacing(PADDING);
    }

    public void getDice() {
        try {
            view.getNewOffer();
        } catch (RuntimeException e) {
            this.view.displayError(e.getMessage());
        }
    }

}
