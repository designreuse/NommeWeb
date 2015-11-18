package com.camut.controller.restaurant;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Restaurants;
import com.camut.pageModel.PageMessage;
import com.camut.service.RestaurantsService;
import com.camut.utils.CommonUtil;
import com.stripe.model.Account;

/**
 * @ClassName StripeController.java
 * @author wangpin
 * @createtime Aug 4, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("StripeController")
@RequestMapping("stripe")
public class StripeController extends BaseController {

	@Autowired
	private RestaurantsService restaurantsService;

	/**
	 * @Title: getStripeAccount
	 * @Description: TODO
	 * @param:
	 * @return: String
	 */
	@RequestMapping(value = "getStripeAccount", method = RequestMethod.GET)
	public String getStripeAccount() {
		return "restaurant/stripeaccount";
	}

	/**
	 * @Title: getAccountDetail
	 * @Description: TODO
	 * @param: HttpServletRequest
	 * @return: Account
	 */
	@RequestMapping(value = "getAccountDetail", method = RequestMethod.POST)
	@ResponseBody
	public Account getAccountDetail(HttpServletRequest request) {
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		if (restaurants.getStripeAccount() != null && !"".equals(restaurants.getStripeAccount())) {// 已经注册过托管账户
			String accountId = restaurants.getStripeAccount();
			Account account = CommonUtil.getAccountById(accountId);
			return account;
		}
		return null;
	}

	/**
	 * @Title: registerAccount
	 * @Description: TODO
	 * @param: HttpServletRequest request,String contex
	 * @return: PageMessage
	 */
	@RequestMapping(value = "registerAccount", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage registerAccount(HttpServletRequest request, String context) {
		PageMessage pm = new PageMessage();
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		// 获取当前时间以及客户端ip
		context = context.substring(0, context.indexOf("}"));
		context += ",\"date\":" + new Date().getTime() + "}";
		//Map<String, Object> contextMap = (Map<String, Object>) JSONUtils.parse(context);
		String accountId = CommonUtil.createManagedAccount();
		if (accountId != null) {// 创建托管账户成功
			// 更新当前托管账户
			PageMessage p1 = CommonUtil.updateManagedAccount(accountId, context);
			if (p1.getSuccess()) {// 更新成功
				// 将注册成功的托管账户id保存在餐厅数据库中
				restaurants.setStripeAccount(p1.getErrorMsg());
				int flag = restaurantsService.updateRestaurants(restaurants);
				if (flag == -1) {// 更新餐厅信息时出错
					pm.setErrorMsg(MessageConstant.STRIPE_ACCOUNT_REGISTER_FAIL);
					pm.setSuccess(false);
				}
				return pm;
			} else {
				return p1;
			}

		}
		pm.setErrorMsg(MessageConstant.STRIPE_ACCOUNT_REGISTER_FAIL);
		pm.setSuccess(false);
		return pm;
	}
	
	/**
	 * @Title: updateAccount
	 * @Description:修改餐厅的托管账户的资料
	 * @param:    HttpServletRequest request,String context
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateAccount",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateAccount(HttpServletRequest request,String context){
		PageMessage pm = new PageMessage();
		if("{}".equals(context)){
			return pm;
		}
		Restaurants restaurants = this.getRestaurants(request.getSession(), request);
		PageMessage p1 = CommonUtil.updateManagedAccount(restaurants.getStripeAccount(), context);
		if (p1.getSuccess()) {// 更新成功
			return pm;
		} else {
			return p1;
		}
	}
	
}
