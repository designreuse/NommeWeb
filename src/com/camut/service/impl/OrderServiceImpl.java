package com.camut.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.camut.dao.CartDishGarnishDao;
import com.camut.dao.CartHeaderDao;
import com.camut.dao.CartItemDao;
import com.camut.dao.ConsumersAddressDao;
import com.camut.dao.DiscountDao;
import com.camut.dao.DishDao;
import com.camut.dao.GarnishDao;
import com.camut.dao.GarnishHeaderDao;
import com.camut.dao.OrderDao;
import com.camut.dao.OrderDishGarnishDao;
import com.camut.dao.OrderItemDao;
import com.camut.dao.RestaurantsDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.CartDishGarnish;
import com.camut.model.CartDishGarnishId;
import com.camut.model.CartHeader;
import com.camut.model.CartItem;
import com.camut.model.Consumers;
import com.camut.model.ConsumersAddress;
import com.camut.model.Discount;
import com.camut.model.Dish;
import com.camut.model.DistancePrice;
import com.camut.model.GarnishItem;
import com.camut.model.OrderDishGarnish;
import com.camut.model.OrderDishGarnishId;
import com.camut.model.OrderHeader;
import com.camut.model.OrderItem;
import com.camut.model.Restaurants;
import com.camut.model.api.AcceptOrderApiModel;
import com.camut.model.api.CancelOrderApiModel;
import com.camut.model.api.DineinOrderApiModel;
import com.camut.model.api.LiveOrderApiMdoel;
import com.camut.model.api.OrderAllMoneyApiModel;
import com.camut.model.api.OrderDineInApiModel;
import com.camut.model.api.OrderHeaderId;
import com.camut.model.api.OrderItemApiModel;
import com.camut.model.api.OrderListApiModel;
import com.camut.model.api.OrderMenuApiModel;
import com.camut.model.api.TotalAmountApiModel;
import com.camut.pageModel.PageAdminStatementOrders;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageGarnishItem;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageOrderHeader;
import com.camut.pageModel.PageOrderItem;
import com.camut.pageModel.PageRestaurantOrderStatement;
import com.camut.pageModel.PageSelectItemReservationOrder;
import com.camut.service.CartService;
import com.camut.service.ConsumersAddressService;
import com.camut.service.ConsumersService;
import com.camut.service.DishGarnishService;
import com.camut.service.DishService;
import com.camut.service.GarnishItemService;
import com.camut.service.OrderCharityService;
import com.camut.service.OrderService;
import com.camut.service.PaymentService;
import com.camut.service.RestaurantsService;
import com.camut.service.task.TaskDemoService;
import com.camut.utils.CommonUtil;
import com.camut.utils.CreateOrderNumber;
import com.camut.utils.DateUtil;
import com.camut.utils.GoogleTimezoneAPIUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.StringUtil;

/**
 * @dao OrderServicrImpl.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired private OrderDao orderDao;// 自动注入orderDao
	@Autowired private RestaurantsDao restaurantsDao;
	@Autowired private DishDao dishDao;
	@Autowired private OrderItemDao orderItemDao;
	@Autowired private GarnishDao garnishDao;
	@Autowired private OrderDishGarnishDao orderDishGarnishDao;
	@Autowired private DishService dishService;
	@Autowired private ConsumersService consumersService;
	@Autowired private ConsumersAddressService consumersAddressService;
	@Autowired private DishGarnishService dishGarnishService;
	@Autowired private GarnishItemService garnishItemService;
	@Autowired private GarnishHeaderDao garnishHeaderDao;
	@Autowired private CartHeaderDao cartHeaderDao;
	@Autowired private CartItemDao cartItemDao;
	@Autowired private CartDishGarnishDao cartDishGarnishDao;
	@Autowired private ConsumersAddressDao consumersAddressDao;
	@Autowired private DiscountDao discountDao;
	@Autowired private CartService cartService;
	@Autowired private PaymentService paymentService;  
	@Autowired private TaskDemoService taskDemoService;
	@Autowired private OrderCharityService orderCharityService;
	@Autowired private RestaurantsService restaurantsService;
	
	private static long newOrderId = 0;
	
	/**
	 * @Title: getCount
	 * @Description: 获取查询出的数据数量
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int getCount(){
		return orderDao.getCount();
	}
	
	/**
	 * @Title: selectPastOrder
	 * @Description: 已完成订单列表
	 * @param:  consumerId   
	 * @return: ResultApiModel
	 */
	@Override
	public List<OrderListApiModel> selectPastOrder(String consumerUuid) {
		Date localTime = consumersAddressService.getCurrentLocalTimeFromConsumersDefaultAddress(consumerUuid);
		List<OrderHeader> ohList = orderDao.selectPastOrder(consumerUuid, localTime);
		List<OrderListApiModel> odamList = new ArrayList<OrderListApiModel>();
		for (OrderHeader orderHeader : ohList) {
			String a = orderHeader.getRestaurantUuid();
			Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(a);
			if(restaurants!=null){
				OrderListApiModel olam = new OrderListApiModel();
				olam.setOrderId(orderHeader.getId());
				String orderTypeStr ="";
				if(orderHeader.getOrderType() == 1){
					orderTypeStr = "Delivery";
				} else if (orderHeader.getOrderType() == 2) {
					orderTypeStr = "Pick up";
				} else if (orderHeader.getOrderType() == 3) {
					orderTypeStr = "Reservation/Dine-in";
				}
				olam.setRestaurantName(restaurants.getRestaurantName() + " (" + orderTypeStr + ")");
				olam.setCreatedate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));
				olam.setOrderDate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));
				olam.setTotal(orderHeader.getAmount());
				olam.setOrderType(orderHeader.getOrderType());
				if(orderHeader.getNumber() != null){
					olam.setNumber(orderHeader.getNumber());
				}else {
					olam.setNumber(0);
				}
				odamList.add(olam);
			}
		}
		return odamList;
	}

	/**
	 * @Title: addOrder
	 * @Description: 用户订单新增
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	@SuppressWarnings("deprecation")
	@Override
	public int addOrder(OrderHeader orderHeader) {
		if (orderHeader != null) {
			orderHeader.setOrderNo(CreateOrderNumber.createUnique());
			Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(orderHeader.getRestaurantUuid());
			Date currentLocalTime = GoogleTimezoneAPIUtil.getLocalDateTime(restaurants.getRestaurantLat(),
					restaurants.getRestaurantLng());
			orderHeader.setCreatedate(currentLocalTime);
			
			if(orderHeader.getStatus()== null){
				orderHeader.setStatus(10);
			}
			Date nowDay = null;
			Date orderDay = null;
			nowDay = DateUtil.SetToMidnightTime(currentLocalTime);
			orderDay = DateUtil.SetToMidnightTime(orderHeader.getOrderDate());
			if (nowDay != null && orderDay != null) {
				if (orderDay.after(nowDay)) {
					orderHeader.setStatus(3);
				}
			}
			// orderHeader.setStatus(1);//未付款
			// 添加dinein 类型的订单到原有的reservation订单中
			if (orderHeader.getOrderType() == 3 && orderHeader.getOrderItems() != null
					&& orderHeader.getOrderItems().size() > 0) {
				int flag = orderDao.updateOrderHeader(orderHeader);
				Log4jUtil.info("新增订单==> 订单类型:" + orderHeader.getOrderType() + "-->id:" + orderHeader.getId());
				if (flag == -1) {
					return -1;
				} else {
					taskDemoService.pushOrderid(orderHeader.getId());
					taskDemoService.timerTaskOrder();
				}
			}
			else{//delivery or pick-up
				long ohid = orderDao.addOrder(orderHeader);
				Log4jUtil.info("新增订单==> 订单类型:"+orderHeader.getOrderType() +"-->id:"+ohid);
				if (ohid==-1) {
					return -1;
				}			
				taskDemoService.pushOrderid(ohid);
				taskDemoService.timerTaskOrder();
				orderHeader.setId(ohid);
			}
			
			boolean flag1 = true;
			boolean flag2 = true;
			if (orderHeader.getOrderItems()!=null) {
				for (OrderItem orderItem : orderHeader.getOrderItems()) {
					orderItem.setOrderHeader(orderHeader);
					long ioid = orderItemDao.addOrderItem(orderItem);
					if(ioid>0){//如果订单条目保存成功				
						for (OrderDishGarnish orderDishGarnish : orderItem.getOrderDishGarnishs()) {
							OrderDishGarnish orderDishGarnish1= new OrderDishGarnish();
							int dishId = orderItem.getDishId().intValue();
							OrderDishGarnishId orderDishGarnishId = new OrderDishGarnishId();
							orderDishGarnishId.setGarnishItemId(orderDishGarnish.getGarnishItem().getId().intValue());
							orderDishGarnishId.setOrderItemId((int)ioid);
							orderDishGarnish1.setId(orderDishGarnishId);
							orderDishGarnish1.setGarnishNum(orderDishGarnish.getGarnishNum());
							orderDishGarnish1.setDishId(dishId);
							int temp1 = orderDishGarnishDao.addOrderDishGarnish(orderDishGarnish1);
							if (temp1 < 0) {
								flag2 = false;
								break;
							}
						}
					}
					if (!flag2) {
						flag1 = false;
						break;
					}
				}
			}
			if (flag1) {// 保存成功
				return orderHeader.getId().intValue();
			} else {// 保存失败
				return -1;
			}
		}
		return -1;
	}

	/**
	 * @Title: getOrder
	 * @Description: 获取菜单信息
	 * @param:  type  restaurantId  menuId
	 * @return: List<OrderMenuApiModel>
	 */
	@Override
	public List<OrderMenuApiModel> getOrder(int type, String restaurantUuid, long menuId) {
		OrderHeader ohList = orderDao.getOrder(type, restaurantUuid, menuId);
		List<OrderMenuApiModel> omam = new ArrayList<OrderMenuApiModel>();
		OrderMenuApiModel orderMenuApiModel = new OrderMenuApiModel();
		orderMenuApiModel.setId(ohList.getId());
		orderMenuApiModel.setRestaurantUuid(ohList.getRestaurantUuid());
		Set<OrderItem> orderItems = ohList.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderMenuApiModel.setDishId(orderItem.getDishId());
			Dish dish = dishDao.getDishById(orderItem.getDishId());
			orderMenuApiModel.setChName(dish.getChName());
			orderMenuApiModel.setPrice(dish.getPrice());
			orderMenuApiModel.setStatus(dish.getStatus());
			omam.add(orderMenuApiModel);
		}
		return omam;
	}
	
	/**
	 * @Title: getOrdersByRestaurantId
	 * @Description: 获取商家所有订单加载到表格
	 * @param: @param restaurantId
	 * @param: @param pf
	 * @return PageModel  
	 */
	
	/**
	 每笔订单费用计算方式：
	 	基本费 = 总菜品价+总配菜价+运费
	 	联邦税 = 基本费*联邦税率（GST）
	 	省税 = 基本费*省税率（PST）
	 	最终订单费用 = 基本费+联邦税+省税
	 	PS:小费不参加税金计算，也未放入最终订单费用中
	  */
	public PageModel getOrdersByRestaurantUuid(String restaurantUuid, PageFilter pf,String status, String orderDate){
		PageModel pm = new PageModel();
		List<OrderHeader> orderHeaderList = orderDao.getOrdersByRestaurantUuid(restaurantUuid, pf,status,orderDate);
		if(orderHeaderList!=null && orderHeaderList.size()>0){//如果有订单
			List<PageOrderHeader> pageOrderList = new ArrayList<PageOrderHeader>();
			for (OrderHeader oh : orderHeaderList){//遍历组装每一个订单头
				//double totalFee = oh.getLogistics();//设置初始化当前订单的总金额为运费
				PageOrderHeader pageOrderHeader = new PageOrderHeader();//创建一个用于页面显示的模型
				pageOrderHeader.setId(oh.getId().intValue());//设置订单id
				pageOrderHeader.setConsumerName(oh.getPeopleName());//设置订餐人姓名
				pageOrderHeader.setLoginname(oh.getEmail());//设置订餐人的账户登录名
				pageOrderHeader.setPhone(oh.getPhoneNumber());//设置订餐人电话
				pageOrderHeader.setMemo(oh.getMemo());//设置订餐人备注信息
				pageOrderHeader.setOrderType(oh.getOrderType());//设置订单类型  1:外送 2：自取 3：到店就餐; 1:delivery 2:pick up 3dine-in
				pageOrderHeader.setCreatedate(oh.getCreatedate());//设置订单创建时间
				pageOrderHeader.setStatus(oh.getStatus());//设置订单状态
				pageOrderHeader.setOrderDate(oh.getOrderDate());//设置收货时间
				if (oh.getNumber()!=null) {
					pageOrderHeader.setNumber(oh.getNumber());//设置人数
				}
				pageOrderHeader.setAddress(oh.getAddress());//设置收货地址
				pageOrderHeader.setRejection(oh.getRejection());//设置订单中的拒绝理由
				pageOrderHeader.setTip(oh.getTip());//设置小费
				pageOrderHeader.setLogistics(oh.getLogistics());//设置送餐费
				pageOrderHeader.setOrderNo(oh.getOrderNo());//设置订单号
				pageOrderHeader.setTotal(oh.getAmount());
				/*Set<OrderItem> orderItems = oh.getOrderItems();///获取当前订单下的所有订单条目list（菜品list）
				Set<PageOrderItem> pageOrderItems = new HashSet<PageOrderItem>();//创建用于前台显示的订单条目list
				if(orderItems.size()>0){//判断如果有订单条目，就处理订单条目
					for (OrderItem oi : orderItems) {//遍历组装订单条目，一个条目即一个菜品
						Dish dish = null;
						if (oi.getDishId()!=null) {
							long dishId = (long)oi.getDishId();//获取菜品id
							dish = dishService.getDishById(dishId+"");//通过菜品id获取菜品基本信息
						}
						if(dish!=null){//如果能取到对应菜品就组装菜品里面的信息到前台菜品对象
							totalFee += oi.getPrice() * oi.getNum();//总价加上当前菜品价格
							PageOrderItem pageOrderItem = new PageOrderItem();//创建新的页面订单条目对象
							pageOrderItem.setOrderHeaderId(oh.getId().intValue());//设置条目所属的订单头id
							pageOrderItem.setDishId(oi.getDishId());//设置菜品id
							pageOrderItem.setChName(dish.getChName());//设置菜品中文名称
							pageOrderItem.setEnName(dish.getEnName());//设置菜品英文名称
							pageOrderItem.setInstruction(oi.getInstruction());//设置菜品特殊需求
							pageOrderItem.setNum(oi.getNum());//设置数量
							pageOrderItem.setPrice(oi.getPrice());//设置价格
							pageOrderItem.setStatus(oi.getStatus());//设置状态
							//Set<GarnishItem> garnishItemsSet = oi.getGarnishItemsSet();//获取当前菜品的配菜集合
							List<OrderDishGarnish> orderDishGarnishList = orderDishGarnishDao.getOrderDishGarnishByorderItemIdAndDishId(oi.getId().intValue(),dish.getId().intValue());
							Set<PageGarnishItem> pageGarnishItems = new HashSet<PageGarnishItem>();//创建用于前台显示的菜品配菜list
							if(orderDishGarnishList!=null){
								for (OrderDishGarnish odg : orderDishGarnishList) {//遍历组装菜品的配菜
									//通过菜品id和配菜id查找到唯一一个菜品配菜对象
									PageGarnishItem pageGarnishItem = new PageGarnishItem();//创建一个用于页面显示的配菜对象
									//pageGarnishItem.setGarnishItemId(odg.getGarnishItemId());//设置配菜的id
									//DishGarnish dishGarnish = dishGarnishService.getDishGarnishByDishIdAndGarnishItemId(dishId, odg.getGarnishItemId());
									if(dishGarnish!=null){//如果找到得配菜
										pageGarnishItem.setAddprice(dishGarnish.getAddprice());//设置菜品配菜价格
										totalFee += dishGarnish.getAddprice();//增加菜品配菜价格到总价
									}
								//	GarnishItem garnishItem = garnishItemService.getGarnishItemById(odg.getGarnishItemId());
									if(garnishItem!=null){
										pageGarnishItem.setGarnishName(garnishItem.getGarnishName());//设置配菜的名称
									}
									pageGarnishItems.add(pageGarnishItem);//添加配菜到当前配菜list
								}
							}
							pageOrderItem.setPageGarnishItemsSet(pageGarnishItems);//设置配菜list到当前订单即菜品
							pageOrderItems.add(pageOrderItem);//添加当前订单到当前订单list
						}
					}
				}*/
				//double gst = Math.floor(totalFee*oh.getGst()*100+0.5)/100.0;//计算联邦税额
				//double pst = Math.floor(totalFee*oh.getPst()*100+0.5)/100.0;//计算省税额
				//pageOrderHeader.setGst(gst);//设置联邦税额
				//pageOrderHeader.setPst(pst);//设置省税额
				//pageOrderHeader.setTotal(Math.floor((totalFee+gst+pst)*100+0.5)/100.0);//设置金额
				//pageOrderHeader.setPageOrderItems(pageOrderItems);////设置订单list到当前订单头
				pageOrderList.add(pageOrderHeader);////添加当前订单到订单list
			}
			pm.setRows(pageOrderList);
			pm.setTotal(orderDao.getCount());
		}
		return pm;
	}
	
	/**
	 * @Title: OrderHeader
	 * @Description:通过id获取订单
	 * @param:  orderHeaderId
	 * @return: OrderHeader
	 */
	public OrderHeader getOrderById(long orderHeaderId){
		return orderDao.getOrderById(orderHeaderId);
	}

	/**
	 * @Title: selectRestaurantOrder
	 * @Description: 店家订单列表
	 * @param:  restaurantId  orderType  createdate status
	 * @return: PageModel
	 */
	@Override
	public List<CancelOrderApiModel> selectRestaurantOrder(String restaurantUuid, String orderType, String createdate) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			List<OrderHeader> ohList = orderDao.completeOrder(restaurantUuid,createdate, orderType);
			List<CancelOrderApiModel> coamList = new ArrayList<CancelOrderApiModel>();
			for (OrderHeader orderHeader : ohList) {
				CancelOrderApiModel coam = new CancelOrderApiModel();
				if(StringUtil.isNotEmpty(orderHeader.getPeopleName())){
					coam.setFirstName(orderHeader.getPeopleName());
				} else {
					coam.setFirstName("");
				}
				
				if(StringUtil.isNotEmpty(orderHeader.getPhoneNumber())){
					coam.setPhone(orderHeader.getPhoneNumber());
				} else {
					coam.setPhone("");
				}
				coam.setOrderDate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String tablename = dateFormat.format(orderHeader.getOrderDate());
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				String tablename1 = dateFormat1.format(orderHeader.getOrderDate());
				int h = Integer.parseInt(tablename.split(":")[0]);
				if(h-12<0){//判断是am还是pm
					coam.setOrderDateStr(tablename1+"AM");
				}
				else if(h-12==0){
					coam.setOrderDateStr(tablename1+"PM");
				}
				else{
					coam.setOrderDateStr(tablename1+"PM");
				}
				coam.setOrderType(orderHeader.getOrderType());
				coam.setTotal(orderHeader.getAmount());
				coam.setPayment(orderHeader.getPayment());
				coam.setStatus(orderHeader.getStatus());
				coam.setOrderId(orderHeader.getId());
				coamList.add(coam);		
			}
			return coamList;
		}
		return null;
	}

	/**
	 * @Title: selectCurrentOrder
	 * @Description:  未完成订单列表
	 * @param:  consumerId  
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderListApiModel> selectCurrentOrder(String consumerUuid) {
		Date localTime = consumersAddressService.getCurrentLocalTimeFromConsumersDefaultAddress(consumerUuid);
		List<OrderHeader> ohList = orderDao.selectCurrentOrder(consumerUuid, localTime);
		List<OrderListApiModel> odamList = new ArrayList<OrderListApiModel>();
		for (OrderHeader orderHeader : ohList) {
			String a = orderHeader.getRestaurantUuid();
			Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(a);
			OrderListApiModel olam = new OrderListApiModel();
			olam.setOrderId(orderHeader.getId());
			String orderTypeStr ="";
			if(orderHeader.getOrderType() == 1){
				orderTypeStr = "Delivery";
			} else if (orderHeader.getOrderType() == 2) {
				orderTypeStr = "Pick up";
			} else if (orderHeader.getOrderType() == 3) {
				orderTypeStr = "Reservation/Dine-in";
			}
			olam.setRestaurantName(restaurants.getRestaurantName() + " (" + orderTypeStr + ")");
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			olam.setCreatedate(StringUtil.transformDateToAMPMString(orderHeader.getCreatedate()));
			olam.setOrderDate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));
			olam.setTotal(orderHeader.getAmount());
			olam.setOrderType(orderHeader.getOrderType());
			if(orderHeader.getNumber() != null){
				olam.setNumber(orderHeader.getNumber());
			}else {
				olam.setNumber(0);
			}
			odamList.add(olam);
		}
		return odamList;
	}

	/**
	 * @Title: cancelOrder
	 * @Description:  取消订单
	 * @param:  orderId  
	 * @return: -1失败   1成功
	 */
	@Override
	public int cancelOrder(long orderId) {
			OrderHeader orderHeader = orderDao.getOrderById(orderId);
			if(orderHeader != null&&orderHeader.getStatus()!=7){
				if (orderHeader.getOrderType()==1) {//delivery  >30m
					if (orderHeader.getOrderDate().before(new Date(new Date().getTime()+30*60*1000))) {
						return -2;
					}
				}
				else if(orderHeader.getOrderType()==2){//pick-up >15m
					if (orderHeader.getOrderDate().before(new Date(new Date().getTime()+30*60*1000))) {
						return -2;
					}
				}
				else if(orderHeader.getOrderType()==3){//reservation >60m
					if (orderHeader.getOrderDate().before(new Date(new Date().getTime()+30*60*1000))) {
						return -2;
					}
				}
				if (orderHeader.getPayment()==1) {
					//信用卡支付
					if (orderHeader.getChargeId()!=null) {
						String refundId = CommonUtil.refundAll(orderHeader.getChargeId());
						if (StringUtil.isNotEmpty(refundId)) {//退款成功
							orderHeader.setStatus(0);
							orderHeader.setChargeId(refundId);
							int flag = orderDao.updateOrderHeader(orderHeader);
							if (flag==1) {
								//取消订单成功，需要删除这笔订单的捐款
								flag = orderCharityService.deleteOrderCharity(orderHeader.getId().intValue());
								return 1;
							}
							
						}
					}
				}
				else{
					orderHeader.setStatus(0);
					int flag = orderDao.updateOrderHeader(orderHeader);
					if (flag==1) {
						//取消订单成功，需要删除这笔订单的捐款
						flag = orderCharityService.deleteOrderCharity(orderHeader.getId().intValue());
						return 1;
					}
					
				}
			}
		return -1;
	}

	/**
	 * @Title: repeatOrder
	 * @Description:  重复下订单
	 * @param:  orderId  consumerId
	 * @return: -1失败   1成功
	 */
	@Override
	public int repeatOrder(long orderId, String mobiletoken) {
		
		OrderHeader orderHeader = orderDao.getOrderById(orderId);
		// 返回
		if (orderHeader != null && orderHeader.getStatus() == 7 && orderHeader.getOrderType()!=3) {// 订单存在并且是一件完成的订单
			CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(orderHeader.getConsumers().getUuid());
			if (cartHeader!=null) {
				//购物车存在，先删除
				int i = cartService.deleteCartByConsumerUuid(orderHeader.getConsumers().getUuid());
				if (i!=1) {//删除失败
					return -1;
				}
			}
			CartHeader cartHeader1 = new CartHeader();
			if (StringUtil.isNotEmpty(mobiletoken)) {
				cartHeader1.setMobileToken(mobiletoken);// 设备号
			}
			int temp = this.repeatOrderMethod(orderHeader, cartHeader1);
			
			if(temp>0){
				return 1;
			}else{
				return -1;
			}
		}

		return -1;
	}
	
	/**
	 * @Title: repeatOrderWebUsed
	 * @Description: web端使用的重复下单
	 * @param: @param jsonResObj
	 * @return PageMessage  
	 */
	@Override
	public int repeatOrderWebUsed(int orderId, String consumerUuid) {
		CartHeader cartHeader = cartHeaderDao.getCartHeaderByConsumerUuid(consumerUuid);
		if(cartHeader == null){
			cartHeader = new CartHeader();
		}
		OrderHeader orderHeader = orderDao.getOrderById(orderId);
		if(orderHeader!=null){
			int temp = this.repeatOrderMethod(orderHeader, cartHeader);
			return temp;
		}
		else{
			return -1;
		}
	}
		
	/**
	 * @Title: addReservation
	 * @Description: 用户新增订桌 reservation
	 * @param: @param jsonResObj
	 * @return PageMessage  
	 */
	@SuppressWarnings("unchecked")
	public int addReservation(String jsonResObj){
		int temp = 0;
		Map<String,Object> map = (Map<String,Object>)JSONUtils.parse(jsonResObj);
		OrderHeader oh = new OrderHeader();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CANADA);
		Date orderDate = null;
		try {
			orderDate = sdf.parse(map.get("reservationTime").toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		oh.setOrderDate(orderDate);

		Consumers consumers = new Consumers();
		consumers.setUuid(map.get("consumerUuid").toString());
		oh.setConsumers(consumers);
		oh.setOrderType(Integer.parseInt(map.get("orderType").toString()));
		oh.setCreatedate(new Date());
		if(map.get("email")!=null && map.get("email").toString().length()>0){
			oh.setEmail(map.get("email").toString());
		}
		if(map.get("specialRequest")!=null && map.get("specialRequest").toString().length()>0){
			oh.setMemo(map.get("specialRequest").toString());
		}
		oh.setPayment(0);
		oh.setRestaurantUuid(map.get("restaurantUuid").toString());
		oh.setNumber(Integer.parseInt(map.get("peopleNumber").toString()));
		oh.setOrderNo(CreateOrderNumber.createUnique());
		oh.setPeopleName(map.get("firstName").toString()+" "+map.get("lastName").toString());
		oh.setPhoneNumber(map.get("phoneNumber").toString());
		
		Restaurants restaurants = restaurantsDao.getRestaurantsByUuid(oh.getRestaurantUuid());
		Date currentLocalTime = GoogleTimezoneAPIUtil.getLocalDateTime(restaurants.getRestaurantLat(),
				restaurants.getRestaurantLng());

		if(oh.getStatus()== null){
			oh.setStatus(10);
		}
		Date nowDay = null ;
		Date orderDay = null; 
		nowDay = DateUtil.SetToMidnightTime(currentLocalTime);
		orderDay = DateUtil.SetToMidnightTime(oh.getOrderDate());
		if (nowDay != null && orderDay != null) {
			if (orderDay.after(nowDay)) {
				oh.setStatus(3);
			}
		}
		
		temp = (int)(orderDao.addOrder(oh));
		if(temp>0){
			Log4jUtil.info("新增订单==> 订单类型:"+oh.getOrderType() +"-->id:"+temp);
			/*GlobalConstant.currentOrderId = (long)temp;
			if(GlobalConstant.currentOrderId >0){
				taskDemoService.timerTaskOrder();
			}*/
		}	
		System.out.println("temp=" + temp);
		return temp;
	}
	
	/**
	 * @Title: getUnpaidReservationOrders
	 * @Description: 获取某人某商家的reservation类型的未付款且时间有效的订单
	 * @param: consumerUuid
	 * @param: restaurantUuid
	 * @return List<PageOrderHeader>  
	 */
	public List<PageSelectItemReservationOrder> getUnpaidReservationOrders(String consumerUuid, String restaurantUuid){
		Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
		List<PageSelectItemReservationOrder> orderHeaderList = orderDao.getUnpaidReservationOrders(consumerUuid,
				restaurantUuid, localTime);
		return orderHeaderList;
	}

	/**
	 * @Title: cancelOrder
	 * @Description: 已取消的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	@Override
	public List<CancelOrderApiModel> cancelOrder(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			List<OrderHeader> ohList = orderDao.cancelOrder(restaurantUuid);
			List<CancelOrderApiModel> coamList = new ArrayList<CancelOrderApiModel>();
			for (OrderHeader orderHeader : ohList) {
				CancelOrderApiModel coam = new CancelOrderApiModel();
				if(StringUtil.isNotEmpty(orderHeader.getPeopleName())){
					coam.setFirstName(orderHeader.getPeopleName());
				} else {
					coam.setFirstName("");
				}
				if(StringUtil.isNotBlank(orderHeader.getPhoneNumber())){
					coam.setPhone(orderHeader.getPhoneNumber());
				} else {
					coam.setPhone("");
				}
				coam.setOrderDate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String tablename = dateFormat.format(orderHeader.getOrderDate());
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				String tablename1 = dateFormat1.format(orderHeader.getOrderDate());
				int h = Integer.parseInt(tablename.split(":")[0]);
				if(h-12<0){//判断是am还是pm
					coam.setOrderDateStr(tablename1+"AM");
				}
				else if(h-12==0){
					coam.setOrderDateStr(tablename1+"PM");
				}
				else{
					coam.setOrderDateStr(tablename1+"PM");
				}
				
				coam.setOrderType(orderHeader.getOrderType());
				coam.setTotal(orderHeader.getAmount());
				coam.setPayment(orderHeader.getPayment());
				coam.setStatus(orderHeader.getStatus());
				coam.setOrderId(orderHeader.getId());
				coamList.add(coam);
			}
			return coamList;
		}
		return null;
	}

	/**
	 * @Title: completeOrder
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	@Override
	public List<CancelOrderApiModel> completeOrder(String restaurantUuid, String status) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			List<OrderHeader> ohList = orderDao.completeOrderAll(restaurantUuid, status);
			List<CancelOrderApiModel> coamList = new ArrayList<CancelOrderApiModel>();
			for (OrderHeader orderHeader : ohList) {
				CancelOrderApiModel coam = new CancelOrderApiModel();
				if(StringUtil.isNotEmpty(orderHeader.getPeopleName())){
					coam.setFirstName(orderHeader.getPeopleName());
				} else{
					coam.setFirstName("");
				}
				if(StringUtil.isNotEmpty(orderHeader.getPhoneNumber())){
					coam.setPhone(orderHeader.getPhoneNumber());
				} else {
					coam.setPhone("");
				}
				coam.setOrderDate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String tablename = dateFormat.format(orderHeader.getOrderDate());
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
				String tablename1 = dateFormat1.format(orderHeader.getOrderDate());
				int h = Integer.parseInt(tablename.split(":")[0]);
				if(h-12<0){//判断是am还是pm
					coam.setOrderDateStr(tablename1+"AM");
				}
				else if(h-12==0){
					coam.setOrderDateStr(tablename1+"PM");
				}
				else{
					coam.setOrderDateStr(tablename1+"PM");
				}
				
				coam.setOrderType(orderHeader.getOrderType());
				coam.setTotal(orderHeader.getAmount());
				coam.setPayment(orderHeader.getPayment());
				coam.setStatus(orderHeader.getStatus());
				coam.setOrderId(orderHeader.getId());
				coamList.add(coam);		
			}
			return coamList;
		}
		return null;
	}

	/**
	 * @Title: liveOrder
	 * @Description: 当天未处理的订单
	 * @param: restaurantId
	 * @return: List<OrderHeader>
	 */
	@Override
	public LiveOrderApiMdoel liveOrder(String restaurantUuid) {
		List<Long> orderId = new ArrayList<Long>();
		LiveOrderApiMdoel liveOrderApiMdoel = new LiveOrderApiMdoel();
		Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
		List<OrderHeaderId> oh = orderDao.liveOrder(restaurantUuid, localTime);
		for (OrderHeaderId orderHeader : oh) {
			orderId.add(orderHeader.getOrderId());
		}
		liveOrderApiMdoel.setOrderId(orderId);
		liveOrderApiMdoel.setTotal(oh.size());
		return liveOrderApiMdoel;
	}

	/**
	 * @Title: upcomingOrder
	 * @Description: 当天未处理的订单
	 * @param: restaurantId
	 * @return: List<OrderHeader>
	 */
	@Override
	public LiveOrderApiMdoel upcomingOrder(String restaurantUuid) {
		List<Long> orderId = new ArrayList<Long>();
		LiveOrderApiMdoel liveOrderApiMdoel = new LiveOrderApiMdoel();
		Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
		List<OrderHeaderId> oh = orderDao.upcomingOrder(restaurantUuid, localTime);
		for (OrderHeaderId orderHeader : oh) {
			orderId.add(orderHeader.getOrderId());
		}
		liveOrderApiMdoel.setOrderId(orderId);
		liveOrderApiMdoel.setTotal(oh.size());
		return liveOrderApiMdoel;
	}

	/**
	 * @Title: acceptOrder
	 * @Description: 当天已处理的订单列表
	 * @param:  restaurantId   
	 * @return: List<AcceptOrderApiModel>
	 */
	@Override
	public List<AcceptOrderApiModel> acceptOrder(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
			List<AcceptOrderApiModel> ohList = orderDao.acceptOrder(restaurantUuid, localTime);
			for (AcceptOrderApiModel a : ohList) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String tablename = dateFormat.format(a.getOrderDate());
				int h = Integer.parseInt(tablename.split(":")[0]);
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm");
				String tablename1 = dateFormat1.format(a.getOrderDate());
				if(h-12<0){//判断是am还是pm
					a.setOrderDateStr(tablename1+"AM");
				}
				else if(h-12==0){
					a.setOrderDateStr(tablename1+"PM");
				}
				else{
					a.setOrderDateStr(tablename1+"PM");
				}
				String firstName ="";
				if(a.getFirstName() != null){
					firstName = a.getFirstName();
				}
				String lastName ="";
				if(a.getLastName() != null){
					lastName = a.getLastName();
				}
				a.setShowName(firstName + " " + lastName);
			}
			return ohList;
		}
		return null;
	}

	/**
	 * @Title: acceptUpcomingOrder
	 * @Description: 处理非当天的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<AcceptOrderApiModel> acceptUpcomingOrder(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
			List<AcceptOrderApiModel> ohList = orderDao.acceptUpcomingOrder(restaurantUuid, localTime);
			for (AcceptOrderApiModel a : ohList) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String tablename = dateFormat.format(a.getOrderDate());
				int h = Integer.parseInt(tablename.split(":")[0]);
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("hh:mm");
				String tablename1 = dateFormat1.format(a.getOrderDate());
				if(h-12<0){//判断是am还是pm
					a.setOrderDateStr(tablename1+"AM");
				}
				else if(h-12==0){
					a.setOrderDateStr(tablename1+"PM");
				}
				else{
					a.setOrderDateStr(tablename1+"PM");
				}
				String firstName ="";
				if(a.getFirstName() != null){
					firstName = a.getFirstName();
				}
				String lastName ="";
				if(a.getLastName() != null){
					lastName = a.getLastName();
				}
				a.setShowName(firstName + " " + lastName);
			}
			return ohList;
		}
		return null;
	}
	
	/**
	 * @Title: totalAmount
	 * @Description: 当天营业总金额
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public TotalAmountApiModel totalAmount(String restaurantUuid) {
		TotalAmountApiModel amountApiModel = new TotalAmountApiModel();
		List<OrderHeader> orderHeaderList = orderDao.totalAmount(restaurantUuid);
		double total = 0.0;
		if (orderHeaderList.size() > 0) {
			for (OrderHeader orderHeader : orderHeaderList) {
				total += orderHeader.getTotal();
			}
		}
		amountApiModel.setTotalamount(total);
		return amountApiModel;
	}

	/**
	 * @Title: updateOrder
	 * @Description: 修改订单
	 * @param:    OrderHeader orderHeader
	 * @return: int -1失败 1成功
	 */
	@Override
	public int updateOrder(OrderHeader orderHeader) {
		if (orderHeader!=null) {
			return orderDao.updateOrderHeader(orderHeader);
		}
		return -1;
	}

	/**
	 * @Title: getDineinOrder
	 * @Description: 返回商家同意的预定订单
	 * @param:    restaurantId
	 * @return: List<OrderHeader>
	 */ 
	@Override
	public List<DineinOrderApiModel> getDineinOrder(String restaurantUuid) {
		if(StringUtil.isNotEmpty(restaurantUuid)){
			List<OrderHeader> ohList = orderDao.getDineinOrder(restaurantUuid);
			List<DineinOrderApiModel> doamList = new ArrayList<DineinOrderApiModel>();
			for (OrderHeader orderHeader : ohList) {
				DineinOrderApiModel doam = new DineinOrderApiModel();
				doam.setOrderDate(orderHeader.getOrderDate());
				doam.setNumber(orderHeader.getNumber());
				doam.setOrderId(orderHeader.getId());
				doamList.add(doam);
			}
			return doamList;
		}
		return null;
	}

	/**
	 * @Title: getDineIn
	 * @Description: 商家已经审核的订单（预定）
	 * @param: consumerUuid
	 * @param: restaurantUuid
	 * @return: List<OrderDineInApiModel>
	 */
	@Override
	public List<OrderDineInApiModel> getDineIn(String consumerUuid, String restaurantUuid) {
		if (StringUtil.isNotEmpty(consumerUuid)) {
			Date localTime = restaurantsService.getCurrentLocalTimeFromRestaurantsUuid(restaurantUuid);
			List<OrderHeader> ohlist = orderDao.getDineIn(consumerUuid, restaurantUuid, localTime);
			List<OrderDineInApiModel> odamList = new ArrayList<OrderDineInApiModel>();
			for (OrderHeader orderHeader : ohlist) {
				if (orderHeader.getOrderItems() == null || orderHeader.getOrderItems().size() == 0) {
					OrderDineInApiModel odam = new OrderDineInApiModel();
					odam.setOrderDate(StringUtil.transformDateToAMPMString(orderHeader.getOrderDate()));// 预定时间
					odam.setNumber(orderHeader.getNumber());// 就餐人数
					odam.setOrderId(orderHeader.getId());//订单id
					odamList.add(odam);
				}
			}
			return odamList;
		}
		return null;
	}

	
	/**
	 * @Title: getOrderMoney
	 * @Description: 获取订单所有的费用
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public OrderAllMoneyApiModel getOrderMoney(String cartHeaderId, String addressId) {
		if(cartHeaderId != null){
			OrderAllMoneyApiModel omam = new OrderAllMoneyApiModel();
			CartHeader ch = cartHeaderDao.getCartHeaderById(Long.parseLong(cartHeaderId));
			if(ch != null){
				
				/*if(ch.getOrderType()==1){
					omam.setOrderType("Delivery");
				} else if (ch.getOrderType()==2) {
					omam.setOrderType("Pick up");
				} else if (ch.getOrderType()==3) {
					omam.setOrderType("Reservation/Dine-in");
				}*/
				
				Restaurants r = restaurantsDao.getRestaurantsByUuid(ch.getRestaurantUuid());
				double unitprice = 0.00;//菜+配菜
				double disPrice = 0.00;//获取配送费
				double tax = 0.00;//税率
				Set<CartItem> cartItems = ch.getCartItems();
				for (CartItem cartItem : cartItems) {
					unitprice += cartItem.getUnitprice();//菜+配菜
				}
				
				//计算优惠
				if (ch.getDiscountId()!=null) {
					Discount discount = discountDao.getDiscount(ch.getDiscountId());
					if (discount!=null) {
						//减钱优惠
						if (discount.getType()==GlobalConstant.DISCOUNT_TYPE_1) {
							unitprice = StringUtil.convertLastDouble(unitprice-(discount.getPrice()==null?0:discount.getPrice()));
						}
						//打折优惠
						else if(discount.getType()==GlobalConstant.DISCOUNT_TYPE_2){
							unitprice = StringUtil.convertLastDouble(unitprice*((100-(discount.getDiscount()==null?0:discount.getDiscount()))/100));
						}
					}
				
				}
				
				if(!StringUtil.isEmpty(addressId)){
					ConsumersAddress consumersAddress = consumersAddressDao.getConsumersAddress(Long.parseLong(addressId));
					//获取配送费
					double distance = CommonUtil.getDistance(consumersAddress.getLat(), consumersAddress.getLng(), r.getRestaurantLng(), r.getRestaurantLat());
					Set<DistancePrice> set = r.getDistancePricesSet();
					for (DistancePrice distancePrice : set) {
						if(distance==0 && distance==distancePrice.getStartDistance()){//距离为0的情况
							if(unitprice<distancePrice.getOrderPrice()){//实际总价未达到订单价格;
								disPrice = distancePrice.getNotupPrice();
								break;
							}else{
								disPrice = distancePrice.getUpPrice();
								break;
							}
						}
						if(distance <= distancePrice.getEndDistance() && distance > distancePrice.getStartDistance()){
							if(unitprice<distancePrice.getOrderPrice()){//实际总价未达到订单价格;
								disPrice = distancePrice.getNotupPrice();
								break;
							}else{
								disPrice = distancePrice.getUpPrice();
								break;
							}
						}
					}
				}
				//税率
				double unitpriceTax = (r.getViewArea().getGst() + r.getViewArea().getPst()) * (unitprice);//菜的税费
				double disPriceTax = (r.getViewArea().getGst() + r.getViewArea().getPst()) * (disPrice);//送餐费的税费
				tax = StringUtil.convertLastDouble(unitpriceTax + disPriceTax);
				
				omam.setDeliveryfee(StringUtil.convertLastDouble(disPrice));//送餐费
				omam.setSubtotal(StringUtil.convertLastDouble(unitprice));//菜+配菜
				omam.setTax(StringUtil.convertLastDouble(tax));//税费
				omam.setAmount(StringUtil.convertLastDouble(disPrice + unitprice + tax));//菜+税+运费
				return omam;
			}
			return omam;
		}
		return null;
	}

	/**
	 * @Title: savelineup
	 * @Description: 用户确认排队订单
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	@Override
	public int savelineup(long orderId) {
		if(orderId > 0){
			OrderHeader orderHeader = orderDao.getlineup(orderId);
			if(orderHeader != null){
				orderHeader.setStatus(3);
				return orderDao.savelineup(orderHeader);
			}
		}
		return -1;
	}

	/**
	 * @Title: CartHeaderToOrderHeader
	 * @Description: 将购物车对象转成订单对象
	 * @param:    CartHeader cartHeader
	 * @return: OrderHeader
	 */
	@Override
	public OrderHeader CartHeaderToOrderHeader(long cartHeaderId) {
		CartHeader cartHeader = cartHeaderDao.getCartHeaderById(cartHeaderId);
		if (cartHeader!=null) {
			OrderHeader orderHeader = null;
			if (cartHeader.getOrderType()==3 && cartHeader.getCartItems()!=null && cartHeader.getCartItems().size()>0) {//dine in
				if (cartHeader.getOrderId()!=null) {
					orderHeader = orderDao.getOrderById(cartHeader.getOrderId());
				}
			}
			else{
				orderHeader = new OrderHeader();
				Consumers consumers = new Consumers();
				orderHeader.setOrderType(cartHeader.getOrderType());
				orderHeader.setRestaurantUuid(cartHeader.getRestaurantUuid());
				//orderHeader.setOrderDate(cartHeader.getOrderTime());
				//orderHeader.setEmail(cartHeader.getEmail());
				//orderHeader.setMemo(cartHeader.getMemo());
				consumers.setUuid(cartHeader.getConsumerUuid());
				orderHeader.setConsumers(consumers);
				//orderHeader.setPhoneNumber(cartHeader.getPhone());
			}
			orderHeader.setTotal(cartHeader.getDishFee());

			if (cartHeader.getCartItems()!=null) {
				Set<OrderItem> orderItems = new HashSet<OrderItem>();
				for(CartItem cartItem:cartHeader.getCartItems()){
					OrderItem orderItem = new OrderItem();
					orderItem.setInstruction(cartItem.getInstruction());
					orderItem.setNum(cartItem.getNum());
					orderItem.setPrice(cartItem.getUnitprice());
					orderItem.setDishId(cartItem.getDishId());
					Set<OrderDishGarnish> orderDishGarnishs = new HashSet<OrderDishGarnish>();
					for(CartDishGarnish cartDishGarnish:cartItem.getCartDishGarnishs()){
						OrderDishGarnish orderDishGarnish = new OrderDishGarnish();
						orderDishGarnish.setGarnishNum(cartDishGarnish.getGarnishNum());
						orderDishGarnish.setGarnishItem(cartDishGarnish.getGarnishItem());
						orderDishGarnishs.add(orderDishGarnish);
					}
					orderItem.setOrderDishGarnishs(orderDishGarnishs);
					orderItems.add(orderItem);
				}
				orderHeader.setOrderItems(orderItems);
			}
			
			return orderHeader;
		}
		return null;
	}
	
	
	/**
	 * @Title: CartHeaderToOrderHeader
	 * @Description: 将购物车对象转成订单对象(web用)
	 * @param:    CartHeader cartHeader
	 * @return: OrderHeader
	 */
	@Override
	public OrderHeader CartHeaderToOrderHeaderForWeb(long cartHeaderId) {
		CartHeader cartHeader = cartHeaderDao.getCartHeaderById(cartHeaderId);
		if (cartHeader!=null) {
			OrderHeader orderHeader = null;
			if (cartHeader.getOrderType()==3 && cartHeader.getCartItems()!=null && cartHeader.getCartItems().size()>0) {//dine in
				if (cartHeader.getOrderId()!=null) {
					orderHeader = orderDao.getOrderById(cartHeader.getOrderId());
				}
				else{
					return null;
				}
			}
			else{
				orderHeader = new OrderHeader();
				Consumers consumers = new Consumers();
				orderHeader.setOrderType(cartHeader.getOrderType());
				orderHeader.setRestaurantUuid(cartHeader.getRestaurantUuid());
				orderHeader.setOrderDate(cartHeader.getOrderTime());
				orderHeader.setEmail(cartHeader.getEmail());
				orderHeader.setMemo(cartHeader.getMemo());
				consumers.setUuid(cartHeader.getConsumerUuid());
				orderHeader.setConsumers(consumers);
				orderHeader.setPhoneNumber(cartHeader.getPhone());
				orderHeader.setPeopleName(cartHeader.getPeopleName());
			}
			if (cartHeader.getOrderType()==1) {
				if (cartHeader.getLogistics()!=null) {
					orderHeader.setLogistics(cartHeader.getLogistics());
				}
				orderHeader.setAddress(cartHeader.getAddress());
				
			}	
			orderHeader.setTotal(cartHeader.getDishFee());
			orderHeader.setTax(cartHeader.getTax());
			orderHeader.setAmount(cartHeader.getTotal());
				
			if (cartHeader.getCartItems()!=null) {
				Set<OrderItem> orderItems = new HashSet<OrderItem>();
				for(CartItem cartItem:cartHeader.getCartItems()){
					OrderItem orderItem = new OrderItem();
					orderItem.setInstruction(cartItem.getInstruction());
					orderItem.setNum(cartItem.getNum());
					orderItem.setPrice(cartItem.getUnitprice());
					orderItem.setDishId(cartItem.getDishId());
					Set<OrderDishGarnish> orderDishGarnishs = new HashSet<OrderDishGarnish>();
					for(CartDishGarnish cartDishGarnish:cartItem.getCartDishGarnishs()){
						OrderDishGarnish orderDishGarnish = new OrderDishGarnish();
						orderDishGarnish.setGarnishNum(cartDishGarnish.getGarnishNum());
						orderDishGarnish.setGarnishItem(cartDishGarnish.getGarnishItem());
						orderDishGarnishs.add(orderDishGarnish);
					}
					orderItem.setOrderDishGarnishs(orderDishGarnishs);
					orderItems.add(orderItem);
				}
				orderHeader.setOrderItems(orderItems);
			}
			if(cartHeader.getDiscountId()!=null){
				orderHeader.setDiscountId(cartHeader.getDiscountId());
			}
			
			return orderHeader;
		}
		return null;
	}
	
	
	/**
	 * @Title: getPastOrderInfoByConsumerId
	 * @Description: 获取用户的订单简单信息
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PagePastOrderInfo>  
	 */
	public PageMessage getPastOrderInfoByConsumerUuid(String consumerUuid, int orderType, PageFilter pf) {
		Date localTime = consumersAddressService.getCurrentLocalTimeFromConsumersDefaultAddress(consumerUuid);
		PageMessage pm = orderDao.getPastOrderInfoByConsumerUuid(consumerUuid, orderType, localTime, pf);
		return pm;
	}

	
	
	/*public static void main(String[] args) {
		PageFilter pf = new PageFilter();
		int page = pf.getOffset();//当前页码
		int rows = pf.getLimit();//每页数量
		pf.setLimit(10);
		pf.setOffset(2);
		PageMessage pm = orderDao.getPastOrderInfoByConsumerId(54,1,pf);
	}*/
	/**
	 * @Title: getPageOrderHeaderByOrderId
	 * @Description: 通过订单Id获取一个订单的页面对象
	 * @param: @param orderId
	 * @param: @return
	 * @return PageOrderHeader  
	 */
	/*public PageOrderHeader getPageOrderHeaderByOrderId(long orderId){
		OrderHeader oh = orderDao.getOrderById(orderId);
		if(oh!=null){
			PageOrderHeader pageOrderHeader = new PageOrderHeader();
			pageOrderHeader.setId(oh.getId().intValue());//设置订单id
			pageOrderHeader.setConsumerName(consumers.getFirstName()+" "+consumers.getLastName());//设置订餐人姓名
			pageOrderHeader.setLoginname(consumers.getEmail());//设置订餐人的账户登录名
			pageOrderHeader.setPhone(consumers.getPhone());//设置订餐人电话
			pageOrderHeader.setMemo(consumers.getMemo());//设置订餐人备注信息
			pageOrderHeader.setOrderType(oh.getOrderType());//设置订单类型  1:外送 2：自取 3：到店就餐; 1:delivery 2:pick up 3dine-in
			pageOrderHeader.setCreatedate(oh.getCreatedate());//设置订单创建时间
			pageOrderHeader.setStatus(oh.getStatus());//设置订单状态
			pageOrderHeader.setOrderDate(oh.getOrderDate());//设置收货时间
			pageOrderHeader.setNumber(oh.getNumber());//设置人数
			pageOrderHeader.setAddress(oh.getAddress());//设置收货地址
			pageOrderHeader.setZipcode(oh.getZipcode());//设置邮编
			pageOrderHeader.setRejection(oh.getRejection());//设置订单中的拒绝理由
			pageOrderHeader.setTip(oh.getTip());//设置小费
			pageOrderHeader.setLogistics(oh.getLogistics());//设置送餐费
			//pageOrderHeader.setGst(oh.getGst());
			//pageOrderHeader.setPst(oh.getPst());
			//pageOrderHeader.setTotal(oh.getTotal());
			pageOrderHeader.setOrderNo(oh.getOrderNo());//设置订单号
			Set<OrderItem> orderItems = oh.getOrderItems();///获取当前订单下的所有订单条目list（菜品list）
			Set<PageOrderItem> pageOrderItems = new HashSet<PageOrderItem>();//创建用于前台显示的订单条目list
			if(orderItems.size()>0){//判断如果有订单条目，就处理订单条目
				for (OrderItem oi : orderItems) {//遍历组装订单条目，一个条目即一个菜品
					long dishId = (long)oi.getDishId();//获取菜品id
					Dish dish = dishService.getDishById(dishId+"");//通过菜品id获取菜品基本信息
					if(dish!=null){//如果能取到对应菜品就组装菜品里面的信息到前台菜品对象
						totalFee += oi.getPrice() * oi.getNum();//总价加上当前菜品价格
						PageOrderItem pageOrderItem = new PageOrderItem();//创建新的页面订单条目对象
						pageOrderItem.setOrderHeaderId(oh.getId().intValue());//设置条目所属的订单头id
						pageOrderItem.setDishId(oi.getDishId());//设置菜品id
						pageOrderItem.setChName(dish.getChName());//设置菜品中文名称
						pageOrderItem.setEnName(dish.getEnName());//设置菜品英文名称
						pageOrderItem.setInstruction(oi.getInstruction());//设置菜品特殊需求
						pageOrderItem.setNum(oi.getNum());//设置数量
						pageOrderItem.setPrice(oi.getPrice());//设置价格
						pageOrderItem.setStatus(oi.getStatus());//设置状态
						//Set<GarnishItem> garnishItemsSet = oi.getGarnishItemsSet();//获取当前菜品的配菜集合
						List<OrderDishGarnish> orderDishGarnishList = orderDishGarnishDao.getOrderDishGarnishByorderItemIdAndDishId(oi.getId().intValue(),dish.getId().intValue());
						Set<PageGarnishItem> pageGarnishItems = new HashSet<PageGarnishItem>();//创建用于前台显示的菜品配菜list
						if(orderDishGarnishList!=null){
							for (OrderDishGarnish odg : orderDishGarnishList) {//遍历组装菜品的配菜
								//通过菜品id和配菜id查找到唯一一个菜品配菜对象
								PageGarnishItem pageGarnishItem = new PageGarnishItem();//创建一个用于页面显示的配菜对象
								//pageGarnishItem.setGarnishItemId(odg.getGarnishItemId());//设置配菜的id
								//DishGarnish dishGarnish = dishGarnishService.getDishGarnishByDishIdAndGarnishItemId(dishId, odg.getGarnishItemId());
								if(dishGarnish!=null){//如果找到得配菜
									pageGarnishItem.setAddprice(dishGarnish.getAddprice());//设置菜品配菜价格
									totalFee += dishGarnish.getAddprice();//增加菜品配菜价格到总价
								}
							//	GarnishItem garnishItem = garnishItemService.getGarnishItemById(odg.getGarnishItemId());
								if(garnishItem!=null){
									pageGarnishItem.setGarnishName(garnishItem.getGarnishName());//设置配菜的名称
								}
								pageGarnishItems.add(pageGarnishItem);//添加配菜到当前配菜list
							}
						}
						pageOrderItem.setPageGarnishItemsSet(pageGarnishItems);//设置配菜list到当前订单即菜品
						pageOrderItems.add(pageOrderItem);//添加当前订单到当前订单list
			
			
		}
		return null;
	}*/
	
	/**
	 * @Title: handleOrder
	 * @Description: 订单处理（pad）
	 * @param:  orderItem
	 * @return:  1：处理成功 -1：处理失败
	 */
	@Override
	public int handleOrder(OrderItemApiModel orderItemApiModel) {
		if (orderItemApiModel != null) {
			OrderHeader orderHeader = orderDao.getOrderById(orderItemApiModel.getOrderId());
			if (orderHeader != null) {
				if(orderItemApiModel.getStatus()==7||orderHeader.getStatus()==2 || orderHeader.getStatus()==9 || orderHeader.getStatus()==10){
					orderHeader.setStatus(orderItemApiModel.getStatus());
					orderHeader.setRejection(orderItemApiModel.getInstruction());
					if(orderItemApiModel.getStatus() == 8 && StringUtil.isNotEmpty(orderHeader.getRejection())){
						String neworderDate = orderHeader.getRejection().substring(orderHeader.getRejection().indexOf(":")+1, orderHeader.getRejection().indexOf(" Queue"));
						if(neworderDate.length() < 5){
							neworderDate = "0"+neworderDate;
						}
						neworderDate = neworderDate + ":00";
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String orderDate = df.format(orderHeader.getOrderDate());
						String newDate = (orderDate.substring(0, 10)+" "+neworderDate);
						//System.out.println(newDate);
						try {
							Date date = df.parse(newDate);
							orderHeader.setOrderDate(date);
							orderDao.updateOrderHeader(orderHeader);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					int handleresult = orderDao.handleOrder(orderHeader);
					if(handleresult >0 && orderItemApiModel.getStatus() == GlobalConstant.REJECTED_ORDER){//拒绝接单
						if (orderHeader.getChargeId()!=null) {
							String refundId = CommonUtil.refundAll(orderHeader.getChargeId());
							if (StringUtil.isNotEmpty(refundId)) {//退款成功
								//orderHeader.setStatus(0);
								orderHeader.setChargeId(refundId);
								int flag = orderDao.updateOrderHeader(orderHeader);
								if (flag==1) {
									//取消订单成功，需要删除这笔订单的捐款
									flag = orderCharityService.deleteOrderCharity(orderHeader.getId().intValue());
									return 1;
								}							
							}
						}
					}else{
						return 1;
					}
				}else{
					return -2;
				}
			}else{
				return -1;
			}			
		}else{
			return -1;
		}
		return 1;
	}

	/**
	 * @Title: getOrderItemByHeader
	 * @Description: 通过订单id获取pageorderitem
	 * @param:    long orderId
	 * @return: OrderItem
	 */
	@Override
	public PageOrderHeader getOrderItemByHeader(long orderId) {
		OrderHeader orderHeader = orderDao.getOrderById(orderId);
		PageOrderHeader pageOrderHeader = new PageOrderHeader();
		if (orderHeader!=null) {
			pageOrderHeader.setConsumerName(orderHeader.getPeopleName());
			pageOrderHeader.setCreatedate(orderHeader.getCreatedate());
			pageOrderHeader.setOrderDate(orderHeader.getOrderDate());
			pageOrderHeader.setLoginname(orderHeader.getEmail());
			pageOrderHeader.setLogistics(orderHeader.getLogistics());
			pageOrderHeader.setMemo(orderHeader.getMemo());
			
			pageOrderHeader.setOrderNo(orderHeader.getOrderNo());
			pageOrderHeader.setOrderType(orderHeader.getOrderType());
			pageOrderHeader.setPhone(orderHeader.getPhoneNumber());
			pageOrderHeader.setStatus(orderHeader.getStatus());
			pageOrderHeader.setTax(orderHeader.getTax());
			pageOrderHeader.setTip(orderHeader.getTip());
			pageOrderHeader.setTotal(orderHeader.getTotal());
			pageOrderHeader.setAmount(orderHeader.getAmount());
			if(orderHeader.getOrderType()==1){//delivery
				pageOrderHeader.setAddress(orderHeader.getAddress());
			}/*else if(orderHeader.getOrderType()==2){//pickup
				
			}*/else if(orderHeader.getOrderType()==3){//reservation
				pageOrderHeader.setNumber(orderHeader.getNumber());
			}
			if(orderHeader.getOrderItems()!=null && orderHeader.getOrderItems()!=null){
				Set<OrderItem> orderItems = orderHeader.getOrderItems();//获取当前订单下的所有订单条目list（菜品list）
				List<PageOrderItem> pageOrderItems = new ArrayList<PageOrderItem>();//创建用于前台显示的订单条目list
				for (OrderItem orderItem : orderItems) {//遍历组装订单条目，一个条目即一个菜品
					Dish dish = null;
					if (orderItem.getDishId()!=null) {
						String dishId = orderItem.getDishId().toString();//获取菜品id
						dish = dishService.getDishById(dishId);//通过菜品id获取菜品基本信息
					}
					if(dish!=null){//如果能取到对应菜品就组装菜品里面的信息到前台菜品对象
						PageOrderItem pageOrderItem = new PageOrderItem();//创建新的页面订单条目对象
						pageOrderItem.setOrderHeaderId(orderHeader.getId().intValue());//设置条目所属的订单头id
						pageOrderItem.setDishId(orderItem.getDishId());//设置菜品id
						//pageOrderItem.setChName(dish.getChName());//设置菜品中文名称
						pageOrderItem.setEnName(dish.getEnName());//设置菜品英文名称
						pageOrderItem.setInstruction(orderItem.getInstruction());//设置菜品特殊需求
						pageOrderItem.setNum(orderItem.getNum());//设置数量
						pageOrderItem.setPrice(orderItem.getPrice());//设置价格
						List<OrderDishGarnish> orderDishGarnishList = orderDishGarnishDao.getOrderDishGarnishByorderItemIdAndDishId(orderItem.getId().intValue());
						Set<PageGarnishItem> pageGarnishItems = new HashSet<PageGarnishItem>();//创建用于前台显示的菜品配菜list
						if(orderDishGarnishList!=null){
							for (OrderDishGarnish odg : orderDishGarnishList) {//遍历组装菜品的配菜
								//通过菜品id和配菜id查找到唯一一个菜品配菜对象
								PageGarnishItem pageGarnishItem = new PageGarnishItem();//创建一个用于页面显示的配菜对象
								pageGarnishItem.setNum(odg.getGarnishNum());
								GarnishItem garnishItem = odg.getGarnishItem();
								if(garnishItem!=null){
									pageGarnishItem.setGarnishName(garnishItem.getGarnishName());//设置配菜的名称
								}
								pageGarnishItems.add(pageGarnishItem);
							}
						}
						pageOrderItem.setPageGarnishItemsSet(pageGarnishItems);//设置配菜list到当前订单即菜品
						pageOrderItems.add(pageOrderItem);//添加当前订单到当前订单list
					}
				}
				pageOrderHeader.setPageOrderItems(pageOrderItems);
			}
				//return pageOrderItems;
			return pageOrderHeader;
		}
		return null;
	}
	
	/**
	 * 获取用户所有订单的捐款总额
	 */
	@Override
	public double getCharityAmount(String consumerUuid) {
		return orderDao.getCharityAmount(consumerUuid);
	}
	
	/**
	 * @Title: repeatOrderMethod
	 * @Description: 网页和移动端通用抽出的重复下单的方法
	 * @param: @param orderHeader
	 * @param: @param cartHeader
	 * @param: @return
	 * @return int  
	 */
	private int repeatOrderMethod (OrderHeader orderHeader, CartHeader cartHeader){
		boolean flag = false;
		cartHeader.setOrderType(orderHeader.getOrderType());// 订单种类
		cartHeader.setConsumerUuid(orderHeader.getConsumers().getUuid());// 用户id
		cartHeader.setRestaurantUuid(orderHeader.getRestaurantUuid());// 店家id
		cartHeader.setDishFee((double)0);
		int chid = cartHeaderDao.addCartHeader(cartHeader);// 保存购车头
		if (chid != -1) {// 保存头成功
			double dishFee = 0;
			cartHeader.setId((long) chid);
			// 菜品
			Set<OrderItem> oiSet = orderHeader.getOrderItems();
			out1: for (OrderItem orderItem : oiSet) {
				if (orderItem.getPrice()!=0) {//去除赠送的菜品
					dishFee += orderItem.getPrice();
					// 返回
					CartItem cartItem = new CartItem();
					cartItem.setcartHeader(cartHeader);
					cartItem.setDishId(orderItem.getDishId());// 菜品id
					cartItem.setNum(orderItem.getNum());// 数量
					cartItem.setUnitprice(orderItem.getPrice());// 单价
					cartItem.setInstruction(orderItem.getInstruction());// 要求
					// 添加
					int ciid = cartItemDao.addCartItem(cartItem);
					if (ciid != -1) {// 增加成功
						// 配菜
						cartItem.setId((long) ciid);
						if(orderItem.getOrderDishGarnishs()!=null && orderItem.getOrderDishGarnishs().size()>0){
							Set<OrderDishGarnish> orderDishGarnishs = orderItem.getOrderDishGarnishs();
							for (OrderDishGarnish orderDishGarnish : orderDishGarnishs) {
								// 保存
								CartDishGarnish cartDishGarnish = new CartDishGarnish();
								cartDishGarnish.setCartItem(cartItem);
								GarnishItem garnishItem = new GarnishItem();
								garnishItem.setId(orderDishGarnish.getGarnishItem().getId());//
								cartDishGarnish.setGarnishItem(garnishItem);
								cartDishGarnish.setGarnishNum(orderDishGarnish.getGarnishNum());// 配菜数量
								CartDishGarnishId cartDishGarnishId = new CartDishGarnishId();
								cartDishGarnishId.setCartItemId(cartItem.getId().intValue());
								cartDishGarnishId.setGarnishItemId(orderDishGarnish.getGarnishItem().getId().intValue());
								cartDishGarnish.setId(cartDishGarnishId);
								int cdgid = cartDishGarnishDao.addCartDishGarnish(cartDishGarnish);
								if (cdgid == -1) {
									flag = false;
									break out1;
								} else {
									flag = true;
								}
							}
						}else{
							flag = true;
						}
					}else{
						flag = false;
						break out1;
					}
				}
				
			}
			cartHeader.setDishFee(dishFee);
			int i = cartHeaderDao.updateCartHeader(cartHeader);
			if (i!=1) {
				return -1;
			}
			if (flag) {// 全部成功
				return 1;
			}else{
				return -1;
			}
		}else{
			return -1;
		}
	}
	
	
	/**
	 * @Title: getUndisposedOrders
	 * @Description: 获取下单一个小时后店家尚未处理的订单 
	 * @param: @return
	 * @return List<OrderHeader>  
	 */
	@Override
	public List<OrderHeader> getUndisposedOrders(){
		return orderDao.getUndisposedOrders();
	}

	/**
	 * @Title: completeOrder
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> completeOrder(String restaurantUuid, String createdate, String orderType) {
		return orderDao.completeOrder(restaurantUuid, createdate, orderType);
	}

	/**
	 * @Title: completeOrderAll
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> completeOrderAll(String restaurantUuid, String status) {
		return orderDao.completeOrderAll(restaurantUuid, status);
	}
	
	/**
	 * @Title: getStatementAllOrders
	 * @Description: 管理员报表查看所有的订单
	 * @param: @param searchKey
	 * @param: @param pf
	 * @param: @return
	 * @return List<PageAdminStatementOrders>  
	 */
	@Override
	public List<PageAdminStatementOrders>getStatementAllOrders(String searchKey, PageFilter pf){
		return orderDao.getStatementAllOrders(searchKey, pf);
	}
	
	/**
	 * @Title: getStatementOrdersAmount
	 * @Description: //获取报表搜索出的订单的总金额
	 * @param: @return
	 * @return double  
	 */
	@Override
	public double getStatementOrdersAmount(){
		return orderDao.getStatementOrdersAmount();
	}
	
	/**
	 * @Title: getRestaurantOrders
	 * @Description: 商家获取订单报表数据加载到表格
	 * @param: @param pf
	 * @param: @param searchKey
	 * @param: @return
	 * @return PageModel  
	 */
	public List<PageRestaurantOrderStatement> getRestaurantStatement(String searchKey, PageFilter pf, String restaurantUuid){
		List<PageRestaurantOrderStatement> list = orderDao.getRestaurantStatement(searchKey, pf, restaurantUuid);
		if(list!=null &&list.size()>0){
			PageRestaurantOrderStatement pros = new PageRestaurantOrderStatement();
			int orderQuantity =0;
			double subtotal = 0.0;
			double deliveryFee = 0.0;
			double gst = 0.0;
			double tips = 0.0;
			double nommeFee = 0.0;
			double stripeFee = 0.0;
			double income = 0.0;
			for (PageRestaurantOrderStatement pr : list) {
				orderQuantity += Integer.parseInt(pr.getOrderQuantity());
				subtotal += Double.parseDouble(pr.getSubtotal());
				deliveryFee += Double.parseDouble(pr.getDeliveryFee());
				gst += Double.parseDouble(pr.getGst());
				tips += Double.parseDouble(pr.getTips());
				nommeFee += Double.parseDouble(pr.getNommeFee());
				stripeFee +=Double.parseDouble(pr.getStripeFee());
				income += Double.parseDouble(pr.getIncome());
			}
			pros.setOrderType("12");
			pros.setPaymentType("12");
			pros.setOrderQuantity(orderQuantity+"");
			pros.setSubtotal(StringUtil.twoNumberAfterDecimalPoint(Math.round(subtotal*100.0)/100.0+""));
			pros.setDeliveryFee(StringUtil.twoNumberAfterDecimalPoint(Math.round(deliveryFee*100.0)/100.0+""));
			pros.setGst(StringUtil.twoNumberAfterDecimalPoint(Math.round(gst*100.0)/100.0+""));
			pros.setTips(StringUtil.twoNumberAfterDecimalPoint(Math.round(tips*100.0)/100.0+""));
			pros.setNommeFee(StringUtil.twoNumberAfterDecimalPoint(Math.round(nommeFee*100.0)/100.0+""));
			pros.setStripeFee(StringUtil.twoNumberAfterDecimalPoint(Math.round(stripeFee*100.0)/100.0+""));
			pros.setIncome(StringUtil.twoNumberAfterDecimalPoint(Math.round(income*100.0)/100.0+""));
			list.add(pros);
		}
		return list;
	}
	
}
