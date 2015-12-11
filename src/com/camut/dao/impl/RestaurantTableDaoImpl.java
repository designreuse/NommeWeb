package com.camut.dao.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import com.camut.dao.RestaurantTableDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.RestaurantTable;
import com.camut.model.Restaurants;
import com.camut.model.api.TableEntity;

/**
 * @ClassName RestaurantTableDaoImpl.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class RestaurantTableDaoImpl extends BaseDao<RestaurantTable> implements
		RestaurantTableDao {

	/**
	 * @Title: getRestaurantTable
	 * @Description: 获取商家的桌位信息
	 * @param: Restaurants
	 * @return: RestaurantTable
	 */
	@Override
	public List<RestaurantTable> getRestaurantTable(Restaurants restaurants) {
		String hql = "from RestaurantTable r where r.restaurants=:restaurants order by r.acceptanceNum";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurants", restaurants);
		return this.find(hql, map);
	}

	/**
	 * @Title: addRestaurantTable
	 * @Description: 增加桌位信息
	 * @param: RestaurantTable
	 * @return: int
	 */
	@Override
	public int addRestaurantTable(RestaurantTable restaurantTable) {
		try {
			this.save(restaurantTable);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateRestaurantTable
	 * @Description:修改桌位信息
	 * @param: RestaurantTable
	 * @return: int
	 */
	@Override
	public int updateRestaurantTable(RestaurantTable restaurantTable) {
		try {
			this.update(restaurantTable);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getRestaurantTableById
	 * @Description: 通过id找桌位信息
	 * @param: long
	 * @return: PageTable
	 */
	@Override
	public RestaurantTable getRestaurantTableById(long id) {
		String hql = "from RestaurantTable r where r.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/**
	 * @Title: deleteRestaurantTable
	 * @Description: 删除桌位信息
	 * @param: RestaurantTable
	 * @return: int
	 */
	@Override
	public int deleteRestaurantTable(RestaurantTable restaurantTable) {
		try {
			this.delete(restaurantTable);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	
	/**
	 * @Title: getNumberOfRestaurantReservationOverlaps
	 * @Description: Gets the number of overlapping reservations for each table type.
	 * @param restaurantUuid
	 * @param reservationRequestDateString
	 * @return: List<TableEntity>
	 */
	@Override
	public List<TableEntity> getNumberOfRestaurantReservationOverlaps(String restaurantUuid, String reservationRequestDateString) {
		// Convert the request date.
		DateFormat requestDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date reservationRequestDate = null;
		try {
			reservationRequestDate = requestDateFormat.parse(reservationRequestDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// Get the time frame boundaries for possible overlap.
		// TODO: Get the reservation meal length.
		long mealLength = 1000*60*60*2;
		Date earliestOverlappingReservationTime = new Date(reservationRequestDate.getTime() - mealLength);
		Date latestOverlappingReservationTime = new Date(reservationRequestDate.getTime() + mealLength);
		
		// Check if the meal length causes the bounds to go into other days.  If so, don't use them in the query.
		DateTime e0 = new DateTime(reservationRequestDate);
		DateTime e1 = new DateTime(earliestOverlappingReservationTime);
		DateTime e2 = new DateTime(latestOverlappingReservationTime);
		boolean useEarliest = true;
		boolean useLatest = true;
		if (e0.getDayOfYear() != e1.getDayOfYear()) {
			useEarliest = false;
		}
		if (e0.getDayOfYear() != e2.getDayOfYear()) {
			useLatest = false;
		}
		
		// Format the time boundaries.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String queryReservationRequestDate = dateFormat.format(reservationRequestDate);
		String queryEarliestOverlappingReservationTime = timeFormat.format(earliestOverlappingReservationTime);
		String queryLatestOverlappingReservationTime = timeFormat.format(latestOverlappingReservationTime);
		
		// Get the number of overlapping reservations within this time frame.
		String sql = "SELECT COUNT(oh.id) as count, ";	// Number of reservations for this table type.
		sql += "oh.number as number ";	// Table type.
		sql += "FROM nomme.dat_order_header as oh ";
		sql += "WHERE oh.order_type =:orderType ";
		sql += "AND oh.status = 3 ";
		sql += "AND oh.restaurant_uuid =:restaurantUuid ";
		sql += "AND DATE_FORMAT(oh.order_date, '%Y-%m-%d') = '" + queryReservationRequestDate + "' ";	// Must be for same day.
		if (useEarliest) {
			sql += "AND DATE_FORMAT(oh.order_date, '%H:%i') > '" + queryEarliestOverlappingReservationTime + "'";	// Lower bound.
		}
		if (useLatest) {
			sql += "AND DATE_FORMAT(oh.order_date, '%H:%i') < '" + queryLatestOverlappingReservationTime + "'";	// Upper bound.
		}
		sql += "GROUP BY oh.number ";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("orderType", GlobalConstant.TYPE_RESERVATION);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setResultTransformer(Transformers.aliasToBean(TableEntity.class));
		query.addScalar("count",new org.hibernate.type.IntegerType());
		query.addScalar("number",new org.hibernate.type.IntegerType());
		return query.list();
	}
}
