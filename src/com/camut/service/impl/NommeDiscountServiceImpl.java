package com.camut.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.NommeDiscountDao;
import com.camut.framework.constant.GlobalConstant.DISCOUNT_TYPE;
import com.camut.model.NommeDiscount;
import com.camut.service.NommeDiscountService;

/**
 * @daoimpl NommeDiscountServiceImpl.java
 * @author Cuong Ich Truong
 * @createtime 2016 01 16
 * @author
 * @updateTime
 * @memo
 */
@Service
public class NommeDiscountServiceImpl implements NommeDiscountService {

	@Autowired private NommeDiscountDao nommeDiscountDao;


	/**
	 * @Title: getAllNommeDiscounts
	 * @Description: get all non-deleted nomme discounts
	 * @param:    Restaurants
	 * @return: List<PageDiscount>
	 */
	@Override
	public List<NommeDiscount> getAllNommeDiscounts() {
		List<NommeDiscount> list = nommeDiscountDao.getAllNommeDiscounts();
		return list;
	}

	/**
	 * @Title: addNommeDiscount
	 * @Description:
	 * @param:    Discount
	 * @return: int
	 */
	@Override
	public int addNommeDiscount(NommeDiscount nommeDiscount) {
		if (nommeDiscount!=null) {
			return nommeDiscountDao.addNommeDiscount(nommeDiscount);
		}
		return -1;
	}

	/**
	 * @Title: updateNommeDiscount
	 * @Description:
	 * @param:   Discount 
	 * @return: int
	 */
	@Override
	public int updateNommeDiscount(NommeDiscount nommeDiscount) {
		if (nommeDiscount!=null) {
			return nommeDiscountDao.updateNommeDiscount(nommeDiscount);
		}
		return -1;
	}

	/**
	 * @Title: deleteNommeDiscount
	 * @Description: soft delete
	 * @param:    Discount
	 * @return: int
	 */
	@Override
	public int deleteNommeDiscount(NommeDiscount nommeDiscount) {
		if (nommeDiscount!=null) {
			return nommeDiscountDao.deleteNommeDiscount(nommeDiscount);
		}
		return -1;
	}

	/**
	 * @Title: getNommeDiscountByDishId
	 * @Description:
	 * @param:   dish id 
	 * @return: NommeDiscount
	 */
	@Override
	public NommeDiscount getNommeDiscountByDishId(String dishId) {
		if(dishId!=null && dishId.length()>0){
			NommeDiscount nommeDiscount = nommeDiscountDao.getNommeDiscountByDishId(Integer.parseInt(dishId));
			return nommeDiscount;
		}
		return null;
	}
	
	
	/**
	 * @Title: getNommeDiscountByUuid
	 * @Description: get nomme discount by uuid
	 * @param uuid
	 * @return NommeDiscount
	 */
	@Override
	public NommeDiscount getNommeDiscountByUuid(String uuid){
		NommeDiscount nommeDiscount = nommeDiscountDao.getNommeDiscountByUuid(uuid);
		return nommeDiscount;
	}
	
	/**
	 * @Title: getNommeDiscountByCouponCode
	 * @Description: get nomme discount by couponCode
	 * @param couponCode
	 * @return List<NommeDiscount>
	 */
	@Override
	public List<NommeDiscount> getNommeDiscountByCouponCode(String couponCode){
		List<NommeDiscount> list = nommeDiscountDao.getNommeDiscountByCouponCode(couponCode);
		return list;
	}
	
	/**
	 * @Title: validateCouponCode
	 * @Description: Verifies that the given coupon code is valid.
	 * @param couponCode
	 * @return boolean
	 */
	@Override
	public boolean validateCouponCode(String couponCode) {
		System.out.println("Inside validateCouponCode");
		
		// Get the coupon that matches the given code.
		List<NommeDiscount> nommeDiscountList = nommeDiscountDao.getNommeDiscountByCouponCode(couponCode);
		System.out.println("Got coupon list");
		if (nommeDiscountList != null && nommeDiscountList.size() > 0) {
			NommeDiscount nommeDiscount = nommeDiscountList.get(0);
			System.out.println("Got coupon at index 0");
			
			// Verify that the coupon is not expired.
			// TODO: Use the consumer's address for local time.
			Date localDate = new Date();
			long localTime = localDate.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy, hh:mm aaa", Locale.CANADA);
			long startTime = 0;
			long endTime = 0;
			try {
				startTime = sdf.parse(nommeDiscount.getStartTime()).getTime();
				endTime = sdf.parse(nommeDiscount.getEndTime()).getTime();
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
			if (localTime < startTime || localTime > endTime) {
				return false;
			}
			System.out.println("Verified coupon expiration date");

			// Verify that the coupon is available for use.
			if (nommeDiscount.getMaxUses() <= nommeDiscount.getUsedCount()) {
				return false;
			}
			System.out.println("Got coupon usage count");

			// Verify that the coupon has a valid type.
			if (nommeDiscount.getType() != null && nommeDiscount.getDiscount() != null) {
				System.out.println("Coupon type is valid");
				if (nommeDiscount.getType() == DISCOUNT_TYPE.PERCENTAGE_COUPON.getValue()) {
					System.out.println("Coupon type is percentage");
					return true;
				}
			}
		}
		System.out.println("Defaulting to false");
		return false;
	}
}
