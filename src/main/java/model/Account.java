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

    public static ArrayList<Account> getInviteableAccounts(final String username) {
        ArrayList<Account> accounts = new ArrayList<Account>();

        for (Map<String, String> accountMap : AccountDB.getInviteableAccounts(username)) {
            Account acc = new Account();
            acc.username = accountMap.get("username");
            if (accountMap.get("inviteable") != null) {
                acc.inviteable = (Integer.parseInt(accountMap.get("inviteable")) != 0);
            }
            accounts.add(acc);
        }
        return accounts;
    }

    public static int getAmountOpponents(final String username) {
        return Integer.parseInt(AccountDB.getAmountOpponents(username));
    }

    public static int getAmountWonGames(final String username) {
        return Integer.parseInt(AccountDB.getAmountWonGames(username));
    }

    public static int getAmountLostGames(final String username) {
        return Integer.parseInt(AccountDB.getAmountLostGames(username));
    }

    public static Integer getHighestScore(final String username) {
        String highscore = AccountDB.getHighestScore(username);
        if (highscore != null) {
            return Integer.parseInt(highscore);
        }
        return null;
    }

    public static Integer getMostPlacedValue(final String username) {
        String dienumber = AccountDB.getMostPlacedValue(username);
        if (dienumber != null) {
            return Integer.parseInt(dienumber);
        }
        return null;
    }

    public static String getMostPlacedColor(final String username) {
        String diecolor = AccountDB.getMostPlacedColor(username);
        if (diecolor != null) {
            return diecolor;
        }
        return null;
    }
}
