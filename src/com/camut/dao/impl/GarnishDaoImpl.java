/**
 * 
 */
package com.camut.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.camut.dao.GarnishDao;
import com.camut.model.GarnishHeader;
import com.camut.model.GarnishItem;

/**
 * @ClassName GarnishDaoImpl.java
 * @author wangpin
 * @createtime Jun 15, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class GarnishDaoImpl extends BaseDao<GarnishItem> implements GarnishDao {

	/**
	 * @Title: addGarnish
	 * @Description: 增加配菜
	 * @param:    GarnishItem
	 * @return: int
	 */
	@Override
	public int addGarnish(GarnishItem garnishItem) {
		try {
			this.save(garnishItem);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	/** @Title: updateGarnish
	 * @Description: 修改配菜
	 * @param:    
	 * @return:   
	 */
	@Override
	public int updateGarnish(GarnishItem garnishItem) {
		try {
			this.update(garnishItem);
		} catch (Exception e) {
			return -1;
		}
		return 1;
	}
	/**
	 * @Title: getGarnishItemById
	 * @Description: 根据主键查找配菜
	 * @param:    long
	 * @return: GarnishItem
	 */
	@Override
	public GarnishItem getGarnishItemById(long id) {
		String hql = "from GarnishItem g where g.id=:id and g.status=0";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		return this.get(hql, map);
	}

	
	/**
	 * @Title: addGarnishItem
	 * @Description: 用户订单新增
	 * @param:  garnishItem
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */

	@Override
	public long addGarnishItem(GarnishItem garnishItem) {
		try {
			long a = (Long) this.save(garnishItem);
			return a;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: getGarnishByHeader
	 * @Description: 通过配菜头获取配菜
	 * @param:    GarnishHeader
	 * @return: List<PageGarnish>
	 */
	@Override
	public List<GarnishItem> getGarnishByHeader(GarnishHeader garnishHeader) {
		String hql = "from GarnishItem g where g.garnishHeader=:garnishHeader and g.status=0";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("garnishHeader", garnishHeader);
		return this.find(hql, map);
	}

}
