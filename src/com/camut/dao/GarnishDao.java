package com.camut.dao;

import java.util.List;
import com.camut.model.GarnishHeader;
import com.camut.model.GarnishItem;
/**
 * @ClassName GarnishDao.java
 * @author wangpin
 * @createtime Jun 15, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface GarnishDao {

	/**
	 * @Title: addGarnish
	 * @Description: 增加配菜
	 * @param:    GarnishItem
	 * @return: int
	 */
	public int addGarnish(GarnishItem garnishItem);
	
	/**
	 * @Title: updateGarnish
	 * @Description: 修改配菜
	 * @param:    GarnishItem
	 * @return: int
	 */
	public int updateGarnish(GarnishItem garnishItem);
	
	/**
	 * @Title: getGarnishItemById
	 * @Description: 根据主键查找配菜
	 * @param:    long
	 * @return: GarnishItem
	 */
	public GarnishItem getGarnishItemById(long id);
	

	
	/**
	 * @Title: addGarnishItem
	 * @Description: 用户订单新增
	 * @param:  garnishItem
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	public long addGarnishItem(GarnishItem garnishItem);
	
	/**
	 * @Title: getGarnishByHeader
	 * @Description: 通过配菜头获取配菜
	 * @param:    GarnishHeader
	 * @return: List<PageGarnish>
	 */
	public List<GarnishItem> getGarnishByHeader(GarnishHeader garnishHeader);
}
