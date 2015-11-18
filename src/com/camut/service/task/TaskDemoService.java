package com.camut.service.task;


public interface TaskDemoService {
	
	/**
	 * @Title: timerTaskOrder
	 * @Description: 定时执行 修改订单状态
	 * @param: 
	 * @return void  
	 */
	public void timerTaskOrder();

	/*public void test();*/
	public void pushOrderid(long currentOrderId);
}
