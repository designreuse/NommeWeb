package com.camut.controller.restaurant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageRestaurantOrderStatement;
import com.camut.service.OrderService;
import com.camut.utils.StringUtil;

@Controller(value="restaurantStatementController")
@RequestMapping(value="restaurantStatement")
public class restaurantStatementController {
	
	@Autowired OrderService orderService;
	
	
	/**
	 * @Title: getRestaurantStatementPage
	 * @Description: 打开商家报表页面
	 * @param: @param session
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="getRestaurantStatementPage")
	public String getRestaurantStatementPage(HttpSession session){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String endDate = format.format(calendar.getTime());
		System.out.println("当前时间："+endDate);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String startDate = format.format(calendar.getTime());
		System.out.println("当月第一天："+startDate);
		session.setAttribute("startDate", startDate);
		session.setAttribute("endDate", endDate);
		return "restaurant/restaurantStatementPage";
	}
	
	/**
	 * @Title: getRestaurantOrders
	 * @Description: 商家获取订单报表数据加载到表格
	 * @param: @param pf
	 * @param: @param searchKey
	 * @param: @return
	 * @return PageModel
	 */
	@RequestMapping(value="getRestaurantOrders")
	@ResponseBody
	public PageModel getRestaurantOrders(PageFilter pf, String searchKey,HttpSession session){
		PageModel pm = new PageModel();
		RestaurantsUser restaurantsUser = (RestaurantsUser)session.getAttribute("restaurantsUser");
		if(restaurantsUser!=null){
			String restaurantId = restaurantsUser.getRestaurants().getId()+"";
			if(StringUtil.isNotEmpty(restaurantId)){
				List<PageRestaurantOrderStatement> list = orderService.getRestaurantStatement(searchKey, pf, restaurantId);
				pm.setRows(list);
				pm.setTotal(list.size());				
			}
		}
		return pm;
	}

}
