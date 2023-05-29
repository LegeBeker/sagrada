package main.java.db;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Database {

    private static final Database INSTANCE = new Database();

    private static final int SECONDS = 1000000000;

    private static final int IGNORESTACKTRACE = 3;
    private static final int MAXSTACKTRACE = 6;

    private String host;
    private String name;
    private String username;
    private String password;

    private Connection conn;

    private boolean debug = false;

    private int queryCount = 1;

    public static Database getInstance() {
        return INSTANCE;
    }

    public void setDebug(final boolean debug) {
        this.debug = debug;
    }

    public Database() {
        if (this.debug) {
            writeToDebugLog("Initializing database...");
        }

        Env env = new Env(".env");

        this.host = env.get("DB_HOST");
        this.name = env.get("DB_NAME");
        this.username = env.get("DB_USERNAME");
        this.password = env.get("DB_PASSWORD");

        if (this.debug) {
            writeToDebugLog("Database initialized");
        }
    }

    private void connect() {
        try {
            if (this.debug) {
                writeToDebugLog("Connecting to database...");
            }
            this.conn = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.name + "?user="
                    + this.username + "&password=" + this.password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (this.debug) {
            writeToDebugLog("Connected to database");
        }
    }

    public void close() {
        try {
            if (this.conn == null || this.conn.isClosed()) {
                return;
            }
            this.conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Map<String, String>> exec(final String query, final String[] params) {
        long startTime = 0;
        long endTime = 0;

        if (this.debug) {
            startTime = System.nanoTime();
        }

        List<Map<String, String>> result = new ArrayList<>();

        try {
            if (this.conn == null || this.conn.isClosed()) {
                this.connect();
            }

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS );
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
            } else {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Map<String, String> idRow = new HashMap<String, String>();
                    idRow.put("id", String.valueOf(generatedKeys.getLong(1)));
                    result.add(0, idRow);
                }
                generatedKeys.close(); 
            }

            if (this.debug) {
                endTime = System.nanoTime();
                double timeElapsed = endTime - startTime;
                writeToDebugLog("Query " + this.queryCount++ + ": "
                        + stmt.toString().substring(stmt.toString().indexOf(":") + 2),
                        "Execution time in seconds: " + timeElapsed / SECONDS);
            }

            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void writeToDebugLog(final String... message) {
        try {
            FileWriter fw = new FileWriter("debug.log", true);
            for (String m : message) {
                fw.write("[" + new java.sql.Time(System.currentTimeMillis()) + "] " + m + "\n");
            }
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            for (int i = IGNORESTACKTRACE; i < MAXSTACKTRACE; i++) {
                fw.write("\t" + stackTraceElements[i].toString() + "\n");
            }
            fw.write("--------------------\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
