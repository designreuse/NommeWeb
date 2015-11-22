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
	public int addFavorites(String consumerUuid, String restaurantUuid) {
		if (StringUtil.isNotEmpty(consumerUuid)) {
			ConsumersFavorites cf = consumersFavoritesDao.existFavoritesByConsumerUuidAndrestaurantUuid(consumerUuid, restaurantUuid);
			if(cf != null){
				return consumersFavoritesDao.deleteFavoritesByRidCid(consumerUuid, restaurantUuid);
			}else {
				return consumersFavoritesDao.addFavorites(consumerUuid, restaurantUuid);
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
	public List<ConsumerFavoritesApiModel> selectFavorites(String consumerUuid) {
		List<ConsumersFavorites> cfList = consumersFavoritesDao.selectFavorites(consumerUuid);
		List<ConsumerFavoritesApiModel> cfamList = new ArrayList<ConsumerFavoritesApiModel>();
		for (ConsumersFavorites consumersFavorites : cfList) {
			ConsumerFavoritesApiModel cfam = new ConsumerFavoritesApiModel();
			Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(consumersFavorites.getRestaurantsUuid());
			if(restaurants != null){
				ViewRestaurant vr = viewRestaurantService.getRestaurantScore(restaurants.getUuid());
				cfam.setRestaurantUuid(consumersFavorites.getRestaurantsUuid());
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
	public int existFavoritesByConsumerUuidAndrestaurantUuid (String consumerUuid, String restaurantUuid){
		int temp = 0;
		ConsumersFavorites consumersFavorites = consumersFavoritesDao.existFavoritesByConsumerUuidAndrestaurantUuid (consumerUuid, restaurantUuid);
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
	public long addConsumerFavorite(String consumerUuid, String restaurantUuid){
		ConsumersFavorites consumersFavorites = new ConsumersFavorites();
		Consumers consumers = new Consumers();
		consumers.setUuid(consumerUuid);
		consumersFavorites.setConsumersUuid(consumerUuid);
		consumersFavorites.setRestaurantsUuid(restaurantUuid);
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
	public int deleteConsumerFavorite(String consumerUuid, String restaurantUuid){
		ConsumersFavorites consumersFavorites = consumersFavoritesDao.existFavoritesByConsumerUuidAndrestaurantUuid(consumerUuid, restaurantUuid);
		if(consumersFavorites!=null){
			int temp = consumersFavoritesDao.deleteFavorites(consumersFavorites.getId());
			return temp;
		}
		return -1;
		
	}
	
	/**
	 * @Title: getFavouriteListByConsumerUuid
	 * @Description: 获取用户收藏的商家列表（用于Web）
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageFavourites>  
	 */
	public List<PageFavourites> getFavouriteListByConsumerUuid(String consumerUuid, PageFilter pf){
		return consumersFavoritesDao.getFavouriteListByconsumerUuid(consumerUuid,pf);
	}
	
	/**
	 * @Title: countTotalByConsumerId
	 * @Description: 获取用户收藏的商家数量
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int countTotalByConsumerUuid(String consumerUuid){
		return consumersFavoritesDao.countTotalByConsumerUuid(consumerUuid);
	}
	
}
