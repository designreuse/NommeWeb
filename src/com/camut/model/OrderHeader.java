package com.camut.model;

// default package

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity OrderHeader .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "dat_order_header", catalog = "nomme")
public class OrderHeader extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -9126848601762095585L;
	private Consumers consumers;// 与会员多对一的关系,单向关联; many OrderItem : 1 Consumers
	private Integer orderType;// 订单种类，1:外送 2：自取 3：到店就餐; 1:delivery 2:pick up
								// 3：dine-in/reservation
	private Date createdate;// 下单时间
	private String restaurantUuid;// 商家编号
	private Integer status;// 订单状态 0：订单取消状态 1:未付款 2：已付款 3：已接单 4:拒绝接单  6：已退款
							// 7：完成的订单  8：line up  9：现金付款   10:待审核
	private Date orderDate;// 送货时间
	private Integer number;// 就餐人数
	private String address;// 地址
	private String zipcode;// 邮编
	private String rejection;// 拒绝理由 rejection reason
	private Set<OrderItem> orderItems = new HashSet<OrderItem>();// 与订单详情一对多关系;1OrderHeader:
																	// manyOrderItem
	private double total;// 菜品总金额 order price
	private double logistics;// 送餐费
	private double tax;// 税额
	private double tip;// 小费
	private double amount;// 最终金额（菜品+税+送餐费+小费）
	// private double gst;// 联邦税
	// private double pst;// 省税
	private String orderNo;// 订单编号
	private Integer payment;// 付款类型
	private String chargeId;// stripe的付款对象的id
	private String street;// 街道
	private String floor;// 楼
	private String city;// 城市
	private String province;// 省份

	private String peopleName="";// 订餐人姓名
	private String phoneNumber="";// 订餐人电话号码
	private String email="";// 订餐人电子邮件
	private String memo;// 订单备注
	private Integer discountId;// 优惠券id

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "consumer_uuid")
	public Consumers getConsumers() {
		return this.consumers;
	}

	public void setConsumers(Consumers consumers) {
		this.consumers = consumers;
	}

	@Column(name = "order_type")
	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate", length = 10)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "restaurant_uuid")
	public String getRestaurantUuid() {
		return this.restaurantUuid;
	}

	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}

	@Column(name = "total", precision = 22, scale = 0)
	public double getTotal() {
		return this.total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "order_date", length = 10)
	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	@Column(name = "number")
	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "address", length = 200)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "zipcode", length = 50)
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "rejection", nullable = false, length = 200)
	public String getRejection() {
		return this.rejection;
	}

	public void setRejection(String rejection) {
		this.rejection = rejection;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "orderHeader")
	public Set<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	@Column(name = "tip", precision = 18)
	public double getTip() {
		return this.tip;
	}

	public void setTip(double tip) {
		this.tip = tip;
	}

	@Column(name = "logistics", precision = 18, scale = 0)
	public void setLogistics(double logistics) {
		this.logistics = logistics;
	}

	/*
	 * public void setTax(double tax) { this.tax = tax; }
	 * 
	 * @Column(name = "tax", nullable = false, precision = 18) public double
	 * getTax() { return this.tax; }
	 */

	public double getLogistics() {
		return logistics;
	}

	/*
	 * @Column(name = "gst", nullable = false, precision = 18) public double
	 * getGst() { return gst; }
	 * 
	 * public void setGst(double gst) { this.gst = gst; }
	 * 
	 * @Column(name = "pst", nullable = false, precision = 18) public double
	 * getPst() { return pst; }
	 * 
	 * public void setPst(double pst) { this.pst = pst; }
	 */

	@Column(name = "tax")
	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	@Column(name = "order_no")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "payment")
	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	@Column(name = "charge_id")
	public String getChargeId() {
		return chargeId;
	}

	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}

	@Column(name = "street")
	public String getStreet() {
		return this.street;
	}
	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "floor", length = 200)
	public String getFloor() {
		return this.floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}

	@Column(name = "city", length = 50)
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "province", length = 50)
	public String getProvince() {
		return this.province;
	}
	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "people_name", length = 50)
	public String getPeopleName() {
		return peopleName;
	}
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	@Column(name = "phone_number", length = 50)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "memo")
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "amount")
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Column(name = "discount_id")
	public Integer getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}
	
	

}