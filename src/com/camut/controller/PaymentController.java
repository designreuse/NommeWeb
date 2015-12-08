/**
 * 
 */
package com.camut.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.CardEntity;
import com.camut.model.CartHeader;
import com.camut.model.ChargeEntity;
import com.camut.model.Charity;
import com.camut.model.Consumers;
import com.camut.model.OrderCharity;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.pageModel.PageCartHeader;
import com.camut.pageModel.PageMessage;
import com.camut.service.CartHeaderService;
import com.camut.service.CartService;
import com.camut.service.CharityService;
import com.camut.service.ConsumersService;
import com.camut.service.OrderCharityService;
import com.camut.service.OrderService;
import com.camut.service.PaymentService;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.CommonUtil;
import com.camut.utils.PushUtil;
import com.camut.utils.StringUtil;
import com.camut.utils.StripeUtil;

/**
 * @ClassName PaymentController.java
 * @author wangpin
 * @createtime Jul 21, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("PaymentController")
@RequestMapping("payment")
public class PaymentController {

	@Autowired private PaymentService paymentService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private ConsumersService consumersService;
	@Autowired private OrderService orderService;
	@Autowired private CartHeaderService cartHeaderService;
	@Autowired private CartService cartService;
	@Autowired private CharityService charityService;
	@Autowired private OrderCharityService orderCharityService;
	@Autowired private RestaurantsUserService restaurantsUserService;

	
	/**
	 * @Title: getStripePublishableKey
	 * @Description: Gets the publishable key for Stripe.
	 * @return: String
	 */
	@RequestMapping(value = "getStripePublishableKey", method = RequestMethod.POST)
	@ResponseBody
	public String getStripePublishableKey() {
		return StripeUtil.getPublishableKey();
	}
	
	/**
	 * @Title: getTestPay
	 * @Description: 返回支付页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value = "getPayment")
	public String getTestPay(HttpSession session,Model model) {
		Consumers consumers = (Consumers) session.getAttribute(GlobalConstant.SESSION_CONSUMER);
		if(consumers!=null && consumers.getStripeCustomerId()!=null){//之前保存过
			Consumers consumers2 = consumersService.getConsumersByUuid(consumers.getUuid());
			session.setAttribute("consumer", consumers2);
			List<CardEntity> list = CommonUtil.listAllCards(consumers2.getStripeCustomerId());
			model.addAttribute("cards", list);
		}
		PageCartHeader pageCartHeader = cartHeaderService.getPageCartHeaderByConsumerUuid(consumers.getUuid());
		model.addAttribute("cart", pageCartHeader);
		return "home/payment";
	}

	/**
	 * @Title: pay
	 * @Description: 通过卡的详细信息付款
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value = "/payByDetail", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage pay(ChargeEntity chargeEntity,String consumerUuid,String orderId) {
		PageMessage pm = new PageMessage();
		String errorMessage = null;
		if (chargeEntity != null && StringUtil.isNotEmpty(consumerUuid)) {
			int orderid = 0;
			if (StringUtil.isEmpty(orderId)) {
				CartHeader cartHeader = cartHeaderService.getCartHeaderByConsumerUuid(consumerUuid);
				//将购物车转换成订单对象
				OrderHeader orderHeader = orderService.CartHeaderToOrderHeaderForWeb(cartHeader.getId());
				DecimalFormat format = new DecimalFormat("###.00");
				orderHeader.setTip(Double.parseDouble(format.format((double)chargeEntity.getAmount()/100-orderHeader.getAmount())));
				//计算小费
				//orderHeader.setTip((double)chargeEntity.getAmount()/100-orderHeader.getAmount());
				orderHeader.setPayment(1);
				orderHeader.setStatus(1);
				orderHeader.setAmount(Double.parseDouble(format.format((double)chargeEntity.getAmount()/100)));
				//保存订单
				orderid = orderService.addOrder(orderHeader);
				if (orderid!=-1) {
					//删除购物车
					int flag1 = cartService.deleteCartByConsumerUuid(consumerUuid);
					if (flag1==-1) {
						pm.setErrorMsg(MessageConstant.PAY_FAIL);
						pm.setSuccess(false);
						return pm;
					}
				}
				
			}
			else{
				orderid = Integer.parseInt(orderId);
			}
			
			if (orderid!=-1) {//保存成功
				pm.setFlag(orderid);
				OrderHeader orderHeader = orderService.getOrderById(orderid);
					Restaurants restaurants = restaurantsService.getRestaurantsByUuid(orderHeader.getRestaurantUuid());
					if (restaurants!=null && StringUtil.isNotEmpty(restaurants.getStripeAccount())) {
						chargeEntity.setAccountId(restaurants.getStripeAccount());
						if (chargeEntity.getSave()!=null && chargeEntity.getSave()==1) {//需要保存卡信息
							Consumers consumers = consumersService.getConsumersByUuid(consumerUuid);
							if (consumers!=null) {//用户存在
								if (StringUtil.isEmpty(consumers.getStripeCustomerId())) {//不存在stripe的客户id了
									//创建stripe的customer
									String customerId = CommonUtil.createCustomer(consumers.getEmail());
									if (StringUtil.isNotEmpty(customerId)) {//创建成功
										consumers.setStripeCustomerId(customerId);
									}
									//将stripe的用户id保存到平台用户数据中
									consumersService.updateConsumersForNomme(consumers);
								}
								
								chargeEntity.setCustomerId(consumers.getStripeCustomerId());
								//保存卡的详细信息
								String cardId = CommonUtil.customerAddCardByToken(chargeEntity);
								if (StringUtil.isNotEmpty(cardId)) {//添加卡成功
									chargeEntity.setCardId(cardId);
									int flag = paymentService.chargeByCard(chargeEntity, String.valueOf(orderid));
									if(flag==1){//付款成功
										return pm;
									}
								}
							}
						}
						else{//不需要保存卡信息
							//OrderHeader orderHeader1 = orderService.getOrderById(orderid);
							//int collectedFee = (int) Math.round(((order.getTotal()+order.getTax())*0.1+order.getAmount()*0.029)*100+30);
							//chargeEntity.setApplicatonFee((int) ((orderHeader.getTotal()*0.1+orderHeader.getAmount()*0.029)*100+30));
							chargeEntity.setApplicatonFee((int) Math.round(((orderHeader.getTotal()+orderHeader.getTax())*0.1+orderHeader.getAmount()*0.029)*100+30));
							String chargeId = null;
							try {
								chargeId = CommonUtil.chargeByToken(chargeEntity);
							} catch (Exception e) {
								errorMessage = e.getMessage();
							}
							if (StringUtil.isNotEmpty(chargeId)) {//付款成功
								//将charge的id保存到订单中
								if (orderHeader!=null) {
									orderHeader.setStatus(2);
									orderHeader.setChargeId(chargeId);
									orderService.updateOrder(orderHeader);
								}
								return pm;
							}
						}
					}
				
				
			}
		}
		if (errorMessage != null && errorMessage != "") {
			pm.setErrorMsg(errorMessage);
		} else {
			pm.setErrorMsg(MessageConstant.PAY_FAIL);
		}
		pm.setSuccess(false);
		return pm;
	}
	
	/**
	 * 根据指定的卡号付款
	 * @param String cradId  stripe的卡id
	 * @param Integer amount 收款金额，美分
	 * @param String consumerId  平台的客户id
	 * @param String restaurantId 餐厅的id
	 * @param String orderId 订单的id
	 * @return ResultApiModel flag:0失败  1成功
	 */
	@RequestMapping(value="/payByCardId",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage chargeByCardId(ChargeEntity chargeEntity,String consumerUuid,String orderId){
		PageMessage pm = new PageMessage();
		//Consumers consumers = consumersService.getConsumersById(Long.parseLong(consumerId));
		if(chargeEntity!=null && StringUtil.isNotEmpty(consumerUuid)){
			int orderid = 0;
			if (StringUtil.isEmpty(orderId)) {
				CartHeader cartHeader = cartHeaderService.getCartHeaderByConsumerUuid(consumerUuid);
				//将购物车转换成订单对象
				OrderHeader orderHeader = orderService.CartHeaderToOrderHeaderForWeb(cartHeader.getId());
				if (orderHeader!=null) {
					//计算小费
					DecimalFormat format = new DecimalFormat("###.00");
					orderHeader.setTip(Double.parseDouble(format.format((double)chargeEntity.getAmount()/100-orderHeader.getAmount())));
					orderHeader.setAmount(Double.parseDouble(format.format((double)chargeEntity.getAmount()/100)));
					orderHeader.setPayment(1);
					orderHeader.setStatus(1);
					//保存订单
					orderid = orderService.addOrder(orderHeader);
					if (orderid!=-1) {
						//删除购物车
						int flag1 = cartService.deleteCartByConsumerUuid(consumerUuid);
						if (flag1==-1) {
							pm.setErrorMsg(MessageConstant.PAY_FAIL);
							pm.setSuccess(false);
							return pm;
						}
					}
				}
				else{
					pm.setErrorMsg(MessageConstant.PAY_FAIL);
					pm.setSuccess(false);
					return pm;
				}
				
			}
			else{
				orderid = Integer.parseInt(orderId);
			}
			if (orderid!=-1) {
				pm.setFlag(orderid);
				Consumers consumers = consumersService.getConsumersByUuid(consumerUuid);
				OrderHeader orderHeader = orderService.getOrderById(orderid);
					if (consumers!=null && orderHeader!=null) {
						chargeEntity.setCustomerId(consumers.getStripeCustomerId());
						Restaurants restaurants = restaurantsService.getRestaurantsByUuid(orderHeader.getRestaurantUuid());
						if (restaurants!=null && StringUtil.isNotEmpty(restaurants.getStripeAccount())) {
							chargeEntity.setAccountId(restaurants.getStripeAccount());
							int flag = paymentService.chargeByCard(chargeEntity,String.valueOf(orderid));
							if(flag==1){//付款成功
								return pm;
							}
						}
					}
					
			}
			
			}
		pm.setErrorMsg(MessageConstant.PAY_FAIL);
		pm.setSuccess(false);
		return pm;
	}
	
	
	/**
	 * 现金支付
	 * @param Integer amount 收款金额，美分
	 * @param String consumerId  平台的客户id
	 * @param String restaurantId 餐厅的id
	 * @return ResultApiModel flag:0失败  1成功
	 */
	@RequestMapping(value="/payByCash",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage chargeByCash(ChargeEntity chargeEntity,String consumerUuid,String orderId){
		PageMessage pm = new PageMessage();
		if (StringUtil.isEmpty(orderId)) {
			if(chargeEntity!=null && StringUtil.isNotEmpty(consumerUuid)){
				Consumers consumers = consumersService.getConsumersByUuid(consumerUuid);
				if (consumers!=null) {
					CartHeader cartHeader = cartHeaderService.getCartHeaderByConsumerUuid(consumerUuid);
					//将购物车转换成订单对象
					OrderHeader orderHeader = orderService.CartHeaderToOrderHeaderForWeb(cartHeader.getId());
					//计算小费
					orderHeader.setTip(0);
					//orderHeader.setAmount(Double.parseDouble(format.format(orderHeader.getAmount()+orderHeader.getTip())));
					orderHeader.setPayment(0);//现金支付
					orderHeader.setStatus(9);
					//保存订单
					int orderid = orderService.addOrder(orderHeader);
					if (orderid!=-1) {//保存成功
						//删除购物车
						pm.setFlag(orderid);
						cartService.deleteCartByConsumerUuid(consumerUuid);
						return pm;
					}
				}
			}
		}
		else{
			pm.setFlag(Integer.parseInt(orderId));
			OrderHeader orderHeader = orderService.getOrderById(Long.parseLong(orderId));
			orderHeader.setPayment(0);//现金支付
			orderHeader.setStatus(9);
			int orderid = orderService.updateOrder(orderHeader);
			if (orderid!=-1) {
				return pm;
			}
		}
		
		pm.setErrorMsg(MessageConstant.ALL_FAILED);
		pm.setSuccess(false);
		return pm;
	}
	
	/**
	 * @Title: refundByOrder
	 * @Description: 退款
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="/refundByOrder",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage refundByOrder(String orderId){
		PageMessage pm = new PageMessage();
		if (StringUtil.isNotEmpty(orderId)) {
			int flag = paymentService.refundByOrder(Long.parseLong(orderId));
			if(flag>0){
				OrderHeader oh = orderService.getOrderById(Long.parseLong(orderId));			
				Restaurants restaurants = new Restaurants();
				restaurants.setUuid(oh.getRestaurantUuid());
				List<RestaurantsUser> list = restaurantsUserService.getAllRestaurantsUser(restaurants);
				for (RestaurantsUser restaurantsUser : list) {
					if(StringUtil.isNotBlank(restaurantsUser.getToken())){//判断token是否为空
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
						PushUtil.pushPad("Cancel the order", oh.getPeopleName()+"("+oh.getPhoneNumber()+")"+" Cancelled the "+sdf.format(oh.getOrderDate())+" Order information", restaurantsUser.getToken());
					}
				}
				
				//取消订单成功，需要删除这笔订单的捐款
				flag = orderCharityService.deleteOrderCharity(Integer.parseInt(orderId));
				pm.setSuccess(true);
				return pm;
				
				
			}else{
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.ALL_FAILED);
				return pm;
			}
			
		}
		pm.setSuccess(false);
		pm.setErrorMsg(MessageConstant.ALL_FAILED);
		return pm;
	};
	
	/**
	 * @Title: deleteCardById
	 * @Description: 删除卡
	 * @param:    String cardId,String consumerId
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteCard",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteCardById(String cardId,String consumerUuid){
		PageMessage pm = new PageMessage();
		if (StringUtil.isNotEmpty(cardId)&&StringUtil.isNotEmpty(consumerUuid)) {
			int flag = paymentService.deleteCard(cardId, consumerUuid);
			if (flag==1) {//删除成功
				return pm;
			}
		}
		pm.setSuccess(false);
		pm.setErrorMsg(MessageConstant.DELETE_CARD_FAILURE);
		return pm;
	};
	
	/**
	 * @Title: getAllCharityFirstLetters
	 * @Description: gets the unique first letters for all available charities
	 * @return: List<String>
	 */
	@RequestMapping(value = "getAllCharityFirstLetters")
	@ResponseBody
	public List<String> getAllCharityFirstLetters() {
		List<Charity> charityList = charityService.getAllCharity();
		List<String> charityFirstLetters = new ArrayList<String>();
		for (Charity charity : charityList) {
			String currentFirstLetter = Character.toString(charity.getCharityName().charAt(0));
			if (charityFirstLetters.contains(currentFirstLetter) == false) {
				charityFirstLetters.add(currentFirstLetter);
			}
		}
		return charityFirstLetters;
	}
	
	/**
	 * @Title: getAllCharity
	 * @Description: 根据首字母获取慈善机构
	 * @param:    
	 * @return: List<Charity>
	 */
	@RequestMapping(value = "getAllCharity")
	@ResponseBody
	public List<Charity> getAllCharity(String letter) {
		//return charityService.getAllCharity();
		List<Charity> list = charityService.getAllCharity();
		List<Charity> charities = new ArrayList<Charity>(); 
		for (Charity charity : list) {
			if (letter.equalsIgnoreCase(String.valueOf(charity.getCharityName().charAt(0)))) {
				charities.add(charity);
			}
		}
		return charities;
	}
	
	/**
	 * @Title: saveOrderCharity
	 * @Description: 保存捐款信息
	 * @param:    
	 * @return: List<Charity>
	 */
	@RequestMapping(value = "saveOrderCharity")
	@ResponseBody
	public PageMessage saveOrderCharity(OrderCharity orderCharity) {
		PageMessage pm = new PageMessage();
		if (orderCharity.getOrderId()!=null) {
			OrderHeader orderHeader = orderService.getOrderById(orderCharity.getOrderId());
			if (orderHeader!=null) {
				orderCharity.setMoney(StringUtil.convertLastDouble(orderHeader.getTotal()*0.05));
				if (orderHeader.getConsumers()!=null) {
					orderCharity.setConsumerUuid(orderHeader.getConsumers().getUuid());
					orderCharityService.saveOrderCharity(orderCharity);
				}
			}
		}
		return pm;
	}
	

	@RequestMapping(value = "testChargeByCard", method = RequestMethod.POST)
	@ResponseBody
	public String testChargeByCard(CardEntity cardEntity, ChargeEntity chargeEntity) {
		return CommonUtil.chargeByCard(cardEntity, chargeEntity);
	}

	@RequestMapping(value = "testsaveCustomerAndCard", method = RequestMethod.POST)
	@ResponseBody
	public String testsaveCustomerAndCard(CardEntity cardEntity) {
		return CommonUtil.saveCustomerAndCard(cardEntity);
	}

	@RequestMapping(value = "testchargeByCustomer", method = RequestMethod.POST)
	@ResponseBody
	public String testchargeByCustomer(ChargeEntity chargeEntity, String customerId) {
		return CommonUtil.chargeByCustomer(chargeEntity, customerId);
	}

	@RequestMapping(value = "testcustomerAddCard", method = RequestMethod.POST)
	@ResponseBody
	public String testcustomerAddCard(CardEntity cardEntity, String customerId) {
		return CommonUtil.customerAddCard(cardEntity, customerId);
	}

	@RequestMapping(value = "testlistAllCards", method = RequestMethod.POST)
	@ResponseBody
	public List<CardEntity> testlistAllCards(String customerId) {
		return CommonUtil.listAllCards(customerId);
	}

	@RequestMapping(value = "testrefundAll", method = RequestMethod.POST)
	@ResponseBody
	public String testrefundAll(String chargeId) {
		return CommonUtil.refundAll(chargeId);
	}

	@RequestMapping(value = "testcreateManagedAccount", method = RequestMethod.POST)
	@ResponseBody
	public String testcreateManagedAccount() {
		return CommonUtil.createManagedAccount();
	}

	@RequestMapping(value = "testcreateBankAccount", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage testcreateBankAccount(String routingNumber, String bankAccountNumber, String accountId) {
		return CommonUtil.createBankAccount(routingNumber, bankAccountNumber, accountId);
	}

	@RequestMapping(value = "testchargeForPlatform", method = RequestMethod.POST)
	@ResponseBody
	public String testchargeForPlatform(CardEntity cardEntity, String accountNumber, ChargeEntity chargeEntity) {
		return CommonUtil.chargeForPlatform(cardEntity, accountNumber, chargeEntity);
	}

	@RequestMapping(value = "testupdateManagedAccount", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage testupdateManagedAccount(String accountNumber, String context) {
		return CommonUtil.updateManagedAccount(accountNumber, context);
	}
	

}
