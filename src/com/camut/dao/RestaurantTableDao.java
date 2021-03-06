package com.camut.dao;

import java.util.List;
import com.camut.model.RestaurantTable;
import com.camut.model.Restaurants;
import com.camut.model.api.TableEntity;

/**
 * @ClassName RestaurantTableDao.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantTableDao {

	/**
	 * @Title: getRestaurantTable
	 * @Description: 获取商家的桌位信息
	 * @param:    Restaurants
	 * @return: RestaurantTable
	 */
	public List<RestaurantTable> getRestaurantTable(Restaurants restaurants);
	
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
	public RestaurantTable getRestaurantTableById(long id);
	
	/**
	 * @Title: deleteRestaurantTable
	 * @Description: 删除桌位信息
	 * @param:    RestaurantTable
	 * @return: int
	 */
	public int deleteRestaurantTable(RestaurantTable restaurantTable);
	
	/**
	 * @Title: getNumberOfRestaurantReservationOverlaps
	 * @Description: Gets the number of overlapping reservations for each table type.
	 * @param restaurantUuid
	 * @param reservationRequestDateString
	 * @return: List<TableEntity>
	 */
	public List<TableEntity> getNumberOfRestaurantReservationOverlaps(String restaurantUuid,String reservationRequestDateString);
}
