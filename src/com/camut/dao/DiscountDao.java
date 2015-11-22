package com.camut.dao;

import java.util.List;
import java.util.Map;
import com.camut.model.Discount;
import com.camut.model.Restaurants;

/**
 * @dao DiscountDao.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DiscountDao {

	/**
	 * @Title: getDiscountByRestaurantId
	 * @Description: 店铺优惠信息
	 * @param:  restaurantId
	 * @return: List<Discount>
	 */
	public List<Discount> getDiscountByRestaurantUuid(String restaurantUuid, int orderType, double consumePrice);
	
	/**
	 * @Title: getAllDiscounts
	 * @Description: 获取商家的所有优惠信息
	 * @param:    Restaurants
	 * @return: List<PageDiscount>
	 */
	public List<Discount> getAllDiscounts(Restaurants restaurants);
	
	/**
	 * @Title: addDiscount
	 * @Description: 增加优惠信息
	 * @param:    Discount
	 * @return: int
	 */
	public int addDiscount(Discount discount);
	
	/**
	 * @Title: updateDiscount
	 * @Description: 修改优惠信息
	 * @param:   Discount 
	 * @return: int
	 */
	public int updateDiscount(Discount discount);
	
	/**
	 * @Title: deleteDiscount
	 * @Description: 删除优惠信息
	 * @param:    Discount
	 * @return: int
	 */
	public int deleteDiscount(Discount discount);
	
	/**
	 * @Title: getDiscountByDishId
	 * @Description: 根据菜品id查询优惠信息中有没有用到此菜品
	 * @param:   String 
	 * @return: int -1没用到  1用到
	 */
	public int getDiscountByDishId(int dishId);
	
	/**
	 * @Title: chooseDistance
	 * @Description: 用户选择使用优惠券时，查找出原先使用的和将要使用的优惠券信息 只会一条或者两条信息
	 * @param: @param idMap
	 * @return List<PageDiscount>  
	 */
	public List<Discount> chooseDiscount(Map<String, Object> idMap);
	
	/**
	 * 根据Id获取折扣信息
	 * @param discountId
	 * @return
	 */
	public Discount  getDiscount(long discountId);
	
	
}
