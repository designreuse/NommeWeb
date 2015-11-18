
import com.camut.model.api.ConsumersApiModel;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MailUtil;


public class Test{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConsumersApiModel consumersApiModel=new ConsumersApiModel();
		consumersApiModel.setEmail("make.yuan@aliyun.com");
		
		//System.out.println(JSONObject.toJSON(consumersApiModel).toString());
		
		
		//用户地址列表
		/*ConsumersAddressApiModel consumersAddressApiModel=new ConsumersAddressApiModel();
		List<ConsumersAddressApiModel> list=new ArrayList<ConsumersAddressApiModel>();
		list.add(consumersAddressApiModel);
		list.add(consumersAddressApiModel);
		list.add(consumersAddressApiModel);
		list.add(consumersAddressApiModel);
		System.out.println(JSONObject.toJSON(list));*/
		
		//信用卡列表
		/*CustomerBankaccountApiModel customerBankaccountApiModel = new CustomerBankaccountApiModel();
		List<CustomerBankaccountApiModel> list = new ArrayList<CustomerBankaccountApiModel>();
		list.add(customerBankaccountApiModel);
		list.add(customerBankaccountApiModel);
		list.add(customerBankaccountApiModel);
		System.out.println(JSONObject.toJSON(list));*/
		
		//用户历史订单列表
		/*CustomerPastOrder customerPastOrder = new CustomerPastOrder();
		List<CustomerPastOrder> list = new ArrayList<CustomerPastOrder>();
		list.add(customerPastOrder);
		list.add(customerPastOrder);
		list.add(customerPastOrder);
		list.add(customerPastOrder);
		System.out.println(JSONObject.toJSON(list));*/
		
		//用户历史订单详情
		/*OrderDetailsApiModel orderDetailsApiModel = new OrderDetailsApiModel();
		System.out.println(JSONObject.toJSON(orderDetailsApiModel).toString());*/
		
		//用户收藏列表
		/*CustomerFavoritesApiModel customerFavoritesApiModel = new CustomerFavoritesApiModel();
		List<CustomerFavoritesApiModel> list = new ArrayList<CustomerFavoritesApiModel>();
		list.add(customerFavoritesApiModel);
		list.add(customerFavoritesApiModel);
		list.add(customerFavoritesApiModel);
		list.add(customerFavoritesApiModel);
		System.out.println(JSONObject.toJSON(list));*/
		
		//发布评论
		/*EvaluateApiModel evaluateApiModel = new EvaluateApiModel();
		System.out.println(JSONObject.toJSON(evaluateApiModel).toString());*/
		
		//营业时间显示
		/*OpenTimeApiModel openTimeApiModel = new OpenTimeApiModel();
		List<OpenTimeApiModel> list = new ArrayList<OpenTimeApiModel>();
		list.add(openTimeApiModel);
		list.add(openTimeApiModel);
		list.add(openTimeApiModel);
		list.add(openTimeApiModel);
		System.out.println(JSONObject.toJSON(list));*/
		
		//订单列表（pad）
		/*OrderApiMOdel orderApiMOdel = new OrderApiMOdel();
		List<OrderApiMOdel> list = new ArrayList<OrderApiMOdel>();
		list.add(orderApiMOdel);
		list.add(orderApiMOdel);
		PageModel pageModel=new PageModel();
		pageModel.setRows(list);
		
		System.out.println(JSONObject.toJSON(pageModel));*/
		
		//店铺详情
		/*RestaurantsApiModel restaurantsApiModel = new RestaurantsApiModel();
		System.out.println(JSONObject.toJSON(restaurantsApiModel).toString());*/
		
		//店铺评论
		/*ComplainReplyApiModel complainReplyApiModel = new ComplainReplyApiModel();
		List<ComplainReplyApiModel> list = new ArrayList<ComplainReplyApiModel>();
		list.add(complainReplyApiModel);
		list.add(complainReplyApiModel);
		list.add(complainReplyApiModel);
		list.add(complainReplyApiModel);
		System.out.println(JSONObject.toJSON(list));*/
		
		//店铺优惠信息
		/*DiscountApiModel discountApiModel = new DiscountApiModel();
		System.out.println(JSONObject.toJSON(discountApiModel).toString());*/
		
		//菜品信息
		/*DishApiModel dishApiModel = new DishApiModel();
		List<DishApiModel> list = new ArrayList<DishApiModel>();
		list.add(dishApiModel);
		list.add(dishApiModel);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("menuId", "1");
		map.put("menuName", "seafood");
		map.put("dish", list);
		List test=new ArrayList();
		test.add(map);
		test.add(map);
		
		System.out.println(JSONObject.toJSON(test));*/
		
		//搜索
		/*RestaurantsListApiModel restaurantsListApiModel =new RestaurantsListApiModel();
		List<RestaurantsListApiModel> list = new ArrayList<RestaurantsListApiModel>();
		list.add(restaurantsListApiModel);
		list.add(restaurantsListApiModel);
		PageModel pageModel=new PageModel();
		pageModel.setRows(list);
		
		System.out.println(JSONObject.toJSON(pageModel));*/
		
		//配菜信息
		/*GarnishHeaderApiModel garnishHeaderApiModel = new GarnishHeaderApiModel();
		List<GarnishHeaderApiModel> list = new ArrayList<GarnishHeaderApiModel>();
		list.add(garnishHeaderApiModel);
		list.add(garnishHeaderApiModel);
		list.add(garnishHeaderApiModel);
		list.add(garnishHeaderApiModel);
		System.out.println(JSONObject.toJSON(list));*/
		
		//获取菜单信息
//		MenuApiModel menuApiModel = new MenuApiModel();
//		List<MenuApiModel> list = new ArrayList<MenuApiModel>();
//		list.add(menuApiModel);
//		list.add(menuApiModel);
//		list.add(menuApiModel);
//		list.add(menuApiModel);
//		System.out.println(JSONObject.toJSON(list));
		
		MailUtil.sendMail("标题", "内容", "yuanyoulin@czumi.com");
		Log4jUtil.info("管理员", "测试发送邮件");  //这个一定要放在方法中，必须启动taomcat加载log4j的配置文件才可以
	}

}
