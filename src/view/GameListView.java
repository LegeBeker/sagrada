package view;

import controller.ViewController;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;

public class GameListView extends HBox {

    private ViewController view;
    private Game game;

    private Text textId;
    private Text textTurnPlayer;
    private Text textRoundNumber;

    private Text textDate;

    private final int height = 50;

    private final double widthFraction = 1.0 / 6.0;

    public GameListView(final ViewController view, final Game game) {
        this.view = view;
        this.game = game;

        this.setBackground(new Background(new BackgroundFill(Color.web("#73b4f5"), null, null)));
        this.setPrefHeight(this.height);

        this.setMinWidth(Region.USE_PREF_SIZE);

        this.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.textId = new Text(Integer.toString(this.game.getId()));
        this.textId.setFill(Color.WHITE);
        StackPane textIdBox = new StackPane(this.textId);
        textIdBox.prefWidthProperty().bind(this.widthProperty().multiply(widthFraction).subtract(100));
        textIdBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.textTurnPlayer = new Text(this.game.getTurnPlayer().getUsername());
        this.textTurnPlayer.setFill(Color.WHITE);
        StackPane textTurnPlayerBox = new StackPane(this.textTurnPlayer);
        textTurnPlayerBox.prefWidthProperty().bind(this.widthProperty().multiply(2 * widthFraction).subtract(100));
        textTurnPlayerBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.textRoundNumber = new Text(Integer.toString(this.game.getCurrentRound()));
        this.textRoundNumber.setFill(Color.WHITE);
        StackPane textRoundNumberBox = new StackPane(this.textRoundNumber);
        textRoundNumberBox.prefWidthProperty().bind(this.widthProperty().multiply(widthFraction).subtract(100));
        textRoundNumberBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.textDate = new Text(this.game.getCreationDate());
        this.textDate.setFill(Color.WHITE);
        StackPane textDateBox = new StackPane(this.textDate);
        textDateBox.prefWidthProperty().bind(this.widthProperty().multiply(2 * widthFraction).subtract(100));
        textDateBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.getChildren().addAll(textIdBox, textTurnPlayerBox, textRoundNumberBox, textDateBox);
        this.setAlignment(Pos.CENTER);

        HBox.setHgrow(textIdBox, Priority.ALWAYS);
        HBox.setHgrow(textTurnPlayerBox, Priority.ALWAYS);
        HBox.setHgrow(textRoundNumberBox, Priority.ALWAYS);
        HBox.setHgrow(textDateBox, Priority.ALWAYS);

        HBox.setHgrow(this, Priority.ALWAYS);

        this.setOnMouseClicked(e -> this.openGameView());
    }

    public void update() {
        this.textTurnPlayer.setText("Beurtspeler: " + this.game.getTurnPlayer());
        this.textRoundNumber.setText("Ronde: " + this.game.getCurrentRound());
    }

    public void openGameView() {
        this.view.openGameView(this.game);
    }
}
