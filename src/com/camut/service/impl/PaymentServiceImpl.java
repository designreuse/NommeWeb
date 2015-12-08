/**
 * 
 */
package com.camut.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.ConsumersDao;
import com.camut.dao.OrderDao;
import com.camut.dao.RestaurantsDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.ApiResponse;
import com.camut.model.CardEntity;
import com.camut.model.ChargeEntity;
import com.camut.model.Consumers;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.service.PaymentService;
import com.camut.utils.CommonUtil;
import com.camut.utils.GoogleTimezoneAPIUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.StringUtil;
import com.camut.utils.StripeUtil;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccountCollection;

/**
 * @ClassName PaymentServiceImpl.java
 * @author wangpin
 * @createtime Jul 21, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private ConsumersDao consumersDao;
	
	@Autowired 
	private RestaurantsDao restaurantsDao;
	
	@Autowired 
	private OrderDao orderDao;
	
	@Override
	public void pay() {

		Stripe.apiKey = StripeUtil.getApiKey();

		Map<String, Object> chargeParams = new HashMap<String, Object>();
		chargeParams.put("amount", 2000);
		chargeParams.put("currency", "usd");
		chargeParams.put("customer", "cus_6fMPJJemtJoaaA");
		chargeParams.put("card", "card_16S1oBK629Jm0Bi8ZgzL92sL");
		/*Map<String, Object> chargeParams = new HashMap<String, Object>();
		
		chargeParams.put("amount", 2000);
		chargeParams.put("currency", "usd");*/
		
		//String cardId = "card_16S1oBK629Jm0Bi8ZgzL92sL";
		//Map<String, Object> cardParams = new HashMap<String, Object>();
		
		try {
			/*String customer_id = "cus_6fMPJJemtJoaaA";
			Customer cu = Customer.retrieve(customer_id);
			ExternalAccount source = cu.getSources().retrieve(cardId);
			Card card = null;
			if(source.getObject().equals("card")){
				card = (Card)source;
			}*/
			//cardParams.put("card", "card_16S8GRK629Jm0Bi8gRGMtWKm");
			//Token token2 = Token.create(cardParams);
			//System.out.println(token2);
			Charge charge = Charge.create(chargeParams);
			System.out.println(charge);
			/*chargeParams.put("customer", "cus_6fMPJJemtJoaaA");
			Charge charge = Charge.create(chargeParams);
			System.out.println(charge);*/
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CardException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* @Title: getCard
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public void getCard(String token) {
		Stripe.apiKey = StripeUtil.getApiKey();
		
		try {
			
			String customer_id = "cus_6fMPJJemtJoaaA";
			Customer cu = Customer.retrieve(customer_id);
			
			Map<String, Object> cardParams = new HashMap<String, Object>();
			cardParams.put("object", "card");
			ExternalAccountCollection collection = cu.getSources().all(cardParams);
			System.out.println(collection);
			/*Map<String, Object> params = new HashMap<String, Object>();
			params.put("source", token);
			Card card = cu.createCard(params);
			System.out.println(card);*/
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* @Title: getCustomer
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public void getCustomer(String token) {
		Stripe.apiKey = StripeUtil.getApiKey();
		Map<String, Object> customerParams = new HashMap<String, Object>();
		customerParams.put("source", token); 
		try {
			Customer cu = Customer.create(customerParams);
			System.out.println(cu);
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Title: addCard
	 * @Description:增加银行卡
	 * @param:    CardEntity
	 * @return: int 1成功，其他失败
	 */
	@Override
	public int addCard(CardEntity cardEntity,String consumerUuid) {
		if(cardEntity!=null && StringUtil.isNotEmpty(consumerUuid)){
			Consumers consumers = consumersDao.getConsumersByUuid(consumerUuid);
			if(consumers!=null){
				String customerId = consumers.getStripeCustomerId();
				if (StringUtil.isEmpty(customerId)) {//还没有注册
					customerId = CommonUtil.createCustomer(consumers.getEmail());
					if (StringUtil.isEmpty(customerId)) {//注册失败
						return 0;
					}
				}
				String cardId = CommonUtil.customerAddCard(cardEntity, customerId);
				if (StringUtil.isNotEmpty(cardId)) {//添加卡成功
					consumers.setStripeCustomerId(customerId);
					int flag = consumersDao.updateConsumers(consumers);
					if(flag==1){//修改成功
						return 1;
					}
				}
			}
		}
		return 0;
	}
	
	
	/**
	 * @Title: listAllCards
	 * @Description:列出客户所有的银行卡
	 * @param:    String consumerId 平台的客户ID
	 * @return: List<CardEntity> null失败,没有
	 */
	@Override
	public List<CardEntity> listAllCards(String consumerUuid) {
		if(StringUtil.isNotEmpty(consumerUuid)){
			Consumers consumers = consumersDao.getConsumersByUuid(consumerUuid);
			if(consumers!=null && StringUtil.isNotEmpty(consumers.getStripeCustomerId())){
				List<CardEntity> list = CommonUtil.listAllCards(consumers.getStripeCustomerId());
				return list;
			}
		}
		return null;
	}

	/**
	 * @Title: chargeByCard
	 * @Description:指定的卡收款
	 * @param:    CardEntity cardEntity,ChargeEntity chargeEntity
	 * @return: ApiResponse 1成功 其他失败
	 */
	@Override
	public ApiResponse chargeByCard(ChargeEntity chargeEntity,String orderId) {
		if (chargeEntity!=null && StringUtil.isNotEmpty(orderId)) {
			OrderHeader order = orderDao.getOrderById(Long.parseLong(orderId));
			Log4jUtil.info("付款==>"+"total="+order.getTotal()+"tax="+order.getTax()+"amount="+order.getAmount());
			chargeEntity.setAmount((int)(order.getAmount()*100));
			Log4jUtil.info("用指定卡付款时的Amount==>"+chargeEntity.getAmount());
			int collectedFee = (int) Math.round(((order.getTotal()+order.getTax())*0.1+order.getAmount()*0.029)*100+30);
			chargeEntity.setApplicatonFee(collectedFee);
			ApiResponse stripeResponse = CommonUtil.chargeByCardId(chargeEntity);
			String chargeId = stripeResponse.getResponseString();
			Log4jUtil.info("付款==>"+"collectedFee="+collectedFee);
			if (StringUtil.isNotEmpty(chargeId)) {
				if(StringUtil.isNotEmpty(orderId)){
					if (order != null) {
						order.setChargeId(chargeId);
						//TODO: paid orders still not be accepted automatically
						order.setStatus(2);// 订单状态修改为已支付
						orderDao.updateOrderHeader(order);
					}
				}
				stripeResponse.setStatusEnum(1);
				return stripeResponse;
			}
			stripeResponse.setStatusEnum(0);
			return stripeResponse;
		}
		return new ApiResponse(0, null, null);
	}

	/**
	 * @Title: refundByOrder
	 * @Description: 退款
	 * @param:    
	 * @return: int
	 */
	@Override
	public int refundByOrder(long orderId) {
		OrderHeader orderHeader = orderDao.getOrderById(orderId);
				if (orderHeader!=null) {
					String refundId = null;
					if(StringUtil.isNotEmpty(orderHeader.getChargeId())){//信用卡付款的订单
						refundId = CommonUtil.refundAll(orderHeader.getChargeId());
						if(StringUtil.isNotEmpty(refundId)){
							orderHeader.setStatus(GlobalConstant.CANCELED_ORDER);
							orderHeader.setChargeId(refundId);
							orderDao.updateOrderHeader(orderHeader);
							return 1;
						}else{
							return -1;
						}
					}else{//现金付款的订单
						orderHeader.setStatus(GlobalConstant.CANCELED_ORDER);
						orderDao.updateOrderHeader(orderHeader);
						return 1;
					}
					/*else if(orderHeader.getOrderType()==3){
						refundId = "success";
						orderHeader.setStatus(6);
					}
					if (StringUtil.isNotEmpty(refundId)) {
						orderDao.updateOrderHeader(orderHeader);
						return 1;
					}*/
				}
		return -1;
	}

	/**
	 * @Title: deleteCard
	 * @Description:删除卡
	 * @param:    String cardId,String consumerId
	 * @return: int -1失败  1成功
	 */
	@Override
	public int deleteCard(String cardId, String consumerUuid) {
		Consumers consumers = consumersDao.getConsumersByUuid(consumerUuid);
		if (consumers!=null && StringUtil.isNotEmpty(consumers.getStripeCustomerId())) {
			return CommonUtil.deleteCard(consumers.getStripeCustomerId(), cardId);
		}
		return -1;
	}

}
