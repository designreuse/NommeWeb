package com.camut.dao;

import java.util.List;

import com.camut.model.OpenTime;

/**
 * @dao OpenTimeDao.java
 * @author zhangyunfei
 * @createtime 6 08, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface OpenTimeDao {

	/**
	 * @Title: selectOpenTime
	 * @Description: 营业时间显示
	 * @param:  restaurantId
	 * @return: List<OpenTime>
	 */
	public List<OpenTime> selectOpenTime(String restaurantUuid);
	
	/**
	 * @Title: addOpenTime
	 * @Description: 增加营业时间
	 * @param:    OpenTime
	 * @return: int
	 */
	public int addOpenTime(OpenTime openTime);

	
	/**
	 * @Title: deleteOpenTime
	 * @Description: 删除营业时间
	 * @param:    OpenTime
	 * @return: int
	 */
	public int deleteOpenTime(OpenTime openTime);
	
	/**
	 * @Title: getOpenTime
	 * @Description: 获取当天的营业时间
	 * @param:  restaurantId
	 * @return: List<OpenTime>
	 */
	public List<OpenTime> getOpenTime(String restaurantUuid, int type, int week);
	
	/**
	 * @Title: getOpenTimeByRestaurantIdAndType
	 * @Description: 根据商家id和订单类型查找营业时间
	 * @param:  restaurantId type
	 * @return: List<OpenTime>
	 */
	public List<OpenTime> getOpenTimeByRestaurantUuidAndType(String restaurantUuid, int type);
}
