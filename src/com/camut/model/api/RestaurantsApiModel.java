package com.camut.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @entity RestaurantsApiModel . 
 * @author zyf	
 * @createTime 2015-05-31
 * @author 
 * @updateTime 
 * @memo 
 */
public class RestaurantsApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6370444968153891237L;
	private String restaurantUuid="";
	private String restaurantName="";// 店名
	private String restaurantContact="";// 餐厅负责人
	private String restaurantPhone="";// 联系电话
	private String restaurantAddress="";// 地址
	private double restaurantLng=1;// 经度
	private double restaurantLat=2;// 纬度
	private Date createtime=new Date();// 创建时间
	private Integer status=0;// 商家状态,-1为无效，0为有效
	private double avgPrice=12;// 人均小费价格
	private String features="";// 特征
	private Integer isdelivery=1;// 是否外带（0：是，1：否）
	private Integer ispickup=1;// 是否自取（0：是，1：否）
	private Integer isreservation=1;// 是否预定（0：是，1：否）
	private Integer chainid=1;// 连锁店
	private String logourl;// logo图片的存放地址
	private Integer deliverTime;// 送餐所需时间

	public Integer getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(Integer deliverTime) {
		this.deliverTime = deliverTime;
	}
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
	public double getRestaurantLng() {
		return restaurantLng;
	}
	public void setRestaurantLng(double restaurantLng) {
		this.restaurantLng = restaurantLng;
	}
	public double getRestaurantLat() {
		return restaurantLat;
	}
	public void setRestaurantLat(double restaurantLat) {
		this.restaurantLat = restaurantLat;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}
	public String getFeatures() {
		return features;
	}
	public void setFeatures(String features) {
		this.features = features;
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
	public Integer getChainid() {
		return chainid;
	}
	public void setChainid(Integer chainid) {
		this.chainid = chainid;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	
	
}
