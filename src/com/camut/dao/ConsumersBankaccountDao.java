package com.camut.dao;

import java.util.List;
import com.camut.model.ConsumersBankaccount;

/**
 * @dao CustomerBankaccountDao.java
 * @author zhangyunfei
 * @createtime May 27, 2015
 * @author
 * @updateTime
 * @memo
 */

public interface ConsumersBankaccountDao {

	/**
	 * @Title: getConsumersBankaccountById
	 * @Description: 通用户id查找用户 银行卡
	 * @param:    id
	 * @return: List<ConsumersBankaccount>
	 */
	public List<ConsumersBankaccount> getConsumersBankaccountById(long id);
	
	/**
	 * @Title: getConsumersBankaccount
	 * @Description: 通id查找用户 银行卡
	 * @param:    id
	 * @return: ConsumersBankaccount
	 */
	public ConsumersBankaccount getConsumersBankaccount(long id);
	 
	/**
	 * @Title: addBankaccount
	 * @Description: 添加用户银行卡   added new card to customer's card list
	 * @param:  customerBankaccount对象  
	 * @return: int -1表示添加失败，1表示添加成功    -1: registration failed
	 */
	public int addBankaccount(ConsumersBankaccount consumersBankaccount);
	
	/**
	 * @Title: deleteBankaccount
	 * @Description:  通过银行卡卡号删除银行卡
	 * @param:  card
	 * @return: int -1删除失败 ，1删除成功
	 */
	public int deleteBankaccount(String card);
	
	/**
	 * @Title: getBankaccountByCard
	 * @Description:  判断card是否存在
	 * @param:    card
	 * @return: 1：银行卡号已经存在 -1：银行卡号不存在
	 */
	public ConsumersBankaccount getBankaccountByCard(String card);

}
