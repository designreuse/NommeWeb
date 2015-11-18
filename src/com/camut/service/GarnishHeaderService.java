package com.camut.service;

import java.util.List;
import com.camut.model.GarnishHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.GarnishApiModel;
import com.camut.model.api.GarnishHeaderApiModel;
import com.camut.pageModel.PageGarnishHeader;

/**
 * @dao GarnishHeaderService.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface GarnishHeaderService {

	/**
	 * @Title: getAllGarnishHeader
	 * @Description: 获取商家的所有配菜
	 * @param:    
	 * @return: PageModel
	 */
	public List<PageGarnishHeader> getAllGarnishHeader(Restaurants restaurants);
	
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
	 * @return: int -1失败 1成功
	 */
	public int updateGarnishHeader(GarnishHeader garnishHeader);
	
	/**
	 * @Title: deleteGarnishHeader
	 * @Description: 删除配菜头
	 * @param:    long
	 * @return: int -1失败 1成功
	 */
	public int deleteGarnishHeader(long id);
	
	/**
	 * @Title: getDish
	 * @Description: 配菜类型
	 * @param:    dishId
	 * @return: List<GarnishHeaderApiModel>
	 */
	public List<GarnishApiModel> getDish(long dishId);
	
	/**
	 * @Title: getGarnishItemByGarnishHeaderId
	 * @Description: 配菜信息
	 * @param:    garnishHeaderId
	 * @return: List<GarnishHeaderApiModel>
	 */
	public List<GarnishHeaderApiModel> getGarnishItemByGarnishHeaderId(long dishId, long garnishMenuId);
	
	/**
	 * @Title: checkGarnishHeader
	 * @Description: 检查给定配菜分类下是否含有配菜
	 * @param:   String
	 * @return: int -1不含  1含有配菜
	 */
	public int checkGarnishHeader(String id);
	
	/**
	 * @Title: getGarnishHeaderById
	 * @Description: 通过主键找配菜头
	 * @param:   long 
	 * @return: GarnishHeader
	 */
	public GarnishHeader getGarnishHeaderById(long id);
}
