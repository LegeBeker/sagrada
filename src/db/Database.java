package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public final class Database {

    private static final Database INSTANCE = new Database();

    private String host;
    private String name;
    private String username;
    private String password;

    private Connection conn;

    public static Database getInstance() {
        return INSTANCE;
    }

    public Database() {
        Env env = new Env(".env");

        this.host = env.get("DB_HOST");
        this.name = env.get("DB_NAME");
        this.username = env.get("DB_USERNAME");
        this.password = env.get("DB_PASSWORD");
    }

    public ArrayList<ArrayList<String>> exec(String query, String[] params) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.name + "?user="
                    + this.username + "&password=" + this.password);

            PreparedStatement stmt = conn.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                stmt.setString(i + 1, params[i]);
            }
            stmt.execute(); 
            ResultSet rs = stmt.getResultSet(); 

            if (rs != null) {
                while (rs.next()) {
                    ArrayList<String> row = new ArrayList<>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.add(rs.getString(i));
                    }
                    result.add(row);
                }
                rs.close();
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return result;
    }
}
