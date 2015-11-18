package com.camut.service;

import java.util.List;

import com.camut.model.OrderCharity;

/**
 * @ClassName OrderCharityService.java
 * @author zyf
 * @createtime 2015-08-11
 * @author
 * @updateTime
 * @memo
 */
public interface OrderCharityService {

	/**
	 * @Title: saveOrderCharity
	 * @Description: 保存用户与慈善机构的方法
	 * @param:    
	 * @return: 
	 */
	public int saveOrderCharity(OrderCharity orderCharity);
	
	/**
	 * @Title: deleteOrderCharity
	 * @Description: 根据订单ID删除该笔订单的捐款数据
	 * @param: @param orderHeaderId
	 * @param: @return
	 * @return int  
	 */
	public int deleteOrderCharity(int orderHeaderId);
	
	/**
	 * @Title: getOrderCharityByCharityId
	 * @Description: 通过慈善机构ID获取该慈善机构下的所有捐款
	 * @param: @return
	 * @return List<OrderCharity>  
	 */
	public List<OrderCharity> getOrderCharityByCharityId(int charityId);
	
	/**
	 * @Title: countOrderCharityByCharityId
	 * @Description: 获取某个慈善机构下的捐款订单数量
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	public int countOrderCharityByCharityId(int charityId);
}
