package com.camut.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @entity CustomerPastOrder . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class ConsumerPastOrder implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6799089279964212585L;
	private long orderId=0;//订单ID
	private String restaurantName="";// 店名
	private Date createdate=new Date();//下单时间
	private double total;//总金额 
	private Integer orderType=1;//订单种类，1:外送 2：自取 3：到店就餐;
	private Integer number=1;//就餐人数

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
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	
	
}
