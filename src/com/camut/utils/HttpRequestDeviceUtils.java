package com.camut.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestDeviceUtils {

	/**
	 * The specific information in the Wap gateway Via header description
	 * Wap网关Via头信息中特有的描述信息*/  
	
    private static String mobileGateWayHeaders[]=new String[]{  
    "ZXWAP",//Zte provide wap gateway via information. 中兴提供的wap网关的via信息，For example：Via=ZXWAP GateWayZTE Technologies，  
    "chinamobile.com",//China mobile's nokia wap gateway 中国移动的诺基亚wap网关，For example：Via=WTP/1.1 GDSZ-PB-GW003-WAP07.gd.chinamobile.com (Nokia WAP Gateway 4.1 CD1/ECD13_D/4.1.04)  
    "monternet.com",// Mobile monternet portal wap gateway 移动梦网的网关，For example：Via=WTP/1.1 BJBJ-PS-WAP1-GW08.bj1.monternet.com. (Nokia WAP Gateway 4.1 CD1/ECD13_E/4.1.05)  
    "infoX",//Huawei wap gateway 华为提供的wap网关，For example：Via=HTTP/1.1 GDGZ-PS-GW011-WAP2 (infoX-WISG Huawei Technologies)，或Via=infoX WAP Gateway V300R001 Huawei Technologies  
    "XMS 724Solutions HTG",//Foreign telecom operators wap gateway, Don't know is which one. 国外电信运营商的wap网关，
    "wap.lizongbo.com",//Self test simulation header information自己测试时模拟的头信息  
    "Bytemobile",//Just like a Provide mobile Internet solutions to improving the efficiency of the network 貌似是一个给移动互联网提供解决方案提高网络运行效率的，For example：Via=1.1 Bytemobile OSN WebProxy/5.1  
    };  
    /**
     * The keyword 'User-Agent' of IE OR Firefox and any others browser on PC  
     * 电脑上的IE或Firefox浏览器等的User-Agent关键词*/  
    private static String[] pcHeaders=new String[]{  
    "Windows 98",  
    "Windows ME",  
    "Windows 2000",  
    "Windows XP",  
    "Windows NT",  
    "Ubuntu"  
    };  
    /**The keyword 'User-Agent' in cellphone's browser.
     * 手机浏览器的User-Agent里的关键词*/  
    private static String[] mobileUserAgents=new String[]{  
    "Nokia",//Nokia and any cheap copy cellphone,  诺基亚，有山寨机也写这个的，总还算是手机，Mozilla/5.0 (Nokia5800 XpressMusic)UC AppleWebkit(like Gecko) Safari/530  
    "SAMSUNG",//SAMSUNG. 三星手机 SAMSUNG-GT-B7722/1.0+SHP/VPP/R5+Dolfin/1.5+Nextreaming+SMM-MMS/1.2.0+profile/MIDP-2.1+configuration/CLDC-1.1  
    "MIDP-2",//j2me2.0，Mozilla/5.0 (SymbianOS/9.3; U; Series60/3.2 NokiaE75-1 /110.48.125 Profile/MIDP-2.1 Configuration/CLDC-1.1 ) AppleWebKit/413 (KHTML like Gecko) Safari/413  
    "CLDC1.1",//M600/MIDP2.0/CLDC1.1/Screen-240X320  
    "SymbianOS",//SymbianOS system.  塞班系统的，  
    "MAUI",//Defaule ua of MTK cheap copy cellphone  MTK山寨机默认ua  
    "UNTRUSTED/1.0",//Suspected a cheap copy cellphone ua 疑似山寨机的ua，基本可以确定还是手机  
    "Windows CE",//Windows CE，Mozilla/4.0 (compatible; MSIE 6.0; Windows CE; IEMobile 7.11)  
    "iPhone",//Don't sure iPhone is convert to wap. iPhone是否也转wap？不管它，先区分出来再说。Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_1 like Mac OS X; zh-cn) AppleWebKit/532.9 (KHTML like Gecko) Mobile/8B117  
    "iPad",//IPad UA. iPad的ua，Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; zh-cn) AppleWebKit/531.21.10 (KHTML like Gecko) Version/4.0.4 Mobile/7B367 Safari/531.21.10  
    "Android",//Don't sure Android is convert to wap. Android是否也转wap？Mozilla/5.0 (Linux; U; Android 2.1-update1; zh-cn; XT800 Build/TITA_M2_16.22.7) AppleWebKit/530.17 (KHTML like Gecko) Version/4.0 Mobile Safari/530.17  
    "BlackBerry",//BlackBerry8310/2.7.0.106-4.5.0.182  
    "UCWEB",//Don't sure 'UCWEB' is only used in 'wap' pages. ucweb是否只给wap页面？ Nokia5800 XpressMusic/UCWEB7.5.0.66/50/999  
    "ucweb",//Lowcase'ucweb' Seemingly uc's proxy server . 小写的ucweb貌似是uc的代理服务器Mozilla/6.0 (compatible; MSIE 6.0;) Opera ucweb-squid  
    "BREW",//It's a very strange 'ua'.  很奇怪的ua，例如：REW-Applet/0x20068888 (BREW/3.1.5.20; DeviceId: 40105; Lang: zhcn) ucweb-squid  
    "J2ME",//It's a very strange 'ua', only has 'J2ME' the four case.很奇怪的ua，只有J2ME四个字母  
    "YULONG",//Yulong phone. 宇龙手机，YULONG-CoolpadN68/10.14 IPANEL/2.0 CTC/1.0  
    "YuLong",//Still Yulong phone. 还是宇龙  
    "COOLPAD",//Yulong COOLPAD. 宇龙酷派YL-COOLPADS100/08.10.S100 POLARIS/2.9 CTC/1.0  
    "TIANYU",//Tianyu phone. 天语手机TIANYU-KTOUCH/V209/MIDP2.0/CLDC1.1/Screen-240X320  
    "TY-",//Tianyu. 天语，TY-F6229/701116_6215_V0230 JUPITOR/2.2 CTC/1.0  
    "K-Touch",//Still Tianyu phone. 还是天语K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0  
    "Haier",//Haier phone. 海尔手机，Haier-HG-M217_CMCC/3.0 Release/12.1.2007 Browser/WAP2.0  
    "DOPOD",//DOPOD phone.多普达手机  
    "Lenovo",//Lenovo phone. 联想手机，Lenovo-P650WG/S100 LMP/LML Release/2010.02.22 Profile/MIDP2.0 Configuration/CLDC1.1  
    "LENOVO",//Lenovo phone. 联想手机，比如：LENOVO-P780/176A  
    "HUAQIN",//Huaqin phone.  华勤手机  
    "AIGO-",//Aigo phone. 爱国者居然也出过手机，AIGO-800C/2.04 TMSS-BROWSER/1.0.0 CTC/1.0  
    "CTC/1.0",//Don't understand. 含义不明  
    "CTC/2.0",//Don't understand. 含义不明  
    "CMCC",//Mobile custom cellphone 移动定制手机，K-Touch_N2200_CMCC/TBG110022_1223_V0801 MTK/6223 Release/30.07.2008 Browser/WAP2.0  
    "DAXIAN",//Daxian phone. 大显手机DAXIAN X180 UP.Browser/6.2.3.2(GUI) MMP/2.0  
    "MOT-",//MOTOROLA 摩托罗拉，MOT-MOTOROKRE6/1.0 LinuxOS/2.4.20 Release/8.4.2006 Browser/Opera8.00 Profile/MIDP2.0 Configuration/CLDC1.1 Software/R533_G_11.10.54R  
    "SonyEricsson",// 索爱手机，SonyEricssonP990i/R100 Mozilla/4.0 (compatible; MSIE 6.0; Symbian OS; 405) Opera 8.65 [zh-CN]  
    "GIONEE",//Gionee phone.金立手机  
    "HTC",//HTC手机  
    "ZTE",//中兴手机，ZTE-A211/P109A2V1.0.0/WAP2.0 Profile  
    "HUAWEI",//华为手机，  
    "webOS",//palm手机，Mozilla/5.0 (webOS/1.4.5; U; zh-CN) AppleWebKit/532.2 (KHTML like Gecko) Version/1.0 Safari/532.2 Pre/1.0  
    "GoBrowser",//3g GoBrowser.User-Agent=Nokia5230/GoBrowser/2.0.290 Safari  
    "IEMobile",//Windows CE system phone Built-in browser. Windows CE手机自带浏览器，  
    "WAP2.0"//support wap 2.0. 支持wap 2.0的  
    };  
    /** 
     * According to the characteristics of the current request, to judge whether the request from the mobile terminal
     * Main testing special header information, and the 'user-agent' header.
    * 根据当前请求的特征，判断该请求是否来自手机终端，主要检测特殊的头信息，以及user-Agent这个header 
    * @param request http请求 
    * @return If hit phone features rules, it returns the corresponding string 如果命中手机特征规则，则返回对应的特征字符串 
    */  
    public static boolean isMobileDevice(HttpServletRequest request){  
        boolean pcFlag = false;  
        boolean mobileFlag = false;  
        String via = request.getHeader("Via");  
        String userAgent = request.getHeader("user-agent");  
        for (int i = 0; via!=null && !via.trim().equals("") && i < mobileGateWayHeaders.length; i++) {  
            if(via.contains(mobileGateWayHeaders[i])){  
                mobileFlag = true;  
                break;  
            }  
        }  
        for (int i = 0;!mobileFlag && userAgent!=null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {  
            if(userAgent.contains(mobileUserAgents[i])){  
                mobileFlag = true;  
                break;  
            }  
        }  
        for (int i = 0; userAgent!=null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {  
            if(userAgent.contains(pcHeaders[i])){  
                pcFlag = true;  
            }  
        }  
        if(mobileFlag==true && mobileFlag!=pcFlag){  
            return true;  
        }  
        return false;  
    }  

}
