package com.group2.FileShare.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
	
   @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        String reqUri = request.getRequestURI();
        if(!(reqUri.contains(".css") || reqUri.contains(".js"))) {
	        boolean isUserLoggedIn = AuthenticationSessionManager.instance().isUserLoggedIn(session);
	        boolean isLogInPage = reqUri.equals("/login");
	        boolean isRegisterPage = reqUri.equals("/register");
	         if(!(isLogInPage || isRegisterPage) && !isUserLoggedIn) {
	 		   response.sendRedirect("/login");
	        } else if((isLogInPage || isRegisterPage) && isUserLoggedIn) {
	        	 response.sendRedirect("/dashboard");
	        }
        }
  
        return super.preHandle(request, response, handler);
    }
}
