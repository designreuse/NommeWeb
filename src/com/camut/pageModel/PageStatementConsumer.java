package com.camut.pageModel;

public class PageStatementConsumer {
	
	private String consumerId;//用户ID
	private String firstName=" ";//
	private String lastName=" ";//
	private String phone=" ";//电话号码
	private String regDate=" ";//注册时间
	private String lastLoginDate = " ";//最后一次登录时间
	private String completedOrderQuantity = "0";//完成订单数量
	private String completedOrderAmount = "0.00";//完成订单总额
	private String unfinishedOrderQuantity = "0";//未完成订单数量
	private String unfinishedOrderAmount = "0.00";//未完成订单总额
	private String refundOrderQuantity = "0";//退款订单数量
	private String refundOrderAmount = "0.00";//退款成订单总额
	private String donateAmount = "0.00";//捐款总额
	private String mobileType = " ";//使用的设备类型
	
	public String getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getCompletedOrderQuantity() {
		return completedOrderQuantity;
	}
	public void setCompletedOrderQuantity(String completedOrderQuantity) {
		this.completedOrderQuantity = completedOrderQuantity;
	}
	public String getCompletedOrderAmount() {
		return completedOrderAmount;
	}
	public void setCompletedOrderAmount(String completedOrderAmount) {
		this.completedOrderAmount = completedOrderAmount;
	}
	public String getUnfinishedOrderQuantity() {
		return unfinishedOrderQuantity;
	}
	public void setUnfinishedOrderQuantity(String unfinishedOrderQuantity) {
		this.unfinishedOrderQuantity = unfinishedOrderQuantity;
	}
	public String getUnfinishedOrderAmount() {
		return unfinishedOrderAmount;
	}
	public void setUnfinishedOrderAmount(String unfinishedOrderAmount) {
		this.unfinishedOrderAmount = unfinishedOrderAmount;
	}
	public String getRefundOrderQuantity() {
		return refundOrderQuantity;
	}
	public void setRefundOrderQuantity(String refundOrderQuantity) {
		this.refundOrderQuantity = refundOrderQuantity;
	}
	public String getRefundOrderAmount() {
		return refundOrderAmount;
	}
	public void setRefundOrderAmount(String refundOrderAmount) {
		this.refundOrderAmount = refundOrderAmount;
	}
	public String getDonateAmount() {
		return donateAmount;
	}
	public void setDonateAmount(String donateAmount) {
		this.donateAmount = donateAmount;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	
	

}
