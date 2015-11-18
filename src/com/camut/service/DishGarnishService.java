package com.camut.service;



import com.camut.model.Dish;
import com.camut.model.DishGarnish;

public interface DishGarnishService {
	
	/**
	 * @Title: getDishGarnishByDishIdAndGarnishItemId
	 * @Description: 根据菜品和配菜联合主键找到一个唯一的菜品和配菜关系数据
	 * @param: @param dishId
	 * @param: @param GarnishItemId
	 * @return DishGarnish  
	 */
	public DishGarnish getDishGarnishByDishIdAndGarnishItemId(long dishId, long garnishItemId);
	
	/**
	 * @Title: addDishGarnish
	 * @Description: 保存菜品配菜
	 * @param:    DishGarnish
	 * @return: int
	 */
	public int addDishGarnish(DishGarnish dishGarnish);
	
	/**
	 * @Title: deleteDisnGarnishByDish
	 * @Description: 删除指定菜品下面的配菜关系
	 * @param:    Dish
	 * @return: int
	 */
	public int deleteDisnGarnishByDish(Dish dish);

}
