package com.camut.model;

// default package


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * CartDishGarnishId entity. @author MyEclipse Persistence Tools 联合主键类
 */
@Embeddable
public class CartDishGarnishId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3146597325988411540L;
	private Integer garnishItemId;// 配菜主键
	private Integer cartItemId;// 购物车详情主键

	// Property accessors

	@Column(name = "garnish_item_id", nullable = false)
	public Integer getGarnishItemId() {
		return this.garnishItemId;
	}

	public void setGarnishItemId(Integer garnishItemId) {
		this.garnishItemId = garnishItemId;
	}

	@Column(name = "cart_item_id", nullable = false)
	public Integer getCartItemId() {
		return this.cartItemId;
	}

	public void setCartItemId(Integer cartItemId) {
		this.cartItemId = cartItemId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CartDishGarnishId))
			return false;
		CartDishGarnishId castOther = (CartDishGarnishId) other;

		return ((this.getGarnishItemId() == castOther.getGarnishItemId()) || (this
				.getGarnishItemId() != null
				&& castOther.getGarnishItemId() != null && this
				.getGarnishItemId().equals(castOther.getGarnishItemId())))
				&& ((this.getCartItemId() == castOther.getCartItemId()) || (this
						.getCartItemId() != null
						&& castOther.getCartItemId() != null && this
						.getCartItemId().equals(castOther.getCartItemId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getGarnishItemId() == null ? 0 : this.getGarnishItemId()
						.hashCode());
		result = 37
				* result
				+ (getCartItemId() == null ? 0 : this.getCartItemId()
						.hashCode());
		return result;
	}

}