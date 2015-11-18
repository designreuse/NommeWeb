package com.camut.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.ConsumersBankaccountDao;
import com.camut.dao.ConsumersDao;
import com.camut.model.CardEntity;
import com.camut.model.Consumers;
import com.camut.model.api.ConsumerBankaccountApiModel;
import com.camut.service.ConsumersBankaccountService;
import com.camut.utils.CommonUtil;

/**
 * @ServiceImpl CustomerBankaccountServiceImpl.java
 * @author zhangyunfei
 * @createtime May 27, 2015 
 * @author
 * @updateTime
 * @memo
 */
@Service
public class ConsumersBankaccountServiceImpl implements ConsumersBankaccountService {

	@Autowired
	private ConsumersBankaccountDao consumersBankaccountDao;//自动注入customerBankaccountDao
	
	@Autowired
	private ConsumersDao  consumerDao;

	/**
	 * @Title: getConsumersBankaccountById
	 * @Description: 通用户id查找用户 银行卡
	 * @param:    id
	 * @return: List<ConsumerBankaccountApiModel>
	 */
	@Override
	public List<CardEntity> getConsumersBankaccountById(long id) {
		Consumers consumers=consumerDao.getConsumersById(id);		
		return CommonUtil.listAllCards(consumers.getStripeCustomerId());
	}

	/**
	 * @Title: addBankaccount
	 * @Description: 添加用户银行卡   added new card to customer's card list
	 * @param:  customerBankaccount对象  
	 * @return: int -1表示添加失败，1表示添加成功    -1: registration failed
	 */
	@Override
	public int addBankaccount(ConsumerBankaccountApiModel consumerBankaccountApiModel,String stripeConsumerId) {
		Consumers consumers = new Consumers();
		consumers.setId(consumerBankaccountApiModel.getConsumerId());
		consumers=consumerDao.getConsumersById(consumerBankaccountApiModel.getConsumerId());	
		consumers.setStripeCustomerId(stripeConsumerId);		
		return consumerDao.updateConsumers(consumers);
	}

	/**
	 * @Title: deleteBankaccount
	 * @Description:  通过银行卡卡号删除银行卡
	 * @param:  card
	 * @return: int -1删除失败 ，1删除成功
	 */
	@Override
	public int deleteBankaccount(String card) {
		if (card != null && card.length() > 0) {
			return consumersBankaccountDao.deleteBankaccount(card);
		}
		return -1;
	}

}
