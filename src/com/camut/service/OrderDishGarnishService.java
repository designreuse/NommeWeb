package com.camut.service;

import com.camut.model.OrderDishGarnish;

public interface OrderDishGarnishService {
	
	/**
	 * @Title: addOrderDishGarnish
	 * @Description: 新增订单时增加订单中菜品的配菜
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，1表示返回的是新增成功
	 */
	public int addOrderDishGarnish(OrderDishGarnish orderDishGarnish);
}
