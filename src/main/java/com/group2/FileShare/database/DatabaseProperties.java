package com.group2.FileShare.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseProperties {

    private String url;
    private String username;
    private String password;
    private String database;

    public DatabaseProperties() {

        try {
            Properties dbProperties = new Properties();
            String dbFile = "database.properties";
            InputStream inputDB = getClass().getClassLoader().getResourceAsStream(dbFile);
            dbProperties.load(inputDB);

            url = dbProperties.getProperty("DatabaseURL");
            username = dbProperties.getProperty("Username");
            password = dbProperties.getProperty("Password");
            database = dbProperties.getProperty("DatabaseName");
        }
        catch (IOException e)
        {
            System.out.print("Error connecting file of Database Properties");
        }
    }

    public String getUrl()
    {
        return this.url;
    }


    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getDatabase()
    {
        return this.database;
    }


}
