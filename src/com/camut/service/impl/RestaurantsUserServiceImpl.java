/**
 * 
 */
package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.RestaurantsUserDao;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageRestaurantUser;
import com.camut.pageModel.PageRestaurantAdmins;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.MD5Util;
import com.camut.utils.StringUtil;

/**
 * @ClassName RestaurantsUserServiceImpl.java
 * @author wangpin
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class RestaurantsUserServiceImpl implements RestaurantsUserService {

	@Autowired
	private RestaurantsUserDao restaurantsUserDao;// 自动注入RestaurantsUserDao

	public RestaurantsUser Login(RestaurantsUser restaurantsUser){
		if (restaurantsUser.getCode() != null && restaurantsUser.getCode().length() > 0) {
			RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUseByLoginName(restaurantsUser.getCode());			
			if (restaurantsUser2 != null && restaurantsUser2.getPassword().equals(MD5Util.md5(restaurantsUser.getPassword()))) {
				return restaurantsUser2;
			}
		}		
		return null;
	};
	
	/**
	 * @Title: restaurantsUserLogin
	 * @Description: 商家登陆
	 * @param:    RestaurantsUser
	 * @return:-2普通员工不能登陆，-1用户名不存在，0密码错误，1登陆成功，2待审核状态不能登陆，3商家不存在
	 */
	@Override
	public int restaurantsUserLogin(RestaurantsUser restaurantsUser) {
		if (restaurantsUser != null && restaurantsUser.getCode().length() > 0) {
			RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUseByLoginName(restaurantsUser.getCode());
			if (restaurantsUser2 != null) {// 用户名存在
				if (restaurantsUser2.getStatus() == 0) {// 状态正常
					if (restaurantsUser2.getType() == 1) {// 是管理员
						if (restaurantsUser2.getRestaurants().getStatus() == 0 || restaurantsUser2.getRestaurants().getStatus() == 3) {// 商家状态正常
							if (restaurantsUser2.getPassword().equals(MD5Util.md5(restaurantsUser.getPassword()))) {// 密码正确
								restaurantsUserDao.updateLastLoginTime(restaurantsUser2);// 修改最后一次登陆时间
								return 1;
							} else {// 密码错误
								return 0;
							}
						} else {
							return 3;// 商家已经不在本平台
						}
					} else {
						return -2;// 普通员工不能登录
					}
				} else {
					if (restaurantsUser2.getStatus() == 1 && restaurantsUser2.getType() == 1) {// 待审核状态
						return 2;
					}
				}
			}
		}
		return -1;// 用户不存在
	}

	/**
	 * @Title: addRestaurantsUser
	 * 
	 * @Description: 新增商家员工
	 * 
	 * @param: restaurantsUser
	 * 
	 * @return: int -1操作失败 1操作成功
	 */
	@Override
	public int addRestaurantsUser(RestaurantsUser restaurantsUser) {
		if (restaurantsUser != null) {
			restaurantsUser.setPassword(MD5Util.md5(restaurantsUser.getPassword()));
			restaurantsUser.setCreatetime(new Date());
			restaurantsUser.setUuid(StringUtil.getUUID());
			return restaurantsUserDao.addRestaurantsUser(restaurantsUser);
		}
		return -1;
	}

	/**
	 * @Title: checkLoginNameUnique
	 * 
	 * @Description: 检查管理员用户名是否唯一
	 * 
	 * @param: loginName
	 * 
	 * @return: int -1不唯一，1唯一
	 */
	@Override
	public int checkLoginNameUnique(RestaurantsUser restaurantsUser) {
		if (restaurantsUser != null && restaurantsUser.getCode() != null && restaurantsUser.getCode().length() > 0) {
			if (restaurantsUser.getUuid() != null) {// 修改资料
				RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUseByLoginNameForCheck(restaurantsUser.getCode());
				if (restaurantsUser2 != null) {// 存在用户名
					if (restaurantsUser.getUuid().equals(restaurantsUser2.getUuid())) {// 没修改，不判断
						return 1;
					}
				} else {// 不存在用户名
					return 1;
				}
			} else {// 注册管理员
				RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUseByLoginNameForCheck(restaurantsUser.getCode());
				if (restaurantsUser2 == null) {// 不存在相同用户名
					return 1;
				}
			}
		}

		return -1;
	}

	/**
	 * @Title: deleteRestaurantsUser
	 * 
	 * @Description: 通过主键删除店铺员工
	 * 
	 * @param: long
	 * 
	 * @return: int -1删除失败，1删除成功
	 */
	@Override
	public int deleteRestaurantsUser(String restaurantUserUuid) {
		RestaurantsUser restaurantsUser = restaurantsUserDao.getRestaurantsUserByUuid(restaurantUserUuid);
		if (restaurantsUser != null) {
			return restaurantsUserDao.deleteRestaurantsUser(restaurantsUser);
		}
		return -1;
	}

	/**
	 * @Title: getRestaurantsUserByUuid
	 * 
	 * @Description: 通过主键查找商家员工
	 * 
	 * @param:
	 * 
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getRestaurantsUserByUuid(String restaurantUserUuid) {
		return restaurantsUserDao.getRestaurantsUserByUuid(restaurantUserUuid);
	}

	/**
	 * @Title: updateRestaurantsUser
	 * 
	 * @Description: 修改商家员工资料
	 * 
	 * @param: RestaurantsUser
	 * 
	 * @return: int
	 */
	@Override
	public int updateRestaurantsUser(RestaurantsUser restaurantsUser) {
		if (restaurantsUser != null) {
			RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUserByUuid(restaurantsUser.getUuid());
			if (restaurantsUser2 != null) {
				restaurantsUser.setRestaurants(restaurantsUser2.getRestaurants());
				restaurantsUser.setCreatetime(restaurantsUser2.getCreatetime());
				restaurantsUser.setLastLoginTime(restaurantsUser2.getLastLoginTime());
				restaurantsUser.setStatus(0);
				if (!restaurantsUser2.getPassword().equals(restaurantsUser.getPassword())) {// 判断密码是否修改过
					restaurantsUser.setPassword(MD5Util.md5(restaurantsUser.getPassword()));
				}
				long restaurantsUser2Id = restaurantsUser2.getId();
				BeanUtils.copyProperties(restaurantsUser, restaurantsUser2);
				restaurantsUser2.setId(restaurantsUser2Id);
				restaurantsUser2.setModon(new Date());
				return restaurantsUserDao.UpdateRestaurantsUser(restaurantsUser2);
			}
		}
		return -1;
	}

	/**
	 * @Title: getRestaurantsByLoginName
	 * 
	 * @Description: 根据用户查找RestaurantsUser对象
	 * 
	 * @param: String
	 * 
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getRestaurantsByLoginName(String loginName) {
		return restaurantsUserDao.getRestaurantsUseByLoginName(loginName);
	}

	/**
	 * @Title: getRestaurantsByLoginName
	 * 
	 * @Description: 查找所有的员工，数据表格用的
	 * 
	 * @param:
	 * 
	 * @return: RestaurantsUser
	 */
	@Override
	public List<PageRestaurantUser> getAllRestaurantUser(Restaurants restaurants) {
		List<PageRestaurantUser> list = new ArrayList<PageRestaurantUser>();
		if (restaurants != null) {
			List<RestaurantsUser> restaurantsUsers = restaurantsUserDao.getAllRestaurantsUser(restaurants);
			for (RestaurantsUser restaurantsUser : restaurantsUsers) {
				PageRestaurantUser pageRestaurantUser = new PageRestaurantUser();
				BeanUtils.copyProperties(restaurantsUser, pageRestaurantUser);
				list.add(pageRestaurantUser);
			}
		}
		return list;
	}

	/**
	 * @Title: checkLoginNameForEmployee
	 * 
	 * @Description: 验证员工工号，一个店内工号不能重复,管理员账号不能重复
	 * 
	 * @param: String Restaurants
	 * 
	 * @return: int -1不唯一，1唯一
	 */
	@Override
	public int checkLoginNameForEmployee(RestaurantsUser restaurantsUser, Restaurants restaurants) {
		if (restaurantsUser != null && restaurantsUser.getUuid() != null) {// 修改用户
			if (restaurantsUser.getCode() != null && restaurantsUser.getCode().length() > 0) {// 工号不为空
				RestaurantsUser restaurantsUser1 = restaurantsUserDao.getEmployee(restaurantsUser.getCode(), restaurants);
				//RestaurantsUser restaurantsUser1 = restaurantsUserDao.getRestaurantsUseByLoginName(restaurantsUser.getCode());
				if (restaurantsUser1 != null) {// 用户名存在
					return -1;
				} else {// 用户名不存在
					return 1;
				}
			}
		} else {// 增加用户
			if (restaurantsUser.getCode() != null && restaurantsUser.getCode().length() > 0) {
				RestaurantsUser restaurantsUser1 = restaurantsUserDao.getEmployee(restaurantsUser.getCode(), restaurants);
				if (restaurantsUser1 == null) {
					return 1;
				}
			}
		}
		return -1;
	}

	/**
	 * @Title: getAllRestaurantsAdmin
	 * @Description: 获取所有商家的管理员
	 * @param:
	 * @return List<PageRestaurantsAdmin>
	 */
	/*
	 * public List<PageRestaurantAdmins> getAllRestaurantsAdmin(){
	 * List<RestaurantsUser> restaurantsUserList =
	 * restaurantsUserDao.getAllrestaurantsAdmin(); List<PageRestaurantAdmins>
	 * adminList = new ArrayList<PageRestaurantAdmins>(); for (RestaurantsUser
	 * restaurantsUser : restaurantsUserList) { PageRestaurantAdmins prAdmin =
	 * new PageRestaurantAdmins(); prAdmin.setId(restaurantsUser.getId());
	 * 
	 * 
	 * }
	 * 
	 * return null; }
	 */

	/**
	 * @Title: getRestaurantsUsersByRestaurantUuid
	 * @Description: 通过商家id获取该商家的所有员工
	 * @param: @param restaurantId
	 * @return List<PageRestaurantAdmins>
	 */
	public List<PageRestaurantAdmins> getRestaurantsUsersByRestaurantUuid(String restaurantUuid) {
		List<RestaurantsUser> list = restaurantsUserDao.getRestaurantsUsersByRestaurantUuid(restaurantUuid);
		List<PageRestaurantAdmins> pageAdminList = new ArrayList<PageRestaurantAdmins>();
		for (RestaurantsUser restaurantsUser : list) {
			PageRestaurantAdmins admin = new PageRestaurantAdmins();
			BeanUtils.copyProperties(restaurantsUser, admin);
			admin.setRestaurantUserUuid(restaurantsUser.getUuid());
			pageAdminList.add(admin);
		}
		return pageAdminList;
	}

	@Override
	/**
	 * @Title: auditRestaurantAdmin
	 * @Description: 审核商家的管理员
	 * @param: id
	 * @param: statu
	 * @return void  
	 */
	public int auditRestaurantAdmin(String restaurantUserUuid, int statu, String operatorName) {
		RestaurantsUser restaurantsUser = restaurantsUserDao.getAnyRestaurantsUserByUuid(restaurantUserUuid);
		restaurantsUser.setStatus(statu);// 设置修改状态
		restaurantsUser.setModby(operatorName);// 设置操作人
		restaurantsUser.setModon(new Date());
		int temp = restaurantsUserDao.UpdateRestaurantsUser(restaurantsUser);
		return temp;
	}

	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param: RestaurantsUser
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser saveTokenAndType(RestaurantsUser restaurantsUser) {
		RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUseByLoginName(restaurantsUser.getCode());
		restaurantsUser2.setDevicetype(restaurantsUser.getDevicetype());
		restaurantsUser2.setToken(restaurantsUser.getToken());
		return restaurantsUserDao.saveTokenAndType(restaurantsUser2);
	}
	
	/**
	 * @Description: 实现用户密码重置; reset password
	 * @param String loginname
	 * @param String newPassword
	 * @return String: message  
	 */
	public PageMessage resetPassword(String loginname, String newPassword){
		PageMessage pm = new PageMessage();
		RestaurantsUser restaurantsUser = restaurantsUserDao.getRestaurantsUseByLoginName(loginname);
		if(restaurantsUser!= null){
			restaurantsUser.setPassword(MD5Util.md5(newPassword));
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}

	/**
	 * @Title: appRestaurantsUserLogin
	 * @Description: 商家员工登陆
	 * @param:    restaurantsUser对象
	 * @return: ram -1用户名不存在，0密码错误，1登陆成功，2待审核状态
	 */
	@Override
	public int appRestaurantsUserLogin(RestaurantsUser restaurantsUser) {
		if (restaurantsUser != null && restaurantsUser.getCode().length() > 0) {
			RestaurantsUser restaurantsUser2 = restaurantsUserDao.getRestaurantsUseByLoginName(restaurantsUser.getCode());
			if (restaurantsUser2 != null) {// 用户名存在
				//保存token
				if(StringUtil.isNotEmpty(restaurantsUser.getToken())){
					restaurantsUser2.setToken(restaurantsUser.getToken());
					restaurantsUserDao.UpdateRestaurantsUser(restaurantsUser2);
				}
				if (restaurantsUser2.getStatus() == 0) {// 状态正常
					if (restaurantsUser2.getPassword().equals(MD5Util.md5(restaurantsUser.getPassword()))) {// 密码正确
						restaurantsUserDao.updateLastLoginTime(restaurantsUser2);// 修改最后一次登陆时间
						return 1;
					} else {// 密码错误
						return 0;
					}
				} else {
					if (restaurantsUser2.getStatus() == 1
							&& restaurantsUser2.getType() == 1) {// 待审核状态
						return 2;
					}
				}
			}
		}
		return -1;
	}

	/**
	 * @Title: getAllRestaurantsUser
	 * @Description: 获取所有的餐厅员工
	 * @param:    
	 * @return: List<RestaurantsUser>
	 */
	@Override
	public List<RestaurantsUser> getAllRestaurantsUser(Restaurants restaurants) {
		return restaurantsUserDao.getAllRestaurantsUser(restaurants);
	}

}
