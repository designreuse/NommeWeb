package com.camut.util;

import static org.junit.Assert.*;

import java.util.TimeZone;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.camut.utils.GoogleTimezoneAPIUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
public class GoogleTimezoneAPIUtilTest {
	@Test
	public void getTimeZoneFromGeoLocationTest() {	
		TimeZone timezone1 = GoogleTimezoneAPIUtil.getTimeZoneFromGeoLocation(51.078180, -114.131500);
		assertEquals("America/Edmonton", timezone1.getID());
		
		TimeZone timezone2 = GoogleTimezoneAPIUtil.getTimeZoneFromGeoLocation(45.4214, -75.6919);
		assertEquals("America/Toronto", timezone2.getID());

	}
	
	@Test
	public void getLocalDateTimeFromGeoLocationTest() {
		DateTime localTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);
		DateTime expectedTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);
		assertEquals(expectedTime.getHourOfDay(), localTime.getHourOfDay());

		// -07:00
		DateTime calgaryTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);
		// -05:00
		DateTime torontoTime = GoogleTimezoneAPIUtil.getLocalDateTime(45.4214, -75.6919);
		assertNotEquals(calgaryTime.plusHours(0).getHourOfDay(), torontoTime.getHourOfDay());
		assertEquals(calgaryTime.plusHours(2).getHourOfDay(), torontoTime.getHourOfDay());

		// by default, if cannot get the correct time from GeoLocation, should
		// return machine time
		DateTime errorTime = GoogleTimezoneAPIUtil.getLocalDateTime(-123123123, -123123123);
		expectedTime = new DateTime();
		assertEquals(expectedTime, errorTime);
	}
}
