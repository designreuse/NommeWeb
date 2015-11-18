/**
 * 
 */
package com.camut.controller.restaurant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.framework.constant.MessageConstant;
import com.camut.model.RestaurantTable;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageTable;
import com.camut.service.RestaurantTableService;

/**
 * @ClassName TableAndBusinessTimeController.java
 * @author wangpin
 * @createtime Jun 18, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("TableController")
@RequestMapping("table")
public class TableController extends BaseController {

	@Autowired
	private RestaurantTableService restaurantTableService;
	
	/**
	 * @Title: toTableAndBusinessTime
	 * @Description: 返回桌位管理页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toTable",method=RequestMethod.GET)
	public String toTableAndBusinessTime(){
		return "restaurant/table";
	}
	
	/**
	 * @Title: getAllTable
	 * @Description: 获取商家所有的桌位信息
	 * @param:    HttpServletRequest
	 * @return: List<PageTable>
	 */
	@RequestMapping(value="getAllTable")
	@ResponseBody
	public List<PageTable> getAllTable(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return restaurantTableService.getRestaurantTable(restaurants);
	}
	
	/**
	 * @Title: getTableById
	 * @Description: 通过id查找桌位信息
	 * @param:    long
	 * @return: PageTable
	 */
	@RequestMapping(value="getTableById",method=RequestMethod.POST)
	@ResponseBody
	public PageTable getTableById(long id){
		return restaurantTableService.getRestaurantTableById(id);
	}
	
	/**
	 * @Title: addRestaurantTable
	 * @Description: 增加桌位信息
	 * @param:    RestaurantTable HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="addRestaurantTable",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addRestaurantTable(RestaurantTable restaurantTable,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		restaurantTable.setRestaurants(restaurants);
		int flag = restaurantTableService.addRestaurantTable(restaurantTable);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: updateRestaurantTable
	 * @Description: 修改桌位信息
	 * @param:    RestaurantTable HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateRestaurantTable",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateRestaurantTable(RestaurantTable restaurantTable,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		restaurantTable.setRestaurants(restaurants);
		int flag = restaurantTableService.updateRestaurantTable(restaurantTable);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: deleteRestaurantTable
	 * @Description: 删除桌位信息
	 * @param:    RestaurantTable HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteRestaurantTable",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteRestaurantTable(RestaurantTable restaurantTable){
		PageMessage pm = new PageMessage();
		int flag = restaurantTableService.deleteRestaurantTable(restaurantTable);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
}
