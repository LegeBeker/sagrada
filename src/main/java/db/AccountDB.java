package main.java.db;

import java.util.List;
import java.util.Map;

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
}
