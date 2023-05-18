package main.java.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

    public ArrayList<Map<String, String>> getAccounts() {
        ArrayList<Account> accounts = Account.getAll();
        ArrayList<Map<String, String>> accountsMap = new ArrayList<Map<String, String>>();

        for (Account acc : accounts) {
            Map<String, String> accountMap = new HashMap<String, String>();
            accountMap.put("username", acc.getUsername());
            accountMap.put("amount_won_games", Integer.toString(acc.getAmountWonGames()));
            accountsMap.add(accountMap);
        }

        return accountsMap;
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
