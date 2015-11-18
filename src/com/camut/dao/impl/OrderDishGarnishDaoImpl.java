package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.camut.dao.OrderDishGarnishDao;
import com.camut.model.OrderDishGarnish;

@Repository
public class OrderDishGarnishDaoImpl extends BaseDao<OrderDishGarnish> implements OrderDishGarnishDao {

	/**
	 * @Title: addOrderDishGarnish
	 * @Description: 新增订单时增加订单中菜品的配菜
	 * @param:  orderDishGarnish
	 * @return:  -1表示新增失败 ，1表示返回的是新增成功
	 */
	@Override
	public int addOrderDishGarnish(OrderDishGarnish orderDishGarnish) {
		try{
			this.save(orderDishGarnish);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @Title: getOrderDishGarnishByorderItemIdAndDishId
	 * @Description: 通过订单条目的Id 和菜品Id获取某个订单下的某个订单菜品的配菜列表
	 * @param: int orderItemId
	 * @param: int dishId
	 * @return List<OrderDishGarnish>  
	 */
	public List<OrderDishGarnish> getOrderDishGarnishByorderItemIdAndDishId(int orderItemId){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("orderItemId", (long)orderItemId);
		String hql = "from OrderDishGarnish odg where odg.orderItem.id=:orderItemId";
		try{
			 List<OrderDishGarnish> list = this.find(hql, map);
			return list;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
