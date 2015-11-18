/**
 * 
 */
package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.camut.dao.DishDao;
import com.camut.dao.RestaurantsMenuDao;
import com.camut.model.Dish;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsMenu;
import com.camut.model.api.RestaurantsMenuApiModel;
import com.camut.pageModel.PageDishMenu;
import com.camut.service.RestaurantsMenuService;

/**
 * @ClassName DishMenuServiceIml.java
 * @author wangpin
 * @createtime Jun 17, 2015
 * @author
 * @updateTime
 * @memo
 */

@Service
@Transactional
public class RestaurantsMenuServiceIml implements RestaurantsMenuService {

	@Autowired
	private RestaurantsMenuDao restaurantsMenuDao;
	
	@Autowired
	private DishDao dishDao;
	
	/**
	 * @Title: getAllDishMenu
	 * @Description: 获取商家所有的菜品分类
	 * @param:    Restaurants
	 * @return: List<PageDishMenu>
	 */
	@Override
	public List<PageDishMenu> getAllRestaurantsMenu(Restaurants restaurants) {
		List<PageDishMenu> list = new ArrayList<PageDishMenu>();
		if(restaurants!=null){
			List<RestaurantsMenu> menus = restaurantsMenuDao.getAllRestaurantsMenu(restaurants);
			for(RestaurantsMenu restaurantsMenu:menus){//遍历菜品分类，组装页面对象
				PageDishMenu pageDishMenu = new PageDishMenu();
				BeanUtils.copyProperties(restaurantsMenu, pageDishMenu);
				list.add(pageDishMenu);
			}
		}
		return list;
	}

	/**
	 * @Title: addRestaurantsMenu
	 * @Description: 增加菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	@Override
	public int addRestaurantsMenu(RestaurantsMenu restaurantsMenu) {
		if(restaurantsMenu!=null){
			return restaurantsMenuDao.addRestaurantsMenu(restaurantsMenu);
		}
		return -1;
	}

	/**
	 * @Title: updateRestaurantsMenu
	 * @Description: 修改菜品分类
	 * @param:    RestaurantsMenu
	 * @return: int
	 */
	@Override
	public int updateRestaurantsMenu(RestaurantsMenu restaurantsMenu) {
		if(restaurantsMenu!=null){
			return restaurantsMenuDao.updateRestaurantsMenu(restaurantsMenu);
		}
		return -1;
	}

	/**
	 * @Title: deleteRestaurantsMenu
	 * @Description: 根据主键删除菜品分类
	 * @param:    long
	 * @return: int
	 */
	@Override
	public int deleteRestaurantsMenu(long id) {
		if(id!=0){
			RestaurantsMenu restaurantsMenu = restaurantsMenuDao.getRestaurantsMenuById(id);
			if(restaurantsMenu!=null){
				return restaurantsMenuDao.deleteRestaurantsMenu(restaurantsMenu);
			}
		}
		return -1;
	}

	/**
	 * @Title: checkRestaurantMenu
	 * @Description: 检查给定的菜品下有没有菜品
	 * @param:    String
	 * @return: int -1没有 1有
	 */
	@Override
	public int checkRestaurantMenu(String id) {
		if(id!=null && id.length()>0){
			RestaurantsMenu menu = restaurantsMenuDao.getRestaurantsMenuById(Long.parseLong(id));
			if(menu.getDishs().size()>0){
				return 1;
			}
		}
		return -1;
	}

	/**
	 * @Title: getGarnishHeaderByRestaurantId
	 * @Description: 获取商家配菜分类
	 * @param:    restaurantId
	 * @return: List<GarnishHeaderApiModel>
	 */
	/*@Override
	public List<RestaurantsMenuApiModel> getGarnishHeaderByRestaurantId(Restaurants restaurants) {
		List<RestaurantsMenu> rmList = restaurantsMenuDao.getAllRestaurantsMenu(restaurants);
		List<RestaurantsMenuApiModel> ghamList = new ArrayList<RestaurantsMenuApiModel>();
		for (RestaurantsMenu restaurantsMenu : rmList) {
			RestaurantsMenuApiModel rmam = new RestaurantsMenuApiModel();
			rmam.setRestaurantMenuId(restaurantsMenu.getId());
			rmam.setMenuName(restaurantsMenu.getMenuName());
			ghamList.add(rmam);
		}
		return ghamList;	
	}*/
	
	/**
	 * @Title: getAllDishMenu
	 * @Description: 菜品分类
	 * @param:    Restaurants
	 * @return: List<PageDishMenu>
	 */
	@Override
	public List<RestaurantsMenuApiModel> getAllDishMenu(Restaurants restaurants) {
		List<RestaurantsMenuApiModel> list = new ArrayList<RestaurantsMenuApiModel>();
		if(restaurants!=null){		
			List<Dish> dishs =  dishDao.getdish(restaurants.getId(), restaurants.getIsdelivery().intValue());
			for (Dish dish : dishs) {
				if(list.size()==0){
					RestaurantsMenuApiModel apiModel=new RestaurantsMenuApiModel();
					apiModel.setMenuName(dish.getRestaurantsMenu().getMenuName());
					apiModel.setRestaurantMenuId(dish.getRestaurantsMenu().getId());
					apiModel.setMenuDiscription(dish.getRestaurantsMenu().getDescribe());
					list.add(apiModel);
				}else{
					boolean flag=false;
					for (RestaurantsMenuApiModel restaurantsMenuApiModel : list) {
						if(restaurantsMenuApiModel.getRestaurantMenuId()==dish.getRestaurantsMenu().getId()){
							flag=false;
							break;
						}else{
							flag=true;
						}
					}
					if(flag){
						RestaurantsMenuApiModel apiModel=new RestaurantsMenuApiModel();
						apiModel.setMenuName(dish.getRestaurantsMenu().getMenuName());
						apiModel.setRestaurantMenuId(dish.getRestaurantsMenu().getId());
						apiModel.setMenuDiscription(dish.getRestaurantsMenu().getDescribe());
						list.add(apiModel);
					}
				}
				
			}
		}
		Collections.sort(list);
		return list;
	}
}
