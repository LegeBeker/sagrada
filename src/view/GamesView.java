package view;

import java.util.Collections;

import controller.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Game;

public class GamesView extends VBox {

    private ViewController view;

    private StackPane textTitle;

    private StackPane scrollBox;

    private final int scrollBoxHeight = 300;
    private final int scrollBoxWidth = 400;

    private TableView<Game> table;

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

        Text text = new Text("Alle Spellen");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(0, 0, this.spacing, 0));

        this.table = new TableView<Game>();

        TableColumn<Game, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Game, String> turnPlayerCol = new TableColumn<>("Beurt Speler");
        turnPlayerCol.setCellValueFactory(new PropertyValueFactory<>("turnPlayerUsername"));

        TableColumn<Game, Integer> roundCol = new TableColumn<>("Ronde");
        roundCol.setCellValueFactory(new PropertyValueFactory<>("currentRound"));

        TableColumn<Game, String> dateCol = new TableColumn<>("Datum");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("creationDateShow"));

        Collections.addAll(this.table.getColumns(), idCol, turnPlayerCol, roundCol, dateCol);

        for (Game game : view.getGameController().getGames()) {
            this.table.getItems().add(game);
        }

        this.table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Game game = this.table.getSelectionModel().getSelectedItem();
                this.view.openGameView(game);
            }
        });

        this.scrollBox = new StackPane();

        this.scrollBox.getChildren().add(table);

        this.scrollBox.setPrefSize(scrollBoxWidth, scrollBoxHeight);
        this.scrollBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));

        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefWidthProperty().bind(this.scrollBox.widthProperty());

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

        this.getChildren().addAll(this.textTitle, this.scrollBox, this.boxButtons);
    }

    private void back() {
        this.view.openMenuView();
    }

    private void openNewGameView() {
        this.view.openNewGameView();
    }
}