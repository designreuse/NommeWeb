package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import com.camut.dao.GarnishItemDao;
import com.camut.model.GarnishItem;
import com.camut.pageModel.PageGarnish;

/**
 * @dao GarnishItemDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 23, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class GarnishItemDaoImpl extends BaseDao<GarnishItem> implements GarnishItemDao {

	/**
	 * @Title: getGarnishItemById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: GarnishItem
	 */
	@Override
	public GarnishItem getGarnishItemById(long id) {
		String hql = "from GarnishItem gi where gi.id=:id and gi.status=0";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	/** @Title: getGarnishItemByRestaurantId
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageGarnish> getGarnishItemByRestaurantId(long id) {
		String sql = "select dat.id as id,dat.garnish_name as garnishName," +
				"tbl.garnish_menu as garnishHeaderName,tbl.id as garnishHeaderId," +
				"tbl.show_type as showType,tbl.ismust as ismust from dat_garnish_item dat "+
				"INNER JOIN "+
				"(select id,ismust,show_type,garnish_menu "+
				"from tbl_garnish_header  "+
				"where restaurant_id="+id+") tbl on dat.garnish_id = tbl.id and dat.status=0";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(PageGarnish.class));
		query.addScalar("id", new org.hibernate.type.LongType());
		query.addScalar("garnishName", new org.hibernate.type.StringType());
		query.addScalar("garnishHeaderName", new org.hibernate.type.StringType());
		query.addScalar("garnishHeaderId", new org.hibernate.type.IntegerType());
		query.addScalar("showType", new org.hibernate.type.IntegerType());
		query.addScalar("ismust", new org.hibernate.type.IntegerType());
		try {
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}

}
