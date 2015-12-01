package com.camut.util;

import static org.junit.Assert.*;

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

}
