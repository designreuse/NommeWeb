package com.camut.model;

// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity Restaurants .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "dat_restaurants")
public class Restaurants implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -3363966588190195756L;
	
	
	private Long id;//
	private String restaurantName;// 店名
	private String restaurantContact;// 餐厅负责人
	private String restaurantPhone;// 联系电话
	private String restaurantAddress;// 地址
	private Double restaurantLng;// 经度
	private Double restaurantLat;// 纬度
	private Date createtime;// 创建时间
	private Integer status;// 商家状态,-1为无效，0为有效(说明已经审核通过)，1待审核（新注册的商家状态为1）,2 冻结（未审核通过）
	private Double distance;// 最大外卖距离
	private Double deliverPrice;// 外卖起步价
	private Double avgPrice;// 人均xiaofei费价格
	private String features;// 特征,介绍
	private double stars;// 评分
	private Integer isdelivery;// 是否外带 1:是 0:否
	private Integer ispickup;// 是否自取 1:是 0:否
	private Integer isreservation;// 是否预定 1:是 0:否
	private Date modon;// 操作时间
	private String moddy;// 操作人
	private Integer chainid;// 连锁店
	private Set<Classification> classificationsSet = new HashSet<Classification>();// 与商家分类多对多的关系,单向关联
	// private Areas areas;// 与区域多对一,单向关联
	private ViewArea viewArea;// 与区域视图多对一单向关联
	private Set<RestaurantsUser> restaurantsUsersSet = new HashSet<RestaurantsUser>();// 与商家员工一对多
	private Set<Evaluate> evaluatesSet = new HashSet<Evaluate>();// 与店铺评价一对多的关系
	private Set<RestaurantsMenu> restaurantsMenusSet = new HashSet<RestaurantsMenu>();// 与菜品分类一对多
	private Set<GarnishHeader> garnishHeadersSet = new HashSet<GarnishHeader>();// 与配菜分类头一对多
	private String logourl;// logo图片的存放地址
	private String notice;// 商家的消息
	private Integer deliverTime;// 送餐所需时间
	private Set<DistancePrice> distancePricesSet = new HashSet<DistancePrice>();// 与配送费一对多
	private String stripeAccount;// 餐厅的托管账号
	private String uuid;//通用唯一识别码

	// Property accessors
	
	
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "restaurant_name")
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

	@Column(name = "restaurant_address")
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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 10)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
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

	@Column(name = "deliver_price", precision = 18)
	public Double getDeliverPrice() {
		return this.deliverPrice;
	}

	public void setDeliverPrice(Double deliverPrice) {
		this.deliverPrice = deliverPrice;
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

	@Column(name = "token", length = 200)
	public void setIsreservation(Integer isreservation) {
		this.isreservation = isreservation;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modon", length = 10)
	public Date getModon() {
		return this.modon;
	}

	public void setModon(Date modon) {
		this.modon = modon;
	}

	@Column(name = "moddy", length = 200)
	public String getModdy() {
		return this.moddy;
	}

	public void setModdy(String moddy) {
		this.moddy = moddy;
	}

	@Column(name = "chainid")
	public Integer getChainid() {
		return this.chainid;
	}

	public void setChainid(Integer chainid) {
		this.chainid = chainid;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "res_restaurant_classification", joinColumns = { @JoinColumn(name = "restaurants_uuid", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "classification_id", nullable = false, updatable = false) })
	public Set<Classification> getClassificationsSet() {
		return this.classificationsSet;
	}

	public void setClassificationsSet(Set<Classification> classificationsSet) {
		this.classificationsSet = classificationsSet;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id")
	public ViewArea getViewArea() {
		return viewArea;
	}

	public void setViewArea(ViewArea viewArea) {
		this.viewArea = viewArea;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
	public Set<RestaurantsUser> getRestaurantsUsersSet() {
		return restaurantsUsersSet;
	}

	public void setRestaurantsUsersSet(Set<RestaurantsUser> restaurantsUsersSet) {
		this.restaurantsUsersSet = restaurantsUsersSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
	public Set<Evaluate> getEvaluatesSet() {
		return evaluatesSet;
	}

	public void setEvaluatesSet(Set<Evaluate> evaluatesSet) {
		this.evaluatesSet = evaluatesSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
	public Set<RestaurantsMenu> getRestaurantsMenusSet() {
		return restaurantsMenusSet;
	}

	public void setRestaurantsMenusSet(Set<RestaurantsMenu> restaurantsMenusSet) {
		this.restaurantsMenusSet = restaurantsMenusSet;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
	public Set<GarnishHeader> getGarnishHeadersSet() {
		return garnishHeadersSet;
	}

	public void setGarnishHeadersSet(Set<GarnishHeader> garnishHeadersSet) {
		this.garnishHeadersSet = garnishHeadersSet;
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

	@Column(name = "deliver_time")
	public Integer getDeliverTime() {
		return this.deliverTime;
	}

	public void setDeliverTime(Integer deliverTime) {
		this.deliverTime = deliverTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurants")
	public Set<DistancePrice> getDistancePricesSet() {
		return distancePricesSet;
	}

	public void setDistancePricesSet(Set<DistancePrice> distancePricesSet) {
		this.distancePricesSet = distancePricesSet;
	}

	@Column(name = "stripe_account")
	public String getStripeAccount() {
		return stripeAccount;
	}

	public void setStripeAccount(String stripeAccount) {
		this.stripeAccount = stripeAccount;
	}

	@Column(name = "stars", precision = 18, scale = 5)
	public double getStars() {
		return this.stars;
	}

	public void setStars(double stars) {
		this.stars = stars;
	}

	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "uuid")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
}