package com.camut.model.api;

public class CancelOrderApiModel {

	private String lastName="";// 姓
	private String firstName="";// 名
	private String phone="";// 联系电话
	private String orderDate;// 送货时间
	private Integer orderType;// 订单种类，1:外送 2：自取 3：到店就餐; 1:delivery 2:pick up 3：dine-in 4：reservation
	private double total;// 总金额 order price
	private Integer payment=0;// 付款类型
	private Integer status;// 订单状态 0：订单取消状态 1:未付款 2：已付款 3：已接单 4:拒绝接单 5：退单 6：已退款  7：完成的订单
	private String showName="";
	private Long orderId;
	private String orderDateStr;
	
	
	
	public String getOrderDateStr() {
		return orderDateStr;
	}
	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getShowName() {
		return this.firstName+" "+this.lastName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
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
	
	
	
}
