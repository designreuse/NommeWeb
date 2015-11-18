package com.camut.service;

import java.util.List;
import com.camut.model.Admin;
import com.camut.pageModel.PageAdminUser;
import com.camut.pageModel.PageMessage;

/**
 * @Service AdminService.java
 * @author zhangyunfei
 * @createtime May 28, 2015 
 * @author
 * @updateTime
 * @memo
 */
public interface AdminService {

	/**
	 * @Title: AdminLogin
	 * @Description: 管理员登录方法 customer login
	 * @param: consumers   
	 * @return: int -1表示登录名不存在，0表示密码错误，1代表登录成功
	 * 				-1: no such customer, 0: wrong password, 1: success
	 */
	public int adminLogin(String email,String password);
	
	/**
	 * @Title: getAdminByEmail
	 * @Description: 获得登录信息对象
	 * @param:  email
	 * @return: Admin
	 */
	public PageAdminUser getAdminByLoginname(String loginname);
	
	
	/**
	 * @Title: addAdmin
	 * @Description: 创建管理员
	 * @param:  admin
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功
	 */
	public int addAdmin(Admin admin);
	
	/**
	 * @Description: 修改管理员信息
	 * @param @param pageAdminUser
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-6
	 */
	public PageMessage modifyAdmin(PageAdminUser pageAdminUser);
	
	/**
	 * @Title: deleteAdmin
	 * @Description: 删除管理员
	 * @param:  admin
	 * @return: -1表示删除失败,1表示成功; -1: failed ，1：success
	 */
	public PageMessage deleteAdmin(long id, String operatorName);
	
	/**
	 * @Title: getAllUsers
	 * @Description: 获取所有admin用户
	 * @param:  admin
	 * @return: List
	 */
	
	public List<PageAdminUser> getAllAdminUsers();
	/**
	 * 
	 * @Description: 重置密码
	 * @param @param loginname
	 * @param @param newPassword
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-3
	 *
	 */
	public PageMessage resetPassword(String loginname, String newPassword);
	
	/**
	 * @Description: 验证用户名是否存在
	 * @param @param loginname
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-5
	 */
	public int loginnameExists(String loginname);
	
	/**
	 * @Description: 验证用户名是否在被其他ID中使用
	 * @param @param loginname
	 * @param @param id
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-8
	 */
	public int loginnameIsUsedInOtherId(String loginname,String id);
	
	
	
}
