package com.camut.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.AreasDao;
import com.camut.dao.ChainDao;
import com.camut.dao.ConsumersFavoritesDao;
import com.camut.dao.DiscountDao;
import com.camut.dao.DishDao;
import com.camut.dao.EvaluateDao;
import com.camut.dao.OpenTimeDao;
import com.camut.dao.RestaurantsDao;
import com.camut.dao.RestaurantsMenuDao;
import com.camut.dao.ViewRestaurantDao;
import com.camut.framework.constant.No_Picture;
import com.camut.model.Chain;
import com.camut.model.Classification;
import com.camut.model.ConsumersFavorites;
import com.camut.model.Discount;
import com.camut.model.Dish;
import com.camut.model.Evaluate;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsMenu;
import com.camut.model.RestaurantsUser;
import com.camut.model.ViewRestaurant;
import com.camut.model.api.RestaurantsApiModel;
import com.camut.model.api.RestaurantsDetailApiModel;
import com.camut.model.api.RestaurantsMoreApiModel;
import com.camut.pageModel.PageDiscount;
import com.camut.pageModel.PageDish;
import com.camut.pageModel.PageEvaluate;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageRestaurant;
import com.camut.pageModel.PageRestaurantAdmins;
import com.camut.pageModel.PageRestaurantName;
import com.camut.pageModel.PageRestaurantsMenu;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.GoogleTimezoneAPIUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MailUtil;
import com.camut.utils.PageEvaluteComparator;
import com.camut.utils.StringUtil;

/**
 * @ClassName RestaurantsServiceImpl.java
 * @author wangpin
 * @createtime May 27, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class RestaurantsServiceImpl implements RestaurantsService {

	@Autowired private RestaurantsDao restaurantsDao;//自动注入RestaurantsDao
	@Autowired private AreasDao areasDao;
	@Autowired private ChainDao chainDao;
	@Autowired private RestaurantsMenuDao restaurantsMenuDao; 
	@Autowired private DishDao dishDao;
	@Autowired private ViewRestaurantDao viewRestaurantDao;
	@Autowired private EvaluateDao evaluateDao;
	@Autowired private DiscountDao discountDao;
	@Autowired private OpenTimeDao openTimeDao;
	@Autowired private ConsumersFavoritesDao consumersFavoritesDao; 
	@Autowired private RestaurantsUserService restaurantsUserService;
	
	/* (non-Javadoc)
	 * @see com.camut.service.RestaurantsService#getRestaurantsById(java.lang.String)
	 */
	@Override
	public Restaurants getRestaurantsById(long id) {
		return restaurantsDao.getRestaurantsById(id);
	}
	
	/* (non-Javadoc)
	 * @see com.camut.service.RestaurantsService#getRestaurantsById(java.lang.String)
	 */
	@Override
	public Restaurants getRestaurantsByUuid(String restaurantUuid) {
		return restaurantsDao.getRestaurantsByUuid(restaurantUuid);
	}
	
	/*
	 * @Title: addRestaurants
	 * @Description: 增加商家; add a restaurant
	 * @param:    restaurants
	 * @return:Restaurants
	 */
	@Override
	public String addRestaurants(Restaurants restaurants) {
		if(restaurants!=null){
			restaurants.setCreatetime(new Date());
			restaurants.setUuid(StringUtil.getUUID());
			restaurants.setStatus(1);
			return restaurantsDao.addRestaurants(restaurants);
		}
		return null;
	}

	/*
	 * @Title: deleteRestaurants
	 * @Description: 通过主键删除商家; delete a restaurant by id
	 * @param:    long
	 * @return: int -1删除失败 1删除成功
	 */
	@Override
	public int deleteRestaurants(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(restaurantUuid);
			if(restaurants!=null){
				return restaurantsDao.deleteRestaurants(restaurants);
			}
		}
		return -1;
	}

	/*
	 * @Title: updateRestaurants
	 * @Description: 修改商家资料; update restaurant info
	 * @param:    Restaurants
	 * @return: int -1修改失败 1修改成功; -1:failed, 1:success
	 */
	@Override
	public int updateRestaurants(Restaurants restaurants) {
		if(restaurants!=null){
				return restaurantsDao.updateRestaurants(restaurants);
		}
		return -1;
	}

	/*
	 * @Title: checkRestaurantsNameUnique
	 * @Description: 验证商家名称是否唯一
	 * @param:    
	 * @return: int
	 */
	@Override
	public int checkRestaurantsNameUnique(Restaurants restaurants) {
		if(restaurants.getRestaurantName()!=null && restaurants.getRestaurantName().length()>0){
			Restaurants restaurants2 = restaurantsDao.getRestaurantsByName(restaurants.getRestaurantName());
			if(restaurants2!=null){
				if(restaurants.getId()!=null){//商家维护信息
					if(restaurants.getId()==restaurants2.getId()){
						return 1;
					}
				}
			}
			else{
				return 1;
			}
		}
		return -1;
	}
	
	/**
	 * @Title: getAllRestaurants
	 * @Description: 获取所有的商家对象
	 * @param: 
	 * @return List<PageRestaurant>  
	 */
	@Override
	public PageModel getAllRestaurants(PageFilter pf) {
		List<Restaurants> restaurantsList = restaurantsDao.getAllRestaurants(pf);
		List<PageRestaurant> li = new ArrayList<PageRestaurant>();
		if(restaurantsList.size()>0){
			for (Restaurants list : restaurantsList){
				PageRestaurant pr = new PageRestaurant();
				//设置商家管理员邮箱
				List<PageRestaurantAdmins> adminList = restaurantsUserService.getRestaurantsUsersByRestaurantUuid(list.getUuid()+"");
				boolean flag = false;
				if(adminList!=null && adminList.size()>0){
					for (PageRestaurantAdmins pageRestaurantAdmins : adminList) {
						if(flag){
							break;
						}
						if(pageRestaurantAdmins.getType()==1){
							String restaurantCode = pageRestaurantAdmins.getCode();
							if(StringUtil.isNotEmpty(restaurantCode)){
								flag = true;
								pr.setRestaurantEmail(restaurantCode);
							}
						}
					}
				}
				BeanUtils.copyProperties(list, pr);
				pr.setId(list.getId());
				pr.setRestaurantUuid(list.getUuid());
				pr.setRestaurantName(list.getRestaurantName());
				pr.setRestaurantContact(list.getRestaurantContact());
				pr.setRestaurantPhone(list.getRestaurantPhone());
				pr.setRestaurantAddress(list.getRestaurantAddress());
				pr.setRestaurantLng(list.getRestaurantLng());
				pr.setRestaurantLat(list.getRestaurantLat());
				if(list.getDistance()!=null){
					pr.setDistance(list.getDistance());
				}else{
					pr.setDistance(0.00);
				}
				if(list.getDeliverPrice()!=null){
					pr.setDeliverPrice(list.getDeliverPrice());
				}else{
					pr.setDeliverPrice(0.0);
				}
				if(list.getAvgPrice()!=null){
					pr.setAvgPrice(list.getAvgPrice());
				}else{
					pr.setAvgPrice(0.00);
				}
				pr.setFeatures(list.getFeatures());
				pr.setIsdelivery(list.getIsdelivery());
				pr.setIspickup(list.getIspickup());
				pr.setIsreservation(list.getIsreservation());
				if(list.getChainid()!=null){
					pr.setChainid(list.getChainid());
				}else{
					pr.setChainid(-1);
				}
				Set<Classification> set = list.getClassificationsSet();
				List<Long> classificationIdList = new ArrayList<Long>();
				for (Classification classification : set) {
					long classificationId = classification.getId();
					classificationIdList.add(classificationId);
				}
				pr.setClassificationId(classificationIdList);
				pr.setLogourl(list.getLogourl());
				pr.setStatus(list.getStatus());
				if(list.getNotice()!=null){
					pr.setNotice(list.getNotice());
				}else{
					pr.setNotice("");
				}
				pr.setDeliverTime(list.getDeliverTime());
				li.add(pr);
			}
		}
		PageModel pm = new PageModel();
		pm.setRows(li);
		pm.setTotal(restaurantsDao.getCount());
		return pm;
	}
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 审核商家
	 * @param: statu
	 * @return int  
	 */
	public int auditRestaurant(String restaurantUuid, int statu){
		Restaurants restaurant = restaurantsDao.getRestaurantByUuidToAudit(restaurantUuid);
		if(statu==0){//当审核状态是变成已经审核通过时，设置商家下面的所有管理员状态为审核通过状态
			Set<RestaurantsUser> restaurantsUsersSet = restaurant.getRestaurantsUsersSet();
			Set<RestaurantsUser> newRestaurantsUsersSet = new HashSet<RestaurantsUser>();
			for (RestaurantsUser restaurantsUser : restaurantsUsersSet) {
				if(restaurantsUser.getType()==1){//如果用户类型是管理员
					restaurantsUser.setStatus(0);
					String emailAddress = restaurantsUser.getCode();
					String title="Nomme account verification";//Your restaurant account"
					String content = "Your restaurant account is confirmed! You can login Nomme by \" <span style='color:#064977'>" 
								+ emailAddress + "</span> \".";
					MailUtil.sendMail(title, content, emailAddress);
					//String user = ((Admin)session.getAttribute("adminUser")).getLoginname();
					Log4jUtil.info("管理员审核商家", "测试发送邮件");
				}
				newRestaurantsUsersSet.add(restaurantsUser);
			}
			restaurant.setRestaurantsUsersSet(newRestaurantsUsersSet);
		}
		restaurant.setStatus(statu);
		return restaurantsDao.updateRestaurants(restaurant);
		
	}
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 获取商家信息到前台表格用于下拉框选择
	 * @param: 
	 * @return List<PageRestaurantSelect> 
	 */
	public List<PageRestaurantName> getAllRestaurantsName(){
		List<Restaurants> list = restaurantsDao.getAllRestaurantsName();
		List<PageRestaurantName> nameList = new ArrayList<PageRestaurantName>();
		for (Restaurants restaurant : list) {
			PageRestaurantName ptName = new PageRestaurantName();
			ptName.setId(restaurant.getId());
			ptName.setRestaurantUuid(restaurant.getUuid());
			ptName.setRestaurantName(restaurant.getRestaurantName());
			nameList.add(ptName);
		}
		return nameList;
	}

	/**
	 * @Title: RestaurantsDetailApiModel
	 * @Description: 店家详情
	 * @param: restaurantId
	 * @return restaurantsDetailApiModel 
	 */
	@Override
	public RestaurantsDetailApiModel restaurantsDetailApiModel(String restaurantUuid, String consumerUuid) {
		//Restaurants restaurants = restaurantsDao.getRestaurantsById(restaurantId);
		ViewRestaurant r = viewRestaurantDao.getRestaurantsByRestaurantUuid(restaurantUuid);
		ConsumersFavorites cf = consumersFavoritesDao.existFavoritesByConsumerUuidAndrestaurantUuid(consumerUuid, restaurantUuid);
		RestaurantsDetailApiModel rdam = new RestaurantsDetailApiModel();
		rdam.setRestaurantUuid(restaurantUuid);//商家Id
		if(StringUtil.isNotEmpty(r.getLogourl())){
			rdam.setLogourl(r.getLogourl());//店家logo
		} else {
			rdam.setLogourl(No_Picture.Picture);
		}
		rdam.setRestaurantName(r.getRestaurantName());//店家名称
		rdam.setRestaurantAddress(r.getRestaurantAddress()==null?"":r.getRestaurantAddress());//店家地址
		rdam.setAvgPrice(r.getAvgPrice()==null?0:r.getAvgPrice());//平均价格
		if(r.getScore() != null){//评分
			rdam.setScore(Math.floor(r.getScore()*100+0.5)/100.0);
		} else {
			rdam.setScore(0.0);
		}
		rdam.setClassificationName(r.getClassificationName()==null?"":r.getClassificationName());//类型名称
		rdam.setDeliverPrice(r.getDeliverPrice()==null?"":"Min. Delivery Order $"+r.getDeliverPrice());//外送起步价
		rdam.setDeliverTime(r.getDeliverTime()==null?"":"Est. Delivery Time "+r.getDeliverTime()+" minutes");//送餐所需时间
		rdam.setIsdelivery(r.getIsdelivery()==null?0:r.getIsdelivery());//是否外卖
		rdam.setIspickup(r.getIspickup()==null?0:r.getIspickup());//是否自取
		rdam.setIsreservation(r.getIsreservation()==null?0:r.getIsreservation());//是否预定
		//String description = "Min.$"+r.getDeliverPrice();
		rdam.setDescription(r.getFeatures()==null?"":r.getFeatures());
		if(StringUtil.isNotEmpty(r.getNotice())){
			rdam.setNotice(r.getNotice());
		} else {
			rdam.setNotice("");
		}
		if(r.getOpentime()!=null && r.getOpentime()>0){
			rdam.setIsOpen(1);//0：开业
		}else{
			rdam.setIsOpen(0);
		}
		if(r.getDiscountNum()!=null && r.getDiscountNum()>0){//如果大于零设置是否有优惠为0：有优惠
			rdam.setIsDiscount(1);
		}else{
			rdam.setIsDiscount(0);
		}
		if(StringUtil.isNotEmpty(consumerUuid)){
			ConsumersFavorites favorites= consumersFavoritesDao.existFavoritesByConsumerUuidAndrestaurantUuid(consumerUuid, restaurantUuid);
			//Evaluate evaluate = evaluateDao.getEvaluate(restaurantId, consumerId);
			if(favorites != null){
				rdam.setIsCollection(1);
			}
			else{
				rdam.setIsCollection(0);
			}
		}else {
			rdam.setIsCollection(0);
		}
		return rdam;
	}
	
	/**
	 * @Title: getPageRestaurantById
	 * @Description: 打开商家页面获取商家基础信息
	 * @return PageRestaurant  
	 */
	@SuppressWarnings("unchecked")
	public PageRestaurant getPageRestaurantByUuid(String restaurantUuid){
		PageRestaurant pr = new PageRestaurant();
		Restaurants restaurant = restaurantsDao.getRestaurantsByUuid(restaurantUuid);
		if(restaurant==null){
			return null;
		}
		pr.setId(restaurant.getId());//主键
		pr.setRestaurantUuid(restaurant.getUuid());
		pr.setRestaurantName(restaurant.getRestaurantName());// 店名
		pr.setRestaurantContact(restaurant.getRestaurantContact());//餐厅负责人
		pr.setRestaurantPhone(restaurant.getRestaurantPhone());// 联系电话
		pr.setRestaurantAddress(restaurant.getRestaurantAddress());// 地址
		pr.setRestaurantLng(restaurant.getRestaurantLng());// 纬度
		pr.setRestaurantLat(restaurant.getRestaurantLat());// 经度
		pr.setDistance(restaurant.getDistance());// 外卖距离
		pr.setDeliverPrice(restaurant.getDeliverPrice());// 外卖起步价
		pr.setAvgPrice(restaurant.getAvgPrice());// 人均消费价格
		pr.setFeatures(restaurant.getFeatures());// 特征
		pr.setIsdelivery(restaurant.getIsdelivery());//是否外送
		pr.setIspickup(restaurant.getIspickup());//是否自取
		pr.setIsreservation(restaurant.getIsreservation());//是否堂食
		if(restaurant.getChainid()!=null){//连锁店名称
			long chainId = restaurant.getChainid();
			Chain chain = chainDao.getById(chainId);
			if(chain!=null){
				pr.setChainid((int) chainId);
				pr.setChainName(chain.getChainname());
			}else{
				pr.setChainid(-1);
				pr.setChainName(null);
			}
		}else{
			pr.setChainid(-1);
			pr.setChainName("");
		}
		pr.setLogourl(restaurant.getLogourl());//餐厅图片地址
		if(StringUtil.isNotEmpty(restaurant.getNotice())){
			pr.setNotice(restaurant.getNotice());//消息
		}else {
			pr.setNotice("");//消息
		}
		pr.setDeliverTime(restaurant.getDeliverTime());//外卖时间
		pr.setAreasId(restaurant.getViewArea().getAreaId());//所属区域id
		double taxRate = restaurant.getViewArea().getGst()+restaurant.getViewArea().getPst();
		pr.setTaxRate(taxRate);
		
		/*ViewArea ViewArea = restaurant.getViewArea(); 
		PageAreas pa = new PageAreas();
		pa.setPid(areas.getParent().getId()+"");////父类区域id
		pa.setParentAreaName(areas.getParent().getAreaName());//父类区域名称
		pa.setParentTax(areas.getParent().getTax()+"");//父税率
		pa.setId(areas.getId()+"");//区域的id
		pa.setAreaName(areas.getAreaName());//区域名称
		pa.setTax(areas.getTax()+"");//区域税率 例：0.05;
		pa.setTaxMode(areas.getTaxMode());//税额计算显示方式   1表示： HST  2表示：GST+PST  0表示：父区域（不用于显示 ） 
		pr.setPageAreas(pa);//组装后的区域税率对象装到餐厅对象里面
*/		Set<Evaluate> evaluateSet = restaurant.getEvaluatesSet();//获取商家的所有评论集合
		
		List<PageEvaluate> evaluateList = new ArrayList<PageEvaluate>();
		if(evaluateSet.size()>0){
			double num = evaluateSet.size();
			double totalScore = 0;
			for (Evaluate evaluate : evaluateSet) {
				totalScore += evaluate.getScore();
				PageEvaluate pageEvaluate = new PageEvaluate();
				pageEvaluate.setContent(evaluate.getContent());
				pageEvaluate.setScore(evaluate.getScore());
				pageEvaluate.setCreatetime(evaluate.getCreatetime());
				String consumerName = evaluate.getConsumers().getFirstName();
				pageEvaluate.setConsumer(consumerName);
				evaluateList.add(pageEvaluate);
			}
			Collections.sort(evaluateList,new PageEvaluteComparator());
			pr.setPageEvaluateList(evaluateList);
			pr.setScore((double)Math.round(totalScore/num));
			
		}else{
			pr.setScore(0);
		}
		List<PageDiscount> pageDiscountList = new ArrayList<PageDiscount>();
		List<Discount> discountList =  discountDao.getAllDiscounts(restaurant);
		if(discountList.size()>0){
			for (Discount discount : discountList) {
				PageDiscount pageDiscount = new PageDiscount();
				pageDiscount.setId(discount.getId());
				pageDiscount.setContent(discount.getContent());
				pageDiscount.setPrice(discount.getDiscount());
				pageDiscount.setConsumePrice(discount.getConsumePrice());
				pageDiscount.setDiscount(discount.getDiscount());
				pageDiscount.setType(discount.getType());
				pageDiscount.setStartTime(discount.getStartTime());
				pageDiscount.setEndTime(discount.getEndTime());
				pageDiscount.setOrderType(discount.getOrderType());
				pageDiscount.setDishId(discount.getDishId());
				if(discount.getDishId()!=null){
					Dish dish = dishDao.getDishById(discount.getDishId());
					if(dish!=null){
						pageDiscount.setDishName(dish.getEnName());
					}
				}
				pageDiscountList.add(pageDiscount);
			}
			pr.setPageDiscountList(pageDiscountList);
			
		}
		return pr;
	}
	
	/**
	 * @Title: getPageRestaurantById
	 * @Description: 通过id和是否外卖获取PageRestaurantsMenu集合
	 * @param: long id
	 * @param: int isPickup
	 * @return PageRestaurant  
	 */
	@Override
	public List<PageRestaurantsMenu> getRestaurantsMenusByRestaurantsIdAndIsPickup(String restaurantUuid){
		//获取当前餐厅所有的菜单
		List<RestaurantsMenu> restaurantsMenuList = restaurantsMenuDao.getRestaurantsMenuByRestaurantsUuid(restaurantUuid);
		if(restaurantsMenuList.size()>0){
			//菜单分类列表用于遍历
			long restaurantId = restaurantsMenuList.get(0).getRestaurants().getId();
			List<PageDish> popularDishList = new ArrayList<PageDish>();
			List<PageRestaurantsMenu> pageRestaurantsMenuList = new ArrayList<PageRestaurantsMenu>();
			for (RestaurantsMenu restaurantsMenu : restaurantsMenuList) {
				//菜单类里面放多个菜品
				PageRestaurantsMenu prm = new PageRestaurantsMenu();
				prm.setId(restaurantsMenu.getId());
				prm.setMenuName(restaurantsMenu.getMenuName());
				prm.setRestaurantId(restaurantsMenu.getRestaurants().getId());
				prm.setDescribe(restaurantsMenu.getDescribe());
				//获取到菜单分类下所有菜品
				Set<Dish> dishSet = restaurantsMenu.getDishs();
				List<PageDish> pageDishList = new ArrayList<PageDish>();
				for (Dish dish : dishSet) {
					if(dish.getStatus()==0){
						PageDish pd = new PageDish();
						pd.setId(dish.getId());//菜品id
						pd.setChName(dish.getChName());//菜品中文名称
						pd.setEnName(dish.getEnName());//菜品英文名称
						pd.setPrice(dish.getPrice());//菜品价格
						pd.setPhotoUrl(dish.getPhotoUrl());//菜品图片链接
						pageDishList.add(pd);
						
						// If this dish is popular, add it to the list of popular dishes.
						if (dish.getIsPopular() == 1) {
							PageDish popularPageDish = new PageDish();
							popularPageDish.setId(dish.getId());
							popularPageDish.setChName(dish.getChName());
							popularPageDish.setEnName(dish.getEnName());
							popularPageDish.setPrice(dish.getPrice());
							popularPageDish.setPhotoUrl(dish.getPhotoUrl());
							popularDishList.add(popularPageDish);
						}
					}
				}
				Collections.sort(pageDishList);
				if(pageDishList.size()>0){
					//说明当前菜单下有多个菜品
					//有多个菜品的情况下，将菜品的list设到PageRestaurantsMenu（当前菜单下面）
					prm.setPageDishList(pageDishList);
					//再将菜单加入到结果集中，如果当前菜单下没有菜品就不显示该菜单分类
					pageRestaurantsMenuList.add(prm);
				}
			}
			// Create a category for popular dishes.
			if (popularDishList.size() > 0) {
				PageRestaurantsMenu popularDishMenu = new PageRestaurantsMenu();
				popularDishMenu.setId(0);
				popularDishMenu.setMenuName("Popular Dishes");
				popularDishMenu.setRestaurantId(restaurantId);
				popularDishMenu.setDescribe("Our most popular dishes.");
				popularDishMenu.setPageDishList(popularDishList);
				pageRestaurantsMenuList.add(popularDishMenu);
			}
			Collections.sort(pageRestaurantsMenuList);
			return pageRestaurantsMenuList;
		}
		return null;
	}

	/** 
	 * @Title: getRestaurantsById
	 * @Description: 通过商家id查找商家
	 * @param:    商家id
	 * @return: Restaurants
	 */
	@Override
	public RestaurantsApiModel getRestaurantByUuid(String restaurantUuid) {
		Restaurants r = restaurantsDao.getRestaurantsByUuid(restaurantUuid);
		RestaurantsApiModel ram = new RestaurantsApiModel();
		ram.setRestaurantUuid(r.getUuid());
		ram.setRestaurantName(r.getRestaurantName());
		ram.setRestaurantContact(r.getRestaurantContact());
		ram.setRestaurantPhone(r.getRestaurantPhone());
		ram.setRestaurantAddress(r.getRestaurantAddress());
		ram.setRestaurantLng(r.getRestaurantLng());
		ram.setRestaurantLat(r.getRestaurantLat());
		ram.setCreatetime(r.getCreatetime());
		ram.setStatus(r.getStatus());
		ram.setAvgPrice(r.getAvgPrice());
		ram.setFeatures(r.getFeatures());
		ram.setIsdelivery(r.getIsdelivery());
		ram.setIspickup(r.getIspickup());
		ram.setIsreservation(r.getIsreservation());
		ram.setChainid(r.getChainid());
		ram.setLogourl(r.getLogourl());
		ram.setDeliverTime(r.getDeliverTime());
		return ram;
	}

	/** 
	 * @Title: getRestaurantMoreById
	 * @Description: 通过商家id查找商家(more)
	 * @param:    商家id
	 * @return: RestaurantsMoreApiModel
	 */
	@Override
	public RestaurantsMoreApiModel getRestaurantMoreById(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			Restaurants r = restaurantsDao.getRestaurantsByUuid(restaurantUuid);
			RestaurantsMoreApiModel rmam = new RestaurantsMoreApiModel();
			if(r != null){
				rmam.setFeatures(r.getFeatures());
			}
			return rmam;
		}
		return null;
	}
	
	/**
	 * @Title: getCurrentLocalTimeFromRestaurantsUuid
	 * @Description: get the local time from restaurant's uuid
	 * @param: restaurantUuid
	 * @return: Date
	 */
	@Override
	public Date getCurrentLocalTimeFromRestaurantsUuid(String restaurantUuid) {
		Restaurants restaurant = restaurantsDao.getRestaurantsByUuid(restaurantUuid);
		Date currentLocalTime = new Date();
		if (restaurant != null) {
			currentLocalTime = GoogleTimezoneAPIUtil.getLocalDateTime(restaurant.getRestaurantLat(),
					restaurant.getRestaurantLng());
		}
		return currentLocalTime;
	}

}