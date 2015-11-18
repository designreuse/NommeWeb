package com.camut.service;

import java.util.List;
import com.camut.model.GarnishHeader;
import com.camut.model.GarnishItem;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageGarnish;

/**
 * @dao GarnishService.java
 * @author zhangyunfei
 * @createtime 6 13, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface GarnishService {

	/**
	 * @Title: getAllGarnish
	 * @Description: 返回商家所有的配菜
	 * @param:    
	 * @return: PageModel
	 */
	public  List<PageGarnish> getAllGarnish(Restaurants restaurants);
	
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
	 * @Title: deleteGarnishItem
	 * @Description: 删除配菜
	 * @param:    GarnishItem
	 * @return: int
	 */
	public int deleteGarnishItem(long id);
	
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
	public List<PageGarnish> getGarnishByHeader(GarnishHeader garnishHeader);
	
}
