package com.camut.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @entity OrderApiMOdel . 
 * @author zyf	
 * @createTime 2015-05-31
 * @author 
 * @updateTime 
 * @memo 
 */
public class OrderApiMOdel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2535162922177677556L;
	private Integer orderType=1; //订单状态(1:外送 2：自取 3：到店就餐)
	private String consumerUuid=""; // 用户编号
	private String restaurantUuid=""; //店铺编号
	private double total=1.0; //订单总价
	private Date orderDate=new Date(); //订单时间
	private Integer number=1; //就餐人数
	private String address;// 地址
	private String zipcode=""; //邮编
	private double tax=0.0; //税率
	private double tip=0.0; //小费
	private double logistics=0.0; //送餐费
	private Integer dishId=1; //菜品ID
	private Integer num=1; //菜品数量
	private double unitprice=0.0;  //菜品单价
	private Integer garnishItemId=1;  //配菜ID
	
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
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public double getTax() {
		return tax;
	}
	public void setTax(double tax) {
		this.tax = tax;
	}
	public double getTip() {
		return tip;
	}
	public void setTip(double tip) {
		this.tip = tip;
	}
	public double getLogistics() {
		return logistics;
	}
	public void setLogistics(double logistics) {
		this.logistics = logistics;
	}
	
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public double getUnitprice() {
		return unitprice;
	}
	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}
	public Integer getGarnishItemId() {
		return garnishItemId;
	}
	public void setGarnishItemId(Integer garnishItemId) {
		this.garnishItemId = garnishItemId;
	}
	
	
	
}
