package com.camut.model.api;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import com.camut.model.CartItem;

public class CartApiModel {
	private String mobileToken;// 设备编号
	private Integer orderType;// 订单种类
	private String consumerUuid;// 客户id
	private String restaurantUuid;// 商家id
	private double total;// 总金额
	private double tax;// 税费
	private double discount;
	private double logistics;// 送餐费
	private double restaurantLat;
	private double restaurantLng;
	private Set<CartItem> cartItems = new HashSet<CartItem>(0);// 与购物车详情一对多关系
	
	
	public String getMobileToken() {
		return mobileToken;
	}
	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getConsumerUuid() {
		return consumerUuid;
	}
	public void setConsumerUuid(String consumerUuid) {
		this.consumerUuid = consumerUuid;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	public double getTotal() {
		DecimalFormat    df   = new DecimalFormat("######0.00"); 
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getLogistics() {
		return logistics;
	}
	public void setLogistics(double logistics) {
		this.logistics = logistics;
	}
	public Set<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getRestaurantLat() {
		return restaurantLat;
	}
	public void setRestaurantLat(double restaurantLat) {
		this.restaurantLat = restaurantLat;
	}
	public double getRestaurantLng() {
		return restaurantLng;
	}
	public void setRestaurantLng(double restaurantLng) {
		this.restaurantLng = restaurantLng;
	}
	
	
	
	
}
