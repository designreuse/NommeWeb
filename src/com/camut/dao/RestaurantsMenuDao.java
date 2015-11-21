/**
 * 
 */
package com.camut.dao;

import java.util.List;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsMenu;

/**
 * @ClassName DishMenuDao.java
 * @author wangpin
 * @createtime Jun 17, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface RestaurantsMenuDao {

	/**
	 * @Title: getAllDishMenu
	 * @Description: 获取商家所有的菜品分类
	 * @param:    Restaurants
	 * @return: List<PageDishMenu>
	 */
	public List<RestaurantsMenu> getAllRestaurantsMenu(Restaurants restaurants);

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
	 * @Title: getRestaurantsMenuById
	 * @Description: 根据主键查找菜品分类
	 * @param:    
	 * @return: RestaurantsMenu
	 */
	public RestaurantsMenu getRestaurantsMenuById(long id);
	
	/**
	 * @Title: deleteRestaurantsMenu
	 * @Description: 删除菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	public int deleteRestaurantsMenu(RestaurantsMenu restaurantsMenu);
	

	/**
	 * @Title: getClassificationByRestaurantsId
	 * @Description: 获取对应商家的所有菜单分类
	 * @param: int id
	 * @return List<RestaurantsMenu>  
	 */
	public List<RestaurantsMenu> getRestaurantsMenuByRestaurantsUuid(String restaurantUuid);

}
