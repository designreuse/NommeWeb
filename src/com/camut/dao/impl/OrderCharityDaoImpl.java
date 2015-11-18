package com.camut.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.camut.dao.OrderCharityDao;
import com.camut.model.OrderCharity;

/**
 * @ClassName OrderCharityDaoImpl.java
 * @author zyf
 * @createtime 2015-08-11
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class OrderCharityDaoImpl extends BaseDao<OrderCharity> implements OrderCharityDao {

	/**
	 * @Title: saveOrderCharity
	 * @Description: 保存用户与慈善机构的方法
	 * @param:    
	 * @return: 
	 */
	@Override
	public int saveOrderCharity(OrderCharity orderCharity) {
		try {
			this.save(orderCharity);
			return 1;
		} catch (Exception e) {
			return -1;
		}
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
		String hql = "from OrderCharity o where o.orderId = "+orderHeaderId;
		OrderCharity o = this.get(hql);
		try {
			if(o!=null){
				this.delete(o);
				return 1;
			}else{
				return -1;
			}
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: countOrderCharityByCharityId
	 * @Description: 获取某个慈善机构下的捐款订单数量
	 * @param: @param charityId
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int countOrderCharityByCharityId(int charityId){
		String sql = "select count(*)from tbl_order_charity a where a.charity_id=:charityId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("charityId", charityId);
		int t = this.countBySql(sql, map).intValue();
		
		return t;
	}

	
	
}
