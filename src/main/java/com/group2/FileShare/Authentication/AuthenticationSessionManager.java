package com.group2.FileShare.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.group2.FileShare.User.User;

public class AuthenticationSessionManager {

	private static final String UserIdKey = "UserId";
	private static final String EmailKey = "Email";
	private static final String FirstNameKey = "FirstName";
	private static final String LastNameKey = "LastName";
	private static final String UserKey = "UserKey";
	
	private static AuthenticationSessionManager uniqueInstance = null;

	public static AuthenticationSessionManager instance()
	{
		if (null == uniqueInstance)
		{
			uniqueInstance = new AuthenticationSessionManager();
		}
		return uniqueInstance;
	}
	
	private HttpSession getRequestSession() {
	   RequestAttributes requestAttributes = RequestContextHolder
	            .currentRequestAttributes();
	    ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
	    HttpServletRequest request = attributes.getRequest();
	    HttpSession httpSession = request.getSession(true);
		return httpSession;
	}
	
	public boolean isUserLoggedIn() {
		return (getRequestSession().getAttribute(UserIdKey) != null);

	}
	
	public int getUserId() {
		return (int) getRequestSession().getAttribute(UserIdKey);
	}
	
	public String getEmail() {
		return (String) getRequestSession().getAttribute(EmailKey);
	}
	
	public String getFirstName() {
		return (String) getRequestSession().getAttribute(FirstNameKey);
	}
	
	public void setFirstName(String value, HttpSession session) {
		session.setAttribute(FirstNameKey, value);
	}
	
	public String getLastName() {
		return (String) getRequestSession().getAttribute(LastNameKey);
	}
	
	public void destroySession() {
		getRequestSession().invalidate();;
	}
	
	public User getUser() {
		return (User) getRequestSession().getAttribute(UserKey);
	}
	
	public void setSession(User user, HttpSession session) {
		session.setAttribute(UserIdKey, user.getId());
		session.setAttribute(EmailKey, user.getEmail());
		session.setAttribute(FirstNameKey, user.getFirstName());
		session.setAttribute(LastNameKey, user.getLastName());
		session.setAttribute(UserKey, user);
	}
}