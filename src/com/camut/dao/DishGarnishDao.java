package com.camut.dao;

import java.util.List;

import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.model.api.DishGarnishApiModel;
import com.camut.model.api.GarnishHeaderApiModel;

/**
 * @dao DishGarnishDao.java
 * @author zhangyunfei
 * @createtime 6 23, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DishGarnishDao {

	/**
	 * @Title: getDishGarnishById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: DishGarnish
	 */
	public DishGarnish getDishGarnishById(long id);
	
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
	
	/**
	 * @Title: getDishGarnishByGarnishHeaderId
	 * @Description: 根据配菜类型ID获取数据
	 * @param:    garnishHeaderId
	 * @return: DishGarnish
	 */
	 public List<DishGarnish> getDishGarnishByGarnishHeaderId(int garnishHeaderId);
	 
	 /**
	  * 根据配菜类型ID获取配菜数据，sql
	  * @param garnishHeaderId
	  * @return
	  */
	 public List<GarnishHeaderApiModel> getGarnishItemByHeaderIdForSql(long dishId, long garnishMenuId);
	 
	 /**
	  * @Title: getDishGarnishApiModelByDishId
	  * @Description: 根据菜品id获取DishGarnishApiModel
	  * @param:    
	  * @return: List<DishGarnishApiModel>
	  */
	 public List<DishGarnishApiModel> getDishGarnishApiModelByDishId(long dishId);
	 
	 public List<DishGarnishApiModel> getDishGarnishApiModelByDishIdForCheck(long dishId);
}
