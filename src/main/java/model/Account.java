package main.java.model;

import java.util.ArrayList;
import java.util.Map;

import main.java.db.AccountDB;
import main.java.enums.ColorEnum;

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
        return Integer.parseInt(AccountDB.getAmountOpponents(username).get("amount_diff_opponents"));
    }

    public static int getAmountWonGames(final String username) {
        return Integer.parseInt(AccountDB.getAmountWonGames(username).get("amount_won_games"));
    }

    public static int getAmountLostGames(final String username) {
        return Integer.parseInt(AccountDB.getAmountWonGames(username).get("amount_won_games"));
    }

    public static String getHighestScore(final String username) {

        String returnValue = AccountDB.getHighestScore(username).get("highest_score");
        if (returnValue == null) {
            returnValue = "Geen score gevonden";
        }
        return returnValue;
    }

    public static String getMostPlacedValue(final String username) {
        Map<String, String> accValMap = AccountDB.getMostPlacedValue(username);
        String returnValue;
        if (accValMap == null) {
            // -- Player did not place a single die
            returnValue = "Geen dobbelsteen geplaatst";
        } else {
            returnValue = AccountDB.getMostPlacedValue(username).get("dienumber");
        }
        return returnValue;
    }

    public static String getMostPlacedColor(final String username) {
        Map<String, String> accValMap = AccountDB.getMostPlacedColor(username);
        String returnValue;
        if (accValMap == null) {
            // -- Player did not place a single die
            returnValue = "Geen dobbelsteen geplaatst";
        } else {
            returnValue = ColorEnum.fromString(AccountDB.getMostPlacedColor(username).get("diecolor")).toString();
        }
        return returnValue;
    }
}
