package com.camut.dao.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.DiscountDao;
import com.camut.framework.constant.GlobalConstant.DELETE_STATUS;
import com.camut.model.Discount;
import com.camut.model.Restaurants;
import com.camut.utils.UDSqlCommand;

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
	 * @param: restaurantId
	 * @return: List<Discount>
	 */
	@Override
	public List<Discount> getDiscountByRestaurantUuid(String restaurantUuid, int orderType, double consumePrice) {
		UDSqlCommand command = new UDSqlCommand();
		command.SelectFrom("Discount").Where("restaurants.uuid=:restaurantUuid").And("orderType=:orderType")
				.And("consumePrice<=:consumePrice").GetNonDeletedRecordsOnly();

		command.AddParameters("restaurantUuid", restaurantUuid);
		command.AddParameters("orderType", orderType);
		command.AddParameters("consumePrice", consumePrice);

		List<Discount> dList = this.find(command);
		return dList;
	}

	/**
	 * @Title: getAllDiscounts
	 * @Description: 获取商家的所有优惠信息
	 * @param: Restaurants
	 * @return: List<PageDiscount>
	 */
	@Override
	public List<Discount> getAllDiscounts(Restaurants restaurants) {
		UDSqlCommand command = new UDSqlCommand();
		command.SelectFrom("Discount").Where("restaurants=:restaurants").GetNonDeletedRecordsOnly().OrderBy("type")
				.WithAscOrder();
		command.AddParameters("restaurants", restaurants);

		List<Discount> list = this.find(command);
		return list;
	}

	/**
	 * @Title: addDiscount
	 * @Description: 增加优惠信息
	 * @param: Discount
	 * @return: int
	 */
	@Override
	public int addDiscount(Discount discount) {
		try {
			discount.setDeleteStatus(DELETE_STATUS.NOT_DELETED.getValue());
			this.save(discount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateDiscount
	 * @Description: 修改优惠信息
	 * @param: Discount
	 * @return: int
	 */
	@Override
	public int updateDiscount(Discount discount) {
		try {
			discount.setDeleteStatus(DELETE_STATUS.NOT_DELETED.getValue());
			this.update(discount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteDiscount
	 * @Description: 删除优惠信息
	 * @param: Discount
	 * @return: int
	 */
	@Override
	public int deleteDiscount(Discount discount) {
		try {
			Discount toDelete = getDiscount(discount.getId());
			toDelete.setDeleteStatus(DELETE_STATUS.DELETED.getValue());
			this.update(toDelete);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getDiscountByDishId
	 * @Description: 根据菜品id查询优惠信息中有没有用到此菜品
	 * @param: String
	 * @return: int -1没用到 1用到
	 */
	@Override
	public int getDiscountByDishId(int dishId) {
		UDSqlCommand command = new UDSqlCommand();
		command.CountFrom("Discount").Where("dishId=:dishId").GetNonDeletedRecordsOnly();
		command.AddParameters("dishId", dishId);
		return this.count(command).intValue();
	}

	/**
	 * @Title: chooseDiscount
	 * @Description: 用户选择使用优惠券时，查找出原先使用的和将要使用的优惠券信息 只会一条或者两条信息
	 * @param: @param
	 *             idMap
	 * @return List<PageDiscount>
	 */
	public List<Discount> chooseDiscount(Map<String, Object> idMap) {
		long oldId = 0;
		long newId = 0;
		UDSqlCommand command = new UDSqlCommand();
		if (idMap.get("oldId") != null) {
			oldId = Long.parseLong(idMap.get("oldId").toString());
		}
		if (idMap.get("newId") != null) {
			newId = Long.parseLong(idMap.get("newId").toString());
		}
		if (oldId > 0) {
			command.SelectFrom("Discount").Where("id In (:oldId, :newId)").GetNonDeletedRecordsOnly();
			command.AddParameters("oldId", oldId);
			command.AddParameters("newId", newId);

		} else {
			command.SelectFrom("Discount").Where("id=:discountId").GetNonDeletedRecordsOnly();
			command.AddParameters("discountId", newId);
		}

		List<Discount> discountList = this.find(command);
		return discountList;
	}

	@Override
	public Discount getDiscount(long discountId) {
		UDSqlCommand command = new UDSqlCommand();
		command.SelectFrom("Discount").Where("id=:discountId").GetNonDeletedRecordsOnly();

		command.AddParameters("discountId", discountId);

		Discount discount = this.get(command);
		return discount;
	}

	@Override
	public int hardDeleteDiscount(Discount discount) {
		try {
			this.delete(discount);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
}
