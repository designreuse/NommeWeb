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
	 * @Title: validSessionLogin
	 * @Description: Checks to see if there is a valid user logged in.
	 * @param session
	 * @return boolean
	 */
	@Override
	public boolean validSessionLogin(HttpSession session) {
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
	 * @Title: validMobileLogin
	 * @Description: Checks to see if there is a valid user logged in with mobile.
	 * @param mobileToken
	 * @param mobileType
	 * @param consumerUuid
	 * @return boolean
	 */
	@Override
	public boolean validMobileLogin(String mobileToken, String mobileType, String consumerUuid) {
		// Get the consumer with the same Uuid.
		Consumers expectedConsumer = consumersService.getConsumersByUuid(consumerUuid);
		if (expectedConsumer == null) {
			return false;
		}
		
		// Compare the mobile token.  If it is the same, then the user is valid.
		if (StringUtil.isNotEmpty(mobileToken) && StringUtil.isNotEmpty(mobileType)
				&& expectedConsumer.getMobileToken() == mobileToken && expectedConsumer.getMobileType() == mobileType) {
			return true;
		}
		
		return false;
	}
}
