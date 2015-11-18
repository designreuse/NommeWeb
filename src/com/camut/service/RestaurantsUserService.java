/**
 * 
 */
package com.camut.service;


import java.util.List;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageRestaurantAdmins;
import com.camut.pageModel.PageRestaurantUser;

/**
 * @service RestaurantsUserService.java
 * @author wangpin
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantsUserService {

	public RestaurantsUser Login(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: restaurantsUserLogin
	 * @Description: 商家登陆
	 * @param:    RestaurantsUser
	 * @return:-2普通员工不能登陆，-1用户名不存在，0密码错误，1登陆成功，2待审核状态不能登陆，3商家不存在
	 */
	public int restaurantsUserLogin(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: addRestaurantsUser
	 * @Description: 新增商家员工
	 * @param:    restaurantsUser
	 * @return: int -1增加失败 1操作成功
	 */
	public int addRestaurantsUser(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: checkLoginNameUnique
	 * @Description: 检查管理员用户名是否唯一
	 * @param:    loginName
	 * @return: int -1不是唯一，1唯一
	 */
	public int checkLoginNameUnique(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: deleteRestaurantsUser
	 * @Description: 通过主键删除店铺员工
	 * @param:    id
	 * @return: int
	 */
	public int deleteRestaurantsUser(long id);
	
	/**
	 * @Title: getRestaurantsUserById
	 * @Description: 通过主键查找商家员工
	 * @param:    
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getRestaurantsUserById(long id);
	
	/**
	 * @Title: updateRestaurantsUser
	 * @Description: 修改商家员工资料
	 * @param:    RestaurantsUser
	 * @return: int -1操作失败 1操作成功
	 */
	public int updateRestaurantsUser(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: getRestaurantsByLoginName
	 * @Description: 根据用户查找RestaurantsUser对象
	 * @param:    String
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser getRestaurantsByLoginName(String loginName);
	
	/**
	 * @Title: getRestaurantsByLoginName
	 * @Description: 查找所有的员工，数据表格用的
	 * @param:    
	 * @return: RestaurantsUser
	 */
	public List<PageRestaurantUser> getAllRestaurantUser(Restaurants restaurants);
	
	/**
	 * @Title: checkLoginNameForEmployee
	 * @Description: 验证员工工号，一个店内工号不能重复
	 * @param:    String Restaurants
	 * @return: int
	 */
	public int checkLoginNameForEmployee(RestaurantsUser restaurantsUser,Restaurants restaurants);
	
	
	/**
	 * @Title: getAllRestaurantsAdmin
	 * @Description: 获取所有商家的管理员
	 * @param: 
	 * @return List<PageRestaurantsAdmin>  
	 */
	//public List<PageRestaurantAdmins> getAllRestaurantsAdmin();
	
	/**
	 * @Title: getRestaurantsAdminByRestaurantId
	 * @Description: 通过商家id获取该商家的管理员
	 * @param: @param restaurantId
	 * @return List<PageRestaurantAdmins>  
	 */
	public List<PageRestaurantAdmins> getRestaurantsUsersByRestaurantId(String restaurantId);
	
	/**
	 * @Title: auditRestaurantAdmin
	 * @Description: 审核商家的管理员
	 * @param: id
	 * @param: statu
	 * @return void  
	 */
	public int auditRestaurantAdmin(long id, int statu, String operatorName);
	
	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    RestaurantsUser
	 * @return: RestaurantsUser
	 */
	public RestaurantsUser saveTokenAndType(RestaurantsUser restaurantsUser);
	
	/**
	 * @Description: 实现用户密码重置; reset password
	 * @param String loginname
	 * @param String newPassword
	 * @return String: message  
	 */
	public PageMessage resetPassword(String loginname, String newPassword);
	
	/**
	 * @Title: appRestaurantsUserLogin
	 * @Description: 商家员工登陆
	 * @param:    restaurantsUser对象
	 * @return: ram -1用户名不存在，0密码错误，1登陆成功，2待审核状态
	 */
	public int appRestaurantsUserLogin(RestaurantsUser restaurantsUser);
	
	/**
	 * @Title: getAllRestaurantsUser
	 * @Description: 获取所有的餐厅员工
	 * @param:    
	 * @return: List<RestaurantsUser>
	 */
	public List<RestaurantsUser> getAllRestaurantsUser(Restaurants restaurants);
}
