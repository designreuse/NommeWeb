package com.camut.utils;


import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;

import com.camut.framework.constant.StripeApiKey;
import com.camut.utils.StripeUtil.IS_PRODUCTION;
import com.google.common.base.Objects;
import com.tencent.xinge.XingeApp;

/**
 * 推送工具类
 * @author Administrator
 *
 */
public class PushUtil {
	private static long padAppId=2100152290;
	private static String padSecretKey="9fc22f85d95adab2af1d7c65dae33a98";
	public static enum IS_PRODUCTION{
		YES,
		NO
	}

	// environment variable names
	public static String IS_PRODUCTION_VAR = "IS_PRODUCTION";
	
	public static void push(HttpSession session,String title,String content,String token,int type){
		Log4jUtil.info("发送推送==>"+"token：="+token+"| 类型："+type);
		if(type==1){
			pushSourceIos(session,content, token);
		}else{			
			Log4jUtil.info(XingeApp.pushTokenAndroid(androidAppId, androidSecretKey, title, content, token).toString());
		}
	}
	
	/**
	 * 推送pad
	 * @param title  推送标题
	 * @param content 推送内容
	 * @param token 推送设备号
	 */
	public static  void pushPad(String title,String content,String token){
		//XingeApp push=new XingeApp(padAppId, padSecretKey);
		//Style style = new Style(0,0,0,0,0,0,0,0);
		//push.		
		//push.pushTokenAndroid(padAppId, padSecretKey, title, content, token);
		Log4jUtil.info("推送pad==>"+"title="+title+", content="+content+", token="+token);
		//XingeApp.pushTokenAndroid(padAppId, padSecretKey, title, content, token);
		System.out.println(XingeApp.pushTokenAndroid(padAppId, padSecretKey, title, content, token));
	}
	
	/*com.nestree.nomme
	private static long androidAppId=2100152356;
	private static String androidSecretKey="73648c45d18b839031cefeb5c63582cb";
	*/
	
	//com.canada.nomme
	private static long androidAppId=2100168291;
	private static String androidSecretKey="99b2a994438c5224f058dd16f259f234";
	
	/** 
	 * 推送android手机
	 * @param title  推送标题
	 * @param content 推送内容
	 * @param token 推送设备号
	 */
	public static void pushAndroid(String title,String content,String token){
		System.out.println(XingeApp.pushTokenAndroid(androidAppId, androidSecretKey, title, content, token));
	}
	
	
	
	private static long iosAppId=2200152357L;
	private static String iosSecretKey="b3d88910331b048249fe319a4a4715b1";
	
	/**
	 * 推送ios
	 * @param content 推送内容
	 * @param token 推送设备token 
	 */
	public static void pushIos(String content,String token ){
		System.out.println(XingeApp.pushTokenIos(iosAppId, iosSecretKey, content, token, XingeApp.IOSENV_DEV));
	}
	
	
	public static void pushSourceIos(HttpSession session,String content,String deviceToken ){		
		String path=session.getServletContext().getRealPath("/");
		  try {	         
	           //被推送的iphone应用程序标示符      
	           PayLoad payLoad = new PayLoad();
	           payLoad.addAlert(content);
	           payLoad.addBadge(1);
	           payLoad.addSound("default");
	                    
	           PushNotificationManager pushManager = PushNotificationManager.getInstance();
	           pushManager.addDevice(deviceToken, deviceToken);
	           
	           String host="gateway.sandbox.push.apple.com";  //测试用的苹果推送服务器
	           int port = 2195;          
	           
	           //String certificatePath = path+"/p12/aps_production_Server.p12"; //刚才在mac系统下导出的证书
	           //String certificatePath = path+"/p12/aps_Service_developer.p12"; //刚才在mac系统下导出的证书
	           
	           String certificatePath = path + getIosCertificatePath();
	           
	           String certificatePassword= "1";
	           
	           pushManager.initializeConnection(host, port, certificatePath,certificatePassword, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);
	                     
	           //Send Push
	           Device client = pushManager.getDevice(deviceToken);
	           pushManager.sendNotification(client, payLoad); //推送消息
	           pushManager.stopConnection();
	           pushManager.removeDevice(deviceToken);
	          }
	          catch (Exception e) {
	           e.printStackTrace();
	           System.out.println("push faild!");
	           return;
	          }
	          System.out.println("push succeed!");
	}
	
	public static String getIosCertificatePath()
	{	
		String path=null;
		String variable = IS_PRODUCTION_VAR;
		String is_production = System.getenv(variable);
		
		//check environment variable
		if (is_production == null)
		{
			//// Check Java system properties
			is_production = System.getProperty(variable);
		}
		
		//second check
		if (is_production == null){
			System.out.println("ERROR: The environment variable " + variable + " not found");
			return null;
		}
		
		if (Objects.equal(is_production.toLowerCase(), IS_PRODUCTION.NO.toString().toLowerCase()))
		{
			path = "/p12/aps_Service_developer.p12";
		}
		else if (Objects.equal(is_production.toLowerCase(), IS_PRODUCTION.YES.toString().toLowerCase()))
		{
			path = "/p12/aps_production_Server.p12";
		}
		else{
			System.out.println("ERROR: Environment variable=" +IS_PRODUCTION_VAR + " has invalid value="+ is_production +". "
					+ "Exptecting value=" + IS_PRODUCTION.YES.toString() + "/" + IS_PRODUCTION.NO.toString() );
			return null;
		}
		
				
		return path;
	}
	
	
	public static void main(String[] args) {
		//用法
		//PushUtil.pushIos( "数字常量声明时，前面加0，代表这个常量是以8进制格式声明的。", "ed3d5cab515619feb39dec227b43a5966353e9cf2b362d0a3c21164a7e47f036");		
		//PushUtil.pushAndroid("title", "test", "3cad9b1d34b1dd9c492e9e84f176c303be46190f");
		PushUtil.pushPad("Cancel the order", "You Ki 12345555 Cancelled the2015-10-12 09:51:00.0book information", "e7d6fcfa408e13c2436dade694ff4a30a43eb5da");
		//pushSourceIos(null,"test", "72749aa39475b2feb369dece1abb2e406f7d2402b437e6fcbf07295d2fb32d25");
	}
	
}
