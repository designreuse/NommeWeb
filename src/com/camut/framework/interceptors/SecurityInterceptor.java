package com.camut.framework.interceptors;


import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.Consumers;
import com.camut.service.ConsumersService;

/**
 * Authority interceptor
 * 权限拦截器
 * 
 */
public class SecurityInterceptor implements HandlerInterceptor {
	@Autowired
	private ConsumersService consumersService;

	private List<String> excludeUrls;//The resource don't need intercept 不需要拦截的资源

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * After finish the page render call
	 * 完成页面的render后调用
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {

	}

	/**
	 * intercept after invoke controller's method
	 * 在调用controller具体方法后拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * intercept before invoke controller's method
	 * 在调用controller具体方法前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		// 获取访问的全路径
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		//System.out.println(url);
		Object sessionInfo = request.getSession().getAttribute(GlobalConstant.SESSION_CONSUMER);
		boolean flag = true;
		//自动登录的功能
		if(sessionInfo == null){//未获取到session中的用户信息
			Cookie[] cookies=request.getCookies();
			if(cookies!=null && cookies.length>0){
				for (Cookie cookie : cookies) {
					if ("user".equals(cookie.getName())) {
						flag = false;//说明有自动登录
						String user = cookie.getValue();
						String userEmail = user.substring(0, user.indexOf("=="));
						String userPassword = user.substring(user.indexOf("==")+2);
						Consumers consumer = consumersService.getConsumersByLoginName(userEmail);
						if(consumer!=null && consumer.getPassword().equals(userPassword)){
							HttpSession session = request.getSession();
				        	session.setAttribute("consumer", consumer);
				        	
							return true;
						}
					}
				}
			}
			if(flag){//没有自动登录
				if((url.indexOf("/api") > -1) || excludeUrls.contains(url)||(url.indexOf("/api") > -1) ){
					return true;
				}
				if ((url.indexOf("/index/index") > -1) || excludeUrls.contains(url)||(url.indexOf("/index/index") > -1) ) {//// If the resource you call on needn't verify 如果要访问的资源是不需要验证的
					return true;
				}
				if(url.indexOf("/index") != -1 || url.indexOf("/payment") != -1){//Url地址栏包含index
					if(sessionInfo==null){//||StringUtil.isEmpty(consumers.getEmail())){
						request.getRequestDispatcher("/").forward(request, response);
						return false;
					}
				}else{
					return true;
				}
			}
		}
		
		//SessionInfo sessionInfo= (SessionInfo) request.getSession().getAttribute(Constant.LOGIN_SESSION_INFO);
		//Consumers consumers = (Consumers)request.getSession().getAttribute(GlobalConstant.SESSION_CONSUMER);
		//拦截商家登陆
		/*if (url.indexOf("/index") != -1) {//Url地址栏包含index
			if(url.endsWith(".do")&&!(url.contains("login.do"))){
				if(sessionInfo==null||StringUtil.isEmpty(consumers.getEmail())){
					request.getRequestDispatcher("/").forward(request, response);
					return false;
				}	
			}else if(url.endsWith(".ajax")){
				return true;
			}
		}else{
			request.getRequestDispatcher("/").forward(request, response);
			return false;
		}*/
		 
		//return super.preHandle(request, response, handler);
		return true;
	}
}
