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
		// Get the number of overlapping reservations by checking for other reservations whose time and duration overlap with the requested time.
		String sql = "SELECT COUNT(oh.id) as count, ";	// Number of reservations for this table type.
		sql += "oh.number as number ";	// Table type.
		sql += "FROM nomme.dat_order_header as oh ";
		sql += "INNER JOIN nomme.tbl_restaurant_table as tbl ";
		sql += "ON oh.restaurant_uuid = tbl.restaurant_uuid AND oh.number = tbl.acceptance_num ";
		sql += "WHERE oh.order_type =:orderType ";
		sql += "AND oh.status = 3 ";
		sql += "AND oh.restaurant_uuid =:restaurantUuid ";
		sql += "AND TIME_TO_SEC(TIMEDIFF('" + reservationRequestDateString + "', oh.order_date)) < tbl.meal_duration_in_minutes * 60 ";
		sql += "AND TIME_TO_SEC(TIMEDIFF('" + reservationRequestDateString + "', oh.order_date)) > -tbl.meal_duration_in_minutes * 60 ";
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
