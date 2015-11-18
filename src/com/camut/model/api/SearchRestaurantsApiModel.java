package com.camut.model.api;

public class SearchRestaurantsApiModel {

	private String restaurantLng;// 经度
	private String restaurantLat;// 纬度
	private String avgPrice;// 平均价格
	private String distance;// 外送距离
	private String stars;// 评分
	private String letter;
	private String classification;// 类型名称
	private String isDiscount;// 折扣条数（用来判断是否有折扣）
	private String isOpen;// 用来判断是否在营业时间内。
	private String isReservation;// 是否预定
	private String isDelivery;// 是否外卖
	private String classificationId;
	public String getRestaurantLng() {
		return restaurantLng;
	}
	public void setRestaurantLng(String restaurantLng) {
		this.restaurantLng = restaurantLng;
	}
	public String getRestaurantLat() {
		return restaurantLat;
	}
	public void setRestaurantLat(String restaurantLat) {
		this.restaurantLat = restaurantLat;
	}
	public String getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(String avgPrice) {
		this.avgPrice = avgPrice;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getLetter() {
		return letter;
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public String getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
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
	public String getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(String classificationId) {
		this.classificationId = classificationId;
	}
	@Override
	public String toString() {
		return "SearchRestaurantsApiModel [restaurantLng=" + restaurantLng + ", restaurantLat=" + restaurantLat + ", avgPrice=" + avgPrice
				+ ", distance=" + distance + ", stars=" + stars + ", letter=" + letter + ", classification=" + classification + ", isDiscount="
				+ isDiscount + ", isOpen=" + isOpen + ", isReservation=" + isReservation + ", isDelivery=" + isDelivery + ", classificationId="
				+ classificationId + "]";
	}
	
	
	
	
}
