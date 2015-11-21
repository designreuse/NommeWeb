package com.camut.pageModel;

public class PageRestaurantName {
	private long id;//主键
	private String restaurantUuid;
	private String restaurantName;// 店名
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	
	

}
