package com.camut.dao;

import java.util.List;
import java.util.Map;
import com.camut.model.Discount;
import com.camut.model.NommeDiscount;
import com.camut.model.Restaurants;

/**
 * @dao DiscountDao.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface NommeDiscountDao {

	
	/**
	 * @Title: getAllDiscounts
	 * @Description: get all nomme discounts
	 * @param:    
	 * @return: List<NommeDiscount>
	 */
	public List<NommeDiscount> getAllNommeDiscounts();
	
	/**
	 * @Title: addNommeDiscount
	 * @Description: 
	 * @param:    Discount
	 * @return: int
	 */
	public int addDiscount(NommeDiscount nommeDiscount);
	
	/**
	 * @Title: updateNommeDiscount
	 * @Description: 
	 * @param:   NommeDiscount 
	 * @return: int
	 */
	public int updateNommeDiscount(NommeDiscount nommeDiscount);
	
	/**
	 * @Title: deleteNommeDiscount
	 * @Description: soft delete
	 * @param:    NommeDiscount
	 * @return: int
	 */
	public int deleteNommeDiscount(NommeDiscount nommeDiscount);
	
	/**
	 * @Title: getDiscountByDishId
	 * @Description: get discount by free item's id
	 * @param:   int 
	 * @return: NommeDiscount
	 */
	public NommeDiscount getDiscountByDishId(int dishId);
	
	/**
	 * @Title: getDiscountByUuid
	 * @Description: get discount by uuid
	 * @param discountId
	 * @return NommeDiscount
	 */
	public NommeDiscount getDiscountByUuid(String uuid);
	
	/**
	 * @Title: hardDeleteNommeDiscount
	 * @Description: WARINING: removing the record from the table cannot be undone
	 * @param:    NommeDiscount
	 * @return: int
	 */
	public int hardDeleteNommeDiscount(NommeDiscount nommeDiscount);
}
