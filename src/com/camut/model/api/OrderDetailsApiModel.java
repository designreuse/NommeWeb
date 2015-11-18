package com.camut.model.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @entity OrderDetailsApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class OrderDetailsApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1657564694878468672L;
	private long orderId;
	private Integer restaurantId;// 商家编号
	private String restaurantName="";// 店名
	private String restaurantAddress="";// 餐厅地址
	private Integer orderType;//订单种类，1:外送 2：自取 3：到店就餐;
	private Integer number=0;//就餐人数
	private String createdate="";//下单时间	
	private String intruction="";// 备注
	private double total=0.00;// 菜品总金额 order price
	private double deliveryfee=0.00;// 送餐费
	private double tax=0.00;// 税额
	private double tip=0.00;// 小费
	private double amount=0.00;// 最终金额（菜品+税+送餐费+小费）
	private String consumersAddress="";// 用户地址
	private Integer status;// 订单状态  0：订单取消状态 1:未付款 2：已付款 3：已接单 4:拒绝接单 5：退单 6：已退款 7：完成的订单
	private String orderDate;// 送货时间	
	private String orderDateStr; //long类型的订单时间
	private long consumersId; //用户
	private String consumersName="";
	private String consumersIdPhone="";	
	private Integer payment;// 付款类型
	private String orderTypeStr;//订单类型
	private String rejection="";// 拒绝理由 rejection reason
	private String discountText=""; //优惠信息
	private Integer isRating;//是否已经评论
	private String chargeId;// stripe的付款对象的id
	
	private List<CartItemApiModel> item=new  ArrayList<CartItemApiModel>();
	
	public String getRejection() {
		return rejection;
	}
	public void setRejection(String rejection) {
		this.rejection = rejection;
	}
	public String getOrderTypeStr() {
		return orderTypeStr;
	}
	public void setOrderTypeStr(String orderTypeStr) {
		this.orderTypeStr = orderTypeStr;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}	
	public String getIntruction() {
		return intruction;
	}
	public void setIntruction(String intruction) {
		this.intruction = intruction;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTip() {
		return tip;
	}
	public void setTip(double tip) {
		this.tip = tip;
	}
	public double getDeliveryfee() {
		return deliveryfee;
	}
	public void setDeliveryfee(double deliveryfee) {
		this.deliveryfee = deliveryfee;
	}
	public String getConsumersAddress() {
		return consumersAddress;
	}
	public void setConsumersAddress(String consumersAddress) {
		this.consumersAddress = consumersAddress;
	}
	
	public List<CartItemApiModel> getItem() {
		return item;
	}
	public void setItem(List<CartItemApiModel> item) {
		this.item = item;
	}
	public long getConsumersId() {
		return consumersId;
	}
	public void setConsumersId(long consumersId) {
		this.consumersId = consumersId;
	}
	public String getConsumersName() {
		return consumersName;
	}
	public void setConsumersName(String consumersName) {
		this.consumersName = consumersName;
	}
	public String getConsumersIdPhone() {
		return consumersIdPhone;
	}
	public void setConsumersIdPhone(String consumersIdPhone) {
		this.consumersIdPhone = consumersIdPhone;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getDiscountText() {
		return discountText;
	}
	public void setDiscountText(String discountText) {
		this.discountText = discountText;
	}
	public String getOrderDateStr() {
		return orderDateStr;
	}
	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}
	public Integer getIsRating() {
		return isRating;
	}
	public void setIsRating(Integer isRating) {
		this.isRating = isRating;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	
	
	
	
	
	
}
