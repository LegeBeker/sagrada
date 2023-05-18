package main.java.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class AccountsView extends TableView<Map<String, String>> {
    private ViewController view;

    private ArrayList<String> accountUsernames;

    private static final double TABLEHEIGHT = 400;

    public AccountsView(final ViewController view, final Boolean isInvite) {
        this.view = view;

        this.setPlaceholder(new Text("Geen accounts gevonden"));
        this.setMaxHeight(TABLEHEIGHT);

        TableColumn<Map<String, String>, String> idUsername = new TableColumn<>("Gebruikersnaam");
        idUsername.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("username")));

        TableColumn<Map<String, String>, String> idAmountGamesWon = new TableColumn<>("Aantal gewonnen spellen");
        idAmountGamesWon.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("amount_won_games")));

        Collections.addAll(this.getColumns(), idUsername, idAmountGamesWon);

        if (isInvite) {
            this.accountUsernames = this.view.getInviteableAccountsUsernames();
        } else {
            this.accountUsernames = this.view.getAccountsUsernames();
        }

        for (Map<String, String> acc : view.getAccounts()) {
            this.getItems().add(acc);
        }

        setTableClickEvent();
    }

    private void setTableClickEvent() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Map<String, String> acc = this.getSelectionModel().getSelectedItem();
                this.view.openStatView(acc.get("username"));
            }
        });
    }
}
