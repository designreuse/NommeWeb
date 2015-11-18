package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.DishDao;
import com.camut.dao.DishGarnishDao;
import com.camut.dao.GarnishHeaderDao;
import com.camut.dao.GarnishItemDao;
import com.camut.model.GarnishHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.GarnishApiModel;
import com.camut.model.api.GarnishHeaderApiModel;
import com.camut.pageModel.PageGarnishHeader;
import com.camut.service.GarnishHeaderService;

/**
 * @dao GarnishHeaderServiceImpl.java
 * @author zhangyunfei
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class GarnishHeaderServiceImpl implements GarnishHeaderService {

	@Autowired
	private GarnishHeaderDao garnishHeaderDao;
	
	@Autowired
	private DishDao dishDao;
	
	@Autowired
	private GarnishItemDao garnishItemDao;
	
	@Autowired
	private DishGarnishDao dishGarnishDao;
	
	/**
	 * @Title: getAllGarnishHeader
	 * @Description: 获取商家的所有配菜
	 * @param:    
	 * @return: PageModel
	 */
	@Override
	public List<PageGarnishHeader> getAllGarnishHeader(Restaurants restaurants) {
		List<GarnishHeader> list = garnishHeaderDao.getAllGarnishHeader(restaurants);
		List<PageGarnishHeader> list2 = new ArrayList<PageGarnishHeader>();
		for(GarnishHeader garnishHeader:list){
			PageGarnishHeader pageGarnishHeader = new PageGarnishHeader();
			BeanUtils.copyProperties(garnishHeader, pageGarnishHeader);
			list2.add(pageGarnishHeader);
		}
		return list2;
	}

	/**
	 * @Title: addGarnishHeader
	 * @Description: 增加配菜头
	 * @param:    GarnishHeader
	 * @return: int -1失败 1成功
	 */
	@Override
	public int addGarnishHeader(GarnishHeader garnishHeader) {
		return garnishHeaderDao.addGarnishHeader(garnishHeader);
	}

	/**
	 * @Title: updateGarnishHeader
	 * @Description: 修改配菜头
	 * @param:    GarnishHeader
	 * @return: int
	 */
	@Override
	public int updateGarnishHeader(GarnishHeader garnishHeader) {
		return garnishHeaderDao.updateGarnishHeader(garnishHeader);
	}

	/**
	 * @Title: deleteGarnishHeader
	 * @Description: 删除配菜头
	 * @param:    long
	 * @return: int -1失败 1成功
	 */
	@Override
	public int deleteGarnishHeader(long id) {
		if(id!=0){
			GarnishHeader garnishHeader = garnishHeaderDao.getGarnishHeaderById(id);
			return garnishHeaderDao.deleteGarnishHeader(garnishHeader);
		}
		return -1;
	}

	/**
	 * @Title: getDish
	 * @Description: 配菜类型
	 * @param:  dishId
	 * @return: List<GarnishApiModel>
	 */
	@Override
	public List<GarnishApiModel> getDish(long dishId) {
		List<GarnishApiModel> ghamList = garnishHeaderDao.getGarnishHeaderModel(dishId);
		for (GarnishApiModel garnishApiModel : ghamList) {
			if(garnishApiModel.getIsMust() == 1){
				garnishApiModel.setGarnishMenu(garnishApiModel.getGarnishMenu() + " (" + "Required" + ")");
			}else{
				garnishApiModel.setGarnishMenu(garnishApiModel.getGarnishMenu() + " (" + "Optional" + ")");
			}
		}
		Collections.sort(ghamList);
		return ghamList;
	}

	/**
	 * @Title: getGarnishItemByGarnishHeaderId
	 * @Description: 配菜信息
	 * @param:    garnishHeaderId
	 * @return: List<GarnishHeaderApiModel>
	 */
	@Override
	public List<GarnishHeaderApiModel> getGarnishItemByGarnishHeaderId(long dishId, long garnishMenuId) {
		List<GarnishHeaderApiModel> ghamList = dishGarnishDao.getGarnishItemByHeaderIdForSql(dishId, garnishMenuId);
		Collections.sort(ghamList);
		return ghamList;
	}

	/**
	 * @Title: checkGarnishHeader
	 * @Description: 检查给定配菜分类下是否含有配菜
	 * @param:   String
	 * @return: int -1不含  1含有配菜
	 */
	@Override
	public int checkGarnishHeader(String id) {
		if(id!=null && id.length()>0){
			GarnishHeader garnishHeader = garnishHeaderDao.getGarnishHeaderById(Long.parseLong(id));
			if (garnishHeader.getGarnishItems().size()>0) {
				return 1;
			}
		}
		return -1;
	}

	/**
	 * @Title: getGarnishHeaderById
	 * @Description: 通过主键找配菜头
	 * @param:   long 
	 * @return: GarnishHeader
	 */
	@Override
	public GarnishHeader getGarnishHeaderById(long id) {
		return garnishHeaderDao.getGarnishHeaderById(id);
	}

}