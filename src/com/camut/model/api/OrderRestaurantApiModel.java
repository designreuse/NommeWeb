package com.camut.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @entity OrderListApiModel . 
 * @author zyf	
 * @createTime 2015-06-08
 * @author 
 * @updateTime 
 * @memo 
 */
public class OrderRestaurantApiModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long orderId;//订单ID
	private Date createdate;//下单时间
	private double total;//总金额 
	private int orderType;//订单种类，1:外送 2：自取 3：到店就餐;
	private Integer payment;//付款类型
	private Integer status;// 订单状态  0：订单取消状态 1:未付款 2：已付款 3：已接单 4:拒绝接单 5：退单 6：已退款 7：完成的订单
	private String phone;// 联系电话
	private String lastName;// 姓
	private String firstName;// 名
	private Date orderDate;// 送货时间
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
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
	public int getOrderType() {
		return orderType;
	}
	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}
	public Integer getPayment() {
		return payment;
	}
	public void setPayment(Integer payment) {
		this.payment = payment;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	
}
