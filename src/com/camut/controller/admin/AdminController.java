package com.camut.controller.admin;

import java.io.IOException;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.camut.framework.constant.GlobalConstant;
import com.camut.framework.constant.MessageConstant;
import com.camut.model.Admin;
import com.camut.model.Chain;
import com.camut.model.Charity;
import com.camut.model.Classification;
import com.camut.model.OrderCharity;
import com.camut.pageModel.PageAdminUser;
import com.camut.pageModel.PageAreas;
import com.camut.pageModel.PageCharity;
import com.camut.pageModel.PageClassification;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageRestaurantName;
import com.camut.pageModel.PageRestaurantAdmins;
import com.camut.service.AdminService;
import com.camut.service.AreasService;
import com.camut.service.ChainService;
import com.camut.service.CharityService;
import com.camut.service.ClassificationService;
import com.camut.service.OrderCharityService;
import com.camut.service.RestaurantsService;
import com.camut.service.RestaurantsUserService;
import com.camut.utils.ImageUtils;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MD5Util;
import com.camut.utils.MailUtil;
import com.camut.utils.StringUtil;

/**
 * ClassName: AdminController 
 * @Description: 管理员admin后台控制层； System admin controller
 * @author wj
 * @date 2015-6-3
 */

@Controller("AdminManageController")
@RequestMapping("/admin")
// 显示登录页面
public class AdminController {
	
	@Autowired private AdminService adminService;
	@Autowired private AreasService areasService;
	@Autowired private ChainService chainService;
	@Autowired private RestaurantsService restaurantsService;
	@Autowired private RestaurantsUserService restaurantsUserService;
	@Autowired private ClassificationService classificationService;
	@Autowired private CharityService charityService;
	@Autowired private OrderCharityService orderCharityService;
	
	/**
	 * @Description: 显示登陆页面； check if system admin's login name and password are 
	 * 							saved in cookies, then go to login page (url)
	 * @param @param httpServletRequest
	 * @return String  
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String toLogin(HttpServletRequest httpServletRequest,Model model) {
		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("adminUser".equals(cookie.getName())) {//获取request中的cookie的用户名和密码
					String value = cookie.getValue();
					String[] values = value.split("==");
					model.addAttribute("loginname", values[0]);
					model.addAttribute("password", values[1]);
				}
			}
		}
		return "admin/login";
	}
	
	/**
	 * @Description: 登录验证用户名和密码; verify system admin's login name and password. 
     *               login success->admin/main, login failed -> admin/login
	 * @param @param admin
	 * @param @param remember_me; if need to save the cookie for a week
	 * @param @param httpSession
	 * @param @param request
	 * @param @param response
	 * @return String: login success->
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/dologin", method = RequestMethod.POST)
	public String doLogin(Admin admin, String rememberMe,
			HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response) {
		// PageMessage pm = new PageMessage();
		int temp = GlobalConstant.LOGINNAME_ERROR;// 默认用户名不存在; login name doesn't exist
		String loginname = admin.getLoginname();
		String password = admin.getPassword();
		String url = null;
		if (StringUtil.isNotEmpty(loginname) && StringUtil.isNotEmpty(password)) {
			temp = adminService.adminLogin(loginname, password);
		}
		if (temp == GlobalConstant.LOGIN_OK) {// 如果登录成功; login success
			httpSession.setAttribute("adminUserLoginname",
					adminService.getAdminByLoginname(loginname));
			url = "redirect:/admin/main"; 
			if (rememberMe != null) {
				Cookie cookie = new Cookie("adminUser", loginname + "=="
						+ password);
				cookie.setMaxAge(60 * 60 * 24 * 7);// cookie保存一周//save the cookie for 1 week
				response.addCookie(cookie);
			}
		} else if (temp == GlobalConstant.PASSWORD_ERROR) {// 如果密码错误; wroing password
			request.setAttribute("errorMsg", MessageConstant.PASSWORD_ERROR);
			url = "/admin/login";

		}
		if (temp == GlobalConstant.LOGINNAME_ERROR) {// 如果用户名不存在; no such login name
			request.setAttribute("errorMsg", MessageConstant.LOGINNAME_ERROR);
			url = "/admin/login";
		}
		return url;
	}
	
	/**
	 * @Title: signOut
	 * @Description: 当前用户注销按钮
	 * @param: HttpSession session
	 * @return String  
	 */
	@RequestMapping(value="signout",method = RequestMethod.GET)
	public String signOut(HttpSession session){
		session.removeAttribute("adminUserLoginname");
		return "admin/login";
	}

	/**
	 * @Description: 跳转到admin的主页面; go to admin/main page
	 * @return String: url
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/main")
	public String main() {
		return "admin/main";
	}

	/**
	 * @Description: 打开管理员维护页面; go to admin management page
	 * @return String: url  
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/adminmanagepage")
	public String adminManagePage() {
		return "admin/adminManage";
	}

	/**
	 * @Description: 打开餐厅商家管理页面; go to list of restaurants page
	 * @param    
	 * @return String  url
	 * @author wj
	 * @date 2015-6-10
	 */
	@RequestMapping(value = "/restaurantauditpage")
	public String restaurantAuditPage(){
		return "admin/restaurantAudit";
	}

	/**
	 * @Description: 打开区域和税率维护页面 areaAndTaxPage; go to area and tax management page
	 * @return String: url  
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/areaandtaxpage")
	public String areaAndTaxPage(){
		return "admin/areaAndTax";
	}

	/**
	 * @Description: 打开菜系管理页面; go to cuisine management page
	 * @return String  : url
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/cuisinepage")
	public String cuisinepagePage() {
		return "admin/cuisine";
	}
	
	/**
	 * @Description: 打开连锁店管理页面; go to chain manage page
	 * @return String  : url
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value = "/chainmanagepage")
	public String chainmanagePage() {
		return "admin/chainManage";
	}
	
	/**
	 * @Description: 打开商家管理员维护页面; go to restaurant admin manage page
	 * @return String  : url
	 * @author wj
	 * @date 2015-6-16
	 */
	@RequestMapping(value = "/restaurantadminauditpage")
	public String restaurantAdminPage() {
		return "admin/restaurantUserAdmin";
	}
	
	/**
	 * @Description: 获取所有管理员用户; get all admin users
	 * @return List<Admin>  
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value="/getadminusers",method = RequestMethod.GET)
	@ResponseBody
	public List<PageAdminUser> getAdminUsers(){
		//modelMap.addAttribute("adminList", );
		//PageModel pm = adminService.getAllUsers(pageFilter);
		//System.out.println(JSONObject.toJSONString(pm.getRows().get(0)));
		List<PageAdminUser> adminList =  adminService.getAllAdminUsers();
		return adminList;
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
		
		PageAdminUser admin = adminService.getAdminByLoginname(emailAddress);
		if(admin == null){

			pm.setErrorMsg(MessageConstant.LOGINNAME_ERROR);//no such login name(email)
			pm.setSuccess(false);
			return pm;
		}else{
			String verificationCode = (int)(Math.random()*900000+100000)+"";//生成6位验证码; generate 6 digits code
			//System.out.println(verificationCode);
			session.setAttribute("verificationCode", verificationCode);
			session.setAttribute("loginname",admin.getLoginname());
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
	 * @Description: 跳转到输入验证码和新密码的页面; go to reset password page
	 * @param @param request
	 * @return String: url
	 * @author wj
	 * @date 2015-6-3
	 */
	@RequestMapping(value="/resetPage")
	public String resetPage(){
		return "admin/resetPassword";
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
		return adminService.resetPassword(loginname,newPassword);
	}
	
	/**
	 * @Title: addUser
	 * @Description:新增平台管理员 Add an admin user 
	 * @param: @param pageAdminUser
	 * @param: @param session
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="/adduser", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage addUser(PageAdminUser pageAdminUser,HttpSession session){
		PageMessage pm = new PageMessage();
		PageAdminUser operator = (PageAdminUser) session.getAttribute("adminUserLoginname");
		if(operator==null){
			pm.setSuccess(false);
			pm.setErrorMsg("Login timeout, please login again!");
			return pm;
		}
		//Admin operator = adminService.getAdminByLoginname(modby);
		//组装数据到新的adminUser
		Admin newAdmin = new Admin();
		newAdmin.setLoginname(pageAdminUser.getLoginname());
		newAdmin.setFirstName(pageAdminUser.getFirstName());
		newAdmin.setLastName(pageAdminUser.getLastName());
		newAdmin.setPassword(MD5Util.md5(pageAdminUser.getPassword()));
		//newAdmin.setSex(Integer.parseInt(pageAdminUser.getSex()));
		//newAdmin.setAge(Integer.parseInt(pageAdminUser.getAge()));
		newAdmin.setUsertype(1);
		newAdmin.setState(0);
		newAdmin.setModon(new Date());
		newAdmin.setModby(operator.getFirstName()+" "+operator.getLastName());
		int temp = adminService.addAdmin(newAdmin);
		if(temp>=0){
			pm.setSuccess(true);
			return pm;
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg("Save Error");
			return pm;
		}
		
	}
	
	/**
	 * @Title: deleteUser
	 * @Description: 通过用户id删除用户
	 * @param: @param id
	 * @return void  -1表示删除失败,1表示成功; -1: failed ，1：success
	 */
	@RequestMapping(value = "deleteuser", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteUser(String id, HttpSession session ){
		PageMessage pm = new PageMessage();
		PageAdminUser operator = (PageAdminUser) session.getAttribute("adminUserLoginname");
		if(operator==null){
			pm.setSuccess(false);
			return pm;
		}
		String operatorName = operator.getFirstName()+" "+operator.getLastName();
		pm = adminService.deleteAdmin(Long.parseLong(id),operatorName); //-1表示删除失败,1表示成功; -1: failed ，1：success
		return pm;
	}
	 
	/**
	 * @Description: 修改管理员信息
	 * @param @param pageAdminUser
	 * @param @param session
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-10
	 */
	@RequestMapping(value="/modifyuser", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage modifyUser(PageAdminUser pageAdminUser,HttpSession session){
		PageAdminUser operator = (PageAdminUser) session.getAttribute("adminUserLoginname");
		PageMessage pm = new PageMessage();
		if(operator==null){
			pm.setSuccess(false);
			pm.setErrorMsg("Login timeout, please login again!");
			return pm;
		}
		pageAdminUser.setModby(operator.getFirstName()+" "+operator.getLastName());
		pm = adminService.modifyAdmin(pageAdminUser);
		return pm;
	}
	/**
	 * @Description: 验证用户名是否存在 check if the loginname (email) exists
	 * @param @param loginname
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-5
	 */
	@RequestMapping(value="/loginnameexist",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage loginnameExists(PageAdminUser pageAdminUser){
		PageMessage pm = new PageMessage();
		int temp = adminService.loginnameExists(pageAdminUser.getLoginname());
		if(temp>0){//用户名已存在
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.LOGINNAME_EXISTS);
		}else{//用户名不存在
			pm.setSuccess(true);
		}
		return pm;
	}
	
	/**
	 * @Description: 判断用户名是否已经被其他id使用了; 
	 * 				check if the loginname (email) used by other amdins
	 * @param  pageAdminUser
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-8
	 */
	@RequestMapping(value="/loginnameisusedinotherid",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage loginnameIsUsedInOtherId(PageAdminUser pageAdminUser){
		PageMessage pm = new PageMessage();
		int temp = adminService.loginnameIsUsedInOtherId(pageAdminUser.getLoginname(), pageAdminUser.getId());
		if(temp>0){//用户名已存在
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.LOGINNAME_EXISTS);
		}else{//用户名不存在
			pm.setSuccess(true);
		}
		return pm;
	}
	
	/**
	 * @Description: 获取所有父区域用于加载select选项;  get all parent areas (country level)
	 * @param @return   
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-8
	 */
	@RequestMapping(value="getallparentarea",method=RequestMethod.GET)
	@ResponseBody
	public PageModel getAllParentArea (){
		PageModel pm = areasService.getAllParentAreas();
		return pm;
	}
	
	/**
	 * @Description: 加载区域数据到表格; get all areas (province level)
	 * @param  pageFilter
	 * @return List<PageAreas>  
	 * @author wj
	 * @date 2015-6-8
	 */
	@RequestMapping(value="getallareas",method=RequestMethod.GET)
	@ResponseBody
	public List<PageAreas> getAllAreas (PageFilter pageFilter){
		//modelMap.addAttribute("adminList", );
		List<PageAreas> areaList = areasService.getAllAreas();
		if(areaList!=null && areaList.size()>0){
			
			return areaList;
		}else{
			return null;
		}
		//System.out.println(JSONObject.toJSONString(pm.getRows().get(0)));
	}
	
	
	/**
	 * @Description: 增加区域税率; add rates for areas
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@RequestMapping(value="addAreas",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addAreas(PageAreas pageAreas){
		PageMessage pm = areasService.addAreas(pageAreas);
		return pm;
	}
	
	/**
	 * @Description: 增加区域税率时校验名称在同一个父类区域中是否被使用了
	 * 				check if the area and rate already existed
	 * @param  pageAreas
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@RequestMapping(value="areanameexist",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage areaNameExist(PageAreas pageAreas){
		PageMessage pm = areasService.areaNameExist(pageAreas);
		return pm;
	}
	/**
	 * @Description: 修改区域; modify areas
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-10
	 */
	@RequestMapping(value="modifyareas",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage modifyAreas(PageAreas pageAreas){
		PageMessage pm = areasService.modifyAreas(pageAreas);
		return pm;
	}
	
	/**
	 * @Description: 删除区域税率; delete an area and it's rate
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-10
	 */
	@RequestMapping(value="deletearea", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteArea(PageAreas pageAreas){
		PageMessage pm = areasService.deleteArea(pageAreas);
		return pm;
	}
	
	/**
	 * @Description: 获取所有连锁店到表格显示; get all chain restaurants names
	 * @return PageModel  
	 * @author wj
	 * @date 2015-6-11
	 */
	@RequestMapping(value="getallchain", method = RequestMethod.GET)
	@ResponseBody
	public List<Chain> getAllChain(){
		PageModel pm = new PageModel();
		List<Chain> chainList = chainService.getAllChain();
		pm.setRows(chainList);
		pm.setTotal((long)chainList.size());
		//System.out.println(JSONObject.toJSONString(pm.getRows().get(0)));
		return chainList;
	}
	
	/**
	 * @Description: 增加连锁店名称; add a chain name
	 * @param @param pageAreas
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@RequestMapping(value="addchain", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage addChain(Chain newChain){
		PageMessage pm = chainService.addChain(newChain);
		return pm;
	}
	
	/**
	 * @Description: 修改连锁店名称; modify a chain name
	 * @param @param pageAreas
	 * @param @return   
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@RequestMapping(value="modifychain", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage modifyChain(Chain newChain){
		PageMessage pm = chainService.modifyChain(newChain);
		return pm;
	}
	
	/**
	 * @Description: 增加修改连锁店名称时校验名称是否被使用了
	 * 				 check if a chain name exists
	 * @param pageAreas
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-9
	 */
	@RequestMapping(value="chainnameexist",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage chainNameExist(Chain chain){
		PageMessage pm = chainService.chainNameExist(chain);
		return pm;
	}
	
	/**
	 * @Title: deleteChain
	 * @Description: 连锁店名称
	 * @param: long id
	 * @return PageMessage  
	 *
	 */
	@RequestMapping(value="deletechain",method = RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteChain(long id){
		return chainService.deleteChain(id);
	}
	
	/**
	 * @Title: getAllRestaurants
	 * @Description: 获取商家信息到前台表格; get all restaurants
	 * @param: pf
	 * @return PageModel  
	 */
	@RequestMapping(value="getallrestaurants",method = RequestMethod.GET)
	@ResponseBody
	public PageModel getAllRestaurants(PageFilter pf){
		PageModel pm = restaurantsService.getAllRestaurants(pf);
		return pm;
	}
	
	/**
	 * @Title: getAllRestaurants
	 * @Description: 获取商家信息到前台表格用于select下拉框选择
	 * 				get all restaurants' names
	 * @return List<PageRestaurantSelect>  
	 */
	@RequestMapping(value="getallrestaurantsname",method = RequestMethod.GET)
	@ResponseBody
	public List<PageRestaurantName> getAllRestaurantsName(){
		return restaurantsService.getAllRestaurantsName();
	}
	
	/**
	 * @Title: auditRestaurant
	 * @Description: 审核商家; verify a restaurant
	 * @param: id
	 * @param: statu
	 * @return void  
	 */
	@RequestMapping(value="auditrestaurant", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage auditRestaurant(long id, int statu){
		PageMessage pm= new PageMessage(); 
		int temp = restaurantsService.auditRestaurant(id, statu);
		// -1修改失败, 1修改成功
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.OPERATION_FAILED);
		}
		return pm;
	}
	
	/**
	 * @Title: deleteRestaurant
	 * @Description: 删除餐厅商家
	 * @param: int id
	 * @return PageMessage  
	 */
	@RequestMapping(value="/deleterestaurant",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteRestaurant(String id){
		PageMessage pm = new PageMessage();
		int temp = 0;
		if(StringUtil.isNotEmpty(id)){
			temp = restaurantsService.deleteRestaurants(Long.parseLong(id));
		}
		if(temp>0){
			pm.setSuccess(true);
		}else{
			pm.setSuccess(false);
		}
		return pm;
	}
	
	
	/**
	 * @Title: getRestaurantsAdminByRestaurantId
	 * @Description: 通过商家id获取商家的所有员工和管理员  get a restaurant's admin by restaurant's id
	 * * @param: restaurantId
	 * @param: order
	 * @return List<PageRestaurantAdmins>  
	 */
	@RequestMapping(value="getrestaurantadminsbyrestaurantid", method = RequestMethod.GET)
	@ResponseBody
	public List<PageRestaurantAdmins> getRestaurantsAdminByRestaurantId(String restaurantId,String order){
		List<PageRestaurantAdmins> restaurantAdminsList = restaurantsUserService.getRestaurantsUsersByRestaurantId(restaurantId);
		//List<PageRestaurantsAdmin> admins = restaurantsService.getAllRestaurantsAdmin();
		return restaurantAdminsList;
	}
	
	/**
	 * @Title: auditRestaurantAdmin 
	 * @Description:审核、增删改商家的员工 verify a restaurant's admin
	 * @param: id
	 * @param: statu
	 * @return void  
	 */
	@RequestMapping(value = "/auditrestaurantadmin", method = RequestMethod.POST)
	@ResponseBody
	public PageMessage auditRestaurantAdmin(long id, int statu, HttpSession httpSession){
		PageAdminUser operator = (PageAdminUser) httpSession.getAttribute("adminUserLoginname");//session中获取当前系统的用户信息，用于设置审核商家的操作人
		PageMessage pm = new PageMessage();
		if(operator==null){//如果取不到session中的值，说明该用户登录超时了，提醒重新登录
			pm.setSuccess(false);
			pm.setErrorMsg("Login timeout, please login again!");
			return pm;
		}
		String operatorName = operator.getFirstName()+" "+operator.getLastName();
		int temp = restaurantsUserService.auditRestaurantAdmin(id,statu,operatorName);
		if(temp>0){
			pm.setSuccess(true);
			
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg("Error!");
		}
		return pm;
		
	}
	
	/**
	 * @Title: getAllClassification
	 * @Description:  获得所有菜系用于页面表格显示
	 * @param: @return
	 * @return List<PageRestaurantClassification>  
	 */
	@RequestMapping(value = "/getallclassification",method = RequestMethod.GET)
	@ResponseBody
	public List<PageClassification> getAllClassification (){
		List<PageClassification> list = classificationService.getAllClassification();
		return list;
	}
	
	
	/**
	 * @Description: 判断菜系名是否已经被使用了; 
	 * 				check if the cuisineName used by other amdins
	 * @param  pageAdminUser
	 * @return PageMessage  
	 * @author wj
	 * @date 2015-6-8
	 */
	@RequestMapping(value="/classificationnameexist",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage classificationNameExist(PageClassification pc){
		return classificationService.classificationNameIsUsed(pc);
	}
	
	/**
	 * @Title: addClassification
	 * @Description: 新增菜系名称
	 * @param: PageRestaurantClassification prc
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="/addclassification",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage addClassification(PageClassification pc){
		return classificationService.addClassification(pc);
	}

	/**
	 * @Title: editClassification
	 * @Description: 修改餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	@RequestMapping(value="/modifyclassification",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage editClassification(PageClassification pc){
		return classificationService.editClassification(pc);
	}
	
	/**
	 * @Title: deleteClassification
	 * @Description: 删除餐厅菜系分类
	 * @param: PageClassification pc
	 * @return PageMessage  
	 */
	@RequestMapping(value="/deleteclassification",method=RequestMethod.POST)
	@ResponseBody
	public PageMessage deleteClassification(PageClassification pc){
		return classificationService.deleteClassification(pc);
	}
	/**
	 * 
	 * @Title: fileinput
	 * @Description: 
	 * @param: @return
	 * @return String  
	 *
	 */
	@RequestMapping(value = "/fileinput")
	public String fileinput() {
		return "admin/fileInputTest";
	}
	
	/**
	 * @Title: saveImageWithOrginalImageName
	 * @Description: 上传菜系分类的图片
	 * @param: @param request
	 * @param: @return
	 * @param: @throws IOException
	 * @return PageMessage  
	 */
	@RequestMapping(value = "/uploadImage")
	@ResponseBody
	public PageMessage saveImageWithOrginalImageName(HttpServletRequest request) throws IOException {
		PageMessage pm = new PageMessage();
		String imageType = request.getParameter("imageType");
		String classificationId = request.getParameter("classificationId");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		MultipartFile file = multipartRequest.getFile("classification-image");
		String path = "upload/classificationImages";
		String tempDir = "";
		String classificationname = "";
		if(StringUtil.isNotEmpty(imageType) && StringUtil.isNotEmpty(classificationId) && file!=null){
			Classification classification = classificationService.getClassificationById(Long.parseLong(classificationId));
			if(classification!=null){
				classificationname = classification.getClassificationName();
			}
			else{
				pm.setSuccess(false);
				return pm;
			}
			String orgFilename = file.getOriginalFilename();
			String orgFilnameType = orgFilename.substring(orgFilename.indexOf("."));
			orgFilename = classificationname+orgFilnameType;
			orgFilename = orgFilename.replace(" ", "");
			
			if(imageType.equals("1-1")){
				tempDir="/iosNomal";
			}else if(imageType.equals("1-2")){
				tempDir="/iosHover";
			}else if(imageType.equals("2-1")){
				tempDir="/androidNomal";
			}else if(imageType.equals("2-2")){
				tempDir="/androidHover";
			}else if(imageType.equals("3-1")){
				tempDir="/webNomal";
			}
			String imageUrl = "";
			try{
				imageUrl = ImageUtils.saveImageWithOrginalImageName(file, request, path, tempDir,orgFilename, true);
			} catch (Exception e) {
				pm.setSuccess(false);
				return pm;
			}
			String path2 = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path2+"/";
			//String imageFullPath = basePath+imageUrl;//服务器域名的全路径
			String imageFullPath = "/"+imageUrl;//只有图片存储位置的路径
			if(imageType.equals("1-1")){
				classification.setIosImageUrl(imageFullPath);
			}else if(imageType.equals("1-2")){
				classification.setIosHoverImageUrl(imageFullPath);
			}else if(imageType.equals("2-1")){
				classification.setAndroidImageUrl(imageFullPath);
			}else if(imageType.equals("2-2")){
				classification.setAndroidHoverImageUrl(imageFullPath);
			}else if(imageType.equals("3-1")){
				classification.setWebImageUrl(imageFullPath);
			}
			int temp2 = classificationService.updateClassification(classification);
			if(temp2>0){
				pm.setErrorMsg(basePath+imageUrl);
				pm.setSuccess(true);
				return pm;
			}else{
				pm.setSuccess(false);
				return pm;
			}
			
		}else{
			pm.setSuccess(false);
			return pm;
		}
	
	}
	
	/**
	 * @Title: getCharityPage
	 * @Description: 打开慈善机构管理页面
	 * @param: @return
	 * @return String  
	 */
	@RequestMapping(value="charityPage")
	public String getCharityPage(){
		return "admin/charityManage";
	}
	
	/**
	 * @Title: getCharityList
	 * @Description: 获得慈善机构列表
	 * @param: @param pf
	 * @param: @return
	 * @return PageModel  
	 */
	@RequestMapping(value="getCharityList")
	@ResponseBody
	public PageModel getCharityList(PageFilter pf){
		PageModel pm = new PageModel();
		List<PageCharity> list = charityService.getAllPageCharity(pf);
		pm.setRows(list);
		pm.setTotal(charityService.getCount());
		return pm;
	}
	
	
	/**
	 * @Title: addCharity
	 * @Description: 新增慈善机构 
	 * @param: @param pc
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="addCharity")
	@ResponseBody
	public PageMessage addCharity(PageCharity pc){
		PageMessage pm = new PageMessage();
		if(StringUtil.isNotEmpty(pc.getCharityName())){
			Charity charity = charityService.getCharityByCharityName(pc.getCharityName());
			if(charity!=null){
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.CHARITYNAME_USED);
			}else{
				long flag = charityService.addCharity(pc);
				if(flag>0){
					pm.setSuccess(true);
				}else{
					pm.setSuccess(false);
					pm.setErrorMsg(MessageConstant.ALL_FAILED);
				}
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.ALL_FAILED);
		}
		return pm;
	}
	
	/**
	 * @Title: editCharity
	 * @Description: 修改慈善机构 
	 * @param: @param pc
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="editCharity")
	@ResponseBody
	public PageMessage editCharity(PageCharity pc){
		PageMessage pm = new PageMessage();
		if(pc!=null && StringUtil.isNotEmpty(pc.getId()) && StringUtil.isNotEmpty(pc.getCharityName())){
			Charity charity = charityService.getCharityByCharityName(pc.getCharityName());
			if(charity!=null && charity.getId() != Long.parseLong(pc.getId())){//说明名称被占用了
				pm.setSuccess(false);
				pm.setErrorMsg(MessageConstant.CHARITYNAME_USED);
			}else{
				int temp = charityService.updateCharity(pc);
				if(temp>0){
					pm.setSuccess(true);
				}else{
					pm.setSuccess(false);
					pm.setErrorMsg(MessageConstant.ALL_FAILED);
				}
			}
		}else{
			pm.setSuccess(false);
			pm.setErrorMsg(MessageConstant.ALL_FAILED);
		}
		return pm;
	}
	
	
	/**
	 * @Title: deleteCharity
	 * @Description: 删除慈善机构
	 * @param: @param charityId
	 * @param: @return
	 * @return PageMessage  
	 */
	@RequestMapping(value="deleteCharity")
	@ResponseBody
	public PageMessage deleteCharity (String charityId){
		PageMessage pm = new PageMessage();
		pm.setSuccess(false);
		if(StringUtil.isNotEmpty(charityId)){
			int count = orderCharityService.countOrderCharityByCharityId(Integer.parseInt(charityId));
			if(count>0){//说明这个慈善机构下面是有订单捐款了，不能直接删除，只能改状态为：disabled
				pm.setErrorMsg(MessageConstant.CHARITY_CAN_NOT_DELETE);
				return pm;
			}
			int temp = charityService.deleteCharity(Integer.parseInt(charityId));
			if(temp>0){
				pm.setSuccess(true);
				return pm;
			}else{
				pm.setErrorMsg(MessageConstant.DELETE_CARD_FAILURE);
				return pm;
			}
		}
		pm.setErrorMsg(MessageConstant.DELETE_CARD_FAILURE);
		return pm;
	}
	
	
	
}
