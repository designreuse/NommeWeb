package com.camut.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/spring-mvc.xml", "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class PaymentControllerTest {

	@Autowired
	private PaymentController paymentController;

	@Test
	public void paymentControllerTest() {
		
		// Check against expected list of charities in the test server ("Red Cross, U of C").
		List<String> expectedCharityFirstLetters = new ArrayList<String>();
		expectedCharityFirstLetters.add("R");
		expectedCharityFirstLetters.add("U");
		
		List<String> actualCharityFirstLetters = paymentController.getAllCharityFirstLetters();
		
		assertEquals(actualCharityFirstLetters, expectedCharityFirstLetters);
	}

}
