package com.group2.FileShare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.group2.FileShare.Authentication.AuthenticationInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	AuthenticationInterceptor interceptor;

	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(interceptor);

	  }
}
