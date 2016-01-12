package com.camut.model;

// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ViewRestaurantId entity. @author MyEclipse Persistence Tools
 */
@javax.persistence.Entity
@Table(name = "view_restaurant")
public class ViewRestaurant implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2699138106972007292L;
	private String restaurantUuid;//商家唯一编号
	private String restaurantName;// 商家名称
	private String restaurantContact; // 商家介绍
	private String restaurantPhone; // 商家电话
	private String restaurantAddress;// 商家地址
	private Double restaurantLng;// 经度
	private Double restaurantLat;// 纬度
	private Timestamp createtime; // 创建时间
	// private Double stars; //评分
	private Integer status; // 商家状态
	private Double distance;// 外送距离
	private Double deliverPrice; //外送起步价格
	private Double avgPrice;// 平均价格
	private String features;
	private Integer isdelivery;// 是否外卖
	private Integer ispickup;// 是否自取
	private Integer isreservation;// 是否预定
	// private Timestamp modon;//修改时间
	// private String moddy; //修改人
	private Integer chainid;// 连锁店ID
	private Integer areaId;// 区域ID
	private String logourl;// 商家logo
	private String notice;// 商家通知
	private String classificationName;// 类型名称
	private Double score;// 评分
	private String areaName;// 区域名称（省）
	private Double pst;// 省税率
	private String PAreaName;// 联邦名称
	private Double gst;// 联邦税率
	private Integer taxMode;// 税率模式
	private Long discountNum;// 折扣条数（用来判断是否有折扣）
	private Long opentime;// 用来判断是否在营业时间内。
	private Integer deliverTime;// 送餐所需时间
	private Long scoreCount;// 评分人数
	private Double maxDiscount;

	// Constructors

	/** default constructor */
	public ViewRestaurant() {
	}

	// Property accessors
	
	@Id
	@Column(name = "restaurant_uuid")
	public String getRestaurantUuid() {
		return restaurantUuid;
	}

	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	

	@Column(name = "restaurant_name", length = 50)
	public String getRestaurantName() {
		return this.restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	@Column(name = "restaurant_contact", length = 30)
	public String getRestaurantContact() {
		return this.restaurantContact;
	}

	public void setRestaurantContact(String restaurantContact) {
		this.restaurantContact = restaurantContact;
	}

	@Column(name = "restaurant_phone", length = 30)
	public String getRestaurantPhone() {
		return this.restaurantPhone;
	}

	public void setRestaurantPhone(String restaurantPhone) {
		this.restaurantPhone = restaurantPhone;
	}

	@Column(name = "restaurant_address", length = 200)
	public String getRestaurantAddress() {
		return this.restaurantAddress;
	}

	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}

	@Column(name = "restaurant_lng", precision = 18, scale = 5)
	public Double getRestaurantLng() {
		return this.restaurantLng;
	}

	public void setRestaurantLng(Double restaurantLng) {
		this.restaurantLng = restaurantLng;
	}

	@Column(name = "restaurant_lat", precision = 18, scale = 5)
	public Double getRestaurantLat() {
		return this.restaurantLat;
	}

	public void setRestaurantLat(Double restaurantLat) {
		this.restaurantLat = restaurantLat;
	}

	@Column(name = "createtime", length = 19)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "distance", precision = 18)
	public Double getDistance() {
		return this.distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Column(name = "avg_price", precision = 18)
	public Double getAvgPrice() {
		return this.avgPrice;
	}

	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}

	@Column(name = "features", length = 65535)
	public String getFeatures() {
		return this.features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	@Column(name = "isdelivery")
	public Integer getIsdelivery() {
		return this.isdelivery;
	}

	public void setIsdelivery(Integer isdelivery) {
		this.isdelivery = isdelivery;
	}

	@Column(name = "ispickup")
	public Integer getIspickup() {
		return this.ispickup;
	}

	public void setIspickup(Integer ispickup) {
		this.ispickup = ispickup;
	}

	@Column(name = "isreservation")
	public Integer getIsreservation() {
		return this.isreservation;
	}

	public void setIsreservation(Integer isreservation) {
		this.isreservation = isreservation;
	}

	@Column(name = "chainid")
	public Integer getChainid() {
		return this.chainid;
	}

	public void setChainid(Integer chainid) {
		this.chainid = chainid;
	}

	@Column(name = "area_id")
	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	@Column(name = "logourl", length = 200)
	public String getLogourl() {
		return this.logourl;
	}

	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}

	@Column(name = "notice", length = 65535)
	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Column(name = "classification_name", length = 50)
	public String getClassificationName() {
		return this.classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	@Column(name = "score", precision = 21, scale = 4)
	public Double getScore() {
		return this.score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	@Column(name = "area_name", length = 50)
	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	@Column(name = "pst", precision = 18)
	public Double getPst() {
		return this.pst;
	}

	public void setPst(Double pst) {
		this.pst = pst;
	}

	@Column(name = "p_area_name", length = 50)
	public String getPAreaName() {
		return this.PAreaName;
	}

	public void setPAreaName(String PAreaName) {
		this.PAreaName = PAreaName;
	}

	@Column(name = "gst", precision = 18)
	public Double getGst() {
		return this.gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	@Column(name = "tax_mode")
	public Integer getTaxMode() {
		return this.taxMode;
	}

	public void setTaxMode(Integer taxMode) {
		this.taxMode = taxMode;
	}

	@Column(name = "discountNum")
	public Long getDiscountNum() {
		return this.discountNum;
	}

	public void setDiscountNum(Long discountNum) {
		this.discountNum = discountNum;
	}

	@Column(name = "opentime")
	public Long getOpentime() {
		return this.opentime;
	}

	public void setOpentime(Long opentime) {
		this.opentime = opentime;
	}

	@Column(name = "deliver_time")
	public Integer getDeliverTime() {
		return this.deliverTime;
	}

	public void setDeliverTime(Integer deliverTime) {
		this.deliverTime = deliverTime;
	}

	@Column(name = "scoreCount")
	public Long getScoreCount() {
		return this.scoreCount;
	}

	public void setScoreCount(Long scoreCount) {
		this.scoreCount = scoreCount;
	}

	@Column(name="deliver_price")
	public Double getDeliverPrice() {
		return deliverPrice;
	}

	public void setDeliverPrice(Double deliverPrice) {
		this.deliverPrice = deliverPrice;
	}
	
	@Column(name="max_discount")
	public Double getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(Double maxDiscount) {
		this.maxDiscount = 	maxDiscount;
	}
}