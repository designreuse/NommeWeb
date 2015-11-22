package com.camut.dao;

import java.util.List;

import com.camut.model.ConsumersFavorites;
import com.camut.pageModel.PageFavourites;
import com.camut.pageModel.PageFilter;

/**
 * @dao CustomerFavoritesDao.java
 * @author zhangyunfei
 * @createtime May 27, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersFavoritesDao {
	
	/**
	 * @Title: addFavorites
	 * @Description: 用户添加收藏
	 * @param:  
	 * @return: -1表示新增失败 ，1表示新增成功
	 */
	public int addFavorites(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: selectFavorites
	 * @Description: 用户收藏列表
	 * @param:  
	 * @return: List<CustomerFavorites>
	 */
	public List<ConsumersFavorites> selectFavorites(String consumerUuid);
	
	/**
	 * @Title: deleteFavorites
	 * @Description: 用户删除收藏
	 * @param:   
	 * @return: 1：删除成功 -1：删除失败
	 */
	public int deleteFavorites(long id);
	
	/**
	 * @Title: existFavoritesByConsumerIdAndrestaurantId
	 * @Description: 通过用户通过商家id和用户id查找当前用户是否收藏了当前商家
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return ConsumersFavorites  
	 */
	public ConsumersFavorites existFavoritesByConsumerUuidAndrestaurantUuid (String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: addConsumerFavorite
	 * @Description: 用户新增收藏店铺
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return long  
	 */
	public long addConsumerFavorite(ConsumersFavorites consumersFavorites);
	
	/**
	 * @Title: deleteFavoritesByRidCid
	 * @Description: 用户删除收藏
	 * @param:  
	 * @return: -1表示新增失败 ，1表示新增成功
	 */
	public int deleteFavoritesByRidCid(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: getFavouriteListByconsumerId
	 * @Description: 获取用户收藏的商家列表（用于Web）
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageFavourites>  
	 */
	public List<PageFavourites> getFavouriteListByconsumerUuid(String consumerUuid, PageFilter pf);
	
	/**
	 * @Title: countTotalByConsumerId
	 * @Description: 获取用户收藏的商家数量
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int countTotalByConsumerUuid(String consumerUuid);
	
	
	
	
}

