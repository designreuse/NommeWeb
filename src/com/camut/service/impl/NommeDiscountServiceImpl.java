package com.camut.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.DishDao;
import com.camut.dao.NommeDiscountDao;
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

	@Autowired
	private NommeDiscountDao nommeDiscountDao;
	
	@Autowired
	private DishDao dishDao;


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

}
