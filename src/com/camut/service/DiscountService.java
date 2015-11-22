package com.camut.service;

import java.util.List;

import com.camut.model.Discount;
import com.camut.model.Restaurants;
import com.camut.model.api.DiscountApiModel;
import com.camut.pageModel.PageDiscount;

/**
 * @daoimpl DiscountService.java
 * @author zhangyunfei
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface DiscountService {

	/**
	 * @Title: getDiscountByRestaurantId
	 * @Description: 店铺优惠信息
	 * @param:  
	 * @return: List<DiscountApiModel>
	 */
	public List<DiscountApiModel> getDiscountByRestaurantId(String restaurantUuid, int orderType, double consumePrice);
	
	/**
	 * @Title: getAllDiscounts
	 * @Description: 获取商家的所有优惠信息
	 * @param:    Restaurants
	 * @return: List<PageDiscount>
	 */
	public List<PageDiscount> getAllDiscounts(Restaurants restaurants);
	
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
	public int getDiscountByDishId(String dishId);
	
	/**
	 * @Title: chooseDistance
	 * @Description: 用户选择使用优惠券时，查找出原先使用的和将要使用的优惠券信息 只会一条或者两条信息
	 * @param: @param oldDistanceId
	 * @param: @param newDistanceId
	 * @return List<PageDiscount>  
	 */
	public List<PageDiscount> chooseDiscount(String oldDiscountId, String newDiscountId);
	
	/**
	 * @Title: getDiscountById
	 * @Description:通过id获取一条优惠信息 
	 * @param: @param discountId
	 * @return Discount  
	 */
	public PageDiscount getDiscountById(long discountId);
	
	
	/**
	 * @Title: getDiscountById
	 * @Description:通过id获取一条优惠信息 
	 * @param: @param discountId
	 * @return Discount  
	 */
	public Discount getDiscount(long discountId);
	
	
}
