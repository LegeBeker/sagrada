package main.java.db;

import java.util.List;
import java.util.Map;

public final class AccountDB {
    private AccountDB() {
    }

    public static Boolean checkLogin(final String username, final String password) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM account WHERE BINARY username = ? AND password = ?";
        String[] params = { username, password };

        return !db.exec(sql, params).isEmpty();
    }

    public static boolean createAccount(final String username, final String password) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO account VALUES (?, ?)";
        String[] params = { username, password };

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
        sql += "LEFT JOIN player ON player.username = account.username AND player.playstatus = 'challengee' AND player.username != ? ";
        sql += "AND player.idGame IN (SELECT idGame FROM player WHERE username = ? AND playstatus = 'CHALLENGER') GROUP BY account.username;";
        
        String[] params = { username, username };
        return db.exec(sql, params);

    }
}
