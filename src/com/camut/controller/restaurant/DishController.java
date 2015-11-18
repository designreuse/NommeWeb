/**
 * 
 */
package com.camut.controller.restaurant;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.alibaba.druid.support.json.JSONUtils;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Dish;
import com.camut.model.DishGarnish;
import com.camut.model.GarnishItem;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsMenu;
import com.camut.pageModel.PageDish;
import com.camut.pageModel.PageDishMenu;
import com.camut.pageModel.PageMessage;
import com.camut.service.DishGarnishService;
import com.camut.service.DishService;
import com.camut.service.RestaurantsMenuService;
import com.camut.service.RestaurantsService;
import com.camut.utils.ImageUtils;

/**
 * @ClassName DiahController.java
 * @author wangpin
 * @createtime Jun 16, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("DishController")
@RequestMapping("dish")
public class DishController extends BaseController {

	@Autowired
	private RestaurantsMenuService restaurantsMenuService;
	
	@Autowired
	private RestaurantsService restaurantsService;
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private DishGarnishService dishGarnishService;

	
	/**
	 * @Title: toDishHeader
	 * @Description: 跳转菜品分类页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toDishMenu",method=RequestMethod.GET)
	public String toDishHeader(){
		return "restaurant/dishmenu";
	}
	
	/**
	 * @Title: toDish
	 * @Description: 返回菜品管理页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toDishManager",method=RequestMethod.GET)
	@Transactional
	public String toDishManager(Model model,HttpServletRequest request){
		model.addAttribute("type", request.getParameter("type"));
		if(request.getParameter("type").equals("update")){
			//PageDish pd = dishService.getPageDishById(request.getParameter("id"));
			model.addAttribute("modelDishId",request.getParameter("id"));
		}
		return "restaurant/dishmanager";
	}
	
	/**
	 * @Title: toDish
	 * @Description: 返回菜品页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toDish",method=RequestMethod.GET)
	public String toDish(){
		return "restaurant/dish";
	}
	
	/**
	 * @Title: getAllDishMenu
	 * @Description:获取所有的菜品分类 
	 * @param:    
	 * @return: List<PageDishMenu>
	 */
	@RequestMapping(value="getAllDishMenu")
	@ResponseBody
	public List<PageDishMenu> getAllDishMenu(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return restaurantsMenuService.getAllRestaurantsMenu(restaurants);
	}
	
	/**
	 * @Title: addRestaurantsMenu
	 * @Description: 增加菜品分类
	 * @param:    HttpSession RestaurantsMenu
	 * @return: PageMessage
	 */
	@RequestMapping(value="addRestaurantsMenu",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addRestaurantsMenu(HttpServletRequest request,RestaurantsMenu restaurantsMenu){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		restaurantsMenu.setRestaurants(restaurants);
		int flag = restaurantsMenuService.addRestaurantsMenu(restaurantsMenu);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: updateRestaurantsMenu
	 * @Description: 修改菜品分类
	 * @param:    HttpServletRequest RestaurantsMenu
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateRestaurantsMenu",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateRestaurantsMenu(HttpServletRequest request,RestaurantsMenu restaurantsMenu){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		restaurantsMenu.setRestaurants(restaurants);
		int flag = restaurantsMenuService.updateRestaurantsMenu(restaurantsMenu);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: deleteRestaurantsMenu
	 * @Description: 删除菜品分类
	 * @param:    long
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteRestaurantsMenu",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteRestaurantsMenu(long id){
		PageMessage pm = new PageMessage();
		int flag = restaurantsMenuService.deleteRestaurantsMenu(id);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getAllDish
	 * @Description: 获取所有的菜品信息
	 * @param:    
	 * @return: List<PageDish>
	 */
	@RequestMapping(value="getAllDish")
	@ResponseBody
	public List<PageDish> getAllDish(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return dishService.getAllDish(restaurants);
	}
	
	/**
	 * @Title: addDishPhoto
	 * @Description: 上传图片
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="addDishPhoto")
	@ResponseBody
	public String addDishPhoto(HttpServletRequest request){
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		MultipartFile file = multipartRequest.getFile("photo");
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		String tmpDir = restaurants.getId()+"";
		String logourl = "";
		try {
			logourl = ImageUtils.saveImage(file, request, "upload/Dish", tmpDir, true);
		} catch (Exception e) {
			return null;
		}
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		return basePath+logourl;
	}
	
	/**
	 * @Title: addDish
	 * @Description: 增加菜品信息
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="addDish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addDish(Dish dish,HttpServletRequest request,Long dishMenuId){
		PageMessage pm = new PageMessage();
		/*role1 = role1==null?0:role1;
		role2 = role2==null?0:role2;
		role3 = role3==null?0:role3;*/
		dish.setCreatedate(new Date());
		dish.setStatus(0);//增加默认上架
		dish.setIsPickup(7);//二进制权限
		RestaurantsMenu menu = new RestaurantsMenu();
		menu.setId(dishMenuId);
		dish.setRestaurantsMenu(menu);
		dish.setRestaurantUuid(this.getRestaurants(request.getSession(), request).getUuid());
		int flag = dishService.addDish(dish);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		else{
			pm.setFlag(flag);
		}
		return pm;
	}
	
	/**
	 * @Title: addDish
	 * @Description: 修改菜品信息
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateDish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateDish(Dish dish,HttpServletRequest request,Long dishMenuId){
		PageMessage pm = new PageMessage();
		/*role1 = role1==null?0:role1;
		role2 = role2==null?0:role2;
		role3 = role3==null?0:role3;*/
		dish.setIsPickup(7);//二进制权限
		RestaurantsMenu menu = new RestaurantsMenu();
		menu.setId(dishMenuId);
		dish.setRestaurantsMenu(menu);
		dish.setRestaurantUuid(this.getRestaurants(request.getSession(), request).getUuid());
		Dish dish2 = dishService.getDishById(dish.getId()+"");
		dish.setCreatedate(dish2.getCreatedate());
		dish.setStatus(dish2.getStatus());
		int flag = dishService.updateDish(dish);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		else{
			pm.setFlag(dish.getId().intValue());//将菜品的主键返回
		}
		return pm;
	}
	
	/**
	 * @Title: addDishGarnish
	 * @Description: 保存菜品与配菜的关系
	 * @param:    HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="addDishGarnish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addDishGarnish(HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Dish dish = new Dish();
		dish.setId(Long.parseLong(request.getParameter("dishId").toString()));
		//先把原来的菜品配菜关系删除
		if(dishGarnishService.deleteDisnGarnishByDish(dish)==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
			return pm;
		} 
		String jsonStr = request.getParameter("dishGarnish");
		//JSONArray jsonArray = JSONArray.parseArray(jsonStr);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> mapListJson = (List<Map<String, Object>>) JSONUtils.parse(jsonStr);
		//List<Map<String,Object>> mapListJson = (List)jsonArray;
		for(Map<String,Object> m:mapListJson){
			GarnishItem garnishItem = new GarnishItem();
			garnishItem.setId(Long.parseLong(m.get("id").toString()));
			int garnishHeaderId = Integer.parseInt(m.get("garnishHeaderId").toString());
			double addprice = Double.parseDouble(m.get("addprice").toString());
			DishGarnish dishGarnish = new DishGarnish(dish, garnishItem, addprice,garnishHeaderId);
			int flag = dishGarnishService.addDishGarnish(dishGarnish);
			if(flag==-1){
				pm.setErrorMsg(MessageConstant.ALL_FAILED);
				pm.setSuccess(false);
			}
		}
		return pm;
	}
	
	/**
	 * @Title: checkRestaurantMenu
	 * @Description: 检查给定的菜品分类下是否有菜品
	 * @param:    String
	 * @return: int
	 */
	@RequestMapping(value="checkRestaurantMenu",method=RequestMethod.POST)
	@ResponseBody
	public int checkRestaurantMenu(String id){
		return restaurantsMenuService.checkRestaurantMenu(id);
	}
	
	/**
	 * @Title: checkRestaurantMenu
	 * @Description: 删除菜品
	 * @param:    String
	 * @return: int
	 */
	@RequestMapping(value="deleteDish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteDish(Dish dish){
		PageMessage pm = new PageMessage();
		Dish dish2 = dishService.getDishById(dish.getId().toString());
		dish2.setStatus(dish.getStatus());
		int flag = dishService.deleteDish(dish2);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getAllAvailableDish
	 * @Description: 获取所有上架的菜品
	 * @param:    Restaurants
	 * @return: List<PageDish>
	 */
	@RequestMapping(value="getAllAvailableDish",method=RequestMethod.POST)
	@ResponseBody
	public List<PageDish> getAllAvailableDish(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return dishService.getAllAvailableDish(restaurants);
	}
	
	/**
	 * @Title: getAllAvailableDish
	 * @Description: 获取给定id的菜品
	 * @param:    Restaurants
	 * @return: List<PageDish>
	 */
	@RequestMapping(value="getDishById",method=RequestMethod.POST)
	@ResponseBody
	public PageDish getDishById(String id){
		return dishService.getPageDishById(id);
	}
	
}
