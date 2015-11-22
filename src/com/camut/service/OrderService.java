package com.camut.service;

import java.util.List;
import com.camut.model.OrderHeader;
import com.camut.model.api.AcceptOrderApiModel;
import com.camut.model.api.CancelOrderApiModel;
import com.camut.model.api.DineinOrderApiModel;
import com.camut.model.api.LiveOrderApiMdoel;
import com.camut.model.api.OrderAllMoneyApiModel;
import com.camut.model.api.OrderDineInApiModel;
import com.camut.model.api.OrderItemApiModel;
import com.camut.model.api.OrderListApiModel;
import com.camut.model.api.OrderMenuApiModel;
import com.camut.model.api.TotalAmountApiModel;
import com.camut.pageModel.PageAdminStatementOrders;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageModel;
import com.camut.pageModel.PageOrderHeader;
import com.camut.pageModel.PageRestaurantOrderStatement;
import com.camut.pageModel.PageSelectItemReservationOrder;

/**
 * @dao OrderServicr.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface OrderService {
	/**
	 * @Title: getCount
	 * @Description: 获取查询出的数据数量
	 * @param: @return
	 * @return int  
	 */
	public int getCount();

	/**
	 * @Title: selectPastOrder
	 * @Description: 已完成订单列表
	 * @param:  consumerId   
	 * @return: ResultApiModel
	 */
	public List<OrderListApiModel> selectPastOrder(String consumerUuid);
	
	/**
	 * @Title: addOrder
	 * @Description: 用户订单新增
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	public int addOrder(OrderHeader orderHeader);
	
	/**
	 * @Title: getOrder
	 * @Description: 获取菜单信息
	 * @param:  type  restaurantId  menuId
	 * @return: List<OrderMenuApiModel>
	 */
	public List<OrderMenuApiModel> getOrder(int type, String restaurantUuid, long menuId);

	
	/**
	 * @Title: getOrdersByRestaurantId
	 * @Description: 获取商家所有订单加载到表格
	 * @param: @param restaurantId
	 * @param: @param pf
	 * @return PageModel  
	 */
	public PageModel getOrdersByRestaurantUuid(String restaurantUuid, PageFilter pf,String status,String orderDate);

	/**
	 * @Title: OrderHeader
	 * @Description:通过id获取订单
	 * @param:  orderHeaderId
	 * @return: OrderHeader
	 */
	public OrderHeader getOrderById(long orderHeaderId);

	/**
	 * @Title: selectRestaurantOrder
	 * @Description: 店家订单列表
	 * @param:  restaurantId  orderType  createdate status
	 * @return: PageModel
	 */
	public List<CancelOrderApiModel> selectRestaurantOrder(String restaurantUuid, String orderType, String createdate);
	
	/**
	 * @Title: selectCurrentOrder
	 * @Description:  未完成订单列表
	 * @param:  consumerId  
	 * @return: List<OrderHeader>
	 */
	public List<OrderListApiModel> selectCurrentOrder(String consumerUuid);
	
	/**
	 * @Title: cancelOrder
	 * @Description:  取消订单
	 * @param:  orderId  
	 * @return: -1失败   1成功
	 */
	public int cancelOrder(long orderId);
	
	/**
	 * @Title: repeatOrder
	 * @Description:  重复下订单
	 * @param:  orderId  consumerId
	 * @return: -1失败   1成功
	 */
	public int repeatOrder(long orderId, String mobiletoken);
	
	/**
	 * @Title: repeatOrderWebUsed
	 * @Description:  web端使用的重复下订单
	 * @param:  orderId  consumerId
	 * @return: -1失败   1成功
	 */
	public int repeatOrderWebUsed(int orderId, String consumerUuid);
	
	/**
	 * @Title: addReservation
	 * @Description: 用户新增订桌 reservation
	 * @param: @param jsonResObj
	 * @return PageMessage  
	 */
	public int addReservation(String jsonResObj);
	
	/**
	 * @Title: getUnpaidReservationOrders
	 * @Description: 获取某人某商家的reservation类型的未付款且时间有效的订单
	 * @param: @param resId
	 * @param: @param conId
	 * @param: @param orderType
	 * @param: @param status
	 * @return List<PageOrderHeader>  
	 */
	public List<PageSelectItemReservationOrder> getUnpaidReservationOrders(String restaurantUuid, String consumerUuid, int orderType,long currentOrderNo);
	
	/**
	 * @Title: cancelOrder
	 * @Description: 已取消的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	public List<CancelOrderApiModel> cancelOrder(String restaurantUuid); 
	
	/**
	 * @Title: completeOrder
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	public List<CancelOrderApiModel> completeOrder(String restaurantUuid, String status);
	
	/**
	 * @Title: liveOrder
	 * @Description: 当天未处理的订单
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public LiveOrderApiMdoel liveOrder(String restaurantUuid);
	
	/**
	 * @Title: upcomingOrder
	 * @Description: 当天未处理的订单
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public LiveOrderApiMdoel upcomingOrder(String restaurantUuid);
	
	/**
	 * @Title: acceptOrder
	 * @Description: 当天已处理的订单列表
	 * @param:  restaurantId   
	 * @return: List<AcceptOrderApiModel>
	 */
	public List<AcceptOrderApiModel> acceptOrder(String restaurantUuid);
	
	/**
	 * @Title: acceptUpcomingOrder
	 * @Description: 已处理非当天的订单列表
	 * @param:  restaurantId   
	 * @return: List<AcceptOrderApiModel>
	 */
	public List<AcceptOrderApiModel> acceptUpcomingOrder(String restaurantUuid);
	
	/**
	 * @Title: totalAmount
	 * @Description: 当天营业总金额
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public TotalAmountApiModel totalAmount(String restaurantUuid);
	
	/**
	 * @Title: updateOrder
	 * @Description: 修改订单
	 * @param:    OrderHeader orderHeader
	 * @return: int -1失败 1成功
	 */
	public int updateOrder(OrderHeader orderHeader);
	
	/**
	 * @Title: getDineinOrder
	 * @Description: 返回商家同意的预定订单
	 * @param:    restaurantId
	 * @return: List<OrderHeader>
	 */ 
	public List<DineinOrderApiModel> getDineinOrder(String restaurantUuid);
	
	/**
	 * @Title: getDineIn
	 * @Description: 商家已经审核的订单（预定）
	 * @param:  restaurantId   
	 * @return: List<OrderDineInApiModel>
	 */
	public List<OrderDineInApiModel> getDineIn(String consumerUuid, String restaurantUuid);
	
	/**
	 * @Title: getOrderMoney
	 * @Description: 获取订单所有的费用
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public OrderAllMoneyApiModel getOrderMoney(String cartHeaderId, String adressId);
	
	/**
	 * @Title: savelineup
	 * @Description: 用户确认排队订单
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	public int savelineup(long orderId);
	
	/**
	 * @Title: CartHeaderToOrderHeader
	 * @Description: 将购物车对象转成订单对象（app用）
	 * @param:    CartHeader cartHeader
	 * @return: OrderHeader
	 */
	public OrderHeader CartHeaderToOrderHeader(long cartHeaderId);
	
	/**
	 * @Title: CartHeaderToOrderHeader
	 * @Description: 将购物车对象转成订单对象(web用)
	 * @param:    CartHeader cartHeader
	 * @return: OrderHeader
	 */
	public OrderHeader CartHeaderToOrderHeaderForWeb(long cartHeaderId);
	
	/**
	 * @Title: getPastOrderInfoByConsumerId
	 * @Description: 获取用户的订单简单信息
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PagePastOrderInfo>  
	 */
	public PageMessage getPastOrderInfoByConsumerUuid(String consumerUuid,int orderType, PageFilter pf);
	
	/**
	 * @Title: getPageOrderHeaderByOrderId
	 * @Description: 通过订单Id获取一个订单的页面对象
	 * @param: @param orderId
	 * @param: @return
	 * @return PageOrderHeader  
	 */
	//public PageOrderHeader getPageOrderHeaderByOrderId(long orderId);
	
	/**
	 * @Title: handleOrder
	 * @Description: 订单处理（pad）
	 * @param:  orderItem
	 * @return:  1：处理成功 -1：处理失败
	 */
	public int handleOrder(OrderItemApiModel orderItemApiModel);
	
	/**
	 * @Title: getOrderItemByHeader
	 * @Description: 通过订单id获取orderitem
	 * @param:    long orderId
	 * @return: OrderItem
	 */
	public PageOrderHeader getOrderItemByHeader(long orderId);

	
	/**
	 * @Title: getCharityAmount
	 * @Description: 获取用户捐款总额
	 * @param: @param consumerId
	 * @param: @return
	 * @return double  
	 */
	public double getCharityAmount(String consumerUuid);
	
	
	/**
	 * @Title: getUndisposedOrders
	 * @Description: 获取下单一个小时后店家尚未处理的订单 
	 * @param: @return
	 * @return List<OrderHeader>  
	 */
	public List<OrderHeader> getUndisposedOrders();
	
	/**
	 * @Title: completeOrder
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> completeOrder(String restaurantUuid, String createdate, String orderType);
	
	/**
	 * @Title: completeOrderAll
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> completeOrderAll(String restaurantUuid ,String status);
	
	/**
	 * @Title: getStatementAllOrders
	 * @Description: 管理员报表查看所有的订单
	 * @param: @param searchKey
	 * @param: @param pf
	 * @param: @return
	 * @return List<PageAdminStatementOrders>  
	 */
	public List<PageAdminStatementOrders> getStatementAllOrders(String searchKey, PageFilter pf);
	
	/**
	 * @Title: getStatementOrdersAmount
	 * @Description: //获取报表搜索出的订单的总金额
	 * @param: @return
	 * @return double  
	 */
	public double getStatementOrdersAmount();
	
	/**
	 * @Title: getRestaurantOrders
	 * @Description: 商家获取订单报表数据加载到表格
	 * @param: @param pf
	 * @param: @param searchKey
	 * @param: @return
	 * @return PageModel  
	 */
	public List<PageRestaurantOrderStatement> getRestaurantStatement(String searchKey, PageFilter pf, String restaurantUuid);
	
}
