package com.camut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.pageModel.PageDish;
import com.camut.pageModel.PageRestaurant;
import com.camut.pageModel.PageRestaurantsMenu;
import com.camut.service.DishService;
import com.camut.service.DistancePriceService;
import com.camut.service.RestaurantsService;
import com.camut.utils.StringUtil;

@Controller("RestaurantMenuController")
@RequestMapping("/restaurantMenu")
public class RestaurantMenuController {
	
	@Autowired private DishService dishService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private DistancePriceService distancePriceService;
	
	/**
	 * @Title: getRestaurantMenus
	 * @Description: 打开商家首页异步加载商家信息
	 * @param: @param restaurantId
	 * @param: @param isPickup
	 * @param: @return
	 * @return PageRestaurant  
	 */
	@RequestMapping(value="getRestaurantMenus", method=RequestMethod.POST)
	@ResponseBody
	public PageRestaurant getRestaurantMenus (String restaurantUuid, String isPickup){
		if(StringUtil.isNotEmpty(restaurantUuid) &&StringUtil.isNotEmpty(isPickup)){
			//Long id = Long.parseLong(restaurantId);
			//int isPickup2 =Integer.parseInt(isPickup); 
			PageRestaurant pageRestaurant = restaurantsService.getPageRestaurantByUuid(restaurantUuid);
			return pageRestaurant;
		}
		return null;
	}
	
	/**
	 * @Title: getDishes
	 * @Description: 获取商家下面的所有菜品
	 * @param: @param restaurantId
	 * @param: @param isPickup
	 * @param: @param model
	 * @return String  
	 */
	@RequestMapping(value="getDishes", method=RequestMethod.POST)
	public String getDishes(String restaurantUuid, Model model){
		if(StringUtil.isNotEmpty(restaurantUuid)){
			//Long id = Long.parseLong(restaurantId);
			List<PageRestaurantsMenu> prmList = restaurantsService.getRestaurantsMenusByRestaurantsIdAndIsPickup(restaurantUuid);
			model.addAttribute("menuList", prmList);
		}
		return "home/menuList";
	}
	
	
	/**
	 * @Title: getOneDish
	 * @Description: 获取一道菜的信息
	 * @param: @param dishId
	 * @param: @param model
	 * @return String  
	 */
	@RequestMapping(value="getOneDish", method=RequestMethod.POST)
	public String getOneDish(String dishId,Model model){
		if(StringUtil.isNotEmpty(dishId)){
			PageDish pageDish = dishService.getPageDishByDishId(dishId);
			//session.setAttribute("dish", pageDish);
			model.addAttribute("dish", pageDish);
		}
		return "home/dish";
	}
	/**
	 * @Title: getDiscountDish
	 * @Description: 获取折扣信息中的一道菜的信息
	 * @param: @param dishId
	 * @param: @param model
	 * @return String  
	 */
	@RequestMapping(value="getDiscountDish", method=RequestMethod.POST)
	@ResponseBody
	public PageDish getDiscountDish(String dishId,Model model){
		if(StringUtil.isNotEmpty(dishId)){
			PageDish pageDish = dishService.getPageDishByDishId(dishId);
			return pageDish;
		}
		return null;
	}
	//{restaurantId:restaurantId,subTotal:subTotal,consumerLng:consumerLng,consumerLat:consumerLat},
	/**
	 * @Title: chooseDiscount
	 * @Description: 获取订单的配送费
	 * @param: @param restaurantId
	 * @param: @param subTotal
	 * @param: @param consumerLng
	 * @param: @param consumerLat
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getDelivaryFee", method = RequestMethod.POST)
	@ResponseBody
	public String chooseDiscount(String restaurantUuid, String subTotal, String consumerLng, String consumerLat){
		String strDistancePrice = "";
		if(StringUtil.isNotEmpty(restaurantUuid)&&StringUtil.isNotEmpty(subTotal)&&StringUtil.isNotEmpty(consumerLng)&&StringUtil.isNotEmpty(consumerLat)){
			//long restaurantId2 = Long.parseLong(restaurantId);//商家Id
			double subTotal2 = Double.parseDouble(subTotal);//菜品总价
			double consumerLng2 = Double.parseDouble(consumerLng);//经度
			double consumerLat2 = Double.parseDouble(consumerLat);//纬度
			double distancePrice = distancePriceService.getOneDistanceByFee(restaurantUuid,subTotal2,consumerLng2,consumerLat2);
			strDistancePrice = distancePrice+"";
		}
		return strDistancePrice;
	}

}
