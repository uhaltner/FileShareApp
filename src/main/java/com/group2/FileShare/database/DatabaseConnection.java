package com.group2.FileShare.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.group2.FileShare.DefaultProperties;

public class DatabaseConnection {

    private String dbURL;
    private Connection connection = null;
    private static DatabaseConnection dbConnectionInstance = null;


    private DatabaseConnection() {
    	dbURL = DefaultProperties.getInstance().getJDBCConnectionString();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if(connection.isClosed())
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection(dbURL);
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
