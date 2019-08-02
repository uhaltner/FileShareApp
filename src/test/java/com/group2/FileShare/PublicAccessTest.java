package com.group2.FileShare;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PublicAccessTest {

    private static PublicAccess publicAccess;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        publicAccess = PublicAccess.instance();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        publicAccess = null;
        assertNull(publicAccess);
    }

    @Test
    public void getInstanceTest() {
        Assert.assertNotNull(publicAccess);
    }

    @Test
    public void publicURISuccessTest()
    {
        boolean status = publicAccess.isPublic(".css");
        Assert.assertEquals(true, status);
    }

    @Test
    public void notPublicURITest()
    {
        boolean status = publicAccess.isPublic("/dashboard/private");
        Assert.assertEquals(false, status);
    }
}
