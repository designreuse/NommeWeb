package com.camut.model.api;

import java.util.Date;

public class DineinOrderApiModel {

	private long orderId;//订单id
	private Integer number;// 就餐人数
	private Date orderDate;// 送货时间
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	
}
