/**
 * 
 */
package com.camut.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.camut.dao.ClassificationDao;
import com.camut.dao.ViewRestaurantDao;
import com.camut.framework.constant.No_Picture;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageViewRestaurant;
import com.camut.service.ViewRestaurantService;
import com.camut.utils.CommonUtil;
import com.camut.utils.StringUtil;
import com.camut.model.Classification;
import com.camut.model.ViewRestaurant;
import com.camut.model.api.SearchRestaurantsApiModel;
import com.camut.model.api.ViewRestaurantApiModel;

/**
 * @ClassName ViewRestaurantServiceImpl.java
 * @author wangpin
 * @createtime Jul 9, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class ViewRestaurantServiceImpl implements ViewRestaurantService {
	
	@Autowired
	private ViewRestaurantDao viewRestaurantDao;
	
	@Autowired
	private ClassificationDao classificationDao;
	
	/**
	 * @Title: getRestaurants
	 * @Description:获取商家
	 * @param:    
	 * @return: List<PageViewRestaurant>
	 */
	@Override
	public PageModel getRestaurants(ViewRestaurant viewRestaurant,PageFilter pf) {
		List<ViewRestaurant> list = viewRestaurantDao.getRestaurants(viewRestaurant,pf);
		List<PageViewRestaurant> pageViewRestaurants = new ArrayList<PageViewRestaurant>();
		for (ViewRestaurant viewRestaurant1 : list) {
			PageViewRestaurant pageViewRestaurant = new PageViewRestaurant();
			BeanUtils.copyProperties(viewRestaurant1, pageViewRestaurant);
			//截取分类名称
			if(pageViewRestaurant.getClassificationName()!=null && pageViewRestaurant.getClassificationName().length()>20){
				pageViewRestaurant.setClassificationName(pageViewRestaurant.getClassificationName().substring(0, 20)+"...");
			}
			//截取商家名称
			if(pageViewRestaurant.getRestaurantName()!=null && pageViewRestaurant.getRestaurantName().length()>25){
				pageViewRestaurant.setRestaurantName(pageViewRestaurant.getRestaurantName().substring(0, 25)+"...");
			}
			//计算餐厅与输入地址之间的距离
			if(viewRestaurant1.getRestaurantLat()!=null && viewRestaurant1.getRestaurantLng()!=null){
				double apart = CommonUtil.getDistance(viewRestaurant.getRestaurantLat(), viewRestaurant.getRestaurantLng(), viewRestaurant1.getRestaurantLng(), viewRestaurant1.getRestaurantLat());
				pageViewRestaurant.setApart(apart);
			}
			//评分四舍五入
			if (pageViewRestaurant.getScore()!=null && !"".equals(pageViewRestaurant.getScore())) {
				pageViewRestaurant.setScore((double)Math.round(pageViewRestaurant.getScore()));
			}
			pageViewRestaurants.add(pageViewRestaurant);
		}
		PageModel pm = new PageModel();
		pm.setRows(pageViewRestaurants);
		pm.setTotal(viewRestaurantDao.getTotal());
		return pm;
	}
	


	/** @Title: getRestaurantsForMap
	 * @Description: 获取餐厅
	 * @param:    ViewRestaurant PageFilter
	 * @return:   List<PageViewRestaurant>
	 */
	@Override
	public List<PageViewRestaurant> getRestaurantsForMap(ViewRestaurant viewRestaurant, PageFilter pf) {
		List<ViewRestaurant> list = viewRestaurantDao.getRestaurants(viewRestaurant,pf);
		List<PageViewRestaurant> pageViewRestaurants = new ArrayList<PageViewRestaurant>();
		for (ViewRestaurant viewRestaurant1 : list) {
			PageViewRestaurant pageViewRestaurant = new PageViewRestaurant();
			BeanUtils.copyProperties(viewRestaurant1, pageViewRestaurant);
			//截取分类名称
			if(pageViewRestaurant.getClassificationName()!=null && pageViewRestaurant.getClassificationName().length()>20){
				pageViewRestaurant.setClassificationName(pageViewRestaurant.getClassificationName().substring(0, 20)+"...");
			}
			//截取商家名称
			if(pageViewRestaurant.getRestaurantName()!=null && pageViewRestaurant.getRestaurantName().length()>20){
				pageViewRestaurant.setRestaurantName(pageViewRestaurant.getRestaurantName().substring(0, 20)+"...");
			}
			//计算餐厅与输入地址之间的距离
			if(viewRestaurant1.getRestaurantLat()!=null && viewRestaurant1.getRestaurantLng()!=null){
				double apart = CommonUtil.getDistance(viewRestaurant.getRestaurantLat(), viewRestaurant.getRestaurantLng(), viewRestaurant1.getRestaurantLng(), viewRestaurant1.getRestaurantLat());
				pageViewRestaurant.setApart(apart);
			}
			
			pageViewRestaurants.add(pageViewRestaurant);
		}
		return pageViewRestaurants;
	}

	/**
	 * @Title: getSearchRestaurants
	 * @Description: 商家搜索
	 * @param:    viewRestaurant pf
	 * @return: PageModel
	 */
	@Override
	public PageModel getSearchRestaurants(SearchRestaurantsApiModel viewRestaurant, PageFilter pf) {
		String str="";
		if(StringUtil.isNotEmpty(viewRestaurant.getClassificationId())){
			String[] strs=viewRestaurant.getClassificationId().split(",");
			List<Classification> classifications= classificationDao.getAllClassification();
			for (int i = 0; i < strs.length; i++) {
				for (Classification classification : classifications) {
					if(classification.getId()==Long.parseLong(strs[i])){
						str+=classification.getClassificationName()+",";
						break;
					}
				}
			}			
		}
		if(StringUtil.isNotBlank(str)){
			str=str.substring(0, str.length()-1);
			viewRestaurant.setClassification(str);
		}

		List<ViewRestaurant> list = viewRestaurantDao.getViewRestaurants(viewRestaurant,pf);
		List<ViewRestaurantApiModel> vramList = new ArrayList<ViewRestaurantApiModel>();
		for (ViewRestaurant viewRestaurant2 : list) {
			ViewRestaurantApiModel vram = new ViewRestaurantApiModel();
			if(viewRestaurant2.getRestaurantUuid() != null){
				vram.setRestaurantUuid(viewRestaurant2.getRestaurantUuid());//店家id
			}
			if(viewRestaurant2.getLogourl() != null && viewRestaurant2.getLogourl().length() > 0){
				vram.setLogourl(viewRestaurant2.getLogourl());//logo
			} else {
				vram.setLogourl(No_Picture.Picture);
			}
			if(viewRestaurant2.getRestaurantName() != null && viewRestaurant2.getRestaurantName().length() > 0){
				vram.setRestaurantName(viewRestaurant2.getRestaurantName());//店名
			}
			if(viewRestaurant2.getAvgPrice() != null){
				if(0 < viewRestaurant2.getAvgPrice() && viewRestaurant2.getAvgPrice() <= 20){
					vram.setAvgPriceStr("$");//人均价格
				} else if (20 < viewRestaurant2.getAvgPrice() && viewRestaurant2.getAvgPrice() <= 30) {
					vram.setAvgPriceStr("$$");//人均价格
				} else if (30 < viewRestaurant2.getAvgPrice() && viewRestaurant2.getAvgPrice() <= 40) {
					vram.setAvgPriceStr("$$$");//人均价格
				} else if (40 < viewRestaurant2.getAvgPrice() && viewRestaurant2.getAvgPrice() <= 50) {
					vram.setAvgPriceStr("$$$$");//人均价格
				} else if (40 < viewRestaurant2.getAvgPrice() && viewRestaurant2.getAvgPrice() <= 50) {
					vram.setAvgPriceStr("$$$$$");//人均价格
				}
			}
			if(viewRestaurant2.getScore() != null){
				vram.setStars(StringUtil.convertLastDouble(viewRestaurant2.getScore()));//评级
			}
			if(viewRestaurant2.getOpentime() != null){
				vram.setIsOpen(viewRestaurant2.getOpentime());//是否营业
			}
			if (viewRestaurant2.getRestaurantLat()!=null) {
				vram.setDistance(CommonUtil.getDistance(viewRestaurant2.getRestaurantLat(), viewRestaurant2.getRestaurantLng(), Double.valueOf(viewRestaurant.getRestaurantLng()),  Double.valueOf(viewRestaurant.getRestaurantLat())));
			}
			if(viewRestaurant2.getDiscountNum()!=null&&viewRestaurant2.getDiscountNum()>0){
				vram.setIsDiscount(1);
			}
			vramList.add(vram);
		}
		PageModel pm = new PageModel();
		pm.setCurrentPageIndex(pf.getOffset());
		pm.setRows(vramList);
		pm.setTotal(viewRestaurantDao.getTotal());
		return pm;
	}

	/**
	 * @Title: getSearchRestaurantsCount
	 * @Description: 商家数量
	 * @param:    viewRestaurant pf
	 * @return: PageModel
	 */
	@Override
	public PageModel getSearchRestaurantsCount(SearchRestaurantsApiModel viewRestaurant, PageFilter pf) {
		List<ViewRestaurant> vrLsit = viewRestaurantDao.getViewRestaurants(viewRestaurant,pf);
		PageModel pm = new PageModel();
		pm.setTotal(vrLsit.size());
		return pm;
	}

	/**
	 * @Title: getRestaurantScore
	 * @Description: 商家评分
	 * @param:    
	 * @return: ViewRestaurant
	 */
	@Override
	public ViewRestaurant getRestaurantScore(String restaurantUuid) {
		return viewRestaurantDao.getRestaurantsByRestaurantUuid(restaurantUuid);
	}
	

}
