package com.camut.service;


import java.util.List;

import com.camut.model.NommeDiscount;
import com.camut.pageModel.PageDiscount;

/**
 * @daoimpl NommeDiscountService.java
 * @author Cuong Ich Truong
 * @createtime 2016 01 16
 * @author
 * @updateTime
 * @memo
 */
public interface NommeDiscountService {

	/**
	 * @Title: getAllNommeDiscounts
	 * @Description: get all non-deleted nomme discounts
	 * @param:    Restaurants
	 * @return: List<PageDiscount>
	 */
	public List<NommeDiscount> getAllNommeDiscounts();
	
	/**
	 * @Title: addNommeDiscount
	 * @Description:
	 * @param:    Discount
	 * @return: int
	 */
	public int addNommeDiscount(NommeDiscount nommeDiscount);
	
	/**
	 * @Title: updateNommeDiscount
	 * @Description:
	 * @param:   Discount 
	 * @return: int
	 */
	public int updateNommeDiscount(NommeDiscount nommeDiscount);
	
	/**
	 * @Title: deleteNommeDiscount
	 * @Description: soft delete
	 * @param:    Discount
	 * @return: int
	 */
	public int deleteNommeDiscount(NommeDiscount nommeDiscount);
	
	/**
	 * @Title: getNommeDiscountByDishId
	 * @Description:
	 * @param:   dish id 
	 * @return: NommeDiscount
	 */
	public NommeDiscount getNommeDiscountByDishId(String dishId);
		
	/**
	 * @Title: getNommeDiscountByUuid
	 * @Description: get nomme discount by uuid
	 * @param uuid
	 * @return NommeDiscount
	 */
	public NommeDiscount getNommeDiscountByUuid(String uuid);
	
	
	/**
	 * @Title: getNommeDiscountByCouponCode
	 * @Description: get nomme discount by couponCode
	 * @param couponCode
	 * @return List<NommeDiscount>
	 */
	public List<NommeDiscount> getNommeDiscountByCouponCode(String couponCode);
	
}
