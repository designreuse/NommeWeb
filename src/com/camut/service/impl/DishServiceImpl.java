package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.DishDao;
import com.camut.dao.EvaluateDao;
import com.camut.dao.RestaurantsMenuDao;
import com.camut.framework.constant.No_Picture;
import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.model.GarnishHeader;
import com.camut.model.Restaurants;
import com.camut.model.api.DishApiModel;
import com.camut.model.api.DishMenuApiModel;
import com.camut.pageModel.PageDish;
import com.camut.pageModel.PageDishGarnish;
import com.camut.pageModel.PageGarnishItem;
import com.camut.service.DishService;
import com.camut.utils.StringUtil;

/**
 * @dao DishServiceImpl.java
 * @author zyf
 * @createtime 6 10, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class DishServiceImpl implements DishService {

	@Autowired private DishDao dishDao;
	@Autowired private EvaluateDao evaluateDao;
	@Autowired private RestaurantsMenuDao restaurantsMenuDao;

	/**
	 * @Title: getdish
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: List<DishApiModel>
	 */
	@Override
	public List<DishApiModel> getdish(int restaurantId, int type) {
		List<Dish> dList = dishDao.getdish(restaurantId, type);
		List<DishApiModel> damList = new ArrayList<DishApiModel>();
		for (Dish dish : dList) {
			DishApiModel dishApiModel = new DishApiModel();
			dishApiModel.setChName(dish.getChName());
			dishApiModel.setCreatedate(dish.getCreatedate());
			dishApiModel.setEnIntro(dish.getEnIntro());
			dishApiModel.setEnName(dish.getEnName());
			dishApiModel.setDishId(dish.getId());
			dishApiModel.setIsPickup(String.valueOf(type));
			dishApiModel.setIsStarch(dish.getIsStarch());
			dishApiModel.setModby(dish.getModby());
			dishApiModel.setModon(dish.getModon());
			dishApiModel.setPrice(dish.getPrice());
			dishApiModel.setRestaurantId(dish.getRestaurantId());
			dishApiModel.setIsMsg(dish.getIsMsg());
			dishApiModel.setSpicy(dish.getSpicy());
			dishApiModel.setStatus(dish.getStatus());
			dishApiModel.setMenuId(dish.getRestaurantsMenu().getId());
			if(StringUtil.isNotEmpty(dish.getPhotoUrl())){
				dishApiModel.setPhotoUrl(dish.getPhotoUrl());
			} else {
				dishApiModel.setPhotoUrl(No_Picture.Picture);
			}
			dishApiModel.setMenuName(dish.getRestaurantsMenu().getMenuName());
			dishApiModel.setDescribe(dish.getRestaurantsMenu().getDescribe());
			damList.add(dishApiModel);
		}
		Collections.sort(damList);
		return damList;
	}

	/**
	 * @Title: getAllDish
	 * @Description: 获取商家的所有菜品
	 * @param:    Restaurants
	 * @return: List<PageDish>
	 */
	@Override
	public List<PageDish> getAllDish(Restaurants restaurants) {
		List<PageDish> list = new ArrayList<PageDish>();
		if(restaurants!=null){
			List<Dish> dishs = dishDao.getAllDish(restaurants);
			for(Dish dish:dishs){//遍历所有的菜品
				PageDish pageDish = new PageDish();
				BeanUtils.copyProperties(dish, pageDish);
				pageDish.setDishMenuId(dish.getRestaurantsMenu().getId().intValue());
				pageDish.setDishMenuName(dish.getRestaurantsMenu().getMenuName());
				list.add(pageDish);
			}
		}
		return list;
	}

	/**
	 * @Title: addDish
	 * @Description: 增加菜品信息
	 * @param:    Dish
	 * @return: int
	 */
	@Override
	public int addDish(Dish dish) {
		if(dish!=null){
			return dishDao.addDish(dish);
		}
		return -1;
	}

	/**
	 * @Title: getDishById
	 * @Description: 根据主键查找菜品
	 * @param:    
	 * @return: Dish
	 */
	@Override
	public Dish getDishById(String id) {
		if(id!=null && id.length()>0){
			try {
				return dishDao.getDishById(Long.parseLong(id));
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * @Title: updateDish
	 * @Description:修改菜品
	 * @param:   Dish 
	 * @return: int
	 */
	@Override
	public int updateDish(Dish dish) {
		if(dish!=null){
			return dishDao.updateDish(dish);
		}
		return -1;
	}

	/**
	 * @Title: getPageDishById
	 * @Description: 根据主键查找菜品的页面对象
	 * @param:    String
	 * @return: PageDish
	 */
	@Override
	public PageDish getPageDishById(String id) {
		PageDish pd = new PageDish();
		if(StringUtil.isNotEmpty(id)){
			try {
				Dish dish = dishDao.getDishById(Long.parseLong(id));
				BeanUtils.copyProperties(dish, pd);
				pd.setDishMenuId(dish.getRestaurantsMenu().getId().intValue());
				Set<DishGarnish> set = dish.getDishGarnishsSet();
				List<PageDishGarnish> list = new ArrayList<PageDishGarnish>();
				for(DishGarnish dg:set){//遍历菜品配菜的关系
					if(dg.getGarnishItem().getStatus()==0){//判断配菜是否是有效状态
						PageDishGarnish pageDishGarnish = new PageDishGarnish();
						pageDishGarnish.setAddprice(dg.getAddprice());
						pageDishGarnish.setName(dg.getGarnishItem().getGarnishName());
						pageDishGarnish.setId(dg.getGarnishItem().getId());
						//获取配菜分类头
						GarnishHeader garnishHeader = dg.getGarnishItem().getGarnishHeader();
						
						pageDishGarnish.setMenu(garnishHeader.getGarnishMenu());//配菜分类名称
						pageDishGarnish.setGarnishHeaderId(garnishHeader.getId());
						pageDishGarnish.setShowType(garnishHeader.getShowType());
						pageDishGarnish.setIsMust(garnishHeader.getIsmust());
						list.add(pageDishGarnish);
					}
				}
				Collections.sort(list);
				pd.setGarnishList(list);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return pd;
	}
	/**
	 * @Title: getPageDishByDishId
	 * @Description: 根据主键查找菜品的页面对象
	 * @param:    String
	 * @return: PageDish
	 */
	@Override
	public PageDish getPageDishByDishId(String id) {
		PageDish pd = new PageDish();
		if(StringUtil.isNotEmpty(id)){
			try {
				Dish dish = dishDao.getDishById(Long.parseLong(id));
				BeanUtils.copyProperties(dish, pd);
				pd.setDishMenuId(dish.getRestaurantsMenu().getId().intValue());
				Set<DishGarnish> set = dish.getDishGarnishsSet();//获取
				Set<GarnishHeader> garnishHeaderSet = new HashSet<GarnishHeader>();
				for(DishGarnish dg:set){//遍历菜品配菜的关系
					GarnishHeader garnishHeader = dg.getGarnishItem().getGarnishHeader();
					garnishHeaderSet.add(garnishHeader);
				}
				List<PageDishGarnish> list = new ArrayList<PageDishGarnish>();
				for (GarnishHeader garnishHeader : garnishHeaderSet) {
					PageDishGarnish pageDishGarnish = new PageDishGarnish();
					pageDishGarnish.setGarnishHeaderId(garnishHeader.getId());
					pageDishGarnish.setGarnishMenu(garnishHeader.getGarnishMenu());
					pageDishGarnish.setShowType(garnishHeader.getShowType());
					pageDishGarnish.setIsMust(garnishHeader.getIsmust());
					pageDishGarnish.setId(garnishHeader.getId());
					if(garnishHeader.getCount()==null){
						pageDishGarnish.setCount(0);
					}else{
						pageDishGarnish.setCount(garnishHeader.getCount());
					}
					List<PageGarnishItem> pageGarnishItemList = new ArrayList<PageGarnishItem>();
					for(DishGarnish dg:set){
						//如果这个配菜属于上面的的配菜头，并且是可用状态
						if(dg.getGarnishItem().getGarnishHeader().getId()==garnishHeader.getId() && dg.getGarnishItem().getStatus()==0){
							PageGarnishItem pageGarnishItem = new PageGarnishItem();
							pageGarnishItem.setGarnishItemId(dg.getGarnishItem().getId());
							pageGarnishItem.setGarnishName(dg.getGarnishItem().getGarnishName());
							pageGarnishItem.setAddprice(dg.getAddprice());
							pageGarnishItemList.add(pageGarnishItem);
						}
					}
					Collections.sort(pageGarnishItemList);
					pageDishGarnish.setPageGarnishItemList(pageGarnishItemList);
					list.add(pageDishGarnish);
				}
				Collections.sort(list);
				pd.setGarnishList(list);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return pd;
	}

	/**
	 * @Title: getDishMenu
	 * @Description: 获取菜单信息
	 * @param:    restaurantId , type 
	 * @return: List<DishMenuApiModel>
	 */
	@Override
	public List<DishMenuApiModel> getDishMenu(int restaurantId, int type) {
		//List<RestaurantsDishMenuApiModel> rdmamList = new ArrayList<RestaurantsDishMenuApiModel>();
		List<DishMenuApiModel> resutl=new ArrayList<DishMenuApiModel>();
		List<Dish> dLidt = dishDao.getDishMenu(restaurantId, type);//从数据获取到菜品
		boolean flag=true;
	    int num=0;
		for (Dish dish : dLidt) {	
			DishMenuApiModel tmp=new DishMenuApiModel();
			if(num==0){
				DishMenuApiModel dmam = new DishMenuApiModel();
				dmam.setRestaurantMenuId(dish.getRestaurantsMenu().getId());
				dmam.setMenuName(dish.getRestaurantsMenu().getMenuName());
				if(StringUtil.isNotEmpty(dish.getRestaurantsMenu().getDescribe())){
					dmam.setMenuDiscription(dish.getRestaurantsMenu().getDescribe());
				}
				if(!StringUtil.isNotEmpty(dish.getPhotoUrl())){
					dish.setPhotoUrl(No_Picture.Picture);
				}
				resutl.add(dmam);
				num++;
			}
			for (DishMenuApiModel item : resutl) {
				if(item.getRestaurantMenuId()==dish.getRestaurantsMenu().getId()){
					tmp=item;
					flag=true;
					break;
				}else{					
					flag=false;
				}				
			}
			
			if(flag){
				tmp.getContent().add(dish2DishApiModel(dish));
			}else{
				DishMenuApiModel dmam = new DishMenuApiModel();
				dmam.setRestaurantMenuId(dish.getRestaurantsMenu().getId());
				dmam.setMenuName(dish.getRestaurantsMenu().getMenuName());
				if(StringUtil.isNotEmpty(dish.getRestaurantsMenu().getDescribe())){
					dmam.setMenuDiscription(dish.getRestaurantsMenu().getDescribe());
				}
				resutl.add(dmam);
				if(dmam.getRestaurantMenuId()==dish.getRestaurantsMenu().getId()){
					dmam.getContent().add(dish2DishApiModel(dish));
				}
			}
			
		}
		Collections.sort(resutl);
		return resutl;
	}
	
	private DishApiModel dish2DishApiModel(Dish dish){
		DishApiModel dishApiModel=new DishApiModel();
		dishApiModel.setChName(dish.getChName());
		//dishApiModel.setCreatedate(dish.getCreatedate());
		dishApiModel.setCreatedate(null);
		dishApiModel.setModon(null);
		dishApiModel.setModby(null);
		dishApiModel.setIsPickup(null);
		
		dishApiModel.setDishId(dish.getId());
		dishApiModel.setEnIntro(dish.getEnIntro());
		dishApiModel.setEnName(dish.getEnName());
		dishApiModel.setPrice(dish.getPrice());
		//dishApiModel.setSpicy(dish.getSpicy());
		dishApiModel.setPhotoUrl(dish.getPhotoUrl());
		return dishApiModel;
	}
	

	/**
	 * @Title: deleteDish
	 * @Description: 删除菜品
	 * @param:    Dish
	 * @return: int
	 */
	@Override
	public int deleteDish(Dish dish) {
		if (dish!=null){
			return dishDao.updateDish(dish);
		}
		return -1;
	}

	/**
	 * @Title: getAllAvailableDish
	 * @Description: 获取所有上架的菜品
	 * @param:    Restaurants
	 * @return: List<Dish>
	 */
	@Override
	public List<PageDish> getAllAvailableDish(Restaurants restaurants) {
		List<PageDish> list = new ArrayList<PageDish>();
		if (restaurants!=null) {
			List<Dish> dishs = dishDao.getAllAvailableDish(restaurants);
			for (Dish dish : dishs) {
				PageDish pageDish = new PageDish();
				BeanUtils.copyProperties(dish, pageDish);
				list.add(pageDish);
			}
		}
		return list;
	}

	/**
	 * @Title: getdishs
	 * @Description: 菜品信息
	 * @param:    restaurantId , type
	 * @return: List<Dish>
	 */
	@Override
	public List<Dish> getdishs(int restaurantId, int type) {
		return dishDao.getdish(restaurantId, type);
	}

}
