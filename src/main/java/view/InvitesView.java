package main.java.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;

public class InvitesView extends HBox {
    private ViewController view;

    private AccountsView accountsView;

    private TableView<Account> selectionTable;
    private ArrayList<Account> selectedAccounts = new ArrayList<Account>();
    private static final double SELECTIONTABLEHEIGHT = 120;
    private static final int MAXSIZESELECTION = 3;

    public InvitesView(final ViewController view) {
        this.view = view;

        this.accountsView = new AccountsView(view);

        this.alignmentProperty().set(Pos.CENTER);

        this.selectionTable = new TableView<Account>();
        this.selectionTable.setPlaceholder(new Text("Geen accounts geselecteerd"));
        this.selectionTable.setMaxHeight(SELECTIONTABLEHEIGHT);

        TableColumn<Account, String> idUsernameSelected = new TableColumn<>("Username");
        idUsernameSelected.setCellValueFactory(new PropertyValueFactory<>("username"));
        Collections.addAll(this.selectionTable.getColumns(), idUsernameSelected);

        setTableClickEvent();
        this.getChildren().addAll(this.accountsView, this.selectionTable);

        this.accountsView.setRowFactory(tv -> new TableRow<Account>() {
            @Override
            protected void updateItem(final Account acc, final boolean empty) {
                super.updateItem(acc, empty);
                if (acc == null) {
                    setStyle("");
                } else if (!acc.getInviteable()) {
                    setStyle("-fx-background-color: #9e9e9e;");
                } else {
                    setStyle("");
                }
            }
        });
    }

    public ArrayList<Account> getSelectedAccounts() {
        return this.selectedAccounts;
    }

    private void setTableClickEvent() {
        this.accountsView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Account acc = this.accountsView.getSelectionModel().getSelectedItem();
                if (acc == null) {
                    return;
                }
                if (!acc.getInviteable()) {
                    this.view.displayError("Je wacht nog op een antwoord van deze speler");
                    return;
                }
                if (this.view.getAccountController().getAccount().getUsername().equals(acc.getUsername())) {
                    this.view.displayError("Je kan jezelf niet uitnodigen");
                    return;
                }
                if (!selectedAccounts.contains(acc) && selectedAccounts.size() < MAXSIZESELECTION) {
                    selectedAccounts.add(acc);
                    this.selectionTable.getItems().add(acc);
                }
            }
        });
        this.selectionTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Account acc = this.selectionTable.getSelectionModel().getSelectedItem();
                selectedAccounts.remove(acc);
                this.selectionTable.getItems().remove(acc);
            }
        });
    }
}
