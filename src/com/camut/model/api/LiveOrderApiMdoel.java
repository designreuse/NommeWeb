package com.camut.model.api;

import java.util.List;

public class LiveOrderApiMdoel {

	public List<Long> orderId;
	public int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Long> getOrderId() {
		return orderId;
	}

	public void setOrderId(List<Long> orderId) {
		this.orderId = orderId;
	}

	
	
	
}
