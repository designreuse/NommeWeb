/**
 * 
 */
package com.camut.service;

import java.util.List;
import com.camut.model.ViewRestaurant;
import com.camut.model.api.SearchRestaurantsApiModel;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageViewRestaurant;;

/**
 * @ClassName ViewRestaurantService.java
 * @author wangpin
 * @createtime Jul 9, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ViewRestaurantService {

	/**
	 * @Title: getRestaurants
	 * @Description:获取商家
	 * @param:    
	 * @return: List<PageViewRestaurant>
	 */
	public PageModel getRestaurants(ViewRestaurant viewRestaurant,PageFilter pf);
	
	/**
	 * @Title: getRestaurantsForMap
	 * @Description: 获取商家
	 * @param:    
	 * @return: List<PageViewRestaurant>
	 */
	public List<PageViewRestaurant> getRestaurantsForMap(ViewRestaurant viewRestaurant,PageFilter pf);
	
	/**
	 * @Title: getSearchRestaurants
	 * @Description: 商家搜索
	 * @param:    viewRestaurant pf
	 * @return: PageModel
	 */
	public PageModel getSearchRestaurants(SearchRestaurantsApiModel viewRestaurant,PageFilter pf);
	
	/**
	 * @Title: getSearchRestaurantsCount
	 * @Description: 商家数量
	 * @param:    viewRestaurant pf
	 * @return: PageModel
	 */
	public PageModel getSearchRestaurantsCount(SearchRestaurantsApiModel viewRestaurant,PageFilter pf);
	
	/**
	 * @Title: getRestaurantScore
	 * @Description: 商家评分
	 * @param:    
	 * @return: ViewRestaurant
	 */
	public ViewRestaurant getRestaurantScore(String restaurantUuid);
}
