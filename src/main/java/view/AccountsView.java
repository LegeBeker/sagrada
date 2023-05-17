package main.java.view;

import java.util.Collections;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;

public class AccountsView extends TableView<Account> {
    private ViewController view;

    private static final double TABLEHEIGHT = 400;

    public AccountsView(final ViewController view) {
        this.view = view;

        this.setPlaceholder(new Text("Geen accounts gevonden"));
        this.setMaxHeight(TABLEHEIGHT);

        TableColumn<Account, String> idUsername = new TableColumn<>("Username");
        idUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        Collections.addAll(this.getColumns(), idUsername);

        for (Account acc : view.getInviteableAccounts()) {
            this.getItems().add(acc);
        }

        setTableClickEvent();
    }

    private void setTableClickEvent() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Account acc = this.getSelectionModel().getSelectedItem();
                this.view.openStatView(acc.getUsername());
            }
        });
    }
}
