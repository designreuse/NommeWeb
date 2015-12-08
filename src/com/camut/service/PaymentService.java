/**
 * 
 */
package com.camut.service;

import java.util.List;

import com.camut.model.ApiResponse;
import com.camut.model.CardEntity;
import com.camut.model.ChargeEntity;

/**
 * @ClassName PaymentService.java
 * @author wangpin
 * @createtime Jul 21, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface PaymentService {

	public void pay();
	
	public void getCard(String token);
	
	public void getCustomer(String token);
	
	/**
	 * @Title: addCard
	 * @Description:增加银行卡
	 * @param:    CardEntity cardEntity,String consumerUuid
	 * @return: int
	 */
	public int  addCard(CardEntity cardEntity,String consumerUuid);
	
	/**
	 * @Title: listAllCards
	 * @Description:列出用户所有银行卡
	 * @param:    CardEntity cardEntity,String consumerId
	 * @return: int
	 */
	public List<CardEntity>  listAllCards(String consumerUuid);
	
	/**
	 * @Title: chargeByCard
	 * @Description:指定的卡收款
	 * @param:    CardEntity cardEntity,ChargeEntity chargeEntity
	 * @return: ApiResponse 1成功 其他失败
	 */
	public ApiResponse chargeByCard(ChargeEntity chargeEntity,String orderId);
	
	/**
	 * @Title: refundByOrder
	 * @Description: 退款
	 * @param:    
	 * @return: int
	 */
	public int refundByOrder(long orderId);
	
	/**
	 * @Title: deleteCard
	 * @Description:删除卡
	 * @param:    String cardId,String consumerId
	 * @return: int -1失败  1成功
	 */
	public int deleteCard(String cardId,String consumerUuid);
	
}
