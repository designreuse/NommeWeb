package com.camut.controller.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.camut.model.ConsumersAddress;
import com.camut.model.Restaurants;
import com.camut.model.api.DistancePriceApiModel;
import com.camut.service.ConsumersAddressService;
import com.camut.service.DistancePriceService;
import com.camut.utils.CommonUtil;
import com.camut.utils.StringUtil;

public class BaseAPiModel {
	
	@Autowired
	private ConsumersAddressService consumersAddressService;
	
	@Autowired
	private DistancePriceService distancePriceService;

	//protected ResultApiModel ram = new ResultApiModel();
	protected double getLogistic(String addressID,Restaurants restaurants,double orderPrice) {
			//计算距离配送费
			if (StringUtil.isNotEmpty(addressID)) {
				ConsumersAddress consumersAddress = consumersAddressService.getAddressById(Long.parseLong(addressID));
				double lng1 = consumersAddress.getLng();
				double lat1 = consumersAddress.getLat();
				//计算距离
				double distance = CommonUtil.getDistance(lat1, lng1, restaurants.getRestaurantLat(), restaurants.getRestaurantLng());
				//获取当前商家所有种类的送餐费
				List<DistancePriceApiModel> distancePriceList = distancePriceService.getDistancePrice(restaurants.getUuid());
				for (DistancePriceApiModel distancePrice : distancePriceList) {
					 //判断在某个距离区间内
					if (distance <= distancePrice.getEndDistance() && distance > distancePrice.getStartDistance()) {
						if (orderPrice>=distancePrice.getOrderPrice()) {
							return distancePrice.getUpPrice();
						}
						else{
							return distancePrice.getNotupPrice();
						}
						}
					}
				}
			return 0;
			}
	}

