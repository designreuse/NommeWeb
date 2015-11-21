package com.camut.controller.api;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.support.json.JSONUtils;
import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.LoginTypeConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.CardEntity;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.model.ChargeEntity;
import com.camut.model.Consumers;
import com.camut.model.ConsumersAddress;
import com.camut.model.Discount;
import com.camut.model.GarnishItem;
import com.camut.model.OrderDishGarnish;
import com.camut.model.OrderHeader;
import com.camut.model.OrderItem;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.model.api.CartHeaderApiModel;
import com.camut.model.api.ConsumerBankaccountApiModel;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.ConsumersAddressDefaultApiModel;
import com.camut.model.api.ConsumersApiModel;
import com.camut.model.api.EvaluateApiModel;
import com.camut.model.api.ResultApiModel;
import com.camut.model.api.ViewConsumerClassifitionApiModel;
import com.camut.service.CartDishGarnishService;
import com.camut.service.CartHeaderService;
import com.camut.service.CartItemService;
import com.camut.service.CartService;
import com.camut.service.ClassificationService;
import com.camut.service.ConsumersAddressService;
import com.camut.service.ConsumersBankaccountService;
import com.camut.service.ConsumersFavoritesService;
import com.camut.service.ConsumersService;
import com.camut.service.DiscountService;
import com.camut.service.DishGarnishService;
import com.camut.service.DishService;
import com.camut.service.DistancePriceService;
import com.camut.service.EvaluateService;
import com.camut.service.GarnishItemService;
import com.camut.service.OpenTimeService;
import com.camut.service.OrderCharityService;
import com.camut.service.OrderItemService;
import com.camut.service.OrderService;
import com.camut.service.PaymentService;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.CommonUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MD5Util;
import com.camut.utils.MailUtil;
import com.camut.utils.PushUtil;
import com.camut.utils.StringUtil;

/**
 * @entity ConsumersApiController .
 * @author zyf
 * @createTime 2015-06-01
 * @author
 * @updateTime
 * @memo
 */
@Controller("ConsumersApiController")
@RequestMapping("/api/consumer")
public class ConsumersApiController extends BaseAPiModel {

	@Autowired private ConsumersService consumersService;
	@Autowired private ConsumersAddressService consumersAddressService;
	@Autowired private ConsumersBankaccountService consumersBankaccountService;
	@Autowired private ConsumersFavoritesService consumersFavoritesService;
	@Autowired private EvaluateService evaluateService;
	@Autowired private OrderService orderService;
	@Autowired private OrderItemService orderItemService;
	@Autowired private ClassificationService classificationService;
	@Autowired private CartService cartService;
	@Autowired private CartHeaderService cartHeaderService;
	@Autowired private CartDishGarnishService cartDishGarnishService;
	@Autowired private CartItemService cartItemService;
	@Autowired private PaymentService paymentService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private DiscountService discountService;
	@Autowired private DistancePriceService distancePriceService;
	@Autowired private OrderCharityService orderCharityService;
	@Autowired private OpenTimeService openTimeService;
	@Autowired private DishService dishService;
	@Autowired private GarnishItemService garnishItemService;
	@Autowired private DishGarnishService dishGarnishService;
	@Autowired private RestaurantsUserService restaurantsUserService;
	
	private static Map<String, String> map=new HashMap<String, String>();
	
	private  String IOS_TYPE="1";
	
	/**
	 * @Title: doLogin
	 * @Description: 登陆，验证用户名密码，用户类型; login, verify login name, password and login type
	 * @param: Consumers对象; 
	 * @return: consumersApiModel类; consumer for mobile app
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel doLogin(HttpSession session, Consumers consumers) {
		Log4jUtil.info("用户登录接口==>"+consumers.toString());
		ResultApiModel ram = new ResultApiModel();
		ConsumersApiModel consumersApiModel = new ConsumersApiModel();
		if(IOS_TYPE.equals(consumers.getMobileType())&&StringUtil.isNotEmpty(consumers.getMobileToken())){
			consumers.setMobileToken(StringUtil.replace(consumers.getMobileToken(), "<", ""));
			consumers.setMobileToken(StringUtil.replace(consumers.getMobileToken(), ">", ""));
			consumers.setMobileToken(StringUtil.replace(consumers.getMobileToken(), " ", ""));
		}
		if (LoginTypeConstant.NOMME == consumers.getLoginType()) {// 平台登录			
			int falg = consumersService.consumersLogin(consumers);
			if (falg == -1) { // 用户名不存在
				ram.setFlag(GlobalConstant.LOGINNAME_ERROR);
				ram.setResultMessage(MessageConstant.LOGINNAME_ERROR);
			} else if (falg == 0) {// 密码错误
				ram.setFlag(GlobalConstant.PASSWORD_ERROR);
				ram.setResultMessage(MessageConstant.PASSWORD_ERROR);
			} else if (falg == 1) {// 登陆成功
				Consumers coms = consumersService.getConsumersByLoginName(consumers.getEmail());
				coms.setMobileToken(consumers.getMobileToken());
				coms.setMobileType(consumers.getMobileType());
				consumersService.updateTokenAndType(coms);
				consumersApiModel.setConsumerUuid(coms.getUuid());
				consumersApiModel.setEmail(coms.getEmail());
				consumersApiModel.setPhone(coms.getPhone()==null?"":coms.getPhone());
				consumersApiModel.setFirstName(coms.getFirstName()==null?"":coms.getFirstName());
				consumersApiModel.setLastName(coms.getLastName()==null?"":coms.getLastName());
				String firstName ="";
				if(coms.getFirstName() != null){
					firstName = coms.getFirstName();
				}
				String lastName ="";
				if(coms.getLastName() != null){
					lastName = coms.getLastName();
				}
				consumersApiModel.setShowName(firstName +" "+lastName);
				ram.setFlag(GlobalConstant.LOGIN_OK);
				ram.setBody(consumersApiModel);
				ram.setResultMessage("");
			}
		} else if (LoginTypeConstant.FACEBOOK == consumers.getLoginType() // 第三方登录; third party social login
				|| LoginTypeConstant.WEIXIN == consumers.getLoginType()
				|| LoginTypeConstant.TWITTER == consumers.getLoginType()) {
			//登录已经成功了
			if (StringUtil.isNotEmpty(consumers.getOtherCode())) {
				Consumers consumers2 = consumersService.getConsumersByOtherCode(consumers.getOtherCode(), consumers.getLoginType());
				if (consumers2 != null) {// 存在第三方登陆的信息
					consumers2.setMobileToken(consumers.getMobileToken());
					consumers2.setMobileType(consumers.getMobileType());
					consumers2.setLastLoginDate(new Date());
					// 修改失败
					if (consumersService.updateConsumersForNomme(consumers2) != 1) {
						ram.setFlag(-1);
						ram.setResultMessage(MessageConstant.ALL_FAILED);
						return ram;
					}
					consumersApiModel.setConsumerUuid(consumers2.getUuid());
					consumersApiModel.setEmail(consumers2.getEmail()==null?"":consumers2.getEmail());
					consumersApiModel.setFirstName(consumers2.getFirstName()==null?"":consumers2.getFirstName());
					consumersApiModel.setLastName(consumers2.getLastName()==null?"":consumers2.getLastName());
					consumersApiModel.setPhone(consumers2.getPhone()==null?"":consumers2.getPhone());
					consumersApiModel.setShowName(consumers2.getNickname()==null?"":consumers2.getNickname());
					ram.setFlag(GlobalConstant.LOGIN_OK);
					ram.setBody(consumersApiModel);
				} else {// 不存在第三方登陆的信息
					//推送
					String temporarypassword = (int)(Math.random()*900000+100000)+"";//生成6位验证码
					//try {
						PushUtil.push(session,"Nomme", "This is a temporary password "+temporarypassword+" and you'd better to change it to access your account in the future.", consumers.getMobileToken(), Integer.valueOf(consumers.getMobileType()));
						//SNSMobilePush.push(consumers.getMobileToken(),"This is a temporary password "+temporarypassword+" and you'd better to change it to access your account in the future." ,Integer.valueOf(consumers.getMobileType()));//1:android 2:ios 
					//} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					//}
					consumers.setPassword(MD5Util.md5(temporarypassword));
					String uuid = consumersService.saveTokenAndType(consumers);
					//增加失败
					if (!StringUtil.isNotEmpty(uuid)) {
						ram.setFlag(-1);
						ram.setResultMessage(MessageConstant.ADD_FAILED);
						return ram;
					}
					Consumers consumers3 = consumersService.getConsumersByUuid(uuid);
					//consumersApiModel.setConsumerId(id);
					consumersApiModel.setConsumerUuid(consumers3.getUuid());
					if (StringUtil.isNotEmpty(consumers.getNickname())) {
						consumersApiModel.setShowName(consumers.getNickname());
					}
					else{
						consumersApiModel.setShowName("");
					}
					ram.setFlag(GlobalConstant.LOGIN_OK);
					ram.setBody(consumersApiModel);
				}
			} else {
				ram.setFlag(-1);
				ram.setResultMessage("have no otherCode!");
				return ram;
			}
		}
		return ram;
	}
	
	/**
	 * @Title: addConsumers
	 * @Description: 会员注册 customer registration
	 * @param: consumers 对象
	 * @return: ram -1表示注册失败 ，0表示会员email已存在，1表示注册成功   
	 * 			ram -1: failed, 0:exist, 1:success
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addConsumers(Consumers consumers) {
		Log4jUtil.info("会员注册接口==>"+consumers.toString());
		ResultApiModel ram = new ResultApiModel();
		int flag = consumersService.addConsumers(consumers);
		if (flag == GlobalConstant.FAILED) {// 表示注册失败; failed
			ram.setFlag(-1);
			ram.setResultMessage(MessageConstant.FAILED);
		} else if (flag == GlobalConstant.EXIST) {// 表示会员email已存在; login email exists
			ram.setFlag(0);
			ram.setResultMessage(MessageConstant.REGISTER_FAILED);
		} else if (flag == GlobalConstant.SUCCESS) {// 表示注册成功; success
			ram.setFlag(1);
			ram.setResultMessage("");
			if(StringUtil.isNotEmpty(consumers.getEmail())){
				MailUtil.sendMail("Create Nomme Account Success", "Thank you for creating Nomme user, your account name is "+consumers.getEmail(), consumers.getEmail()); 
			}
		}
		return ram;
	}
	
	/**
	 * @Title: updateConsumers
	 * @Description: 用户信息修改; update consumer infor
	 * @param:  consumers
	 * @return: ram -1修改失败 1修改成功; 
	 *          set flag -1: failed, set flag 1: success
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel updateConsumers(ConsumersApiModel consumersApiModel){	
		Log4jUtil.info("用户信息修改接口==>"+consumersApiModel.toString());
		ResultApiModel ram = new ResultApiModel();
		if(StringUtil.isNotEmpty(consumersApiModel.getPhone())&&CommonUtil.isNumeric(consumersApiModel.getPhone())){
			int flag = consumersService.updateConsumers(consumersApiModel);
			if(flag == -1){//修改失败
				ram.setResultMessage(MessageConstant.PASSWORD_ERROR);//update failed
				ram.setFlag(-1);
			} else if (flag == 1){//修改成功; success
				ram.setResultMessage("");
				ram.setFlag(1);
			}
		}else{
			ram.setResultMessage("The phone must be a number.");//update failed
			ram.setFlag(-1);
		}		
		return ram;
	}
	
	/**
	 * @Title: forgetPassword
	 * @Description: 忘记密码; check if the email exists
	 * @param:  email
	 * @return: ResultApiModel 1：存在邮箱地址 -1：不存在邮箱地址
	 *                set flag 1: exist, -1: not exist
	 */
	@RequestMapping(value = "/forget", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel forgetPassword(HttpSession session,String email) {
		Log4jUtil.info("忘记密码接口==>"+"email="+email);
		ResultApiModel ram = new ResultApiModel();
		int falg = consumersService.getConsumersByEmail(email);
		if (falg == -1) {
			ram.setResultMessage(MessageConstant.EMAIL_EXIST);
			ram.setFlag(-1);
		} else if (falg == 1) {
			String verificationCode = (int)(Math.random()*900000+100000)+"";//生成6位验证码
			session.setAttribute(email, verificationCode);
			map.put(email, verificationCode);
			String title = "Verification code by Nomme";
			String content = "To reset your Nomme account "+email+" password , please enter the verification code— <span style='color:#064977'>" 
					+ verificationCode + "</span> to retrieve password input box, and then click the button to reset the password."
					+ "<br> Note: please use the verification code within 30 minutes.";
			MailUtil.sendMail(title, content, email);
			Log4jUtil.info("管理员", "测试发送邮件");
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: forgetPassword
	 * @Description: 忘记密码验证; send verify code to the current consumer
	 * @param:  email
	 * @return: ram 1：验证码发送成功 -1：邮箱不存在
	 *          set flag 1: success, -1: no such email
	 */
	@RequestMapping(value = "/forget/verify", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel sendEnail(HttpSession session, String email,String validateCode){
		Log4jUtil.info("忘记密码验证接口==>"+"email="+email+"validateCode="+validateCode);
		ResultApiModel ram = new ResultApiModel();
		//String code= (String) session.getAttribute(email);
		String code=map.get(email);
		if(validateCode.equals(code)){
			map.remove(email);	
	 		ram.setFlag(1);
		}else{
			ram.setResultMessage(MessageConstant.VALIDATECODE_MESSAGE);
			ram.setFlag(-1);
		}		
		return ram;
	}
	
	/**
	 * @Title: updateConsumersPassword
	 * @Description: 根据email修改密码; reset password by using email
	 * @param:   consumers
	 * @return: ResultApiModel ram 1：修改成功 -1：修改失败
	 *                    set flag 1: success, -1: failed
	 */
	@RequestMapping(value = "/forget/new", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel forgetNewPassword(ConsumersApiModel consumersApiModel){
		Log4jUtil.info("根据Email修改密码接口==>"+consumersApiModel.toString());
		ResultApiModel ram = new ResultApiModel();
		int flag = consumersService.updateConsumersPassword(consumersApiModel);
		if(flag == -1){//修改失败
			ram.setResultMessage(MessageConstant.PASSWORD_ERROR);
			ram.setFlag(-1);
		} else if (flag == 1){//修改成功
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: getConsumersAddressById
	 * @Description: 通id查找用户 地址; get consumer's addresses by consumer's id
	 * @param:    id 
	 * @return: ram
	 */
	@RequestMapping(value = "/address/list", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getConsumersAddressById(String consumerUuid,String restaurantUuid) {
		Log4jUtil.info("用户地址列表接口==>"+"consumerUuid="+consumerUuid+"restaurantUuid="+restaurantUuid);
		ResultApiModel ram = new ResultApiModel();
		if (StringUtil.isNotEmpty(consumerUuid)&&StringUtil.isNotEmpty(restaurantUuid)) {
			List<ConsumersAddressApiModel> list = consumersAddressService.getConsumersAddressById(consumerUuid, restaurantUuid);
			if (list!=null) {
				ram.setBody(list);
				ram.setFlag(1);
			}
			else{
				ram.setBody(new ArrayList<>());
				ram.setFlag(-1);
			}
		}
		return ram;
	}
	
	/**
	 * @Title: addConsumersAddress
	 * @Description: 添加用户 地址; add new address
	 * @param:    consumersAddress
	 * @return:  ram -1表示添加失败 ，1表示添加成功
	 *   set flag 1: success, -1: failed
	 */
	@RequestMapping(value = "/address/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addConsumersAddress(ConsumersAddressApiModel consumersAddressApiModel){
		Log4jUtil.info(" 添加用户 地址接口接口==>"+consumersAddressApiModel.toString());
		ResultApiModel ram = new ResultApiModel();
		int flag = consumersAddressService.addConsumersAddress(consumersAddressApiModel);
		if(flag == -1){//添加失败; failed
			ram.setFlag(-1);
			ram.setResultMessage(MessageConstant.ALL_FAILED);
		} else if (flag == 1){//添加成功; success
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: updateConsumersAddress
	 * @Description: 用户地址修改; modify/update address
	 * @param:    consumersAddress
	 * @return: ram -1修改失败 1修改成功
	 *     set flag 1: success, -1: failed
	 */
	@RequestMapping(value = "/address/update", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel updateConsumersAddress(ConsumersAddressApiModel consumersAddressApiModel){
		Log4jUtil.info(" 用户 地址修改接口==>"+consumersAddressApiModel.toString());
		ResultApiModel ram = new ResultApiModel();
		int flag = consumersAddressService.updateConsumersAddress(consumersAddressApiModel);
		if(flag == -1){//修改失败; failed
			ram.setResultMessage(MessageConstant.ALL_FAILED);
			ram.setFlag(-1);
		} else if (flag == 1){//修改成功; success
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: deleteConsumersAddress
	 * @Description: 用户地址删除; delete consumer's address
	 * @param: id
	 * @return: ram -1删除失败 ，1删除成功
	 *     set flag 1: success, -1: failed
	 */
/*	@RequestMapping(value = "/address/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel deleteConsumersAddress(String consumerId){
		ResultApiModel ram = new ResultApiModel();
		if(consumerId != null && consumerId.length() > 0){
			int flag = consumersAddressService.deleteConsumersAddress(Long.parseLong(consumerId));
			if(flag == -1){//修改失败 failed
				ram.setResultMessage(MessageConstant.ALL_FAILED);
				ram.setFlag(-1);
			} else if (flag == 1){//修改成功 success
				ram.setResultMessage("");
				ram.setFlag(1);
			}
		}
		return ram;
	}
	*/
	/**
	 * @Title: getConsumersBankaccountById
	 * @Description: 通用户id查找用户 银行卡
	 * @param:    id
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/paycard/list", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getConsumersBankaccountById(String consumerUuid) {
		Log4jUtil.info(" 用户银行卡列表接口==>"+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(consumersBankaccountService.getConsumersBankaccountByUuid(consumerUuid));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: addBankaccount
	 * @Description: 添加用户银行卡   added new card to customer's card list
	 * @param:  consumersBankaccount
	 * @return: ram -1添加失败 ，1添加成功
	 *     set flag 1: success, -1: failed
	 */
	@RequestMapping(value = "/paycard/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addBankaccount(ConsumerBankaccountApiModel consumersBankaccount){
		Log4jUtil.info("添加银行卡接口==>"+consumersBankaccount.toString());
		ResultApiModel ram = new ResultApiModel();
		CardEntity card=new  CardEntity();
		card.setCvc(consumersBankaccount.getCvv());
		String month=consumersBankaccount.getExpiratioin().substring(0, consumersBankaccount.getExpiratioin().indexOf("/"));
		String year =consumersBankaccount.getExpiratioin().substring(consumersBankaccount.getExpiratioin().indexOf("/")+1,consumersBankaccount.getExpiratioin().length());
		card.setExp_month(Integer.valueOf(month));
		card.setExp_year(Integer.valueOf(year));
		card.setNumber(consumersBankaccount.getCard());
		String stripeConsumerId= CommonUtil.saveCustomerAndCard(card);
		if(StringUtil.isEmpty(stripeConsumerId)){
			ram.setFlag(-1);
			ram.setResultMessage(MessageConstant.CARD_ERR_MESSAGE);
		}else{
			int flag = consumersBankaccountService.addBankaccount(consumersBankaccount,stripeConsumerId);
			if(flag == -1){//添加失败；failed
				ram.setResultMessage(MessageConstant.ALL_FAILED);
				ram.setFlag(-1);
			} else if (flag == 1){//添加成功；success
				ram.setResultMessage("");
				ram.setFlag(1);
			}
		}		
		return ram;
	}
	
	/**
	 * @Title: deleteBankaccount
	 * @Description:  通过银行卡卡号删除银行卡
	 * @param:  card
	 * @return: ram -1删除失败 ，1删除成功
	 */
	@RequestMapping(value = "/paycard/deleteall", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel deleteBankaccount(String card){
		Log4jUtil.info("删除银行卡接口==>"+"card="+card);
		ResultApiModel ram = new ResultApiModel();
		int flag = consumersBankaccountService.deleteBankaccount(card);
		if(flag == -1){//删除失败
			ram.setResultMessage(MessageConstant.ALL_FAILED);
			ram.setFlag(-1);
		} else if (flag == 1){//删除成功
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: selectFavorites
	 * @Description: 用户收藏列表
	 * @param:  id
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/collection/list", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectFavorites(String consumerUuid) {
		Log4jUtil.info("用户收藏接口==>"+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		if(StringUtil.isNotEmpty(consumerUuid)){
			try {
				ram.setFlag(1);
				ram.setBody(consumersFavoritesService.selectFavorites(consumerUuid));
				ram.setResultMessage("");
			} catch (Exception e) {
				ram.setFlag(-1);
				ram.setResultMessage(e.toString());
			}
		}
		return ram;
	}
	
	/**
	 * @Title: addFavorites
	 * @Description: 用户添加收藏
	 * @param:  rid  cid
	 * @return: ram  -1表示添加失败,1表示添加成功
	 */
	@RequestMapping(value = "/collection/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addFavorites(String consumerUuid, String restaurantUuid){
		Log4jUtil.info("用户添加收藏接口==>"+"consumerUuid="+consumerUuid+"restaurantUuid="+restaurantUuid);
		ResultApiModel ram = new ResultApiModel();
		if(StringUtil.isNotEmpty(consumerUuid) && StringUtil.isNotEmpty(restaurantUuid)){
			int flag = consumersFavoritesService.addFavorites(consumerUuid, restaurantUuid);
			if(flag == -1){//添加失败
				ram.setResultMessage(MessageConstant.ALL_FAILED);
				ram.setFlag(-1);
			} else if (flag == 1){//添加成功
				ram.setResultMessage("");
				ram.setFlag(1);
			}
		}
		return ram;
	}
	
	/**
	 * @Title: deleteFavorites
	 * @Description: 用户删除收藏
	 * @param:   id
	 * @return: ram  -1表示删除失败，1表示删除成功
	 */
	@RequestMapping(value = "/collection/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel deleteFavorites(String favoriteId){
		Log4jUtil.info("用户删除收藏接口==>"+"favoriteId="+favoriteId);
		ResultApiModel ram = new ResultApiModel();
		if(favoriteId != null && favoriteId.length() > 0){
			int flag = consumersFavoritesService.deleteFavorites(Long.parseLong(favoriteId));
			if(flag == -1){//添加失败
				ram.setResultMessage(MessageConstant.ALL_FAILED);
				ram.setFlag(-1);
			} else if (flag == 1){//添加成功
				ram.setResultMessage("");
				ram.setFlag(1);
			}
		}
		return ram;
	}
	
	/**
	 * @Title: addEvaluate
	 * @Description: 发布评论
	 * @param:  evaluateApiModel
	 * @return: ram  -1表示评论失败 ，1表示评论成功
	 */
	@RequestMapping(value = "/review/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addEvaluat(EvaluateApiModel evaluateApiModel){
		Log4jUtil.info("用户发布评论接口==>"+"OrderHeaderId"+evaluateApiModel.getOrderHeaderId()+"评分=>"+evaluateApiModel.getScore());
		ResultApiModel ram = new ResultApiModel();
		int flag = evaluateService.addEvaluate(evaluateApiModel);
		if(flag == -1){//添加失败
			ram.setResultMessage(MessageConstant.ALL_FAILED);
			ram.setFlag(-1);
		} else if (flag == 1){//添加成功
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: selectPastOrder
	 * @Description: 已完成订单列表
	 * @param:  consumerId   
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/order/history/list", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectPastOrder(String consumerUuid) {
		Log4jUtil.info("已完成订单接口==>"+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderService.selectPastOrder(consumerUuid));
			ram.setTotal(orderService.getCharityAmount(consumerUuid));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: selectCurrentOrder
	 * @Description:  未完成订单列表
	 * @param:  consumerId  
	 * @return: List<OrderHeader>
	 */
	@RequestMapping(value = "/order/current/list", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectCurrentOrder(String consumerUuid) {
		Log4jUtil.info("未完成订单接口==>"+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderService.selectCurrentOrder(consumerUuid));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: selectHistoryOrder
	 * @Description: 订单详情
	 * @param:  id
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/order/history/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectHistoryOrder(long orderId){
		Log4jUtil.info("订单详情接口==>"+"orderId="+orderId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderItemService.selectHistoryOrder(orderId));
			ram.setResultMessage("");
		} catch (Exception e) {
			e.printStackTrace();
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: addOrder
	 * @Description: 用户订单新增
	 * @param:  orderHeader   consumerId
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addOrder(@RequestParam String context){
		Log4jUtil.info("用户订单新增接口==>"+context+"当前时间="+new Date().getTime());
		DecimalFormat format = new DecimalFormat("###.00");
		ResultApiModel ram = new ResultApiModel();
		Map<String, Object> map= (Map<String, Object>)JSONUtils.parse(context);
		OrderHeader orderHeader=new OrderHeader();
		if (map.get("cartHeaderId")!=null) {//不是reservation
				CartHeader cartHeader = cartHeaderService.getCartHeaderById(Long.parseLong(map.get("cartHeaderId").toString()));
				if (cartHeader!=null) {
					orderHeader = orderService.CartHeaderToOrderHeader(cartHeader.getId());
					
					if (map.get("logistics")!=null) {
						orderHeader.setLogistics(Double.parseDouble(format.format(Double.parseDouble(map.get("logistics").toString()))));
					}
					
					if (orderHeader==null) {
						ram.setFlag(-1);
						ram.setResultMessage("the cart is not exist!");
						return ram;
					}

					if (map.get("memo")!=null) {
						orderHeader.setMemo((map.get("memo").toString()));
					}
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("the cart is not exist!");
					return ram;
				}
				
		}
		else{//购物车id为空
			//订单类型
			if(map.get("orderType")!=null){
				orderHeader.setOrderType(Integer.valueOf(map.get("orderType").toString()));
				if (Integer.valueOf(map.get("orderType").toString())==3) {//预定
					if (map.get("number")!=null) {
						orderHeader.setNumber(Integer.parseInt(map.get("number").toString()));
					}
				}
			}
			else{
				ram.setFlag(-1);
				ram.setResultMessage("please enter order type!");
				return ram;
			}

			if (map.get("memo")!=null) {
				orderHeader.setMemo((map.get("memo").toString()));
			}
			//排除dine in的情况
			if (!(orderHeader.getOrderType()==3 && map.get("item")!=null)){
				//reservation
				if (orderHeader.getOrderType()==3&&map.get("item")==null) {
					if (map.get("number")!=null) {
						orderHeader.setNumber(Integer.parseInt(map.get("number").toString()));
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("please enter number!");
						return ram;
					}
				}
				
				
				if(map.get("firstName")!=null && map.get("lastName")!=null){
					orderHeader.setPeopleName(map.get("firstName").toString()+" "+map.get("lastName"));
				}
				if(map.get("phone")!=null){
					orderHeader.setPhoneNumber(map.get("phone").toString());
				}
				if(map.get("email")!=null){
					orderHeader.setEmail(map.get("email").toString());
				}
				
				if(map.get("consumerUuid") != null && StringUtil.isNotEmpty(map.get("consumerUuid").toString())){
					Consumers consumers=new Consumers();
					consumers.setUuid(map.get("consumerUuid").toString());
					orderHeader.setConsumers(consumers);
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("please enter consumerUuid!");
					return ram;
				}
				if(map.get("restaurantUuid")!=null && StringUtil.isNotEmpty(map.get("restaurantUuid").toString())){
					orderHeader.setRestaurantUuid(map.get("restaurantUuid").toString());
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("please enter restaurantUuid!");
					return ram;
				}
				if(map.get("orderDate")!=null){
					try {
						Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(map.get("orderDate").toString());
						orderHeader.setOrderDate(date);
						/*if (orderHeader.getOrderType()==3 && map.get("item")==null) {//reservation
							int flagTime = openTimeService.orderDateAtOpenTime(orderHeader.getOrderDate(), orderHeader.getRestaurantId(), orderHeader.getOrderType());
							if(flagTime!=1){//不在营业时间内
								ram.setFlag(-1);
								if (flagTime==-1) {
									ram.setResultMessage(MessageConstant.OUTOFBUSINESSTIME);
								}
								else if (flagTime==-2) {
									ram.setResultMessage(MessageConstant.ORDER_AFTER_30);
								}
								else if(flagTime==-3){
									ram.setResultMessage(MessageConstant.ORDER_AFTER_15);
								}
								else if(flagTime==-4){
									ram.setResultMessage(MessageConstant.ORDER_AFTER_NOW);
								}
								return ram;
							}
						}*/
						
					} catch (ParseException e) {
						ram.setFlag(-1);
						ram.setResultMessage("Please enter the correct date format!");
						return ram;
					}
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("please enter order date!");
					return ram;
				}
			}
			
			if(map.get("payment")!=null){
				orderHeader.setPayment(Integer.parseInt(map.get("payment").toString()));
			}
			else{
				ram.setFlag(-1);
				ram.setResultMessage("please enter payment!");
				return ram;
			}
			
			
			if (orderHeader.getOrderType()==1) {//delivery
				if (map.get("logistics")!=null) {
					orderHeader.setLogistics(Double.parseDouble(format.format(Double.parseDouble(map.get("logistics").toString()))));
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("have no logistics!");
					return ram;
				}
				
				if (map.get("addressId")!=null) {
					ConsumersAddress address = consumersAddressService.getAddressById(Long.parseLong(map.get("addressId").toString()));
					if (address!=null) {
						orderHeader.setAddress(address.getFullAddress());
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("the addressId invalid!");
						return ram;
					}
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("have no addressId!");
					return ram;
				}
			}
			
			//排除reservation情况
			if (!(orderHeader.getOrderType()==3 && map.get("item")==null)){
				if (map.get("total")!=null) {
					orderHeader.setTotal(Double.parseDouble(format.format(Double.parseDouble(map.get("total").toString()))));
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("have no total!");
					return ram;
				}

			}
			
			//加载所有菜品和配菜
			
			if(map.get("item")!=null){
				//先判断dine in的情况
				if (orderHeader.getOrderType()==3 && map.get("item")!=null) {
					if (map.get("orderId")!=null) {
						orderHeader.setId(Long.parseLong(map.get("orderId").toString()));
						OrderHeader orderHeader1 = orderService.getOrderById(orderHeader.getId());
						orderHeader.setConsumers(orderHeader1.getConsumers());
						orderHeader.setRestaurantUuid(orderHeader1.getRestaurantUuid());
						orderHeader.setOrderDate(orderHeader1.getOrderDate());
						orderHeader.setPeopleName(orderHeader1.getPeopleName());
						orderHeader.setEmail(orderHeader1.getEmail());
						orderHeader.setMemo(orderHeader1.getMemo());
						orderHeader.setNumber(orderHeader1.getNumber());
						orderHeader.setPhoneNumber(orderHeader1.getPhoneNumber());
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("have no order id!");
						return ram;
					}
				}
				List<Map<String, Object>> listItem=(List<Map<String, Object> >) map.get("item");
				List<OrderItem> orderItems=new ArrayList<OrderItem>();
				//double total = 0;
				for (Map<String, Object> mapItem : listItem) {
					OrderItem orderItem=new OrderItem();
					orderItem.setCreatetime(new Date());
					if(mapItem.get("num")!=null){
						orderItem.setNum(Integer.valueOf(mapItem.get("num").toString()));
					}
					
					if (mapItem.get("dishId")!=null) {
						orderItem.setDishId(Integer.parseInt(mapItem.get("dishId").toString()));
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("have no dishId!");
						return ram;
					}
				
					if(mapItem.get("instruction")!=null){
						orderItem.setInstruction(mapItem.get("instruction").toString());
					}
					
					if(mapItem.get("subItem")!=null){
						List<OrderDishGarnish> orderDishGarnishs=new ArrayList<OrderDishGarnish>();
						List<Map<String, Object>> listSubItem=(List<Map<String, Object> >) mapItem.get("subItem");
						
						for (Map<String, Object> mapSubItem : listSubItem) {
							OrderDishGarnish orderDishGarnish = new OrderDishGarnish();
							GarnishItem garnishItem = new GarnishItem();
							garnishItem.setId(Long.valueOf(mapSubItem.get("garnishItemId").toString()));
							orderDishGarnish.setGarnishItem(garnishItem);
							orderDishGarnish.setGarnishNum(Integer.parseInt(mapSubItem.get("garnishNum").toString()));
							orderDishGarnishs.add(orderDishGarnish);
						}
						Set<OrderDishGarnish> dishgarnishSet =new HashSet<OrderDishGarnish>();
						dishgarnishSet.addAll(orderDishGarnishs);
						orderItem.setOrderDishGarnishs(dishgarnishSet);
					}				
					orderItem.setPrice(Double.parseDouble(format.format(Double.parseDouble(mapItem.get("unitprice").toString()))));
					orderItems.add(orderItem);
					//total += orderItem.getPrice();
				}
				Set<OrderItem> itemSet =new HashSet<OrderItem>();
				itemSet.addAll(orderItems);
				orderHeader.setOrderItems(itemSet);
			}
		}
///////////////////////////////////////////////////////////////////////////////////////
		
		
			if (orderHeader.getOrderType()==1||orderHeader.getOrderType()==2) {
				if (map.get("orderDate")!=null ) {
				try {
					Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(map.get("orderDate").toString());
					orderHeader.setOrderDate(date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					ram.setFlag(-1);
					ram.setResultMessage("Please enter the correct date format!");
					return ram;
				}
			}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("have no orderDate!");
					return ram;
				}
				
				if (map.get("email")!=null) {
					orderHeader.setEmail(map.get("email").toString());
				}
				if (map.get("phone")!=null) {
					orderHeader.setPhoneNumber(map.get("phone").toString());
				}
				String first = map.get("firstName")==null?"":map.get("firstName").toString();
				String last = map.get("lastName")==null?"":map.get("lastName").toString();
				orderHeader.setPeopleName(first+" "+last);
				if (orderHeader.getOrderType()==1) {
					if (map.get("addressId")!=null) {
						ConsumersAddress address = consumersAddressService.getAddressById(Long.parseLong(map.get("addressId").toString()));
						if (address!=null) {
							orderHeader.setAddress(address.getFullAddress());
						}
					}
				}
			
		}


		if (!(orderHeader.getOrderType()==3 && orderHeader.getOrderItems()==null)) {
			//非reservation情况
			if(map.get("tip")!=null){
				Log4jUtil.info("传来的tip==>"+"tip="+(Math.round(Double.valueOf(map.get("tip").toString())*100))/100.0);
				orderHeader.setTip((Math.round(Double.valueOf(map.get("tip").toString())*100))/100.0);
			}
			else{
				orderHeader.setTip(0);
			}
		}

		Restaurants restaurants = null;
		if (orderHeader.getOrderType()==3 && orderHeader.getOrderItems()!=null && orderHeader.getOrderItems().size()>0){
			OrderHeader oh = orderService.getOrderById(orderHeader.getId());
			restaurants = restaurantsService.getRestaurantsByUuid(oh.getRestaurantUuid());
		}
		else{
			restaurants = restaurantsService.getRestaurantsByUuid(orderHeader.getRestaurantUuid());
		}
		

		
		//计算优惠
		if (map.get("discountId")!=null) {
			//如果有优惠券id 就将这个id保存到订单头中
			orderHeader.setDiscountId(Integer.parseInt(map.get("discountId").toString()));
			Discount discount = discountService.getDiscount(Long.parseLong(map.get("discountId").toString()));
			if(discount!=null){
				if(discount.getType() == 1){//1、现金抵用券 2、打折券 
					orderHeader.setTotal(orderHeader.getTotal()-discount.getPrice());
				}else if(discount.getType() == 2){
					orderHeader.setTotal(StringUtil.convertLastDouble(orderHeader.getTotal()*(100-discount.getDiscount())/100));
				}
			}
		}
		
		//计算配送费
		/*orderHeader.setLogistics(0);
		if (orderHeader.getOrderType()==1) {
			if (orderHeader.getTotal()>=(restaurants.getDeliverPrice()==null?0:restaurants.getDeliverPrice())) {
				if (map.get("addressId")!=null) {
					double logistic = this.getLogistic(map.get("addressId").toString(), restaurants, orderHeader.getTotal());
					orderHeader.setLogistics(StringUtil.convertLastDouble(logistic));
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("have no addressId!");
					return ram;
				}
			}
			else{
				ram.setFlag(-1);
				ram.setResultMessage("min delivery order price is "+restaurants.getDeliverPrice());
				return ram;
			}
			
		}*/

		//计算税费
		orderHeader.setTax(Double.parseDouble(format.format((restaurants.getViewArea().getGst()+restaurants.getViewArea().getPst())*(orderHeader.getLogistics()+orderHeader.getTotal()))));
		//计算总价
		//orderHeader.setAmount(orderHeader.getTotal()+orderHeader.getLogistics()+orderHeader.getTax()+orderHeader.getTip());
		Log4jUtil.info("tip==>"+orderHeader.getTip()+"amount==>"+Double.parseDouble(format.format(orderHeader.getTotal()+orderHeader.getLogistics()+orderHeader.getTax()+orderHeader.getTip())));
		orderHeader.setAmount(Double.parseDouble(format.format(orderHeader.getTotal()+orderHeader.getLogistics()+orderHeader.getTax()+orderHeader.getTip())));
		if (map.get("payment")!=null) {
			orderHeader.setPayment(Integer.parseInt(map.get("payment").toString()));
			if (orderHeader.getPayment()==0) {//现金支付
				orderHeader.setStatus(9);//现金支付状态
			}
			else if(orderHeader.getPayment()==1){//卡支付
				if (map.get("cardId")==null) {
					ram.setFlag(-1);
					ram.setResultMessage("Have no cardId!");
					return ram;
				}
				orderHeader.setStatus(1);//未支付状态
			}
		}
		else{
			ram.setFlag(-1);
			ram.setResultMessage("Have no payment!");
			return ram;
		}

		if (orderHeader.getOrderType()==3 && (orderHeader.getOrderItems()==null || orderHeader.getOrderItems().size()==0)) {
			//预定并且没有点菜
			if(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).equals(new SimpleDateFormat("yyyy-MM-dd").format(orderHeader.getOrderDate()))){
				orderHeader.setStatus(10);
			}
			else{
				orderHeader.setStatus(3);
			}
		}
	
		int flag = orderService.addOrder(orderHeader);
		if(flag == -1){//添加失败
			ram.setResultMessage(MessageConstant.ALL_FAILED);
			ram.setFlag(-1);
			return ram;
		} else if (flag > 0){//添加成功
			
			//删除购物车
			//判断是否有cartHeaderId
			if(map.get("cartHeaderId") != null){//排除Reservation的情况
				cartService.deleteCartByConsumerUuid(orderHeader.getConsumers().getUuid());
			}
			
			if (orderHeader.getOrderType()==3 && (orderHeader.getOrderItems()==null || orderHeader.getOrderItems().size()==0)) {
				//预定并且没有点菜
				ram.setFlag(1);
				Map<String,Object> mapBody = new HashMap<String,Object>();
				mapBody.put("orderId", flag);
				ram.setBody(mapBody);
				return ram;
			}
			//设置付款
			if(orderHeader.getPayment()==1){//信用卡付款
				Consumers consumers = consumersService.getConsumersByUuid(orderHeader.getConsumers().getUuid());
				if(consumers!=null && StringUtil.isNotEmpty(consumers.getStripeCustomerId())){
					ChargeEntity chargeEntity = new ChargeEntity();
					chargeEntity.setCustomerId(consumers.getStripeCustomerId());
					//Restaurants restaurants = restaurantsService.getRestaurantsById(Long.parseLong(restaurantId));
					if (restaurants!=null && StringUtil.isNotEmpty(restaurants.getStripeAccount())) {
						chargeEntity.setAccountId(restaurants.getStripeAccount());
						chargeEntity.setAmount((int)orderHeader.getAmount()*100);
						chargeEntity.setCardId(map.get("cardId").toString());
						chargeEntity.setApplicatonFee((int)(orderHeader.getTotal()*10+orderHeader.getAmount()*2.9+30));
						int flag1 = paymentService.chargeByCard(chargeEntity,String.valueOf(flag));
						if(flag1==1){//付款成功
							ram.setFlag(1);
							Map<String,Object> mapBody = new HashMap<String,Object>();
							mapBody.put("orderId", flag);
							ram.setBody(mapBody);
							ram.setResultMessage("");
							return ram;
						}
					}
				}
				ram.setFlag(-1);
				Map<String,Object> mapBody = new HashMap<String,Object>();
				mapBody.put("orderId", flag);
				ram.setBody(mapBody);
				ram.setResultMessage(MessageConstant.PAY_FAIL);
				return ram;
			}
			else if(orderHeader.getPayment()==0){
				ram.setFlag(1);
				Map<String,Object> mapBody = new HashMap<String,Object>();
				mapBody.put("orderId", flag);
				ram.setBody(mapBody);
				return ram;
			}
		}
		ram.setFlag(-1);
		ram.setResultMessage(MessageConstant.PAY_FAIL);
		return ram;
	}
	
	/**
	 * @Title: addOrder
	 * @Description: 获取所有店铺分类
	 * @param:  orderHeader   consumerId
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	@RequestMapping(value = "/classification", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel classification(){
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(classificationService.getAllClassification());
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setResultMessage(e.toString());
			ram.setFlag(-1);
		}
		return ram;
	}
	
	/**
	 * @Title: getShortcutMenu
	 * @Description: 获取快捷菜单
	 * @param:    
	 * @return: ResultApiModel
	 */
	@RequestMapping(value="/index/type",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getShortcutMenu(String consumerUuid,Integer type,HttpSession session){
		Log4jUtil.info("获取快捷菜单接口==>"+"consumerUuid="+consumerUuid+"type="+type);
		ResultApiModel ram = new ResultApiModel();
		if(!StringUtil.isNotEmpty(consumerUuid)){
			consumerUuid="";
		}
		if(session.getAttribute("consumer")!=null ){
			consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
		}
		List<ViewConsumerClassifitionApiModel> list = consumersService.getShortcutMenu(consumerUuid,type);
		if(list!=null){
			ram.setBody(list);
			ram.setFlag(1);
			ram.setResultMessage("");
		}else{		
			ram.setFlag(-1);
			ram.setResultMessage(MessageConstant.ALL_FAILED);
		}
		return ram;
	}
	
	/**
	 * @Title: addCart
	 * @Description: 添加购物车信息
	 * @param:    
	 * @return: ResultApiModel
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cart/add",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addCart(String context){
		Log4jUtil.info("添加购物车信息==>"+context);
		ResultApiModel ram = new ResultApiModel();
		if (StringUtil.isNotEmpty(context)) {
			//return cartService.addAndCheckCart(context);
			ram = cartService.addAndCheckCart(context);
			if (ram.getFlag()==1) {
				Map<String,Object> map = (Map<String,Object>)ram.getBody();
				//CartHeader cartHeader = cartHeaderService.getCartHeaderById(Long.parseLong(map.get("cartId").toString()));
				CartHeader cartHeader = new CartHeader();
				cartHeader.setId(Long.parseLong(map.get("cartId").toString()));
				List<CartItem> list = cartItemService.getCartItemByHeaderId(cartHeader);
				map.clear();
				int num=0;
				for (CartItem cartItem : list) {
					num+=cartItem.getNum();
				}
				map.put("dishNum", num);
				return ram;
			}
		}
		return ram;
	}
	
	/**
	 * @Title: getInfo
	 * @Description: 获取购物车信息
	 * @param:    
	 * @return: ResultApiModel
	 */
	@RequestMapping(value="/cart/getInfo",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getInfo(String mobileToken, Double consumerLng, Double consumerLat, String consumerUuid){
		Log4jUtil.info("获取购物车接口==>"+"mobileToken="+mobileToken+"consumerLng="+consumerLng+"consumerLat="+consumerLat+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		if(!StringUtil.isNotEmpty(consumerUuid)){
			consumerUuid = "";
		}
		CartHeaderApiModel apiModel = cartService.getCartHeaderApiModel(mobileToken, consumerUuid);
		if (apiModel!=null) {
			ram.setFlag(1);
			ram.setBody(apiModel);
			return ram;
		}
		ram.setFlag(-1);
		ram.setResultMessage("The cart is empty!");
		return ram;
	}
	
	/**
	 * @Title: getCartHeaderByMobileToken
	 * @Description:登陆获取购物车有无信息
	 * @param:    mobileToken
	 * @return: CartHeaderLoginApiModel
	 */
	@RequestMapping(value="/cart/have",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getCartHeaderByMobileToken(String mobileToken){
		Log4jUtil.info("登录获取购物车接口==>"+"mobileToken="+mobileToken);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(cartHeaderService.getCartHeaderByMobileToken(mobileToken));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setResultMessage(e.toString());
			ram.setFlag(-1);
		}
		
		return ram;
	}
	
	/**
	 * @Title: deleteCartHeaderByMobileToken
	 * @Description:删除购物车信息
	 * @param:    String
	 * @return: CartHeader
	 */
	@RequestMapping(value="/cart/delete",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel deleteCartHeaderByMobileToken(String mobileToken, String consumerUuid){
		Log4jUtil.info("清空购物车接口==>"+"mobilToken="+mobileToken+",consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		if (StringUtil.isNotEmpty(mobileToken) || StringUtil.isNotEmpty(consumerUuid)) {
			int flag = cartService.deleteCartHeader(mobileToken, consumerUuid);
			if (flag==1) {//成功
				ram.setFlag(1);
				return ram;
			}
		}
		ram.setFlag(-1);
		ram.setResultMessage(MessageConstant.ALL_FAILED);
		return ram;
	}
	
	/**
	 * 删除购物车中单个菜品
	 * @param mobileToken
	 * @param dishId
	 * @return
	 */
	@RequestMapping(value="/cart/deleteone",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel deleteCartContent(String mobileToken,Integer dishId, Double consumerLng, Double consumerLat,String consumerUuid){
		Log4jUtil.info("删除获取购物车单个菜的接口==>"+"mobileToken="+mobileToken+"dishId="+dishId+"consumerLng="+consumerLng+"consumerLat="+consumerLat+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		int flag = cartHeaderService.deleteCartDish(mobileToken, dishId, consumerUuid);
		if (flag==1) {
			ram.setFlag(1);
			ram.setBody(cartService.getCartHeaderApiModel(mobileToken,consumerUuid));
			return ram;
		}
		ram.setFlag(-1);
		ram.setResultMessage(MessageConstant.ADD_FAILED);
		return ram;
	}
	
	/**
	 * @Title: cancelOrder
	 * @Description:  取消订单
	 * @param:  orderId  
	 * @return: -1失败   1成功
	 */
	@RequestMapping(value="/order/cancel",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel cancelOrder(Long orderId){
		Log4jUtil.info("取消订单接口==>"+"orderId="+orderId);
		ResultApiModel ram = new ResultApiModel();
		if (orderId!=null) {
			int flag = orderService.cancelOrder(orderId);
			if (flag == 1){
				ram.setResultMessage("");
				ram.setFlag(1);
				//推送  "标题", "内容", "token"
				OrderHeader oh = orderService.getOrderById(orderId);
				if(oh != null && StringUtil.isNotEmpty(oh.getRestaurantUuid())){
					Restaurants restaurants = new Restaurants();
					restaurants.setUuid(oh.getRestaurantUuid());
					List<RestaurantsUser> list = restaurantsUserService.getAllRestaurantsUser(restaurants);
					for (RestaurantsUser restaurantsUser : list) {
						if(StringUtil.isNotBlank(restaurantsUser.getToken())){//判断token是否为空
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
							String str="";
							if(oh.getOrderType()==1){
								str=GlobalConstant.ORDER_DELIVERY_STR;
							}else if(oh.getOrderType()==2){
								str=GlobalConstant.ORDER_PICKUP_STR;
							}else{
								str=GlobalConstant.ORDER_RESERVATION_STR;
							}
							PushUtil.pushPad("Cancel the order", oh.getPeopleName()+"("+oh.getPhoneNumber()+")"+" Cancelled the "+sdf.format(oh.getOrderDate())+"(Order Type:"+str+")"+" Order information", restaurantsUser.getToken());
						}
					}
				}
				return ram;
			}
			else if(flag==-2){
				ram.setResultMessage("Too late to cancel your order now.");
				ram.setFlag(-2);
				return ram;
			}
		}
		ram.setResultMessage(MessageConstant.ALL_FAILED);
		ram.setFlag(-1);
		return ram;
	}
	
	/**
	 * @Title: repeatOrder
	 * @Description:  重复下订单
	 * @param:  orderId  consumerId
	 * @return: -1失败   1成功
	 */
	@RequestMapping(value="/repeat",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel repeatOrder(String consumerUuid, Long orderId, String mobiletoken){
		Log4jUtil.info("重复下订单接口==>consumerUuid="+consumerUuid+",orderId="+orderId+",mobiletoken="+mobiletoken);
		ResultApiModel ram = new ResultApiModel();
		if (orderId!=null) {
			int flag = orderService.repeatOrder(orderId, mobiletoken);
			OrderHeader orderHeader = orderService.getOrderById(orderId);
			if (flag==1) {//购物车增加成功
				CartHeaderApiModel cartHeaderApiModel = cartService.getCartHeaderApiModel(mobiletoken, orderHeader.getConsumers().getUuid());
				ram.setFlag(1);
				ram.setBody(cartHeaderApiModel);
				ram.setResultMessage("Please review your items in My Order");
				return ram;
			}
		}
		ram.setFlag(-1);
		ram.setResultMessage(MessageConstant.ALL_FAILED);
		return ram;
	}
	
	
	/**
	 * 选择折扣	
	 * @param mobileToken
	 * @param dishId
	 * @return
	 */
	@RequestMapping(value="/cart/sDiscount",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectDiscount(String mobileToken,Double restaurantLat,Double restaurantLng,Long discountId,String restaurantUuid,String consumerUuid){
		Log4jUtil.info("选择折扣接口==>mobilToken="+mobileToken+",discountId="+discountId+",restaurantUuid="+restaurantUuid+",consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		try{
			if(restaurantLat==null||restaurantLng==null){
				restaurantLat=new Double(0);
				restaurantLng=new Double(0);
			}
			ram.setFlag(1);
			ram.setBody(cartHeaderService.reCalcCost(mobileToken, restaurantLat, restaurantLng, discountId, restaurantUuid,consumerUuid));
			ram.setResultMessage("");
		}catch(Exception e){
			e.printStackTrace();
			ram.setFlag(0);
			ram.setResultMessage(MessageConstant.ALL_FAILED);
		}
		return ram;
	}
	
	
	/**
	 * 客户添加信用卡
	 * @param String number  卡号
	 * @param Integer exp_month 过期月
	 * @param Integer exp_year 过期年 yyyy
	 * @param String cvc 
	 * @param String consumerId 平台客户的id
	 * @return ResultApiModel flag:-1失败  1成功
	 */
	@RequestMapping(value="/stripe/addCard",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel addCard(CardEntity cardEntity,String consumerUuid,HttpSession session){
		Log4jUtil.info("添加信用卡接口==>"+cardEntity.toString()+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		try {
			if (StringUtil.isNotEmpty(cardEntity.getNumber()) && cardEntity.getExp_month()!=null &&
					cardEntity.getExp_year()!=null && StringUtil.isNotEmpty(cardEntity.getCvc())) {
				String number = cardEntity.getNumber();
				if (CommonUtil.luhnTest(number)) {//验证是否是一个信用卡
					if ((number.matches("(4\\d{12}(?:\\d{3})?)") && !number.matches("^(4026|417500|4405|4508|4844|4913|4917)\\d+$"))|| //visa
							(number.matches("(5[1-5]\\d{14})"))) {//mastercard
						if (String.valueOf(cardEntity.getExp_month()).matches("^(0?[1-9]|1[0-2])$")) {
							int year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
							if (cardEntity.getExp_year()>=year) {
								if (cardEntity.getCvc().matches("^\\d{3}$")) {
									//卡号初步验证通过
									int flag = paymentService.addCard(cardEntity, consumerUuid);
									if (flag==1) {//增加成功
										ram.setFlag(1);
										Consumers consumers2 = consumersService.getConsumersByUuid(consumerUuid);
										if(consumers2!=null){
											session.setAttribute("consumer", consumers2);
										}
										return ram;
									}
								}
								else{
									ram.setFlag(-1);
									ram.setResultMessage("cvv is not correct!");
									return ram;
								}
							}
							else{
								ram.setFlag(-1);
								ram.setResultMessage("exp_year is not correct!");
								return ram;
							}
						}
						else{
							ram.setFlag(-1);
							ram.setResultMessage("exp_month is not correct!");
							return ram;
						}
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("This card is not support!");
						return ram;
					}
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("Card number is not correct!");
					return ram;
				}
			}
			ram.setFlag(-1);
			ram.setResultMessage(MessageConstant.STRIPE_ADDCARD_FAIL);
			return ram;
			
		} catch (Exception e) {
			// TODO: handle exception
			Log4jUtil.info("添加信用卡接口Stripe异常==>"+e.toString());
			ram.setFlag(-1);
			ram.setResultMessage(MessageConstant.STRIPE_ADDCARD_FAIL);
			return ram;
		}
	}
	
	
	/**
	 * 列出所有的银行卡
	 * @param String consumerId 平台客户的id
	 * @return ResultApiModel flag:0没有银行卡  1有银行卡
	 * @return id:stripe的card的id
	 * @return last4:卡号后四位 
	 * @return type:卡的类型  例如visa
	 */
	@RequestMapping(value="/stripe/listAllCards",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel listAllCards(String consumerUuid){
		Log4jUtil.info("列出所有的银行卡接口==>"+"consumerUuid="+consumerUuid);
		ResultApiModel ram = new ResultApiModel();
		List<CardEntity> list = paymentService.listAllCards(consumerUuid);
		if (list!=null) {
			ram.setBody(list);
			ram.setFlag(1);
		}
		return ram;
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
	@RequestMapping(value="/stripe/chargeByCardId",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel chargeByCardId(ChargeEntity chargeEntity,String consumerUuid,String restaurantUuid,String orderId){
		Log4jUtil.info("根据指定的卡号付款接口==>"+chargeEntity.toString()+"consumerUuid="+consumerUuid+"restaurantUuid="+restaurantUuid+"orderId="+orderId);
		ResultApiModel ram = new ResultApiModel();
		Consumers consumers = consumersService.getConsumersById(Long.parseLong(consumerUuid));
		if(consumers!=null && StringUtil.isNotEmpty(consumers.getStripeCustomerId())){
			chargeEntity.setCustomerId(consumers.getStripeCustomerId());
			Restaurants restaurants = restaurantsService.getRestaurantsByUuid(restaurantUuid);
			if (restaurants!=null && StringUtil.isNotEmpty(restaurants.getStripeAccount())) {
				chargeEntity.setAccountId(restaurants.getStripeAccount());
				int flag = paymentService.chargeByCard(chargeEntity,orderId);
				if(flag==1){//付款成功
					ram.setFlag(1);
					return ram;
				}
			}
		}
		ram.setResultMessage(MessageConstant.PAY_FAIL);
		return ram;
	}
	
	/**
	 * @Title: getDineIn
	 * @Description: 商家已经审核的订单（预定）
	 * @param:  restaurantId   
	 * @return: List<OrderDineInApiModel>
	 */
	@RequestMapping(value="/dineinorder",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getDineIn(String consumerUuid, String restaurantUuid){
		Log4jUtil.info("商家已经审核的订单（预定）接口==>"+"consumerUuid="+consumerUuid+"restaurantUuid="+restaurantUuid);
		ResultApiModel ram = new ResultApiModel();
		try{
			ram.setFlag(1);
			ram.setBody(orderService.getDineIn(consumerUuid, restaurantUuid));
			ram.setResultMessage("");
		}catch(Exception e){
			e.printStackTrace();
			ram.setFlag(0);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getOrderMoney
	 * @Description: 获取订单所有的费用
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@RequestMapping(value="/cartordermoney",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getOrderMoney(String cartHeaderId, String addressId){
		Log4jUtil.info("获取订单所有的费用==>cartHeaderId="+cartHeaderId+",addressId="+addressId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderService.getOrderMoney(cartHeaderId,addressId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setResultMessage(e.toString());
			ram.setFlag(-1);
		}
		return ram;
	}
	
	/**
	 * @Title: savelineup
	 * @Description: 用户确认排队订单
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ， 1成功
	 */
	@RequestMapping(value="/savelineup",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel savelineup(long orderId){
		Log4jUtil.info("用户确认排队订单 接口==>"+"orderId="+orderId);
		ResultApiModel ram = new ResultApiModel();
		OrderHeader oh = orderService.getOrderById(orderId);
		int flag = orderService.savelineup(orderId);
		if (flag == 1) {// 成功
			ram.setFlag(1);
			ram.setResultMessage("");
			//推送
			if(oh != null && StringUtil.isNotEmpty(oh.getRestaurantUuid())){
				Restaurants restaurants = new Restaurants();
				restaurants.setUuid(oh.getRestaurantUuid());
				List<RestaurantsUser> list = restaurantsUserService.getAllRestaurantsUser(restaurants);
				for (RestaurantsUser restaurantsUser : list) {
					if(StringUtil.isNotBlank(restaurantsUser.getToken())){//判断token是否为空
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
						PushUtil.pushPad("Line up to confirm", oh.getPeopleName()+"("+oh.getPhoneNumber()+")"+" Line up to confirm "+sdf.format(oh.getOrderDate())+" Order information", restaurantsUser.getToken());
					}
				}
			}
		} else {
			ram.setFlag(-1);
			ram.setResultMessage("");
		}
		return ram;
	}
	
	/**
	 * 重复付款
	 * @param String cradId  stripe的卡id
	 * @param String consumerId  平台的客户id
	 * @return ResultApiModel flag:0失败  1成功
	 */
	@RequestMapping(value="/stripe/chargeByCardId/repeat",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel chargeByCardId(String cardId, String orderId){
		Log4jUtil.info("重复付款接口==>cardId="+cardId+",orderId="+orderId);
		ResultApiModel ram = new ResultApiModel();
		//判断订单id与信用卡的id非空
		if(StringUtil.isNotEmpty(orderId) && StringUtil.isNotEmpty(cardId)){
			OrderHeader order = orderService.getOrderById(Long.parseLong(orderId));
			if(order != null){
				Consumers consumer = consumersService.getConsumersById(order.getConsumers().getId());
				Restaurants restaurant = restaurantsService.getRestaurantsByUuid(order.getRestaurantUuid());
				if(consumer != null && restaurant != null){
					ChargeEntity chargeEntity = new ChargeEntity();
					chargeEntity.setAmount((int)order.getAmount()*100);
					chargeEntity.setCustomerId(consumer.getStripeCustomerId());
					chargeEntity.setAccountId(restaurant.getStripeAccount());
					chargeEntity.setCardId(cardId);
					int flag = paymentService.chargeByCard(chargeEntity,orderId);
					if(flag==1){//付款成功
						ram.setFlag(1);
						return ram;
					}
				}
			}
		}
		ram.setResultMessage(MessageConstant.PAY_FAIL);
		return ram;
	}
	
	/**
	 * @Title: getConsumersAddressDefault
	 * @Description: 获取用户默认地址
	 * @param:    consumersid
	 * @return: ConsumersAddress
	 */
	@RequestMapping(value="/address/default",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getConsumersAddressDefault(String consumerUuid,String restauranUuid) {
		Log4jUtil.info("获取用户默认地址接口==>"+"consumerUuid="+consumerUuid+"restauranUuid"+restauranUuid);
		ResultApiModel ram = new ResultApiModel();
		ram.setFlag(1);
		if (StringUtil.isNotEmpty(consumerUuid)&&StringUtil.isNotEmpty(restauranUuid)) {
			ConsumersAddressDefaultApiModel apiModel = consumersAddressService.getConsumersAddressDefaultByConsumerUuid(consumerUuid);
			if (apiModel!=null) {                                              
				ram.setBody(apiModel);
			}
			else{
				List<ConsumersAddressApiModel> list = consumersAddressService.getConsumersAddressById(consumerUuid, restauranUuid);
				ram.setBody(list.get(0));
			}
		}
		return ram;
	}
	
}
