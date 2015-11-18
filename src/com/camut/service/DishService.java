package com.camut.service;

import java.util.List;
import com.camut.model.Dish;
import com.camut.model.Restaurants;
import com.camut.model.api.DishApiModel;
import com.camut.model.api.DishMenuApiModel;
import com.camut.pageModel.PageDish;

/**
 * @dao DishService.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DishService {

	/**
	 * @Title: getdish
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: List<DishApiModel>
	 */
	public List<DishApiModel> getdish(int restaurantId, int type);
	
	/**
	 * @Title: getAllDish
	 * @Description: 获取商家的所有菜品
	 * @param:    Restaurants
	 * @return: List<PageDish>
	 */
	public List<PageDish> getAllDish(Restaurants restaurants);
	
	/**
	 * @Title: addDish
	 * @Description: 增加菜品信息
	 * @param:    Dish
	 * @return: int
	 */
	public int addDish(Dish dish);
	
	/**
	 * @Title: getDishById
	 * @Description: 根据主键查找菜品
	 * @param: id
	 * @return: Dish
	 */
	public Dish getDishById(String id);
	
	/**
	 * @Title: updateDish
	 * @Description:修改菜品
	 * @param:   Dish 
	 * @return: int
	 */
	public int updateDish(Dish dish);
	
	/**
	 * @Title: getPageDishById
	 * @Description: 根据主键查找菜品的页面对象
	 * @param:    String
	 * @return: PageDish
	 */
	public PageDish getPageDishById(String id);
	
	/**
	 * @Title: getPageDishByDishId
	 * @Description: 根据主键查找菜品的页面对象
	 * @param:    String
	 * @return: PageDish
	 */
	public PageDish getPageDishByDishId(String id);
	
	/**
	 * @Title: getDishMenu
	 * @Description: 获取菜单信息
	 * @param:    restaurantId , type 
	 * @return: List<RestaurantsDishMenuApiModel>
	 */
	public  List<DishMenuApiModel> getDishMenu(int restaurantId, int type);
	
	/**
	 * @Title: deleteDish
	 * @Description: 删除菜品
	 * @param:    Dish
	 * @return: int
	 */
	public int deleteDish(Dish dish);
	
	/**
	 * @Title: getAllAvailableDish
	 * @Description: 获取所有上架的菜品
	 * @param:    Restaurants
	 * @return: List<Dish>
	 */
	public List<PageDish> getAllAvailableDish(Restaurants restaurants);
	
	/**
	 * @Title: getdishs
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: List<Dish>
	 */
	public List<Dish> getdishs(int restaurantId, int type);
	
}
