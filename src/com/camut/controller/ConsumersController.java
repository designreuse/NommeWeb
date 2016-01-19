package com.camut.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.GlobalConstant.DISCOUNT_TYPE;
import com.camut.framework.constant.MessageConstant;
import com.camut.framework.constant.MessageConstant.PASSWORD_VALIDATION;
import com.camut.model.CardEntity;
import com.camut.model.CartHeader;
import com.camut.model.Consumers;
import com.camut.model.ConsumersAddress;
import com.camut.model.NommeDiscount;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.CartHeaderApiModel;
import com.camut.model.api.CartItemApiModel;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.EvaluateApiModel;
import com.camut.model.api.OrderDetailsApiModel;
import com.camut.pageModel.PageConsumersAddress;
import com.camut.pageModel.PageDiscount;
import com.camut.pageModel.PageEvaluate;
import com.camut.pageModel.PageFavourites;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageOrderHeader;
import com.camut.pageModel.PageSelectItemReservationOrder;
import com.camut.service.CartDishGarnishService;
import com.camut.service.CartHeaderService;
import com.camut.service.CartItemService;
import com.camut.service.CartService;
import com.camut.service.ConsumersAddressService;
import com.camut.service.ConsumersFavoritesService;
import com.camut.service.ConsumersService;
import com.camut.service.DiscountService;
import com.camut.service.DishService;
import com.camut.service.DistancePriceService;
import com.camut.service.EvaluateService;
import com.camut.service.NommeDiscountService;
import com.camut.service.OpenTimeService;
import com.camut.service.OrderItemService;
import com.camut.service.OrderService;
import com.camut.service.RestaurantsService;
import com.camut.utils.CommonUtil;
import com.camut.utils.GetLatLngByAddress;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MD5Util;
import com.camut.utils.StringUtil;
import com.camut.utils.ValidationUtil;

@Controller("ConsumersController")
@RequestMapping("/consumers")
public class ConsumersController {
	
	@Autowired private DishService dishService;
	@Autowired private CartService cartService;
	@Autowired private CartItemService cartItemService;
	@Autowired private CartHeaderService cartHeaderService;
	@Autowired private CartDishGarnishService cartDishGarnishService;
	@Autowired private OrderService orderService;
	@Autowired private DiscountService discountService; 
	@Autowired private ConsumersAddressService consumersAddressService;
	@Autowired private ConsumersService consumersService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private DistancePriceService distancePriceService;
	@Autowired private OpenTimeService openTimeService;//orderItemService
	@Autowired private OrderItemService orderItemService;
	@Autowired private EvaluateService evaluateService;
	@Autowired private ConsumersFavoritesService consumersFavoritesService;
	@Autowired private NommeDiscountService nommeDiscountService;
	
	/**
	 * @Title: getServiceSideCurrentTime
	 * @Description: 获取当前服务器时间
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getServiceSideCurrentTime",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage getServiceSideCurrentTime(){
		PageMessage pm = new PageMessage();
		String strcurrentTime = new Date().getTime()+"";
		pm.setErrorMsg(strcurrentTime);
		return pm;
	}
	
	/**
	 * @Title: addCart
	 * @Description: 添加购物车
	 * @param: @param cartHeader
	 * @param: @param request
	 * @param: @throws Exception
	 * @return PageMessage  
	 */
	@RequestMapping(value="/addCart",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage addCart(String cartHeader, HttpServletRequest request) throws Exception{
		int temp = cartService.addWebCart(cartHeader);
		PageMessage pm = new PageMessage();
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: updateCartOrderType
	 * @Description: 点击订单类型按钮，更新购物车的orderType
	 * @param: @param cartId
	 * @param: @param orderType
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="/updateCartOrderType",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage updateCartOrderType(String cartId, String orderType){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		if(StringUtil.isNotEmpty(cartId) && StringUtil.isNotEmpty(orderType)){
			CartHeader cartHeader2 = cartHeaderService.getCartHeaderById(Long.parseLong(cartId));
			if(cartHeader2!=null){
				cartHeader2.setOrderType(Integer.parseInt(orderType));
				int temp = cartHeaderService.updateCartHeader(cartHeader2);		
				if(temp>0){
					pm.setSuccess(true);
				}
			}
		}
		return pm;
	}
	
	
	
	
	/**
	 * @Title: showCart
	 * @Description: 通过设备号和距离显示购物车小页面，经纬度用于计算送餐费
	 * @param: @param distance
	 * @param: @param model
	 * @param: @param session
	 * @param: @param request
	 * @param: @throws Exception
	 * @return String  
	 */
	@RequestMapping(value="/showCart", method = RequestMethod.POST)
	public String showCart(Double consumerLng, Double consumerLat,String consumerUuid, Model model, HttpSession session,HttpServletRequest request) throws Exception{
		CartHeader cartHeader2 = new CartHeader();
		if(consumerUuid!=null){
			cartHeader2 = cartHeaderService.getCatrHeaderByConsumerUuid(consumerUuid);
		}
		if(cartHeader2!=null){
			//刷新时设置该购物车不使用任何优惠券
			boolean flag12 = false;
			if(cartHeader2 != null && cartHeader2.getDiscountId()!=null){//有购物车，并且购物车里面有优惠券ID
				cartHeader2.setDiscountId(null);
				flag12 = true;
			}
			if(flag12){
				cartHeaderService.updateCartHeader(cartHeader2);//getCartHeaderByMobileToken(mobileToken);
			}
			String consumerUuid2 = ""; 
			if(consumerUuid!=null){
				consumerUuid2=consumerUuid;
			}
			CartHeaderApiModel cartHeaderApiModel = cartService.getCartHeaderApiModelByConsumerUuid(consumerUuid2);//consumerLng, consumerLat, 
			if(cartHeaderApiModel!=null){
				if(cartHeaderApiModel.getTax()>0){
					cartHeaderApiModel.setTax(Math.floor(cartHeaderApiModel.getTax()*100+0.5)/100.0);
				}
				if(cartHeaderApiModel.getItem()!=null && cartHeaderApiModel.getItem().size() == 0){
					cartHeaderApiModel.setOrderType(0);//如果购物车条目为空时设置购物车订单类型为0
				}else{
					cartHeaderApiModel.setOrderType(cartHeader2.getOrderType());
				}
				cartHeaderApiModel.setAllTotal(cartHeaderApiModel.getTotal()+cartHeaderApiModel.getTax());
				cartHeaderApiModel.setCartId(cartHeader2.getId());
				
				model.addAttribute("cartHeader", cartHeaderApiModel);
			}
		}
		return "home/cart";
	}
	
	/**
	 * @Title: deleteCartItem
	 * @Description: 删除购物车条目（购物车中的一个菜品）
	 * @param: @param cartItemId
	 * @param: @param model
	 * @param: @param session
	 * @return PageMessage  
	 */
	@RequestMapping(value="/deleteCartItem", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteCartItem(String cartItemId, Model model, HttpSession session, HttpServletRequest request){
		PageMessage pm = new PageMessage();
		int temp = 0;
		if(StringUtil.isNotEmpty(cartItemId)){
			temp = cartService.deleteCatrItem(cartItemId);
		}
		if(temp>0){
			
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	
	/**
	 * @Title: getOneCartItem
	 * @Description: 获取购物车中一道菜品的详细信息 
	 * @param: @param cartItemId
	 * @return CartItemApiModel  
	 */
	@RequestMapping(value="/getOneCartItem", method = RequestMethod.POST)
	@ResponseBody
	public CartItemApiModel getOneCartItem(String cartItemId){
		if(StringUtil.isNotEmpty(cartItemId)){
			CartItemApiModel catrItem = cartItemService.getCatrItemById(Long.parseLong(cartItemId));
			return catrItem;
		}
		return null;
	}
	
	/**
	 * @Title: editCartItem
	 * @Description: 保存修改购物车中的一个菜品
	 * @param: @param cartHeader
	 * @param: @param request
	 * @return PageMessage  
	 */
	@RequestMapping(value="editCartItem", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage editCartItem(String cartHeader, HttpServletRequest request){
		int temp = 0;
		if(StringUtil.isNotEmpty(cartHeader)){
			temp = cartItemService.editCartItem(cartHeader);
		}
		PageMessage pm = new PageMessage();
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: clearCart
	 * @Description: 清除购物车信息
	 * @param: @param request
	 * @param: @throws Exception
	 * @return PageMessage  
	 */
	@RequestMapping(value="clearCart", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage clearCart(String consumerUuid, HttpServletRequest request) throws Exception{
		int temp = 0;
		if(consumerUuid!=null){
			temp=cartService.deleteCartByConsumerUuid(consumerUuid);
		}
		PageMessage pm = new PageMessage();
		if (temp > 0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: addReservation
	 * @Description: 新增订桌reservation订单
	 * @param: @param jsonResObj
	 * @return PageMessage  
	 */
	@RequestMapping(value="addReservation", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addReservation(String jsonResObj){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(jsonResObj)){
			int temp = orderService.addReservation(jsonResObj);//返回值为保存的订单的id
			if(temp>0){
				pm.setSuccess(true);
				pm.setFlag(temp);//设置保存的orderHeader的id并返回给前端页面
			}else{
				pm.setSuccess(false);
			}
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getUnpaidReservationOrders
	 * @Description: 获取未点菜付款的订桌订单
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return List<PageOrderHeader>  
	 */
	@RequestMapping(value="getUnpaidReservationOrders", method=RequestMethod.POST)
	@ResponseBody
	public List<PageSelectItemReservationOrder> getUnpaidReservationOrders(String consumerUuid,String restaurantUuid,String currentReservationOrderNumber){
		List<PageSelectItemReservationOrder> orderHeaderList = new ArrayList<PageSelectItemReservationOrder>();
		if(StringUtil.isNotEmpty(restaurantUuid) && StringUtil.isNotEmpty(consumerUuid)){
			orderHeaderList = orderService.getUnpaidReservationOrders(consumerUuid, restaurantUuid);
		}
		if(orderHeaderList!=null &&orderHeaderList.size()>0){
			List<PageSelectItemReservationOrder> orderHeaderList2 = new ArrayList<PageSelectItemReservationOrder>(); 
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			//long nowTime = (new Date().getTime())+(1000*60*60);//筛选当前时间一个小时以后的订桌订单
			Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
			long nowTime = localTime.getTime();
			for (int i=0;i< orderHeaderList.size(); i++){
				PageSelectItemReservationOrder pageOrderHeader = orderHeaderList.get(i);
				if(pageOrderHeader.getItemSize()==0){
					long orderTime = pageOrderHeader.getOrderDate().getTime();
					//Date d =  pageOrderHeader.getOrderDate();
					//String time = format.format(d);  
					//pageOrderHeader.setStrOrderDate(time);
					//如果有当前订单号传进来 并且要订单的订单号与传入的订单号相等时，且订单时间要大于当前时间的，才给予显示出来
					if( Long.parseLong(currentReservationOrderNumber) == pageOrderHeader.getId() && orderTime<nowTime){
						continue;
					}else{
						orderHeaderList2.add(pageOrderHeader);
						
					}
				}
			}
			
			return orderHeaderList2;
		}else{
			return null;
		}
		
		
	}
	
	/**
	 * @Title: chooseDiscount
	 * @Description: 选择使用一个优惠券
	 * @param: @param oldDiscountId
	 * @param: @param newDiscountId
	 * @return List<PageDiscount>  
	 * @throws Exception 
	 */
	@RequestMapping(value="chooseDiscount", method = RequestMethod.POST)
	@ResponseBody
	public List<PageDiscount> chooseDiscount(String oldDiscountId, String newDiscountId, String consumerUuid, HttpServletRequest request) throws Exception{
		//String ip = GetIpAndMAC.getRemoteIPAddress(request);//获取客户端ip地址
		//String mobileToken = GetIpAndMAC.getMACAddress(ip);//通过ip地址获取客户端MAC地址/,使用MAC地址作为mobileToken
		CartHeader cartHeader = new CartHeader();
		if(StringUtil.isNotEmpty(consumerUuid)){
			cartHeader = cartHeaderService.getCatrHeaderByConsumerUuid(consumerUuid);
		}
		int temp = 0;
		if(cartHeader!=null && StringUtil.isNotEmpty(newDiscountId)){
			cartHeader.setDiscountId(Integer.parseInt(newDiscountId));
			temp = cartHeaderService.updateCartHeader(cartHeader);
		}
		
		List<PageDiscount> pageDiscountList = new ArrayList<PageDiscount>();
		if(temp>0){
			pageDiscountList = discountService.chooseDiscount(oldDiscountId,newDiscountId);
		}
		
		return pageDiscountList;
	}
	
	/**
	 * @Title: updateCatrFee
	 * @Description: 点击checkout 时，保存前台计算出来的菜价，税额，运费，总金额到购物车头
	 * @param: @param dishFee
	 * @param: @param logistics
	 * @param: @param tax
	 * @param: @param total
	 * @param: @param request
	 * @param: @throws Exception
	 * @return PageMessage  
	 */
	@RequestMapping(value="/checkout", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage updateCatrFee (String dishFee,String logistics,String tax,String total,String consumerUuid,String orderTime,String orderId,String orderType, HttpServletRequest request) throws Exception{
		PageMessage pm = new PageMessage();
		CartHeader cartHeader = null;
		if(StringUtil.isNotEmpty(consumerUuid)){
			cartHeader = cartHeaderService.getCatrHeaderByConsumerUuid(consumerUuid);
		}
		//Double dishFee,Double logistics,Double tax,Double total,Integer consumerId,String orderTime,
		if(cartHeader != null){
			if(StringUtil.isNotEmpty(dishFee)){
				cartHeader.setDishFee(Double.parseDouble(dishFee));
			}
			if(StringUtil.isNotEmpty(logistics)){
				cartHeader.setLogistics(Double.parseDouble(logistics));
			}
			if(StringUtil.isNotEmpty(tax)){
				cartHeader.setTax(Double.parseDouble(tax));
			}
			if(StringUtil.isNotEmpty(total)){
				cartHeader.setTotal(Double.parseDouble(total));
			}
			if(StringUtil.isNotEmpty(orderTime)){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date orderTime2 = format.parse(orderTime);
				cartHeader.setOrderTime(orderTime2);
			}
			if(StringUtil.isNotEmpty(orderId)){
				int orderId2 = Integer.parseInt(orderId);
				if(orderId2>0){//说明是reservation的订单，需要设置reservation订单id到购物车头中
					cartHeader.setOrderId(orderId2);
				}
			}
			if(StringUtil.isNotEmpty(orderType)){
				cartHeader.setOrderType(Integer.parseInt(orderType));
			}
			if(cartHeader.getOrderType()==1){
				cartHeader.setLogistics(0.00);
			}
		}
		
		int temp = cartHeaderService.updateCartHeader(cartHeader);
		if(temp>0){
			pm.setSuccess(true);
			pm.setFlag(cartHeader.getId().intValue());
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: showRegistCart
	 * @Description: 获取不能修改的购物车在regise和支付页面显示
	 * @param: @param consumerLng
	 * @param: @param consumerLat
	 * @param: @param model
	 * @param: @param session
	 * @param: @param request
	 * @param: @throws Exception
	 * @return String  
	 */
	@RequestMapping(value="/showRegistCart", method = RequestMethod.POST)
	public String showRegistCart(String consumerUuid, Model model, HttpSession session) throws Exception{
		if(StringUtil.isNotEmpty(consumerUuid)){
			CartHeaderApiModel cartHeader = cartService.getRegistCartHeaderApiModel(consumerUuid);//, consumerLng, consumerLat getPageCartHeaderByMobileToken(mobileToken);
			if(cartHeader!=null){
				model.addAttribute("cartHeader", cartHeader);
				if(cartHeader.getDiscountId()!=null){
					int discountId = cartHeader.getDiscountId();
					PageDiscount pageDiscount = discountService.getDiscountById(discountId);
					if(pageDiscount!=null){
						model.addAttribute("discount", pageDiscount);//优惠券
					}
				}
			}
		}
		return "home/noEditCart";
	}
	
	/**
	 * @Title: getConsumersAddressList
	 * @Description: 获取用户的维护的多个收货地址信息 
	 * @param: @param consumerId
	 * @param: @param session
	 * @param: @return
	 * @param: @throws Exception
	 * @return List<PageConsumersAddress>  
	 */
	@RequestMapping(value="/getConsumersAddressList", method = RequestMethod.POST)
	@ResponseBody
	public List<PageConsumersAddress> getConsumersAddressListInDistance (String consumerUuid, HttpSession session) throws Exception{
		if(StringUtil.isNotEmpty(consumerUuid)){
			//long restaurantId2 = Long.parseLong(restaurantId);
			List<PageConsumersAddress> consumersAddressList = consumersAddressService.getPageConsumersAddressByConsumerUuid(consumerUuid);
			//getConsumersAddressDefault(long consumerId) {
			//List<ConsumersAddressApiModel> apiAddressList = consumersAddressService.getConsumersAddressInDistance(consumerId2, restaurantId2);
			/*List<PageConsumersAddress> consumersAddressList2 = new ArrayList<PageConsumersAddress>();
			if(apiAddressList!=null && apiAddressList.size()>0){
				for(int i=0; i<consumersAddressList.size(); i++){//找出可用的地址
					PageConsumersAddress p = consumersAddressList.get(i);
					for(int j=0; j<apiAddressList.size(); j++){
						ConsumersAddressApiModel c = apiAddressList.get(j);
						if(p.getAddressId() == c.getAddressId()){
							consumersAddressList2.add(p);
						}
					}
				}
			}*/
			if(consumersAddressList!=null && consumersAddressList.size()>0){
				return consumersAddressList;
			}
		}
		return null;
	}
	
	/**
	 * @Title: getLatLng
	 * @Description: 通过省市区街道全地址获取经纬度
	 * @param: @param address
	 * @param: @param subTotal
	 * @param: @param restaurantId
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="getLatLng",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage getLatLng(String address, Double subTotal,String restaurantUuid, HttpSession session){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(address) && subTotal!=null && StringUtil.isNotEmpty(restaurantUuid)){
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				map = GetLatLngByAddress.getCoordinate(address, false);//地址,是否使用代理，默认不使用
			} catch (Exception e) {
				// TODO: handle exception
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.GOOGLE_MAP_NOT_FOUND_ADDRESS);
				return pm;
			}
			if(map.get("status")!=null && map.get("status").toString().equals("OK")){//能获取到经纬度
				Object o=  map.get("result");
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> list = (List<Map<String,Object>>) o;
				Map<String, Object> map2 = list.get(0);
				String lat = map2.get("lat").toString();
				String lng = map2.get("lng").toString();
				//System.out.println("lat:"+lat+" lng:"+lng);
				//session.setAttribute("currentLng", lng);//经度
				//session.setAttribute("currentLat", lat);//纬度
				//String latLngAndFee = lat+"-"+lng;//返回经纬度l
				
				Restaurants restaurants = restaurantsService.getRestaurantsByUuid(restaurantUuid);
				if(restaurants!=null){
					//计算距离
					double distance= CommonUtil.getDistance(Double.parseDouble(lat), Double.parseDouble(lng), restaurants.getRestaurantLng(), restaurants.getRestaurantLat());
					if(distance > restaurants.getDistance()){//实际配送距离超出了最大外送距离，通知前台不可以下单
						pm.setSuccess(false);
						pm.setErrorMsg(MessageConstant.ADDRESS_OUT_OF_DISTANCE);
						return pm;
					}else{
						pm.setSuccess(true);
						double distanceFee = distancePriceService.getOneDistanceByFee(restaurantUuid, subTotal, Double.parseDouble(lng), Double.parseDouble(lat));//restaurants.getDistancePricesSet();
						String latLngAndFee = lat+"=="+lng+"=="+distanceFee;
						//String latLngAndFee = "{\"lat\":"+lat+",\"lng\":"+lng+",\"fee\":"+distanceFee+"}";
						pm.setErrorMsg(latLngAndFee);//设置配送费
					}
				}
			}else{//通过地址无法获取经纬度
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.GOOGLE_MAP_NOT_FOUND_ADDRESS);
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.GOOGLE_MAP_NOT_FOUND_ADDRESS);
		}
		return pm;
	}
	
	/**
	 * @Title: getDeliveryFee
	 * @Description: 获取配送费
	 * @param: @param lng
	 * @param: @param lat
	 * @param: @param subTotal
	 * @param: @param restaurantId
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="getDeliveryFee",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage getDeliveryFee(Double lng,Double lat, Double subTotal,String restaurantUuid){
		PageMessage pm = new PageMessage();
		if(lng!=null&&lat!=null&&subTotal!=null&&StringUtil.isNotEmpty(restaurantUuid)){
			Restaurants restaurants = restaurantsService.getRestaurantsByUuid(restaurantUuid);
			if(restaurants!=null){
				double distance= CommonUtil.getDistance(lat, lng, restaurants.getRestaurantLng(), restaurants.getRestaurantLat());
				if(distance > restaurants.getDistance()){//实际配送距离超出了最大外送距离，通知前台不可以下单
					pm.setSuccess(false);
					return pm;
				}else{
					pm.setSuccess(true);
					double distanceFee = distancePriceService.getOneDistanceByFee(restaurantUuid, subTotal, lng, lat);//restaurants.getDistancePricesSet();
					pm.setErrorMsg(distanceFee+"");
				}
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.ALL_FAILED);
		}
		return pm;
	}
	
	/**
	 * @Title: gotoPayment
	 * @Description: 点完菜转到支付页面
	 * @param: @param jsonCartObj
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="gotoPayment", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage gotoPayment(String jsonCartObj, HttpSession session){
		PageMessage pm = new PageMessage();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JSONUtils.parse(jsonCartObj);
		if(StringUtil.isNotEmpty(map.get("id").toString())){
			CartHeader cartHeader = cartHeaderService.getCartHeaderById(Long.parseLong(map.get("id").toString()));
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			if(cartHeader!=null){
				PageConsumersAddress cartAddress = new PageConsumersAddress();
				if (map.get("name") != null&&map.get("name").toString().length()>0) {
					cartHeader.setPeopleName(map.get("name").toString());
					cartAddress.setConsignee(cartHeader.getPeopleName());
				}
				if (map.get("email") != null&&map.get("email").toString().length()>0) {
					cartHeader.setEmail(map.get("email").toString());
				}
				if (map.get("phone") != null&&map.get("phone").toString().length()>0) {
					cartHeader.setPhone(map.get("phone").toString());
					cartAddress.setPhone(cartHeader.getPhone());
				}
				if (map.get("memo") != null&&map.get("memo").toString().length()>0) {
					cartHeader.setMemo(map.get("memo").toString());
					cartAddress.setMemo(cartHeader.getMemo());
				}
				if(StringUtil.isNotEmpty(map.get("subTotal").toString())){
					cartHeader.setDishFee(Double.parseDouble(map.get("subTotal").toString()));
				}
				if(StringUtil.isNotEmpty(map.get("deliveryFee").toString())){
					cartHeader.setLogistics(Double.parseDouble(map.get("deliveryFee").toString()));
				}
				if(StringUtil.isNotEmpty(map.get("salesTax").toString())){
					cartHeader.setTax(Double.parseDouble(map.get("salesTax").toString()));
				}
				if(StringUtil.isNotEmpty(map.get("amount").toString())){
					cartHeader.setTotal(Double.parseDouble(map.get("amount").toString()));
				}
				
				if(StringUtil.isNotEmpty(map.get("orderType").toString())){
					cartHeader.setOrderType(Integer.parseInt(map.get("orderType").toString()));
				}
				if(Integer.parseInt(map.get("orderType").toString())==1){//delivery订单,设置地址信息
					if (map.get("address") != null&&map.get("address").toString().length()>0) {
						cartHeader.setAddress(map.get("address").toString());
						cartAddress.setFullAddress(cartHeader.getAddress());
					}
					if (map.get("floor") != null&&map.get("floor").toString().length()>0) {
						cartHeader.setFloor(map.get("floor").toString());
						cartAddress.setFloor(cartHeader.getFloor());
					}
					if(StringUtil.isNotEmpty(map.get("lat").toString())){
						cartHeader.setConsumerLat(Double.parseDouble(map.get("lat").toString()));
						cartAddress.setLat(cartHeader.getConsumerLat()+"");
					}
					if(StringUtil.isNotEmpty(map.get("lng").toString())){
						cartHeader.setConsumerLng(Double.parseDouble(map.get("lng").toString()));
						cartAddress.setLng(cartHeader.getConsumerLng()+"");
					}
					if(StringUtil.isNotEmpty(map.get("street").toString())){
						cartAddress.setStreet(map.get("street").toString());
					}
					if(StringUtil.isNotEmpty(map.get("city").toString())){
						cartAddress.setCity(map.get("city").toString());
					}
					if(StringUtil.isNotEmpty(map.get("province").toString())){
						cartAddress.setProvince(map.get("province").toString());
					}
				}
				
				
				if(cartHeader.getOrderType()==1 && StringUtil.isNotEmpty(map.get("isSaveAddress").toString())){
					if(StringUtil.isNotEmpty(map.get("isSaveAddress").toString()) && map.get("isSaveAddress").toString().equals("true")){//说明需要保存地址
						cartAddress.setIsSaveAddress("1");
						long addressId = 0;
						if(StringUtil.isNotEmpty(map.get("addressId").toString())){
							//大于0说明是有Id的需要update，不大于0 说明是要新增
							addressId = Long.parseLong(map.get("addressId").toString());
						}
						ConsumersAddress address = new ConsumersAddress();
						if(addressId>0){
							address = consumersAddressService.getAddressById(addressId);
							if(address!=null){
								address.setId(addressId);
							}else{
								address = new ConsumersAddress();
							}
						}
						Consumers consumers = new Consumers();
						consumers.setUuid(consumerUuid);
						address.setConsumers(consumers);
						//if(StringUtil.isNotEmpty(map.get("isSaveAddress").toString())){
						address.setPhone(StringUtil.removeNonNumberCharacters(cartHeader.getPhone()));
						address.setConsignee(cartHeader.getPeopleName());
						address.setFloor(cartHeader.getFloor());
						address.setFullAddress(cartHeader.getAddress());
						if(StringUtil.isNotEmpty(map.get("lng").toString())){
							address.setLng(Double.parseDouble(map.get("lng").toString()));
					
						}
						if(StringUtil.isNotEmpty(map.get("lat").toString())){
							address.setLat(Double.parseDouble(map.get("lat").toString()));
						}
						if(StringUtil.isNotEmpty(map.get("city").toString())){
							address.setCity(map.get("city").toString());
						}
						if(StringUtil.isNotEmpty(map.get("street").toString())){
							address.setStreet(map.get("street").toString());
						}
						if(StringUtil.isNotEmpty(map.get("province").toString())){
							address.setProvince(map.get("province").toString());
						}
						
						address.setIsdefault(2);
						int temp2 =0;
						if(addressId>0){
							temp2 = consumersAddressService.updateWebConsumersAddress(address, consumerUuid);
						}else{
							temp2 = consumersAddressService.addWebConsumersAddress(address, consumerUuid);
						}
						
						
						if(temp2<=0){//如果返回temp2>0 说明保存地址成功
							pm.setSuccess(false);
							return pm;
						}
					}else{
						cartAddress.setIsSaveAddress("0");
					}
				}
				
				int temp = cartHeaderService.updateCartHeader(cartHeader);
				if(temp>0){//更新成功
					String strAddress = JSON.toJSONString(cartAddress);
					session.setAttribute("cartAddress", strAddress);//保存当前的地址信息到session中，用于如果返回regist页面时加载之前填的这个地址信息
					pm.setSuccess(true);
				}else{
					pm.setSuccess(false);
				}
			}else{
				pm.setSuccess(false);
			}
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: editConsumerBasisInfo
	 * @Description: 用户维护页面---提交修改用户基础信息
	 * @param: @param context
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="editConsumerBasisInfo", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage editConsumerBasisInfo(String context,HttpSession session ){
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)JSONUtils.parse(context);
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
		//if(map.get("consumerId")!=null && map.get("consumerId").toString().length()>0){
			Consumers consumers = consumersService.getConsumersByUuid(consumerUuid);
			if(consumers!=null){
				if(map.get("firstName")!=null && map.get("firstName").toString().length()>0){
					consumers.setFirstName(map.get("firstName").toString());
				}
				if(map.get("lastName")!=null && map.get("lastName").toString().length()>0){
					consumers.setLastName(map.get("lastName").toString());
				}
				if(map.get("email")!=null && map.get("email").toString().length()>0){
					consumers.setEmail(map.get("email").toString());
				}
				if(map.get("password")!=null && map.get("password").toString().length()>0){
					String md5Passworld= MD5Util.md5(map.get("password").toString());
					consumers.setPassword(md5Passworld);
				}
				if(map.get("specislRequest")!=null && map.get("specislRequest").toString().length()>0){
					consumers.setMemo(map.get("specislRequest").toString());
				}
				if(map.get("phone")!=null && map.get("phone").toString().length()>0){
					consumers.setPhone(StringUtil.removeNonNumberCharacters(map.get("phone").toString()));
				}
				if( map.get("password")!=null){
					PASSWORD_VALIDATION validationResult = ValidationUtil.validatePassword( map.get("password").toString());
					if(validationResult != PASSWORD_VALIDATION.VALID){
						pm.setSuccess(false);
						pm.setFlag(GlobalConstant.PASSWORD_ERROR);
						pm.setErrorMsg(validationResult.getMessage());
						return pm;
					}
				}
				int temp = consumersService.updateConsumersForNomme(consumers);
				if(temp>0){
					Consumers consumers2 = consumersService.getConsumersByUuid(consumerUuid);
					if(consumers2!=null){
						session.setAttribute("consumer", consumers2);
					}
					pm.setSuccess(true);
				}
			}
		//}
		return pm;
	}
	
	/**
	 * @Title: validateOrderTime
	 * @Description: 验证订单时间是否在营业时间内 
	 * @param: @param orderTime
	 * @param: @param restaurantId
	 * @param: @param orderType
	 * @param: @return
	 * @return PageMessage  
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="validateOrderTime", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage validateOrderTime(String orderTime, String restaurantUuid, String orderType){
		PageMessage pm = new PageMessage( );
		pm.setSuccess(false);
		if(StringUtil.isNotEmpty(orderTime)&&StringUtil.isNotEmpty(restaurantUuid)&&StringUtil.isNotEmpty(orderType)){
			Date orderDate = new Date(orderTime);
			//int restaurantId2 = Integer.parseInt(restaurantId);
			int orderType2 = Integer.parseInt(orderType);
			if(orderType2!=0){//购物车为空的情况除外
				int temp = openTimeService.orderDateAtOpenTime(orderDate, restaurantUuid, orderType2);//orderDateAtOpenTime(orderDate,restaurantId,orderType);
					//-1不在 1在
				if(temp>0){
					pm.setSuccess(true);
				}
			}
		}
		return pm;
	}
	
	/**
	 * @Title: getPastOrders
	 * @Description: 用户维护页面---当前订单
	 * @param: @param pf
	 * @param: @param orderType 1: Delivery和pickup的订单，2: rservation(dinein)的订单
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getCurrentAndPastOrders", method=RequestMethod.POST)
	public String getCurrentOrders(PageFilter pf,int statusType, Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null ){
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			if(StringUtil.isNotEmpty(consumerUuid)){
				PageMessage ordersInfo = orderService.getPastOrderInfoByConsumerUuid(consumerUuid,statusType,pf);
				if(ordersInfo!=null){
					model.addAttribute("ordersInfo", ordersInfo);
				}
			}
		}
		return "user/currentOrders";
	}
	
	/**
	 * @Title: getReservationOrderPage
	 * @Description: 用户维护页面---历史订单
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
/*	@RequestMapping(value="getPastOrders", method=RequestMethod.POST)
	public String getPastOrders(PageFilter pf, int statusType, Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null ){
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			if(StringUtil.isNotEmpty(consumerUuid)){
				PageMessage ordersInfo = orderService.getPastOrderInfoByConsumerUuid(consumerUuid,statusType,pf);
				if(ordersInfo!=null){
					model.addAttribute("ordersInfo", ordersInfo);
				}
			}
		}
		return "user/currentOrders";
	}*/
	
	/**
	 * @Title: editBasisInfoPage
	 * @Description: 用户维护页面---基础信息维护
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String
	 */
	@RequestMapping(value="editBasisInfoPage", method=RequestMethod.POST)
	public String editBasisInfoPage(Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null){
			
		}
		return "user/editBasisInfo";
	}
	
	/**
	 * @Title: validateExistAndSaveEmail
	 * @Description: 验证邮箱是否被别的用户占用，如果没被占用就保存这个新的邮箱到当前用户
	 * @param: @param email
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="validateExistAndSaveEmail", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage validateExistAndSaveEmail(String email, Model model, HttpSession session){
		PageMessage pm = new PageMessage();
		boolean flag = false;
		if(StringUtil.isNotEmpty(email)){
			Consumers consumers = consumersService.getConsumersByLoginName(email);
			if(consumers==null){
				flag = true;
			}else{
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.EMAIL_USED);
				return pm;
			}
		}
		if(flag){//未被使用
			int consumerId =((Consumers)session.getAttribute("consumer")).getId().intValue();
			Consumers consumers = consumersService.getConsumersById(consumerId);
			int temp = 0;
			if(consumers!=null){
				consumers.setEmail(email);
				temp = consumersService.updateConsumersForNomme(consumers);
				
			}
			if(temp>0){//修改成功
				pm.setSuccess(true);
			}else{//修改失败
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.UPDATE_EMAIL_FAILURE);
			}
		}else{
			pm.setSuccess(true);
		}
		return pm;
	}
	
	/**
	 * @Title: getOneOrderDetail
	 * @Description: 用户维护页面---显示一个订单详情
	 * @param: @param orderId
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getOneOrderDetail", method=RequestMethod.POST)
	public String getOneOrderDetail(String orderId, Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null){
			int consumerId =((Consumers)session.getAttribute("consumer")).getId().intValue();
			if(consumerId>0 && StringUtil.isNotEmpty(orderId)){
				long orderId2 = Long.parseLong(orderId);
				OrderDetailsApiModel order = orderItemService.selectHistoryOrderWebUsed(orderId2);
				//PageOrderHeader pageOrderHeader = orderService.getPageOrderHeaderByOrderId(orderId2);
				model.addAttribute("order",order);
				int temp =  evaluateService.existEvaluateByOrderId(Integer.parseInt(orderId));
				if(temp<0){//没有被评论
					model.addAttribute("haveEvaluate","0");
				}else{
					model.addAttribute("haveEvaluate","1");
				}
			}
		}
		return "user/orderDetail";
	}

	/**
	 * @Title: getAddressPage
	 * @Description: 用户维护页面---地址维护
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getAddressPage", method=RequestMethod.POST)
	public String getAddressPage(Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null){
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			String restaurantUuid = "0";
			if(StringUtil.isNotEmpty(consumerUuid)){
				List<PageConsumersAddress> addressList = consumersAddressService.getPageConsumersAddressListByConsumerUuid(consumerUuid);
				model.addAttribute("addressList",addressList);
			}
		}
		return "user/address";
	}
	/**
	 * @Title: getPaymentPage
	 * @Description: 用户维护页面---支付
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getPaymentPage", method=RequestMethod.POST)
	public String getPaymentPage(Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null){
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();  
			Consumers consumers = consumersService.getConsumersByUuid(consumerUuid);
			if(consumers.getStripeCustomerId()!=null){//之前保存过
				List<CardEntity> list = CommonUtil.listAllCards(consumers.getStripeCustomerId());
				model.addAttribute("cards", list);
			}
		}
		return "user/payment";
	}
	
	/**
	 * @Title: getFavouritesPage
	 * @Description: 用户维护页面---收藏夹维护
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getFavouritesPage", method=RequestMethod.POST)
	public String getFavouritesPage(PageFilter pf, Model model, HttpSession session){
		if(session.getAttribute("consumer")!=null){
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			List<PageFavourites> favouritesList = consumersFavoritesService.getFavouriteListByConsumerUuid(consumerUuid, pf);
			int total = consumersFavoritesService.countTotalByConsumerUuid(consumerUuid);
			model.addAttribute("favouritesList",favouritesList);
			model.addAttribute("total",total);
		}
		return "user/favourites";
	}
	
	/**
	 * @Title: repeatOrder
	 * @Description: 重复下单
	 * @param: @param orderId
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="repeatOrder", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage repeatOrder(String orderId,HttpSession session){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(orderId)){
			int orderId2 = Integer.parseInt(orderId);
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			int temp1 = cartService.deleteCartByConsumerUuid(consumerUuid);
			if (temp1>0){//清空购物车成功
				OrderHeader orderHeader = orderService.getOrderById(orderId2); 
				int temp2 = 0;
				if(orderHeader != null){
					temp2 = orderService.repeatOrderWebUsed(orderId2, consumerUuid);//没有mobiletoken，传入一个空字符串
				}
				if(temp2>0 && orderHeader != null){
					pm.setSuccess(true);
					pm.setStringFlag(orderHeader.getRestaurantUuid());
				}else{
					pm.setSuccess(false);
					pm.setErrorMsg("Repeat failed!");
				}
				
			}else{
				pm.setSuccess(false);
				pm.setErrorMsg("Repeat failed, add dish to new order failed!");
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg("Repeat failed, order number not find!");
		}
		return pm;
	}
	
	/**
	 * @Title: rating
	 * @Description: 发表评论
	 * @param: @param orderId
	 * @param: @param restaurantId
	 * @param: @param stars
	 * @param: @param review
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="rating", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage rating(String orderId,String restaurantUuid, String stars, String review, HttpSession session){
		PageMessage pm = new PageMessage();
		String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
		Consumers consumers = consumersService.getConsumersByUuid(consumerUuid);
		if(StringUtil.isNotEmpty(orderId) && StringUtil.isNotEmpty(restaurantUuid) && StringUtil.isNotEmpty(stars) && consumers!=null){
			EvaluateApiModel e = new EvaluateApiModel();
			e.setConsumerUuid(consumerUuid);
			if(StringUtil.isNotEmpty(review)){
				e.setContent(review);
			}
			e.setCreatetime(new Date());
			e.setFirstName(consumers.getFirstName());
			e.setLastName(consumers.getLastName());
			e.setRestaurantUuid(restaurantUuid);
			e.setOrderHeaderId(Integer.parseInt(orderId));
			e.setScore(stars);
			e.setStatus(1);
			int temp = evaluateService.addEvaluate(e);
			if(temp>0){//添加成功
				pm.setSuccess(true);
			}else{
				pm.setSuccess(false);
			}
		}else{//传入数据不准确
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: verifyPassword
	 * @Description: 修改登录密码 验证输入的原密码是否正确
	 * @param: @param password
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="verifyPassword", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage verifyPassword(String password, HttpSession session){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		PASSWORD_VALIDATION validationResult = ValidationUtil.validatePassword(password);
		if(validationResult != PASSWORD_VALIDATION.VALID){
			pm.setFlag(GlobalConstant.PASSWORD_ERROR);
			pm.setErrorMsg(validationResult.getMessage());
			return pm;
		}
		int consumerId =((Consumers)session.getAttribute("consumer")).getId().intValue();
		Consumers consumers = consumersService.getConsumersById(consumerId);
		if(consumers!=null && StringUtil.isNotEmpty(password)){
			password = MD5Util.md5(password);
			if(consumers.getPassword().equals(password)){
				pm.setSuccess(true);
			}
		}
		return pm;
	}
	
	/**
	 * @Title: getFavouritesList
	 * @Description: 获取用户收藏列表
	 * @param: @param pf
	 * @param: @param model
	 * @param: @param session
	 * @param: @return
	 * @return List<PageFavourites>  
	 */
	@RequestMapping(value="getFavouritesList", method=RequestMethod.POST)
	@ResponseBody
	public List<PageFavourites> getFavouritesList(PageFilter pf, Model model, HttpSession session){
		String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
		List<PageFavourites> favouritesList = consumersFavoritesService.getFavouriteListByConsumerUuid(consumerUuid, pf);
		return favouritesList;
	}
	
	
	/**
	 * @Title: deleteConsumerFavourite
	 * @Description: 删除用户收藏
	 * @param: @param favouriteId
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="deleteConsumerFavourite", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteConsumerFavourite(String favouriteId, HttpSession session){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		if(StringUtil.isNotEmpty(favouriteId)){
			long favouriteId2 = Long.parseLong(favouriteId);
			int temp = consumersFavoritesService.deleteFavorites(favouriteId2);
			if(temp>0){
				pm.setSuccess(true);
			}
		}
		return pm;
	}
	
	/**
	 * @Title: deleteAddress
	 * @Description: 删除用户一个地址
	 * @param: @param addressId
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="deleteAddress", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteAddress(String addressId){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		if(StringUtil.isNotEmpty(addressId)){
			long addressId2 = Long.parseLong(addressId);
			int temp = consumersAddressService.deleteConsumersAddressById(addressId2);
			if(temp>0){
				pm.setSuccess(true);
			}
		}
		return pm;
	}
	
	/**
	 * @Title: getAddressDetail
	 * @Description:获取用户的一个地址的详细信息 
	 * @param: @param addressId
	 * @param: @return
	 * @return PageConsumersAddress  
	 */
	@RequestMapping(value="getAddressDetail", method=RequestMethod.POST)
	@ResponseBody
	public PageConsumersAddress getAddressDetail(String addressId){
		if(StringUtil.isNotEmpty(addressId)){
			PageConsumersAddress pca = consumersAddressService.getPageAddressById(Long.parseLong(addressId));
			if(pca!=null){
				return pca;
			}else{
				return null;
			}
		}
		return null;
	}
	
	/**
	 * @Title: addOrUpdateAddress
	 * @Description: 新增或修改用户地址信息
	 * @param: @param addressId
	 * @param: @param floor
	 * @param: @param street
	 * @param: @param city
	 * @param: @param province
	 * @param: @param phone
	 * @param: @param name
	 * @param: @param checkDefault
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="addOrUpdateAddress", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addOrUpdateAddress(String addressId, String floor, String street ,String city, String province, String phone, String name, String checkDefault, HttpSession session){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		String lat = "";
		String lng = "";
		//if(StringUtil.isNotEmpty(floor) && StringUtil.isNotEmpty(street) && StringUtil.isNotEmpty(city) && StringUtil.isNotEmpty(province)){
		if(!StringUtil.isNotEmpty(floor)){
			floor="";
		}
		
		String fullAddress = street+" "+floor+" "+city+" "+province;
		Map<String, Object> map = GetLatLngByAddress.getCoordinate(fullAddress, false);//地址,是否使用代理，默认不使用
		//if(map.size()==0){
			if(map.get("status").toString().equals("OK")){//能获取到经纬度
				Object o=  map.get("result");
				@SuppressWarnings("unchecked")
				List<Map<String,Object>> list = (List<Map<String,Object>>) o;
				Map<String, Object> map2 = list.get(0);
				lat = map2.get("lat").toString();
				lng = map2.get("lng").toString();
			}else{//通过地址无法获取经纬度
				return pm;
			}
			String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
			if(StringUtil.isNotEmpty(addressId)){//修改地址
				ConsumersAddress ca = consumersAddressService.getAddressById(Long.parseLong(addressId));
				if(ca!=null){
					ca.setFloor(floor);
					ca.setStreet(street);
					ca.setCity(city);
					ca.setProvince(province);
					ca.setPhone(StringUtil.removeNonNumberCharacters(phone));
					ca.setLat(Double.parseDouble(lat));
					ca.setLng(Double.parseDouble(lng));
					ca.setConsignee(name);
					ca.setFullAddress(street+","+floor+","+city+","+province);
					if(checkDefault.equals("true")){
						ca.setIsdefault(1);
					}else{
						ca.setIsdefault(2);
					}
					int temp = consumersAddressService.updateWebConsumersAddress(ca,consumerUuid);
					if(temp>0){
						pm.setSuccess(true);
					}
				}
			}else{//新增地址
				ConsumersAddress ca = new ConsumersAddress();
				Consumers consumers = new Consumers();
				consumers.setUuid(consumerUuid);
				ca.setConsumers(consumers);
				ca.setFloor(floor);
				ca.setFloor(floor);
				ca.setStreet(street);
				ca.setCity(city);
				ca.setProvince(province);
				ca.setPhone(StringUtil.removeNonNumberCharacters(phone));
				ca.setConsignee(name);
				ca.setLat(Double.parseDouble(lat));
				ca.setLng(Double.parseDouble(lng));
				if(checkDefault.equals("true")){
					ca.setIsdefault(1);
				}else{
					ca.setIsdefault(2);
				}
				int temp = consumersAddressService.addWebConsumersAddress(ca,consumerUuid);
				if(temp>0){
					pm.setSuccess(true);
				}
			}
		return pm;
	}
	
	@RequestMapping(value="getRestaurantOpenTimeOfOneDay", method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addOrUpdateAddress(String orderDay, String restaurantUuid, String type){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		if (StringUtil.isNotEmpty(orderDay) && StringUtil.isNotEmpty(restaurantUuid) && StringUtil.isNotEmpty(type)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			//Date day = new Date(orderDay);
			Date day = null;
			try {
				day = format.parse(orderDay);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return pm;
			}
			int type2 = Integer.parseInt(type);
			String[] openTimesArr = openTimeService.getOpenTimeByOrderDate(day, restaurantUuid, type2); 
			String openTimesStr = "";
			if(openTimesArr.length>0){
				for (int i = 0; i < openTimesArr.length; i++) {
					openTimesStr = openTimesStr + openTimesArr[i]+"==";
				}
				openTimesStr = openTimesStr.substring(0, openTimesStr.lastIndexOf("=="));
				pm.setSuccess(true);
				pm.setErrorMsg(openTimesStr);
			}
		}
		return pm;
	}
	
	@RequestMapping(value="loadReviews", method=RequestMethod.POST)
	public String loadReviews(String restaurantUuid, String offset, String limit,Model model){
		List<EvaluateApiModel> reviewsList = new ArrayList<EvaluateApiModel>();
		if(StringUtil.isNotEmpty(restaurantUuid) && StringUtil.isNotEmpty(offset) && StringUtil.isNotEmpty(limit)){
			reviewsList = evaluateService.getEvaluatePagingByRestaurantId(restaurantUuid, Integer.parseInt(offset), Integer.parseInt(limit));
		}
		if(reviewsList!=null && reviewsList.size()>0){
			model.addAttribute("reviewsList", reviewsList);
			model.addAttribute("count", evaluateService.getCount());
		}
		return "/home/reviews";
	}
	
	/**
	 * @Title: submitPromoCode
	 * @Description: get promo code and check if valid 
	 * @param: String consumerUuid
	 * @param: String couponCode
	 * @return PageMessage
	 */
	@RequestMapping(value = "/submitPromoCode", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage submitPromoCode(String consumerUuid, String couponCode) throws Exception {
		// AHGPBTZU
		PageMessage pm = new PageMessage();
		pm.setErrorMsg("Cart not found.");
		pm.setSuccess(false);
		if (StringUtil.isNotEmpty(consumerUuid)) {
			CartHeader cartHeader = cartHeaderService.getCartHeaderByConsumerUuid(consumerUuid);
			if (cartHeader != null) {
				OrderHeader orderHeader = orderService.CartHeaderToOrderHeader(cartHeader.getId());
				if (orderHeader == null) {
					pm.setErrorMsg("Order not found.");
					return pm;
				}
				pm.setErrorMsg("Coupon code is invalid.");
				List<NommeDiscount> nommeDiscountList = nommeDiscountService.getNommeDiscountByCouponCode(couponCode);
				if (nommeDiscountList != null) {
					if (nommeDiscountList.size() > 0) {
						NommeDiscount nommeDiscount = nommeDiscountList.get(0);

						// check date
						long localTime = consumersAddressService.getCurrentLocalTimeForConsumer(consumerUuid).getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy, hh:mm aaa", Locale.CANADA);
						long startTime = sdf.parse(nommeDiscount.getStartTime()).getTime();
						long endTime = sdf.parse(nommeDiscount.getEndTime()).getTime();
						if (localTime < startTime || localTime > endTime) {
							pm.setErrorMsg("Coupon code is expired.");
							return pm;
						}

						// check number of use
						if (nommeDiscount.getMaxUses() <= nommeDiscount.getUsedCount()) {
							pm.setErrorMsg("Coupon code has already used.");
							return pm;
						}

						//final check if the coupon has valid info
						if (nommeDiscount.getType() != null && nommeDiscount.getDiscount() != null) {
							if (nommeDiscount.getType() == DISCOUNT_TYPE.PERCENTAGE_COUPON.getValue()) {

								// TODO: update CartHeader here

								pm.setErrorMsg("");
								pm.setSuccess(true);
							}
						} 
					}
				}
			}
		}
		return pm;
	}
}
