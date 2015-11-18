/**
 * 
 */
package com.camut.dao;

import java.util.List;

import com.camut.model.DistancePrice;
import com.camut.model.Restaurants;

/**
 * @ClassName DistancePriceDao.java
 * @author wangpin
 * @createtime Jun 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DistancePriceDao {

	/**
	 * @Title: getAllDistancePrice
	 * @Description: 获取商家所有的配送费信息
	 * @param:    Restaurants
	 * @return: DistancePrice
	 */
	public List<DistancePrice> getAllDistancePrice(Restaurants restaurants);
	
	/**
	 * @Title: addDistancePrice
	 * @Description: 增加配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	public int addDistancePrice(DistancePrice distancePrice);
	
	/**
	 * @Title: updateDistancePrice
	 * @Description: 修改配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	public int updateDistancePrice(DistancePrice distancePrice);
	
	/**
	 * @Title: deleteDistancePrice
	 * @Description: 删除配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	public int deleteDistancePrice(DistancePrice distancePrice);
	
	/**
	 * @Title: getDistancePrice
	 * @Description: 获取配送费
	 * @param: restaurantsApiModel
	 * @return List<DistancePrice> 
	 */
	public List<DistancePrice> getDistancePrice(long restaurantId);
	
	
	
	
	
	
	
}
