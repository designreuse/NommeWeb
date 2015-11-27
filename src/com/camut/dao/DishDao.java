package com.camut.dao;

import java.util.List;
import com.camut.model.Dish;
import com.camut.model.Restaurants;

/**
 * @dao DishDao.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DishDao {

	/**
	 * @Title: getdish
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: List<Dish>
	 */
	public List<Dish> getdish(String restaurantUuid, int type);
	
	/**
	 * @Title: getDishById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: Dish
	 */
	public Dish getDishById(long id);
	
	/**
	 * @Title: getAllDish
	 * @Description: 获取商家的所有菜品
	 * @param:    Restaurants
	 * @return: List<PageDish>
	 */
	public List<Dish> getAllDish(Restaurants restaurants);
	
	/**
	 * @Title: addDish
	 * @Description: 增加菜品信息
	 * @param:    Dish
	 * @return: int
	 */
	public int addDish(Dish dish);
	
	/**
	 * @Title: updateDish
	 * @Description:修改菜品
	 * @param:   Dish 
	 * @return: int
	 */
	public int updateDish(Dish dish);
	
	/**
	 * @Title: getDishMenu
	 * @Description: 获取菜单信息
	 * @param:    restaurantId , type 
	 * @return: List<Dish>
	 */
	public  List<Dish> getDishMenu(String restaurantUuid, int type);
	
	/**
	 * @Title: getAllAvailableDish
	 * @Description: 获取所有上架的菜品
	 * @param:    Restaurants
	 * @return: List<Dish>
	 */
	public List<Dish> getAllAvailableDish(Restaurants restaurants);
	
	/**
	 * @Title: getDishListByRestaurantsMenuIdAndRestaurantsId
	 * @Description: 通过商家id和菜单分离id获取菜品列表
	 * @param: int restaurantId
	 * @param: long restaurantsMenuId
	 * @return List<Dish>  
	 */
	public List<Dish> getDishListByRestaurantsMenuIdAndRestaurantsId(String restaurantUuid,long restaurantsMenuId);
	
}
