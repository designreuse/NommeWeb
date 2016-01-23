package com.camut.framework.interceptors;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.camut.service.AuthenticationService;

public class ConsumerLoginRequiredInterceptor extends HandlerInterceptorAdapter {
	@Autowired private AuthenticationService authenticationService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		if (authenticationService.validConsumerSessionLogin(request.getSession())) {
			return true;
		} else {
			response.sendError(403);
			return false;
		}
	}
}