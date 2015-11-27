package com.camut.model;

// default package

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @entity Evaluate . 
 * @author 王频
 * @createTime 2015-05-25
 * @author 
 * @updateTime 
 * @memo ratings and reviews
 */
@Entity
@Table(name = "tbl_evaluate")
public class Evaluate extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8077441435006577376L;
	private Restaurants restaurants;// 与商家多对一 many Evaluate: 1 Restaurants
	private String content;// 评价内容 review details
	private double score;// 评分 rating
	private Integer status;// 状态
	private Date createtime;// 创建时间
	private Consumers consumers;//与会员多对一，单向关联 many consumers : 1 Restaurants
	private Integer orderHeaderId;//订单Id
	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurants_uuid")
	public Restaurants getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Restaurants restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "score")
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "createtime", length = 10)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="consumers_uuid")
	public Consumers getConsumers() {
		return consumers;
	}

	public void setConsumers(Consumers consumers) {
		this.consumers = consumers;
	}

	@Column(name = "order_header_id")
	public Integer getOrderHeaderId() {
		return orderHeaderId;
	}

	public void setOrderHeaderId(Integer orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}

	

}