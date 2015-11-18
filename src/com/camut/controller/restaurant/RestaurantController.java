package com.camut.controller.restaurant;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Restaurants;
import com.camut.model.RestaurantsUser;
import com.camut.model.ViewArea;
import com.camut.pageModel.PageAdminUser;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageRestaurantUser;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MailUtil;
import com.camut.utils.StringUtil;

@Controller("RestaurantController")
@RequestMapping("/restaurant")
public class RestaurantController extends BaseController {
	

	@Autowired
	private RestaurantsUserService restaurantsUserService;//自动注入RestaurantsUserService
	
	@Autowired
	private RestaurantsService restaurantsService;//自动注入RestaurantsService


	/**
	 * @Title: toLogin
	 * @Description: 跳转登陆页面； go to the restaurant user login page
	 * @param:    HttpServletRequest Model
	 * @return: restaurant user login url
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String toLogin(HttpServletRequest httpServletRequest,Model model){
		Cookie[] cookies  = httpServletRequest.getCookies();
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if("restaurantsUser".equals(cookie.getName())){
					String value = cookie.getValue();
					String[] values= value.split("==");
					model.addAttribute("code", values[0]);
					model.addAttribute("password", values[1]);
				}
			}
		}
		return "restaurant/login";
	}
	
	/**
	 * @Title: toMain
	 * @Description: 跳转商家后台; go to restaurant management page
	 * @param:    
	 * @return: restaurant main page url
	 */
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String toMain(HttpSession session){
		if(session.getAttribute("restaurantsUser")!=null){
			return "restaurant/main";
		}else{
			return "restaurant/login";
		}
		
		
	}
	
	/**
	 * @Title: toRegister
	 * @Description: 跳转注册页面; go to the restaurant registration page
	 * @param:    
	 * @return: restaurant registration page url
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String toRegister(){
		return "restaurant/register";
	}
	
	/**
	 * @Title: toRegisterSuccess
	 * @Description: 注册成功之后跳转页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="toRegisterSuccess",method=RequestMethod.GET)
	public String toRegisterSuccess(){
		return "restaurant/registersuccess";
	}
	
	/**
	 * @Title: toStaffmanagement
	 * @Description: 跳转员工管理页面
	 * @param:    
	 * @return: String
	 */
	@RequestMapping(value="/staffmanagement",method=RequestMethod.GET)
	public String toStaffmanagement(){
		return "restaurant/staffmanagement";
	}
	
	/**
	 * @Title: checkLoginName
	 * @Description: 验证管理员用户名是否唯一,注册时用的; check if the restaurant user name exists
	 * @param:  code:  restaurant user name
	 * @return: PageMessage: the message will display to the user
	 */
	@RequestMapping(value="/checkLoginName",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage checkLoginName(RestaurantsUser restaurantsUser){
		PageMessage pm = new PageMessage();
		int flag = restaurantsUserService.checkLoginNameUnique(restaurantsUser);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.LOGINNAME_EXISTS);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: checkLoginName
	 * @Description: 验证商家名称是否唯一
	 * @param:    String
	 * @return: PageMessage
	 */
	@RequestMapping(value="/checkRestaurantName",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage checkRestaurantName(Restaurants restaurants){
		PageMessage pm = new PageMessage();
		int flag = restaurantsService.checkRestaurantsNameUnique(restaurants);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.RESTAURANTSNAMR_EXISTS);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: register
	 * @Description: 商家与商家员工注册;Restaurants User registration
	 * @param:    Restaurants, RestaurantsUser
	 * @return: PageMessage: the message will display to the user
	 */
	@RequestMapping(value="restaurantRegister",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage register(Restaurants restaurants,RestaurantsUser restaurantsUser,Integer area){
		restaurants.setRestaurantLat((double)51);
		restaurants.setRestaurantLng((double)-114);
		PageMessage pm = new PageMessage();
		if (area!=null) {//选择的区域不为空
			ViewArea viewArea = new ViewArea();
			viewArea.setAreaId(area);
			restaurants.setViewArea(viewArea);
		}
		String uuid = restaurantsService.addRestaurants(restaurants);
		if(StringUtil.isNotEmpty(uuid)){
			Restaurants restaurants2 = new Restaurants();
			restaurants2.setUuid(uuid);
			restaurantsUser.setRestaurants(restaurants2);
			restaurantsUser.setType(1);
			restaurantsUser.setRole(7);//所有权限
			restaurantsUser.setStatus(1);//待审核状态
			int flag = restaurantsUserService.addRestaurantsUser(restaurantsUser);
			if(flag==-1){
				pm.setErrorMsg(MessageConstant.REGISTER_FAILED);
				pm.setSuccess(false);
			}
		}
		return pm;
	}
	
	/**
	 * @Title: login
	 * @Description: 商家员工登陆; Restaurants User login
	 *               success:/restaurant/main.jsp, failed：/restaurant/login。jsp
	 * @param:    RestaurantsUser
	 * @return: url
	 */
	@RequestMapping(value="restaurantLogin",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage login(RestaurantsUser restaurantsUser,HttpSession httpSession,Model model,
			String remember_me,HttpServletResponse httpServletResponse){
		PageMessage pm = new PageMessage();
		int flag = restaurantsUserService.restaurantsUserLogin(restaurantsUser);
		if(flag==1){//登陆成功; success
			httpSession.setAttribute("restaurantsUser", restaurantsUserService.getRestaurantsByLoginName(restaurantsUser.getCode()));
			if(remember_me!=null){
				Cookie cookie = new Cookie("restaurantsUser", restaurantsUser.getCode()+"=="+restaurantsUser.getPassword());
				cookie.setMaxAge(60*60*24*7);//cookie保存一周; keep the cookie for 1 week
				httpServletResponse.addCookie(cookie);
			}
		}
		else if(flag==0){//密码错误; wrong password
			pm.setErrorMsg(MessageConstant.PASSWORD_ERROR);
			pm.setSuccess(false);
			
		}
		else if(flag==-1){//用户名不存在; no such login name
			pm.setErrorMsg(MessageConstant.LOGINNAME_ERROR);
			pm.setSuccess(false);
		}
		else if (flag==2) {//待审核状态
			pm.setErrorMsg(MessageConstant.AUDIT_ERROR);
			pm.setSuccess(false);
		}
		else if (flag==-2) {//普通员工不能登录
			pm.setErrorMsg(MessageConstant.NOTADMINLOGIN);
			pm.setSuccess(false);
		}
		else if(flag==3){//商家在本平台已经不存在了
			pm.setErrorMsg(MessageConstant.RESTAURANTNOTEXIST);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: dataTable
	 * @Description: get a list of restaurant users. 
	 *               we will display this list in restaurant management page
	 *  
	 * @param:    HttpSession httpSession
	 * @return: List<PageRestaurantUser>
	 */
	@RequestMapping(value="getAllRestaurantUser",method=RequestMethod.GET)
	@ResponseBody
	public List<PageRestaurantUser> getAllRestaurantUser(HttpServletRequest request){
			Restaurants restaurants = this.getRestaurants(request.getSession(),request);
			return restaurantsUserService.getAllRestaurantUser(restaurants);
	}
	/**
	 * @Title: addRestaurantUser
	 * @Description: 商家增加员工
	 * @param:    RestaurantsUser HttpSession
	 * @return: PageMessage
	 */
	@RequestMapping(value="addRestaurantUser",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addRestaurantUser(RestaurantsUser restaurantsUser,HttpServletRequest request,
			Integer role1,Integer role2,Integer role3){
		role1 = role1==null?0:role1;
		role2 = role2==null?0:role2;
		role3 = role3==null?0:role3;
		PageMessage pm = new PageMessage();
			//Restaurants restaurants = ((RestaurantsUser)httpSession.getAttribute("restaurantsUser")).getRestaurants();
			Restaurants restaurants = this.getRestaurants(request.getSession(), request);
			if(restaurants!=null){
				restaurantsUser.setRestaurants(restaurants);
				if(restaurantsUser.getType()==1){
					restaurantsUser.setRole(7);//管理员最高权限
				}
				else{
					restaurantsUser.setRole(role1|role2|role3);//二进制权限
				}
				restaurantsUser.setStatus(0);
				int flag = restaurantsUserService.addRestaurantsUser(restaurantsUser);
				if(flag!=1){
					pm.setErrorMsg(MessageConstant.ADD_FAILED);
					pm.setSuccess(false);
				}
			}
		return pm;
	}
	
	/**
	 * @Title: checkCodeForEmployee
	 * @Description: 验证普通员工的工号在一个店内是否唯一
	 * @param:    
	 * @return: PageMessage
	 */
	@RequestMapping(value="checkCodeForEmployee",method= RequestMethod.POST)
	@ResponseBody
	public PageMessage checkCodeForEmployee(HttpServletRequest request,RestaurantsUser restaurantsUser){
		PageMessage pm = new PageMessage();
			int flag = -1;
			Restaurants restaurants = this.getRestaurants(request.getSession(), request);
			flag = restaurantsUserService.checkLoginNameForEmployee(restaurantsUser, restaurants);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.LOGINNAME_EXISTS);
			pm.setSuccess(false);
		}
		return pm;
	}
	
	/**
	 * @Title: updateRestaurantUser
	 * @Description:修改餐厅员工
	 * @param:    RestaurantsUser HttpSession Integer
	 * @return: PageMessage
	 */
	@RequestMapping(value="updateRestaurantUser",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage updateRestaurantUser(RestaurantsUser restaurantsUser,HttpServletRequest request,
			Integer role1,Integer role2,Integer role3){
		PageMessage pm = new PageMessage();
		role1 = role1==null?0:role1;
		role2 = role2==null?0:role2;
		role3 = role3==null?0:role3;
			RestaurantsUser restaurantsUser2 = this.getRestaurantsUser(request.getSession(), request);
			PageAdminUser operator = null;
			operator = (PageAdminUser)request.getSession().getAttribute("adminUserLoginname");
			if(restaurantsUser2!=null){
				restaurantsUser.setModby(restaurantsUser2.getFirstName()+" "+restaurantsUser2.getLastName());
				if(restaurantsUser.getType()==1){//管理员，最高权限
					restaurantsUser.setRole(7);
				}
				else{
					restaurantsUser.setRole(role1|role2|role3);//二进制权限
				}
				int flag = restaurantsUserService.updateRestaurantsUser(restaurantsUser);
				if(restaurantsUser.getType()==1 && restaurantsUser.getId()==restaurantsUser2.getId()){//如果是当前管理员，修改session
					request.getSession().setAttribute("restaurantsUser", restaurantsUserService.getRestaurantsByLoginName(restaurantsUser.getCode()));
				}
				if(flag!=1){
					pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
					pm.setSuccess(false);
				}
			}else if(operator!=null){
				restaurantsUser.setModby(operator.getFirstName()+" "+operator.getLastName());
				if(restaurantsUser.getType()==1){//管理员，最高权限
					restaurantsUser.setRole(7);
				}
				else{
					restaurantsUser.setRole(role1|role2|role3);//二进制权限
				}
				int flag = restaurantsUserService.updateRestaurantsUser(restaurantsUser);
				if(flag!=1){
					pm.setErrorMsg(MessageConstant.UPDATE_FAILED);
					pm.setSuccess(false);
				}
				
			}else if(operator==null){
				pm.setErrorMsg(MessageConstant.LOGIN_TIME_OUT);
				pm.setSuccess(false);
			}
		return pm;
	}
	/**
	 * @Title: deleteEmployee
	 * @Description: 删除餐厅员工
	 * @param:    long
	 * @return: PageMessage
	 */
	@RequestMapping(value="deleteEmployee",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteEmployee(String restaurantUserUuid){
		PageMessage pm = new PageMessage();
		int flag = restaurantsUserService.deleteRestaurantsUser(restaurantUserUuid);
		if(flag==-1){
			pm.setErrorMsg(MessageConstant.DELETE_FAILED);
			pm.setSuccess(false);
		}
		return pm;
	}
	/**
	 * @Title: resetPasswordPage
	 * @Description: 打开输入取回密码的验证码输入页面
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="resetPassword",method=RequestMethod.GET)
	public String resetPasswordPage(String email){
		return "restaurant/resetPassword";
	}
	
	/**
	 * @Description: 发的送验证码到指定的电子邮箱 ; send verify code to the email address
	 * @param @param emailAddress
	 * @param @param session
	 * @return String  : message
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value="/sendemail",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage sendEmail(String emailAddress,HttpSession session){
		//int temp = 0;
		PageMessage pm = new PageMessage();
		
		 RestaurantsUser restaurantsUser = restaurantsUserService.getRestaurantsByLoginName(emailAddress);
		if(restaurantsUser == null){

			pm.setErrorMsg(MessageConstant.LOGINNAME_ERROR);//no such login name(email)
			pm.setSuccess(false);
			return pm;
		}else{
			String verificationCode = (int)(Math.random()*900000+100000)+"";//生成6位验证码; generate 6 digits code
			//System.out.println(verificationCode);
			session.setAttribute("verificationCode", verificationCode);
			session.setAttribute("loginname",restaurantsUser.getCode());
			String title = "Verification code by Nomme";
			String content = "To reset you own "+emailAddress+" password , please enter in this verification code: <span style='color:#064977'>" 
					+ verificationCode + "</span> to the retrieve password input box, and then click the button to reset the password."
					+ "<br> Note: please use the verification code within 30 minutes.";
			MailUtil.sendMail(title, content, emailAddress);
			//String user = ((Admin)session.getAttribute("adminUser")).getLoginname();
			Log4jUtil.info("管理员", "测试发送邮件");
			pm.setSuccess(true);
			return pm;
		}
	}
	
	/**
	 * @Description: 实现用户密码重置; reset password
	 * @param @param newPassword
	 * @param @param session
	 * @return String: message  
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/doresetpassword", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage doResetPassword (String newPassword, HttpSession session){
		String loginname = (String) session.getAttribute("loginname");
		return restaurantsUserService.resetPassword(loginname,newPassword);
	}
	
	/**
	 * @Title: signOut
	 * @Description: 登出
	 * @param:    HttpSession
	 * @return: String
	 */
	@RequestMapping(value="signOut",method=RequestMethod.GET)
	public String signOut(HttpSession session){
		session.removeAttribute("restaurantsUser");//删除session
		return "restaurant/login";
	}
	
/*	@RequestMapping(value="getRestaurantById",method = RequestMethod.POST)
	@ResponseBody
	public Restaurants getRestaurantById(String restaurantId){
		if(StringUtil.isNotEmpty(restaurantId)){
			//long id = Long.parseLong(restaurantId);
		}
		return null;
	}
	*/
}
