package main.java.db;

import java.util.List;
import java.util.Map;

import main.java.enums.PlayStatusEnum;

public final class AccountDB {
    private AccountDB() {
    }

    public static Boolean checkLogin(final String username, final String password) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM account WHERE BINARY username = ? AND password = ?";
        String[] params = {username, password };

        return !db.exec(sql, params).isEmpty();
    }

    public static boolean createAccount(final String username, final String password) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO account VALUES (?, ?)";
        String[] params = {username, password };

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
                PlayStatusEnum.CHALLENGER.toString() };
        return db.exec(sql, params);

    }

    public static Map<String, String> getAmountOpponents(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT COUNT(DISTINCT(username)) AS 'amount_diff_opponents'FROM player ";
        sql += "WHERE idgame in (SELECT idgame FROM player WHERE username = ?) AND username != ?;";
        String[] params = {username, username };
        return db.exec(sql, params).get(0);
    }

    public static Map<String, String> getAmountWonGames(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT COUNT(*) AS 'amount_won_games' ";
        sql += "FROM (SELECT idgame, username, MAX(score) AS max_score, playstatus FROM player ";
        sql += "WHERE idgame IN (SELECT idgame FROM player WHERE username = ?) ";
        sql += "GROUP BY idgame, username) AS max_scores_per_game ";
        sql += "WHERE max_score = (SELECT MAX(score) FROM player WHERE idgame = max_scores_per_game.idgame) AND username = ? AND playstatus = ?;";
        String[] params = {username, username, PlayStatusEnum.FINISHED.toString() };
        return db.exec(sql, params).get(0);
    }

    public static Map<String, String> getAmountLostGames(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT COUNT(*) AS 'amount_won_games' ";
        sql += "FROM (SELECT idgame, username, MAX(score) AS max_score, playstatus FROM player ";
        sql += "WHERE idgame IN (SELECT idgame FROM player WHERE username = ?) ";
        sql += "GROUP BY idgame, username) AS max_scores_per_game ";
        sql += "WHERE max_score != (SELECT MAX(score) FROM player WHERE idgame = max_scores_per_game.idgame) AND username = ? AND playstatus = ?;";
        String[] params = {username, username, PlayStatusEnum.FINISHED.toString() };
        return db.exec(sql, params).get(0);
    }

    public static Map<String, String> getHighestScore(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT MAX(score) AS 'highest_score' FROM player WHERE username = ?;";
        String[] params = {username };
        return db.exec(sql, params).get(0);
    }

    public static Map<String, String> getMostPlacedValue(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT dienumber, COUNT(*) AS 'most_placed_value' FROM playerframefield ";
        sql += "WHERE dienumber IS NOT NULL and idplayer IN (SELECT idplayer FROM player WHERE username = ?) ";
        sql += "GROUP BY dienumber ORDER BY most_placed_value;";
        String[] params = {username };

        Map<String, String> returnVal = null;
        // Added this check for nullReferences

        if (db.exec(sql, params).size() > 0) {
            // -- No return values found
            returnVal = db.exec(sql, params).get(0);
        }
        return returnVal;

    }

    public static Map<String, String> getMostPlacedColor(final String username) {
        Database db = Database.getInstance();
        String sql = "SELECT diecolor, COUNT(*) AS 'most_placed_color' FROM playerframefield ";
        sql += "WHERE diecolor IS NOT NULL and idplayer IN (SELECT idplayer FROM player WHERE username = ?) ";
        sql += "GROUP BY diecolor ORDER BY most_placed_color;";
        String[] params = {username };

        Map<String, String> returnVal = null;
        // Added this check for nullReferences

        if (db.exec(sql, params).size() > 0) {
            // -- No return values found
            returnVal = db.exec(sql, params).get(0);
        }
        return returnVal;

    }

}
