/**
 * 
 */
package com.camut.service;

import java.util.List;
import com.camut.model.Restaurants;
import com.camut.model.api.RestaurantsApiModel;
import com.camut.model.api.RestaurantsDetailApiModel;
import com.camut.model.api.RestaurantsMoreApiModel;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageRestaurant;
import com.camut.pageModel.PageRestaurantName;
import com.camut.pageModel.PageRestaurantsMenu;

/**
 * @service RestaurantsService.java
 * @author wangpin
 * @createtime May 27, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantsService {

	/*
	 * 
	 * @Title: getRestaurantsById
	 * @Description: 通过商家id查找商家
	 * @param:    商家id
	 * @return: Restaurants
	 */
	public Restaurants getRestaurantsById(long id);
	
	/*
	 * 
	 * @Title: getRestaurantsByUuid
	 * @Description: 通过商家id查找商家
	 * @param:    商家uuid
	 * @return: Restaurants
	 */
	public Restaurants getRestaurantsByUuid(String restaurantUuid);
	
	/*
	 * @Title: addRestaurants
	 * @Description: 增加商家
	 * @param:    restaurants
	 * @return: Restaurants
	 */
	public String addRestaurants(Restaurants restaurants);
	
	/*
	 * @Title: deleteRestaurants
	 * @Description: 通过主键删除商家
	 * @param:    long
	 * @return: int -1删除失败 1删除成功
	 */
	public int deleteRestaurants(String restaurantUuid);
	
	/*
	 * @Title: updateRestaurants
	 * @Description: 修改商家资料
	 * @param:    Restaurants
	 * @return: int -1修改失败 1修改成功
	 */
	public int updateRestaurants(Restaurants restaurants);
	
	/**
	 * @Title: checkRestaurantsNameUnique
	 * @Description: 验证商家名称是否唯一
	 * @param:    Restaurants
	 * @return: int -1已存在 1不存在
	 */
	public int checkRestaurantsNameUnique(Restaurants restaurants);
	
	/**
	 * @Title: getAllRestaurants
	 * @Description: 获取所有的商家对象
	 * @param: 
	 * @return List<PageRestaurant>  
	 */
	public PageModel getAllRestaurants(PageFilter pf);
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 审核商家
	 * @param: statu
	 * @return int  
	 */
	public int auditRestaurant(String restaurantUuid, int statu);
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 获取商家信息到前台表格用于下拉框选择
	 * @param: 
	 * @return List<PageRestaurantSelect> 
	 */
	public List<PageRestaurantName> getAllRestaurantsName();
	
	/**
	 * @Title: RestaurantsDetailApiModel
	 * @Description: 店家详情
	 * @param: restaurantId
	 * @return restaurantsDetailApiModel
	 */
	public RestaurantsDetailApiModel restaurantsDetailApiModel(String restaurantUuid, String consumerUuid);
	
	/**
	 * @Title: getPageRestaurantById
	 * @Description: 打开商家页面获取商家基础信息
	 * @return PageRestaurant  
	 */
	public PageRestaurant getPageRestaurantByUuid(String restaurantUuid);
	
	/**
	 * @Title: getPageRestaurantById
	 * @Description: 通过id获取PageRestaurant
	 * @param: long id
	 * @param: int isPickup
	 * @return List<PageRestaurantsMenu>  
	 */
	public List<PageRestaurantsMenu> getRestaurantsMenusByRestaurantsIdAndIsPickup(String restaurantUuid);
	
	/** 
	 * @Title: getRestaurantsById
	 * @Description: 通过商家id查找商家
	 * @param:    商家id
	 * @return: RestaurantsDetailApiModel
	 */
	public RestaurantsApiModel getRestaurantByUuid(String restaurantUuid);
	
	/** 
	 * @Title: getRestaurantMoreById
	 * @Description: 通过商家id查找商家(more)
	 * @param:    商家id
	 * @return: RestaurantsMoreApiModel
	 */
	public RestaurantsMoreApiModel getRestaurantMoreById(String restaurantUuid);
	
}
