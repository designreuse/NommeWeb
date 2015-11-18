package com.camut.dao;

import java.util.List;

import com.camut.model.OrderDishGarnish;

public interface OrderDishGarnishDao {

	/**
	 * @Title: addOrderDishGarnish
	 * @Description: 新增订单时增加订单中菜品的配菜
	 * @param:  orderDishGarnish
	 * @return:  -1表示新增失败 ，1表示返回的是新增成功
	 */
	public int addOrderDishGarnish(OrderDishGarnish orderDishGarnish);
	
	/**
	 * @Title: getOrderDishGarnishByorderItemIdAndDishId
	 * @Description: 通过订单条目的Id 和菜品Id获取某个订单下的某个订单菜品的配菜列表
	 * @param: int orderItemId
	 * @param: int dishId
	 * @return List<OrderDishGarnish>  
	 */
	public List<OrderDishGarnish> getOrderDishGarnishByorderItemIdAndDishId(int orderItemId);
}
