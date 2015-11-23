package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.DiscountDao;
import com.camut.model.Discount;
import com.camut.model.Restaurants;

/**
 * @dao DiscountDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class DiscountDaoImpl extends BaseDao<Discount> implements DiscountDao {

	/**
	 * @Title: getDiscountByRestaurantId
	 * @Description: 店铺优惠信息
	 * @param:  restaurantId
	 * @return: List<Discount>
	 */
	@Override
	public List<Discount> getDiscountByRestaurantUuid(String restaurantUuid, int orderType, double consumePrice) {
		String hql = "from Discount d where d.restaurants.uuid=:restaurantUuid and d.orderType=:orderType and d.consumePrice<="+consumePrice;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		map.put("orderType", orderType);
		List<Discount> dList = this.find(hql, map);
		return dList;
	}

	/**
	 * @Title: getAllDiscounts
	 * @Description: 获取商家的所有优惠信息
	 * @param:    Restaurants
	 * @return: List<PageDiscount>
	 */
	@Override
	public List<Discount> getAllDiscounts(Restaurants restaurants) {
		String hql = "from Discount d where d.restaurants=:restaurants order by d.type";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurants", restaurants);
		List<Discount> list = this.find(hql,map);
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
		try {
			this.save(discount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateDiscount
	 * @Description: 修改优惠信息
	 * @param:   Discount 
	 * @return: int
	 */
	@Override
	public int updateDiscount(Discount discount) {
		try {
			this.update(discount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteDiscount
	 * @Description: 删除优惠信息
	 * @param:    Discount
	 * @return: int
	 */
	@Override
	public int deleteDiscount(Discount discount) {
		try {
			this.delete(discount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}


	/**
	 * @Title: getDiscountByDishId
	 * @Description: 根据菜品id查询优惠信息中有没有用到此菜品
	 * @param:   String 
	 * @return: int -1没用到  1用到
	 */
	@Override
	public int getDiscountByDishId(int dishId) {
		String hql = "select count(*) from Discount d where d.dishId=:dishId";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dishId", dishId);
		return this.count(hql, map).intValue();
	}
	
	
	/**
	 * @Title: chooseDistance
	 * @Description: 用户选择使用优惠券时，查找出原先使用的和将要使用的优惠券信息 只会一条或者两条信息
	 * @param: @param idMap
	 * @return List<PageDiscount>  
	 */
	public List<Discount> chooseDiscount(Map<String, Object> idMap){
		long oldId = 0;
		long newId = 0;
		String hql = null;
		if(idMap.get("oldId")!=null){
			oldId = Long.parseLong(idMap.get("oldId").toString());
		}
		if(idMap.get("newId")!=null){
			newId = Long.parseLong(idMap.get("newId").toString());
		}
		if(oldId>0){
			hql = "from Discount d where d.id in ( "+oldId+" , "+newId+" )";
		}else{
			hql = "from Discount d where d.id in ( "+newId+" )";
		}
		List<Discount> discountList = this.find(hql);
		return discountList;
	}

	@Override
	public Discount getDiscount(long discountId) {
		return this.get("from Discount d where d.id ="+discountId);
	}
	
}
