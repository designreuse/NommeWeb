package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.OrderItemDao;
import com.camut.model.OrderItem;

/**
 * @dao OrderItemDaoImpl.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class OrderItemDaoImpl extends BaseDao<OrderItem> implements OrderItemDao {

	/**
	 * @Title: selectHistoryOrder
	 * @Description: 用户历史订单列表
	 * @param:  id
	 * @return: OrderHeader
	 */
	@Override
	public OrderItem selectHistoryOrder(long id) {
		String hql = "from OrderItem oi where oi.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getOrderItemById
	 * @Description: 订单详情
	 * @param:  id
	 * @return: OrderItem
	 */
	@Override
	public OrderItem getOrderItemById(long id) {
		String hql = "from OrderItem oh where oh.orderHeader.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/**
	 * @Title: addOrderItem
	 * @Description: 用户订单新增
	 * @param:  orderItem
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	@Override
	public long addOrderItem(OrderItem orderItem) {
		try {
			long a = (Long)this.save(orderItem);
			return a;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: getOrderItemsByOrderHeaderId
	 * @Description: 根据OrderHeaderId获取对象
	 * @param:  orderHeaderId
	 * @return:  List<OrderItem>
	 */
	@Override
	public List<OrderItem> getOrderItemsByOrderHeaderId(long orderHeaderId) {
		String hql = "from OrderItem oh where oh.orderHeader.id=:orderHeaderId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderHeaderId", orderHeaderId);
		return this.find(hql, map);
	}
	
}
