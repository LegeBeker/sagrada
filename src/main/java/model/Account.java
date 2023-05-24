package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import main.java.db.AccountDB;

public class Account {
    private String username;
    private String password;
    private Boolean inviteable;

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getInviteable() {
        return this.inviteable;
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

        ArrayList<Account> accounts = new ArrayList<Account>();

        for (Map<String, String> accountMap : AccountDB.getAll()) {
            Account acc = new Account();
            acc.username = accountMap.get("username");
            accounts.add(acc);
        }
        return accounts;
    }

    public static ArrayList<String> getInviteableAccountsUsernames(final String username) {
        ArrayList<String> accounts = new ArrayList<String>();

        for (Map<String, String> accountMap : AccountDB.getInviteableAccounts(username)) {
            if (accountMap.get("inviteable") != null && accountMap.get("inviteable").equals("1")
                    && !accountMap.get("username").equals(username)) {
                accounts.add(accountMap.get("username"));
            }
        }
        return accounts;
    }

    public static Map<String, String> getStats(final String username) {
        return AccountDB.getStats(username);
    }

    public static ArrayList<String> getAccountsUsernames() {
        return AccountDB.getAccountsUsernames();
    }
}
