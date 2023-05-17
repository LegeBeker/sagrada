package main.java.controller;

import java.util.ArrayList;
import java.util.Map;

import main.java.model.Account;

public class AccountController {
    private Account curAccount;

    public Account getAccount() {
        return this.curAccount;
    }

    public String getUsername() {
        return this.curAccount.getUsername();
    }

    public Boolean loginAccount(final String username, final String password) {
        Account curAccount = new Account();

        curAccount.setUsername(username);
        curAccount.setPassword(password);

        if (!curAccount.login()) {
            return false;
        }
        this.curAccount = curAccount;
        return true;
    }

    public boolean createAccount(final String username, final String password) {
        Account curAccount = new Account();

        curAccount.setUsername(username);
        curAccount.setPassword(password);

        if (!curAccount.createAccount()) {
            return false;
        }
        this.curAccount = curAccount;
        return true;
    }

    public boolean logoutAccount() {
        this.curAccount = null;
        return true;
    }

    public ArrayList<Account> getAccounts() {
        return Account.getAll();
    }

    public ArrayList<String> getInviteableAccountsUsernames() {
        return Account.getInviteableAccountsUsernames(this.getAccount().getUsername());
    }

    public Map<String, String> getStats(final String username) {
        return Account.getStats(username);
    }

    public ArrayList<String> getAccountsUsernames() {
        return Account.getAccountsUsernames();
    }
}
