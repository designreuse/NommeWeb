package com.camut.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @entity NommeDiscount .
 * @author Cuong Ich Truong
 * @createTime 2016-01-15
 * @author
 * @updateTime
 * @memo
 */
@Entity
@Table(name = "tbl_nomme_discount")
public class NommeDiscount extends IdEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3436003873258992463L;

	private String uuid;
	private String couponCode;
	private String content;
	private Double price; // discount amount in cash
	private Double consumePrice;
	private Double discount; // discount amount in percentage
	private Integer type;// refer to DISCOUNT_TYPE
	private String startTime;
	private String endTime;
	private Integer orderType;// apply coupon to: 1:delivery 2:pick up
								// 3：dine-in/reservation 4:apply to all
	private Integer dishId;// free item
	private Integer maxUses; // if max_uses=1 then this is a one time use coupon
	private Integer usedCount; // count number of uses, if equal to max_uses
								// then the coupon will be expired
	private Integer status; // refer to NOMME_DISCOUNT_STATUS
	private Integer deleteStatus; // refer to DELETE_STATUS

	// Property accessors

	@Column(name = "uuid")
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.content = uuid;
	}

	@Column(name = "coupon_code")
	public String getCouponCode() {
		return this.couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	@Column(name = "content")
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

	@Column(name = "consume_price", precision = 18)
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

	@Column(name = "max_uses")
	public Integer getMaxUses() {
		return this.maxUses;
	}

	public void setMaxUses(Integer maxUses) {
		this.maxUses = maxUses;
	}

	@Column(name = "used_count")
	public Integer getUsedCount() {
		return this.usedCount;
	}

	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "delete_status")
	public Integer getDeleteStatus() {
		return this.deleteStatus;
	}

	public void setDeleteStatus(Integer status) {
		this.deleteStatus = status;
	}
}