package com.camut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.DiscountDao;
import com.camut.dao.DishDao;
import com.camut.model.Discount;
import com.camut.model.Dish;
import com.camut.model.Restaurants;
import com.camut.model.api.DiscountApiModel;
import com.camut.pageModel.PageDiscount;
import com.camut.service.DiscountService;
import com.camut.utils.StringUtil;

/**
 * @daoimpl DiscountServiceImpl.java
 * @author zhangyunfei
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private DiscountDao discountDao;
	
	@Autowired
	private DishDao dishDao;

	/**
	 * @Title: getDiscountByRestaurantId
	 * @Description: 店铺优惠信息
	 * @param:  restaurantId
	 * @return: List<DiscountApiModel>
	 */
	@Override
	public List<DiscountApiModel> getDiscountByRestaurantId(long restaurantId, int orderType, double consumePrice) {
		List<Discount> dList = discountDao.getDiscountByRestaurantId(restaurantId, orderType, consumePrice);
		List<DiscountApiModel> damList = new ArrayList<DiscountApiModel>();
		for (Discount discount : dList) {
			DiscountApiModel dam = new DiscountApiModel();
			dam.setDiscountId(discount.getId());
			if(discount.getContent() != null){
				dam.setContent(discount.getContent());
			}
			if(discount.getPrice() != null){
				dam.setPrice(discount.getPrice());
			}
			dam.setConsumePrice(discount.getConsumePrice());
			if(discount.getDiscount() != null){
				dam.setDiscount(discount.getDiscount());
			}
			dam.setType(discount.getType());
			if(discount.getDishId()!=null){				
				Dish dish = dishDao.getDishById((long)discount.getDishId());
				if (dish!=null && dish.getStatus()==0) {
					dam.setDishId(discount.getDishId());
					dam.setDishName(dish.getEnName());
				}
			}
			damList.add(dam);
		}
		return damList;
	}

	/**
	 * @Title: getAllDiscounts
	 * @Description: 获取商家的所有优惠信息
	 * @param:    Restaurants
	 * @return: List<PageDiscount>
	 */
	@Override
	public List<PageDiscount> getAllDiscounts(Restaurants restaurants) {
		List<PageDiscount> list = new ArrayList<PageDiscount>();
		if (restaurants!=null) {
			List<Discount> discounts = discountDao.getAllDiscounts(restaurants);
			for (Discount discount : discounts) {//遍历实体，组装页面对象
				PageDiscount pageDiscount = new PageDiscount();
				BeanUtils.copyProperties(discount, pageDiscount);
				if(discount.getDishId()!=null){
					Dish dish = dishDao.getDishById((long)discount.getDishId());
					if (dish!=null && dish.getStatus()==0) {
						pageDiscount.setDishName(dish.getEnName());
					}
				}
				list.add(pageDiscount);
			}
		}
		
		return list;
	}

	/**
	 * @Title: addDiscount
	 * @Description: 增加优惠信息
	 * @param:    Discount
	 * @return: int
	 */
	@Override
	public int addDiscount(Discount discount) {
		if (discount!=null) {
			return discountDao.addDiscount(discount);
		}
		return -1;
	}

	/**
	 * @Title: updateDiscount
	 * @Description: 修改优惠信息
	 * @param:   Discount 
	 * @return: int
	 */
	@Override
	public int updateDiscount(Discount discount) {
		if (discount!=null) {
			return discountDao.updateDiscount(discount);
		}
		return -1;
	}

	/**
	 * @Title: deleteDiscount
	 * @Description: 删除优惠信息
	 * @param:    Discount
	 * @return: int
	 */
	@Override
	public int deleteDiscount(Discount discount) {
		if (discount!=null) {
			return discountDao.deleteDiscount(discount);
		}
		return -1;
	}

	/**
	 * @Title: getDiscountByDishId
	 * @Description: 根据菜品id查询优惠信息中有没有用到此菜品
	 * @param:   String 
	 * @return: int -1没用到 1用到
	 */
	@Override
	public int getDiscountByDishId(String dishId) {
		if(dishId!=null && dishId.length()>0){
			int flag = discountDao.getDiscountByDishId(Integer.parseInt(dishId));
			if(flag!=0){
				return 1;
			}
		}
		return -1;
	}
	
	/**
	 * @Title: chooseDistance
	 * @Description: 用户选择使用优惠券时，查找出原先使用的和将要使用的优惠券信息 只会一条或者两条信息
	 * @param: @param oldDistanceId
	 * @param: @param newDistanceId
	 * @return List<PageDiscount>  
	 */
	public List<PageDiscount> chooseDiscount(String oldDiscountId, String newDiscountId){
		Map<String, Object> idMap = new HashMap<String, Object>(); 
		if(StringUtil.isNotEmpty(newDiscountId)){
			idMap.put("newId", Long.parseLong(newDiscountId));
		}
		if(StringUtil.isNotEmpty(oldDiscountId)){
			idMap.put("oldId", Long.parseLong(oldDiscountId));
		}
		List<PageDiscount> pageDiscountsList = new ArrayList<PageDiscount>();
		List<Discount> discountList = discountDao.chooseDiscount(idMap);
		for (Discount discount : discountList) {
			PageDiscount pageDiscount = new PageDiscount();
			BeanUtils.copyProperties(discount, pageDiscount);
			pageDiscountsList.add(pageDiscount);
		}
		return pageDiscountsList;
		
	}
	
	/**
	 * @Title: getDiscountById
	 * @Description:通过id获取一条优惠信息 
	 * @param: @param discountId
	 * @return Discount  
	 */
	public PageDiscount getDiscountById(long discountId){
		Discount discount = discountDao.getDiscount(discountId);
		PageDiscount pageDiscount = new PageDiscount();
		BeanUtils.copyProperties(discount, pageDiscount);
		
		return pageDiscount;
	}

	/**
	 * @Title: getDiscountById
	 * @Description:通过id获取一条优惠信息 
	 * @param: @param discountId
	 * @return Discount  
	 */
	@Override
	public Discount getDiscount(long discountId) {
		return discountDao.getDiscount(discountId);
	}
	
	
	
}
