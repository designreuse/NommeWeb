/**
 * 
 */
package com.camut.pageModel;

/**
 * @ClassName PageViewRestaurant.java
 * @author wangpin
 * @createtime Jul 9, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageViewRestaurant {

	private Long id;//商家id
	private String restaurantName;//商家名称
	private String restaurantContact; //商家介绍
	private String restaurantPhone; //商家电话
	private String restaurantAddress;//商家地址
	private String logourl;//商家logo
	private Double score;//评分
	private Double distance;//外送距离
	private Integer deliverTime;// 送餐所需时间
	private String classificationName;// 类型名称
	private Integer isdelivery;// 是否外卖
	private Integer ispickup;// 是否自取
	private Integer isreservation;// 是否预定
	private Double apart;//输入地址与餐厅之间的距离
	private Double restaurantLng;// 经度
	private Double restaurantLat;// 纬度
	private Double avgPrice;// 平均价格
	private Long scoreCount;// 评分人数
	private String restaurantUuid;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantContact() {
		return restaurantContact;
	}
	public void setRestaurantContact(String restaurantContact) {
		this.restaurantContact = restaurantContact;
	}
	public String getRestaurantPhone() {
		return restaurantPhone;
	}
	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Integer getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(Integer deliverTime) {
		this.deliverTime = deliverTime;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public Integer getIsdelivery() {
		return isdelivery;
	}
	public void setIsdelivery(Integer isdelivery) {
		this.isdelivery = isdelivery;
	}
	public Integer getIspickup() {
		return ispickup;
	}
	public void setIspickup(Integer ispickup) {
		this.ispickup = ispickup;
	}
	public Integer getIsreservation() {
		return isreservation;
	}
	public void setIsreservation(Integer isreservation) {
		this.isreservation = isreservation;
	}
	public Double getApart() {
		return apart;
	}
	public void setApart(Double apart) {
		this.apart = apart;
	}
	public Double getRestaurantLng() {
		return restaurantLng;
	}
	public void setRestaurantLng(Double restaurantLng) {
		this.restaurantLng = restaurantLng;
	}
	public Double getRestaurantLat() {
		return restaurantLat;
	}
	public void setRestaurantLat(Double restaurantLat) {
		this.restaurantLat = restaurantLat;
	}
	public Double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	public Long getScoreCount() {
		return scoreCount;
	}
	public void setScoreCount(Long scoreCount) {
		this.scoreCount = scoreCount;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	
	
	
	
	
	
}
