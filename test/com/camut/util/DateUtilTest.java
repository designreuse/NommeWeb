package com.camut.util;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;
import com.camut.utils.DateUtil;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/spring-mvc.xml", "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
public class DateUtilTest {
	
	@Test
	public void ShouldReturnFormattedDateString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String expected = dateFormat.format(new Date());
		String result = DateUtil.FormatDate(new Date(), "yyyy-MM-dd");
		assertEquals(expected, result);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetToMidnightTime()
	{
		Date nowDay = new Date();
		nowDay = DateUtil.SetToMidnightTime(nowDay);
		int hours = nowDay.getHours();
		int min = nowDay.getMinutes();
		int sec = nowDay.getSeconds();
		assertEquals(0, hours);
		assertEquals(0, min);
		assertEquals(0, sec);
	}
	
	@Test
	public void testToDate()
	{	
		DateTime dateTime = DateTime.now();
		Date date = DateUtil.ToDate(dateTime);
		Date expect = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		assertEquals(dateFormat.format(expect),dateFormat.format(date));
		
		assertEquals(null,DateUtil.ToDate(null));

	}
	
	@Test
	public void testAddHours()
	{	
		Date date = new Date();
		date = DateUtil.AddHours(date, 2);
		if(date.getHours()<22)
		{
			Date expected = new Date();
			assertEquals(expected.getHours() + 2, date.getHours());
		}
		else{
			Date expected = new Date();
			assertEquals(expected.getDate() + 1, date.getDate());
		}
	}
}
