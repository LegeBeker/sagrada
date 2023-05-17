package main.java.view;

import java.util.Collections;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class AccountsView extends TableView<String> {
    private ViewController view;

    private static final double TABLEHEIGHT = 400;

    public AccountsView(final ViewController view) {
        this.view = view;

        this.setPlaceholder(new Text("Geen accounts gevonden"));
        this.setMaxHeight(TABLEHEIGHT);

        TableColumn<String, String> idUsername = new TableColumn<>("Username");
        idUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        Collections.addAll(this.getColumns(), idUsername);

        for (String acc : view.getInviteableAccountsUsernames()) {
            this.getItems().add(acc);
        }

        setTableClickEvent();
    }

    private void setTableClickEvent() {
        this.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String acc = this.getSelectionModel().getSelectedItem();
                this.view.openStatView(acc);
            }
        });
    }
}
