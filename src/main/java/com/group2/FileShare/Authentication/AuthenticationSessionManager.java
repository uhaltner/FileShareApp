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
	
	public boolean isUserLoggedIn() {
		if(user_session == null) {
			return false;
		} else {
			return (user_session.getAttribute(UserIdKey) != null);
		}
	}
	
	public int getUserId() {
		return (int) user_session.getAttribute(UserIdKey);
	}
	
	public void setUserId(int value, HttpSession session) {
		session.setAttribute(UserIdKey, value);
	}
	
	public String getEmail() {
		return (String) user_session.getAttribute(EmailKey);
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
	
	public void destroySession() {
		user_session = null;
	}
	
	public User getUser() {
		return (User) user_session.getAttribute(UserKey);
	}
	
	public void setSession(User user, HttpSession session) {
		user_session = session;
		user_session.setAttribute(UserIdKey, user.getId());
		user_session.setAttribute(EmailKey, user.getEmail());
		user_session.setAttribute(FirstNameKey, user.getFirstName());
		user_session.setAttribute(LastNameKey, user.getLastName());
		user_session.setAttribute(UserKey, user);
	}
}