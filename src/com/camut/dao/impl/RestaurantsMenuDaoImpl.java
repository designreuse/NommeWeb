/**
 * 
 */
package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.RestaurantsMenuDao;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsMenu;
/**
 * @ClassName DishMenuDaoImpl.java
 * @author wangpin
 * @createtime Jun 17, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class RestaurantsMenuDaoImpl extends BaseDao<RestaurantsMenu> implements RestaurantsMenuDao {
	

	/**
	 * @Title: getAllDishMenu
	 * @Description: 获取商家所有的菜品分类
	 * @param:    Restaurants
	 * @return: List<PageDishMenu>
	 */
	@Override
	public  List<RestaurantsMenu> getAllRestaurantsMenu(Restaurants restaurants) {
		String hql = "from RestaurantsMenu r where r.restaurants=:restaurant";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurant", restaurants);
		return this.find(hql, map);
	}

	/**
	 * @Title: addRestaurantsMenu
	 * @Description: 增加菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	@Override
	public int addRestaurantsMenu(RestaurantsMenu restaurantsMenu) {
		try {
			this.save(restaurantsMenu);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateRestaurantsMenu
	 * @Description: 修改菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	@Override
	public int updateRestaurantsMenu(RestaurantsMenu restaurantsMenu) {
		try {
			this.update(restaurantsMenu);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getRestaurantsMenuById
	 * @Description: 根据主键查找菜品分类
	 * @param:    
	 * @return: RestaurantsMenu
	 */
	@Override
	public RestaurantsMenu getRestaurantsMenuById(long id) {
		String hql = "from RestaurantsMenu r where r.id=:id";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/**
	 * @Title: deleteRestaurantsMenu
	 * @Description: 删除菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	@Override
	public int deleteRestaurantsMenu(RestaurantsMenu restaurantsMenu) {
		try {
			this.delete(restaurantsMenu);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	
	/**
	 * @Title: getClassificationByRestaurantsId
	 * @Description: 获取对应商家的所有菜单分类
	 * @param: int id
	 * @return List<RestaurantsMenu>  
	 */
	@Override
	public List<RestaurantsMenu> getRestaurantsMenuByRestaurantsId(long restaurantId){
		String hql = "from RestaurantsMenu r where r.restaurants.id=:restaurantId order by r.menuName desc"; //where r.Restaurants.id=t=:restaurantId"; //order by r.menuName desc";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		return this.find(hql,map);
	}

}
