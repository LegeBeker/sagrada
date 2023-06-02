package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import main.java.controller.ViewController;
import main.java.model.Game;
import main.java.pattern.Observable;
import main.java.pattern.Observer;

public class GameButtonsView extends VBox implements Observer {

    private ViewController view;

    private Button buttonBack;
    private Button buttonGetOffer;
    private Button buttonEndTurn;
    private ToggleButton helpToggle;

    private static final int BUTTONWIDTH = 150;

    private static final int PADDING = 10;

    public GameButtonsView(final ViewController view) {
        this.view = view;

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefWidth(BUTTONWIDTH);
        this.buttonBack.setOnAction(e -> view.openGamesView());
        
        this.buttonGetOffer = new Button("Pak dobbelstenen");
        this.buttonGetOffer.setPrefWidth(BUTTONWIDTH);
        this.buttonGetOffer.setOnAction(e -> getNewOffer());

        this.helpToggle = new ToggleButton("Help!");
        this.helpToggle.setPrefWidth(BUTTONWIDTH);
        this.helpToggle.setOnAction(e -> {
            view.setHelpFunction();
            this.view.displayMessage("Help functie is " + (view.getHelpFunction() ? "aan" : "uit"));
        });

        this.buttonEndTurn = new Button("Einde beurt");
        this.buttonEndTurn.setPrefWidth(BUTTONWIDTH);
        this.buttonEndTurn.setOnAction(e -> endTurn());

        this.getChildren().addAll(this.buttonBack, this.helpToggle);
        Observable.addObserver(Game.class, this);

        this.update();

        this.setPadding(new Insets(PADDING));
        this.setSpacing(PADDING);
    }

    private void getNewOffer() {
        this.buttonGetOffer.setDisable(true);
        view.getNewOffer();
    }

    private void endTurn() {
        this.buttonEndTurn.setDisable(true);
        view.endTurn();
    }

    @Override
    public void update() {
        if (this.getChildren().contains(buttonEndTurn)) {
            this.getChildren().remove(buttonEndTurn);
        }
        if (view.isTurnPlayer() && !view.getOffer().isEmpty()) {
            this.getChildren().addAll(buttonEndTurn);
            this.buttonEndTurn.setOnMouseReleased(e -> this.buttonEndTurn.setDisable(false));
        }

        if (this.getChildren().contains(buttonGetOffer)) {
            this.getChildren().remove(buttonGetOffer);
        }
        if (view.getOffer().isEmpty() && view.isTurnPlayer()) {
            this.getChildren().addAll(buttonGetOffer);
            this.buttonGetOffer.setOnMouseReleased(e -> this.buttonEndTurn.setDisable(false));
        }
    }

}
