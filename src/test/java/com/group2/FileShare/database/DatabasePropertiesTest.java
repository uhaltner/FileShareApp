package com.group2.FileShare.database;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabasePropertiesTest {

    private String urlMock = "db-5308.cs.dal.ca:3306";
    private String usernameMock = "CSCI5308_2_DEVINT_USER";
    private String passwordMock = "CSCI5308_2_DEVINT_2009";
    private String databaseMock = "CSCI5308_2_DEVINT";
    private String dbFile = "database.properties";

    @Test
    public void getDatabasePropFileTest() {
        InputStream inputDBTest = getClass().getClassLoader().getResourceAsStream(dbFile);
        Assert.assertNotNull(inputDBTest);
    }

    @Test
    public void getDatabaseURLTest() {
        DatabaseProperties dbPropertiesTest = new DatabaseProperties();
        String url = dbPropertiesTest.getUrl();
        Assert.assertEquals(urlMock, url);
    }

    @Test
    public void getDatabaseUsernameTest() {
        DatabaseProperties dbPropertiesTest = new DatabaseProperties();
        String username = dbPropertiesTest.getUsername();
        Assert.assertEquals(usernameMock, username);
    }

    @Test
    public void getDatabasePasswordTest() {
        DatabaseProperties dbPropertiesTest = new DatabaseProperties();
        String password = dbPropertiesTest.getPassword();
        Assert.assertEquals(passwordMock, password);
    }


    @Test
    public void getDatabaseNameTest() {
        DatabaseProperties dbPropertiesTest = new DatabaseProperties();
        String database = dbPropertiesTest.getDatabase();
        Assert.assertEquals(databaseMock, database);
    }

}
