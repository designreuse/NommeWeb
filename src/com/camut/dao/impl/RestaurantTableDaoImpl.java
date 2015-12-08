package com.camut.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.RestaurantTableDao;
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

	@Override
	public List<TableEntity> getRestaurantTableNumberByOrderTypeAndOrderDate(String restaurantUuid, int orderType, String orderDate) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Date dt = null;
		try {
			dt = fmt.parse(orderDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = Calendar.getInstance();
		Date dt16=new Date(dt.getYear(), dt.getMonth(),dt.getDate(), 16, 0);
		Date dt8=new Date(dt.getYear(), dt.getMonth(),dt.getDate(), 8, 0);
		//System.out.println(dt);
		//System.out.println(dt8);
		//System.out.println(dt16);
		/*if(dt8.before(dt)&&dt.before(dt16)){
			System.out.println("上午");
		}else {
			System.out.println("xiawu");
		}*/
		String sql="";
		if(dt8.before(dt)&&dt.before(dt16)){
			sql = "select  consumer_uuid as consumerUuid,restaurant_uuid as restaurantUuid,number,count(1) as count from  dat_order_header " +
					"WHERE  DATE_FORMAT(:dt,'%Y-%m-%d')=DATE_FORMAT(order_date,'%Y-%m-%d') " +
					"and  HOUR(order_date)-HOUR(:dt8)>0 and HOUR(:dt16)-HOUR(order_date)>0 " +
					"and order_type=:type and `status`=3 and restaurant_uuid=:restaurantUuid " +
					"GROUP BY consumer_uuid,restaurant_uuid,number";
		}else{
			sql="select consumer_uuid as consumerUuid,restaurant_uuid as restaurantUuid,number,count(1) as count  from  dat_order_header  " +
					"WHERE  DATE_FORMAT(:dt,'%Y-%m-%d')=DATE_FORMAT(order_date,'%Y-%m-%d') " +
					"and (HOUR(order_date)-HOUR(:dt8)<0 or HOUR(:dt16)-HOUR(order_date)<0) " +
					"and order_type=:type and `status`=3 and restaurant_uuid=:restaurantUuid " +
					"GROUP BY consumer_uuid,restaurant_uuid,number ";
		}		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("dt", dt);
		query.setParameter("dt8", dt8);
		query.setParameter("dt16", dt16);
		query.setParameter("type", orderType);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setResultTransformer(Transformers.aliasToBean(TableEntity.class));
		query.addScalar("consumerUuid",new org.hibernate.type.StringType());
		query.addScalar("restaurantUuid",new org.hibernate.type.StringType());
		query.addScalar("number",new org.hibernate.type.IntegerType());
		query.addScalar("count",new org.hibernate.type.IntegerType());
		return query.list();
	}
}
