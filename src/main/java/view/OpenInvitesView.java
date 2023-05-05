package main.java.view;

import java.util.Collections;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;
import main.java.model.Player;

public class OpenInvitesView extends VBox {

    private ViewController view;
    private Account account;
    private StackPane textTitle;
    private TableView<Player> table;

    private final int padding = 200;
    private final int spacing = 15;
    private final int tableHeight = 400;
    private final int tableWidth = 600;

    public OpenInvitesView(final ViewController view, final Account account) {
        this.view = view;
        this.account = account;

        this.setBackground(view.getBackground());
        this.setAlignment(Pos.CENTER);

        Text text = new Text("Uw uitnodigingen");
        text.setStyle("-fx-font-size: 40px");
        text.setStroke(Color.web("#000000"));
        text.setFill(Color.web("#ffffff"));

        this.textTitle = new StackPane(text);
        this.textTitle.setPadding(new Insets(0, 0, this.spacing, 0));

        this.table = new TableView<Player>();
        this.table.setPlaceholder(new Text("Geen uitnodigingen gevonden"));
        this.table.setMaxHeight(this.tableHeight);
        this.table.setMaxWidth(this.tableWidth);

        TableColumn<Player, Integer> playerIdColumn = new TableColumn<>("Player ID");
        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("idPlayer"));
        TableColumn<Player, Integer> gameIdColumn = new TableColumn<>("Game ID");
        gameIdColumn.setCellValueFactory(new PropertyValueFactory<>("idGame"));

        Collections.addAll(this.table.getColumns(), playerIdColumn, gameIdColumn);

        for (Player player : view.getPlayerController()
                .getInvites(view.getAccountController().getAccount().getUsername())) {
            System.out.println("Adding playerID: " + player.getId());
            this.table.getItems().add(player);
        }

        this.getChildren().addAll(this.textTitle, this.table);
    }

}
