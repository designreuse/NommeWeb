package com.camut.controller.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.CartHeader;
import com.camut.model.Consumers;
import com.camut.model.ConsumersAddress;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.model.api.CancelOrderApiModel;
import com.camut.model.api.CartDishGarnishApiModel;
import com.camut.model.api.CartItemApiModel;
import com.camut.model.api.OrderDetailsApiModel;
import com.camut.model.api.OrderItemApiModel;
import com.camut.model.api.RestaurantsApiModel;
import com.camut.model.api.ResultApiModel;
import com.camut.service.CartHeaderService;
import com.camut.service.ConsumersAddressService;
import com.camut.service.ConsumersService;
import com.camut.service.DiscountService;
import com.camut.service.DistancePriceService;
import com.camut.service.EvaluateService;
import com.camut.service.DishService;
import com.camut.service.OpenTimeService;
import com.camut.service.OrderItemService;
import com.camut.service.OrderService;
import com.camut.service.RestaurantTableService;
import com.camut.service.RestaurantsMenuService;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;
import com.camut.service.ViewRestaurantService;
import com.camut.utils.CommonUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MailUtil;
import com.camut.utils.PushUtil;
import com.camut.utils.StringUtil;

/**
 * @entity RestaurantApiController .
 * @author zyf
 * @createTime 2015-06-08
 * @author
 * @updateTime
 * @memo
 */
@Controller("RestaurantApiController")
@RequestMapping("/api/restaurant")
public class RestaurantApiController  extends BaseAPiModel {

	@Autowired private RestaurantsUserService restaurantsUserService;
	@Autowired private OpenTimeService openTimeService;
	@Autowired private OrderService orderServicr;
	@Autowired private OrderItemService orderItemService;
	@Autowired private DishService dishService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private EvaluateService evaluateService;
	@Autowired private DiscountService discountService;
	@Autowired private DistancePriceService distancePriceService;
	@Autowired private RestaurantsMenuService restaurantsMenuService;
	@Autowired private RestaurantTableService restaurantTableService;
	@Autowired private ConsumersAddressService consumersAddressService;
	@Autowired private ViewRestaurantService viewRestaurantService;
	@Autowired private ConsumersService consumersService;
	@Autowired private CartHeaderService cartHeaderService;
	
	/**
	 * @Title: restaurantsUserLogin
	 * @Description: 商家员工登陆
	 * @param:    restaurantsUser对象
	 * @return: ram -1用户名不存在，0密码错误，1登陆成功，2待审核
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel doLogin(HttpSession httpSession, RestaurantsUser restaurantsUser){
		Log4jUtil.info("商家员工登录接口==>"+restaurantsUser.toString());
		ResultApiModel ram = new ResultApiModel();
		if(restaurantsUser != null){
			int flag = restaurantsUserService.appRestaurantsUserLogin(restaurantsUser);
			RestaurantsUser ru = restaurantsUserService.Login(restaurantsUser);
			RestaurantsApiModel rdam = restaurantsService.getRestaurantById(ru.getRestaurants().getId());
			if(flag == GlobalConstant.LOGINNAME_ERROR){//用户名不存在
				ram.setFlag(-1);
				ram.setResultMessage(MessageConstant.LOGINNAME_ERROR);
			}else if(flag == GlobalConstant.PASSWORD_ERROR){//密码不正确
				ram.setFlag(0);
				ram.setResultMessage(MessageConstant.PASSWORD_ERROR);
			}else if(flag == GlobalConstant.LOGIN_OK){//登录成功
				restaurantsUserService.saveTokenAndType(restaurantsUser);
				ram.setFlag(1);
				ram.setBody(rdam);
				ram.setResultMessage("");
			}else if(flag == 2){//待审核状态
				ram.setFlag(2);
				ram.setResultMessage("This user's status is pending");
			}
		}
		return ram;
	}
	
	/**
	 * @Title: selectOpenTime
	 * @Description: 营业时间显示
	 * @param:  
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/opentime", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectOpenTime(String restaurantId, int type, String date) {
		Log4jUtil.info("营业时间显示接口==>"+"restaurantId="+restaurantId+"type="+type+"date="+date);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(openTimeService.selectOpenTime(Long.parseLong(restaurantId), type, date));
			//ram.setBody(openTimeService.selectOpenTime(Long.parseLong(restaurantId)));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: selectRestaurantOrder
	 * @Description: 店家订单列表
	 * @param:  consumerId  orderType  createdate
	 * @return: List<OrderHeader>
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel selectRestaurantOrder(int restaurantId, String orderType, String createdate) throws ParseException{
		Log4jUtil.info("营业时间显示接口==>"+"restaurantId="+restaurantId+"orderType="+orderType+"createdate="+createdate);
		ResultApiModel ram = new ResultApiModel();
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = null;
		if (StringUtil.isEmpty(createdate)) {
			dt = new Date();
		} else {
			dt = sim.parse(createdate);
		}
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.selectRestaurantOrder(restaurantId, orderType, sim.format(dt)));
			List<CancelOrderApiModel> coamList = orderServicr.selectRestaurantOrder(restaurantId, orderType, sim.format(dt));
			double total = 0;
			double subtotal = 0;
			double delivery = 0;
			double tax = 0;
			double tip = 0;
			for (CancelOrderApiModel cancelOrderApiModel : coamList) {
				total += cancelOrderApiModel.getTotal();
			}
			BigDecimal b = new BigDecimal(total);//商家评分  
			double totalscore = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//保留小数后一位
			ram.setTotal(totalscore);
			
			List<OrderHeader> ohList = orderServicr.completeOrder(restaurantId,createdate, orderType);
			if(ohList.size() > 0){
				for (OrderHeader orderHeader : ohList) {
					subtotal += orderHeader.getTotal();
					delivery += orderHeader.getLogistics();
					tax += orderHeader.getTax();
					tip += orderHeader.getTip();
				}	
			}
			ram.setSubtotal(StringUtil.convertLastDouble(subtotal));
			ram.setDelivery(StringUtil.convertLastDouble(delivery));
			ram.setTax(StringUtil.convertLastDouble(tax));
			ram.setTip(StringUtil.convertLastDouble(tip));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: handleOrder
	 * @Description: 订单处理（pad）
	 * @param:  orderItem
	 * @return:  ram  1：处理成功 -1：处理失败
	 */
	@RequestMapping(value = "/handle", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel handleOrder(OrderItemApiModel orderItemApiModel,HttpSession session) {
		Log4jUtil.info("处理订单=>"+orderItemApiModel.getOrderId()+"=>"+orderItemApiModel.getStatus()+"=>"+orderItemApiModel.getInstruction());
		ResultApiModel ram = new ResultApiModel();
		//获取订单详情
		OrderDetailsApiModel odam = orderItemService.selectHistoryOrder(orderItemApiModel.getOrderId());		
		int flag = orderServicr.handleOrder(orderItemApiModel);
		if (flag == -1) {// 处理失败
			ram.setResultMessage(MessageConstant.HANDLE_ORDER);
			ram.setFlag(-1);
		} else if (flag == 1) {// 处理成功
			ram.setResultMessage("");
			ram.setFlag(1);
			//推送
			OrderHeader oh = orderServicr.getOrderById(orderItemApiModel.getOrderId());
			Consumers c = consumersService.getConsumersById(oh.getConsumers().getId());
			Restaurants r = restaurantsService.getRestaurantsById(oh.getRestaurantId());
			String restaurant = r.getRestaurantName();//店名
			String rejection = oh.getRejection();//拒绝理由
			if(orderItemApiModel.getStatus() == 3){//接单
				if(StringUtil.isNotEmpty(oh.getEmail())){
					MailUtil.sendMail("Accept Order", convertHtml1(odam), oh.getEmail());//1:android 2:ios 					
				}
				if(StringUtil.isNotEmpty(c.getMobileToken())){
					PushUtil.push(session,"Nomme", "Your order was accepted by "+restaurant+".", c.getMobileToken(), Integer.valueOf(c.getMobileType()));
					Log4jUtil.info("Your order was accepted by "+restaurant+".");
					/*try {
						SNSMobilePush.push(c.getMobileToken(),"Your order was accepted by "+restaurant+".",Integer.parseInt(c.getMobileType()));//1:android 2:ios 
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				}
			}else if (orderItemApiModel.getStatus() == 4) {//拒单
				if(StringUtil.isNotEmpty(oh.getEmail())){
					MailUtil.sendMail("Reject Order", "Your order was reject by "+restaurant+", Because of the following reason: "+rejection+".", oh.getEmail());//1:android 2:ios
				}
				if(StringUtil.isNotEmpty(c.getMobileToken())){
					PushUtil.push(session,"Nomme", "Your order was rejected by "+restaurant+", Because of the following reason: "+rejection+".", c.getMobileToken(), Integer.valueOf(c.getMobileType()));
					Log4jUtil.info("餐厅拒绝点推送ios"+"token="+c.getMobileToken()+", content="+"Your order was rejected by "+restaurant+", Because of the following reason: "+rejection+"."+", type="+c.getMobileType());
					/*try {
						SNSMobilePush.push(c.getMobileToken(),"Your order was rejected by "+restaurant+", Because of the following reason: "+rejection+".",Integer.parseInt(c.getMobileType()));//1:android 2:ios
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				}
			}else if (orderItemApiModel.getStatus() == 8) {//Line up
				if(StringUtil.isNotEmpty(oh.getEmail())){
					MailUtil.sendMail("Line Up Order", restaurant+" got your reservation order. "+rejection, oh.getEmail());//1:android 2:ios
				}
				if(StringUtil.isNotEmpty(c.getMobileToken())){
					PushUtil.push(session,"Nomme", restaurant+" got your reservation order. "+rejection, c.getMobileToken(), Integer.valueOf(c.getMobileType()));
					/*try {
						SNSMobilePush.push(c.getMobileToken(),restaurant+"got your reservation order. "+rejection,Integer.parseInt(c.getMobileType()));//1:android 2:ios 
					} catch (IOException e) {
						e.printStackTrace();
					}*/
				}
			}
		} else if (flag == -2) {
			ram.setResultMessage("Do not repeat the operation,Please reflash again");
			ram.setFlag(-2);
		}
		return ram;
	}
	
	/**
	 * @Title: getdish
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/dish", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getdish(int restaurantId, int type) {
		Log4jUtil.info("菜品信息接口==>"+"restaurantId="+restaurantId+"type="+type);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(dishService.getdish(restaurantId, type));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getRestaurantsById
	 * @Description: 店铺详情
	 * @param:    id
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getRestaurantsById(String restaurantId, String consumerId) {
		Log4jUtil.info("店铺详情接口==>"+"restaurantId="+restaurantId+"consumerId="+consumerId);
		ResultApiModel ram = new ResultApiModel();
		if(restaurantId != null && restaurantId.length() > 0){
			long rid = Long.parseLong(restaurantId);
			long cid = 0;
			if(consumerId != null && consumerId.length() > 0){
				cid = Long.parseLong(consumerId);
			}
			try {
				ram.setFlag(1);
				ram.setBody(restaurantsService.restaurantsDetailApiModel(rid, cid));
				ram.setResultMessage("");
			} catch (Exception e) {
				ram.setFlag(-1);
				ram.setResultMessage(e.toString());
			}
		}
		return ram;
	}
	
	/**
	 * @Title: getEvaluateByRestaurantId
	 * @Description: 店铺评论
	 * @param:  restaurantId
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getEvaluateByRestaurantId(long restaurantId) {
		Log4jUtil.info("店铺评论接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(evaluateService.getEvaluateByRestaurantId(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getDiscountByRestaurantId
	 * @Description: 店铺优惠信息
	 * @param:  restaurantId
	 * @return: ResultApiModel
	 */
	@RequestMapping(value = "/discount", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getDiscountByRestaurantId(long restaurantId, int orderType, double consumePrice) {
		Log4jUtil.info("店铺优惠信息接口==>"+"restaurantId="+restaurantId+"orderType="+orderType+"consumePrice="+consumePrice);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(discountService.getDiscountByRestaurantId(restaurantId, orderType, consumePrice));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getDistancePrice
	 * @Description: 获取配送费
	 * @param: restaurantsApiModel
	 * @return List<DistancePrice> 
	 */
	@RequestMapping(value = "/distanceprice", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getDistancePrice(long restaurantId) {
		Log4jUtil.info("获取配送费接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(distancePriceService.getDistancePrice(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getGarnishHeaderByRestaurantId
	 * @Description: 菜品分类
	 * @param:    restaurantId
	 * @return: List<GarnishHeader>
	 */
	@RequestMapping(value = "/garnishheader", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getGarnishHeaderByRestaurantId(long restaurantId,	int type) {
		Log4jUtil.info("菜品分类接口==>"+"restaurantId="+restaurantId+"type="+type);
		Restaurants restaurants = new Restaurants();
		restaurants.setId(restaurantId);
		if (type > 0) {
			if (type == 1) {
				restaurants.setIsdelivery(1);
			} else if (type == 2) {
				restaurants.setIsdelivery(2);
			} else if (type == 3) {
				restaurants.setIsdelivery(3);
			}
		}
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(restaurantsMenuService.getAllDishMenu(restaurants));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getRestaurantTable
	 * @Description: 获取商家的桌位信息
	 * @param:    Restaurants
	 * @return: RestaurantTable
	 */
	@RequestMapping(value = "/tablecount", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getRestaurantTable(String restaurantId,String orderDate){
		Log4jUtil.info("获取商家的桌位信息接口==>"+"restaurantId="+restaurantId+"orderDate="+orderDate);
		Restaurants restaurants = new Restaurants();
		restaurants.setId(Long.parseLong(restaurantId));
		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date dt = null;
		if (StringUtil.isEmpty(orderDate)) {
			dt = new Date();
		} else {
			try {
				dt = sim.parse(orderDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(restaurantTableService.getRestaurantTableList(restaurants,sim.format(dt)));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: cancelOrder
	 * @Description: 已取消的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	@RequestMapping(value = "/cancelorder", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel cancelOrder(int restaurantId){
		Log4jUtil.info("已取消的订单列表接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.cancelOrder(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: completeOrder
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	@RequestMapping(value = "/completeorder", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel completeOrder(int restaurantId, String status){
		Log4jUtil.info("已取完成订单列表接口==>"+"restaurantId="+restaurantId+"status="+status);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.completeOrder(restaurantId, status));
			List<CancelOrderApiModel> coamList = orderServicr.completeOrder(restaurantId, status);
			double total = 0;
			double subtotal = 0;
			double delivery = 0;
			double tax = 0;
			double tip = 0;
			for (CancelOrderApiModel cancelOrderApiModel : coamList) {
				total += cancelOrderApiModel.getTotal();
			}
			BigDecimal b = new BigDecimal(total);//商家评分  
			double totalscore = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();//保留小数后一位
			ram.setTotal(totalscore);
			
			List<OrderHeader> ohList = orderServicr.completeOrderAll(restaurantId, status);
			if(ohList.size() > 0){
				for (OrderHeader orderHeader : ohList) {
					subtotal += orderHeader.getTotal();
					delivery += orderHeader.getLogistics();
					tax += orderHeader.getTax();
					tip += orderHeader.getTip();
				}	
			}
			ram.setSubtotal(StringUtil.convertLastDouble(subtotal));
			ram.setDelivery(StringUtil.convertLastDouble(delivery));
			ram.setTax(StringUtil.convertLastDouble(tax));
			ram.setTip(StringUtil.convertLastDouble(tip));
			ram.setResultMessage("");
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: liveOrder
	 * @Description: 当天未处理的订单
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@RequestMapping(value = "/live", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel liveOrder(int restaurantId){
		Log4jUtil.info("已取消的订单列表接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.liveOrder(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: upcomingOrder
	 * @Description: 非当天未处理的订单
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@RequestMapping(value = "/upcoming", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel upcomingOrder(int restaurantId){
		Log4jUtil.info("非当天未处理的订单接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.upcomingOrder(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: acceptOrder
	 * @Description: 当天已处理的订单列表
	 * @param:  restaurantId   
	 * @return: List<AcceptOrderApiModel>
	 */
	@RequestMapping(value = "/liveaccept", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel acceptOrder(int restaurantId){
		Log4jUtil.info("当天已处理的订单列表接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.acceptOrder(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: acceptUpcomingOrder
	 * @Description:  已处理非当天的订单列表
	 * @param:  restaurantId   
	 * @return: List<AcceptOrderApiModel>
	 */
	@RequestMapping(value = "/upcomingaccept", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel acceptUpcomingOrder(int restaurantId){
		Log4jUtil.info("已处理非当天的订单列表接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.acceptUpcomingOrder(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: totalAmount
	 * @Description: 当天营业总金额
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@RequestMapping(value = "/totalAmount", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel totalAmount(int restaurantId) {
		Log4jUtil.info("当天营业总金额接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.totalAmount(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/** 
	 * @Title: getRestaurantMoreById
	 * @Description: 通过商家id查找商家详细信息（more）
	 * @param:    商家id
	 * @return: EvaluateMoreApiModel
	 */
	@RequestMapping(value = "/shopinformore", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getRestaurantMoreById(String restaurantId) {
		Log4jUtil.info("通过商家id查找商家详细信息（more）接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("opentime", openTimeService.getOpenTime(Long.parseLong(restaurantId)));
			map.put("evaluate", evaluateService.getEvaluateByRestaurantId(Long.parseLong(restaurantId)));
			
			if(viewRestaurantService.getRestaurantScore(Long.parseLong(restaurantId)).getScore() != null){
				BigDecimal b = new BigDecimal(viewRestaurantService.getRestaurantScore(Long.parseLong(restaurantId)).getScore());//商家评分  
				double totalscore = b.setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();//保留小数后一位
				map.put("totalscore", totalscore);
			} else {
				map.put("totalscore", 0);
			}
			map.put("about",restaurantsService.getRestaurantMoreById(Long.parseLong(restaurantId)).getFeatures());
			ram.setBody(map);
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getDineinOrder
	 * @Description: 返回商家同意的预定订单
	 * @param:    restaurantId
	 * @return: List<OrderHeader>
	 */ 
	@RequestMapping(value = "/dineinorder", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getDineinOrder(int restaurantId) {
		Log4jUtil.info("返回商家同意的预定订单接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(orderServicr.getDineinOrder(restaurantId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: outOfDistance
	 * @Description: 外卖距离是否超过了商家最大外卖距离
	 * @param:    String restaurantId,String addressId
	 * @return: ResultApiModel flag-1超出外卖距离  1不超出
	 */ 
	@RequestMapping(value = "/outOfDistance", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel outOfDistance(String restaurantId,String addressId) {
		Log4jUtil.info("外卖距离是否超过了商家最大外卖距离接口==>"+"restaurantId="+restaurantId+"addressId="+addressId);
		ResultApiModel ram = new ResultApiModel();
		Restaurants restaurants = restaurantsService.getRestaurantsById(Long.parseLong(restaurantId));
		ConsumersAddress consumersAddress = consumersAddressService.getAddressById(Long.parseLong(addressId));
		CartHeader ch = cartHeaderService.getCartHeaderByConsumerId(Integer.parseInt(consumersAddress.getConsumers().getId()+""));
		if(restaurants.getRestaurantLat() != null && restaurants.getRestaurantLng() != null && 
				consumersAddress.getLat() != null && consumersAddress.getLng() != null){
			double distance = CommonUtil.getDistance(consumersAddress.getLat(), consumersAddress.getLng(), restaurants.getRestaurantLng(), restaurants.getRestaurantLat());
			if (distance <= restaurants.getDistance() && ch.getDishFee() >= restaurants.getDeliverPrice()) {
				ram.setFlag(1);
			}
			else if (distance > restaurants.getDistance()) {
				ram.setFlag(-1);
				ram.setResultMessage(MessageConstant.OUTOFDISTANCE);
			}
			else if (ch.getDishFee() < restaurants.getDeliverPrice()) {
				ram.setFlag(-1);
				ram.setResultMessage("Min delivery order price is "+restaurants.getDeliverPrice());
			}
		} else {
			ram.setResultMessage("You choose to address the lack of latitude and longitude");
			ram.setFlag(-1);
		}
		return ram;
	}
	
	/**
	 * @Title: outOfDistance
	 * @Description: 外卖距离是否超过了商家最大外卖距离
	 * @param:    String restaurantId,String addressId
	 * @return: ResultApiModel flag-1超出外卖距离  1不超出
	 */ 
	@RequestMapping(value = "/raddress", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel restaurantsAddress(Long restaurantId) {
		Log4jUtil.info("外卖距离是否超过了商家最大外卖距离接口==>"+"restaurantId="+restaurantId);
		ResultApiModel ram = new ResultApiModel();
		if(restaurantId!=null){
			Restaurants restaurants = restaurantsService.getRestaurantsById(restaurantId);
			if (restaurants!=null) {
				ram.setFlag(1);
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("address", restaurants.getRestaurantAddress());
				ram.setBody(map);
			}
			else{
				ram.setFlag(-1);
				ram.setResultMessage("The restaurant has't Address");
			}
		}
		return ram;
	}
	
	
	private String convertHtml1(OrderDetailsApiModel item){
		StringBuffer sb=new StringBuffer();
		sb.append("<!DOCTYPE html>");
		sb.append("<html>");
		sb.append("<head>");
		sb.append("</head>");
		sb.append("<body style=\"font-family: 'Helvetica Neue',Helvetica,Arial,sans-serif;font-size: 14px;line-height: 1.428571429;	color: #333;background-color: #fff;\">");
		sb.append("<table align='center'  style='width: 800px;margin-bottom: 20px;background-color: transparent;border-collapse: collapse;border-spacing: 0;border-color: gray;'>  	 ");
		sb.append("<thead>");
		sb.append("<tr>");
		String tmp="";
		if(item.getOrderDate().contains("\n")){
			tmp=item.getOrderDate().substring(0, item.getOrderDate().indexOf("\n"));
		}
		sb.append("<th colspan='6' align='left'>"+item.getRestaurantName()+" at "+tmp+" </th>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<th colspan='6' align='left'>repeipt: </th>");
		sb.append("</tr>");
		if(item.getItem()!=null&&item.getItem().size()>0){
			sb.append("<tr>");
			sb.append("<th width='1%'></th>");
			sb.append("<th width='8%'>Qty</th>");
			sb.append("<th width='20%'>Dish Name</th>");
			sb.append("<th width='20%'>Item</th>");
			sb.append("<th width='20%'>Special</th>");
			sb.append("<th width='20%'>Total</th>");
			sb.append("</tr>");
			sb.append("</thead>");
			sb.append("<tbody style='border-top:1px solid #CCCCCC'>");
			//start
			DecimalFormat    df   = new DecimalFormat("######0.00");   
			for (CartItemApiModel subItem : item.getItem()) {
				sb.append("<tr>");
				sb.append("<td></td>");
				sb.append("<td>"+subItem.getNum()+"</td>");
				sb.append("<th>"+subItem.getDishName()+"</th>");
				String str="";
				for (CartDishGarnishApiModel subsubitem : subItem.getSubItem()) {
					str+="<p style = 'line-height:0.1'>"+subsubitem.getGarnishName()+"</p>";
				}
				sb.append("<th style='display: table-cell;vertical-align: inherit;'>"+str+"</th>");
				if(subItem.getInstruction()!=null&&subItem.getInstruction()!=""){
					sb.append("<th>"+subItem.getInstruction()+"</th>");	
				}else{
					sb.append("<th></th>");
				}
				sb.append("<th>"+df.format(subItem.getUnitprice())+"</th>");
				sb.append("</tr>");
			}
			//end			
			sb.append("<tr  style='border-top:1px solid #CCCCCC'>");
			sb.append("<th align='right' colspan='6' class='text-right'>");
			sb.append("<p style = 'line-height:0.1;margin-top:5px;margin-right: 70px;'>Subtotal:$ "+df.format(item.getTotal())+"</p>");
			sb.append("<p style = 'line-height:0.1;margin-right: 70px;'>Delivery:$ "+df.format(item.getDeliveryfee())+"</p>");
			sb.append("<p style = 'line-height:0.1;margin-right: 70px;'>Tax:$ "+df.format(item.getTax())+"</p>");
			sb.append("<p style = 'line-height:0.1;margin-right: 70px;'>Tip: "+df.format(item.getTip())+"</p>");
			sb.append("<p style = 'line-height:0.1;margin-right: 70px;'>Total: "+df.format(item.getAmount())+"</p>");
			sb.append("</th>");
			sb.append("</tr>");
		}
		sb.append("<tr  style='border-top:1px solid #CCCCCC'>");
		sb.append("<th align='left' colspan='6'>");
		sb.append("<p style = 'line-height:0.1;margin-top:5px'>Name: "+item.getConsumersName()+"</p>");
		sb.append("<p style = 'line-height:0.1'>Phone: "+item.getConsumersIdPhone()+"</p>");
		if(item.getChargeId()!=null&&item.getChargeId()!=""){
			sb.append("<p style = 'line-height:0.1'>Charge No: "+item.getChargeId()+"</p>");
		}
		sb.append("<p style = 'line-height:0.1'>Order Type: "+item.getOrderTypeStr()+"</p>");
		sb.append("</th>");
		sb.append("</tr>");
		sb.append("</tbody>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>	");				
					
		
		return sb.toString();
	}
}
