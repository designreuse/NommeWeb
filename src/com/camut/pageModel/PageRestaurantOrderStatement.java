package com.camut.pageModel;

//商家的报表模型
public class PageRestaurantOrderStatement {
	private String orderType;//订单类型
	private String paymentType;//支付方式
	private String orderQuantity;//订单数量
	private String subtotal;//菜金
	private String deliveryFee;//配送费
	private String gst;//税金
	private String tips;//小费
	private String nommeFee;//nomme平台收取的服务费
	private String stripeFee;//stripe收取的服务费
	private String income;//总共应收金额
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(String orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(String deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getNommeFee() {
		return nommeFee;
	}
	public void setNommeFee(String nommeFee) {
		this.nommeFee = nommeFee;
	}
	public String getStripeFee() {
		return stripeFee;
	}
	public void setStripeFee(String stripeFee) {
		this.stripeFee = stripeFee;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	
	
}
