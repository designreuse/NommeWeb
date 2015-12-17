
package com.camut.framework.constant;

/**
 * @ClassName MessageConstant.java
 * @author wangpin
 * @createtime Jun 2, 2015
 * @author
 * @updateTime
 * @memo
 */
public class MessageConstant {

	public static final String LOGINNAME_ERROR = "Username not exist!";//用户名不存在
	public static final String ACCOUNT_NOT_EXIST ="This account does not exist";//邮箱账号不存在
	public static final String PASSWORD_ERROR = "Wrong password, please try again!"; //密码不正确
	public static final String LOGINNAME_EXISTS = "User name already exists";//用户名已存在
	public static final String RESTAURANTSNAMR_EXISTS = "Restanrant name already exists";//店名已存在
	public static final String REGISTER_FAILED = "The username already exists";//用户名已存在
	public static final String FAILED = "Registration failed";//表示注册失败
	public static final String EMAIL_EXIST = "Email does not exist";//email不存在???
	public static final String EMAIL_USED = "Email already used";//email已经被使用
	public static final String ALL_FAILED = "Failed";//失败
	public static final String ADD_FAILED = "Add new employee failed, please try again";//新增员工失败??
	public static final String UPDATE_FAILED = "Modify employee failed, please try again";//修改员工失败??
	public static final String UPDATE_ADMIN_FAILED = "Modify admin failed";//修改管理员失败
	public static final String DELETE_FAILED = "Delete admin failed, please try again";//修改管理员失败??
	public static final String AREA_CONTAIN = "This area may contains some subareas, please delete sub areas first!";//删除区域时如果含有子区域，提示先删除子区域
	public static final String OPERATION_FAILED = "Operation failed";//提示：操作失败
	public static final String HANDLE_ORDER = "Operation failed";//订单处理失败??
	public static final String LOGIN_TIME_OUT = "Login timeout, please login again!";//超时，重新登陆
	public static final String AUDIT_ERROR = "Your account must be verified by Nomme before you can take this action";//待审核状态不能登录
	public static final String NOTADMINLOGIN="Only the administrator can access";//不是管理员不能登陆
	public static final String RESTAURANTNOTEXIST="The restaurant account does not exist in our system";//商家不存在
	public static final String CREATE_ACCOUNT_ERROR = "Create failed please try later !";//创建账户失败稍后再试
	public static final String WRONG_CODE = "Wrong verification code";//输入的验证码不正确
	public static final String PAY_FAIL = "Payment failed, please try again";//支付失败
	public static final String CARD_ERR_MESSAGE = "Wrong card infornamtion";//错误的卡信息
	public static final String STRIPE_ACCOUNT_REGISTER_FAIL = "Registration failed, please try again";//Stripe账号注册失败
	public static final String STRIPE_ADDCARD_FAIL = "Save card failed, please try again";//增加卡失败
	public static final String OUTOFDISTANCE = "Sorry, the restaurant does not deliver to the address you entered.";//送餐距离超过商家最大送餐距离
	public static final String OUTOFTIME = "Sorry, it's too late to cancel your order";//退订单超过规定时间
	public static final String OUTOFBUSINESSTIME = "Sorry, The order time is not in business hours";//订单时间不在营业时间之内
	public static final String CARTERROR = "You’ve changed your order. Do you wish to cancel your previous order？";//订单类型或者商家id与之前不一致
	public static final String UPDATE_EMAIL_FAILURE = "Update Email failure";//修改邮箱失败
	public static final String DELETE_CARD_FAILURE = "Delete card fail,please try again";//删除卡失败
	public static final String ORDER_AFTER_30 = "The order time must be 30 minutes later";//下单时间必须在30分钟后
	public static final String ORDER_AFTER_15 = "The order time must be 15 minutes later";//下单时间必须在15分钟后
	public static final String ORDER_AFTER_NOW="You selected an invalid time";//下单时间必须在现在之后
	public static final String GOOGLE_MAP_NOT_FOUND_ADDRESS = "Google map can not find your address!";//google地图没有找到输入的地址
	public static final String ADDRESS_OUT_OF_DISTANCE = "Your place is over range of this restaurant delivery distance";//距离超出了最大外送距离；
	public static final String VALIDATECODE_MESSAGE="validateCode  Invalid";
	public static final String ACCEPT="";//接受订单推送信息
	public static final String LINE_UP="";//Line up订单推送信息
	public static final String REJECT="";//拒绝订单推送信息
	
	public static final String CHARITYNAME_USED="This charity is existed!";//慈善机构已存在
	public static final String CHARITY_CAN_NOT_DELETE="The charity is used in order, you are unable to delete! You can set the status disabled to restriction use";//慈善机构已在订单中被使用，无法删除
	public static final String CHARITY_DELETE_FAILED="Delete charity filed!";//删除慈善机构失败

	public static enum PASSWORD_VALIDATION {
		// 1: success, 2: password is too short, 3: password contains special
		VALID(1, "Success"), 
		PASSWORD_TOO_SHORT(2, "Password must be at least 6 characters long"), 
		PASSWORD_HAS_SPECIAL_CHARACTERS(3, "Password must contain only digits and letters");

		private final int _value;
		private final String _message;

		private PASSWORD_VALIDATION(int value, String message) {
			_value = value;
			_message = message;
		}


		public final int getValue() {
			return _value;
		}

		public final String getMessage() {
			return _message;
		}
	}
}

