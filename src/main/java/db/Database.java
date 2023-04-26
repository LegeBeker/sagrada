package main.java.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void connect() {
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.name + "?user="
                    + this.username + "&password=" + this.password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            this.conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, String>> exec(final String query, final String[] params) {
        List<Map<String, String>> result = new ArrayList<>();

        try {
            if (this.conn == null || this.conn.isClosed()) {
                this.connect();
            }

            PreparedStatement stmt = conn.prepareStatement(query);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setString(i + 1, params[i]);
                }
            }
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            if (rs != null) {
                while (rs.next()) {
                    Map<String, String> row = new HashMap<String, String>();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        row.put(rs.getMetaData().getColumnName(i), rs.getString(i));
                    }
                    result.add(0, row);
                }
                rs.close();
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
