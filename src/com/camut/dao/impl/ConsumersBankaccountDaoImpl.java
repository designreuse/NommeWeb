package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.ConsumersBankaccountDao;
import com.camut.model.ConsumersBankaccount;

/**
 * @daoimpl CustomerBankaccountDaoImpl.java
 * @author zhangyunfei
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ConsumersBankaccountDaoImpl extends BaseDao<ConsumersBankaccount> implements ConsumersBankaccountDao {

	/**
	 * @Title: getConsumersBankaccountById
	 * @Description: 通用户id查找用户 银行卡
	 * @param:    id
	 * @return: List<ConsumersBankaccount>
	 */
	@Override
	public List<ConsumersBankaccount> getConsumersBankaccountByConsumerUuid(String restaurantUuid) {
		String hql = "from ConsumersBankaccount ca where ca.consumers.uuid=:restaurantUuid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		List<ConsumersBankaccount> cbList = this.find(hql, map);
		return cbList;
	}
	
	/**
	 * @Title: getConsumersBankaccount
	 * @Description: 通id查找用户 银行卡
	 * @param:    id
	 * @return: ConsumersBankaccount
	 */
	@Override
	public ConsumersBankaccount getConsumersBankaccount(long id) {
		String hql = "from ConsumersBankaccount ca where ca.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: addBankaccount
	 * @Description: 添加用户银行卡   added new card to customer's card list
	 * @param:  customerBankaccount对象  
	 * @return: int -1表示添加失败 -1: registration failed
	 */
	@Override
	public int addBankaccount(ConsumersBankaccount consumersBankaccount) {
		try {
			this.save(consumersBankaccount);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: deleteBankaccount
	 * @Description:  通过银行卡卡号删除银行卡
	 * @param:  customerBankaccount对象  
	 * @return:  1表示清除成功 ，-1表示清除  
	 */
	@Override
	public int deleteBankaccount(String card) {
		String hql = "from ConsumersBankaccount cb where cb.card=:card";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("card", card);
		ConsumersBankaccount cb = this.get(hql, map);
		try {
			this.delete(cb);
			return 1;
		} catch (Exception e) {
			return -1;
		}

	}

	/**
	 * @Title: getBankaccountByCard
	 * @Description:  判断card是否存在
	 * @param:    card
	 * @return: 1：银行卡号已经存在 -1：银行卡号不存在
	 */
	@Override
	public ConsumersBankaccount getBankaccountByCard(String card) {
		String hql = "from ConsumersBankaccount cb where cb.card=:card";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("card", card);
		return this.get(hql, map);
	}

}
