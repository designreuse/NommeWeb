/**
 * 
 */
package com.camut.controller.restaurant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;

/**
 * @ClassName BaseController.java
 * @author wangpin
 * @createtime Jun 16, 2015
 * @author
 * @updateTime
 * @memo
 */
public class BaseController {
	@Autowired
	private RestaurantsService restaurantsService;
	
	@Autowired
	private RestaurantsUserService restaurantsUserService;
	
	public Restaurants getRestaurants(HttpSession httpSession,HttpServletRequest request){
		if(httpSession.getAttribute("restaurantsUser")!=null){
			return restaurantsService.getRestaurantsById(((RestaurantsUser)httpSession.getAttribute("restaurantsUser")).getRestaurants().getId());
		}
		else if(request.getParameter("restaurantId")!=null){
			return restaurantsService.getRestaurantsById(Long.parseLong(request.getParameter("restaurantId")));
		}
		return null;
	}
	
	public RestaurantsUser getRestaurantsUser(HttpSession httpSession,HttpServletRequest request){
		if(httpSession.getAttribute("restaurantsUser")!=null){
			return ((RestaurantsUser)request.getSession().getAttribute("restaurantsUser"));
		}
		/*else if(request.getParameter("id")!=null){//获取请求参数中的该员工的id不为空，则用该id去得到这个员工对象
			return restaurantsUserService.getRestaurantsUserById(Long.parseLong(request.getParameter("id")));
		}*/
		return null;
	}
}
