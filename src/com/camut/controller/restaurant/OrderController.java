package com.camut.controller.restaurant;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.model.RestaurantsUser;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageOrderHeader;
import com.camut.service.OrderItemService;
import com.camut.service.OrderService;
import com.camut.utils.StringUtil;

@Controller("OrderController")
@RequestMapping(value="order")
public class OrderController {
	
	@Autowired OrderService orderService;
	@Autowired OrderItemService orderItemService;
	@RequestMapping(value="/toordermanagementpage", method= RequestMethod.GET)
	public String toOrderManagementPage(){
		return "restaurant/order";
	}
	
	
	@RequestMapping(value="/datatable", method= RequestMethod.GET)
	@ResponseBody
	public PageModel ToData(HttpSession session, PageFilter pf,String status, String orderDate){
		//获取商家id
		String restaurantUuid = ((RestaurantsUser)session.getAttribute("restaurantsUser")).getRestaurants().getUuid();
		PageModel pm = orderService.getOrdersByRestaurantUuid(restaurantUuid, pf,status,orderDate);
		return pm;
	}
	
	@RequestMapping(value="/getOrderItem", method= RequestMethod.POST)
	@ResponseBody
	public PageOrderHeader getOrderItem(String orderId){
		if (StringUtil.isNotEmpty(orderId)) {
			return orderService.getOrderItemByHeader(Long.parseLong(orderId));
		}
		return null;
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
	public String getOneOrderDetail(String orderId, Model model){
			if(StringUtil.isNotEmpty(orderId)){
				//OrderDetailsApiModel order = orderItemService.selectHistoryOrder(Long.parseLong(orderId));
				PageOrderHeader order = orderService.getOrderItemByHeader(Long.parseLong(orderId));
				model.addAttribute("order",order);
			}
		return "restaurant/orderDetail";
	}
	
	
}
