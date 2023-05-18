package main.java.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class InvitesView extends HBox {
    private ViewController view;

    private AccountsView accountsView;

    private TableView<String> selectionTable;
    private ArrayList<String> selectedAccountsUsernames = new ArrayList<String>();
    private static final double SELECTIONTABLEHEIGHT = 120;
    private static final int MAXSIZESELECTION = 3;

    public InvitesView(final ViewController view) {
        this.view = view;

        this.accountsView = new AccountsView(view, true);

        this.alignmentProperty().set(Pos.CENTER);

        this.selectionTable = new TableView<String>();
        this.selectionTable.setPlaceholder(new Text("Geen accounts geselecteerd"));
        this.selectionTable.setMaxHeight(SELECTIONTABLEHEIGHT);

        TableColumn<String, String> idUsernameSelected = new TableColumn<>("Username");
        idUsernameSelected.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));
        Collections.addAll(this.selectionTable.getColumns(), idUsernameSelected);

        setTableClickEvent();
        this.getChildren().addAll(this.accountsView, this.selectionTable);

        // this.accountsView.setRowFactory(tv -> new TableRow<Map<String, String>>() {
        //     @Override
        //     protected void updateItem(final String acc, final boolean empty) {
        //         super.updateItem(acc, empty);
        //         if (acc == null) {
        //             return;
        //         }
        //         if (selectedAccountsUsernames.contains(acc)) {
        //             this.setVisible(false);
        //             this.setManaged(false);
        //         } else {
        //             this.setVisible(true);
        //             this.setManaged(true);
        //         }
        //     }
        // });
    }

    public ArrayList<String> getSelectedAccountsUsernames() {
        return this.selectedAccountsUsernames;
    }

    private void setTableClickEvent() {
        this.accountsView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Map<String, String> acc = this.accountsView.getSelectionModel().getSelectedItem();
                String username = acc.get("username");
                if (username == null) {
                    return;
                }
                if (this.view.getUsername().equals(username)) {
                    this.view.displayError("Je kan jezelf niet uitnodigen");
                    return;
                }
                if (!selectedAccountsUsernames.contains(username) && selectedAccountsUsernames.size() < MAXSIZESELECTION) {
                    selectedAccountsUsernames.add(username);
                    this.selectionTable.getItems().add(username);
                }
            }
        });
        this.selectionTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String acc = this.selectionTable.getSelectionModel().getSelectedItem();
                selectedAccountsUsernames.remove(acc);
                this.selectionTable.getItems().remove(acc);
            }
        });
    }
}
