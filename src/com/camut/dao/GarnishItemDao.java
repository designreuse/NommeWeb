package com.camut.dao;

import java.util.List;
import com.camut.model.GarnishItem;
import com.camut.pageModel.PageGarnish;

/**
 * @dao GarnishItemDao.java
 * @author zhangyunfei
 * @createtime 6 23, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface GarnishItemDao {

	/**
	 * @Title: getGarnishItemById
	 * @Description: 根据id获取对象
	 * @param:    id
	 * @return: GarnishItem
	 */
	public GarnishItem getGarnishItemById(long id);
	
	/**
	 * @Title: getGarnishItemById
	 * @Description: 获取商家下的所有配菜
	 * @param:    id
	 * @return: GarnishItem
	 */
	public List<PageGarnish> getGarnishItemByRestaurantId(long id);
	
}
