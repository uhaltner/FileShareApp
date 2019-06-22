package com.group2.FileShare.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private String url;
    private String username;
    private String password;
    private String database;
    private String dbURL;
    private Connection connection = null;
    private static DatabaseConnection dbConnectionInstance = null;


    private DatabaseConnection() {

        DatabaseProperties databaseCredentials = new DatabaseProperties();
        url = databaseCredentials.getUrl();
        username = databaseCredentials.getUsername();
        password = databaseCredentials.getPassword();
        database = databaseCredentials.getDatabase();
        dbURL = "jdbc:mysql://" + url + "/" + database;
        try {
            this.connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if(connection.isClosed())
            {
                Class.forName("com.mysql.jdbc.Driver");
                this.connection = DriverManager.getConnection(dbURL, username, password);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return this.connection;
    }

    public static DatabaseConnection getdbConnectionInstance() {
        try {
            if (dbConnectionInstance == null) {
                dbConnectionInstance = new DatabaseConnection();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return dbConnectionInstance;
    }


    public void closeConnection()
    {
        try {
            if(!connection.isClosed())
            {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
