package com.camut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.OrderCharityDao;
import com.camut.dao.OrderDao;
import com.camut.model.OrderCharity;
import com.camut.model.OrderHeader;
import com.camut.service.OrderCharityService;

@Service
public class OrderCharityServiceImpl implements OrderCharityService {

	@Autowired private OrderCharityDao orderCharityDao;
	@Autowired private OrderDao orderDao;
	
	/**
	 * @Title: saveOrderCharity
	 * @Description: 保存用户与慈善机构的方法
	 * @param:    
	 * @return: 
	 */
	@Override
	public int saveOrderCharity(OrderCharity orderCharity) {
		if (orderCharity != null) {
			OrderHeader oh = orderDao.getOrderById(orderCharity.getOrderId());
			orderCharity.setMoney(oh.getTotal() * 0.05);
			return orderCharityDao.saveOrderCharity(orderCharity);
		}
		return -1;
	}
	
	/**
	 * @Title: deleteOrderCharity
	 * @Description: 根据订单ID删除该笔订单的捐款数据
	 * @param: @param orderHeaderId
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int deleteOrderCharity(int orderHeaderId){
		
		return orderCharityDao.deleteOrderCharity(orderHeaderId);
	}

	/**
	 * @Title: getOrderCharityByCharityId
	 * @Description: 通过慈善机构ID获取该慈善机构下的所有捐款
	 * @param: @return
	 * @return List<OrderCharity>  
	 */
	@Override
	public List<OrderCharity> getOrderCharityByCharityId(int charityId) {
		return null;
	}

	/**
	 * @Title: countOrderCharityByCharityId
	 * @Description: 获取某个慈善机构下的捐款订单数量
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int countOrderCharityByCharityId(int charityId) {
		return orderCharityDao.countOrderCharityByCharityId(charityId);
	}

	
}
