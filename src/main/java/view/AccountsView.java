package main.java.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class AccountsView extends TableView<String> {
    private ViewController view;

    private ArrayList<String> accountUsernames;

    private static final double TABLEHEIGHT = 400;

    public AccountsView(final ViewController view, final Boolean isInvite) {
        this.view = view;

        this.setPlaceholder(new Text("Geen accounts gevonden"));
        this.setMaxHeight(TABLEHEIGHT);

        TableColumn<String, String> idUsername = new TableColumn<>("Gebruikersnaam");
        idUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        Collections.addAll(this.getColumns(), idUsername);

        if (isInvite) {
            this.accountUsernames = this.view.getInviteableAccountsUsernames();
        } else {
            this.accountUsernames = this.view.getAccountsUsernames();

            TableColumn<String, String> idAmountGamesWon = new TableColumn<>("Aantal gewonnen spellen");
            idAmountGamesWon.setCellValueFactory(
                    cellData -> new SimpleStringProperty(view.getAccountWonGames(cellData.getValue())));

            this.getColumns().add(idAmountGamesWon);
        }

        for (String username : this.accountUsernames) {
            this.getItems().add(username);
        }

        setTableClickEvent();
    }

    private void setTableClickEvent() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String username = this.getSelectionModel().getSelectedItem();
                this.view.openStatView(username);
            }
        });
    }
}
