package com.camut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.DishGarnishDao;
import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.service.DishGarnishService;

@Service
public class DishGarnishServiceImpl implements DishGarnishService {
	
	@Autowired DishGarnishDao dishGarnishDao;
	
	/**
	 * @Title: getDishGarnishByDishIdAndGarnishItemId
	 * @Description: 根据菜品和配菜联合主键找到一个唯一的菜品和配菜关系数据
	 * @param: @param dishId
	 * @param: @param GarnishItemId
	 * @return DishGarnish  
	 */
	public DishGarnish getDishGarnishByDishIdAndGarnishItemId(long dishId, long garnishItemId){
		return dishGarnishDao.getDishGarnishByDishIdAndGarnishItemId(dishId, garnishItemId);
	}

	/**
	 * @Title: addDishGarnish
	 * @Description: 保存菜品配菜
	 * @param:    DishGarnish
	 * @return: int
	 */
	@Override
	public int addDishGarnish(DishGarnish dishGarnish) {
		if(dishGarnish!=null){
			return dishGarnishDao.addDishGarnish(dishGarnish);
		}
		return -1;
	}

	/**
	 * @Title: deleteDisnGarnishByDish
	 * @Description: 删除指定菜品下面的配菜关系
	 * @param:    Dish
	 * @return: int
	 */
	@Override
	public int deleteDisnGarnishByDish(Dish dish) {
		if(dish!=null){
			return dishGarnishDao.deleteDisnGarnishByDish(dish);
		}
		return -1;
	}

}
