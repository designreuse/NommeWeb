package com.camut.service;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.camut.model.Consumers;
import com.camut.model.api.EvaluateApiModel;
import com.camut.utils.GoogleTimezoneAPIUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ConsumersAddressServiceTest {

	@Autowired private ConsumersAddressService consumersAddressService;
		
	@Test
	public void getCurrentLocalTimeFromConsumersDefaultAddressTest() {
		String consumerUuid = "80894a4cc08444c197462c7f7edebdea"; //this user have the default address in Calgary
		Date consumerLocalTime = consumersAddressService.getCurrentLocalTimeFromConsumersDefaultAddress(consumerUuid);		
		
		Date calgaryTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);

		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calgaryTime),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(consumerLocalTime));
		
		
		consumerUuid = "XXXXXXXXXXXXXXXXXXXXX"; //this user is not exist
		consumerLocalTime = consumersAddressService.getCurrentLocalTimeFromConsumersDefaultAddress(consumerUuid);		
		Date machineDate = new Date();
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(machineDate),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(consumerLocalTime));
		
	}
}
