package com.camut.pageModel;

public class PageFavourites {
	private Integer restaurantId;//商家Id
	private Integer favouritesId;//收藏Id
	private Double avgPrice;//平均消费等级
	private String restaurantName;//商家名称
	private Double avgStars;//平均评分
	private String isDelivery;//是否外送
	private String isPickup;//是否自取
	private String isReservation;//是否预定
	private Double distance;// 最大外卖距离
	private Double deliveryPrice;// 外卖起步价
	private String imageUrl;//商家图片地址

	
	
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Integer getFavouritesId() {
		return favouritesId;
	}
	public void setFavouritesId(Integer favouritesId) {
		this.favouritesId = favouritesId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Double getAvgStars() {
		return avgStars;
	}
	public void setAvgStars(Double avgStars) {
		this.avgStars = avgStars;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(String isPickup) {
		this.isPickup = isPickup;
	}
	public String getIsReservation() {
		return isReservation;
	}
	public void setIsReservation(String isReservation) {
		this.isReservation = isReservation;
	}
	public String getIsDelivery() {
		return isDelivery;
	}
	public void setIsDelivery(String isDelivery) {
		this.isDelivery = isDelivery;
	}
	
	public Double getDeliveryPrice() {
		return deliveryPrice;
	}
	public void setDeliveryPrice(Double deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	public Double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
	
	
}
