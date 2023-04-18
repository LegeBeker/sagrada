package main.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
import main.java.db.AccountDB;

public class Account {
    private String username;
    private String password;
    
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

    public static ArrayList<Account> getAll() {
        List<Map<String, String>> accountDB = AccountDB.getAll();

        ArrayList<Account> accounts = new ArrayList<Account>();

        for (Map<String, String> accountMap : accountDB) {
            Account acc = new Account();
            // Temp design choice: Only get username since password is not interesting
            acc.username = accountMap.get("username");
            accounts.add(acc);
        }
        return accounts;
    }
}
