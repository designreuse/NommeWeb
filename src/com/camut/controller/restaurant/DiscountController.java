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
import com.camut.model.Discount;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageDiscount;
import com.camut.pageModel.PageMessage;
import com.camut.service.DiscountService;

/**
 * @ClassName DiscountController.java
 * @author wangpin
 * @createtime Jun 29, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("DiscountController")
@RequestMapping("discount")
public class DiscountController extends BaseController {

	@Autowired
	private DiscountService discountService;
	
	/**
	 * @Title: toDiscount
	 * @Description: 返回优惠信息页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toDiscount",method=RequestMethod.GET)
	public String toDiscount(){
		return "restaurant/discount";
	}
	
	/**
	 * @Title: getAllDiscount
	 * @Description:获取商家所有的优惠信息
	 * @param:    HttpServletRequest
	 * @return: List<PageDiscount>
	 */
	@RequestMapping(value="getAllDiscount",method=RequestMethod.GET)
	@ResponseBody
	public List<PageDiscount> getAllDiscount(HttpServletRequest request){
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		return discountService.getAllDiscounts(restaurants);
	}
	
	/**
	 * @Title: addDiscount
	 * @Description: 增加优惠信息
	 * @param:    Discount HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="addDiscount",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addDiscount(Discount discount,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		discount.setRestaurants(restaurants);
		int flag = discountService.addDiscount(discount);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.ADD_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: addDiscount
	 * @Description: 修改优惠信息
	 * @param:    Discount HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateDiscount",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateDiscount(Discount discount,HttpServletRequest request){
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		discount.setRestaurants(restaurants);
		int flag = discountService.updateDiscount(discount);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: addDiscount
	 * @Description: 删除优惠信息
	 * @param:    Discount HttpServletRequest
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteDiscount",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteDiscount(Discount discount){
		PageMessage pm = new PageMessage();
		int flag = discountService.deleteDiscount(discount);
		if (flag==-1) {
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: getDiscountByDishId
	 * @Description: 根据菜品id查询优惠信息中有没有用到此菜品
	 * @param:   String 
	 * @return: int -1没用到 1用到
	 */
	@RequestMapping(value="getDiscountByDishId",method=RequestMethod.POST)
	@ResponseBody
	public int getDiscountByDishId(String dishId){
		return discountService.getDiscountByDishId(dishId);
	}
}
