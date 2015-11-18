/**
 * 
 */
package com.camut.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.model.ViewRestaurant;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageViewRestaurant;
import com.camut.service.ViewRestaurantService;

/**
 * @ClassName SearchListController.java
 * @author wangpin
 * @createtime Jul 9, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("SearchListController")
@RequestMapping("/searchList")
public class SearchListController {
	
	@Autowired
	private ViewRestaurantService viewRestaurantService;
	
	/**
	 * @Title: getViewRestaurants
	 * @Description: searchList页面返回餐厅的信息 九宫格
	 * @param:    ViewRestaurant Model
	 * @return: PageModel
	 */
	@RequestMapping(value="/sudoku",method=RequestMethod.POST)
	public String getSudoku(ViewRestaurant viewRestaurant,Model model,PageFilter pf){	
		PageModel pm = viewRestaurantService.getRestaurants(viewRestaurant,pf);
		model.addAttribute("viewRestaurants", pm);
		return "home/searchSudoku";
	}
	
	/**
	 * @Title: getViewRestaurants
	 * @Description: searchList页面返回餐厅的信息 列表
	 * @param: ViewRestaurant Model
	 * @return: PageModel
	 */
	@RequestMapping(value="/banner",method=RequestMethod.POST)
	public String getBanner(ViewRestaurant viewRestaurant,Model model,PageFilter pf){
		PageModel pm = viewRestaurantService.getRestaurants(viewRestaurant,pf);
		model.addAttribute("viewRestaurants", pm);
		return "home/searchBanner";
	}
	
	/**
	 * @Title: getViewRestaurants
	 * @Description: searchList页面返回餐厅的信息 地图
	 * @param:    ViewRestaurant Model
	 * @return: PageModel
	 */
	@RequestMapping(value="/map",method=RequestMethod.POST)
	@ResponseBody
	public List<PageViewRestaurant> getMap(ViewRestaurant viewRestaurant,PageFilter pf){
		return viewRestaurantService.getRestaurantsForMap(viewRestaurant, pf);
	}
}
