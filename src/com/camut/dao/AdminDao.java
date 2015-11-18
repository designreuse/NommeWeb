package com.camut.dao;

import java.util.List;
import com.camut.model.Admin;

/**
 * @dao AdminDao.java
 * @author zhangyunfei 
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface AdminDao {

	/**
	 * @Title: getAdminByLoginName
	 * @Description: 根据登录名获取Admin对象 get admin by login name (email)
	 * @param:  含有登录名的Admin对象 ; email(login name)
	 * @return: Admin类 
	 */
	public Admin getAdminByLoginname(String username);
	
	/** 
	 * @Description: 验证用户名是否在被其他ID中使用
	 * @param    
	 * @return void  
	 * @author wj
	 * @date 2015-6-8
	 */
	public Admin getAdminByLoginnameAndId(String loginname,long id);
	
	/** 
	 * @Description: 通过id获取对象
	 * @param    
	 * @return void  
	 * @author wj
	 * @date 2015-6-8
	 */
	public Admin getAdminById(long id);
	
	/**
	 * @Title: uodateLastLoginDate
	 * @Description: 管理员登录修改最后一次登陆时间 update the admin's last login date
	 * @param:  admin
	 * @return: void
	 */
	public void updateLastLoginDate (Admin admin);
	
	/**
	 * @Title: addAdmin
	 * @Description: 创建管理员
	 * @param:  admin
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功
	 *          int -1:failed, 0:the email exist, 1: succeeded
	 */
	public int addAdmin(Admin admin);
	
	/**
	 * @Description: 修改管理员信息
	 * @param @param admin
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-6
	 */
	public int modifyAdmin(Admin admin);
	
	/**
	 * @Title: deleteAdmin
	 * @Description: 删除管理员; delete an admin
	 * @param:  admin
	 * @return: -1表示删除失败; -1:failed
	 */
	public int deleteAdmin(Admin admin);
	
	/**
	 * @Title: getAllUsers
	 * @Description: 获取所有用户
	 * @param:  admin
	 * @return: List
	 */
	public List<Admin> getAllUsers();
	
	/**
	 * @Description: 获取信息条数
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-8
	 */
	public int getCount();
	
	
}
