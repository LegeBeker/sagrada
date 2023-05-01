package main.java.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import main.java.controller.ViewController;
import main.java.model.Account;

public class AccountsView extends HBox {
    private ViewController view;
    private TableView<Account> table;
    private TableView<Account> selectionTable;
    private ArrayList<Account> selectedAccounts = new ArrayList<Account>();

    private final double tableHeight = 400;
    private final double selectionTableHeight = 120;
    private final int maxSizeSelection = 3;

    public AccountsView(final ViewController view, final Boolean inviteBoolean) {
        this.alignmentProperty().set(Pos.CENTER);
        generateGeneralAccountsView(view, inviteBoolean);
        if (inviteBoolean) {
            generateInvitedAccountsOverview();
        }
        setTableClickEvent(inviteBoolean);
    }

    private void showSelectedAccounts() {
        this.selectionTable.getItems().clear();
        for (Account loopAcc : selectedAccounts) {
            this.selectionTable.getItems().add(loopAcc);
        }
    }

    public ArrayList<Account> getSelectedAccounts() {
        return this.selectedAccounts;
    }

    private void generateGeneralAccountsView(final ViewController view, final boolean inviteBoolean) {
        this.view = view;
        this.table = new TableView<Account>();
        this.table.setPlaceholder(new Text("Geen accounts gevonden"));
        this.table.setMaxHeight(tableHeight);

        TableColumn<Account, String> idUsername = new TableColumn<>("Username");
        idUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

        Collections.addAll(this.table.getColumns(), idUsername);

        for (Account acc : view.getAccountController().getAccounts()) {
            if (inviteBoolean) {
                if (!acc.getUsername().equals(this.view.getAccountController().getAccount().getUsername())) {
                    this.table.getItems().add(acc);
                }
            } else {
                this.table.getItems().add(acc);
            }
        }

        this.getChildren().addAll(this.table);
    }

    private void setTableClickEvent(final Boolean inviteBoolean) {
        if (inviteBoolean) {
            // -- Custom on click logic to add value to the generated table
            this.table.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    Account acc = this.table.getSelectionModel().getSelectedItem();
                    boolean found = false;
                    if (selectedAccounts.size() > 0) {
                        for (Account loopAcc : selectedAccounts) {
                            if (loopAcc.getUsername().equals(acc.getUsername())) {
                                // -- Value is found in list
                                found = true;
                                break;
                            }
                        }
                    }
                    if (found) {
                        selectedAccounts.remove(acc);
                        showSelectedAccounts();
                    } else {
                        if (selectedAccounts.size() < maxSizeSelection) {
                            selectedAccounts.add(acc);
                            showSelectedAccounts();
                        }
                    }
                }
            });
        } else {
            System.out.println("Dit is momenteel een W.I.P.");
            // -- TODO: @Someone, map this to the account-stats overview
        }
    }

    private void generateInvitedAccountsOverview() {
        // -- Generate table for chosen values
        this.selectionTable = new TableView<Account>();
        this.selectionTable.setPlaceholder(new Text("Geen accounts geselecteerd"));
        this.selectionTable.setMaxHeight(selectionTableHeight);

        TableColumn<Account, String> idUsernameSelected = new TableColumn<>("Username");
        idUsernameSelected.setCellValueFactory(new PropertyValueFactory<>("username"));
        Collections.addAll(this.selectionTable.getColumns(), idUsernameSelected);

        this.getChildren().addAll(this.selectionTable);
    }

}
