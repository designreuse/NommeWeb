package com.camut.util;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.camut.utils.StripeUtil;
import com.camut.framework.constant.StripeApiKey;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/spring-mvc.xml", "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
public class StripeUtilTest {

	@Test
	public void testGetStripeApiKey() {
		String key = StripeUtil.getApiKey();
		
		//IS_PRODUCTION = NO
		assertEquals(StripeApiKey.TESTAPIKEY,key);
		
		//IS_PRODUCTION = YES
		//assertEquals(StripeApiKey.LIVEAPIKEY, key);
	}
}
