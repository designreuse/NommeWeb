package com.camut.dao;

import java.util.List;
import com.camut.model.NommeDiscount;

/**
 * @dao NommeDiscountDao.java
 * @author Cuong Ich Truong
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
	 * @param: Discount
	 * @return: int
	 */
	public int addNommeDiscount(NommeDiscount nommeDiscount);

	/**
	 * @Title: updateNommeDiscount
	 * @Description:
	 * @param: NommeDiscount
	 * @return: int
	 */
	public int updateNommeDiscount(NommeDiscount nommeDiscount);

	/**
	 * @Title: deleteNommeDiscount
	 * @Description: soft delete
	 * @param: NommeDiscount
	 * @return: int
	 */
	public int deleteNommeDiscount(NommeDiscount nommeDiscount);

	/**
	 * @Title: getDiscountByDishId
	 * @Description: get discount by free item's id
	 * @param: int
	 * @return: NommeDiscount
	 */
	public NommeDiscount getNommeDiscountByDishId(int dishId);

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

	/**
	 * @Title: hardDeleteNommeDiscount
	 * @Description: WARINING: removing the record from the table cannot be
	 *               undone
	 * @param: NommeDiscount
	 * @return: int
	 */
	public int hardDeleteNommeDiscount(NommeDiscount nommeDiscount);
}
