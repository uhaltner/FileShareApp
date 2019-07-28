package com.group2.FileShare.Authentication;

import com.group2.FileShare.PublicAccess;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter
{
   @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
   {
	   String reqUri = request.getRequestURI();
	   if (!PublicAccess.instance().isPublic(reqUri))
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
        return super.preHandle(request, response, handler);
    }
}
