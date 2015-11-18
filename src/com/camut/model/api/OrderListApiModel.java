package com.camut.model.api;

/**
 * @entity OrderListApiModel . 
 * @author zyf	
 * @createTime 2015-06-08
 * @author 
 * @updateTime 
 * @memo 
 */
public class OrderListApiModel {

	private long orderId;//订单ID
	private String restaurantName;// 店名
	private String createdate;//下单时间
	private double total;//总金额 
	private int orderType;//订单种类，1:外送 2：自取 3：到店就餐;
	private int number;//就餐人数
	private String orderDate;// 送货时间
	
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	
	
}
