package com.camut.model;

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
 * CartDishGarnish entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "res_cart_dish_garnish", catalog = "nomme")
public class CartDishGarnish implements java.io.Serializable{

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8682330873677278661L;
	private CartDishGarnishId id;// 联合主键类
	private CartItem cartItem;// 与购物车详情多对一
	private GarnishItem garnishItem;// 与配菜多对一
	private Integer garnishNum;// 配菜数量

	// Property accessors
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "garnishItemId", column = @Column(name = "garnish_item_id", nullable = false)),
			@AttributeOverride(name = "cartItemId", column = @Column(name = "cart_item_id", nullable = false)) })
	public CartDishGarnishId getId() {
		return this.id;
	}

	public void setId(CartDishGarnishId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_item_id", nullable = false, insertable = false, updatable = false)
	public CartItem getCartItem() {
		return this.cartItem;
	}

	public void setCartItem(CartItem cartItem) {
		this.cartItem = cartItem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "garnish_item_id", nullable = false, insertable = false, updatable = false)
	public GarnishItem getGarnishItem() {
		return this.garnishItem;
	}

	public void setGarnishItem(GarnishItem garnishItem) {
		this.garnishItem = garnishItem;
	}

	@Column(name = "garnish_num")
	public Integer getGarnishNum() {
		return this.garnishNum;
	}

	public void setGarnishNum(Integer garnishNum) {
		this.garnishNum = garnishNum;
	}


}