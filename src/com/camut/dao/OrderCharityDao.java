package com.camut.dao;

import com.camut.model.OrderCharity;

/**
 * @ClassName OrderCharityDao.java
 * @author zyf
 * @createtime 2015-08-11
 * @author
 * @updateTime
 * @memo
 */
public interface OrderCharityDao {

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
	 * @Title: countOrderCharityByCharityId
	 * @Description: 获取某个慈善机构下的捐款订单数量
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	public int countOrderCharityByCharityId(int charityId);
	
}
