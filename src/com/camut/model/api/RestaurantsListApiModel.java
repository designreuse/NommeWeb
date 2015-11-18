package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity RestaurantsListApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class RestaurantsListApiModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3035032156802298966L;
	private long restaurantId=0;
	private String logo=""; // logo
	private String restaurantName="";// 店名
	private double avgPrice=12;// 人均小费价格
	private String restaurantAddress="";// 地址
	private double stars=1;// 评级
	private Integer chainid=1;// 连锁店

	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
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
	public Integer getChainid() {
		return chainid;
	}
	public void setChainid(Integer chainid) {
		this.chainid = chainid;
	}

}
