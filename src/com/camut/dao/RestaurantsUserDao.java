package com.camut.dao;

import java.util.List;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;

/**
 * @ClassName RestaurantsUserDao.java
 * @author wangpin
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantsUserDao {

	/**
	 * @Title: restaurantsUserLogin
	 * @Description: 商家员工登陆,根据登陆名查找对象
	 * @param:    restaurantsUser对象
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getRestaurantsUseByLoginName(String loginName);
	
	/**
	 * @Title: updateLastLoginTime
	 * @Description: 修改最后一次登陆时间
	 * @param:    restaurantsUser
	 * @return: void
	 */
	public void updateLastLoginTime(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: addRestaurantsUser
	 * @Description: 新增商家员工
	 * @param:    restaurantsUser
	 * @return: int -1增加失败 1操作成功
	 */
	public int addRestaurantsUser(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: deleteRestaurantsUser
	 * @Description: 通过主键删除店铺员工
	 * @param:    RestaurantsUser
	 * @return: int
	 */
	public int deleteRestaurantsUser(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: getRestaurantsUserById
	 * @Description: 通过主键查找商家员工
	 * @param:    
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getRestaurantsUserByUuid(String restaurantUserUuid);
	
	/**
	 * @Title: getRestaurantsUserById
	 * @Description: 通过主键查找商家员工,用于系统管理员审核商家管理员
	 * @param: long
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getAnyRestaurantsUserByUuid(String restaurantUserUuid);
	
	/**
	 * @Title: updateRestaurantsUser
	 * @Description: 修改商家员工资料
	 * @param:    RestaurantsUser
	 * @return: int -1操作失败 1操作成功
	 */
	public int UpdateRestaurantsUser(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: restaurantsUserLogin
	 * @Description: 商家员工登陆,根据登陆名查找对象,验证用的
	 * @param:    String
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getRestaurantsUseByLoginNameForCheck(String loginName);
	
	/**
	 * @Title: getAllRestaurantsUser
	 * @Description: 获取所有的餐厅员工
	 * @param:    
	 * @return: List<RestaurantsUser>
	 */
	public List<RestaurantsUser> getAllRestaurantsUser(Restaurants restaurants);
	
	/**
	 * @Title: checkLoginNameForEmployee
	 * @Description: 根据工号，获取当前餐厅的员工
	 * @param:    String Restaurants
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getEmployee(String code,Restaurants restaurants);
	
	/**
	 * @Title: getAdministrator
	 * @Description: 根据登陆名获取管理员对象
	 * @param:    String
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getAdministrator(String loginName);
	
	/**
	 * @Title: getAllRestaurantsAdmin
	 * @Description: 获取所有商家的管理员
	 * @param: 
	 * @return List<PageRestaurantsAdmin>  
	 */
	public List<RestaurantsUser> getRestaurantsUsersByRestaurantUuid(String restaurantUuid);
	
	
	
	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    RestaurantsUser
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser saveTokenAndType(RestaurantsUser restaurantsUser);
	
}
