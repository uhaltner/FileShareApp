package com.group2.FileShare.database;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.SQLException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseConnectionTest {

    private DatabaseConnection dbConnectTest;
    private Connection connectionTest = null;
    private boolean booleanConnection;


    @Test
    public void dbConnectionTest() {
        dbConnectTest = DatabaseConnection.getdbConnectionInstance();
        connectionTest = dbConnectTest.getConnection();
        Assert.assertNotNull(connectionTest);
        dbConnectTest.closeConnection();
    }

    @Test
    public void dbCloseConnectionTest() {
        dbConnectTest = DatabaseConnection.getdbConnectionInstance();
        connectionTest = dbConnectTest.getConnection();
        dbConnectTest.closeConnection();
        try {
            booleanConnection = connectionTest.isClosed();
        } catch (SQLException e){
            e.printStackTrace();
        }
        Assert.assertTrue(booleanConnection);
    }
}
