/**
 * 
 */
package com.camut.junit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.camut.dao.CharityDao;
import com.camut.model.Charity;
import com.camut.service.OpenTimeService;

/**
 * @ClassName Test.java
 * @author wangpin
 * @createtime Sep 8, 2015
 * @author
 * @updateTime
 * @memo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "file:WebRoot/WEB-INF/spring-mvc.xml", "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
// 当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否
@TransactionConfiguration(defaultRollback = true)
// 记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class TestJunit1 {


	@Autowired
	private WebApplicationContext wac;
	
	
	private MockMvc mockMvc;
	
	@Autowired
	private OpenTimeService openTimeService;
	@Autowired
	private CharityDao charityDao;
	@Before
	public void setup() {
		// webAppContextSetup 注意上面的static import
		// webAppContextSetup 构造的WEB容器可以添加fileter 但是不能添加listenCLASS
		// WebApplicationContext context =
		// ContextLoader.getCurrentWebApplicationContext();
		// 如果控制器包含如上方法 则会报空指针
		//this.mockMvc = webAppContextSetup(this.wac).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
	}
	
	//@Test
	/*public void toLogin() throws Exception {
		mockMvc.perform((post("/restaurant/restaurantLogin").param("wangpin@qq.com", "123456"))).andExpect(status().isOk()).andDo(print());
	}*/
	
	@Test
	public void getOpenTimeTest(){
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2015-9-16");
			String[] strs = openTimeService.getOpenTimeByOrderDate(date, 16, 1);
			for(String s:strs){
				System.out.println(s);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//@Test
	public void charityTest(){
		List<Charity> list = charityDao.getCharity();
		for (Charity charity : list) {
			System.out.println(charity.getCharityName());
		}
	}
}
