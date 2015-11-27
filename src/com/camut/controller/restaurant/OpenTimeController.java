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
import com.camut.model.OpenTime;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageOpenTime;
import com.camut.service.OpenTimeService;

/**
 * @ClassName OpenTimeController.java
 * @author wangpin
 * @createtime Jun 24, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("OpenTimeController")
@RequestMapping("opentime")
public class OpenTimeController extends BaseController{

	@Autowired
	private OpenTimeService openTimeService;
	
	/**
	 * @Title: toOpenTime
	 * @Description: 跳转营业时间页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toOpenTime",method=RequestMethod.GET)
	public String toOpenTime(){
		return "restaurant/opentime";
	}
	
	/**
	 * @Title: getAllOpenTime
	 * @Description: 获取商家的营业时间
	 * @param:    HttpServletRequest
	 * @return: List<PageOpenTime>
	 */
	@RequestMapping(value="getAllOpenTime",method=RequestMethod.POST)
	@ResponseBody
	public List<PageOpenTime> getAllOpenTime(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return openTimeService.getAllOpenTime(restaurants.getUuid());
	}
	
	/**
	 * @Title: addOpenTime
	 * @Description: 增加营业时间
	 * @param:    OpenTime HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="addOpenTime",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addOpenTime(HttpServletRequest request){
		int flag1=0;
		int flag2=0;
		int flag3=0;
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		String[] weeks =  request.getParameterValues("week");
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");
		if (endtime.equals("00:00")) {
			endtime = "23:59";
		}
		PageMessage pm = new PageMessage();
		for (String string : weeks) {
			int week = Integer.parseInt(string);
			OpenTime openTime = new OpenTime();
			openTime.setEndtime(endtime);
			openTime.setStarttime(starttime);
			openTime.setRestaurants(restaurants);
			openTime.setWeek(week);
			if (request.getParameter("role1")!=null) {
				openTime.setType(Integer.parseInt(request.getParameter("role1")));
				flag1 = openTimeService.addOpenTime(openTime);
			}
			if (request.getParameter("role2")!=null) {
				openTime.setType(Integer.parseInt(request.getParameter("role2")));
				flag2 = openTimeService.addOpenTime(openTime);
			}
			if (request.getParameter("role3")!=null) {
				openTime.setType(Integer.parseInt(request.getParameter("role3")));
				flag3 = openTimeService.addOpenTime(openTime);
			}
			
			if(flag1==-1 || flag2==-1 ||flag3==-1){
				pm.setErrorMsg(MessageConstant.ADD_FAILED);
				pm.setSuccess(false);
				return pm;
			}
		}
		return pm;
	}
	
	/**
	 * @Title: addOpenTime
	 * @Description: 删除营业时间
	 * @param:    OpenTime HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteOpenTime",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteOpenTime(OpenTime openTime,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		openTime.setRestaurants(restaurants);
		int flag = openTimeService.deleteOpenTime(openTime);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
}
