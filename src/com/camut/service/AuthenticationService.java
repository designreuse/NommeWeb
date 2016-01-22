package com.camut.service;

import javax.servlet.http.HttpSession;

public interface AuthenticationService {
	
	/**
	 * @Title: validSessionLogin
	 * @Description: Checks to see if there is a valid user logged in.
	 * @param session
	 * @return boolean
	 */
	public boolean validSessionLogin(HttpSession session);
	
	/**
	 * @Title: validMobileLogin
	 * @Description: Checks to see if there is a valid user logged in with mobile.
	 * @param mobileToken
	 * @param mobileType
	 * @param consumerUuid
	 * @return boolean
	 */
	public boolean validMobileLogin(String mobileToken, String mobileType, String consumerUuid);
}
