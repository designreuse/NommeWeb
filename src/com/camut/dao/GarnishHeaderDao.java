package com.camut.dao;

import java.util.List;

import com.camut.model.GarnishHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.GarnishApiModel;

/**
 * @dao GarnishHeaderDao.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface GarnishHeaderDao {

	/**
	 * @Title: getAllGarnishHeader
	 * @Description: 获取商家的所有配菜
	 * @param:    
	 * @return: PageModel
	 */
	public List<GarnishHeader> getAllGarnishHeader(Restaurants restaurants);
	
	/**
	 * @Title: addGarnishHeader
	 * @Description: 增加配菜头
	 * @param:    GarnishHeader
	 * @return: int -1失败 1成功
	 */
	public int addGarnishHeader(GarnishHeader garnishHeader);
	
	/**
	 * @Title: updateGarnishHeader
	 * @Description: 修改配菜头
	 * @param:    GarnishHeader
	 * @return: int
	 */
	public int updateGarnishHeader(GarnishHeader garnishHeader);
	
	/**
	 * @Title: getGarnishHeaderById
	 * @Description: 通过主键找配菜头
	 * @param:   long 
	 * @return: GarnishHeader
	 */
	public GarnishHeader getGarnishHeaderById(long id);
	
	/**
	 * @Title: daleteGarnishHeader
	 * @Description: 删除配菜头
	 * @param:    GarnishHeader
	 * @return: int
	 */
	public int deleteGarnishHeader(GarnishHeader garnishHeader);
	
	/**
	 * 根据菜品获取配菜列表
	 * @param dishId
	 * @return
	 */
	public List<GarnishApiModel> getGarnishHeaderModel(long dishId);
	
}
