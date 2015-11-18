package com.camut.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.AdminDao;
import com.camut.model.Admin;

/**
 * @daoimpl ConsumersDaoImpl.java
 * @author wangpin
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class AdminDaoImpl extends BaseDao<Admin> implements AdminDao {

	private int count;
	
	public int getCount(){
		return count;
	}
	
	/**
	 * @Title: getAdminByLoginName
	 * @Description: 根据登录名获取Admin对象; get the admin by login name(email)
	 * @param: 含有登录名的Admin对象; param is email(email is the login name)
	 * @return: Admin类
	 */
	@Override
	public Admin getAdminByLoginname(String loginname) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginname", loginname);
		String hql = "from Admin a where a.loginname=:loginname and a.state=0";
		Admin admin = this.get(hql, map);
		return admin;
	}
	
	/**
	 * @Title: getAdminByLoginName
	 * @Description: 根据id获取Admin对象; get the admin by login name(email)
	 * @param: 含有登录名的Admin对象; param is email(email is the login name)
	 * @return: Admin类
	 */
	@Override
	public Admin getAdminById(long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		String hql = "from Admin a where a.id=:id and a.state=0";
		Admin admin = this.get(hql, map);
		return admin;
	}
	
	/** 
	 * @Description: 验证用户名是否在被其他ID中使用
	 * @param    
	 * @return void  
	 * @author wj
	 * @date 2015-6-8
	 */
	@Override
	public Admin getAdminByLoginnameAndId(String loginname,long id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginname", loginname);
		map.put("id", id);
		String hql = "from Admin a where a.loginname=:loginname and a.state=0 and a.id!=:id";
		Admin admin = this.get(hql, map);
		return admin;
	}

	/**
	 * @Title: uodateLastLoginDate
	 * @Description: 管理员登录修改最后一次登陆时间; update the admin's last login time
	 * @param: admin
	 * @return: void
	 */
	@Override
	public void updateLastLoginDate(Admin admin) {
		if (admin != null) {
			admin.setModon(new Date());
			try {
				this.update(admin);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: addAdmin
	 * @Description: 创建管理员; add a new admin
	 * @param: admin
	 * @return: int -1表示注册失败 ，0表示会员email已存在，1表示注册成功; int -1:failed, 0:the email
	 * exist, 1: succeeded
	 */
	@Override
	public int addAdmin(Admin admin) {
		if (admin != null) {
			try {
				this.save(admin);
				return 1;
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}
	
	/**
	 * @Description: 修改管理员信息
	 * @param @param admin
	 * @param @return   
	 * @return int  
	 * @author wj
	 * @date 2015-6-6
	 */
	@Override
	public int modifyAdmin(Admin admin) {
		try {
			this.update(admin);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: deleteAdmin
	 * @Description: 删除管理员; delete an admin
	 * @param: admin
	 * @return: -1表示删除失败; -1:failed
	 */
	@Override
	public int deleteAdmin(Admin admin) {
		try {
			this.update(admin);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: getAllUsers
	 * @Description: 获取所有用户
	 * @param:  admin
	 * @return: List
	 */
	@Override
	public List<Admin> getAllUsers(){
		String hql = "from Admin a where a.state=0 order by a.loginname asc";
		return this.find(hql);
	}
	
}
