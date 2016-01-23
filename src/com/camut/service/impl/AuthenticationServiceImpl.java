package com.camut.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.model.Consumers;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.service.AuthenticationService;
import com.camut.service.ConsumersService;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.StringUtil;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired private ConsumersService consumersService;
	@Autowired private RestaurantsUserService restaurantsUserService;
	
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
		// Require login credentials.
		if (StringUtil.isEmpty(mobileToken) || StringUtil.isEmpty(consumerUuid)) {
			return false;
		}
		
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
	 * @param restaurantUuid
	 * @return
	 */
	@Override
	public boolean validRestaurantMobileLogin(String mobileToken, String restaurantUuid) {
		// Require login credentials.
		if (StringUtil.isEmpty(mobileToken) || StringUtil.isEmpty(restaurantUuid)) {
			return false;
		}
		
		// Get all users for the given restaurant.
		Restaurants searchRestaurant = new Restaurants();
		searchRestaurant.setUuid(restaurantUuid);
		List<RestaurantsUser> restaurantUsers = restaurantsUserService.getAllRestaurantsUser(searchRestaurant);
		
		// Check if one of the users has a matching token.  If so, this is a valid user.
		for (RestaurantsUser restaurantUser : restaurantUsers) {
			if (restaurantUser.getToken().equals(mobileToken)) {
				return true;
			}
		}
		
		return false;
	}
}
