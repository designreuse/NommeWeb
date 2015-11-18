package com.camut.model.api;

import java.util.ArrayList;
import java.util.List;
import com.camut.pageModel.PageDiscount;

/**
 * @ClassName CartHeaderApiModel.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
public class CartHeaderApiModel {

	private Long cartId;//购物车id
	private Integer orderType;// 订单种类
	private Integer restaurantId;// 商家id
	private String restaurantName; //商家名称
	private Double total;// 总金额
	private Double tax;// 税金
	private Double logistics;// 送餐费
	private Double allTotal;//最终金额 （总金额+税金+送餐费）
	private Integer discountId;//优惠券Id
	private List<CartItemApiModel> item = new ArrayList<CartItemApiModel>(0);// 与购物车详情一对多关系
	private List<PageDiscount> discountList=new ArrayList<PageDiscount>();//优惠券列表
	private Integer dishNum;//item的数量
	private Integer orderId;//订单的id
	private String orderDate;// 送货时间
	
	public Integer getDishNum() {
		return dishNum;
	}
	public void setDishNum(Integer dishNum) {
		this.dishNum = dishNum;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Integer restaurantId) {
		this.restaurantId = restaurantId;
	}
	public Double getTotal() {
		return this.total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getTax() {
		return tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}
	public Double getLogistics() {
		return logistics;
	}
	public void setLogistics(Double logistics) {
		this.logistics = logistics;
	}
	public List<CartItemApiModel> getItem() {
		return item;
	}
	public void setItem(List<CartItemApiModel> item) {
		this.item = item;
	}
	public Double getAllTotal() {
		return allTotal;
	}
	public void setAllTotal(Double allTotal) {
		this.allTotal = allTotal;
	}
	
	public List<PageDiscount> getDiscountList() {
		return discountList;
	}
	public void setDiscountList(List<PageDiscount> discountList) {
		this.discountList = discountList;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public Integer getDiscountId() {
		return discountId;
	}
	public void setDiscountId(Integer discountId) {
		this.discountId = discountId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

}
