package com.camut.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.camut.model.Admin;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AdminDaoTest {

	@Autowired private AdminDao adminDao;
	
	/**
	 * @Title: getAdminByLoginName
	 * @Description: 根据登录名获取Admin对象 get admin by login name (email)
	 * @param:  含有登录名的Admin对象 ; email(login name)
	 * @return: Admin类 
	 */
	//@Test
	public void getAdminByLoginname() throws Exception{
		String username = "zyf@qq.com";
		Admin admin = adminDao.getAdminByLoginname(username);
		System.out.println("ok");
		System.out.println(admin.getFirstName()+admin.getLastName());
	}
	
	/** 
	 * @Description: 验证用户名是否在被其他ID中使用
	 * @param    
	 * @return void  
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void getAdminByLoginnameAndId() throws Exception{
		String loginname = "zyf@qq.com";
		long id = 10;
		Admin admin = adminDao.getAdminByLoginnameAndId(loginname, id);
		if(admin != null){
			System.out.println("用户名已经被使用");
		}else {
			System.out.println("用户名没有被使用");
		}
	}
	
	/** 
	 * @Description: 通过id获取对象
	 * @param    
	 * @return void  
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void getAdminById() throws Exception{
		long id = 3;
		Admin admin = adminDao.getAdminById(id);
		if(admin != null){
			System.out.println(admin.getFirstName()+admin.getLastName());
		}else {
			System.out.println("用户不存在");
		}	
	}
	
	/**
	 * @Title: uodateLastLoginDate
	 * @Description: 管理员登录修改最后一次登陆时间 update the admin's last login date
	 * @param:  admin
	 * @return: void
	 */
	//@Test
	public void updateLastLoginDate() throws Exception{
		Admin admin = new Admin();
		admin.setLoginname("zyf@qq.com");
		admin.setPassword("123");
		adminDao.updateLastLoginDate(admin);
	}
	
	/**
	 * @Title: addAdmin
	 * @Description: 创建管理员
	 * @param:  admin
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功
	 *          int -1:failed, 0:the email exist, 1: succeeded
	 */
	//@Test
	public void addAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setLoginname("zyf@qq.com");
		admin.setFirstName("z");
		admin.setLastName("f");
		admin.setPassword("123");
		admin.setSex(1);
		admin.setAge(10);
		int flag = adminDao.addAdmin(admin);
		if (flag == 1) {
			System.out.println("注册成功");
		} else if (flag == -1) {
			System.out.println("注册失败");
		}
	}
	
	/**
	 * @Description: 修改管理员信息
	 * @param  admin
	 * @param    
	 * @return int  -1表示修改失败，1代表修改成功
	 * @author wj
	 * @date 2015-6-6
	 */
	//@Test
	public void modifyAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setFirstName("z"); 
		admin.setLastName("f");
		admin.setLoginname("zf@qq.com");
		admin.setPassword("123");
		admin.setSex(1);
		admin.setAge(10);
		int flag = adminDao.modifyAdmin(admin);
		if(flag == 1){
			System.out.println("修改成功");
		}else if (flag == -1) {
			System.out.println("修改失败");
		}
	}
	
	/**
	 * @Title: deleteAdmin
	 * @Description: 删除管理员; delete an admin
	 * @param: admin
	 * @return: -1表示删除失败; -1:failed
	 */
	//@Test
	public void deleteAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setFirstName("z");
		admin.setLastName("f");
		admin.setLoginname("zf@qq.com");
		admin.setPassword("123");
		admin.setSex(1);
		admin.setAge(3);
		admin.setId((long) 10);
		int flag = adminDao.deleteAdmin(admin);
		if (flag == 1) {
			System.out.println("删除成功");
		} else if (flag == -1) {
			System.out.println("删除失败");
		}
	}
	
	/**
	 * @Title: getAllUsers
	 * @Description: 获取所有用户
	 * @param:  admin
	 * @return: List
	 */
	//@Test
	public void getAllUsers() throws Exception {
		List<Admin> aList = adminDao.getAllUsers();
		System.out.println(aList.size());
	}
}
