package com.camut.model.api;

public class OrderAllMoneyApiModel {

	private double subtotal;//菜品的所有金额
	private double amount;// 总金额
	private double tax;// 税费
	private double deliveryfee;// 送餐费
	//private String orderType;// 订单种类
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getDeliveryfee() {
		return deliveryfee;
	}
	public void setDeliveryfee(double deliveryfee) {
		this.deliveryfee = deliveryfee;
	}
	/*public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}*/
	
	
	
}
