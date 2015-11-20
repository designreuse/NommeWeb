/**
 * 
 */
package com.camut.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.camut.model.Consumers;
import com.camut.service.ConsumersService;
import com.camut.utils.Log4jUtil;
import com.camut.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.camut.framework.constant.LoginTypeConstant;

/**
 * @ClassName ThirdLoginController.java
 * @author wangpin
 * @createtime Jul 7, 2015
 * @author
 * @updateTime
 * @memo
 */
@Controller("ThirdLoginController")
@RequestMapping("thirdLogin")
public class ThirdLoginController {

	@Autowired
	ConsumersService consumersService;

	/**
	 * @Title: gettoken
	 * @Description: twitter第三方登陆
	 * @param: HttpSession，HttpServletRequest
	 * @return: String
	 */
	@RequestMapping(value = "/twitterLogin", method = RequestMethod.GET)
	public String gettoken(HttpSession session, HttpServletRequest request) {
		Log4jUtil.info("进入twitter第三方登录WEB开始 ==>" + request.getParameter("originalUrlForTwitter"));
		// 将登陆前的地址放在session中
		session.setAttribute("originalUrlForTwitter", request.getParameter("originalUrlForTwitter"));
		OAuthService service = new ServiceBuilder().provider(TwitterApi.class)
		// 此处是 用户名: Meta_Universe 的
				.apiKey("M9PuxP9rqjuFTGWofuyYq4Zxf").apiSecret("SM6rJAUJrjgEmdQZw87ArPFOcGraVo0Ey4kpEs0zgNTOm1h9by")
				//.callback("https://www.metaorder.ca/thirdLogin/twitterCallback")// 加拿大服务器
				// .callback("http://119.29.2.48:8080/camut/thirdLogin/twitterCallback")//中国服务器
				 .callback("http://localhost:8080/thirdLogin/twitterCallback")//
				// 本地机器
				.build();
		Token token = service.getRequestToken();// 获取token
		session.setAttribute("twitterToken", token);
		session.setAttribute("twitterService", service);
		String authorizationUrl = service.getAuthorizationUrl(token);
		Log4jUtil.info("twitter第三方登录WEB转向授权页面 ==>" + authorizationUrl);
		return "redirect:" + authorizationUrl;
		// 下面的key是a76238754用户的
		// .apiKey("ps5npNelxiiTfkYoI3tcpEav7")
		// .apiSecret("kJIgtrPWLKzGVDsEd6ZaTAvTpUgRCbPXpt9en6T5dsULIU0usW")
	}

	/**
	 * @Title: getAccess
	 * @Description: twitter登陆成功回调的地址
	 * @param: HttpServletRequest，HttpSession
	 * @return: String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/twitterCallback", method = RequestMethod.GET)
	public String getAccess(HttpServletRequest req, HttpSession session) {
		Log4jUtil.info("twitter第三方登录WEB ==>nomme的回调地址被请求");
		if (StringUtil.isNotEmpty(req.getParameter("denied"))) {// 如果是点击取消按钮，重新返回到首页
			Log4jUtil.info("twitter第三方登录WEB 成功转入nomme的回调地址 ==>" + "取消授权");
			return "redirect:" + "../index/index";
		}
		Verifier verifier = new Verifier(req.getParameter("oauth_verifier"));
		OAuthService service = (OAuthService) session.getAttribute("twitterService");
		Token access = service.getAccessToken((Token) session.getAttribute("twitterToken"), verifier);
		// 查询个人资料地址
		OAuthRequest r = new OAuthRequest(Verb.GET, "https://api.twitter.com/1.1/account/verify_credentials.json");
		service.signRequest(access, r);
		Response res = r.send();
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse(res.getBody());
		Log4jUtil.info("twitter第三方登录WEB 转入nomme的回调地址 ==>查询个人资料 用户id = " + map.get("id_str").toString());
		Consumers consumers = consumersService.getConsumersByOtherCode(map.get("id_str").toString(), LoginTypeConstant.TWITTER);
		if (consumers != null) {// 存在第三方登陆的信息
			session.setAttribute("consumer", consumers);
		} else {// 不存在第三方登陆的信息
			Consumers consumers2 = new Consumers();
			consumers2.setOtherCode(map.get("id_str").toString());
			consumers2.setNickname(map.get("screen_name").toString());
			consumers2.setLoginType(LoginTypeConstant.TWITTER);
			consumers2.setStatus(0);
			consumers2.setUuid(StringUtil.getUUID());
			consumers2.setRegDate(new Date());
			int flag = consumersService.addConsumerForNomme(consumers2);
			if (flag == 1) {// 增加成功
				session.setAttribute("consumer", consumers2);
			}
		}
		Log4jUtil.info("twitter第三方登录WEB成功 跳转回原来的nomme页面");
		return "redirect:" + session.getAttribute("originalUrlForTwitter").toString();
	}

	/**
	 * @Title: facebookLogin
	 * @Description: facebook第三方登陆
	 * @param: HttpServletRequest HttpSession
	 * @return String
	 */
	@RequestMapping(value = { "/facebookLogin" })
	public String facebookLogin(HttpServletRequest request, HttpSession session) {
		Log4jUtil.info("进入Facebook第三方登录WEB开始 ==>" + request.getParameter("originalUrlForFacbook"));
		// 将登陆前的地址放在session中
		session.setAttribute("originalUrlForFacbook", request.getParameter("originalUrlForFacbook"));
		OAuthService service = new ServiceBuilder().provider(FacebookApi.class)
		// 使用的Facebook 应用账号 用户名: meta.universe.2015@gmail.com 密码：Test20150101
				.apiKey("1913585478866636").apiSecret("4e6e5994324fc99146771cc3f37ba79d")
				//.callback("https://www.metaorder.ca/thirdLogin/facebookCallback")// 加拿大服务器
				// .callback("http://119.29.2.48:8080/camut/thirdLogin/facebookCallback")//国内服务器
				 .callback("http://localhost:8080/thirdLogin/facebookCallback")//本地
				.build();
		String authorizationUrl = service.getAuthorizationUrl(null);// 获取登陆地址
		session.setAttribute("facebookService", service);
		Log4jUtil.info("Facebook第三方登录WEB转向授权页面 ==>" + authorizationUrl);
		return "redirect:" + authorizationUrl;

	}

	/**
	 * @Title: facebookCallback
	 * @Description: facebook第三方登陆成功后的回调地址
	 * @param: HttpServletRequest HttpSession
	 * @return: String
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/facebookCallback" })
	public String facebookCallback(HttpServletRequest request, HttpSession session) {
		Log4jUtil.info("Facebook第三方登录WEB ==>nomme的回调地址被请求");
		Verifier verifier = new Verifier(request.getParameter("code"));
		OAuthService service = (OAuthService) session.getAttribute("facebookService");
		Token accessToken = service.getAccessToken(null, verifier);
		OAuthRequest req = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me");// 查询个人资料地址
		service.signRequest(accessToken, req);
		Response res = req.send();
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse(res.getBody());
		Log4jUtil.info("Facebook第三方登录WEB 转入nomme的回调地址 ==>查询个人资料 用户id = " + map.get("id").toString());
		Consumers consumers = consumersService.getConsumersByOtherCode(map.get("id").toString(), LoginTypeConstant.FACEBOOK);
		if (consumers != null) {// 存在第三方登陆的信息
			session.setAttribute("consumer", consumers);
		} else {// 不存在第三方登陆的信息
			Consumers consumers2 = new Consumers();
			consumers2.setOtherCode(map.get("id").toString());
			consumers2.setNickname(map.get("name").toString());
			consumers2.setLoginType(LoginTypeConstant.FACEBOOK);
			consumers2.setStatus(0);
			consumers2.setUuid(StringUtil.getUUID());
			consumers2.setRegDate(new Date());
			int flag = consumersService.addConsumerForNomme(consumers2);
			if (flag == 1) {// 增加成功
				session.setAttribute("consumer", consumers2);
			}
		}
		Log4jUtil.info("Facebook第三方登录WEB成功 跳转回原来的nomme页面");
		return "redirect:" + session.getAttribute("originalUrlForFacbook").toString();
	}
}
