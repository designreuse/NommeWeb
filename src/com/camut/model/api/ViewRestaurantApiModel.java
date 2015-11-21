package com.camut.model.api;

public class ViewRestaurantApiModel {

	private String restaurantUuid;  //商家id
	private String logourl = "";  // logo图片
	private String restaurantName = "";   // 店名
	private Double avgPrice = 0.0;   // 人均小费价格
	private Double stars = 0.0;   // 评级
	private Double distance=0.0; //距离
	private Long isOpen = (long)0; //是否在营业（0：是，1：否）
	private int isDiscount=0;
	private String avgPriceStr="";
	
	public String getAvgPriceStr() {
		return avgPriceStr;
	}
	public void setAvgPriceStr(String avgPriceStr) {
		this.avgPriceStr = avgPriceStr;
	}
	//private Integer currentPageIndex;  //当前页数
	//private Integer total;  //总数量
	
	public String getLogourl() {
		return logourl;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
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
	public Double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	public Double getStars() {
		return stars;
	}
	public void setStars(Double stars) {
		this.stars = stars;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Long getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(Long isOpen) {
		this.isOpen = isOpen;
	}
	/*public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}*/
	public int getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(int isDiscount) {
		this.isDiscount = isDiscount;
	}
	
	
}
