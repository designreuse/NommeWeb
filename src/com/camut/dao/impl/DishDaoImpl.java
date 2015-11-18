package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.DishDao;
import com.camut.model.Dish;
import com.camut.model.Restaurants;

/**
 * @dao DishDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class DishDaoImpl extends BaseDao<Dish> implements DishDao {

	/**
	 * @Title: getdish
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: List<Dish>
	 */
	@Override
	public List<Dish> getdish(long restaurantId, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		/*if (type == 1) {// 外送
			int a = 1;
			int b = 3;
			int c = 5;
			int d = 7;
			map.put("a", a);
			map.put("b", b);
			map.put("c", c);
			map.put("d", d);
		} else if (type == 2) {// 自取
			int a = 2;
			int b = 3;
			int c = 6;
			int d = 7;
			map.put("a", a);
			map.put("b", b);
			map.put("c", c);
			map.put("d", d);
		} else if (type == 3) {// 预定
			int a = 4;
			int b = 5;
			int c = 6;
			int d = 7;
			map.put("a", a);
			map.put("b", b);
			map.put("c", c);
			map.put("d", d);
		}*/
		//String hql = "from Dish di where di.restaurantId=:restaurantId and (di.isPickup=:a or di.isPickup=:b or di.isPickup=:c or di.isPickup=:d) and di.status=0";
		String hql = "from Dish di where di.restaurantId=:restaurantId and di.status=0"; 
		map.put("restaurantId", restaurantId);
		List<Dish> dList = this.find(hql, map);
		return dList;
	}
	
	/**
	 * @Title: getDishById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: Dish
	 */
	@Override
	public Dish getDishById(long id) {
		String hql = "from Dish d where d.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/**
	 * @Title: getAllDish
	 * @Description: 获取商家的所有菜品,加载菜品页面时用的
	 * @param:    Restaurants
	 * @return: List<PageDish>
	 */
	@Override
	public List<Dish> getAllDish(Restaurants restaurants) {
		String hql = "from Dish d where d.restaurantId=:id order by d.status";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", restaurants.getId());
		return this.find(hql, map);
	}

	/**
	 * @Title: addDish
	 * @Description: 增加菜品信息
	 * @param:    Dish
	 * @return: int
	 */
	@Override
	public int addDish(Dish dish) {
		try {
			return Integer.parseInt(this.save(dish)+"");
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: updateDish
	 * @Description:修改菜品
	 * @param:   Dish 
	 * @return: int
	 */
	@Override
	public int updateDish(Dish dish) {
		try {
			this.update(dish);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getDishMenu
	 * @Description: 获取菜单信息
	 * @param:    restaurantId , type 
	 * @return: List<Dish>
	 */
	@Override
	public List<Dish> getDishMenu(long restaurantId, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		/*if (type == 1) {// 外送
			int a = 1;
			int b = 3;
			int c = 5;
			int d = 7;
			map.put("a", a);
			map.put("b", b);
			map.put("c", c);
			map.put("d", d);
		} else if (type == 2) {// 自取
			int a = 2;
			int b = 3;
			int c = 6;
			int d = 7;
			map.put("a", a);
			map.put("b", b);
			map.put("c", c);
			map.put("d", d);
		} else if (type == 3) {// 预定
			int a = 4;
			int b = 5;
			int c = 6;
			int d = 7;
			map.put("a", a);
			map.put("b", b);
			map.put("c", c);
			map.put("d", d);
		}*/
		String hql = "from Dish di where di.restaurantId=:restaurantId and di.status=0";//and (di.isPickup=:a or di.isPickup=:b or di.isPickup=:c or di.isPickup=:d)
		/*if(dishName != null && dishName.length() > 0){
			hql += " and di.enName like '%"+dishName+"%'";
		}*/
		map.put("restaurantId", restaurantId);
		List<Dish> dList = this.find(hql, map);
		return dList;
	}

	/**
	 * @Title: getAllAvailableDish
	 * @Description: 获取所有上架的菜品
	 * @param:    Restaurants
	 * @return: List<Dish>
	 */
	@Override
	public List<Dish> getAllAvailableDish(Restaurants restaurants) {
		String hql = "from Dish d where d.restaurantId=:id and d.status=0 order by d.createdate desc";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", restaurants.getId());
		return this.find(hql, map);
	}
	
	/**
	 * @Title: getDishListByRestaurantsMenuIdAndRestaurantsId
	 * @Description: 通过商家id和菜单分离id获取菜品列表
	 * @param: int restaurantId
	 * @param: long restaurantsMenuId
	 * @return List<Dish>  
	 */
	public List<Dish> getDishListByRestaurantsMenuIdAndRestaurantsId(int restaurantId,long restaurantsMenuId){
		String hql = "from Dish d where d.restaurantId=:restaurantId and d.restaurantsMenu.id=:restaurantsMenuId order by d.enName asc";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurantId", restaurantId);
		map.put("restaurantsMenuId", restaurantsMenuId);
		return this.find(hql, map);
		
	}

}
