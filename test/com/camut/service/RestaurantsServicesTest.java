package com.camut.service;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.camut.utils.GoogleTimezoneAPIUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml", "classpath*:spring-hibernate.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class RestaurantsServicesTest {

	@Autowired
	private RestaurantsService restaurantsService;

	@Test
	public void getCurrentLocalTimeFromRestaurantsUuidTest() {
		String restaurantUuid = "9b740d70ad8c4214ba6561e512ea8949";
		Date restaurantLocalTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);

		Date calgaryTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);

		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calgaryTime),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(restaurantLocalTime));

		restaurantUuid = "XXXXXXXXXXXXXXXXXXXXX"; // this user does not exist
		restaurantLocalTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
		Date machineDate = new Date();
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(machineDate),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(restaurantLocalTime));

	}

}
