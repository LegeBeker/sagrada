package view;

import java.util.ArrayList;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;

public class GamesView extends VBox {

    private ViewController view;

    private StackPane textTitle;

    private HBox boxInfo;

    private final int boxInfoHeight = 10;

    private ScrollPane scrollBox;

    private VBox boxGames;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 400;

    private ArrayList<Game> games;

    private HBox boxButtons;

    private Button buttonBack;
    private Button buttonNewGame;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int padding = 200;
    private final int spacing = 15;

    private final double widthFraction = 1.0 / 6.0;

    public GamesView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        Text text = new Text("Alle Spellen");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(0, 0, this.spacing, 0));

        this.boxInfo = new HBox();
        this.boxInfo.setAlignment(Pos.CENTER);
        this.boxInfo.setBackground(new Background(new BackgroundFill(Color.web("#4591de"), null, null)));
        this.boxInfo.setPrefSize(this.scrollBoxWidth, this.boxInfoHeight);

        Text idText = new Text("Id");
        idText.setFill(Color.WHITE);
        StackPane idTextBox = new StackPane(idText);
        idTextBox.prefWidthProperty().bind(this.boxInfo.widthProperty().multiply(widthFraction));
        idTextBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        Text playerText = new Text("Beurtspeler");
        playerText.setFill(Color.WHITE);
        StackPane playerTextBox = new StackPane(playerText);
        playerTextBox.prefWidthProperty().bind(this.boxInfo.widthProperty().multiply(2 * widthFraction));
        playerTextBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        Text roundText = new Text("Ronde");
        roundText.setFill(Color.WHITE);
        StackPane roundTextBox = new StackPane(roundText);
        roundTextBox.prefWidthProperty().bind(this.boxInfo.widthProperty().multiply(widthFraction));
        roundTextBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        Text dateText = new Text("Datum");
        dateText.setFill(Color.WHITE);
        StackPane dateTextBox = new StackPane(dateText);
        dateTextBox.prefWidthProperty().bind(this.boxInfo.widthProperty().multiply(2 * widthFraction));
        dateTextBox.setStyle("-fx-border-color: white; -fx-border-width: 1px;");

        this.boxInfo.getChildren().addAll(idTextBox, playerTextBox, roundTextBox, dateTextBox);
        this.boxInfo.setPadding(new Insets(0, 0, 0, 0));

        HBox.setHgrow(idTextBox, Priority.ALWAYS);
        HBox.setHgrow(playerTextBox, Priority.ALWAYS);
        HBox.setHgrow(roundTextBox, Priority.ALWAYS);
        HBox.setHgrow(dateTextBox, Priority.ALWAYS);

        this.scrollBox = new ScrollPane();

        this.games = view.getGameController().getGames();

        this.boxGames = new VBox();
        this.boxGames.setAlignment(Pos.CENTER);
        this.boxGames.prefWidthProperty().bind(this.scrollBox.widthProperty());

        for (Game game : this.games) {
            this.boxGames.getChildren().add(new GameListView(this.view, game));
        }

        this.scrollBox.setContent(this.boxGames);

        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);
        this.scrollBox.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scrollBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));

        // fill full width of scrollbox with boxGames
        this.boxGames.prefWidthProperty().bind(this.scrollBox.widthProperty());

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonBack.setOnAction(e -> this.back());

        this.buttonNewGame = new Button("Nieuwe potje starten");
        this.buttonNewGame.setPrefSize(this.buttonWidth, this.buttonHeight);
        this.buttonNewGame.setOnAction(e -> this.openNewGameView());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack, this.buttonNewGame);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(this.spacing);
        this.boxButtons.setPadding(new Insets(this.spacing, 0, this.spacing, 0));

        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(this.textTitle, this.boxInfo, this.scrollBox, this.boxButtons);
    }

    private void back() {
        this.view.openMenuView();
    }

    private void openNewGameView() {
        this.view.openNewGameView();
    }
}
