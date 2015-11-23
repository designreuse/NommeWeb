package com.camut.model.api;

public class CartHeaderLoginApiModel {

	private Integer orderType;// 订单种类
	private String consumerUuid;// 客户id
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getConsumerUuid() {
		return consumerUuid;
	}
	public void setConsumerUuid(String consumerUuid) {
		this.consumerUuid = consumerUuid;
	}
	

}
