package com.camut.service;

import java.util.List;
import com.camut.model.api.ConsumerFavoritesApiModel;
import com.camut.pageModel.PageFavourites;
import com.camut.pageModel.PageFilter;

/**
 * @Service CustomerFavoritesService.java
 * @author zhangyunfei 
 * @createtime May 27, 2015 
 * @author
 * @updateTime
 * @memo
 */
public interface ConsumersFavoritesService {

	/**
	 * @Title: addFavorites
	 * @Description: 用户添加收藏
	 * @param:  rid  cid
	 * @return: -1表示添加失败，1 表示添加成功
	 */
	public int addFavorites(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: selectFavorites
	 * @Description: 用户收藏列表
	 * @param:  id
	 * @return: List<ConsumerFavoritesApiModel>
	 */
	public List<ConsumerFavoritesApiModel> selectFavorites(String consumerUuid);
	
	/**
	 * @Title: deleteFavorites
	 * @Description: 用户删除收藏
	 * @param:   id
	 * @return: 1：删除成功 -1：删除失败
	 */
	public int deleteFavorites(long id);
	
	/**
	 * @Title: existFavoritesByConsumerUuidAndrestaurantUuid 
	 * @Description: 通过用户通过商家id和用户id查找当前用户是否收藏了当前商家
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return int  
	 */
	public int existFavoritesByConsumerUuidAndrestaurantUuid (String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: addConsumerFavorite
	 * @Description: 用户新增收藏店铺
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return long  
	 */
	public long addConsumerFavorite(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: deleteConsumerFavorite
	 * @Description: 用户删除收藏店铺
	 * @param: long consumerId
	 * @param: int restaurantId
	 * @return int
	 */
	public int deleteConsumerFavorite(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: getFavouriteListByConsumerUuid
	 * @Description: 获取用户收藏的商家列表（用于Web）
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageFavourites>  
	 */
	public List<PageFavourites> getFavouriteListByConsumerUuid(String consumerUuid, PageFilter pf);
	
	/**
	 * @Title: countTotalByConsumerId
	 * @Description: 获取用户收藏的商家数量
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int countTotalByConsumerUuid(String consumerUuid);
}
