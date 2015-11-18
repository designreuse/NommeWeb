package com.camut.model;

// default package

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * OrderDishGarnish entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "res_order_dish_garnish", catalog = "nomme")
public class OrderDishGarnish implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6476239203077412515L;
	private OrderDishGarnishId id;// 联合主键类
	private Integer dishId=1;// 菜品id
	private Integer garnishNum=1;// 配菜数量
	private GarnishItem garnishItem;// 与配菜多对一
	private OrderItem orderItem;// 与订单详情多对一

	// Constructors

	/** default constructor */
	public OrderDishGarnish() {
	}

	/** minimal constructor */
	public OrderDishGarnish(OrderDishGarnishId id) {
		this.id = id;
	}

	/** full constructor */
	public OrderDishGarnish(OrderDishGarnishId id, Integer dishId, Integer garnishNum) {
		this.id = id;
		this.dishId = dishId;
		this.garnishNum = garnishNum;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "orderItemId", column = @Column(name = "order_item_id", nullable = false)),
			@AttributeOverride(name = "garnishItemId", column = @Column(name = "garnish_item_id", nullable = false)) })
	public OrderDishGarnishId getId() {
		return this.id;
	}

	public void setId(OrderDishGarnishId id) {
		this.id = id;
	}

	@Column(name = "dish_id")
	public Integer getDishId() {
		return this.dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	@Column(name = "garnish_num")
	public Integer getGarnishNum() {
		return this.garnishNum;
	}

	public void setGarnishNum(Integer garnishNum) {
		this.garnishNum = garnishNum;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "garnish_item_id",insertable=false,updatable=false)
	public GarnishItem getGarnishItem() {
		return garnishItem;
	}

	public void setGarnishItem(GarnishItem garnishItem) {
		this.garnishItem = garnishItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_item_id",insertable=false,updatable=false)
	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

}