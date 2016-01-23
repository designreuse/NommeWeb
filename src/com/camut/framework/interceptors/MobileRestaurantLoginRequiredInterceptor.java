package com.camut.framework.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.camut.service.AuthenticationService;

public class MobileRestaurantLoginRequiredInterceptor extends HandlerInterceptorAdapter {
	@Autowired private AuthenticationService authenticationService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		// Get the user credentials.
		String mobileToken = request.getParameter("token");
		String restaurantUuid = request.getParameter("restaurantUuid");
		
		if (authenticationService.validRestaurantMobileLogin(mobileToken, restaurantUuid)) {
			return true;
		} else {
			response.sendError(403);
			return false;
		}
	}
}