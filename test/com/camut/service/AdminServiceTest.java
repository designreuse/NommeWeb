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
import com.camut.model.Admin;
import com.camut.pageModel.PageAdminUser;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AdminServiceTest {

	@Autowired
	private AdminService adminService;
	
	/**
	 * @Title: AdminLogin
	 * @Description: 管理员登录方法 customer login
	 * @param: consumers   
	 * @return: int -1表示登录名不存在，0表示密码错误，1代表登录成功
	 * 				-1: no such customer, 0: wrong password, 1: success
	 */
	@Test
	public void adminLogin() throws Exception {
		String email="zyf@qq.com";
		String password="1231223";
		int admin = adminService.adminLogin(email, password);
		if(admin == -1){
			System.out.println("登录名不存在");
		}else if(admin == 1){
			System.out.println("登录成功");
		}else if(admin == 0){
			System.out.println("密码错误");
		}
	}
	
	/**
	 * @Title: getAdminByEmail
	 * @Description: 获得登录信息对象
	 * @param:  email
	 * @return: Admin
	 */
	//@Test
	public void getAdminByLoginname() throws Exception{
		String email="zyf@qqa.com";
		PageAdminUser admin = adminService.getAdminByLoginname(email);
		if(admin != null){
			System.out.println(admin.getFirstName()+admin.getLastName());
		}else {
			System.out.println("登录名不存在");
		}
	}
	
	/**
	 * @Title: addAdmin
	 * @Description: 创建管理员
	 * @param:  admin
	 * @return: int -1表示注册失败 ，1表示注册成功
	 */
	//@Test
	public void addAdmin() throws Exception {
		Admin admin = new Admin();
		admin.setFirstName("z");
		admin.setLastName("f");
		admin.setLoginname("zyf@qq.com");
		admin.setPassword("123");
		int flag = adminService.addAdmin(admin);
		if (flag == 1) {
			System.out.println("注册成功");
		} else if (flag == -1) {
			System.out.println("注册失败");
		} 
	}
	
	/**
	 * @Description: 修改管理员信息 modify Admin
	 * @param @param pageAdminUser
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-6
	 */
	//@Test
	public void modifyAdmin() throws Exception {
		PageAdminUser pageAdminUser = new PageAdminUser();
		pageAdminUser.setLoginname("zf@qq.com");
		pageAdminUser.setPassword("1212");
		pageAdminUser.setFirstName("d");
		pageAdminUser.setLastName("f");
		pageAdminUser.getName();
		pageAdminUser.setAge("10");
		pageAdminUser.setSex("1");
		pageAdminUser.setId("1");
		PageMessage pm = new PageMessage();
		pm = adminService.modifyAdmin(pageAdminUser);
		if (pm.getSuccess()) {
			System.out.println("修改成功");
		} else {
			System.out.println("修改失败");
		}
	}
	
	/**
	 * @Title: deleteAdmin
	 * @Description: 删除管理员; delete an admin
	 * @param:  admin
	 * @return: -1表示删除失败; -1:failed
	 */
	//@Test
	/*public void deleteAdmin() throws Exception {
		int a = adminService.deleteAdmin(10);
		if (a == -1) {
			System.out.println("删除失败");
		} else if (a == 1) {
			System.out.println("删除成功");
		}
	}*/
	
	/**
	 * @Title: getAllUsers
	 * @Description: 获取所有admin用户
	 * @param:  admin
	 * @return: List
	 */
	//@Test
	public void getAllUsers() throws Exception{
		PageFilter pageFilter = new PageFilter();
		pageFilter.setOffset(0);
		pageFilter.setLimit(10);
		pageFilter.setSort("");
		pageFilter.setOrder("");
		List<PageAdminUser> adminUserList  = adminService.getAllAdminUsers();
		
		System.out.println(adminUserList);
	}
	
	/**
	 * 修改密码
	 */
	//@Test
	public void resetPassword() throws Exception{
		String loginname="zyf@qq.com";
		String newPassword="222";
		PageMessage pm = new PageMessage();
		pm = adminService.resetPassword(loginname, newPassword);
		if (pm.getSuccess()) {
			System.out.println("修改成功");
		} else {
			System.out.println("修改失败");
		}
	}
	
	/**
	 * @Description: 验证用户名是否存在; check if the login email exists
	 * @param  loginname (email)
	 * @return int  
	 * @author wj
	 * @date 2015-6-5
	 */
	//@Test
	public void loginnameExists() throws Exception {
		String loginname = "zyf@aqq.com";
		int flag = adminService.loginnameExists(loginname);
		if (flag == 0) {
			System.out.println("用户名不存在");
		} else if (flag == 1) {
			System.out.println("用户名已存在");
		}
	}
	
	/**
	 * @Description: 验证用户名是否在被其他ID中使用 ; 
	 * 				check if the loginname(email) used by other admins
	 * @param @param loginname (email)
	 * @param @param id (the id from the database)
	 * @return int  
	 * @author wj
	 * @date 2015-6-8
	 */
	//@Test
	public void loginnameIsUsedInOtherId() throws Exception{
		String loginname="zyf@qq.com";
		String id="15";
		int flag = adminService.loginnameIsUsedInOtherId(loginname, id);
		if (flag == 0) {
			System.out.println("用户名没有被使用");
		} else if (flag == 1) {
			System.out.println("用户名已经被使用");
		}
	}
	
}
