package com.group2.FileShare;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.group2.FileShare.DefaultProperties;

import java.io.InputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultPropertiesTest {

    private String jdbcConnectionStringMock = "jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_2_DEVINT?user=CSCI5308_2_DEVINT_USER&password=CSCI5308_2_DEVINT_2009";
    private String s3BucketNameMock = "csci5308-file-share-app-dev";
    private long s3LinkValidityMock = 3600000;
    private String dbFile = "default.properties";
    private int deleteDocumentExpiryDaysMock = 30;
    private int pinDocumentsLimitMock = 3;

    @Test
    public void getDatabasePropFileTest() {
        InputStream inputDBTest = getClass().getClassLoader().getResourceAsStream(dbFile);
        Assert.assertNotNull(inputDBTest);
    }

    @Test
    public void getInstanceTest() {
        DefaultProperties dbPropertiesTest = DefaultProperties.getInstance();
        Assert.assertNotNull(dbPropertiesTest);
    }

    @Test
    public void getJDBCConnectionStringTest() {
        String JDBCString = DefaultProperties.getInstance().getJDBCConnectionString();
        Assert.assertEquals(jdbcConnectionStringMock, JDBCString);
    }

    @Test
    public void getS3BucketNameTest() {
        String s3_bucketName = DefaultProperties.getInstance().getS3BucketName();
        Assert.assertEquals(s3BucketNameMock, s3_bucketName);
    }

    @Test
    public void getS3LinkValidityMillisTest() {
        long s3LinkValidityMillis = DefaultProperties.getInstance().getS3LinkValidityMillis();
        Assert.assertEquals(s3LinkValidityMock, s3LinkValidityMillis);
    }

    @Test
    public void getPinDocumentsLimitTest()
    {
        int pinDocumentsLimit = DefaultProperties.getInstance().getPinDocumentsLimit();
        Assert.assertEquals(pinDocumentsLimitMock, pinDocumentsLimit);
    }

    @Test
    public void getDeleteDocumentExpiryTest()
    {
        int deleteDocumentExpiryDays = DefaultProperties.getInstance().getDeleteDocumentExpiry();
        Assert.assertEquals(deleteDocumentExpiryDaysMock, deleteDocumentExpiryDays);
    }

}
