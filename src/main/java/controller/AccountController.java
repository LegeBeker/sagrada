package main.java.controller;

import java.util.ArrayList;

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

    public ArrayList<Account> getInviteableAccounts() {
        return Account.getInviteableAccounts(this.getAccount().getUsername());
    }

    public int getAmountOpponents(final String username) {
        return Account.getAmountOpponents(username);
    }

    public int getAmountWonGames(final String username) {
        return Account.getAmountWonGames(username);
    }

    public int getAmountLostGames(final String username) {
        return Account.getAmountLostGames(username);
    }

    public String getHighestScore(final String username) {
        Integer score = Account.getHighestScore(username);
        if (score == null) {
            return "Geen scores gevonden";
        }
        return score.toString();
    }

    public String getMostPlacedValue(final String username) {
        Integer value = Account.getMostPlacedValue(username);
        if (value == null) {
            return "Geen dobbelsteen geplaatst";
        }
        return value.toString();
    }

    public String getMostPlacedColor(final String username) {
        String color = Account.getMostPlacedColor(username);
        if (color == null) {
            return "Geen dobbelsteen geplaatst";
        }
        return color;
    }
}
