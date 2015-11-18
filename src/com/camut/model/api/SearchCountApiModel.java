package com.camut.model.api;

public class SearchCountApiModel {

	private String restaurantLng;//经度
	private String restaurantLat;//纬度纬度
	private int isPickup=1;// 是否自取（0：是，1：否）
	private int isDiscount;//是否有优惠（0：是，1：否）
	private int isOpen;//是否在营业（0：是，1：否）
	private int isReservation=1;// 是否预定（0：是，1：否）
	private int isDelivery=1;// 是否外带（0：是，1：否）
	private int classificationId;//餐厅类型（1：china，4：pizza，8：Italian）
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
	public int getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(int isPickup) {
		this.isPickup = isPickup;
	}
	public int getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(int isDiscount) {
		this.isDiscount = isDiscount;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public int getIsReservation() {
		return isReservation;
	}
	public void setIsReservation(int isReservation) {
		this.isReservation = isReservation;
	}
	public int getIsDelivery() {
		return isDelivery;
	}
	public void setIsDelivery(int isDelivery) {
		this.isDelivery = isDelivery;
	}
	public int getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}
	
}
