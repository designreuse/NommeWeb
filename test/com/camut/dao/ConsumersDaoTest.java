package com.camut.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.camut.model.Consumers;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ConsumersDaoTest {

	@Autowired private ConsumersDao consumersDao;
	
	/**
	 * @Title: getConsumersByLoginName
	 * @Description: 根据登录名获取Consumers对象 get Consumers By Login Name
	 * @param:  loginName
	 * @return: Consumers类 return null if the consumer does not exist
	 */
	//@Test
	public void getConsumersByLoginName() throws Exception{
		Consumers consumers = consumersDao.getConsumersByLoginName("asd@qq.com");
		System.out.println(consumers.getFirstName()+consumers.getLastName());
	}
	
	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param:  consumers 对象  
	 * @return: int -1表示注册失败 ，1表示注册成功   -1: registration failed
	 */
	//@Test
	public void addConsumers() throws Exception {
		Consumers consumers = new Consumers();
		consumers.setLastName("asd11@qq.com");
		consumers.setPassword("1234565");
		int flag = consumersDao.addConsumers(consumers);
		if (flag == -1) {
			System.out.println("注册失败");
		} else if (flag == 1) {
			System.out.println("注册成功");
		} 
	}
	
	/**
	 * @Title: uodateLastLoginDate
	 * @Description: 会员登录修改最后一次登陆时间
	 * @param:    consumers
	 * @return: void
	 */
	//@Test
	public void uodateLastLoginDate() throws Exception {
		Consumers consumers = new Consumers();
		consumers.setLastName("asd11@qq.com");
		consumers.setPassword("1234565");
		consumersDao.updateLastLoginDate(consumers);
	}
	
	/**
	 * @Title: getByLoginName
	 * @Description: 会员登录
	 * @param:    consumers
	 * @return: Consumers
	 */
	//@Test
	public void getByLoginName() throws Exception{
		Consumers consumers = new Consumers();
		consumers.setEmail("asd@qq.com");
		Consumers c = consumersDao.getByLoginName(consumers);
		if(c != null){
			System.out.println("登录成功");
		}
	}
	
	/**
	 * @Title: getByLoginName
	 * @Description: 根据第三方ID获取Consumers对象
	 * @param:    mobileToken
	 * @return: Consumers
	 */
	//@Test
	public void getConsumersByOtherCode() throws Exception{
		String otherCode = "2";
		int loginType = 2;
		Consumers consumers = consumersDao.getConsumersByOtherCode(otherCode,loginType);
		if(consumers != null){
			System.out.println("ok");
		}
	}
	
	/**
	 * @Title: updateConsumers
	 * @Description: 用户信息修改
	 * @param:    consumers
	 * @return: int -1修改失败 1修改成功
	 */
	//@Test
	public void updateConsumers() throws Exception{
		Consumers consumers = new Consumers();
		consumers.setId((long)1);
		consumers.setLastName("asd11@qq.com");
		consumers.setPassword("1234565");
		consumers.setFirstName("Mike");
		consumers.setLastName("Rose");
		int flag = consumersDao.updateConsumers(consumers);
		if(flag == -1){
			System.out.println("修改失败");
		}else if (flag == 1) {
			System.out.println("修改成功");
		}
	}
	
	/**
	 * @Title: getConsumersById
	 * @Description: 通id查找用户
	 * @param:    id
	 * @return: Consumers
	 */
	//@Test
	/*public void getConsumersById() throws Exception{
		long id = 1;
		Consumers consumers = consumersDao.getConsumersById(id);
		if(consumers != null){
			System.out.println("ok");
		}
	}*/
	
	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	//@Test
/*	public void saveTokenAndType() throws Exception{
		Consumers consumers = new Consumers();
		consumers.setLastName("asd@qq.com");
		consumers.setPassword("1234565");
		Consumers consumers2 = consumersDao.saveTokenAndType(consumers);
		if(consumers2 != null){
			System.out.println("ok");
		}
	}
	*/
	/**
	 * @Title: updateTokenAndType
	 * @Description: 修改token和type
	 * @param:    consumers
	 * @return: Consumers
	 */
	//@Test
	public void updateTokenAndType() throws Exception{
		Consumers consumers = new Consumers();
		consumers.setId((long)1);
		consumers.setLastName("asd@qq.com");
		consumers.setPassword("1234565");
		consumers.setOtherCode("whgffggfdagf");
		consumersDao.updateTokenAndType(consumers);
	}
	
	/**
	 * @Title: addConsumerForNomme
	 * @Description: 增加会员
	 * @param:    Consumers
	 * @return: int -1增加失败，1增加成功
	 */
	@Test
	public void addConsumerForNomme() throws Exception{
		Consumers consumers = new Consumers();
		consumers.setLastName("asd@qq.com");
		consumers.setPassword("1234565");
		consumers.setFirstName("z");
		consumers.setLastName("s");
		int flag = consumersDao.addConsumerForNomme(consumers);
		if(flag == 1){
			System.out.println("增加成功");
		}else if (flag == -1) {
			System.out.println("");
		}
	}
}
