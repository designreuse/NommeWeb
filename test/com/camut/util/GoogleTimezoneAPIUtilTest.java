package com.camut.util;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
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
		Date localTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);
		Date expectedTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expectedTime),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localTime));

		// -07:00
		Date calgaryTime = GoogleTimezoneAPIUtil.getLocalDateTime(51.078180, -114.131500);
		// -05:00
		Date torontoTime = GoogleTimezoneAPIUtil.getLocalDateTime(45.4214, -75.6919);
		assertNotEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calgaryTime),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(torontoTime));

		// by default, if cannot get the correct time from GeoLocation, should
		// return machine time
		Date errorTime = GoogleTimezoneAPIUtil.getLocalDateTime(-123123123, -123123123);
		expectedTime = new Date();
		assertEquals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(errorTime),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expectedTime));
	}
}
