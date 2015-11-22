/**
 * 
 */
package com.camut.dao;

import java.util.List;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageFilter;

/**
 * @ClassName RestaurantsDao.java
 * @author wangpin
 * @createtime May 27, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantsDao {

	/*
	 * @Title: getAllRestaurants
	 * @Description: 查询所有的商家
	 * @param:    
	 * @return: List<Restaurants>
	 */
	public List<Restaurants> getAllRestaurants(PageFilter pf);
	
	/**
	 * @Title: getCount
	 * @Description: 获取总记录条数
	 * @param: 
	 * @return int  
	 */
	public long getCount();

	/*
	 * @Title: getRestaurantsById
	 * @Description: 通过商家id查找商家
	 * @param:    
	 * @return: Restaurants
	 */
	public Restaurants getRestaurantsById(long id);
	
	/*
	 * @Title: getRestaurantsByUuid
	 * @Description: 通过商家id查找商家
	 * @param:    
	 * @return: Restaurants
	 */
	public Restaurants getRestaurantsByUuid(String restaurantUuid);
	
	/*
	 * @Title: addRestaurants
	 * @Description: 增加商家
	 * @param:    restaurants
	 * @return: int 返回的主键
	 */
	public String addRestaurants(Restaurants restaurants);
	
	/*
	 * @Title: deleteRestaurants
	 * @Description: 删除商家
	 * @param:    long
	 * @return: int -1删除失败 1删除成功
	 */
	public int deleteRestaurants(Restaurants restaurants);
	
	/*
	 * @Title: updateRestaurants
	 * @Description: 修改商家资料
	 * @param:    Restaurants
	 * @return: int -1修改失败 1修改成功
	 */
	public int updateRestaurants(Restaurants restaurants);
	
	/*
	 * @Title: getRestaurantsByName
	 * @Description: 通过店名找商家
	 * @param:    String
	 * @return: Restaurants
	 */
	public Restaurants getRestaurantsByName(String restaurantsName);
	
	/*
	 * @Title: getRestaurantsByNameForCheck
	 * @Description: 店名找商家，验证用的
	 * @param:    String
	 * @return: Restaurants
	 */
	public Restaurants getRestaurantsByNameForCheck(String restaurantsName);
	
	/**
	 * @Title: getRestaurantByIdToAudit
	 * @Description: 通过id获取餐厅用于审核，不管是什么状态的都可以找到
	 * @param:id
	 * @return Restaurants  
	 */
	public Restaurants getRestaurantByUuidToAudit(String restaurantUuid);
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 获取商家信息到前台表格用于下拉框选择
	 * @param: 
	 * @return List<PageRestaurantSelect> 
	 */
	public List<Restaurants> getAllRestaurantsName();
	
}
