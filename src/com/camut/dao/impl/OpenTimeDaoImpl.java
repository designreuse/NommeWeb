package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.OpenTimeDao;
import com.camut.model.OpenTime;

/**
 * @dao OpenTimeDaoImpl.java
 * @author zyf
 * @createtime 6 08, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class OpenTimeDaoImpl extends BaseDao<OpenTime> implements OpenTimeDao {

	/**
	 * @Title: selectOpenTime
	 * @Description: 营业时间显示
	 * @param:  restaurantId
	 * @return: List<OpenTime>
	 */
	@Override
	public List<OpenTime> selectOpenTime(long restaurantId) {
		String hql = "from OpenTime o where o.restaurants.id=:restaurantId order by o.type,o.week,o.starttime";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		List<OpenTime> otList = this.find(hql, map);
		return otList;
	}

	/**
	 * @Title: addOpenTime
	 * @Description: 增加营业时间
	 * @param:    OpenTime
	 * @return: int
	 */
	@Override
	public int addOpenTime(OpenTime openTime) {
		try {
			this.save(openTime);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteOpenTime
	 * @Description: 删除营业时间
	 * @param:    OpenTime
	 * @return: int
	 */
	@Override
	public int deleteOpenTime(OpenTime openTime) {
		String hql = "delete OpenTime o where o.restaurants=:restaurant and o.week=:week and o.type=:type";
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("restaurant", openTime.getRestaurants());
		map.put("week", openTime.getWeek());
		map.put("type", openTime.getType());
		try {
			this.executeHql(hql, map);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getOpenTime
	 * @Description: 获取当天的营业时间
	 * @param:  restaurantId
	 * @return: List<OpenTime>
	 */
	@Override
	public List<OpenTime> getOpenTime(long restaurantId, int type, int week) {
		String hql = "from OpenTime o where o.restaurants.id=:restaurantId and o.type=:type and o.week=:week";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		map.put("type", type);
		map.put("week", week);
		List<OpenTime> otList = this.find(hql, map);
		return otList;
	}

	/**
	 * @Title: getOpenTimeByRestaurantIdAndType
	 * @Description: 根据商家id和订单类型查找营业时间
	 * @param:  restaurantId type
	 * @return: List<OpenTime>
	 */
	@Override
	public List<OpenTime> getOpenTimeByRestaurantIdAndType(long restaurantId, int type) {
		String hql = "from OpenTime o where o.restaurants.id=:restaurantId and o.type=:type";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantId", restaurantId);
		map.put("type", type);
		return this.find(hql, map);
	}
}
