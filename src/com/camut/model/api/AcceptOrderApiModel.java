package com.camut.model.api;

import java.util.Date;

public class AcceptOrderApiModel {

	private long orderId;// 下单时间
	private Date createdate;// 下单时间
	private Date orderDate;// 送货时间
	private String orderDateStr;
	private String phone="";// 联系电话
	private String lastName="";// 姓
	private String firstName="";// 名
	private String showName;
	private	int timeNum=0;    //
	private int orderType;
	
	
	public String getOrderDateStr() {
		if(orderDate!=null){
			return orderDate.getTime()+"";
		}
		return "";
	}
	
	public void setOrderDateStr(String orderDateStr) {
		this.orderDateStr = orderDateStr;
	}

	public int getTimeNum() {
		return timeNum;
	}
	public void setTimeNum(int timeNum) {
		this.timeNum = timeNum;
	}
	

	public String getShowName() {
		return showName;
	}

	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	
	
}
