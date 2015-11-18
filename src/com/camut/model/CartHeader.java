package com.camut.model;

// default package

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * OrderCart entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dat_cart_header", catalog = "nomme")
public class CartHeader extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 2622616905936191207L;
	private String mobileToken;// 设备编号
	private Integer orderType;// 订单种类
	private String consumerUuid;// 客户uuid
	private String restaurantUuid;// 商家id
	private Integer discountId;//优惠券Id
	private Double dishFee;//菜品的所有金额
	private Double total;// 总金额
	private Double tax;// 税费
	private Double logistics;// 送餐费
	private Set<CartItem> cartItems = new TreeSet<CartItem>();// 与购物车详情一对多关系
	private Double consumerLng;// 经度
	private Double consumerLat;// 纬度
	private String peopleName;//收货人姓名
	private String email;//收货人邮箱
	private String phone;//电话
	private String address;//地址
	private String floor;//楼层门牌
	private String memo;//备注
	private Date orderTime;//订单时间
	private Integer orderId;//订单id
	

	@Column(name = "mobile_token")
	public String getMobileToken() {
		return this.mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	@Column(name = "order_type")
	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(name = "consumer_uuid")
	public String getConsumerUuid() {
		return this.consumerUuid;
	}

	public void setConsumerUuid(String consumerUuid) {
		this.consumerUuid = consumerUuid;
	}

	@Column(name = "restaurant_uuid")
	public String getRestaurantUuid() {
		return this.restaurantUuid;
	}

	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}

	@Column(name = "total", precision = 22, scale = 0)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "tax", precision = 18)
	public Double getTax() {
		return this.tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	@Column(name = "logistics", precision = 18, scale = 0)
	public Double getLogistics() {
		return this.logistics;
	}

	public void setLogistics(Double logistics) {
		this.logistics = logistics;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartHeader")
	public Set<CartItem> getCartItems() {
		return this.cartItems;
	}

	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	@Column(name = "consumer_lng", precision = 18, scale = 5)
	public Double getConsumerLng() {
		return this.consumerLng;
	}

	public void setConsumerLng(Double consumerLng) {
		this.consumerLng = consumerLng;
	}

	@Column(name = "consumer_lat", precision = 18, scale = 5)
	public Double getConsumerLat() {
		return this.consumerLat;
	}

	public void setConsumerLat(Double consumerLat) {
		this.consumerLat = consumerLat;
	}

	@Column(name = "discount_id" )
	public Integer getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}

	@Column(name = "dish_fee")
	public Double getDishFee() {
		return dishFee;
	}

	public void setDishFee(Double dishFee) {
		this.dishFee = dishFee;
	}

	@Column(name = "people_name")
	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "floor")
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "order_time")
	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Column(name="order_id")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
	
	

}