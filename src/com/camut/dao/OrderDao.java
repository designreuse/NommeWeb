package com.camut.dao;

import java.util.Date;
import java.util.List;

import com.camut.model.OrderHeader;
import com.camut.model.api.AcceptOrderApiModel;
import com.camut.model.api.OrderHeaderId;
import com.camut.pageModel.PageAdminStatementOrders;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageOrderHeader;
import com.camut.pageModel.PageRestaurantOrderStatement;
import com.camut.pageModel.PageSelectItemReservationOrder;

/**
 * @dao OrderDao.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
public interface OrderDao {

	/**
	 * @Title: selectPastOrder
	 * @Description: 已完成订单列表
	 * @param:  consumerId   
	 * @return: ResultApiModel
	 */
	public List<OrderHeader> selectPastOrder(String consumerUuid, Date localTime);
	
	/**
	 * @Title: addOrder
	 * @Description: 用户订单新增
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	public long addOrder(OrderHeader orderHeader);
	
	/**
	 * @Title: getOrderById
	 * @Description: 根据订单ID获取对象
	 * @param:  id
	 * @return:  OrderHeader
	 */
	public OrderHeader getOrderById(long id);
	
	/**
	 * @Title: selectRestaurantOrder
	 * @Description: 店家订单列表
	 * @param:  consumerId  orderType  createdate
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> selectRestaurantOrder(String restaurantUuid, String orderType, Date createdate);
	
	/**
	 * @Title: getOrder
	 * @Description: 获取菜单信息
	 * @param:  type  restaurantId  menuId
	 * @return: List<OrderHeader>
	 */
	public OrderHeader getOrder(int type, String restaurantUuid, long menuId);
	

	/**
	 * @Title: getOrdersByRestaurantId
	 * @Description: 获取商家所有订单加载到表格
	 * @param: @param restaurantId
	 * @param: @param pf
	 * @return PageModel  
	 */
	public List<OrderHeader> getOrdersByRestaurantUuid(String restaurantUuid, PageFilter pf,String status,String orderDate);
	
	/**
	 * @Title: getCount
	 * @Description: 获取相应的记录的条数（用于分页）
	 * @param: @return
	 * @return int  
	 */
	public int getCount();
	
	/**
	 * @Title: selectCurrentOrder
	 * @Description:  未完成订单列表
	 * @param:  consumerId  
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> selectCurrentOrder(String consumerUuid, Date localTime);
	
	/**
	 * @Title: cancelOrder
	 * @Description:  取消订单
	 * @param:  orderId  
	 * @return: -1失败   1成功
	 */
	public int cancelOrder(OrderHeader orderHeader);
	
	/**
	 * @Title: getUnpaidReservationOrders
	 * @Description: 获取某人某商家的reservation类型的未付款且时间有效的订单
	 * @param: @param restaurantUuid
	 * @param: @param consumerUuid
	 * @param: @param orderType
	 * @param: @param currentOrderNo
	 * @param: @param localTime
	 * @return List<PageOrderHeader>  
	 */
	public List<PageSelectItemReservationOrder> getUnpaidReservationOrders(String restaurantUuid, String consumerUuid, int orderType, long currentOrderNo, Date localTime);
	
	/**
	 * @Title: cancelOrder
	 * @Description: 已取消的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> cancelOrder(String restaurantUuid);
	
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
	public List<OrderHeader> completeOrderAll(String restaurantUuid,String status);
	
	/**
	 * @Title: liveOrder
	 * @Description: 当天未处理的订单
	 * @param: restaurantId
	 * @param: localTime
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeaderId> liveOrder(String restaurantUuid, Date localTime);
	
	/**
	 * @Title: upcomingOrder
	 * @Description: 当天未处理的订单
	 * @param: restaurantId
	 * @param: localTime
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeaderId> upcomingOrder(String restaurantUuid, Date localTime);
	
	/**
	 * @Title: acceptOrder
	 * @Description: 当天已处理的订单列表
	 * @param:  restaurantId   
	 * @param:  localTime
	 * @return: List<OrderHeader>
	 */
	public List<AcceptOrderApiModel> acceptOrder(String restaurantUuid, Date localTime);
	
	/**
	 * @Title: acceptUpcomingOrder
	 * @Description: 已处理非当天的订单列表
	 * @param:  restaurantId   
	 * @param:  localTime
	 * @return: List<OrderHeader>
	 */
	public List<AcceptOrderApiModel> acceptUpcomingOrder(String restaurantUuid, Date localTime);
	
	/**
	 * @Title: totalAmount
	 * @Description: 当天营业总金额
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> totalAmount(String restaurantUuid);
	
	/**
	 * @Title: updateOrderHeader
	 * @Description: 修改订单
	 * @param:    OrderHeader
	 * @return: int -1失败 1成功
	 */ 
	public int updateOrderHeader(OrderHeader orderHeader);
	
	/**
	 * @Title: getDineinOrder
	 * @Description: 返回商家同意的预定订单
	 * @param:    restaurantId
	 * @return: List<OrderHeader>
	 */ 
	public List<OrderHeader> getDineinOrder(String restaurantUuid);
	
	/**
	 * @Title: getDineIn
	 * @Description: 商家已经审核的订单（预定）
	 * @param: consumerUuid
	 * @param: restaurantUuid
	 * @param: localTime
	 * @return: List<OrderHeader>
	 */
	public List<OrderHeader> getDineIn(String consumerUuid, String restaurantUuid, Date localTime);
	
	/**
	 * @Title: getlineup
	 * @Description: 获取lineup订单
	 * @param:  orderHeaderId   
	 * @return: OrderHeader
	 */
	public OrderHeader getlineup(long orderId);
	
	/**
	 * @Title: savelineup
	 * @Description: 用户确认排队订单
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	public int savelineup(OrderHeader orderHeader);
	
	/**
	 * @Title: getPastOrderInfoByConsumerId
	 * @Description: 获取用户的所有订单简单信息
	 * @param: @param consumerId
	 * @param: @return
	 * @return List<PagePastOrderInfo>  
	 */
	public PageMessage getPastOrderInfoByConsumerUuid(String consumerUuid, int statusType, Date localTime, PageFilter pf);
	
	/**
	 * @Title: handleOrder
	 * @Description: 订单处理（pad）
	 * @param:  orderItem
	 * @return: 1：处理成功 -1：处理失败
	 */
	public int handleOrder(OrderHeader orderHeader);
	
	/**
	 * 获取捐款金额
	 * @param consumerId
	 * @return
	 */
	public double getCharityAmount(String consumerUuid) ;
	
	/**
	 * @Title: getUndisposedOrders
	 * @Description: 获取下单一个小时后店家尚未处理的订单 
	 * @param: @return
	 * @return List<OrderHeader>  
	 */
	public List<OrderHeader> getUndisposedOrders();
	
	/**
	 * @Title: updateUndisposedOrders
	 * @Description: 将商家一个小时还未处理的订单设置为取消状态
	 * @param: @param orderList
	 * @param: @return
	 * @return int  
	 */
	public int updateUndisposedOrders(List<OrderHeader> orderList);
	
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
