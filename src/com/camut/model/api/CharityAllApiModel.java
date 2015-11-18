package com.camut.model.api;

import java.util.ArrayList;
import java.util.List;

import com.camut.model.Charity;

public class CharityAllApiModel {
	
	private String restaurantName;// 店名
	private double total;//捐款金额
	private String orderDate;// 送货时间
	private List<CharityApiModel> charity;
	private List<Charity> oftenCharity=new ArrayList<Charity>();
	
	
	public List<Charity> getOftenCharity() {
		return oftenCharity;
	}
	public void setOftenCharity(List<Charity> oftenCharity) {
		this.oftenCharity = oftenCharity;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public List<CharityApiModel> getCharity() {
		return charity;
	}
	public void setCharity(List<CharityApiModel> charity) {
		this.charity = charity;
	}
	
}
