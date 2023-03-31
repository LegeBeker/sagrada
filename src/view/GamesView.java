package view;

import java.util.ArrayList;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;

public class GamesView extends VBox {

    private ViewController view;

    private Text textTitle;

    private ScrollPane scrollBox;

    private VBox boxGames;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 600;

    private ArrayList<Game> games;

    private HBox boxButtons;

    private Button buttonBack;
    private Button buttonNewGame;

    private final int buttonHeight = 25;
    private final int buttonWidth = 200;

    private final int padding = 200;
    private final int spacing = 15;

    public GamesView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        this.textTitle = new Text("Alle Spellen");
        this.textTitle.setStyle("-fx-font-size: 40px");
        this.textTitle.setStroke(Color.web("#000000"));
        this.textTitle.setFill(Color.web("#ffffff"));

        this.scrollBox = new ScrollPane();

        this.games = view.getGameController().getGames();

        this.boxGames = new VBox();

        for (Game game : this.games) {
            this.boxGames.getChildren().add(new GameListView(this.view, game));
        }

        this.scrollBox.setContent(this.boxGames);

        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);

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

        this.setSpacing(this.spacing);
        this.setPadding(new Insets(0, this.padding, 0, this.padding));

        this.getChildren().addAll(this.textTitle, this.scrollBox, this.boxButtons);
    }

    private void back() {
        this.view.openMenuView();
    }

    private void openNewGameView() {
        this.view.openNewGameView();
    }
}
