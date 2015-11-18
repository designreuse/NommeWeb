package com.camut.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.camut.model.Consumers;
import com.camut.model.api.EvaluateApiModel;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ConsumersServicesTest {

	@Autowired private ConsumersService consumersService;
	
	@Autowired private EvaluateService evaluateService;
	/**
	 * @Description: 会员登录方法 
	 * @param: consumers
	 * @return: 
	 */
	//@Test
	public void login() throws Exception {
		Consumers consumers = new Consumers();
		consumers.setEmail("asd@qq.com");
		consumers.setPassword("123456");
		Consumers consumersRes = consumersService.login(consumers);
		if(consumersRes != null){
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
	}
	
	/**
	 * @Title: consumersLogin
	 * @Description: 会员登录方法 customer login
	 * @param: consumers   
	 * @return: int -1表示用户名不存在，0表示密码错误，1代表登录成功
	 * 				-1: no such customer, 0: wrong password, 1: success
	 */
	//@Test
	public void consumersLogin() throws Exception {
		Consumers consumers = new Consumers();
		consumers.setEmail("asd@qq.com");
		consumers.setPassword("123456");
		int flag = consumersService.consumersLogin(consumers);
		if(flag == 1){
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
	}
	
	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param:  consumers 对象  
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功   -1: registration failed
	 */
	//@Test
	public void addConsumers() throws Exception {
		Consumers consumers = new Consumers();
		consumers.setEmail("asda@qq.com");
		consumers.setPassword("123456");
		consumers.setFirstName("张");
		consumers.setLastName("飞");
		int flag = consumersService.addConsumers(consumers);
		if(flag == 1){
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
	}
	
	/**
	 * @Title: getConsumersByLoginName
	 * @Description: 根据登录名获取Consumers对象 get Consumers By Login Name
	 * @param:  loginName
	 * @return: Consumers类 return null if the consumer does not exist
	 */
	//@Test
	public void getConsumersByLoginName() throws Exception {
		String loginName = "asda@qq.com";
		Consumers consumers = consumersService.getConsumersByLoginName(loginName);
		if(consumers != null){
			System.out.println("ok");
		}else {
			System.out.println("no");
		}
	}
	
	@Test
	public void testReviews ()throws Exception {
		List<EvaluateApiModel> reviewsList = evaluateService.getEvaluatePagingByRestaurantId(16, 1, 10);
		long count = evaluateService.getCount();
		System.out.println(count);
		
		
	}
}
