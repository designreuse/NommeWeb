package com.camut.controller.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.dao.RestaurantsDao;
import com.camut.framework.constant.MessageConstant;
import com.camut.framework.constant.NommeEmail;
import com.camut.model.GarnishHeader;
import com.camut.model.OrderCharity;
import com.camut.model.api.CharityAllApiModel;
import com.camut.model.api.ResultApiModel;
import com.camut.model.api.SearchRestaurantsApiModel;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;
import com.camut.service.CharityService;
import com.camut.service.DishService;
import com.camut.service.GarnishHeaderService;
import com.camut.service.OpenTimeService;
import com.camut.service.OrderCharityService;
import com.camut.service.ViewRestaurantService;
import com.camut.utils.Log4jUtil;

/**
 * @entity Controller .
 * @author zyf
 * @createTime 2015-06-08
 * @author
 * @updateTime
 * @memo
 */
@Controller("CommonController")
@RequestMapping("/api")
public class CommonController extends BaseAPiModel{

	@Autowired private GarnishHeaderService garnishHeaderService;
	@Autowired private DishService dishService;
	@Autowired private RestaurantsDao restaurantsDao;
	@Autowired private ViewRestaurantService viewRestaurantService;
	@Autowired private CharityService charityService;
	@Autowired private OrderCharityService orderCharityService;
	@Autowired private OpenTimeService openTimeService;
	
	/**
	 * @Title: searchRestaurant
	 * @Description: 搜索
	 * @param: restaurantsApiModel
	 * @return ResultApiModel 
	 */
	@RequestMapping(value ="/search", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel searchRestaurantOld(SearchRestaurantsApiModel viewRestaurant, PageModel pm){
		Log4jUtil.info("搜索商家接口==>"+viewRestaurant.toString()+pm.toString());
		ResultApiModel ram = new ResultApiModel();
		PageFilter pf = new PageFilter();
		pf.setOffset(pm.getCurrentPageIndex());
		try {
			ram.setFlag(1);
			ram.setBody(viewRestaurantService.getSearchRestaurants(viewRestaurant, pf));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getDishMenu
	 * @Description: 获取菜单信息
	 * @param:    restaurantId , type 
	 * @return: ResultApiModel
	 */
	@RequestMapping(value ="/restaurant/dish/menu", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getDishMenu(int restaurantId, int type) {
		Log4jUtil.info("获取菜单信息接口==>"+"restaurantId"+restaurantId+"type"+type);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(dishService.getDishMenu(restaurantId, type));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getDish
	 * @Description: 配菜类型
	 * @param:    menuId
	 * @return: ResultApiModel
	 */
	@RequestMapping(value ="/restaurant/garnish/menu", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getDish(long dishId) {
		Log4jUtil.info("配菜类型接口==>dishId="+dishId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(garnishHeaderService.getDish(dishId));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getGarnish
	 * @Description: 配菜信息
	 * @param:    garnishHeaderId
	 * @return: List<GarnishApiModel>
	 */
	@RequestMapping(value ="/restaurant/garnish/item", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getGarnish(long dishId, long garnishMenuId){//参数：配菜分类id
		Log4jUtil.info("配菜信息接口==>"+"dishId="+dishId+"garnishMenuId="+garnishMenuId);
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(garnishHeaderService.getGarnishItemByGarnishHeaderId(dishId, garnishMenuId));
			GarnishHeader gh = garnishHeaderService.getGarnishHeaderById(garnishMenuId);
			if(gh != null){
				if(gh.getCount() == 1){
					ram.setResultMessage("Choose 1");
				} else if (gh.getCount() > 1) {
					ram.setResultMessage("Maximum " + gh.getCount() + " items/types of side dishes");
				}
			}
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getGarnish
	 * @Description: 获取商家数量
	 * @param:    garnishHeaderId
	 * @return: List<GarnishApiModel>
	 */
	@RequestMapping(value ="/search/count", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel searchRestaurantCount(SearchRestaurantsApiModel viewRestaurant, PageFilter pf){
		Log4jUtil.info("获取商家数量接口==>"+"viewRestaurant="+viewRestaurant.toString()+"pf="+pf.toString());
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(viewRestaurantService.getSearchRestaurantsCount(viewRestaurant, pf));
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: getCharity
	 * @Description: 显示所有慈善机构
	 * @param:    
	 * @return: List<CharityApiModel>
	 */
	@RequestMapping(value ="/search/charity", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getCharity(String orderId){
		Log4jUtil.info("显示所有慈善机构接口==>"+"orderId="+orderId);
		ResultApiModel ram = new ResultApiModel();
		CharityAllApiModel charityAllApiModel=charityService.getCharity(orderId);
		try {
			ram.setFlag(1);
			ram.setBody(charityAllApiModel);
			if(charityAllApiModel!=null){
				ram.setTotal(charityService.getCharity(orderId).getTotal());
			}else{
				ram.setTotal(0);
			}
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
	
	/**
	 * @Title: saveOrderCharity
	 * @Description: 保存用户与慈善机构的方法
	 * @param:    
	 * @return: 
	 */
	@RequestMapping(value ="/save/ordercharity", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel saveOrderCharity(OrderCharity orderCharity) {
		Log4jUtil.info("保存慈善机构接口==>"+orderCharity.toString());
		ResultApiModel ram = new ResultApiModel();
		int flag = orderCharityService.saveOrderCharity(orderCharity);
		if (flag == -1) {// 添加失败
			ram.setResultMessage(MessageConstant.ALL_FAILED);
			ram.setFlag(-1);
		} else if (flag == 1) {// 添加成功
			ram.setResultMessage("");
			ram.setFlag(1);
		}
		return ram;
	}
	
	/**
	 * @Title: getNommeEmail
	 * @Description: email
	 * @param:    
	 * @return: 
	 */
	@RequestMapping(value="/nommeemail",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getNommeEmail(String consumerId) {
		Log4jUtil.info("NommeEmail接口==>"+"consumerId="+consumerId);
		ResultApiModel ram = new ResultApiModel();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", NommeEmail.Email);
		ram.setBody(map);
		ram.setFlag(1);
		return ram;
	}
	
	
	/**
	 *  @Title: getOpenTime
	 * @Description: 获取营业时间
	 * @param:    String orderDate:like "yyyy-MM-dd"; String restaurantId;String type
	 * @return: ResultApiModel
	 */
	@RequestMapping(value="/getOpenTime",method=RequestMethod.POST)
	@ResponseBody
	public ResultApiModel test(String orderDate,String restaurantId,String type){
		Log4jUtil.info("获取营业时间接口==>"+"orderDate="+orderDate+"restaurantId="+restaurantId+"type="+type);
		ResultApiModel ram = new ResultApiModel();
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(orderDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ram.setFlag(-1);
			ram.setResultMessage("Time format is not correct!");
			return ram;
		}
		ram.setFlag(1);
		ram.setBody(openTimeService.getOpenTimeByOrderDate(date, Long.parseLong(restaurantId), Integer.parseInt(type))==null?"":openTimeService.getOpenTimeByOrderDate(date, Long.parseLong(restaurantId), Integer.parseInt(type)));
		return ram;
	}
	
	/**
	 * @Title: getOftenCharity
	 * @Description: 获取常用的慈善机构（3个）
	 * @param:    
	 * @return: List<Charity>
	 */
	@RequestMapping(value ="/search/oftencharity", method = RequestMethod.POST)
	@ResponseBody
	public ResultApiModel getOftenCharity(){
		ResultApiModel ram = new ResultApiModel();
		try {
			ram.setFlag(1);
			ram.setBody(charityService.getOftenCharity());
			ram.setResultMessage("");
		} catch (Exception e) {
			ram.setFlag(-1);
			ram.setResultMessage(e.toString());
		}
		return ram;
	}
}
