package main.java.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.java.controller.ViewController;

public class InvitesView extends HBox {

    private AccountsView accountsView;

    private TableView<String> selectionTable;
    private ArrayList<String> selectedAccountsUsernames = new ArrayList<String>();
    private static final double SELECTIONTABLEHEIGHT = 100;
    private static final int MAXSIZESELECTION = 3;

    public InvitesView(final ViewController view) {
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
        this.accountsView.setPlaceholder(new Text("Er is niemand om uit te nodigen"));
    }

    public ArrayList<String> getSelectedAccountsUsernames() {
        return this.selectedAccountsUsernames;
    }

    private void setTableClickEvent() {
        this.accountsView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String username = this.accountsView.getSelectionModel().getSelectedItem();
                if (username != null && (!selectedAccountsUsernames.contains(username)
                        && selectedAccountsUsernames.size() < MAXSIZESELECTION)) {
                    selectedAccountsUsernames.add(username);
                    this.selectionTable.getItems().add(username);
                }
            }
        });

        this.selectionTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                String username = this.selectionTable.getSelectionModel().getSelectedItem();
                selectedAccountsUsernames.remove(username);
                this.selectionTable.getItems().remove(username);
            }
        });
    }

    public void update() {
        this.accountsView.update();
    }
}
