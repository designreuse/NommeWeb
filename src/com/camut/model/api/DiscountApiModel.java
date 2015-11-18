package com.camut.model.api;

import java.io.Serializable;

/**
 * @entity DiscountApiModel . 
 * @author zyf	
 * @createTime 2015-05-30
 * @author 
 * @updateTime 
 * @memo 
 */
public class DiscountApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3461656085074544526L;
	private long discountId;//优惠信息id
	private String content;// 优惠内容
	private double price;// 优惠金额
	private double consumePrice;// 消费金额
	private double discount=0;// 折扣
	private Integer type;// 类型,1、现金抵用券 2、打折券 3、赠送菜品
	private Integer dishId=0;
	private String dishName="";
	
	public long getDiscountId() {
		return discountId;
	}
	public void setDiscountId(long discountId) {
		this.discountId = discountId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getConsumePrice() {
		return consumePrice;
	}
	public void setConsumePrice(double consumePrice) {
		this.consumePrice = consumePrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getDishId() {
		return dishId;
	}
	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	
	
}
