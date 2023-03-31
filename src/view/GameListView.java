package view;

import controller.ViewController;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;

public class GameListView extends FlowPane {

    private ViewController view;
    private Game game;

    private Text textId;
    private Text textTurnPlayer;
    private Text textRoundNumber;

    private Text textDate;

    private final int height = 50;

    public GameListView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;

        this.setBackground(new Background(new BackgroundFill(Color.web("#73b4f5"), null, null)));
        this.setPrefHeight(height);

        this.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.textId = new Text("Id: " + this.game.getId());

        // this.textTurnPlayer = new Text("Beurtspeler: " + this.game.getTurnPlayer().getUsername());
        this.textTurnPlayer = new Text("Beurtspeler: " + this.game.getTurnPlayer());

        this.textRoundNumber = new Text("Ronde: " + this.game.getCurrentRound());

        this.textDate = new Text("Datum: " + this.game.getCreationDate());

        this.getChildren().addAll(this.textId, this.textTurnPlayer, this.textRoundNumber, this.textDate);

    }
}
