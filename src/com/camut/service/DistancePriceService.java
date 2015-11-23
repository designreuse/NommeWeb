/**
 * 
 */
package com.camut.service;

import java.util.List;

import com.camut.model.DistancePrice;
import com.camut.model.Restaurants;
import com.camut.model.api.DistancePriceApiModel;
import com.camut.pageModel.PageDistancePrice;

/**
 * @ClassName DistancePriceService.java
 * @author wangpin
 * @createtime Jun 28, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DistancePriceService {

	/**
	 * @Title: getAllDistancePrice
	 * @Description: 获取商家所有的配送费信息
	 * @param:    Restaurants
	 * @return: DistancePrice
	 */
	public List<PageDistancePrice> getAllDistancePrice(Restaurants restaurants);
	
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
	 * @return List<DistancePriceApiModel> 
	 */
	public List<DistancePriceApiModel> getDistancePrice(String restaurantUuid);
	
	/**
	 * @Title: getOneDistanceByFee
	 * @Description: 工具商家id 订单菜品价格 经纬度 获取一个配送费
	 * @param: @param restaurantId
	 * @param: @param subTotal
	 * @param: @param consumerLng
	 * @param: @param consumerLat
	 * @return double  
	 */
	public double getOneDistanceByFee(String restaurantUuid, double subTotal, double consumerLng, double consumerLat);
	
	
	
}
