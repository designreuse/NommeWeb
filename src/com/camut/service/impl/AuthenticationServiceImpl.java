package com.camut.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.model.Consumers;
import com.camut.service.AuthenticationService;
import com.camut.service.ConsumersService;
import com.camut.utils.StringUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired private ConsumersService consumersService;
	
	/**
	 * @Title: validConsumerSessionLogin
	 * @Description: Checks to see if there is a valid consumer logged in.
	 * @param session
	 * @return boolean
	 */
	@Override
	public boolean validConsumerSessionLogin(HttpSession session) {
		// Get the consumer from session.  If the consumer exists in session, then the user is valid.
		if (session != null && session.getAttribute("consumer") != null) {
			Consumers sessionConsumer = null;
			try {
				sessionConsumer = (Consumers)session.getAttribute("consumer");
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			if (sessionConsumer != null) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @Title: validRestaurantSessionLogin
	 * @Description: Checks to see if there is a valid restaurant user logged in.
	 * @param session
	 * @return boolean
	 */
	@Override
	public boolean validRestaurantSessionLogin(HttpSession session) {
		if (session != null && session.getAttribute("restaurantsUser") != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Title: validAdminSessionLogin
	 * @Description: Checks to see if there is a valid admin logged in.
	 * @param session
	 * @return boolean
	 */
	@Override
	public boolean validAdminSessionLogin(HttpSession session) {
		if (session != null && session.getAttribute("adminUserLoginname") != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @Title: validConsumerMobileLogin
	 * @Description: Checks to see if there is a valid consumer logged in with mobile.
	 * @param mobileToken
	 * @param consumerUuid
	 * @return boolean
	 */
	@Override
	public boolean validConsumerMobileLogin(String mobileToken, String consumerUuid) {
		// Get the consumer with the same Uuid.
		Consumers expectedConsumer = consumersService.getConsumersByUuid(consumerUuid);
		if (expectedConsumer == null) {
			return false;
		}
		
		// Compare the mobile token.  If it is the same, then the user is valid.
		if (StringUtil.isNotEmpty(mobileToken) && expectedConsumer.getMobileToken().equals(mobileToken)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * @Title: validRestaurantMobileLogin
	 * @Description: Checks to see if there is a valid restaurant user logged in with mobile.
	 * @param mobileToken
	 * @param mobileType
	 * @param restaurantUuid
	 * @return
	 */
	@Override
	public boolean validRestaurantMobileLogin(String mobileToken, String mobileType, String restaurantUuid) {
		// TODO: Get the restaurant user with the same Uuid.
		// TODO: Compare the mobile token.  If it is the same, then the user is valid.
		return false;
	}
}
