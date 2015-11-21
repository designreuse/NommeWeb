package com.camut.pageModel;

import java.util.Date;

public class PageSelectItemReservationOrder {

	private Date orderDate;
	private Integer id;
	private Integer number;
	private Integer itemSize;
	private String strOrderDate;// 字符串就餐时间
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getItemSize() {
		return itemSize;
	}
	public void setItemSize(Integer itemSize) {
		this.itemSize = itemSize;
	}
	public String getStrOrderDate() {
		return strOrderDate;
	}
	public void setStrOrderDate(String strOrderDate) {
		this.strOrderDate = strOrderDate;
	}
	
	
	
	
	
	
}
