package com.camut.model.api;

import java.io.Serializable;
import java.util.Date;

/**
 * @entity EvaluateApiModel . 
 * @author zyf	
 * @createTime 2015-05-29
 * @author 
 * @updateTime 
 * @memo 
 */
public class EvaluateApiModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6498682244203908832L;
	private String content="";// 评价内容 
	private String score;// 评分 
	private Date createtime=new Date();// 创建时间
	private String consumerUuid="";// 用户id
	private String restaurantUuid="";// 店铺id
	private Integer orderHeaderId;//订单Id
	private Integer status;// 状态
	private String lastName="";// 姓
	private String firstName="";// 名
	private String showName="";
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getShowName() {
		return this.firstName+" "+this.lastName;
	}
	public Integer getOrderHeaderId() {
		return orderHeaderId;
	}
	public void setOrderHeaderId(Integer orderHeaderId) {
		this.orderHeaderId = orderHeaderId;
	}
	public String getConsumerUuid() {
		return consumerUuid;
	}
	public void setConsumerUuid(String consumerUuid) {
		this.consumerUuid = consumerUuid;
	}
	public String getRestaurantUuid() {
		return restaurantUuid;
	}
	public void setRestaurantUuid(String restaurantUuid) {
		this.restaurantUuid = restaurantUuid;
	}
	
	
}
