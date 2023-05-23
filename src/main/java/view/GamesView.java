package main.java.view;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class GamesView extends VBox {

    private ViewController view;

    private StackPane textTitle;

    private StackPane scrollBox;

    private static final int SCROLLBOXHEIGHT = 300;
    private static final int SCROLLBOXWIDTH = 400;

    private TableView<Map<String, String>> table;

    private Map<Integer, Boolean> gamesWithOpenInvites;

    private HBox boxButtons;

    private Button buttonBack;
    private Button buttonNewGame;

    private static final int BUTTONHEIGHT = 25;
    private static final int BUTTONWIDTH = 200;

    private static final int PADDING = 200;
    private static final int SPACING = 15;

    public GamesView(final ViewController view) {
        this.view = view;
        this.setBackground(view.getBackground());

        this.setAlignment(Pos.CENTER);

        Text text = new Text("Alle Spellen");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.gamesWithOpenInvites = view.getGamesWithOpenInvites();

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(0, 0, SPACING, 0));

        this.table = new TableView<Map<String, String>>();

        this.table.setPlaceholder(new Text("Geen spellen gevonden"));

        TableColumn<Map<String, String>, String> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("idgame")));

        TableColumn<Map<String, String>, String> turnPlayerCol = new TableColumn<>("Beurt Speler");
        turnPlayerCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().get("username")));

        TableColumn<Map<String, String>, String> roundCol = new TableColumn<>("Ronde");
        roundCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("current_roundID")));

        TableColumn<Map<String, String>, String> dateCol = new TableColumn<>("Datum");
        dateCol.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().get("formatted_creationdate")));

        Collections.addAll(this.table.getColumns(), idCol, turnPlayerCol, roundCol, dateCol);

        for (Map<String, String> game : view.getGamesList()) {
            this.table.getItems().add(game);
        }

        this.table.setRowFactory(tv -> new TableRow<Map<String, String>>() {
            @Override
            protected void updateItem(final Map<String, String> game, final boolean empty) {
                super.updateItem(game, empty);

                if (game != null) {
                    System.out.println(
                            "Parsed game(" + game.get("idgame") + ":) "
                                    + Boolean.parseBoolean(game.get("isPlayerInGame")));
                    System.out.println(game.get("isPlayerInGame"));
                }

                if (game == null) {
                    setStyle("");
                } else if (view.getUsername().equals(game.get("username"))) {
                    setStyle("-fx-background-color: lightblue;");
                } else if (gamesWithOpenInvites.containsKey(Integer.parseInt(game.get("idgame")))
                        && gamesWithOpenInvites.get(Integer.parseInt(game.get("idgame")))) {
                    setStyle("-fx-background-color: orange;");
                } else if (!Boolean.parseBoolean(game.get("isPlayerInGame"))) {
                    setDisable(true);
                } else {
                    setStyle("");
                }

            }
        });

        this.table.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Map<String, String> game = this.table.getSelectionModel().getSelectedItem();

                if (game != null) {
                    if (gamesWithOpenInvites.containsKey(Integer.parseInt(game.get("idgame")))
                            && gamesWithOpenInvites.get(Integer.parseInt(game.get("idgame")))) {
                        showInviteAlert(Integer.parseInt(game.get("idgame")));
                    } else if (gamesWithOpenInvites.containsKey(Integer.parseInt(game.get("idgame")))
                            && view.playerHasChosenPatternCard(Integer.parseInt(game.get("idgame")),
                                    view.getUsername())) {
                        view.displayError("Niet alle spelers hebben de uitnodiging geaccepteerd");
                    } else {
                        this.view.openGameView(Integer.parseInt(game.get("idgame")));
                    }
                }
            }
        });

        this.scrollBox = new StackPane();

        this.scrollBox.getChildren().add(table);

        this.scrollBox.setPrefSize(SCROLLBOXWIDTH, SCROLLBOXHEIGHT);
        this.scrollBox.setBackground(new Background(new BackgroundFill(Color.web("#ffffff"), null, null)));

        this.scrollBox.setPadding(new Insets(0, 0, 0, 0));

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.prefWidthProperty().bind(this.scrollBox.widthProperty());

        this.buttonBack = new Button("Terug");
        this.buttonBack.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonBack.setOnAction(e -> this.view.openMenuView());

        this.buttonNewGame = new Button("Nieuwe potje starten");
        this.buttonNewGame.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        this.buttonNewGame.setOnAction(e -> this.view.openNewGameView());

        this.boxButtons = new HBox();
        this.boxButtons.getChildren().addAll(this.buttonBack, this.buttonNewGame);

        this.boxButtons.setAlignment(Pos.CENTER);
        this.boxButtons.setSpacing(SPACING);
        this.boxButtons.setPadding(new Insets(SPACING, 0, SPACING, 0));

        this.setPadding(new Insets(0, PADDING, 0, PADDING));

        this.getChildren().addAll(this.textTitle, this.scrollBox, this.boxButtons);

    }

    private void showInviteAlert(final int gameId) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uitnodiging");
        alert.setHeaderText("Bevestiging");
        alert.setContentText(
                "Je bent uitgenodigd voor een spel. Door op accepteren te klikken, doe je mee aan het spel.");

        ButtonType acceptButton = new ButtonType("Accepteren");
        ButtonType refuseButton = new ButtonType("Weigeren");
        ButtonType closeButton = new ButtonType("Sluiten", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(acceptButton, refuseButton, closeButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (!result.isPresent()) {
            return;
        }

        if (result.get() == acceptButton) {
            view.acceptInvite(gameId);
            view.openGamesView();
        } else if (result.get() == refuseButton) {
            view.refuseInvite(gameId);
            view.openGamesView();
        }
    }
}
