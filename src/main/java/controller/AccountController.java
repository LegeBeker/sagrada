package main.java.controller;

import java.util.ArrayList;

import main.java.model.Account;

public class AccountController {
    private Account curAccount;

    public Account getAccount() {
        return this.curAccount;
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

    public ArrayList<Account> getInviteableAccounts(){
        return Account.getInviteableAccounts(this.getAccount().getUsername());
    }
}
