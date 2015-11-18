/**
 * 
 */
package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.DistancePriceDao;
import com.camut.dao.RestaurantsDao;
import com.camut.model.DistancePrice;
import com.camut.model.Restaurants;
import com.camut.model.api.DistancePriceApiModel;
import com.camut.pageModel.PageDistancePrice;
import com.camut.service.DistancePriceService;
import com.camut.utils.CommonUtil;

/**
 * @ClassName DistancePriceServiceImpl.java
 * @author wangpin
 * @createtime Jun 28, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class DistancePriceServiceImpl implements DistancePriceService {

	@Autowired
	private DistancePriceDao distancePriceDao;
	@Autowired private RestaurantsDao restaurantsDao;
	
	/**
	 * @Title: getAllDistancePrice
	 * @Description: 获取商家所有的配送费信息
	 * @param:    Restaurants
	 * @return: DistancePrice
	 */
	@Override
	public  List<PageDistancePrice> getAllDistancePrice(Restaurants restaurants) {
		List<PageDistancePrice> list = new ArrayList<PageDistancePrice>();
		if(restaurants!=null){
			List<DistancePrice> distancePrices = distancePriceDao.getAllDistancePrice(restaurants);
			for (DistancePrice distancePrice : distancePrices) {
				PageDistancePrice pageDistancePrice = new PageDistancePrice();
				BeanUtils.copyProperties(distancePrice, pageDistancePrice);
				list.add(pageDistancePrice);
			}
		}
		return list;
	}

	/**
	 * @Title: addDistancePrice
	 * @Description: 增加配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	@Override
	public int addDistancePrice(DistancePrice distancePrice) {
		if (distancePrice!=null) {
			return distancePriceDao.addDistancePrice(distancePrice);
		}
		return -1;
	}

	/**
	 * @Title: updateDistancePrice
	 * @Description: 修改配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	@Override
	public int updateDistancePrice(DistancePrice distancePrice) {
		if (distancePrice!=null) {
			return distancePriceDao.updateDistancePrice(distancePrice);
		}
		return -1;
	}

	/**
	 * @Title: deleteDistancePrice
	 * @Description: 删除配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	@Override
	public int deleteDistancePrice(DistancePrice distancePrice) {
		if (distancePrice!=null) {
			return distancePriceDao.deleteDistancePrice(distancePrice);
		}
		return -1;
	}

	/**
	 * @Title: getDistancePrice
	 * @Description: 获取配送费
	 * @param: restaurantsApiModel
	 * @return List<DistancePriceApiModel> 
	 */
	@Override
	public List<DistancePriceApiModel> getDistancePrice(long restaurantId) {
		List<DistancePrice> dpList = distancePriceDao.getDistancePrice(restaurantId);
		List<DistancePriceApiModel> dpamList = new ArrayList<DistancePriceApiModel>();
		for (DistancePrice distancePrice : dpList) {
			DistancePriceApiModel dpam = new DistancePriceApiModel();
			dpam.setStartDistance(distancePrice.getStartDistance());
			dpam.setEndDistance(distancePrice.getEndDistance());
			dpam.setOrderPrice(distancePrice.getOrderPrice());
			dpam.setUpPrice(distancePrice.getUpPrice());
			dpam.setNotupPrice(distancePrice.getNotupPrice());
			dpamList.add(dpam);
		}
		return dpamList;
	}
	

	/**
	 * @Title: getOneDistanceByFee
	 * @Description: 根据商家id 订单菜品价格 经纬度 获取一个配送费
	 * @param: @param restaurantId
	 * @param: @param subTotal
	 * @param: @param consumerLng
	 * @param: @param consumerLat
	 * @return DistancePrice  
	 */
	public double getOneDistanceByFee(long restaurantId, double subTotal, double consumerLng, double consumerLat){
		double disPrice = 0.0;
		Restaurants restaurants = restaurantsDao.getRestaurantsById(restaurantId);
		if(restaurants!=null){
			double resLng = restaurants.getRestaurantLng();
			double resLat = restaurants.getRestaurantLat();
			double distance = CommonUtil.getDistance(consumerLat, consumerLng, resLng, resLat);
			//List<DistancePrice> list = distancePriceDao.getDistancePrice(restaurantId);//getOneDistanceByFee(restaurantId, subTotal, distance);
			Set<DistancePrice> set = restaurants.getDistancePricesSet();
			for (DistancePrice distancePrice : set) {
				if(distance==0 && distance==distancePrice.getStartDistance()){//距离为0的情况
					if(subTotal<distancePrice.getOrderPrice()){//实际总价未达到订单价格;
						disPrice = distancePrice.getNotupPrice();
						break;
					}else{
						disPrice = distancePrice.getUpPrice();
						break;
					}
				}
				if(distance<=distancePrice.getEndDistance() && distance>distancePrice.getStartDistance()){
					if(subTotal<distancePrice.getOrderPrice()){//实际总价未达到订单价格;
						disPrice = distancePrice.getNotupPrice();
						break;
					}else{
						disPrice = distancePrice.getUpPrice();
						break;
					}
				}
			}
		}
		
		return disPrice;
	}
	
	
	

}
