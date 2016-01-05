package com.camut.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.druid.support.json.JSONUtils;
import com.camut.framework.constant.Currency;
import com.camut.framework.constant.MessageConstant;
import com.camut.utils.StripeUtil;
import com.camut.model.ApiResponse;
import com.camut.model.CardEntity;
import com.camut.model.ChargeEntity;
import com.camut.pageModel.PageMessage;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Account;
import com.stripe.model.BankAccount;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.ExternalAccount;
import com.stripe.model.ExternalAccountCollection;
import com.stripe.model.Refund;
import com.stripe.model.Token;
import com.stripe.net.RequestOptions;

public class CommonUtil {

	/**
	 * Measure the distance between the two point's longitude and latitude
	 * 根据两点经度、纬度测算之间的距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return Mileage 公里数
	 */
	public static double getDistance(double lat1, double lng1, double lng2, double lat2) {
		double radLat1 = lat1 * Math.PI / 180;
		double radLat2 = lat2 * Math.PI / 180;
		double a = radLat1 - radLat2;
		double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * 6378137.0 / 1000;// With WGS84 standard reference The ellipsoid
									// long radius of the earth(Unit:m)
									// 取WGS84标准参考椭球中的地球长半径(单位:m)
		DecimalFormat format = new DecimalFormat("###.00");
		s = Double.parseDouble(format.format(s));
		return s;
	}

	/**
	 * @Title: chargeByToken
	 * @Description:通过token收款
	 * @param: String
	 * @return: ApiResponse 返回 charge的id null失败
	 */
	public static ApiResponse chargeByToken(ChargeEntity chargeEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (chargeEntity != null) {
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", chargeEntity.getAmount());
			chargeParams.put("currency", Currency.CAD);
			chargeParams.put("source", chargeEntity.getToken());
			chargeParams.put("destination", chargeEntity.getAccountId());
			chargeParams.put("application_fee", chargeEntity.getApplicatonFee());
			try {
				Charge charge = Charge.create(chargeParams);
				if (charge.getStatus().equals("succeeded")) {// 扣款成功
					return new ApiResponse(1, charge.getId(), null);
				}
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				Log4jUtil.info(e.getMessage() + " - Request-id: " + e.getRequestId());
				return new ApiResponse(0, null, e.getMessage());
			}
		}
		return new ApiResponse(0, null, MessageConstant.PAY_FAIL);
	}

	/**
	 * @Title: saveCustomerAndCardByToken
	 * @Description:通过token创建用户
	 * @param: String
	 * @return: String 返回 customer的id null失败
	 */
	public static String saveCustomerAndCardByToken(String token) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (StringUtil.isNotEmpty(token)) {
			Map<String, Object> customerParams = new HashMap<String, Object>();
			customerParams.put("source", token);
			try {
				Customer customer = Customer.create(customerParams);
				return customer.getId();
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: chargeByCard
	 * @Description:通过卡信息收款
	 * @param: CardEntity
	 * @return: String 返回 charge的id null失败
	 */
	public static String chargeByCard(CardEntity cardEntity, ChargeEntity chargeEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (cardEntity != null && chargeEntity != null) {
			Map<String, Object> cardParams = new HashMap<String, Object>();
			cardParams.put("object", "card");
			cardParams.put("number", cardEntity.getNumber());
			cardParams.put("exp_month", cardEntity.getExp_month());
			cardParams.put("exp_year", cardEntity.getExp_year());
			cardParams.put("cvc", cardEntity.getCvc());
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", chargeEntity.getAmount());
			chargeParams.put("currency", Currency.CAD);
			chargeParams.put("source", cardParams);
			try {
				Charge charge = Charge.create(chargeParams);
				if (charge.getStatus().equals("succeeded")) {// 扣款成功
					return charge.getId();
				}
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: saveCustomerAndCard
	 * @Description:保存customer以及一张默认卡
	 * @param: CardEntity
	 * @return: String 返回 返回 customer的id null失败
	 */
	public static String saveCustomerAndCard(CardEntity cardEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (cardEntity != null) {
			Map<String, Object> cardParams = new HashMap<String, Object>();
			cardParams.put("number", cardEntity.getNumber());
			cardParams.put("exp_month", cardEntity.getExp_month());
			cardParams.put("exp_year", cardEntity.getExp_year());
			cardParams.put("cvc", cardEntity.getCvc());
			Map<String, Object> tokenParams = new HashMap<String, Object>();
			tokenParams.put("card", cardParams);
			Map<String, Object> customerParams = new HashMap<String, Object>();
			try {
				Token token = Token.create(tokenParams);// 创建token
				customerParams.put("source", token.getId());
				Customer customer = Customer.create(customerParams);
				return customer.getId();
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: chargeByCustomer
	 * @Description:使用customer的默认卡付款，通常与上面一个方法联合使用
	 * @param: CardEntity String
	 * @return: String 返回 返回 charge的id null失败
	 */
	public static String chargeByCustomer(ChargeEntity chargeEntity, String customerId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (chargeEntity != null && StringUtil.isNotEmpty(customerId)) {
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", chargeEntity.getAmount());
			chargeParams.put("currency", Currency.CAD);
			chargeParams.put("customer", customerId);
			try {
				Charge charge = Charge.create(chargeParams);
				if (charge.getStatus().equals("succeeded")) {// 扣款成功
					return charge.getId();
				}
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: createCustomer
	 * @Description: 注册customer
	 * @param: String eamil
	 * @return: String 返回customer的id null失败
	 */
	public static String createCustomer(String eamil) {
		Stripe.apiKey = StripeUtil.getApiKey();
		Map<String, Object> customerParams = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(eamil)) {
			customerParams.put("email", eamil);
		}
		try {
			Customer customer = Customer.create(customerParams);
			return customer.getId();
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			return null;
		}
	}

	/**
	 * @Title: customerAddCard
	 * @Description:在一个已经存在的customer上增加卡
	 * @param: CardEntity String
	 * @return: String 返回 返回 card的id null失败
	 */
	public static String customerAddCard(CardEntity cardEntity, String customerId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (cardEntity != null && StringUtil.isNotEmpty(customerId)) {

			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, Object> cardParams = new HashMap<String, Object>();
			cardParams.put("object", "card");
			cardParams.put("number", cardEntity.getNumber());
			cardParams.put("exp_month", cardEntity.getExp_month());
			cardParams.put("exp_year", cardEntity.getExp_year());
			cardParams.put("cvc", cardEntity.getCvc());
			params.put("source", cardParams);
			try {
				Customer cu = Customer.retrieve(customerId);
				Card card = cu.createCard(params);
				return card.getId();
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}

		}
		return null;
	}

	/**
	 * @Title: customerAddCard
	 * @Description:在一个已经存在的customer上增加卡
	 * @param: CardEntity String
	 * @return: ApiResponse 返回 返回 card的id null失败
	 */
	public static ApiResponse customerAddCardByToken(ChargeEntity chargeEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (chargeEntity != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("source", chargeEntity.getToken());
			try {
				Customer cu = Customer.retrieve(chargeEntity.getCustomerId());
				Card card = cu.createCard(params);
				return new ApiResponse(1, card.getId(), null);
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				Log4jUtil.info(e.getMessage() + " - Request-id: " + e.getRequestId());
				return new ApiResponse(0, null, e.getMessage());
			}
		}
		return new ApiResponse(0, null, null);
	}

	/**
	 * @Title: listAllCards
	 * @Description:返回一个customer的所有卡
	 * @param: CardEntity String
	 * @return: String 返回 返回 card的集合 null失败
	 */
	public static List<CardEntity> listAllCards(String customerId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		List<CardEntity> cardEntities = new ArrayList<CardEntity>();
		if (StringUtil.isNotEmpty(customerId)) {

			Map<String, Object> cardParams = new HashMap<String, Object>();
			cardParams.put("object", "card");
			try {
				Customer cu = Customer.retrieve(customerId);
				ExternalAccountCollection externalAccountCollection = cu.getSources().all(cardParams);
				List<ExternalAccount> accounts = externalAccountCollection.getData();
				for (ExternalAccount externalAccount : accounts) {
					if (externalAccount.getObject().equals("card")) {// 表示是卡对象
						CardEntity cardEntity = new CardEntity();
						cardEntity.setLast4(((Card) externalAccount).getLast4());
						cardEntity.setId(((Card) externalAccount).getId());
						cardEntity.setType(((Card) externalAccount).getBrand());
						cardEntities.add(cardEntity);
					}
				}
				return cardEntities;

			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}

		}
		return null;
	}

	/**
	 * @Title: chargeByCardId
	 * @Description:根据指定的卡号付款
	 * @param: String String ChargeEntity
	 * @return: ApiResponse 返回 charge的id null失败
	 */
	public static ApiResponse chargeByCardId(ChargeEntity chargeEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (chargeEntity != null) {
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", chargeEntity.getAmount());
			chargeParams.put("currency", Currency.CAD);
			chargeParams.put("customer", chargeEntity.getCustomerId());
			chargeParams.put("card", chargeEntity.getCardId());
			chargeParams.put("destination", chargeEntity.getAccountId());
			chargeParams.put("application_fee", chargeEntity.getApplicatonFee());
			try {
				Charge charge = Charge.create(chargeParams);
				if (charge.getStatus().equals("succeeded")) {// 扣款成功
					return new ApiResponse(1, charge.getId(), null);
				}
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				Log4jUtil.info(e.getMessage() + " - Request-id: " + e.getRequestId());
				return new ApiResponse(0, null, e.getMessage());
			}
		}
		return new ApiResponse(0, null, MessageConstant.PAY_FAIL);
	}

	/**
	 * @Title: refundAll
	 * @Description:根据chargeid全额退款
	 * @param: String chargeId(扣款对象的id)
	 * @return: String 返回 refund的id null失败
	 */
	public static String refundAll(String chargeId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (StringUtil.isNotEmpty(chargeId) && chargeId.contains("ch")) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("refund_application_fee", true);
			params.put("reverse_transfer", true);
			try {
				Charge ch = Charge.retrieve(chargeId);
				Refund re = ch.getRefunds().create(params);
				return re.getId();
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: refundPart
	 * @Description:创建商家的托管账户
	 * @param: String email(商家注册时的eamil)
	 * @return: String 返回 account的id null失败
	 */
	public static String createManagedAccount() {
		Stripe.apiKey = StripeUtil.getApiKey();
		Map<String, Object> accountParams = new HashMap<String, Object>();
		accountParams.put("managed", true);
		try {
			Account account = Account.create(accountParams);
			return account.getId();
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @Title: getAccountById
	 * @Description:根据id查找托管账户
	 * @param: String accountId
	 * @return: Account 返回 Account null失败
	 */
	public static Account getAccountById(String accountId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		try {
			RequestOptions options = RequestOptions.builder().setIdempotencyKey(CreateOrderNumber.getRadomString()).build();
			Account acct = Account.retrieve(accountId, options);
			return acct;
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			return null;
		}
	}

	/**
	 * @Title: refundPart
	 * @Description:商家托管账户绑定银行账户
	 * @param: String routingNumber(银行代码和转账代码) String bankAccountNumber(银行账户号码)
	 *         String accountId(托管账户的id)
	 * @return: PageMessage
	 */
	public static PageMessage createBankAccount(String routingNumber, String bankAccountNumber, String accountId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		PageMessage pm = new PageMessage();
		if (StringUtil.isNotEmpty(routingNumber) && StringUtil.isNotEmpty(bankAccountNumber) && StringUtil.isNotEmpty(accountId)) {
			Map<String, Object> accountParams = new HashMap<String, Object>();
			Map<String, Object> params = new HashMap<String, Object>();
			accountParams.put("object", "bank_account");
			accountParams.put("country", "ca");
			accountParams.put("currency", "cad");
			accountParams.put("routing_number", routingNumber);
			accountParams.put("account_number", bankAccountNumber);
			params.put("external_account", accountParams);

			try {
				RequestOptions options = RequestOptions.builder().setIdempotencyKey(CreateOrderNumber.getRadomString()).build();
				Account acct = Account.retrieve(accountId, options);
				BankAccount bankAccount = (BankAccount) acct.getExternalAccounts().create(params);
				pm.setErrorMsg(bankAccount.getId());
				return pm;
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				System.out.println(e.getMessage());
				pm.setErrorMsg(e.getMessage());
				pm.setSuccess(false);
				return pm;
			}
		}
		pm.setSuccess(false);
		pm.setErrorMsg(MessageConstant.STRIPE_ACCOUNT_REGISTER_FAIL);
		return pm;

	}

	/**
	 * @Title: refundPart
	 * @Description:创建card的token
	 * @param: String email(商家注册时的eamil)
	 * @return: String 返回 token的id null失败
	 */
	public static String getTokenByCard(CardEntity cardEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (cardEntity != null) {
			Map<String, Object> cardParams = new HashMap<String, Object>();
			cardParams.put("number", cardEntity.getNumber());
			cardParams.put("exp_month", cardEntity.getExp_month());
			cardParams.put("exp_year", cardEntity.getExp_year());
			cardParams.put("cvc", cardEntity.getCvc());
			Map<String, Object> tokenParams = new HashMap<String, Object>();
			tokenParams.put("card", cardParams);
			try {
				Token token = Token.create(tokenParams);
				System.out.println(token);
				return token.getId();
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * @Title: chargeForPlatform
	 * @Description:主账户与托管账户一起收款
	 * @param: String email(商家注册时的eamil)
	 * @return: String 返回 charge的id null失败
	 */
	public static String chargeForPlatform(CardEntity cardEntity, String accountNumber, ChargeEntity chargeEntity) {
		Stripe.apiKey = StripeUtil.getApiKey();
		if (cardEntity != null && StringUtil.isNotEmpty(accountNumber)) {
			String token = getTokenByCard(cardEntity);
			Map<String, Object> chargeParams = new HashMap<String, Object>();
			chargeParams.put("amount", chargeEntity.getAmount());
			chargeParams.put("currency", Currency.CAD);
			chargeParams.put("source", token);
			chargeParams.put("destination", accountNumber);
			chargeParams.put("application_fee", Math.round(chargeEntity.getAmount() * 0.1));
			try {
				Charge charge = Charge.create(chargeParams);
				return charge.getId();
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Title: updateManagedAccount
	 * @Description:更新托管账户
	 * @param: String accountNumber(托管账户id)String context（更新内容，json格式）
	 * @return: PageMessage
	 */
	@SuppressWarnings("unchecked")
	public static PageMessage updateManagedAccount(String accountNumber, String context) {
		Stripe.apiKey = StripeUtil.getApiKey();
		PageMessage pm = new PageMessage();
		if (StringUtil.isNotEmpty(accountNumber) && StringUtil.isNotEmpty(context) && !"{}".equals(context)) {
			RequestOptions options = RequestOptions.builder().setIdempotencyKey(CreateOrderNumber.getRadomString()).build();
			Map<String, Object> Params = new HashMap<String, Object>();
			Map<String, Object> accountParams = new HashMap<String, Object>();
			Map<String, Object> contextMap = (Map<String, Object>) JSONUtils.parse(context);
			if (contextMap.get("first_name") != null) {
				accountParams.put("first_name", contextMap.get("first_name").toString());
			}
			if (contextMap.get("last_name") != null) {
				accountParams.put("last_name", contextMap.get("last_name").toString());
			}
			if (contextMap.get("business_name") != null) {
				accountParams.put("business_name", contextMap.get("business_name").toString());
			}
			if (contextMap.get("business_tax_id") != null) {
				accountParams.put("business_tax_id", contextMap.get("business_tax_id").toString());
			}
			Map<String, Object> dobParams = new HashMap<String, Object>();
			if (contextMap.get("day") != null) {
				dobParams.put("day", Integer.parseInt(contextMap.get("day").toString()));
			}
			if (contextMap.get("month") != null) {
				dobParams.put("month", Integer.parseInt(contextMap.get("month").toString()));
			}
			if (contextMap.get("year") != null) {
				dobParams.put("year", Integer.parseInt(contextMap.get("year").toString()));
			}
			accountParams.put("dob", dobParams);
			if (contextMap.get("type") != null) {
				accountParams.put("type", contextMap.get("type").toString());
			}
			if (contextMap.get("personal_id_number") != null) {
				accountParams.put("personal_id_number", contextMap.get("personal_id_number").toString());
			}
			Map<String, Object> addressParams = new HashMap<String, Object>();
			if (contextMap.get("line1") != null) {
				addressParams.put("line1", contextMap.get("line1").toString());
			}
			if (contextMap.get("city") != null) {
				addressParams.put("city", contextMap.get("city").toString());
			}
			if (contextMap.get("state") != null) {
				addressParams.put("state", contextMap.get("state").toString());
			}
			if (contextMap.get("postal_code") != null) {
				addressParams.put("postal_code", contextMap.get("postal_code").toString());
			}
			accountParams.put("address", addressParams);
			Map<String, Object> tos_acceptanceParams = new HashMap<String, Object>();
			if (contextMap.get("date") != null) {
				tos_acceptanceParams.put("date", Long.parseLong(contextMap.get("date").toString()));
			}
			if (contextMap.get("ip") != null) {
				tos_acceptanceParams.put("ip", contextMap.get("ip").toString());
			}
			Map<String, Object> external_accountParams = new HashMap<String, Object>();

			if (contextMap.get("routing_number") != null && contextMap.get("account_number") != null) {
				external_accountParams.put("object", "bank_account");
				external_accountParams.put("country", "CA");
				external_accountParams.put("currency", Currency.CAD);
				external_accountParams.put("routing_number", contextMap.get("routing_number").toString());
				external_accountParams.put("account_number", contextMap.get("account_number").toString());
			}

			Params.put("tos_acceptance", tos_acceptanceParams);
			Params.put("legal_entity", accountParams);
			Params.put("external_account", external_accountParams);
			try {
				Account acct = Account.retrieve(accountNumber, options);
				Account account = acct.update(Params);
				pm.setErrorMsg(account.getId());
				return pm;
			} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
				pm.setSuccess(false);
				pm.setErrorMsg(e.getMessage());
				return pm;
			}
		}
		pm.setSuccess(false);
		pm.setErrorMsg(MessageConstant.STRIPE_ACCOUNT_REGISTER_FAIL);
		return pm;
	}

	/**
	 * @Title: deleteCard
	 * @Description: 删除用户已经保存的卡
	 * @param: String customerId,String cardId
	 * @return: int -1失败 1成功
	 */
	public static int deleteCard(String customerId, String cardId) {
		Stripe.apiKey = StripeUtil.getApiKey();
		try {
			Customer customer = Customer.retrieve(customerId);
			for (ExternalAccount source : customer.getSources().getData()) {
				if (source.getId().equals(cardId)) {
					source.delete();
					return 1;
				}
			}
		} catch (AuthenticationException | InvalidRequestException | APIConnectionException | CardException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return -1;
	}

	/**
	 * @Title: luhnTest
	 * @Description: 验证是否是一个有效的信用卡卡号
	 * @param:
	 * @return: boolean
	 */
	public static boolean luhnTest(String number) {
		int s1 = 0, s2 = 0;
		String reverse = new StringBuffer(number).reverse().toString();
		for (int i = 0; i < reverse.length(); i++) {
			int digit = Character.digit(reverse.charAt(i), 10);
			if (i % 2 == 0) {
				s1 += digit;
			} else {
				s2 += 2 * digit;
				if (digit >= 5) {
					s2 -= 9;
				}
			}
		}
		return (s1 + s2) % 10 == 0;
	}

	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Title: isPhone
	 * @Description: Checks to see if given string is a valid phone number.
	 * @param str
	 * @return: boolean
	 */
	public static boolean isPhone(String str) {
		String phoneExpReg = "^([0-9]{3})?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$";
		if(StringUtil.isEmpty(str)) {
			return false;
		} else if(str.matches(phoneExpReg)) {
			return true;
		} else {
			return false;
		}
	}
}
