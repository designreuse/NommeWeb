package com.camut.service;

import javax.servlet.http.HttpSession;

public interface AuthenticationService {
	
	/**
	 * @Title: validConsumerSessionLogin
	 * @Description: Checks to see if there is a valid consumer logged in.
	 * @param session
	 * @return boolean
	 */
	public boolean validConsumerSessionLogin(HttpSession session);
	
	/**
	 * @Title: validRestaurantSessionLogin
	 * @Description: Checks to see if there is a valid restaurant user logged in.
	 * @param session
	 * @return boolean
	 */
	public boolean validRestaurantSessionLogin(HttpSession session);
	
	/**
	 * @Title: validAdminSessionLogin
	 * @Description: Checks to see if there is a valid admin user logged in.
	 * @param session
	 * @return boolean
	 */
	public boolean validAdminSessionLogin(HttpSession session);
	
	/**
	 * @Title: validConsumerMobileLogin
	 * @Description: Checks to see if there is a valid consumer logged in with mobile.
	 * @param mobileToken
	 * @param consumerUuid
	 * @return boolean
	 */
	public boolean validConsumerMobileLogin(String mobileToken, String consumerUuid);
	
	/**
	 * @Title: validRestaurantMobileLogin
	 * @Description: Checks to see if there is a valid restaurant user logged in with mobile.
	 * @param mobileToken
	 * @param mobileType
	 * @param restaurantUuid
	 * @return
	 */
	public boolean validRestaurantMobileLogin(String mobileToken, String mobileType, String restaurantUuid);
}
