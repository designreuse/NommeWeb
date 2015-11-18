package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.AdminDao;
import com.camut.dao.AreasDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Admin;
import com.camut.pageModel.PageAdminUser;
import com.camut.pageModel.PageMessage;
import com.camut.service.AdminService;
import com.camut.utils.MD5Util;

/**
 * @ServiceImpl AdminServiceImpl.java
 * @author zhangyunfei
 * @createtime May 28, 2015 
 * @author
 * @updateTime
 * @memo
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDao adminDao;//自动注入adminDao
	@Autowired
	private AreasDao areasDao;//自动注入areasDao
	/**
	 * @Title: AdminLogin
	 * @Description: 管理员登录方法 admin login
	 * @param: consumers   
	 * @return: int -1表示登录名不存在，0表示密码错误，1代表登录成功
	 * 				-1: no such admin, 0: wrong password, 1: success
	 */
	@Override
	public int adminLogin(String username,String password) {
		Admin admin = adminDao.getAdminByLoginname(username);
		if (admin != null) {
			if (admin.getPassword().equals(MD5Util.md5(password))) {
				adminDao.updateLastLoginDate(admin);
				return GlobalConstant.LOGIN_OK;
			} else {
				return GlobalConstant.PASSWORD_ERROR;
			}
		}
		return -1;
	}
	
	/**
	 * @Title: getAdminByEmail
	 * @Description: 获得登录信息对象; get an admin by email
	 * @param:  email
	 * @return: Admin
	 */
	public PageAdminUser getAdminByLoginname(String loginname){
		Admin admin = adminDao.getAdminByLoginname(loginname);
		if(admin != null){
			PageAdminUser pau = new PageAdminUser();
			pau.setId(admin.getId()+"");
			pau.setLoginname(admin.getLoginname());
			pau.setFirstName(admin.getFirstName());
			pau.setLastName(admin.getLastName());
			pau.setName(admin.getFirstName()+" "+admin.getLastName());
			pau.setAge(admin.getAge()+"");
			pau.setPassword(admin.getPassword());
			pau.setSex(admin.getSex()+"");
			pau.setUsertype(admin.getUsertype()+"");
			pau.setModby(admin.getModby());
			return pau;
		}else{
			return null;
		}
	}

	/**
	 * @Title: addAdmin
	 * @Description: 创建管理员; add a new admin
	 * @param:  admin
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功
	 *          int -1:failed, 0:the email exist, 1: succeeded
	 */
	@Override
	public int addAdmin(Admin admin) {
		/*if(adminDao.addAdmin(admin) != null){
			return 0;
		}*/
		return adminDao.addAdmin(admin);
	}
	/**
	 * @Description: 修改管理员信息 modify Admin
	 * @param @param pageAdminUser
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-6
	 */
	@Override
	public PageMessage modifyAdmin(PageAdminUser pageAdminUser) {
		Admin admin= adminDao.getAdminById(Long.parseLong(pageAdminUser.getId()));
		admin.setId(Long.parseLong(pageAdminUser.getId()));
		admin.setLoginname(pageAdminUser.getLoginname());
		admin.setFirstName(pageAdminUser.getFirstName());
		admin.setLastName(pageAdminUser.getLastName());
		admin.setUsertype(Integer.parseInt(pageAdminUser.getUsertype()));
		String newPassword = pageAdminUser.getPassword();
		if(newPassword != admin.getPassword()){
			newPassword = MD5Util.md5(newPassword);
			admin.setPassword(newPassword);
		}
		PageMessage pm = new PageMessage();
		int temp = adminDao.modifyAdmin(admin);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.UPDATE_ADMIN_FAILED);
		}
		return pm;
	}

	/**
	 * @Title: deleteAdmin
	 * @Description: 删除管理员; delete admin
	 * @param:  admin
	 * @return: -1表示删除失败,1表示成功; -1: failed ，1：success
	 */
	@Override
	public PageMessage deleteAdmin(long id, String operatorName) {
		PageMessage pm = new PageMessage();
		Admin admin = adminDao.getAdminById(id);
		int a = GlobalConstant.DISABLE;
		admin.setState(a);
		admin.setModby(operatorName);
		admin.setModon(new Date());
		int temp = adminDao.deleteAdmin(admin);
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getAllUsers 
	 * @Description: 获取所有admin用户; get all admins
	 * @param:  admin
	 * @return: PageModel (it is a list of admins)
	 */
	@Override
	public List<PageAdminUser> getAllAdminUsers() {
		List<Admin> list = adminDao.getAllUsers();
		List<PageAdminUser> pageAdminList = new ArrayList<PageAdminUser>();
		for (Admin admin : list) {
			PageAdminUser adminUser = new PageAdminUser();
			adminUser.setId(admin.getId()+"");
			adminUser.setLoginname(admin.getLoginname());
			adminUser.setPassword(admin.getPassword());
			adminUser.setFirstName(admin.getFirstName());
			adminUser.setLastName(admin.getLastName());
			adminUser.setName(admin.getFirstName()+" "+admin.getLastName());
			adminUser.setUsertype(admin.getUsertype()+"");
			pageAdminList.add(adminUser);
		}
		return pageAdminList;
	}

	/**
	 * reset the password and get a corresponding message
	 */
	@Override
	public PageMessage resetPassword(String loginname, String newPassword) {
		PageMessage pm = new PageMessage();
		Admin admin = adminDao.getAdminByLoginname(loginname);
		if(admin!= null){
			admin.setPassword(MD5Util.md5(newPassword));
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Description: 验证用户名是否存在; check if the login email exists
	 * @param  loginname (email)
	 * @return int  
	 * @author wj
	 * @date 2015-6-5
	 */
	public int loginnameExists(String loginname){
		Admin admin = adminDao.getAdminByLoginname(loginname);
		if(admin==null){
			return GlobalConstant.LOGINNAME_NOT_EXIST;
		}
		return GlobalConstant.LOGINNAME_EXIST;
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
	public int loginnameIsUsedInOtherId(String loginname,String id){
		Admin admin = adminDao.getAdminByLoginnameAndId(loginname,Long.parseLong(id));
		if(admin==null){
			return GlobalConstant.LOGINNAME_NOT_EXIST;
		}
		return GlobalConstant.LOGINNAME_EXIST;
		
	}
	
	

}
