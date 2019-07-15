package com.group2.FileShare.Authentication;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
	private static final Logger logger = LogManager.getLogger(AuthenticationInterceptor.class);

   @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
   {
   	try
	{
		String reqUri = request.getRequestURI();
		if (!(reqUri.contains(".css") || reqUri.contains(".js") || reqUri.contains("/shared") ||
				reqUri.equals("/") || reqUri.equals("") ))
		{
			boolean isUserLoggedIn = AuthenticationSessionManager.instance().isUserLoggedIn();
			boolean isLogInPage = reqUri.equals("/login");
			boolean isSignUpPage = reqUri.equals("/signup");

			if (!(isLogInPage || isSignUpPage) && !isUserLoggedIn)
			{
				response.sendRedirect("/login");
			}
			else if ((isLogInPage || isSignUpPage) && isUserLoggedIn)
			{
				response.sendRedirect("/dashboard");
			}
		}
	}
   	catch (Exception e) {
		logger.log(Level.ERROR, "Failed to get session request at getRequestSession(): ", e);
	}
        return super.preHandle(request, response, handler);
    }
}
