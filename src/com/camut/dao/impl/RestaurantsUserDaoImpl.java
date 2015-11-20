/**
 * 
 */
package com.camut.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.RestaurantsUserDao;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;


/**
 * @ClassName RestaurantsUserDaoImpl.java
 * @author wangpin
 * @createtime May 28, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class RestaurantsUserDaoImpl extends BaseDao<RestaurantsUser> implements RestaurantsUserDao {
	

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.camut.dao.RestaurantsUserDao#restaurantsUserLogin(com.camut.model
	 * .RestaurantsUser)
	 */
	@Override
	public RestaurantsUser getRestaurantsUseByLoginName(String loginName) {
		String hql = "from RestaurantsUser r where r.code=:code and r.status=0";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", loginName);
		return this.get(hql, map);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.camut.dao.RestaurantsUserDao#updateLastLoginTime(com.camut.model.
	 * RestaurantsUser)
	 */
	@Override
	public void updateLastLoginTime(RestaurantsUser restaurantsUser) {
		restaurantsUser.setLastLoginTime(new Date());
		try {
			this.update(restaurantsUser);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/** @Title: addRestaurantsUser
	 * @Description: 新增商家员工; add a new restaurant staff
	 * @param:    restaurantsUser
	 * @return:   int -1操作失败，1操作成功; -1:failed, 1:success
	 */
	@Override
	public int addRestaurantsUser(RestaurantsUser restaurantsUser) {
		try {
			this.save(restaurantsUser);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	/** @Title: deleteRestaurantsUser
	 * @Description: 通过主键删除店铺员工
	 * @param:    long
	 * @return:   int -1删除失败，1删除成功
	 */
	@Override
	public int deleteRestaurantsUser(RestaurantsUser restaurantsUser) {
		restaurantsUser.setStatus(-1);
		try {
			this.update(restaurantsUser);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	/**
	 * @Title: getRestaurantsUserById
	 * @Description: 通过主键查找商家员工
	 * @param:    long
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getRestaurantsUserByUuid(String restaurantUserUuid) {
		String hql = "from RestaurantsUser r where r.uuid=:restaurantUserUuid and r.status=0";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurantUserUuid", restaurantUserUuid);
		return this.get(hql, map);
	}
	/**
	 * @Title: getRestaurantsUserById
	 * @Description: 通过主键查找商家员工,用于系统管理员审核商家管理员
	 * @param:    long
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getAnyRestaurantsUserByUuid(String restaurantUserUuid) {
		String hql = "from RestaurantsUser r where r.uuid=:restaurantUserUuid and r.status>=0";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurantUserUuid", restaurantUserUuid);
		return this.get(hql, map);
	}

	/**
	 * @Title: updateRestaurantsUser
	 * @Description: 修改商家员工资料
	 * @param:    RestaurantsUser
	 * @return: int -1操作失败 1操作成功
	 */
	@Override
	public int UpdateRestaurantsUser(RestaurantsUser restaurantsUser) {
		try {
			this.update(restaurantsUser);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	/**
	 * @Title: restaurantsUserLogin
	 * @Description: 商家员工登陆,根据登陆名查找对象,验证用的
	 * @param:    String
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getRestaurantsUseByLoginNameForCheck(String loginName) {
		String hql = "from RestaurantsUser r where r.code=:code and (r.status=0 or r.status=1)";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", loginName);
		return this.get(hql, map);
	}

	/**
	 * @Title: getAllRestaurantsUser
	 * @Description: 获取所有的餐厅员工
	 * @param:    
	 * @return: List<RestaurantsUser>
	 */
	@Override
	public List<RestaurantsUser> getAllRestaurantsUser(Restaurants restaurants) {
		String hql = "from RestaurantsUser r where r.restaurants=:restaurants and r.status=0 order by r.type,r.code";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurants", restaurants);
		return this.find(hql,map);
	}

	/**
	 * @Title: checkLoginNameForEmployee
	 * @Description: 根据工号，获取当前餐厅的员工
	 * @param:    String Restaurants
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getEmployee(String code, Restaurants restaurants) {
		String restaurantUuid = restaurants.getUuid();
		String hql = "from RestaurantsUser r where r.restaurants.uuid=:restaurantUuid and r.status=0 and r.code=:code";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		map.put("code", code);
		return this.get(hql, map);
	}

	/**
	 * @Title: getAdministrator
	 * @Description: 根据登陆名获取管理员对象
	 * @param:    String
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser getAdministrator(String loginName) {
		String hql = "from RestaurantsUser r where r.code=:code and r.status=0 and r.type=1";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", loginName);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getAllRestaurantsAdmin
	 * @Description: admin 管理后台 获取对应商家id的商家的所有员工
	 * @param: 
	 * @return List<PageRestaurantsAdmin>  
	 */
	public List<RestaurantsUser> getRestaurantsUsersByRestaurantUuid(String restaurantUuid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		String hql = "from RestaurantsUser r where r.restaurants.uuid=:restaurantUuid and r.status>=0 order by r.code";
		return this.find(hql,map);
	}

	/**
	 * @Title: saveTokenAndType
	 * @Description: 用户登录保存token和type
	 * @param:    RestaurantsUser
	 * @return: RestaurantsUser
	 */
	@Override
	public RestaurantsUser saveTokenAndType(RestaurantsUser restaurantsUser) {
		try {
			this.update(restaurantsUser);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
}
