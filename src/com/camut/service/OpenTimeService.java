package com.camut.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.camut.model.OpenTime;
import com.camut.pageModel.PageOpenTime;

/**
 * @Service OpenTimeService.java
 * @author zhangyunfei 
 * @createtime 6 08, 2015 
 * @author
 * @updateTime
 * @memo
 */
public interface OpenTimeService {

	/**
	 * @Title: selectOpenTime
	 * @Description: 营业时间显示
	 * @param:  restaurantId
	 * @return: List<OpenTimeApiModel>
	 */
	public List<String> selectOpenTime(long restaurantId, int type, String date);
	
	/**
	 * @Title: getAllOpenTime
	 * @Description: 获取商家的营业时间
	 * @param:    Restaurants
	 * @return: PageOpenTime
	 */
	public List<PageOpenTime> getAllOpenTime(long restaurantsId);
	
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
	 * @Description: 获取商家的营业时间（moer）
	 * @param:    RestaurantId
	 * @return: List<OpenTimeListApiModel>
	 */
	public List<Map<String, Object>> getOpenTime(long restaurantId);
	
	/**
	 * @Title: orderDateAtOpenTime
	 * @Description: 判断订单时间是否在营业时间内
	 * @param:    Date orderDate,int restaurantId
	 * @return: int -1不在 1在
	 */
	public int orderDateAtOpenTime(Date orderDate,int restaurantId,int orderType);
	
	/**
	 * @Title: getOpenTimeByOrderDate
	 * @Description: 返回营业时间段
	 * @param:    
	 * @return: String[]
	 */
	public String[] getOpenTimeByOrderDate(Date orderDate,long restaurantId,int type);
}
