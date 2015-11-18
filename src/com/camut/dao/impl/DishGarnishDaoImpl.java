package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.DishGarnishDao;
import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.model.api.DishGarnishApiModel;
import com.camut.model.api.GarnishHeaderApiModel;
import com.camut.utils.Log4jUtil;

/**
 * @dao DishGarnishDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 23, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class DishGarnishDaoImpl extends BaseDao<DishGarnish> implements DishGarnishDao {

	/**
	 * @Title: getDishGarnishById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: DishGarnish
	 */
	@Override
	public DishGarnish getDishGarnishById(long id) {
		String hql = "from DishGarnish dg where dg.garnishItem.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getDishGarnishByDishIdAndGarnishItemId
	 * @Description: 根据菜品和配菜联合主键找到一个唯一的菜品和配菜关系数据
	 * @param: @param dishId
	 * @param: @param GarnishItemId
	 * @return DishGarnish  
	 */
	@Override
	public DishGarnish getDishGarnishByDishIdAndGarnishItemId(long dishId, long garnishItemId) {
		String hql = "from DishGarnish dg where dg.dish.id=:dishId and dg.garnishItem.id=:garnishItemId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dishId", dishId);
		map.put("garnishItemId", garnishItemId);
		try{
			return this.get(hql, map);
		}catch(Exception e){
			return null;
		}
	}
	/**
	 * @Title: addDishGarnish
	 * @Description: 保存菜品配菜
	 * @param:    DishGarnish
	 * @return: int
	 */
	@Override
	public int addDishGarnish(DishGarnish dishGarnish) {
		try {
			this.save(dishGarnish);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: deleteDisnGarnishByDish
	 * @Description: 删除指定菜品下面的配菜关系
	 * @param:    Dish
	 * @return: int
	 */
	@Override
	public int deleteDisnGarnishByDish(Dish dish) {
		String hql = "delete DishGarnish d where d.dish=:dish";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dish", dish);
		try {
			this.executeHql(hql, map);
		} catch (Exception e) {
			return -1;
		}
 		return 1;
	}

	/**
	 * @Title: getDishGarnishByGarnishHeaderId
	 * @Description: 根据配菜类型ID获取数据
	 * @param:    garnishHeaderId
	 * @return: List<DishGarnish>
	 */
	@Override
	public List<DishGarnish> getDishGarnishByGarnishHeaderId(int garnishHeaderId) {
		String hql = "from DishGarnish dg where dg.garnishHeaderId=:garnishHeaderId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("garnishHeaderId", garnishHeaderId);
		List<DishGarnish> dgList = this.find(hql, map);
		return dgList;
	}

	/**
	 * @Title: getGarnishItemByGarnishHeaderId
	 * @Description: 配菜信息
	 * @param:    garnishHeaderId
	 * @return: List<GarnishHeaderApiModel>
	 */
	@Override
	public List<GarnishHeaderApiModel> getGarnishItemByHeaderIdForSql(long dishId, long garnishMenuId) {
		String sql = "SELECT   b.id as garnishItemId, a.addprice as addPrice, b.garnish_name as garnishName " +
				"FROM res_dish_garnish as a LEFT JOIN dat_garnish_item as b on a.garnish_id=b.id " +
				"LEFT JOIN tbl_garnish_header c ON b.garnish_id=c.id  "
				+ "WHERE a.dish_id=:dishId and b.garnish_id=:garnishMenuId";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("dishId", dishId);
		query.setParameter("garnishMenuId", garnishMenuId);
		query.setResultTransformer(Transformers.aliasToBean(GarnishHeaderApiModel.class));
		query.addScalar("garnishItemId",new org.hibernate.type.LongType());
		query.addScalar("addPrice",new org.hibernate.type.DoubleType());
		query.addScalar("garnishName",new org.hibernate.type.StringType());
		return query.list();
	}

	 /**
	  * @Title: getDishGarnishApiModelByDishId
	  * @Description: 根据菜品id获取DishGarnishApiModel
	  * @param:    
	  * @return: List<DishGarnishApiModel>
	  */
	@SuppressWarnings("unchecked")
	@Override
	public List<DishGarnishApiModel> getDishGarnishApiModelByDishId(long dishId) {
		String sql = "select r.dish_id as dishId,group_concat(r.garnish_id separator ',') " +
				"AS garnishItemId  ,t.id as garnishHeaderId,t.ismust as isMust," +
				"t.garnish_menu as garnishHeaderName,t.count as maxCount from tbl_garnish_header t " +
				"inner JOIN (select a.dish_id,a.garnish_header_id,a.garnish_id," +
				"d.garnish_name from res_dish_garnish a LEFT JOIN " +
				"dat_garnish_item d on d.id=a.garnish_id where dish_id="+dishId+") " +
				"r on t.id=r.garnish_header_id GROUP BY t.id,t.garnish_menu,t.count ,r.dish_id";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(DishGarnishApiModel.class));
		query.addScalar("dishId",new org.hibernate.type.IntegerType());
		query.addScalar("garnishItemId",new org.hibernate.type.StringType());
		query.addScalar("isMust",new org.hibernate.type.IntegerType());
		query.addScalar("maxCount",new org.hibernate.type.IntegerType());
		query.addScalar("garnishHeaderName",new org.hibernate.type.StringType());
		query.addScalar("garnishHeaderId",new org.hibernate.type.IntegerType());
		try {
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			Log4jUtil.error(e);
			return null;
		}
	}

	@Override
	public List<DishGarnishApiModel> getDishGarnishApiModelByDishIdForCheck(long dishId) {
		String sql = "select z.dish_id as dishId , group_concat(x.garnishItemId separator ',')  AS garnishItemId ,z.garnishMenuId as garnishHeaderId, z.ismust as isMust "
				+ ",z.garnishMenu as garnishHeaderName,z.maxCount from ("
				+ " SELECT DISTINCT a.dish_id,c.id as garnishMenuId, c.garnish_menu as garnishMenu, c.count as maxCount, c.ismust as isMust, c.show_type as showType"
				+ " FROM res_dish_garnish as a LEFT JOIN dat_garnish_item as b on a.garnish_id=b.id "
				+ "LEFT JOIN tbl_garnish_header c ON b.garnish_id=c.id "
				+ "WHERE a.dish_id="+dishId+") z LEFT JOIN ("
						+ "SELECT   b.id as garnishItemId, a.addprice as addPrice, b.garnish_name as garnishName ,b.garnish_id"
						+ " FROM res_dish_garnish as a "
						+ "LEFT JOIN dat_garnish_item as b on a.garnish_id=b.id "
						+ "LEFT JOIN tbl_garnish_header c ON b.garnish_id=c.id  "
						+ "WHERE a.dish_id="+dishId
						+ ") x ON z.garnishMenuId= x.garnish_id GROUP BY z.garnishMenuId";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(DishGarnishApiModel.class));
		query.addScalar("dishId",new org.hibernate.type.IntegerType());
		query.addScalar("garnishItemId",new org.hibernate.type.StringType());
		query.addScalar("isMust",new org.hibernate.type.IntegerType());
		query.addScalar("maxCount",new org.hibernate.type.IntegerType());
		query.addScalar("garnishHeaderName",new org.hibernate.type.StringType());
		query.addScalar("garnishHeaderId",new org.hibernate.type.IntegerType());
		try {
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			Log4jUtil.error(e);
			return null;
		}
	}

}
