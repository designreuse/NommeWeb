package com.camut.util;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.camut.service.OrderService;
import com.camut.utils.CommonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/spring-mvc.xml", "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
public class CommonUtilTest {
	
	@Test
	public void isPhoneTest() {
		String validPhone1 = "4035551234";
		String validPhone2 = "403-5551234";
		String validPhone3 = "403-555-1234";
		String validPhone4 = "403555-1234";
		
		String invalidPhone1 = "abc";
		String invalidPhone2 = "403abc1234";
		String invalidPhone3 = "";
		String invalidPhone4 = null;
		String invalidPhone5 = "403";
		String invalidPhone6 = "12345678901234567890";
		
		assertTrue(CommonUtil.isPhone(validPhone1));
		assertTrue(CommonUtil.isPhone(validPhone2));
		assertTrue(CommonUtil.isPhone(validPhone3));
		assertTrue(CommonUtil.isPhone(validPhone4));
		
		assertFalse(CommonUtil.isPhone(invalidPhone1));
		assertFalse(CommonUtil.isPhone(invalidPhone2));
		assertFalse(CommonUtil.isPhone(invalidPhone3));
		assertFalse(CommonUtil.isPhone(invalidPhone4));
		assertFalse(CommonUtil.isPhone(invalidPhone5));
		assertFalse(CommonUtil.isPhone(invalidPhone6));
	}

}
