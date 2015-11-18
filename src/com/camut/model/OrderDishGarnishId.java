package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * OrderDishGarnishId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class OrderDishGarnishId implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705871069358655495L;
	private Integer orderItemId;// 订单条目id
	private Integer garnishItemId;// 配菜id

	// Property accessors

	@Column(name = "order_item_id", nullable = false)
	public Integer getOrderItemId() {
		return this.orderItemId;
	}

	public void setOrderItemId(Integer orderItemId) {
		this.orderItemId = orderItemId;
	}

	@Column(name = "garnish_item_id", nullable = false)
	public Integer getGarnishItemId() {
		return this.garnishItemId;
	}

	public void setGarnishItemId(Integer garnishItemId) {
		this.garnishItemId = garnishItemId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof OrderDishGarnishId))
			return false;
		OrderDishGarnishId castOther = (OrderDishGarnishId) other;

		return ((this.getOrderItemId() == castOther.getOrderItemId()) || (this.getOrderItemId() != null && castOther.getOrderItemId() != null && this
				.getOrderItemId().equals(castOther.getOrderItemId())))
				&& ((this.getGarnishItemId() == castOther.getGarnishItemId()) || (this.getGarnishItemId() != null
						&& castOther.getGarnishItemId() != null && this.getGarnishItemId().equals(castOther.getGarnishItemId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getOrderItemId() == null ? 0 : this.getOrderItemId().hashCode());
		result = 37 * result + (getGarnishItemId() == null ? 0 : this.getGarnishItemId().hashCode());
		return result;
	}

}