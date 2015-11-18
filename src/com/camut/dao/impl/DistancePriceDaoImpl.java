/**
 * 
 */
package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.DistancePriceDao;
import com.camut.model.DistancePrice;
import com.camut.model.Restaurants;

/**
 * @ClassName DistancePriceDaoImpl.java
 * @author wangpin
 * @createtime Jun 28, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class DistancePriceDaoImpl extends BaseDao<DistancePrice> implements DistancePriceDao {

	/**
	 * @Title: getAllDistancePrice
	 * @Description: 获取商家所有的配送费信息
	 * @param:    Restaurants
	 * @return: DistancePrice
	 */
	@Override
	public List<DistancePrice> getAllDistancePrice(Restaurants restaurants) {
		String hql = "from DistancePrice d where d.restaurants=:restaurants order by startDistance";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("restaurants", restaurants);
		return this.find(hql, map);
	}

	/**
	 * @Title: addDistancePrice
	 * @Description: 增加配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	@Override
	public int addDistancePrice(DistancePrice distancePrice) {
		try {
			this.save(distancePrice);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateDistancePrice
	 * @Description: 修改配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	@Override
	public int updateDistancePrice(DistancePrice distancePrice) {
		try {
			this.update(distancePrice);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteDistancePrice
	 * @Description: 删除配送收费信息
	 * @param:    DistancePrice
	 * @return: int
	 */
	@Override
	public int deleteDistancePrice(DistancePrice distancePrice) {
		try {
			this.delete(distancePrice);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getDistancePrice
	 * @Description: 获取配送费
	 * @param: restaurantsApiModel
	 * @return List<DistancePrice> 
	 */
	public List<DistancePrice> getDistancePrice(String restaurantUuid){
		String hql = "from DistancePrice dp where dp.restaurants.uuid=:restaurantUuid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		List<DistancePrice> pdList = this.find(hql, map);
		return pdList;
	}
}
