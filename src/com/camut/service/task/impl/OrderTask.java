package com.camut.service.task.impl;

import java.util.TimerTask;

@Deprecated
public class OrderTask extends TimerTask{

	private String orderId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("定时执行任务"+ orderId);
		
		
	}

	
}
