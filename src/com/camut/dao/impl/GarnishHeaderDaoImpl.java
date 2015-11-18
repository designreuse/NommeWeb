package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.camut.dao.GarnishHeaderDao;
import com.camut.model.GarnishHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.GarnishApiModel;
/**
 * @dao GarnishHeaderDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class GarnishHeaderDaoImpl extends BaseDao<GarnishHeader> implements GarnishHeaderDao {

	/**
	 * @Title: getAllGarnishHeader
	 * @Description: 获取商家的所有配菜
	 * @param:    
	 * @return: PageModel
	 */
	@Override
	public List<GarnishHeader> getAllGarnishHeader(Restaurants restaurants) {
		String hql = "from GarnishHeader g where g.restaurants=:restaurants order by g.garnishMenu";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurants", restaurants);
		return this.find(hql, map);
	}


	/**
	 * @Title: addGarnishHeader
	 * @Description: 增加配菜头
	 * @param:    GarnishHeader
	 * @return: int -1失败 1成功
	 */
	@Override
	public int addGarnishHeader(GarnishHeader garnishHeader) {
		try {
			this.save(garnishHeader);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: updateGarnishHeader
	 * @Description: 修改配菜头
	 * @param:    GarnishHeader
	 * @return: int
	 */
	@Override
	public int updateGarnishHeader(GarnishHeader garnishHeader) {
		try {
			this.update(garnishHeader);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/** 
	 * @Title: getGarnishHeaderById
	 * @Description: 通过主键找配菜头
	 * @param:    long
	 * @return:   GarnishHeader
	 */
	@Override
	public GarnishHeader getGarnishHeaderById(long id) {
		String hql = "from GarnishHeader g where g.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/**
	 * @Title: daleteGarnishHeader
	 * @Description: 删除配菜头
	 * @param:    GarnishHeader
	 * @return:   int
	 */
	@Override
	public int deleteGarnishHeader(GarnishHeader garnishHeader) {
		try {
			this.delete(garnishHeader);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getDish
	 * @Description: 配菜类型
	 * @param:  dishId
	 * @return: List<GarnishApiModel>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<GarnishApiModel> getGarnishHeaderModel(long dishId) {
		String sql ="SELECT DISTINCT c.id as garnishMenuId, c.garnish_menu as garnishMenu, c.count as maxCount, c.ismust as isMust, c.show_type as showType " +
				" FROM res_dish_garnish as a LEFT JOIN dat_garnish_item as b on a.garnish_id=b.id " +
				"LEFT JOIN tbl_garnish_header c ON b.garnish_id=c.id "
				+ "WHERE a.dish_id=:dishId";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("dishId", dishId);
		query.setResultTransformer(Transformers.aliasToBean(GarnishApiModel.class));
		//query.addScalar("dishId");
		query.addScalar("garnishMenuId",new org.hibernate.type.LongType());
		query.addScalar("garnishMenu",new org.hibernate.type.StringType());
		query.addScalar("showType",new org.hibernate.type.IntegerType());
		query.addScalar("isMust",new org.hibernate.type.IntegerType());
		query.addScalar("maxCount",new org.hibernate.type.IntegerType());
		return query.list();
	}

}
