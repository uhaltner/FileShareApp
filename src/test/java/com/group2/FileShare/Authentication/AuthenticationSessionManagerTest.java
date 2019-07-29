package com.group2.FileShare.Authentication;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.group2.FileShare.User.User;


public class AuthenticationSessionManagerTest {

    protected static MockHttpSession session;

    protected static AuthenticationSessionManager authSessionManager;

    protected static MockHttpServletRequest request;

    protected static User user;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        session = new MockHttpSession();
        authSessionManager = new AuthenticationSessionManager();

        user = new User(1, "babatunde@dal.ca", "Babatunde", "Adeniyi");
        authSessionManager.setSession(user, session);

        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        authSessionManager.destroySession();
        assertFalse(authSessionManager.isUserLoggedIn());
        session.clearAttributes();
        authSessionManager = null;
        session = null;
        request = null;
        user = null;
    }


    @Test
    public void getInstanceTest() {
        Assert.assertNotNull(authSessionManager);
    }

    @Test
    public void isUserLoggedInTest() {
        assertTrue(authSessionManager.isUserLoggedIn());
    }

    @Test
    public void getUserIdTest() {
        assertTrue(authSessionManager.getUserId() == 1);
    }

    @Test
    public void getUserFirstNameTest() {
        assertTrue(authSessionManager.getFirstName().equals("Babatunde"));
    }

    @Test
    public void getUserLastNameTest() {
        assertTrue(authSessionManager.getLastName().equals("Adeniyi"));
    }

    @Test
    public void getUserEmailTest() {
        assertTrue(authSessionManager.getEmail().equals("babatunde@dal.ca"));
    }

    @Test
    public void getUserTest() {
        System.out.println(authSessionManager);
        User u = authSessionManager.getUser();
        assertTrue(u.getId() == 1 &&
                u.getFirstName().equals("Babatunde") &&
                u.getLastName().equals("Adeniyi") &&
                u.getEmail().equals("babatunde@dal.ca"));
    }
}
