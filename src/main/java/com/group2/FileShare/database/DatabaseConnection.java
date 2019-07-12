package com.group2.FileShare.database;

import com.group2.FileShare.DefaultProperties;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection
{

    private String dbURL;
    private Connection connection = null;
    private static DatabaseConnection dbConnectionInstance = null;
    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);


    private DatabaseConnection()
    {
        dbURL = DefaultProperties.getInstance().getJDBCConnectionString();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(dbURL);
        }
        catch (SQLException e)
        {
            logger.log(Level.ERROR, "Connection not established due to SQL in DatabaseConnection :", e);
        }
        catch (ClassNotFoundException e)
        {
            logger.log(Level.ERROR, "Connection not established due to Class not found in DatabaseConnection :", e);
        }
    }

    public Connection getConnection()
    {
        try
        {
            if (connection.isClosed())
            {
                Class.forName("com.mysql.cj.jdbc.Driver");
                this.connection = DriverManager.getConnection(dbURL);
            }

        }
        catch (ClassNotFoundException e)
        {
            logger.log(Level.ERROR, "Connection not established due to Class not found in DatabaseConnection :", e);
        }
        catch (SQLException e)
        {
            logger.log(Level.ERROR, "Connection not established due to SQL in DatabaseConnection :", e);
        }
        return this.connection;
    }

    public static DatabaseConnection getdbConnectionInstance()
    {
        try
        {
            if (dbConnectionInstance == null)
            {

                dbConnectionInstance = new DatabaseConnection();
            }
        } catch (Exception e)
        {
            logger.log(Level.ERROR, "Error while creating Database connection instance :", e);

        }
        return dbConnectionInstance;
    }

    public static Connection getConn()
    {
        return getdbConnectionInstance().getConnection();
    }

    public void closeConnection()
    {
        try
        {
            if(!connection.isClosed())
            {
                connection.close();
            }
        }
        catch (SQLException e)
        {
            logger.log(Level.ERROR, "Error while closing database connection :", e);
        }
    }

}
