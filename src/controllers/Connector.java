package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connector {

    private final String host;
    private final String user;
    private final String password;

    public Connector(String host, String user, String password) {
        this.host = host;
        this.user = user;
        this.password = password;
    }

    //tipo di ritorno: Connection.class obj
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(host, user, password);
//        if (connection != null) {
//            System.out.println("Connected to the database!");
//        } else {
//            System.out.println("Failed to make connection!");
//        }
    }


}
