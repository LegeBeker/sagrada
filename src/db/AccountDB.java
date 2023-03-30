package db;

public class AccountDB {
    public static Boolean checkLogin(String username, String password) {
        Database db = Database.getInstance();

        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        String[] params = { username, password };

        if (db.exec(sql, params).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean createAccount(String username, String password) {
        Database db = Database.getInstance();

        String sql = "INSERT INTO account VALUES (?, ?)";
        String[] params = { username, password };
        
        db.exec(sql, params);

        return true;
    }
}
