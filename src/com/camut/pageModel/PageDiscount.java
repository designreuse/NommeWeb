/**
 * 
 */
package com.camut.pageModel;

/**
 * @ClassName PageDiscount.java
 * @author wangpin
 * @createtime Jun 29, 2015
 * @author
 * @updateTime
 * @memo
 */
public class PageDiscount {

	private long id;//主键
	private String content;// 优惠内容
	private Double price;// 优惠金额
	private Double consumePrice;// 消费金额
	private Double discount;// 折扣
	private Integer type;// 类型,1、现金抵用券 2、打折券 3、赠送菜品
	private String startTime;// 优惠开始时间
	private String endTime;// 优惠结束时间
	private Integer orderType;// 外卖，自取或者预定
	private Integer dishId;// 赠送菜品的菜品id
	private String dishName;//赠送菜品名称
	private Integer isSelect;//是否被选中
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getConsumePrice() {
		return consumePrice;
	}
	public void setConsumePrice(Double consumePrice) {
		this.consumePrice = consumePrice;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
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
	public Integer getIsSelect() {
		return isSelect;
	}
	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}
	
	
	
}
