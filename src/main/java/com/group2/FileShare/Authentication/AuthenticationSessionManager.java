package com.group2.FileShare.Authentication;

import com.group2.FileShare.User.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthenticationSessionManager
{

    private static final String UserIdKey = "UserId";
    private static final String EmailKey = "Email";
    private static final String FirstNameKey = "FirstName";
    private static final String LastNameKey = "LastName";
    private static final String UserKey = "UserKey";
    private static final Logger logger = LogManager.getLogger(AuthenticationSessionManager.class);

    private static AuthenticationSessionManager uniqueInstance = null;

    public static AuthenticationSessionManager instance()
    {
        if (null == uniqueInstance)
        {
            uniqueInstance = new AuthenticationSessionManager();
        }
        return uniqueInstance;
    }

    private HttpSession getRequestSession()
    {
        try
        {
            RequestAttributes requestAttributes = RequestContextHolder
                    .currentRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();

            if(request.isRequestedSessionIdValid())
            {
                HttpSession httpSession = request.getSession(false);
                return httpSession;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Failed to get session request at getRequestSession(): ", e);
        }
        return null;
    }

    public boolean isUserLoggedIn() {
        return getUserId() != -1;
    }

    public int getUserId()
    {
        try
        {
            HttpSession session = getRequestSession();

            if (session != null)
            {
                Object id = session.getAttribute(UserIdKey);
                if(id != null ) {
                    return (int)id;
                }
            }
            return -1;
        }
        catch (Exception e) {
            logger.log(Level.ERROR, "Failed to get user ID  from session at getUserId(): ", e);
        }

        return -1;
    }

    public String getEmail() {
        HttpSession session = getRequestSession();
        if(session != null) {
            return (String) session.getAttribute(EmailKey);
        }
        return "";
    }

    public String getFirstName()
    {
        HttpSession session = getRequestSession();
        if(session != null)
        {
            Object firstName = session.getAttribute(FirstNameKey);
            if(firstName != null) {
                return (String)firstName;
            }
        }
        return "";
    }

    public String getLastName()
    {
        HttpSession session = getRequestSession();
        if(session != null)
        {
            Object lastName = session.getAttribute(LastNameKey);
            if(lastName != null) {
                return (String) lastName;
            }
        }
        return "";
    }

    public void destroySession()
    {
        try
        {
            HttpSession session = getRequestSession();
            if (session != null)
            {
                session.invalidate();
            }
        }
        catch (Exception e)
        {
            logger.log(Level.ERROR, "Failed to destroy session at destroySession(): ", e);
        }

    }

    public User getUser()
    {
        HttpSession session = getRequestSession();
        if (session != null)
        {
            session.getAttribute(UserKey);
        }
        return null;
    }

    public void setSession(User user, HttpSession session)
    {
        session.setAttribute(UserIdKey, user.getId());
        session.setAttribute(EmailKey, user.getEmail());
        session.setAttribute(FirstNameKey, user.getFirstName());
        session.setAttribute(LastNameKey, user.getLastName());
        session.setAttribute(UserKey, user);
    }
}