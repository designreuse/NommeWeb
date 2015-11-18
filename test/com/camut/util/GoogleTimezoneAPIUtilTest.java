package com.camut.util;

import static org.junit.Assert.*;

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
}
