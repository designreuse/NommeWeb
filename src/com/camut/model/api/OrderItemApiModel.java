package com.camut.model.api;

public class OrderItemApiModel {

	private long orderId; //订单id
	private Integer status;// 3：已接单(accpet) 4:拒绝接单(reject) 8:line up
	private String instruction;// 特殊需求
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getInstruction() {
		return instruction;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	

}
