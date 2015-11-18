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
import com.camut.model.DistancePrice;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageDistancePrice;
import com.camut.pageModel.PageMessage;
import com.camut.service.DistancePriceService;

/**
 * @ClassName DistancePriceController.java
 * @author wangpin
 * @createtime Jun 28, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("DistancePriceController")
@RequestMapping("distancePrice")
public class DistancePriceController extends BaseController {

	@Autowired
	private DistancePriceService  distancePriceService;
	
	/**
	 * @Title: toDistancePrice
	 * @Description: 返回配送收费页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toDistancePrice",method=RequestMethod.GET)
	public String toDistancePrice(){
		return "restaurant/distanceprice";
	}
	
	/**
	 * @Title: getAllDistancePrice
	 * @Description: 获取商家所有的配送收费
	 * @param:    HttpServletRequest
	 * @return: List<PageDistancePrice>
	 */
	@RequestMapping(value="getAllDistancePrice",method=RequestMethod.GET)
	@ResponseBody
	public List<PageDistancePrice> getAllDistancePrice(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return distancePriceService.getAllDistancePrice(restaurants);
	}
	
	/**
	 * @Title: addDistancePrice
	 * @Description: 增加配送收费信息
	 * @param:    DistancePrice HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="addDistancePrice",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addDistancePrice(DistancePrice distancePrice,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		distancePrice.setRestaurants(restaurants);
		int flag = distancePriceService.addDistancePrice(distancePrice);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: addDistancePrice
	 * @Description: 修改配送收费信息
	 * @param:    DistancePrice HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateDistancePrice",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateDistancePrice(DistancePrice distancePrice,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		distancePrice.setRestaurants(restaurants);
		int flag = distancePriceService.updateDistancePrice(distancePrice);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: addDistancePrice
	 * @Description: 删除配送收费信息
	 * @param:    DistancePrice HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteDistancePrice",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteDistancePrice(DistancePrice distancePrice){
		PageMessage pm = new PageMessage();
		int flag = distancePriceService.deleteDistancePrice(distancePrice);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
}
