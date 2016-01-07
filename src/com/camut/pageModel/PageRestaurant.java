package com.camut.pageModel;

import java.util.List;

/**
 * @ClassName PageRestaurant.java
 * @author wangpin
 * @createtime Jun 9, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageRestaurant {

	private Long id;//主键
	private String restaurantName;// 店名
	private String restaurantContact;// 餐厅负责人
	private String restaurantEmail;//餐厅负责人邮箱
	private String restaurantPhone;// 联系电话
	private String restaurantAddress;// 地址
	private Double restaurantLng;// 纬度
	private Double restaurantLat;// 经度
	private Double distance;// 外卖距离
	private Double deliverPrice;// 外卖起步价
	private Integer canDelivery;//是否支持外送（如果距离大于最大外送距离设为不可外送：0，在距离范围内，设为可外送1）
	private Double avgPrice;// 人均消费价格
	private String features;// 特征
	private Integer isdelivery;// 是否外带0是，1否
	private Integer ispickup;// 是否自取0是，1否
	private Integer isreservation;// 是否预定0是，1否
	private Integer chainid;// 连锁店id
	private String chainName;// 连锁店名称
	private String logourl;// logo图片的存放地址
	private Integer status;//审核状态 -1为无效，0为有效(说明已经审核通过)，1待审核（新注册的商家状态为1）,2 冻结 、未审核通过
	private String notice;// 商家的消息
	private Integer deliverTime;// 送餐所需时间
	private double score;//商家平均评论得分
	private Double taxRate;// 联邦+省的总税率
	private Integer AreasId;//区域id
	private PageAreas pageAreas;//区域税率对象
	
	private List<Long> classificationId;//餐厅分类
	private List<PageClassification> pageClassificationList;//餐厅分类的集合
	private List<PageRestaurantsMenu> pageRestaurantsMenuList;//餐厅菜单分类集合
	private List<PageEvaluate> pageEvaluateList;//商家评论
	private List<PageDiscount> pageDiscountList;//优惠券信息
	private String restaurantUuid;
	private String keywords;
	
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
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public Double getDeliverPrice() {
		return deliverPrice;
	}
	public void setDeliverPrice(Double deliverPrice) {
		this.deliverPrice = deliverPrice;
	}
	public Double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Double avgPrice) {
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
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Long> getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(List<Long> classificationId) {
		this.classificationId = classificationId;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Integer getDeliverTime() {
		return deliverTime;
	}
	public void setDeliverTime(Integer deliverTime) {
		this.deliverTime = deliverTime;
	}
	public PageAreas getPageAreas() {
		return pageAreas;
	}
	public void setPageAreas(PageAreas pageAreas) {
		this.pageAreas = pageAreas;
	}
	public List<PageClassification> getPageClassificationList() {
		return pageClassificationList;
	}
	public void setPageClassificationList(
			List<PageClassification> pageClassificationList) {
		this.pageClassificationList = pageClassificationList;
	}
	public List<PageRestaurantsMenu> getPageRestaurantsMenuList() {
		return pageRestaurantsMenuList;
	}
	public void setPageRestaurantsMenuList(
			List<PageRestaurantsMenu> pageRestaurantsMenuList) {
		this.pageRestaurantsMenuList = pageRestaurantsMenuList;
	}
	public String getChainName() {
		return chainName;
	}
	public void setChainName(String chainName) {
		this.chainName = chainName;
	}
	public Integer getAreasId() {
		return AreasId;
	}
	public void setAreasId(Integer areasId) {
		AreasId = areasId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public List<PageEvaluate> getPageEvaluateList() {
		return pageEvaluateList;
	}
	public void setPageEvaluateList(List<PageEvaluate> pageEvaluateList) {
		this.pageEvaluateList = pageEvaluateList;
	}
	public List<PageDiscount> getPageDiscountList() {
		return pageDiscountList;
	}
	public void setPageDiscountList(List<PageDiscount> pageDiscountList) {
		this.pageDiscountList = pageDiscountList;
	}
	public Integer getCanDelivery() {
		return canDelivery;
	}
	public void setCanDelivery(Integer canDelivery) {
		this.canDelivery = canDelivery;
	}
	public String getRestaurantEmail() {
		return restaurantEmail;
	}
	public void setRestaurantEmail(String restaurantEmail) {
		this.restaurantEmail = restaurantEmail;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	
	public String getKeywords() {
		return keywords;
	}
	
	public void setKeywords(String keywords){
		this.keywords = keywords;
	}
	
}
