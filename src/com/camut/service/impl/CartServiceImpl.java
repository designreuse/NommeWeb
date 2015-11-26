/**
 * 
 */
package com.camut.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.druid.support.json.JSONUtils;
import com.camut.dao.AreasDao;
import com.camut.dao.CartDishGarnishDao;
import com.camut.dao.CartHeaderDao;
import com.camut.dao.CartItemDao;
import com.camut.dao.DiscountDao;
import com.camut.dao.DishDao;
import com.camut.dao.DishGarnishDao;
import com.camut.dao.DistancePriceDao;
import com.camut.dao.RestaurantsDao;
import com.camut.dao.ViewRestaurantDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.CartDishGarnish;
import com.camut.model.CartDishGarnishId;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.model.Discount;
import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.model.GarnishItem;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.model.ViewRestaurant;
import com.camut.model.api.CartDishGarnishApiModel;
import com.camut.model.api.CartHeaderApiModel;
import com.camut.model.api.CartItemApiModel;
import com.camut.model.api.DishGarnishApiModel;
import com.camut.model.api.ResultApiModel;
import com.camut.pageModel.PageDiscount;
import com.camut.service.CartDishGarnishService;
import com.camut.service.CartService;
import com.camut.service.OrderService;
import com.camut.utils.StringUtil;

/**
 * @ClassName CartServiceImpl.java
 * @author wangpin
 * @createtime Jul 22, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class CartServiceImpl implements CartService {

	@Autowired private CartHeaderDao cartHeaderDao;
	@Autowired private RestaurantsDao restaurantsDao;
	@Autowired private AreasDao areasDao;
	@Autowired private CartItemDao cartItemDao;
	@Autowired private CartDishGarnishDao cartDishGarnishDao;
	@Autowired private DishDao dishDao;
	@Autowired private DishGarnishDao dishGarnishDao;
	@Autowired private ViewRestaurantDao viewRestaurantDao;
	@Autowired private DistancePriceDao distancePriceDao;
	@Autowired private DiscountDao discountDao;
	@Autowired private OrderService orderService;
	@Autowired private CartDishGarnishService cartDishGarnishService;

	/**
	 * @Title: getCartHeaderApiModel
	 * @Description: 获取购物车信息
	 * @param: String，int
	 * @return: CartHeaderApiModel
	 */
	@Override
	public CartHeaderApiModel getCartHeaderApiModel(String mobileToken, String consumerUuid) {
		CartHeader cartHeader = null;
		if(StringUtil.isNotEmpty(consumerUuid)){
			cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(consumerUuid);
		}
		if (cartHeader!=null) {//购物车存在
			CartHeaderApiModel cartHeaderApiModel = new CartHeaderApiModel();
			BeanUtils.copyProperties(cartHeader, cartHeaderApiModel);
			if (cartHeaderApiModel.getOrderId()==null) {
				cartHeaderApiModel.setOrderId(0);
			}
			List<CartItemApiModel> cartItemApiModels = new ArrayList<CartItemApiModel>();
			int num = 0;
			for(CartItem cartItem : cartHeader.getCartItems()){//遍历购物车条目
				CartItemApiModel cartItemApiModel = new CartItemApiModel();
				BeanUtils.copyProperties(cartItem, cartItemApiModel);
				Dish dish = dishDao.getDishById((long)cartItem.getDishId());
				if(dish!=null){
					cartItemApiModel.setDishName(dish.getEnName());
					cartItemApiModel.setPhotoUrl(dish.getPhotoUrl());
					cartItemApiModel.setCartItemId(cartItem.getId().intValue());
					cartItemApiModel.setDishId(cartItem.getId().intValue());
						List<CartDishGarnishApiModel> apiModels = new ArrayList<CartDishGarnishApiModel>();
						// 遍历购物车配菜表
						for (CartDishGarnish cartDishGarnish : cartItem.getCartDishGarnishs()) {
							CartDishGarnishApiModel cartDishGarnishApiModel = new CartDishGarnishApiModel();
							cartDishGarnishApiModel.setCount(cartDishGarnish.getGarnishNum());
							cartDishGarnishApiModel.setGarnishItemId(cartDishGarnish.getGarnishItem().getId().intValue());
							if(cartDishGarnish.getGarnishNum() > 1){
								cartDishGarnishApiModel.setGarnishName(cartDishGarnish.getGarnishItem().getGarnishName() + "(" + cartDishGarnish.getGarnishNum() + ")");
							} else {
								cartDishGarnishApiModel.setGarnishName(cartDishGarnish.getGarnishItem().getGarnishName());
							}
							apiModels.add(cartDishGarnishApiModel);
							DishGarnish dishGarnish = dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(cartItem.getDishId(), cartDishGarnishApiModel.getGarnishItemId());
							Collections.sort(apiModels);
							cartItemApiModel.setSubItem(apiModels);
						}
				}				
				cartItemApiModels.add(cartItemApiModel);
				num += cartItem.getNum();
				cartHeaderApiModel.setDishNum(num);
			}
			Collections.sort(cartItemApiModels);//排序
			cartHeaderApiModel.setCartId(cartHeader.getId());
			cartHeaderApiModel.setTotal(StringUtil.convertLastDouble(cartHeader.getDishFee()));
			cartHeaderApiModel.setItem(cartItemApiModels);
			
			//获取优惠券
			//Discount tmp=null;
			int cartHeaderDiscountId =0;
			if(cartHeaderApiModel.getDiscountId()==null){
				cartHeaderDiscountId =0;
			}else{
				cartHeaderDiscountId=cartHeaderApiModel.getDiscountId();
			}			 
			List<Discount> discountList =  discountDao.getDiscountByRestaurantUuid(cartHeader.getRestaurantUuid(), cartHeader.getOrderType(),cartHeaderApiModel.getTotal());// cartHeader.getDishFee());
			if(discountList.size()>0){
				List<PageDiscount> pageDiscountList = new ArrayList<PageDiscount>();
				int orderType = cartHeader.getOrderType();
				for (Discount discount : discountList){
					/*if(cartHeader.getDiscountId()!=null&&cartHeader.getDiscountId()==discount.getId().intValue()){
						tmp=discount;
					}*/
					//如果当前订单价格达到某个优惠券需要的订单价格
					if(cartHeaderApiModel.getTotal()>=discount.getConsumePrice()){
						long nowTime = new Date().getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy, hh:mm aaa",Locale.CANADA);
						try {
							long startTime = sdf.parse(discount.getStartTime()).getTime();
							long endTime = sdf.parse(discount.getEndTime()).getTime();
							if(startTime<nowTime && endTime>nowTime && orderType == discount.getOrderType()){//当时间在有效期内，并且所属的订单类型等于当前购物车的订单类型
								PageDiscount pageDiscount = new PageDiscount();
								BeanUtils.copyProperties(discount, pageDiscount);
								if(cartHeaderDiscountId == pageDiscount.getId()){
									pageDiscount.setIsSelect(1);
								}else{
									pageDiscount.setIsSelect(0);
								}
								pageDiscountList.add(pageDiscount);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				cartHeaderApiModel.setDiscountList(pageDiscountList);
			}
			// 获取餐厅
			ViewRestaurant restaurants = viewRestaurantDao.getRestaurantsByRestaurantUuid(cartHeader.getRestaurantUuid());
			cartHeaderApiModel.setRestaurantName(restaurants.getRestaurantName());
			if (cartHeader.getDiscountId()!=null) {
				Discount tmp = discountDao.getDiscount(cartHeader.getDiscountId());
				if(tmp!=null){
					if(tmp.getType()==GlobalConstant.DISCOUNT_TYPE_1){
						cartHeaderApiModel.setTotal(StringUtil.convertLastDouble(cartHeader.getDishFee()-tmp.getPrice()));
					}else if(tmp.getType()==GlobalConstant.DISCOUNT_TYPE_2){
						cartHeaderApiModel.setTotal(StringUtil.convertLastDouble(cartHeader.getDishFee()-cartHeader.getDishFee()*(tmp.getDiscount()/100)));
					}
				}
				
			}
			
			return cartHeaderApiModel;
		}
		return null;
	}
	
	/**
	 * @Title: getCartHeaderApiModel
	 * @Description: 获取购物车信息
	 * @param: String，int
	 * @return: CartHeaderApiModel
	 */
	@Override
	public CartHeaderApiModel getCartHeaderApiModelByConsumerUuid(String consumerUuid) {//double consumerLng, double consumerLat, 
		if (StringUtil.isNotEmpty(consumerUuid)) {
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(consumerUuid);
			if (cartHeader == null) {
				return null;
			}
			CartHeaderApiModel cartHeaderApiModel = new CartHeaderApiModel();
			BeanUtils.copyProperties(cartHeader, cartHeaderApiModel);
			List<CartItemApiModel> cartItemApiModels = new ArrayList<CartItemApiModel>();
			double totalPrice=0;
			for(CartItem cartItem : cartHeader.getCartItems()){//遍历购物车条目
				double aDishFee = 0;//存储一个购物车条目菜品的价格
				double aDishGarnishFee = 0;//存储一个菜品下的所有配菜金额
				CartItemApiModel cartItemApiModel = new CartItemApiModel();
				BeanUtils.copyProperties(cartItem, cartItemApiModel);
				Dish dish = dishDao.getDishById((long)cartItem.getDishId());
				if(dish!=null){
					cartItemApiModel.setDishName(dish.getEnName());
					cartItemApiModel.setUnitprice(dish.getPrice());
					cartItemApiModel.setPhotoUrl(dish.getPhotoUrl());
					cartItemApiModel.setCartItemId(cartItem.getId().intValue());
					aDishFee = dish.getPrice();
					//totalPrice += dish.getPrice();//*cartItem.getNum(); 
				}
				List<CartDishGarnishApiModel> cartDishGarnishApiModels = new ArrayList<CartDishGarnishApiModel>();
				// 遍历购物车配菜表
				for (CartDishGarnish cartDishGarnish : cartItem.getCartDishGarnishs()) {
					CartDishGarnishApiModel cartDishGarnishApiModel = new CartDishGarnishApiModel();
					cartDishGarnishApiModel.setCount(cartDishGarnish.getGarnishNum());
					cartDishGarnishApiModel.setGarnishItemId(cartDishGarnish.getGarnishItem().getId().intValue());
					cartDishGarnishApiModel.setGarnishName(cartDishGarnish.getGarnishItem().getGarnishName());
					cartDishGarnishApiModels.add(cartDishGarnishApiModel);
					DishGarnish dishGarnish = dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(cartItem.getDishId(), cartDishGarnishApiModel.getGarnishItemId());
					if(dishGarnish!=null){
						aDishGarnishFee += dishGarnish.getAddprice()*cartDishGarnish.getGarnishNum();
						//totalPrice += dishGarnish.getAddprice()*cartDishGarnish.getGarnishNum();
					}
				}
				Collections.sort(cartDishGarnishApiModels);//排序购物车配菜
				aDishFee = (aDishFee + aDishGarnishFee) * cartItem.getNum();
				//得到购物车的所有菜品和配菜的总价
				totalPrice += aDishFee;
				cartItemApiModel.setaDishTotalFee(aDishFee);
				cartItemApiModel.setSubItem(cartDishGarnishApiModels);
				
				cartItemApiModels.add(cartItemApiModel);
			}
			Collections.sort(cartItemApiModels);
			/*if(cartHeader.getDiscountId()!=null){
				int discountId = cartHeader.getDiscountId();
				Discount discount = discountDao.getDiscount(discountId);//getDiscountById(discountId);
				if(discount.getType()==1){//现金抵用
					//double dishTotal = cartHeader.getTotal();
					//cartHeader.setTotal(dishTotal-Discount.getPrice());
					totalPrice = totalPrice - discount.getPrice();
				}else if(discount.getType()==2){//打折券
					totalPrice = totalPrice - totalPrice*(discount.getDiscount()/100);
				}
			}*/
			
			cartHeaderApiModel.setTotal(totalPrice);
			cartHeaderApiModel.setDiscountId(cartHeader.getDiscountId());
			cartHeaderApiModel.setItem(cartItemApiModels);
			
			
			//获取优惠券
			//List<PageDiscount> pageDiscountList = new ArrayList<PageDiscount>();
			//List<Discount> discountList =  discountDao.getAllDiscounts(restaurant);
			List<Discount> discountList =  discountDao.getDiscountByRestaurantUuid(cartHeader.getRestaurantUuid(), cartHeader.getOrderType(),cartHeaderApiModel.getTotal());// cartHeader.getDishFee());
			if(discountList.size()>0){
				List<PageDiscount> pageDiscountList = new ArrayList<PageDiscount>();
				int orderType = cartHeader.getOrderType();
				for (Discount discount : discountList){
					//如果当前订单价格达到某个优惠券需要的订单价格
					if(totalPrice>=discount.getConsumePrice()){
						long nowTime = new Date().getTime();
						SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy, hh:mm aaa",Locale.CANADA);
						try {
							long startTime = sdf.parse(discount.getStartTime()).getTime();
							long endTime = sdf.parse(discount.getEndTime()).getTime();
							if(startTime<nowTime && endTime>nowTime && orderType == discount.getOrderType()){//当时间在有效期内，并且所属的订单类型等于当前购物车的订单类型
								PageDiscount pageDiscount = new PageDiscount();
								BeanUtils.copyProperties(discount, pageDiscount);
								pageDiscountList.add(pageDiscount);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				cartHeaderApiModel.setDiscountList(pageDiscountList);
			}
			// 获取餐厅
			ViewRestaurant restaurants = viewRestaurantDao.getRestaurantsByRestaurantUuid(cartHeader.getRestaurantUuid());
			cartHeaderApiModel.setRestaurantName(restaurants.getRestaurantName());
			cartHeaderApiModel.setTax(totalPrice*(restaurants.getGst() + restaurants.getPst()));
			//获取送餐费
			/*if(cartHeader.getOrderType()==1){
				List<DistancePrice> list=  distancePriceDao.getDistancePrice(restaurants.getId());
				//判断用户是否传去了经纬度
				Double distance = 0.0;
				if(consumerLng >0 && consumerLat >0){
					distance = CommonUtil.getDistance(restaurants.getRestaurantLat(), restaurants.getRestaurantLng(), consumerLng, consumerLat);
				}else {
					distance = CommonUtil.getDistance(restaurants.getRestaurantLat(), restaurants.getRestaurantLng(), cartHeader.getConsumerLng(), cartHeader.getConsumerLat());
				}
				for (DistancePrice distancePrice : list) {
					if (distance <= distancePrice.getEndDistance() && distance > distancePrice.getStartDistance()) {
						if (totalPrice >= distancePrice.getOrderPrice()) {// 达到订单价格
							cartHeaderApiModel.setLogistics((double) distancePrice.getUpPrice());
						} else {// 没有达到订单价格
							cartHeaderApiModel.setLogistics((double) distancePrice.getNotupPrice());
						}
					}else{
						cartHeaderApiModel.setLogistics(0.00);
					}
				}
			}else{
				cartHeaderApiModel.setLogistics(0.00);
			}*/
			double tax = restaurants.getGst();
			//double l = cartHeaderApiModel.getLogistics();
			double allTotal = totalPrice+totalPrice*(restaurants.getGst() + restaurants.getPst());//+cartHeaderApiModel.getLogistics();
			cartHeaderApiModel.setAllTotal(allTotal);
			return cartHeaderApiModel;
		}
		return null;
	}
	
	/**
	 * @Title: getCartHeaderApiModel
	 * @Description: 获取购物车信息
	 * @param: String，int
	 * @return: CartHeaderApiModel
	 */
	@Override
	public CartHeaderApiModel getRegistCartHeaderApiModel(String consumerUuid) {//, Double consumerLng, Double consumerLat
		if (StringUtil.isNotEmpty(consumerUuid)) {
			CartHeader cartHeader = cartHeaderDao.getWebCartHeaderByConsumerUuid(consumerUuid);
			CartHeaderApiModel cartHeaderApiModel = new CartHeaderApiModel();
			if (cartHeader==null) {
				return null;
			}
			BeanUtils.copyProperties(cartHeader, cartHeaderApiModel);
			cartHeaderApiModel.setCartId(cartHeader.getId());
			cartHeaderApiModel.setLogistics(cartHeader.getLogistics());//附加BUG修改
			List<CartItemApiModel> cartItemApiModels = new ArrayList<CartItemApiModel>();
			//double totalPrice=0;
			for (CartItem cartItem : cartHeader.getCartItems()){//遍历购物车条目
				//double aDishFee = 0;//存储一个购物车条目菜品的价格
				//double aDishGarnishFee = 0;//存储一个菜品下的所有配菜金额
				CartItemApiModel cartItemApiModel = new CartItemApiModel();
				List<CartDishGarnish> garnishList = cartDishGarnishService.getCartDishGarnishList(cartItem.getId());
				if(garnishList!=null &&garnishList.size()>0 ){
					List<CartDishGarnishApiModel> cdamList = new ArrayList<CartDishGarnishApiModel>();
					for (CartDishGarnish cartDishGarnish : garnishList) {
						CartDishGarnishApiModel cdam = new CartDishGarnishApiModel();
						cdam.setCount(cartDishGarnish.getGarnishNum());
						cdam.setGarnishName(cartDishGarnish.getGarnishItem().getGarnishName());
						cdamList.add(cdam);
					}
					Collections.sort(cdamList);
					cartItemApiModel.setSubItem(cdamList);
				}
				//cartDishGarnishService.
				BeanUtils.copyProperties(cartItem, cartItemApiModel);
				Dish dish = dishDao.getDishById((long)cartItem.getDishId());
				if(dish!=null){
					double aDishGarnishFee = dish.getPrice();
					cartItemApiModel.setDishName(dish.getEnName());
					cartItemApiModel.setUnitprice(dish.getPrice());
					cartItemApiModel.setPhotoUrl(dish.getPhotoUrl());
					cartItemApiModel.setCartItemId(cartItem.getId().intValue());
					cartItemApiModels.add(cartItemApiModel);
					Set<CartDishGarnishApiModel> cartDishGarnishApiModels = new HashSet<CartDishGarnishApiModel>();
					// 遍历购物车配菜表
					if(cartItem.getCartDishGarnishs()!=null &&cartItem.getCartDishGarnishs().size()>0){
						for (CartDishGarnish cartDishGarnish : cartItem.getCartDishGarnishs()) {
							CartDishGarnishApiModel cartDishGarnishApiModel = new CartDishGarnishApiModel();
							cartDishGarnishApiModel.setCount(cartDishGarnish.getGarnishNum());
							cartDishGarnishApiModel.setGarnishItemId(cartDishGarnish.getGarnishItem().getId().intValue());
							cartDishGarnishApiModel.setGarnishName(cartDishGarnish.getGarnishItem().getGarnishName());
							cartDishGarnishApiModels.add(cartDishGarnishApiModel);
							DishGarnish dishGarnish = dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(cartItem.getDishId(), cartDishGarnishApiModel.getGarnishItemId());
							if(dishGarnish!=null){
								aDishGarnishFee += dishGarnish.getAddprice()*cartDishGarnish.getGarnishNum();
								//totalPrice += dishGarnish.getAddprice()*cartDishGarnish.getGarnishNum();
							}
						}
					}
					double aDishFee = aDishGarnishFee * cartItem.getNum();
					cartItemApiModel.setaDishTotalFee(aDishFee);
				}
				
			}
			Collections.sort(cartItemApiModels);
			
			if(cartHeader.getDiscountId() != null){
				int discountId = cartHeader.getDiscountId();
				Discount discount = discountDao.getDiscount(discountId);//getDiscountById(discountId);
				if(discount.getType()==3){//增加菜品
					Dish dish = dishDao.getDishById(discount.getDishId());
					CartItemApiModel cartItemApiModel = new CartItemApiModel();
					cartItemApiModel.setPhotoUrl(dish.getPhotoUrl());
					cartItemApiModel.setDishName(dish.getEnName());
					cartItemApiModel.setUnitprice(0.00);
					cartItemApiModel.setaDishTotalFee(0.00);
					cartItemApiModel.setNum(1);
					cartItemApiModels.add(cartItemApiModel);
					
				}
				 List<PageDiscount> pageDiscountList = new ArrayList<PageDiscount>();
				 PageDiscount discount2 = new PageDiscount();
				 discount2.setContent(discount.getContent());
				 pageDiscountList.add(discount2);
				 cartHeaderApiModel.setDiscountList(pageDiscountList);
			}
			
			cartHeaderApiModel.setTotal(cartHeader.getDishFee());
			cartHeaderApiModel.setTax(cartHeader.getTax());
			cartHeaderApiModel.setLogistics(cartHeader.getLogistics());
			cartHeaderApiModel.setAllTotal(cartHeader.getTotal());
			
			cartHeaderApiModel.setDiscountId(cartHeader.getDiscountId());
			cartHeaderApiModel.setItem(cartItemApiModels);
			Restaurants restaurant = restaurantsDao.getRestaurantsByUuid(cartHeader.getRestaurantUuid());
			if(restaurant!=null){
				cartHeaderApiModel.setRestaurantName(restaurant.getRestaurantName());
			}
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String strOrderDate = format.format(cartHeader.getOrderTime());
			cartHeaderApiModel.setOrderDate(strOrderDate);
			return cartHeaderApiModel;
		}
		return null;
	}
	
	/**
	 * @Title: addAndCheckCart
	 * @Description: 增加购物车信息
	 * @param: String context
	 * @return: ResultApiModel 1成功 -1失败
	 */
	@SuppressWarnings("unchecked")
	public ResultApiModel addAndCheckCart(String context) {
		ResultApiModel ram = new ResultApiModel();
		Map<String, Object> map = (Map<String, Object>) JSONUtils.parse(context);
		CartHeader cartHeader = null;
		if (map.get("consumerUuid")!=null) {//用户已经登录
			cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(map.get("consumerUuid").toString());
		}
		else{
			if (map.get("mobileToken") != null) {
				String mobileToken = map.get("mobileToken").toString();
				// 根据设备号获取购物车头
				cartHeader = cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
			}
		}
		
		if (map.get("item")!= null) {
			Map<String, Object> mapItem = (Map<String, Object>)map.get("item");
			if (cartHeader == null) {// 不存在
				cartHeader = new CartHeader();
				if (map.get("mobileToken") != null){
					cartHeader.setMobileToken(map.get("mobileToken").toString());
				}
				if (map.get("orderType") != null) {
					cartHeader.setOrderType(Integer.parseInt(map.get("orderType").toString()));
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("Have no OrderType!");
					return ram;
				}
				
				cartHeader.setDishFee((double) 0);
				
				//dine in
				if (Integer.parseInt(map.get("orderType").toString())==3&&map.get("item")!=null) {
					if (map.get("orderId") != null) {
						cartHeader.setOrderId(Integer.parseInt(map.get("orderId").toString()));
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("Have no OrderId!");
						return ram;
					}
					//取订单中数据放入购物车中
					OrderHeader header = orderService.getOrderById(Long.parseLong(map.get("orderId").toString()));
					cartHeader.setRestaurantUuid(header.getRestaurantUuid());
					cartHeader.setConsumerUuid(header.getConsumers().getUuid());
					
				}
				else{//delivery or pick-up
					if (map.get("consumerUuid") != null && StringUtil.isNotEmpty(map.get("consumerUuid").toString())) {
						cartHeader.setConsumerUuid(map.get("consumerUuid").toString());
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("Have no ConsumerId!");
						return ram;
					}
					if (map.get("restaurantUuid") != null) {
						cartHeader.setRestaurantUuid(map.get("restaurantUuid").toString());
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage("Have no RestaurantUuid!");
						return ram;
					}
				}
				
				//cartHeader.setDishFee(StringUtil.convertLastDouble(Double.parseDouble(mapItem.get("unitprice").toString())));
				int flag = cartHeaderDao.addCartHeader(cartHeader);
				if (flag == -1) {
					ram.setFlag(-1);
					ram.setResultMessage(MessageConstant.ALL_FAILED);
					return ram;
				}
				cartHeader.setId((long) flag);

			}
			else{//购物车之前存在
				//判断订单类型 商家id是否与之前一直
				if (map.get("orderType")!=null && map.get("restaurantUuid")!=null) {
					if (!(map.get("restaurantUuid").toString()).equals(cartHeader.getRestaurantUuid())) {
						ram.setFlag(-2);
						ram.setResultMessage(MessageConstant.CARTERROR);
						return ram;
					}
					if (Integer.parseInt(map.get("orderType").toString())==3) {
						if ( map.get("orderId")!=null) {
							cartHeader.setOrderId(Integer.parseInt(map.get("orderId").toString()));
						}
						else{
							ram.setFlag(-1);
							ram.setResultMessage("Have no OrderId!");
							return ram;
						}
					}
					if (Integer.parseInt(map.get("orderType").toString())!=cartHeader.getOrderType()) {
						cartHeader.setOrderType(Integer.parseInt(map.get("orderType").toString()));
					}
				}
				else{
					ram.setFlag(-1);
					ram.setResultMessage("Have no OrderType or RestaurantUuid!");
					return ram;
				}
			}
			
			CartItem cartItem = new CartItem();
			if (mapItem.get("dishId")!= null) {
				cartItem.setDishId(Integer.parseInt(mapItem.get("dishId").toString()));
			}
			if (mapItem.get("num")!= null) {
				cartItem.setNum(Integer.parseInt(mapItem.get("num").toString()));
			}
			if (mapItem.get("instruction")!= null) {
				cartItem.setInstruction(mapItem.get("instruction").toString());
			}
			if (mapItem.get("unitprice")!=null) {
				cartItem.setUnitprice(Double.parseDouble(mapItem.get("unitprice").toString()));
			}
			
			cartItem.setcartHeader(cartHeader);
			List<DishGarnishApiModel> dishGarnishApiModels = dishGarnishDao.getDishGarnishApiModelByDishIdForCheck(cartItem.getDishId());
				if(mapItem.get("subItem")!=null){
					//List<DishGarnishApiModel> dishGarnishApiModels = dishGarnishDao.getDishGarnishApiModelByDishId(cartItem.getDishId());
					List<Map<String,Object>> list = (List<Map<String, Object>>)mapItem.get("subItem");
					out:
						for(DishGarnishApiModel apiModel : dishGarnishApiModels){
							if (apiModel.getIsMust()==1) {
								String[] strs = apiModel.getGarnishItemId().split(",");
								for(String id:strs){
									for(Map<String, Object> mapList : list){
										if (Integer.parseInt(id)==Integer.parseInt(mapList.get("garnishItemId").toString())) {
											continue out;
										}
									}
								}
								ram.setFlag(-1);
								ram.setResultMessage("You must select "+apiModel.getGarnishHeaderName());
								return ram;
							}
						}
						//判断选的配菜数量是否超过最大数量
					for (DishGarnishApiModel apiModel : dishGarnishApiModels) {
						int maxCount = 0;
						for(Map<String, Object> mapList : list){
							String[] strs = apiModel.getGarnishItemId().split(",");
							for(String id:strs){
								if (Integer.parseInt(id)==Integer.parseInt(mapList.get("garnishItemId").toString())){
									maxCount += Integer.parseInt(mapList.get("count").toString());
									if (maxCount>apiModel.getMaxCount()) {
										ram.setFlag(-1);
										ram.setResultMessage("Maximum "+apiModel.getMaxCount()+" items/types of side! from " + apiModel.getGarnishHeaderName());
										return ram;
									}
								}
							}
						}
					}	
					
					//判断通过
					int flag1 = cartItemDao.addCartItem(cartItem);
					if (flag1!=-1) {
						cartItem.setId((long)flag1);
						for(Map<String,Object> mapList:list){
							CartDishGarnish cartDishGarnish = new CartDishGarnish();
							GarnishItem garnishItem = new GarnishItem();//
							cartDishGarnish.setCartItem(cartItem);
							if (mapList.get("count")!=null) {
								cartDishGarnish.setGarnishNum(Integer.parseInt(mapList.get("count").toString()));
							}
							if(mapList.get("garnishItemId")!=null){
								garnishItem.setId(Long.parseLong(mapList.get("garnishItemId").toString()));
							}
							cartDishGarnish.setGarnishItem(garnishItem);
							CartDishGarnishId cartDishGarnishId = new CartDishGarnishId();
							cartDishGarnishId.setCartItemId(cartItem.getId().intValue());
							cartDishGarnishId.setGarnishItemId(Integer.parseInt(mapList.get("garnishItemId").toString()));
							cartDishGarnish.setId(cartDishGarnishId);
							int flag2 = cartDishGarnishDao.addCartDishGarnish(cartDishGarnish);
							if (flag2==-1) {
								ram.setFlag(-1);
								ram.setResultMessage(MessageConstant.ALL_FAILED);
								return ram;
							}
						}
					}
					else{
						ram.setFlag(-1);
						ram.setResultMessage(MessageConstant.ALL_FAILED);
						return ram;
					}
					
				}
				else{//subitem为空
					for (DishGarnishApiModel dishGarnishApiModel : dishGarnishApiModels) {
						if (dishGarnishApiModel.getIsMust()==1) {//有必须选的配菜
								ram.setFlag(-1);
								ram.setResultMessage(dishGarnishApiModel.getGarnishHeaderName()+" is require!");
								return ram;
						}
					}
					int flag1 = cartItemDao.addCartItem(cartItem);
					if (flag1==-1) {
						ram.setFlag(-1);
						ram.setResultMessage(MessageConstant.ALL_FAILED);
						return ram;
					}
				}
				//所有的成功之后修改购物车头的总价
				cartHeader.setDishFee(StringUtil.convertLastDouble(cartHeader.getDishFee()+Double.parseDouble(mapItem.get("unitprice").toString())));
				int flag = cartHeaderDao.updateCartHeader(cartHeader);
				if (flag!=1) {
					ram.setFlag(-1);
					ram.setResultMessage(MessageConstant.ALL_FAILED);
					return ram;
				}
				ram.setFlag(1);
				Map<String,Object> mapNum = new HashMap<String, Object>();
				mapNum.put("cartId", cartHeader.getId());
				ram.setBody(mapNum);
				return ram;
		}
		ram.setFlag(-1);
		ram.setResultMessage(MessageConstant.ALL_FAILED);
		return ram;
	};
	

	/**
	 * @Title: addAndCheckCart
	 * @Description: 增加购物车信息
	 * @param: String context
	 * @return: int 1成功 0失败
	 */
	public int addWebCart(String context) {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) JSONUtils.parse(context);
		if (map.get("consumerUuid") != null && StringUtil.isNotBlank(map.get("consumerUuid").toString())) {
			String consumerUuid = map.get("consumerUuid").toString();
			// 根据设备号获取购物车头
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(consumerUuid);
			if (cartHeader == null) {//不存在,创建新的
				cartHeader = new CartHeader();
				cartHeader.setDishFee((double) 0);
				if (map.get("consumerUuid") != null && StringUtil.isNotBlank(map.get("consumerUuid").toString())) {
					cartHeader.setConsumerUuid(map.get("consumerUuid").toString());
				}
				if (map.get("restaurantUuid") != null && StringUtil.isNotBlank(map.get("restaurantUuid").toString())) {
					cartHeader.setRestaurantUuid(map.get("restaurantUuid").toString());
				}
				if (map.get("orderType") != null) {
					cartHeader.setOrderType(Integer.parseInt(map.get("orderType").toString()));
				}
				if (map.get("consumerLng") != null) {
					cartHeader.setConsumerLng(Double.parseDouble(map.get("consumerLng").toString()));
				}
				if (map.get("consumerLat") != null) {
					cartHeader.setConsumerLat(Double.parseDouble(map.get("consumerLat").toString()));
				}
				if (map.get("orderId") != null) {
					cartHeader.setOrderId(Integer.parseInt(map.get("orderId").toString()));
				}
				int flag = cartHeaderDao.addCartHeader(cartHeader);
				if (flag == -1) {
					return -1;
				}
				cartHeader.setId((long) flag);
			}
			else{//找到有购物车头
				boolean flag12 = false;
				if(map.get("restaurantUuid") != null) {
					cartHeader.setRestaurantUuid(map.get("restaurantUuid").toString());
					flag12 = true;
				}
				if(cartHeader.getDiscountId()!=null){
					cartHeader.setDiscountId(null);//增加菜品后修改清楚先前使用的优惠券
					flag12 = true;
				}
				if(flag12){
					cartHeaderDao.updateCartHeader(cartHeader);					
				}
				if(StringUtil.isNotEmpty(map.get("consumerUuid").toString()) && cartHeader.getConsumerUuid()==null){
					cartHeader.setConsumerUuid(map.get("consumerUuid").toString());
					cartHeader.setDiscountId(null);
					int flag = cartHeaderDao.updateCartHeader(cartHeader);
					if (flag == -1) {
						return -1;
					}
				}
			}
			/*if(map.get("type")!=null){//调用方法类型
				if(Integer.parseInt(map.get("type").toString())==1){//需要检查
					//传入参数不能为空
					if (map.get("restaurantId")!= null&&map.get("orderType")!= null) {
						int restaurantId = Integer.parseInt(map.get("restaurantId").toString());
						int orderType = Integer.parseInt(map.get("orderType").toString());
						if(restaurantId!=cartHeader.getRestaurantId()||orderType!=cartHeader.getOrderType()){
							return -2;//传入参数与购物车不符,清空购物车
						}
					}
					else{
						return -1;
					}
				}
			}*/
			
			if (map.get("item")!= null){
				@SuppressWarnings("unchecked")
				Map<String, Object> mapItem = (Map<String, Object>)map.get("item");
				CartItem cartItem = new CartItem();
				if(mapItem.get("dishId")!= null) {
					cartItem.setDishId(Integer.parseInt(mapItem.get("dishId").toString()));//主菜id
					/*Dish dish = dishDao.getDishById(Integer.parseInt(mapItem.get("dishId").toString()));
					if(dish!=null){
						cartItem.setUnitprice(dish.getPrice());//设置主菜单价
					}*/
				}
				if (mapItem.get("num")!= null) {//主菜数量
					cartItem.setNum(Integer.parseInt(mapItem.get("num").toString()));
				}
				if (mapItem.get("instruction")!= null) {// 特殊需求
					cartItem.setInstruction(mapItem.get("instruction").toString());
				}
				if(mapItem.get("unitprice")!= null) {//订单条目总金额（菜价+（配菜*数量））*份数
					cartItem.setUnitprice(Double.parseDouble(mapItem.get("unitprice").toString()));
				}
				if(mapItem.get("instruction")!= null)
				cartItem.setcartHeader(cartHeader);
				int flag1 = cartItemDao.addCartItem(cartItem);
				if (flag1==-1) {
					return -1;
				}
				cartItem.setId((long)flag1);
				if(mapItem.get("subItem")!=null){//获取配菜
					@SuppressWarnings("unchecked")
					List<Map<String,Object>> list = (List<Map<String, Object>>)mapItem.get("subItem");
					for(Map<String,Object> mapList:list){
						CartDishGarnish cartDishGarnish = new CartDishGarnish();
						GarnishItem garnishItem = new GarnishItem();//
						cartDishGarnish.setCartItem(cartItem);
						if (mapList.get("count")!=null) {
							cartDishGarnish.setGarnishNum(Integer.parseInt(mapList.get("count").toString()));
						}
						if(mapList.get("garnishItemId")!=null){
							garnishItem.setId(Long.parseLong(mapList.get("garnishItemId").toString()));
						}
						cartDishGarnish.setGarnishItem(garnishItem);
						CartDishGarnishId cartDishGarnishId = new CartDishGarnishId();
						cartDishGarnishId.setCartItemId(cartItem.getId().intValue());
						cartDishGarnishId.setGarnishItemId(Integer.parseInt(mapList.get("garnishItemId").toString()));
						cartDishGarnish.setId(cartDishGarnishId);
						int flag2 = cartDishGarnishDao.addCartDishGarnish(cartDishGarnish);
						if (flag2==-1) {
							return -1;
						}
					}
				}
				return 1;
			}
		}
		return -1;
	};
	
	/**
	 * @Title: deleteCatrItem
	 * @Description: 删除一条购物车中的菜品
	 * @param: long cartItemId
	 * @return int  
	 */
	public int deleteCatrItem(String cartItemId){
		
		int temp = cartDishGarnishDao.deleteCartDishGarnish(cartItemId+"");
		
		if(temp>0){
			//CartItem cartItem = cartItemDao.getCatrItemById(Long.parseLong(cartItemId));
			int temp2 = cartItemDao.deleteById(Long.parseLong(cartItemId));
			if(temp2>0){
				return 1;
			}
			
		}
		
		return -1;
	}
	
	/**
	 * @Title: deleteCartByConsumerId
	 * @Description: 通过用户Id删除购物车
	 * @param: @return
	 * @return int  
	 */
	public int deleteCartByConsumerUuid(String consumerUuid){
		CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(consumerUuid);
		int temp = 0;
		int temp1 = 0;
		int temp2 = 0;
		if(cartHeader!=null){
			Set<CartItem> cartItems = cartHeader.getCartItems();
			int a = cartItems.size();
			if(a==0){
				return 1;
			}
			String s = "";
			for (CartItem cartItem : cartItems) {
				s += cartItem.getId()+",";
			}
			if(StringUtil.isNotEmpty(s)){
				s = s.substring(0, s.length()-1);
				temp1 = cartDishGarnishDao.deleteCartDishGarnish(s);
			}
			if(temp1>0){
				temp2 = cartItemDao.deleteCartItemByCartHeaderId(cartHeader.getId());
			}
			if(temp2>0){
				temp = cartHeaderDao.deleteCartHeader(cartHeader);
			}
			if(temp>0){
				return 1;
			}else{
				return -1;
			}
		}else{//如果没有购物车也代表清空成功
			return 1;
		}
	}

	/**
	 * @Title: deleteCartByConsumerId
	 * @Description: 删除购物车
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int deleteCartHeader(String mobileToken, String consumerUuid) {
		CartHeader cartHeader = new CartHeader(); 
		if(mobileToken != null && mobileToken.length() > 0){
			cartHeader = cartHeaderDao.getCartHeaderByMobileToken(mobileToken);
		}else {
			cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(consumerUuid);
		}
		if(cartHeader!=null){
			Set<CartItem> cartItems = cartHeader.getCartItems();
			String s = "";
			for (CartItem cartItem : cartItems) {
				s += cartItem.getId()+",";
			}
			if(StringUtil.isNotEmpty(s)){
				s = s.substring(0, s.length()-1);
				cartDishGarnishDao.deleteCartDishGarnish(s);
				cartItemDao.deleteCartItemByCartHeaderId(cartHeader.getId());				
			}	
			cartHeaderDao.deleteCartHeader(cartHeader);
			return 1;
		}
		else{//如果没有购物车也代表清空成功
			return 1;
		}
	}
	
}
