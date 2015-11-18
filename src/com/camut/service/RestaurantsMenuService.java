/**
 * 
 */
package com.camut.service;

import java.util.List;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsMenu;
import com.camut.model.api.RestaurantsMenuApiModel;
import com.camut.pageModel.PageDishMenu;

/**
 * @ClassName DishMenuService.java
 * @author wangpin
 * @createtime Jun 17, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantsMenuService {

	/**
	 * @Title: getAllDishMenu
	 * @Description: 获取商家所有的菜品分类
	 * @param:    Restaurants
	 * @return: List<PageDishMenu>
	 */
	public List<PageDishMenu> getAllRestaurantsMenu(Restaurants restaurants);
	
	/**
	 * @Title: addRestaurantsMenu
	 * @Description: 增加菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	public int addRestaurantsMenu(RestaurantsMenu restaurantsMenu);
	
	/**
	 * @Title: updateRestaurantsMenu
	 * @Description: 修改菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	public int updateRestaurantsMenu(RestaurantsMenu restaurantsMenu);
	
	/**
	 * @Title: deleteRestaurantsMenu
	 * @Description: 根据主键删除菜品分类
	 * @param:    long
	 * @return: int
	 */
	public int deleteRestaurantsMenu(long id);
	
	/**
	 * @Title: checkRestaurantMenu
	 * @Description: 检查给定的菜品下有没有菜品
	 * @param:    String
	 * @return: int -1没有 1有
	 */
	public int checkRestaurantMenu(String id);
	
	/**
	 * @Title: getGarnishHeaderByRestaurantId
	 * @Description: 获取商家配菜分类
	 * @param:    restaurantId
	 * @return: List<RestaurantsMenuApiModel>
	 */
	//public List<RestaurantsMenuApiModel> getGarnishHeaderByRestaurantId(Restaurants restaurants);
	
	/**
	 * @Title: getAllDishMenu
	 * @Description: 菜品分类
	 * @param:    Restaurants
	 * @return: List<PageDishMenu>
	 */
	public List<RestaurantsMenuApiModel> getAllDishMenu(Restaurants restaurants);
	
	
	
}
