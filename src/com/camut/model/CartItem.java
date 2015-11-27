package com.camut.model;

// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CartItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dat_cart_item")
public class CartItem extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7220910079573658478L;
	private CartHeader cartHeader;// 与购物车信息头多对一
	private Integer dishId;// 菜品id
	private Integer num;// 数量
	private double unitprice;// 单价(菜+配菜*数量)
	private String instruction;// 特殊需求
	private Set<CartDishGarnish> cartDishGarnishs = new HashSet<CartDishGarnish>(0);//与购物车配菜一对多

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cart_id")
	public CartHeader getcartHeader() {
		return this.cartHeader;
	}

	public void setcartHeader(CartHeader cartHeader) {
		this.cartHeader = cartHeader;
	}

	@Column(name = "dish_id")
	public Integer getDishId() {
		return this.dishId;
	}

	public void setDishId(Integer dishId) {
		this.dishId = dishId;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Column(name = "unitprice", precision = 18)
	public double getUnitprice() {
		return this.unitprice;
	}

	public void setUnitprice(double unitprice) {
		this.unitprice = unitprice;
	}

	@Column(name = "instruction", length = 200)
	public String getInstruction() {
		return this.instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cartItem")
	public Set<CartDishGarnish> getCartDishGarnishs() {
		return this.cartDishGarnishs;
	}

	public void setCartDishGarnishs(Set<CartDishGarnish> cartDishGarnishs) {
		this.cartDishGarnishs = cartDishGarnishs;
	}

}