package com.camut.model.api;

import java.util.ArrayList;
import java.util.List;

public class CartHeaderRepeatApiModel {

	private Integer orderType;// 订单种类
	private String mobileToken;// 设备编号
	private String restaurantUuid;// 商家id
	private String consumerUuid;// 客户id
	private Integer type;//2：直接添加到购物车
	private List<CartItemRepeatApiModel> item = new ArrayList<CartItemRepeatApiModel>();
	
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getMobileToken() {
		return mobileToken;
	}
	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	public String getConsumerUuid() {
		return consumerUuid;
	}
	public void setConsumerUuid(String consumerUuid) {
		this.consumerUuid = consumerUuid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public List<CartItemRepeatApiModel> getItem() {
		return item;
	}
	public void setItem(List<CartItemRepeatApiModel> item) {
		this.item = item;
	}
	
}
