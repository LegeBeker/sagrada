package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.CheckBox;
import main.java.db.AccountDB;

public class Account {
    private String username;
    private String password;
    private CheckBox invited;
    private final BooleanProperty selected = new SimpleBooleanProperty();

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Boolean login() {
        return AccountDB.checkLogin(username, password);
    }

    public Boolean createAccount() {
        return AccountDB.createAccount(username, password);
    }

    public BooleanProperty selectedProperty() {
        return this.selected;
    }

    public boolean isSelected() {
        return this.selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public static ArrayList<Account> getAll() {

        ArrayList<Account> accounts = new ArrayList<Account>();

        for (Map<String, String> accountMap : AccountDB.getAll()) {
            Account acc = new Account();
            acc.username = accountMap.get("username");
            acc.invited = new CheckBox("Uitnodigen");
            acc.invited.setOnAction(e -> selectInvite());
            acc.invited.setVisible(true);
            accounts.add(acc);
        }
        return accounts;
    }

    static private void selectInvite() {
        System.out.println("Selected");
    }
}
