package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.DishGarnishDao;
import com.camut.dao.GarnishDao;
import com.camut.dao.GarnishHeaderDao;
import com.camut.dao.GarnishItemDao;
import com.camut.dao.OrderDao;
import com.camut.model.GarnishHeader;
import com.camut.model.GarnishItem;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageGarnish;
import com.camut.service.GarnishService;

/**
 * @dao GarnishServiceImpl.java
 * @author zhangyunfei
 * @createtime 6 13, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class GarnishServiceImpl implements GarnishService {

	@Autowired
	private GarnishHeaderDao garnishHeaderDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private GarnishDao garnishDao;
	
	@Autowired
	private DishGarnishDao dishGarnishDao;
	
	@Autowired
	private GarnishItemDao garnishItemDao;
	
	/** @Title: getAllGarnish
	 * @Description: 返回商家所有的配菜
	 * @param:    Restaurants
	 * @return:   PageModel
	 */
	@Override
	public List<PageGarnish> getAllGarnish(Restaurants restaurants) {
		List<PageGarnish> garnishs = garnishItemDao.getGarnishItemByRestaurantId(restaurants.getId());
		return garnishs;
	}

	/**
	 * @Title: addGarnish
	 * @Description: 增加配菜
	 * @param:    GarnishItem
	 * @return: int
	 */
	@Override
	public int addGarnish(GarnishItem garnishItem) {
		if(garnishItem!=null){
			return garnishDao.addGarnish(garnishItem);
		}
		return -1;
	}

	/** @Title: updateGarnish
	 * @Description: 修改配菜
	 * @param:    GarnishItem
	 * @return:   int
	 */
	@Override
	public int updateGarnish(GarnishItem garnishItem) {
		if(garnishItem!=null){
			return garnishDao.updateGarnish(garnishItem);
		}
		return -1;
	}

	/** @Title: deleteGarnishItem
	 * @Description: 
	 * @param:    
	 * @return:   
	 */
	@Override
	public int deleteGarnishItem(long id) {
		if(id!=0){
			GarnishItem garnishItem = garnishDao.getGarnishItemById(id);
			if(garnishItem!=null){
				garnishItem.setStatus(1);//设置无效状态
				return garnishDao.updateGarnish(garnishItem);
			}
		}
		return 0;
	}

	/**
	 * @Title: addGarnishItem
	 * @Description: 用户订单新增
	 * @param:  garnishItem
	 * @return:  -1表示新增失败 ，1表示新增成功 
	 */
	@Override
	public long addGarnishItem(GarnishItem garnishItem) {
		return garnishDao.addGarnishItem(garnishItem);
	}

	/**
	 * @Title: getGarnishByHeader
	 * @Description: 通过配菜头获取配菜
	 * @param:    GarnishHeader
	 * @return: List<PageGarnish>
	 */
	@Override
	public List<PageGarnish> getGarnishByHeader(GarnishHeader garnishHeader) {
		if(garnishHeader!=null){
			List<GarnishItem> list = garnishDao.getGarnishByHeader(garnishHeader);
			List<PageGarnish> garnishs = new ArrayList<PageGarnish>();
			for(GarnishItem g:list){
				if (g.getStatus()==0) {//判断配菜是否是有效状态
					PageGarnish pageGarnish = new PageGarnish();
					BeanUtils.copyProperties(g, pageGarnish);
					pageGarnish.setGarnishHeaderId(garnishHeader.getId().intValue());
					pageGarnish.setGarnishHeaderName(g.getGarnishHeader().getGarnishMenu());
					garnishs.add(pageGarnish);
				}
			}
			return garnishs;
		}
		return null;
	}
	
}
