package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @entity DistancePrice .
 * @author 王频
 * @createTime 2015-05-25
 * @author
 * @updateTime
 * @memo delivery fee based on the distance
 */
@Entity
@Table(name = "tbl_distance_price", catalog = "nomme")
public class DistancePrice extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 5988996248635549808L;
	private Restaurants restaurants;// 与商家多对一 many DistancePrice: 1 restaurant
	private double startDistance;// 开始距离
	private double endDistance;// 结束距离
	private double orderPrice;// 订单价格
	private double upPrice;// 达到订单价格配送价
	private double notupPrice;// 没有达到订单价格配送价

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "start_distance", precision = 10)
	public double getStartDistance() {
		return this.startDistance;
	}

	public void setStartDistance(double startDistance) {
		this.startDistance = startDistance;
	}

	@Column(name = "end_distance", precision = 10)
	public double getEndDistance() {
		return this.endDistance;
	}

	public void setEndDistance(double endDistance) {
		this.endDistance = endDistance;
	}

	@Column(name = "order_price", precision = 10)
	public double getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Column(name = "up_price", precision = 10)
	public double getUpPrice() {
		return this.upPrice;
	}

	public void setUpPrice(double upPrice) {
		this.upPrice = upPrice;
	}

	@Column(name = "notup_price", precision = 10)
	public double getNotupPrice() {
		return this.notupPrice;
	}

	public void setNotupPrice(double notupPrice) {
		this.notupPrice = notupPrice;
	}

}