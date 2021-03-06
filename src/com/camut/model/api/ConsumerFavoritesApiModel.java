package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity CustomerFavoritesApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class ConsumerFavoritesApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3631454477320896811L;
	private String restaurantUuid="";//商家ID
	private String restaurantName="";// 店名
	private String restaurantAddress="";// 地址
	private double stars=0;// 评级
	
	
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public double getStars() {
		return stars;
	}
	public void setStars(double stars) {
		this.stars = stars;
	}
	
	
	
}
