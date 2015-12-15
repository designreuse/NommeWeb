package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.camut.framework.constant.GlobalConstant.DELETE_STATUS;

/**
 * @entity Discount .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "tbl_discount")
public class Discount extends IdEntity implements java.io.Serializable{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3625221285832288776L;
	private String content;// 优惠内容
	private Double price;// 优惠金额
	private Double consumePrice;// 消费金额
	private Double discount;// 折扣
	private Integer type;// 类型,1、现金抵用券 2、打折券 3、赠送菜品
	private Restaurants restaurants;// 与商家多对一
	private String startTime;// 优惠开始时间
	private String endTime;// 优惠结束时间
	private Integer orderType;// 外卖，自取或者预定
	private Integer dishId;// 赠送菜品的菜品id
	private Integer deleteStatus;
	
	// Property accessors

	@Column(name = "content", nullable = false)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "price", precision = 18)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "consume_price", nullable = false, precision = 18)
	public Double getConsumePrice() {
		return this.consumePrice;
	}

	public void setConsumePrice(Double consumePrice) {
		this.consumePrice = consumePrice;
	}

	@Column(name = "discount", precision = 5)
	public Double getDiscount() {
		return this.discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_uuid")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "start_time", length = 50)
	public String getStartTime() {
		return this.startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Column(name = "end_time", length = 50)
	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Column(name = "order_type")
	public Integer getOrderType() {
		return this.orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(name = "dish_id")
	public Integer getDishId() {
		return this.dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	@Column(name = "delete_status")
	public Integer getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(Integer status) {
		this.deleteStatus = status;
	}
}