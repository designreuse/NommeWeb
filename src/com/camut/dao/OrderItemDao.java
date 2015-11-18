package com.camut.dao;

import java.util.List;

import com.camut.model.OrderItem;

/**
 * @dao OrderDao.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface OrderItemDao {

	/**
	 * @Title: selectHistoryOrder
	 * @Description: 用户历史订单列表
	 * @param:  id
	 * @return: OrderHeader
	 */
	public OrderItem selectHistoryOrder(long id);
	
	/**
	 * @Title: getOrderItemById
	 * @Description: 订单详情
	 * @param:  id
	 * @return: OrderItem
	 */
	public OrderItem getOrderItemById(long id);
	
	/**
	 * @Title: addOrderItem
	 * @Description: 用户订单新增
	 * @param:  orderItem
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	public long addOrderItem(OrderItem orderItem);
	
	/**
	 * @Title: getOrderItemsByOrderHeaderId
	 * @Description: 根据OrderHeaderId获取对象
	 * @param:  orderHeaderId
	 * @return:  List<OrderItem>
	 */
	public List<OrderItem> getOrderItemsByOrderHeaderId(long orderHeaderId);
	
}
