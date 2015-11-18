/**
 * 
 */
package com.camut.dao;

import java.util.List;

import com.camut.model.ViewRestaurant;
import com.camut.model.api.SearchRestaurantsApiModel;
import com.camut.pageModel.PageFilter;

/**
 * @ClassName ViewRestaurantDao.java
 * @author wangpin
 * @createtime Jul 9, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ViewRestaurantDao {

	/**
	 * @Title: getRestaurants
	 * @Description: 获取商家信息
	 * @param:    
	 * @return: List<ViewRestaurant>
	 */
	public List<ViewRestaurant> getRestaurants(ViewRestaurant viewRestaurant,PageFilter pf);
	
	/**
	 * @Title: getTotal
	 * @Description: 获取商家总数量
	 * @param:    
	 * @return: int
	 */
	public long getTotal();
	
	/**
	 * @Title: getRestaurantsById
	 * @Description: 通过商家Id获取商家
	 * @param: long id
	 * @return ViewRestaurant  
	 */
	public ViewRestaurant getRestaurantsById(long id);
	
	/**
	 * @Title: getViewRestaurants
	 * @Description: 搜索
	 * @param:    viewRestaurant pf
	 * @return: List<ViewRestaurant>
	 */
	public List<ViewRestaurant> getViewRestaurants(SearchRestaurantsApiModel viewRestaurant,PageFilter pf);
	
	
	
}
