/**
 * 
 */
package com.camut.service;

import java.util.List;

import com.camut.model.RestaurantTable;
import com.camut.model.Restaurants;
import com.camut.model.api.RestaurantTableApiModel;
import com.camut.pageModel.PageTable;

/**
 * @ClassName RestaurantTableService.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantTableService {

	/**
	 * @Title: getRestaurantTable
	 * @Description: 获取商家的桌位信息
	 * @param:    Restaurants
	 * @return: RestaurantTable
	 */
	public List<PageTable> getRestaurantTable(Restaurants restaurants);
	
	/**
	 * @Title: addRestaurantTable
	 * @Description: 增加桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	public int addRestaurantTable(RestaurantTable restaurantTable);
	
	/**
	 * @Title: updateRestaurantTable
	 * @Description:修改桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	public int updateRestaurantTable(RestaurantTable restaurantTable);
	
	/**
	 * @Title: getRestaurantTableById
	 * @Description: 通过id找桌位信息
	 * @param:    long
	 * @return: PageTable
	 */
	public PageTable getRestaurantTableById(long id);
	
	/**
	 * @Title: deleteRestaurantTable
	 * @Description: 删除桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	public int deleteRestaurantTable(RestaurantTable restaurantTable);
	
	/**
	 * @Title: getgetRestaurantTableListRestaurantTableList
	 * @Description: 获取商家的桌位信息
	 * @param:    Restaurants
	 * @return: RestaurantTable
	 */
	public List<RestaurantTableApiModel> getRestaurantTableList(Restaurants restaurants,String orderDate);
}
