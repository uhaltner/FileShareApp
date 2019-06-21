package com.group2.FileShare.Authentication;

import javax.servlet.http.HttpSession;

import com.group2.FileShare.User.User;

public class AuthenticationSessionManager {

	private static final String UserIdKey = "UserId";
	private static final String EmailKey = "Email";
	private static final String FirstNameKey = "FirstName";
	private static final String LastNameKey = "LastName";
	private static final String UserKey = "UserKey";
	private HttpSession user_session;
	
	private static AuthenticationSessionManager uniqueInstance = null;

	public static AuthenticationSessionManager instance()
	{
		if (null == uniqueInstance)
		{
			uniqueInstance = new AuthenticationSessionManager();
		}
		return uniqueInstance;
	}
	
	public boolean isUserLoggedIn(HttpSession session) {
		return (session.getAttribute(UserIdKey) != null);
	}
	
	public String getUserId() {
		return (String) user_session.getAttribute(UserIdKey);
	}
	
	public void setUserId(int value, HttpSession session) {
		session.setAttribute(UserIdKey, value);
	}
	
	public String getEmail() {
		return (String) user_session.getAttribute(EmailKey);
	}
	
	public void setEmail(String value, HttpSession session) {
		session.setAttribute(EmailKey, value);
	}
	
	public String getFirstName() {
		return (String) user_session.getAttribute(FirstNameKey);
	}
	
	public void setFirstName(String value, HttpSession session) {
		session.setAttribute(FirstNameKey, value);
	}
	
	public String getLastName() {
		return (String) user_session.getAttribute(LastNameKey);
	}
	
	public void setLastName(String value, HttpSession session) {
		session.setAttribute(LastNameKey, value);
	}
	
	public void destroySession() {
		user_session.invalidate();
	}
	
	public User getUser() {
		return (User) user_session.getAttribute(UserKey);
	}
	
	public void setSession(User user, HttpSession session) {
		user_session = session;
		setUserId(user.getId(), session);
		setEmail(user.getEmail(), session);
		setFirstName(user.getFirstName(), session);
		setLastName(user.getLastName(), session);
		user_session.setAttribute(UserKey, user);
	}
}