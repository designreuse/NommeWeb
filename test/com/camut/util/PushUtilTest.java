package com.camut.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.camut.utils.PushUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/spring-mvc.xml", "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
public class PushUtilTest {

	@Test
	public void testGetIosCertificatePath_productionIsYES() {
		String expectedPath_YES = "/p12/aps_production_Server.p12";
		
		String realPath = PushUtil.getIosCertificatePath();
		
		//IS_PRODUCTION = YES
		assertEquals(expectedPath_YES, realPath);
	}
	
	@Test
	public void testGetIosCertificatePath_productionIsNO() {
		String expectedPath_NO = "/p12/aps_Service_developer.p12";
		
		String realPath = PushUtil.getIosCertificatePath();
		
		//IS_PRODUCTION = NO
		assertEquals(expectedPath_NO,realPath);
	}
}
