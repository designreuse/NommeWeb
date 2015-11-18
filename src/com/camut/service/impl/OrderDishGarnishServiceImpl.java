package com.camut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.OrderDishGarnishDao;
import com.camut.model.OrderDishGarnish;
import com.camut.service.OrderDishGarnishService;

@Service
public class OrderDishGarnishServiceImpl implements OrderDishGarnishService {

	@Autowired private OrderDishGarnishDao orderDishGarnishDao;
	
	/**
	 * @Title: addOrderDishGarnish
	 * @Description: 新增订单时增加订单中菜品的配菜
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，1表示返回的是新增成功
	 */
	@Override
	public int addOrderDishGarnish(OrderDishGarnish orderDishGarnish) {
		
		return orderDishGarnishDao.addOrderDishGarnish(orderDishGarnish);
	}

}
