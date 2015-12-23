package com.camut.service;

import static org.junit.Assert.*;

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
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class CharityServiceTest {
	
	@Autowired private CharityService charityService;

	@Test
	public void getConsumerCharityDonationsTest() {
		// Valid parameters.
		String consumerUuid = "2f97af8567934d65a67c40a17b4020c8";
		String charityId = "11";
		String startDate = "2015-12-22";
		String endDate = "2015-12-22";
		
		double expectedTotalDonation = 3.8;
		double actualTotalDonation = charityService.getConsumerCharityDonations(consumerUuid, charityId, startDate, endDate);
		assertEquals(expectedTotalDonation, actualTotalDonation, 0.001);
		
		// Invalid parameters.
		startDate = "2015-12-22";
		endDate = "1970-01-01";
		
		expectedTotalDonation = 0.0;
		actualTotalDonation = charityService.getConsumerCharityDonations(consumerUuid, charityId, startDate, endDate);
		assertEquals(expectedTotalDonation, actualTotalDonation, 0.001);
	}

}
