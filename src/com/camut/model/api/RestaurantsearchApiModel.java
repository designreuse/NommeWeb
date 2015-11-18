package com.camut.model.api;

public class RestaurantsearchApiModel {

	private long restaurantId=0;
	private String logourl;// logo图片的存放地址
	private String restaurantName="";// 店名
	private double avgPrice=12;// 人均小费价格
	private String restaurantAddress="";// 地址
	private double stars=1;// 评级
	private int chainid=1;// 连锁店
	private double distance;//距离
	private int isOpen;//是否在营业（0：是，1：否）
	private int isDiscount;//是否有优惠（0：是，1：否）
	private int classificationId;//餐厅类型（1：china，4：pizza，8：Italian）
	private int isDelivery=1;// 是否外带（0：是，1：否）
	private int isPickup=1;// 是否自取（0：是，1：否）
	private int isReservation=1;// 是否预定（0：是，1：否）
	private int total;//总数量
	private int currentPageIndex=1;//第几页
	public long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
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
	public int getChainid() {
		return chainid;
	}
	public void setChainid(int chainid) {
		this.chainid = chainid;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}
	public int getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(int isDiscount) {
		this.isDiscount = isDiscount;
	}
	public int getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}
	public int getIsDelivery() {
		return isDelivery;
	}
	public void setIsDelivery(int isDelivery) {
		this.isDelivery = isDelivery;
	}
	public int getIsPickup() {
		return isPickup;
	}
	public void setIsPickup(int isPickup) {
		this.isPickup = isPickup;
	}
	public int getIsReservation() {
		return isReservation;
	}
	public void setIsReservation(int isReservation) {
		this.isReservation = isReservation;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	
}
