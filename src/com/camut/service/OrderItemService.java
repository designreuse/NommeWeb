package com.camut.service;

import com.camut.model.OrderItem;
import com.camut.model.api.OrderDetailsApiModel;

/**
 * @dao OrderItemService.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface OrderItemService {

	/**
	 * @Title: selectHistoryOrder
	 * @Description: 用户历史订单详情
	 * @param:  id
	 * @return: OrderDetailsApiModel
	 */
	public OrderDetailsApiModel selectHistoryOrder(long id);
	
	/**
	 * @Title: selectHistoryOrder
	 * @Description: 用户历史订单详情
	 * @param:  id
	 * @return: OrderDetailsApiModel
	 */
	public OrderDetailsApiModel selectHistoryOrderWebUsed(long id);
	
	/**
	 * @Title: addOrderItem
	 * @Description: 用户订单新增
	 * @param:  orderItem  
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	public long addOrderItem(OrderItem orderItem);
	
	/**
	 * @Title: getOrderItemById
	 * @Description: 订单详情
	 * @param:  id
	 * @return: OrderItem
	 */
	public OrderItem getOrderItemById(long id);
}
