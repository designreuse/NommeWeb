package com.camut.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.ConsumersFavoritesDao;
import com.camut.model.Consumers;
import com.camut.model.ConsumersFavorites;
import com.camut.pageModel.PageFavourites;
import com.camut.pageModel.PageFilter;

/**
 * @daoimpl ConsumersFavoritesDaoImpl.java
 * @author zhangyunfei
 * @createtime May 26, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class ConsumersFavoritesDaoImpl extends BaseDao<ConsumersFavorites> implements ConsumersFavoritesDao {
	
	/**
	 * @Title: addFavorites
	 * @Description: 用户添加收藏; add a restaurant to Favorites
	 * @param:  rid:restaurant ID, cid: consumer ID
	 * @return: -1表示添加失败，1 表示添加成功; -1:failed, 1: success
	 */
	@Override
	public int addFavorites(String consumerUuid, String restaurantUuid) {
		try {
			ConsumersFavorites cf = new ConsumersFavorites();
			cf.setRestaurantsUuid(restaurantUuid);
			Consumers consumers = new Consumers();
			consumers.setUuid(consumerUuid);
			cf.setConsumersUuid(consumerUuid);
			cf.setFavoritesdate(new Date());
			this.save(cf);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: selectFavorites
	 * @Description: 获得当前用户的收藏列表; get a list of Favorites
	 * @param:  id: consumer's id
	 * @return: List<ConsumersFavorites>
	 */
	@Override
	public List<ConsumersFavorites> selectFavorites(String consumerUuid) {
		String hql = "from ConsumersFavorites cf where cf.consumersUuid=:consumerUuid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		List<ConsumersFavorites> cfList = this.find(hql, map);
		return cfList;
	}
	
	/**
	 * @Title: deleteFavorites
	 * @Description: 用户删除收藏
	 * @param:   id
	 * @return: 1：删除成功 -1：删除失败
	 */
	@Override
	public int deleteFavorites(long id) {
		String hql = "from ConsumersFavorites cf where cf.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		ConsumersFavorites cf = this.get(hql, map);
		try {
			this.delete(cf);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: existFavoritesByConsumerIdAndrestaurantId
	 * @Description: 通过用户通过商家id和用户id查找当前用户是否收藏了当前商家
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return ConsumersFavorites  
	 */
	public ConsumersFavorites existFavoritesByConsumerUuidAndrestaurantUuid (String consumerUuid, String restaurantUuid){
		String hql = "from ConsumersFavorites cf where cf.restaurantsUuid=:restaurantUuid and cf.consumersUuid=:consumerUuid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		map.put("restaurantUuid", restaurantUuid);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: addConsumerFavorite
	 * @Description: 用户新增收藏店铺
	 * @param: @param consumerId
	 * @param: @param restaurantId
	 * @return long  
	 */
	public long addConsumerFavorite(ConsumersFavorites consumersFavorites){
		long favoriteId = (Long)this.save(consumersFavorites);
		return favoriteId;
	}

	/**
	 * @Title: deleteFavoritesByRidCid
	 * @Description: 用户删除收藏
	 * @param:  
	 * @return: -1表示新增失败 ，1表示新增成功
	 */
	@Override
	public int deleteFavoritesByRidCid(String consumerUuid, String restaurantUuid) {
		String hql = "from ConsumersFavorites cf where cf.consumersUuid=:consumerUuid and cf.restaurantsUuid=:restaurantUuid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		map.put("restaurantUuid", restaurantUuid);
		ConsumersFavorites cf = this.get(hql, map);
		try {
			this.delete(cf);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	
	/**
	 * @Title: getFavouriteListByconsumerId
	 * @Description: 获取用户收藏的商家列表（用于Web）
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PageFavourites>  
	 */
	public List<PageFavourites> getFavouriteListByconsumerUuid(String consumerUuid, PageFilter pf){
		String sql = "select d.restaurants_uuid as restaurantUuid, d.id as favouritesId, e.aavgPrice as avgPrice, e.arname as restaurantName, e.cscore as avgStars, e.adelivery as isDelivery, e.apickup as isPickup, "
				+"e.areservation as isReservation, e.adistance as distance, e.adeliveryPrice as deliveryPrice, e.url as imageUrl from tbl_customer_favorites d "
				+"LEFT JOIN ( select a.restaurant_name as arname, a.logourl as url, a.uuid as aruuid, c.scroe as cscore,a.isdelivery as adelivery, "
				+"a.ispickup as apickup, a.isreservation as areservation, a.`status` as `status`, a.distance as adistance, a.deliver_price as adeliveryPrice, a.avg_price as aavgPrice "
				 +"from dat_restaurants a "
					+"LEFT JOIN ( select round(avg(b.score)) as scroe, b.restaurants_uuid as rsuuid from tbl_evaluate b group by b.restaurants_uuid) c  on c.rsuuid = a.uuid) e on d.restaurants_uuid=e.aruuid "
						+"where e.status>=0 and d.consumers_uuid=:consumerUuid LIMIT :beginIndex, :rows";

		int page = pf.getOffset();//当前页码
		int rows = pf.getLimit();//每页数量
		int beginIndex = (page-1)*rows;
		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("consumerUuid", consumerUuid);
		query.setParameter("beginIndex", beginIndex);
		query.setParameter("rows", rows);
		
		query.setResultTransformer(Transformers.aliasToBean(PageFavourites.class));
		query.addScalar("restaurantUuid",new org.hibernate.type.StringType());
		query.addScalar("favouritesId",new org.hibernate.type.IntegerType());
		query.addScalar("avgPrice",new org.hibernate.type.DoubleType());
		query.addScalar("restaurantName",new org.hibernate.type.StringType());
		query.addScalar("avgStars",new org.hibernate.type.DoubleType());
		query.addScalar("isDelivery",new org.hibernate.type.StringType());
		query.addScalar("isPickup",new org.hibernate.type.StringType());
		query.addScalar("isReservation",new org.hibernate.type.StringType());
		query.addScalar("distance",new org.hibernate.type.DoubleType());
		query.addScalar("deliveryPrice",new org.hibernate.type.DoubleType());
		query.addScalar("imageUrl",new org.hibernate.type.StringType());
		
		return query.list();	
	}
	
	
	/**
	 * @Title: countTotalByConsumerId
	 * @Description: 获取用户收藏的商家数量
	 * @param: @param consumerId
	 * @param: @return
	 * @return int  
	 */
	public int countTotalByConsumerUuid(String consumerUuid){
		int total = 1;
		String hql = "select count(*) from ConsumersFavorites cf where cf.consumers.uuid=:consumerUuid "; 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		try {
			total = this.count(hql, map).intValue();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return total;
	}

	
	
	
	
	
	
	
	
}
