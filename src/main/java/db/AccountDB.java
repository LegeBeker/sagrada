package main.java.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.java.enums.PlayStatusEnum;

public final class AccountDB {
    private AccountDB() {
    }

    public static Boolean checkLogin(final String username, final String password) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM account WHERE BINARY username = ? AND password = ?";
        String[] params = {username, password};

        return !db.exec(sql, params).isEmpty();
    }

    public static boolean createAccount(final String username, final String password) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO account VALUES (?, ?)";
        String[] params = {username, password};

        db.exec(sql, params);

        return true;
    }

    public static List<Map<String, String>> getAll() {
        Database db = Database.getInstance();
        String sql = "SELECT * FROM account";
        return db.exec(sql, null);
    }

    public static List<Map<String, String>> getInviteableAccounts(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT account.username, IF (COUNT(player.idGame) > 0, false, true) AS 'inviteable' FROM account ";
        sql += "LEFT JOIN player ON player.username = account.username AND player.playstatus = ? AND player.username != ? ";
        sql += "AND player.idGame IN (SELECT idGame FROM player WHERE username = ? AND playstatus = ?) GROUP BY account.username;";

        String[] params = {PlayStatusEnum.CHALLENGEE.toString(), username, username,
                PlayStatusEnum.CHALLENGER.toString()};
        return db.exec(sql, params);

    }

    public static Map<String, String> getStats(final String username) {
        Database db = Database.getInstance();

        String sql = "SELECT "
                + "(SELECT COUNT(DISTINCT(username)) FROM player "
                + "WHERE idgame IN (SELECT idgame FROM player WHERE username = ?) AND username != ?) AS 'amountUniqueOpponents', "
                + "(SELECT COUNT(*) FROM (SELECT idgame, username, MAX(score) AS max_score, playstatus FROM player "
                + "WHERE idgame IN (SELECT idgame FROM player WHERE username = ?) "
                + "GROUP BY idgame, username) AS max_scores_per_game "
                + "WHERE max_score = (SELECT MAX(score) FROM player WHERE idgame = max_scores_per_game.idgame) "
                + "AND username = ? AND playstatus = ?) AS 'wonGames', "
                + "(SELECT COUNT(*) FROM (SELECT idgame, username, MAX(score) AS max_score, playstatus FROM player "
                + "WHERE idgame IN (SELECT idgame FROM player WHERE username = ?) "
                + "GROUP BY idgame, username) AS max_scores_per_game "
                + "WHERE max_score != (SELECT MAX(score) FROM player WHERE idgame = max_scores_per_game.idgame) "
                + "AND username = ? AND playstatus = ?) AS 'lostGames', "
                + "(SELECT MAX(score) FROM player WHERE username = ?) AS 'highestScore', "
                + "(SELECT eyes "
                + "FROM playerframefield AS PFF " 
                + "JOIN gamedie AS gd "
                + "ON gd.dienumber = pff.dienumber AND gd.diecolor = pff.diecolor AND gd.idgame = pff.idgame "
                + "WHERE idplayer IN (SELECT idplayer FROM player WHERE username = ?) "
                + "GROUP BY eyes "
                + "ORDER BY COUNT(*) DESC LIMIT 1) AS 'mostPlacedValue', "
                + "(SELECT diecolor FROM playerframefield "
                + "WHERE idplayer IN (SELECT idplayer FROM player WHERE username = ?) AND diecolor IS NOT NULL "
                + "GROUP BY diecolor ORDER BY COUNT(*) DESC LIMIT 1) AS 'mostPlacedColor';";
        String[] params = {username, username, username, username, PlayStatusEnum.FINISHED.toString(), username,
                username, PlayStatusEnum.FINISHED.toString(), username, username, username};

        List<Map<String, String>> dbResult = db.exec(sql, params);
        if (dbResult.size() > 0) {
            return dbResult.get(0);
        }

        return new HashMap<String, String>();
    }

    public static ArrayList<String> getAccountsUsernames() {
        Database db = Database.getInstance();

        String sql = "SELECT username FROM account";
        List<Map<String, String>> dbResult = db.exec(sql, null);

        ArrayList<String> usernames = new ArrayList<String>();
        for (Map<String, String> row : dbResult) {
            usernames.add(row.get("username"));
        }

        return usernames;
    }
}
