package com.camut.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import com.camut.model.Restaurants;
import com.camut.pageModel.PageFilter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
public class RestaurantsDaoTest {

	@Autowired
	private RestaurantsDao restaurantsDao;
	
	/**
	 * @Title: getAllRestaurants
	 * @Description: 查询所有的商家
	 * @param:    
	 * @return: List<Restaurants>
	 */
	@Test
	public void getAllRestaurants() throws Exception{
		PageFilter pf = new PageFilter();
		pf.setOffset(0);
		pf.setLimit(1);
		List<Restaurants> rList = restaurantsDao.getAllRestaurants(pf);
		System.out.println(rList.size());
	}
	
}
