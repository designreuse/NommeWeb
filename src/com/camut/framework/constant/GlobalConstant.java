package com.camut.framework.constant;

import java.util.HashMap;
import java.util.Map;

public class GlobalConstant {

	public static final String SESSION_INFO = "sessionInfo";
	
	public static final Integer ENABLE = 0; // ENABLE 启用
	public static final Integer DISABLE = 1; // DISABLE 禁用
	
	
	public static final Integer DEFAULT = 0; // DEFAULT 默认
	public static final Integer NOT_DEFAULT = 1; // NOT_DEFAULT 非默认
	
	public static final Map sexlist = new HashMap(){{ put("0", "man");  put("1", "woman");} };
	public static final Map statelist = new HashMap(){{ put("0", "enable");  put("1", "disable");} };
	
	public static final Integer LOGINNAME_ERROR = -1; //用户名不存在
	public static final Integer PASSWORD_ERROR = 0; //密码不正确
	public static final Integer LOGIN_OK = 1; //登录成功
	
	
	public static final Integer SUPER_ADMIN = 0; //系统超级管理员
	public static final Integer ADMIN = 1; //管理员
	
	public static final Integer FAILED = -1; //表示注册失败
	public static final Integer EXIST = 0; //表示会员email已存在
	public static final Integer SUCCESS = 1; //表示注册成功
	
	/*验证用户名是否已经存在*/
	public static final Integer LOGINNAME_EXIST = 1; //用户名已存在
	public static final Integer LOGINNAME_NOT_EXIST = 0; //用户名不存在
	
	public static final Integer ORDRE_RANDOM_LENGTH = 8;//用于定义订单随机数的长度
	
	//1 外送  2 自取 3 预定
	public static final Integer TYPE_PICKUP=2;//自取
	public static final String ORDER_PICKUP_STR="pick up";
	
	public static final Integer TYPE_DELIVERY=1;//外送 
	public static final String ORDER_DELIVERY_STR="Delivery";
	
	public static final Integer TYPE_RESERVATION=3;//预定
	public static final String ORDER_RESERVATION_STR="Reservation";
	
	public static final int  DISCOUNT_TYPE_1=1;
	public static final int  DISCOUNT_TYPE_2=2;
	public static final int  DISCOUNT_TYPE_3=3;
	
	public static final Double CHARITY_RATE=0.05; //捐献给慈善的比率（捐献金额 = 菜品总价 * 比率）
	
	public static final String SESSION_CONSUMER = "consumer";//用户登录时session
	public static final String SESSION_ERSTAURANT = "restaurantsUser";//商家登录时session
	public static final String SESSION_ADMIN = "adminUserLoginname";//商家登录时session
	
	public static Long currentOrderId = (long) 0.0;//当前订单头的Id值
	public static int TIME_CANCEL_ORDER = 1000*60*15;//用户下单后隔多长时间后商家未处理就将该笔订单改成取消状态  单位：毫秒
	
	public static final int GOOGLEMAP_MAX_DISTANCE = 15000000;//搜索页面地图方式下用户所在位置指定范围内商家显示出来
	
	public static final int CANCELED_ORDER = 0;//取消订单状态
	public static final int UNPAID_ORDER = 1;
	public static final int PAID_ORDER = 2;
	public static final int REJECTED_ORDER = 4;//拒绝接单状态
	
	public static enum ORDER_STATUS {
		// 0: cancelled, 1: unpaid, 2: paid, 3: has orders, 4: refuse, 6: refunded, 7: completed, 8: line-up, 9: pay cash, 10: pending 
		CANCELED(0), 
		UNPAID(1), 
		PAID(2),
		ACCEPTED(3),
		REJECTED(4),
		REFUNDED(6),
		COMPLETED(7),
		LINE_UP(8),
		PAY_CASH(9),
		PENDING(10),
		PAYMENT_FAILED(11);
		
	    private final int value;

	    private ORDER_STATUS(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public static enum ORDER_TYPE {
		//1:delivery 2:pick up 3：dine-in/reservation
		DELIVERY(1), 
		PICK_UP(2), 
		DINE_IN_AND_RESERVATION(3);
		
	    private final int value;

	    private ORDER_TYPE(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}

	
	public static enum RESTAURANT_STATUS {
		//-1 is invalid , 0 is active , 1 pending (new businesses registered state 1), 2 frozen ( not approval ), 3 hidden (not show up in the search list)
		INVALID(-1), 
		ACTIVE(0), 
		PENDING(1),
		NOT_APPROVAL(2),
		HIDDEN(3);
		
	    private final int value;

	    private RESTAURANT_STATUS(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}
	
	public static final String DOMAIN_NAME = "http://www.metaorder.ca";//服务器域名
}
