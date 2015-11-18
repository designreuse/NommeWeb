package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.ConsumersFavoritesDao;
import com.camut.dao.RestaurantsDao;
import com.camut.model.Consumers;
import com.camut.model.ConsumersFavorites;
import com.camut.model.Restaurants;
import com.camut.model.ViewRestaurant;
import com.camut.model.api.ConsumerFavoritesApiModel;
import com.camut.pageModel.PageFavourites;
import com.camut.pageModel.PageFilter;
import com.camut.service.ConsumersFavoritesService;
import com.camut.service.ViewRestaurantService;
import com.camut.utils.StringUtil;

@Service
public class ConsumersFavoritesServiceImpl implements ConsumersFavoritesService {

	@Autowired private ConsumersFavoritesDao consumersFavoritesDao; // 自动注入cmersFavoritesDao
	@Autowired private RestaurantsDao restaurantsDao;
	@Autowired private ViewRestaurantService viewRestaurantService;

	/**
	 * @Title: addFavorites
	 * @Description: 用户添加收藏
	 * @param:  rid  cid
	 * @return: -1表示添加失败，1 表示添加成功
	 */
	@Override
	public int addFavorites(long consumerId, int restaurantId) {
		if (restaurantId > 0 && consumerId > 0) {
			ConsumersFavorites cf = consumersFavoritesDao.existFavoritesByConsumerIdAndrestaurantId(consumerId, restaurantId);
			if(cf != null){
				return consumersFavoritesDao.deleteFavoritesByRidCid(consumerId, restaurantId);
			}else {
				return consumersFavoritesDao.addFavorites(consumerId, restaurantId);
			}
		}
		return -1;
	}

	/**
	 * @Title: selectFavorites
	 * @Description: 用户收藏列表
	 * @param:  id
	 * @return: List<ConsumerFavoritesApiModel>
	 */
	@Override
	public List<ConsumerFavoritesApiModel> selectFavorites(long id) {
		List<ConsumersFavorites> cfList = consumersFavoritesDao.selectFavorites(id);
		List<ConsumerFavoritesApiModel> cfamList = new ArrayList<ConsumerFavoritesApiModel>();
		for (ConsumersFavorites consumersFavorites : cfList) {
			ConsumerFavoritesApiModel cfam = new ConsumerFavoritesApiModel();
			Restaurants restaurants = restaurantsDao.getRestaurantsById(consumersFavorites.getRestaurantsId());
			if(restaurants != null){
				ViewRestaurant vr = viewRestaurantService.getRestaurantScore(restaurants.getId());
				cfam.setRestaurantId(consumersFavorites.getRestaurantsId());
				cfam.setRestaurantAddress(restaurants.getRestaurantAddress());
				cfam.setRestaurantName(restaurants.getRestaurantName());
				if(vr.getScore() != null){
					cfam.setStars(StringUtil.convertLastDouble(vr.getScore()));
				} else {
					cfam.setStars(0);
				}
				cfamList.add(cfam);
			}
		}
		return cfamList;
	}

	/**
	 * @Title: deleteFavorites
	 * @Description: 用户删除收藏
	 * @param:   id
	 * @return: 1：删除成功 -1：删除失败
	 */
	@Override
	public int deleteFavorites(long id) {
		if (id != 0 && id != 0) {
			return consumersFavoritesDao.deleteFavorites(id);
		}
		return -1;
	}
	
	
	/**
	 * @Title: existFavoritesByConsumerIdAndrestaurantId
	 * @Description: 通过用户通过商家id和用户id查找当前用户是否收藏了当前商家
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return int  
	 */
	public int existFavoritesByConsumerIdAndrestaurantId (long consumerId, int restaurantId){
		int temp = 0;
		ConsumersFavorites consumersFavorites = consumersFavoritesDao.existFavoritesByConsumerIdAndrestaurantId (consumerId, restaurantId);
		if(consumersFavorites!=null){
			temp = 1;
		}else{
			temp = -1;
		}
		return temp;
	}
	
	/**
	 * @Title: addConsumerFavorite
	 * @Description: 用户新增收藏店铺
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return long  
	 */
	public long addConsumerFavorite(long consumerId, int restaurantId){
		ConsumersFavorites consumersFavorites = new ConsumersFavorites();
		Consumers consumers = new Consumers();
		consumers.setId(consumerId);
		consumersFavorites.setConsumers(consumers);
		consumersFavorites.setRestaurantsId(restaurantId);
		consumersFavorites.setFavoritesdate(new Date());
		return consumersFavoritesDao.addConsumerFavorite(consumersFavorites);
	}
	
	/**
	 * @Title: deleteConsumerFavorite
	 * @Description: 用户删除收藏店铺
	 * @param: long consumerId
	 * @param: int restaurantId
	 * @return int
	 */
	public int deleteConsumerFavorite(long consumerId, int restaurantId){
		ConsumersFavorites consumersFavorites = consumersFavoritesDao.existFavoritesByConsumerIdAndrestaurantId(consumerId, restaurantId);
		if(consumersFavorites!=null){
			int temp = consumersFavoritesDao.deleteFavorites(consumersFavorites.getId());
			return temp;
		}
		return -1;
		
	}
	
	/**
	 * @Title: getFavouriteListByconsumerId
	 * @Description: 获取用户收藏的商家列表（用于Web）
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageFavourites>  
	 */
	public List<PageFavourites> getFavouriteListByconsumerId(int consumerId, PageFilter pf){
		return consumersFavoritesDao.getFavouriteListByconsumerId(consumerId,pf);
	}
	
	/**
	 * @Title: countTotalByConsumerId
	 * @Description: 获取用户收藏的商家数量
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int countTotalByConsumerId(int consumerId){
		return consumersFavoritesDao.countTotalByConsumerId(consumerId);
	}
	
}
