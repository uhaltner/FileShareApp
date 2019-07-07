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
	    if(request.isRequestedSessionIdValid()) {
			HttpSession httpSession = request.getSession(false);
			return httpSession;
		} else {
			return null;
		}
	}
	
	public boolean isUserLoggedIn() {
		return getUserId() != -1;
	}
	
	public int getUserId() {
		HttpSession session = getRequestSession();
		if (session != null) {
			Object id = session.getAttribute(UserIdKey);
			return (id != null ? (int)id : -1);
		}
		return -1;
	}
	
	public String getEmail() {
		return (String) getRequestSession().getAttribute(EmailKey);
	}
	
	public String getFirstName() {
		return (String) getRequestSession().getAttribute(FirstNameKey);
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