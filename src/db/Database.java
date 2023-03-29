package db;

import pattern.Singleton;

public final class Database extends Singleton {

    private String databaseName;
    private String userName;
    private String password;
    private String host;
    private int port = 3306;


    public Database(String name, String username, String password, String host){
        this.databaseName = name;
        this.userName = username;
        this.password = password;
        this.host = host;
    }
}
