package com.camut.model.api;

public class OrderDineInApiModel {
	
	private long orderId;//订单id
	private String orderDate;// 送货时间
	private Integer number;// 就餐人数
	
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	
}
