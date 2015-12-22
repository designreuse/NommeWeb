package com.camut.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.camut.model.CartHeader;
import com.camut.model.ViewRestaurant;
import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.framework.constant.MessageConstant.PASSWORD_VALIDATION;
import com.camut.model.Consumers;
import com.camut.model.Restaurants;
import com.camut.model.api.ConsumersAddressApiModel;
import com.camut.model.api.ViewConsumerClassifitionApiModel;
import com.camut.pageModel.PageClassification;
import com.camut.pageModel.PageCreateConsumerAccount;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageOpenTime;
import com.camut.pageModel.PageOpentimeClassify;
import com.camut.pageModel.PageRestaurant;
import com.camut.service.CartHeaderService;
import com.camut.service.ClassificationService;
import com.camut.service.ConsumersAddressService;
import com.camut.service.ConsumersService;
import com.camut.service.OpenTimeService;
import com.camut.service.OrderService;
import com.camut.service.RestaurantsService;
import com.camut.service.ViewRestaurantService;
import com.camut.utils.GoogleTimezoneAPIUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MD5Util;
import com.camut.utils.MailUtil;
import com.camut.utils.StringUtil;
import com.camut.utils.ValidationUtil;


@Controller("IndexController")
@RequestMapping("/index")
public class IndexController {
	
	@Autowired private ConsumersService consumersService;
	@Autowired private ClassificationService classificationService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private ViewRestaurantService viewRestaurantService;
	@Autowired private ConsumersAddressService consumersAddressService;
	@Autowired private OpenTimeService openTimeService;
	@Autowired private CartHeaderService cartHeaderService;
	@Autowired private OrderService orderService;
	
	//private static ViewRestaurant restaurant = new ViewRestaurant();
	
	/**
	 * @Description: 打开首页; go to index page
	 * @param    
	 * @return String  url
	 */
	@RequestMapping(value = "/index")
	public String indexPage(Model model,HttpSession session){
		String consumerUuid = "";
		if(session.getAttribute("consumer")!=null ){
			consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
		}
		List<ViewConsumerClassifitionApiModel> list = consumersService.getShortcutMenu(consumerUuid,3);
		if(list!=null){
			model.addAttribute("foodClassification", list);
		}
		return "home/index";
	}
	
	/**
	 * @Description: 打开支付页面; go to payment page
	 * @param    
	 * @return String  url
	 */
	@RequestMapping(value = "/payment")
	public String paymentPage(){
		return "home/payment";
	}

	/**
	 * @Description: 打开商家详情介绍页面;
	 * @param    
	 * @return String  url
	 */
	@RequestMapping(value = "/restaurantabout")
	public String restaurantAboutPage(String restaurantId, String distance, Model model){
		return "home/restaurantsAbout";
	}
	
	/**
	 * @Description: 打开商家详情菜单页面;
	 * @param    
	 * @return String  url
	 */
	@RequestMapping(value = "/restaurantmenu")
	public String restaurantMenuPage(String restaurantUuid, Model model,HttpSession session){
		//System.out.println(session.getId());
		if(StringUtil.isNotEmpty(restaurantUuid)){
			PageRestaurant pr = restaurantsService.getPageRestaurantByUuid(restaurantUuid);
			if(pr == null){
				return "redirect:../index/index";
			}
			List<PageOpenTime> opentimeList = openTimeService.getAllOpenTime(restaurantUuid);
			PageOpentimeClassify opentimeClassify = new PageOpentimeClassify();
				List<PageOpenTime> diliveryOpentime = new ArrayList<PageOpenTime>(); 
				List<PageOpenTime> pickupOpentime = new ArrayList<PageOpenTime>(); 
				List<PageOpenTime> reservationOpentime = new ArrayList<PageOpenTime>(); 
			if(opentimeList .size()>0){
				for (PageOpenTime pageOpentime : opentimeList) {
					if(pageOpentime.getType()==1){
						diliveryOpentime.add(pageOpentime);
					}else if(pageOpentime.getType()==2){
						pickupOpentime.add(pageOpentime);
					}else if(pageOpentime.getType()==3){
						reservationOpentime.add(pageOpentime);
					}
				}
				opentimeClassify.setDeliveryOpentimeList(diliveryOpentime);
				opentimeClassify.setPickupOpentimeList(pickupOpentime);
				opentimeClassify.setReservationOpentimeList(reservationOpentime);
				model.addAttribute("opentimeClassify",opentimeClassify);
			}
			Object consumer = session.getAttribute("consumer");
			if(consumer!=null){//如果登陆了，获取用户的购车的类型
				String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();  
				CartHeader ch = cartHeaderService.getWebCartHeaderByConsumerUuid(consumerUuid);
				if(ch!=null){
					String cartHeaderType = ch.getOrderType()+"";
					model.addAttribute("cartHeaderType", cartHeaderType);
				}else{
					model.addAttribute("cartHeaderType", "2");
				}
			}else{
				model.addAttribute("cartHeaderType", "2");
			}
			model.addAttribute("restaurant", pr);
			return "home/restaurantsMenu";
		}else{
			return "home/searchList";
		}
	}
	
	/**
	 * @Description: 打开搜索出来的商家列表页面;
	 * @param 
	 * @return String  url
	 */
	@RequestMapping(value = "/searchlist")
	public String searchListPage(ViewRestaurant viewRestaurant, Model model) {
		ViewRestaurant restaurant = new ViewRestaurant();
		if (viewRestaurant.getRestaurantLat() != null && viewRestaurant.getRestaurantLng() != null) {
			restaurant.setRestaurantLat(viewRestaurant.getRestaurantLat());
			restaurant.setRestaurantLng(viewRestaurant.getRestaurantLng());
			model.addAttribute("restaurantLng", restaurant.getRestaurantLng());
			model.addAttribute("restaurantLat", restaurant.getRestaurantLat());
			Date currentLocalTime = GoogleTimezoneAPIUtil.getLocalDateTime(viewRestaurant.getRestaurantLat(),
					viewRestaurant.getRestaurantLng());
			model.addAttribute("date", new SimpleDateFormat("HH:mm").format(currentLocalTime));
			List<PageClassification> list = classificationService.getAllClassification();
			model.addAttribute("classifications", list);
			model.addAttribute("classification", viewRestaurant.getClassificationName());
			model.addAttribute("userLatLng", "true");
			return "home/searchList";
		} else {
			model.addAttribute("userLatLng", "false");
			// return "redirect:home/index";
			return "redirect:" + "../index/index";
		}
	}
	
	/**
	 * @Description: 打开用户信息设置页面;
	 * @param    
	 * @return String  url
	 */
	@RequestMapping(value = "/user")
	public String userPage(String flag, Model model,HttpSession session){
		Consumers consumers = (Consumers)session.getAttribute("consumer");
		if(consumers!=null && StringUtil.isNotEmpty(consumers.getUuid())){//必须是用户已登录
			//long consumerId = seesion.get
			//Consumers consumers2 = consumersService.getConsumersById(consumers.getId());//Long.parseLong(
			model.addAttribute("consumer", consumers);
			double donatedMoney = orderService.getCharityAmount(consumers.getUuid());
			model.addAttribute("donatedMoney",Math.floor(donatedMoney*100+0.5)/100.0);
			if(StringUtil.isNotEmpty(flag)){
				model.addAttribute("flag",flag);
			}
			return "home/user";
		}else{
			model.addAttribute("consumer", null);
			return "home/index";
		}
	}
	
	/**
	 * @Description: 打开确认个人信息页面; 
	 * @param    
	 * @return String  url
	 */
	@RequestMapping(value = "/regist",method=RequestMethod.GET)
	public String registPage(HttpSession session){
		String consumerUuid =((Consumers)session.getAttribute("consumer")).getUuid();
		String restaurantUuid = "";
		CartHeader cartHeader = cartHeaderService.getWebCartHeaderByConsumerUuid(consumerUuid);
		if(cartHeader!=null){
			session.setAttribute("checkOutOrderType", cartHeader.getOrderType());
		}
		List<ConsumersAddressApiModel> ConsumersAddressList = consumersAddressService.getConsumersAddressById(consumerUuid, restaurantUuid);
		
		if(ConsumersAddressList.size()>0){
			for (ConsumersAddressApiModel consumersAddressApiModel : ConsumersAddressList) {
				if(consumersAddressApiModel.getIsDefault()==1){
					session.setAttribute("defaultAddress", consumersAddressApiModel);
					break;
				}
			}
		}
		return "home/regist";
	}
	
	/**
	 * @Title: login
	 * @Description: consumer的登录方法
	 * @param: Consumers consumers
	 * @param: HttpSession session
	 * @return PageMessage  
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage login(Consumers consumers,String autoLogin,HttpSession session,HttpServletResponse response){
		PageMessage pm = new PageMessage();
		
		PASSWORD_VALIDATION validationResult = ValidationUtil.validatePassword(consumers.getPassword());
		if(validationResult != PASSWORD_VALIDATION.VALID){
			pm.setSuccess(false);
			pm.setFlag(GlobalConstant.PASSWORD_ERROR);//0
			pm.setErrorMsg(validationResult.getMessage());
			return pm;
		}
		
		int temp = consumersService.consumersLogin(consumers);
		//-1表示用户名不存在，0表示密码错误，1代表登录成功 
		if (temp>0){
			if(autoLogin.trim().equals("true")){
				//int saveTime=Integer.parseInt(savetime);//这里接受的表单值为天来计算的  
                int seconds=365*24*60*60;  
                Cookie cookie = new Cookie("user", consumers.getEmail().trim()+"=="+MD5Util.md5(consumers.getPassword().trim()));  
                cookie.setMaxAge(seconds);                     
                response.addCookie(cookie);  
			}
			Consumers consumers2 = consumersService.getConsumersByLoginName(consumers.getEmail());
			session.setAttribute("consumer", consumers2);
			pm.setSuccess(true);
			pm.setFlag(GlobalConstant.LOGIN_OK);//1
		}else if(temp<0){
			pm.setSuccess(false);
			pm.setFlag(GlobalConstant.LOGINNAME_ERROR);//-1
			pm.setErrorMsg(MessageConstant.LOGINNAME_ERROR);
		}else if(temp==0){
			pm.setSuccess(false);
			pm.setFlag(GlobalConstant.PASSWORD_ERROR);//0
			pm.setErrorMsg(MessageConstant.PASSWORD_ERROR);
		}
		return pm;
	}
	
	/**
	 * @Title: signOut
	 * @Description: 注销
	 * @param: HttpSession session
	 * @return String  
	 */
	@RequestMapping(value = "/signOut",method = RequestMethod.GET)
	public String signOut(HttpSession session,HttpServletResponse response){
		Cookie cookie = new Cookie("user","no");//这边得用"",不能用null  
        //cookie.setPath("/");//设置成跟写入cookies一样的  
       // cookie.setDomain(".wangwz.com");//设置成跟写入cookies一样的  
        cookie.setMaxAge(0);  
        response.addCookie(cookie);  
		session.removeAttribute("consumer");
		return "home/index";
	}
	
	
	@RequestMapping(value = "/emailExist",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage emailExist(String email){
		PageMessage pm = new PageMessage();
		int temp = consumersService.getConsumersByEmail(email);
		//返回 1：已被使用的邮箱地址 -1：未被使用的邮箱地址
		if(temp<0){
			
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.EMAIL_USED);
		}
		return pm;
	}
	
	//createAccount
	@RequestMapping(value = "/createAccount",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage createAccount(PageCreateConsumerAccount pageConsumerAccount){
		PageMessage pm = new PageMessage();
		
		PASSWORD_VALIDATION validationResult = ValidationUtil.validatePassword(pageConsumerAccount.getPassword1());
		if(validationResult != PASSWORD_VALIDATION.VALID){
			pm.setSuccess(false);
			pm.setFlag(GlobalConstant.PASSWORD_ERROR);
			pm.setErrorMsg(validationResult.getMessage());
			System.out.println("error:"+ validationResult.getMessage());
			return pm;
		}
		
		Consumers consumers = new Consumers();
		consumers.setFirstName(pageConsumerAccount.getFirstName());
		consumers.setLastName(pageConsumerAccount.getLastName());
		consumers.setEmail(pageConsumerAccount.getEmail());
		consumers.setPhone(pageConsumerAccount.getPhone());
		consumers.setStatus(0);
		consumers.setRegDate(new Date());
		consumers.setPassword(MD5Util.md5(pageConsumerAccount.getPassword1()));
		consumers.setUuid(StringUtil.getUUID());
				
		int temp  = consumersService.addConsumerForNomme(consumers);
		//-1增加失败，1增加成功
		if(temp>0){
			pm.setSuccess(true);
			MailUtil.sendRegistrationEmail(consumers.getFirstName(), consumers.getEmail());
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.CREATE_ACCOUNT_ERROR);
		}
		return pm;
	}
	
	
	//sendResetPasswordVerificationCode
	@RequestMapping(value = "/sendResetPasswordVerificationCode",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage sendResetPasswordVerificationCode(String email,HttpSession session){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(email)){
			int temp = consumersService.getConsumersByEmail(email.trim());
			//返回 1：已被使用的邮箱地址 -1：未被使用的邮箱地址
			if(temp>0){
				String verificationCode = (int)(Math.random()*900000+100000)+"";//生成6位验证码; generate 6 digits code
				session.setAttribute("verificationCode", verificationCode);
				session.setAttribute("resetPasswordEmail", email.trim());
				//session.setAttribute("loginname",admin.getLoginname());
				String title = "Verification code from Nomme";
				String content = "To reset the password of your Nomme account " + email + ", please enter the verification code "
						+ "<span style='color:#064977'>" + verificationCode + "</span> "
						+ "in the input box to retrieve your password, and then click the button to reset the password."
						+ "<br>"
						+ "Please use the verification code within 30 minutes.";
				MailUtil.sendMail(title, content, email.trim());
				//String user = ((Admin)session.getAttribute("adminUser")).getLoginname();
				Log4jUtil.info("Consumer", "测试发送邮件");
				pm.setSuccess(true);
			}else{
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.ACCOUNT_NOT_EXIST);
			}
			
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.ALL_FAILED);
		}
		return pm;
	}
	
	@RequestMapping(value = "/checkVerificationCode",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage checkVerificationCode (String VerificationCode, HttpSession session){
		PageMessage pm = new PageMessage();
		String serverSideCode = (String) session.getAttribute("verificationCode");
		if(StringUtil.isNotEmpty(serverSideCode) && StringUtil.isNotEmpty(VerificationCode)){
			if(VerificationCode.equals(serverSideCode)){
				pm.setSuccess(true);
			}else{
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.WRONG_CODE);
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg("Server-side error please click \"Send Email\" again !");
		}
		return pm;
	}
	
	@RequestMapping(value = "/resetPassword",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage resetPassword (String newPassword, HttpSession session){
		PageMessage pm = new PageMessage();
		
		PASSWORD_VALIDATION validationResult = ValidationUtil.validatePassword(newPassword);
		if(validationResult != PASSWORD_VALIDATION.VALID){
			pm.setSuccess(false);
			pm.setFlag(GlobalConstant.PASSWORD_ERROR);
			pm.setErrorMsg(validationResult.getMessage());
			return pm;
		}
		
		if(StringUtil.isNotEmpty(newPassword)){
			if(session.getAttribute("resetPasswordEmail")!=null){
				String email = (String)session.getAttribute("resetPasswordEmail");
				int temp = consumersService.resetPassword(newPassword,email);
				//-1修改失败 1修改成功
				if(temp>0){
					pm.setSuccess(true);
				}else{
					pm.setSuccess(false);
					pm.setErrorMsg(MessageConstant.OPERATION_FAILED);
				}
			}else{
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.OPERATION_FAILED);
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.OPERATION_FAILED);
		}
		return pm;
	}
	
	
	
	/**
	 * @Title: signOut
	 * @Description: 平台声明描述页面
	 * @param: HttpSession session
	 * @return String  
	 */
	@RequestMapping(value = "/statement",method = RequestMethod.GET)
	public String statement(HttpSession session){
		return "home/statement";
	}
	
}
