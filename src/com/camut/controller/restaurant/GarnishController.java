/**
 * 
 */
package com.camut.controller.restaurant;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.GarnishHeader;
import com.camut.model.GarnishItem;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageGarnish;
import com.camut.pageModel.PageGarnishHeader;
import com.camut.pageModel.PageMessage;
import com.camut.service.GarnishHeaderService;
import com.camut.service.GarnishService;
import com.camut.service.RestaurantsService;

/**
 * @ClassName GarnishController.java
 * @author wangpin
 * @createtime Jun 15, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("GarnishController")
@RequestMapping("garnish")
public class GarnishController extends BaseController {

	@Autowired
	private RestaurantsService restaurantsService;
	
	@Autowired
	private GarnishHeaderService garnishHeaderService;
	
	@Autowired
	private GarnishService garnishService;
	
	/**
	 * @Title: toGarnishHeader
	 * @Description: 跳转配菜头页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toGarnishHeader",method=RequestMethod.GET)
	public String toGarnishHeader(){
		return "restaurant/garnishheader";
	}
	
	/**
	 * @Title: toGarnish
	 * @Description:跳转配菜页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toGarnish",method=RequestMethod.GET)
	public String toGarnish(){
		return "restaurant/garnish";
	}
	
	/**
	 * @Title: dataTable
	 * @Description: 返回配菜头数据
	 * @param:    
	 * @return: PageModel
	 */
	@RequestMapping(value="dataTableGarnishHeader")
	@ResponseBody
	public List<PageGarnishHeader> dataTableGarnishHeader(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return garnishHeaderService.getAllGarnishHeader(restaurants);
	}
	
	/**
	 * @Title: addGarnishHeader
	 * @Description: 增加配菜头
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="addGarnishHeader",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addGarnishHeader(GarnishHeader garnishHeader,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		garnishHeader.setRestaurants(restaurants);
		int flag = garnishHeaderService.addGarnishHeader(garnishHeader);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	/**
	 * @Title: updateGarnishHeader
	 * @Description: 修改配菜头
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateGarnishHeader",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateGarnishHeader(GarnishHeader garnishHeader,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		garnishHeader.setRestaurants(restaurants);
		int flag = garnishHeaderService.updateGarnishHeader(garnishHeader);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: updateGarnishHeader
	 * @Description: 删除配菜头
	 * @param:    long
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteGarnishHeader",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteGarnishHeader(long id){
		PageMessage pm = new PageMessage();
		int flag = garnishHeaderService.deleteGarnishHeader(id);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: dataTableGarnish
	 * @Description: 返回配菜的数据表格
	 * @param:    HttpSession PageFilter
	 * @return: PageModel
	 */
	@RequestMapping(value="dataTableGarnish")
	@ResponseBody
	public  List<PageGarnish> dataTableGarnish(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return garnishService.getAllGarnish(restaurants);
	}
	
	/**
	 * @Title: addGarnish
	 * @Description: 增加配菜
	 * @param:   GarnishItem  long
	 * @return: PageMessage
	 */
	@RequestMapping(value="addGarnish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addGarnish(GarnishItem garnishItem,long garnishHeaderId){
		PageMessage pm = new PageMessage();
		GarnishHeader garnishHeader = new GarnishHeader();
		garnishHeader.setId(garnishHeaderId);
		garnishItem.setGarnishHeader(garnishHeader);
		garnishItem.setStatus(0);//默认有效状态
		int flag = garnishService.addGarnish(garnishItem);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: updateGarnish
	 * @Description: 修改配菜
	 * @param:   GarnishItem  long
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateGarnish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateGarnish(GarnishItem garnishItem,long garnishHeaderId){
		PageMessage pm = new PageMessage();
		GarnishHeader garnishHeader = new GarnishHeader();
		garnishHeader.setId(garnishHeaderId);
		garnishItem.setGarnishHeader(garnishHeader);
		garnishItem.setStatus(0);
		int flag = garnishService.updateGarnish(garnishItem);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: deleteGarnish
	 * @Description: 删除配菜
	 * @param:    long
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteGarnish",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteGarnish(long id){
		PageMessage pm = new PageMessage();
		int flag = garnishService.deleteGarnishItem(id);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getGarnishByHeader
	 * @Description: 根据配菜头查找配菜
	 * @param:    
	 * @return: List<PageGarnish>
	 */
	@RequestMapping(value="getGarnishByHeader",method=RequestMethod.POST)
	@ResponseBody
	public List<PageGarnish> getGarnishByHeader(GarnishHeader garnishHeader){
		return garnishService.getGarnishByHeader(garnishHeader);
	}
	
	/**
	 * @Title: getGarnishByHeader
	 * @Description: 检查给定配菜分类下是否含有配菜
	 * @param:    String
	 * @return: List<PageGarnish>
	 */
	@RequestMapping(value="checkGarnishHeader",method=RequestMethod.POST)
	@ResponseBody
	public int checkGarnishHeader(String id){
		return garnishHeaderService.checkGarnishHeader(id);
	}
}
