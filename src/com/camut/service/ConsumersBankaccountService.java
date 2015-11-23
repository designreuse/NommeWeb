package com.camut.service;

import java.util.List;

import com.camut.model.CardEntity;
import com.camut.model.api.ConsumerBankaccountApiModel;

/**
 * @Service CustomerBankaccountService.java
 * @author zhangyunfei 
 * @createtime May 27, 2015 
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersBankaccountService {

	/**
	 * @Title: getConsumersBankaccountByuuId
	 * @Description: 通用户id查找用户 银行卡
	 * @param:    uuid
	 * @return: List<ConsumerBankaccountApiModel>
	 */
	public List<CardEntity> getConsumersBankaccountByUuid(String consumerUuid);
	
	/**
	 * @Title: addBankaccount
	 * @Description: 添加用户银行卡   added new card to customer's card list
	 * @param:  customerBankaccount对象  
	 * @return: int -1表示添加失败，1表示添加成功    -1: registration failed
	 */
	public int addBankaccount(ConsumerBankaccountApiModel consumersBankaccount,String stripeConsumerUuid);
	
	/**
	 * @Title: deleteBankaccount
	 * @Description:  通过银行卡卡号删除银行卡
	 * @param:  card
	 * @return: int -1删除失败 ，1删除成功
	 */
	public int deleteBankaccount(String card);
	
}
