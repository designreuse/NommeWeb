package com.camut.service.task.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.camut.dao.OrderDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.Consumers;
import com.camut.model.OrderHeader;
import com.camut.model.Restaurants;
import com.camut.service.ConsumersService;
import com.camut.service.OrderCharityService;
import com.camut.service.OrderService;
import com.camut.service.PaymentService;
import com.camut.service.RestaurantsService;
import com.camut.service.task.TaskDemoService;
import com.camut.utils.CommonUtil;
import com.camut.utils.Log4jUtil;
import com.camut.utils.MailUtil;
import com.camut.utils.PushUtil;
import com.camut.utils.StringUtil;


@Service
public class TaskDemoServiceImpl implements TaskDemoService {
	@Autowired OrderService orderService;
	@Autowired OrderDao orderDao;
	@Autowired PaymentService paymentService;
	@Autowired OrderCharityService orderCharityService;
	@Autowired ConsumersService consumersService;
	@Autowired RestaurantsService restaurantsService;

	//private long currentOrderId;
	private List<Long> orderIdList = new ArrayList<Long>();
	
	/**
	 * @Title: timerTaskOrder
	 * @Description: 定时执行 修改订单状态
	 * @param: 
	 * @return void  
	 */
	public void timerTaskOrder (){
		Timer timer = new Timer();  
		timer.schedule(new TimerTask() {
			@Override
			public  void run() {
				// TODO Auto-generated method stub
				int temp = 0;
				long currentOrderId = 0;
				if(orderIdList!=null &&orderIdList.size()>0){
					currentOrderId = orderIdList.get(0);
					
					
				}else{
					return;
				}
				if(currentOrderId==0){
					return;
				}
				OrderHeader orderHeader = orderService.getOrderById(currentOrderId);
				//1：未付款，2：已付款，9：现金付款，10：待审核 
				if(orderHeader!=null && (orderHeader.getStatus()==1 || orderHeader.getStatus()==2 || orderHeader.getStatus()==9 || orderHeader.getStatus()==10)){
					long time=  System.currentTimeMillis()- orderHeader.getCreatedate().getTime();
					if(time > 1000*60*14){
						//如果是使用stripe付款的订单 并且 没有退款,需要进行stripe退款操作
						if(StringUtil.isNotEmpty(orderHeader.getChargeId()) && orderHeader.getChargeId().indexOf("ch_")>=0){
							String refundId = "";
							try {
								refundId = CommonUtil.refundAll(orderHeader.getChargeId());//退款
								if (!StringUtil.isNotEmpty(refundId)) {
									orderHeader.setChargeId(refundId);//将退款成功码写入订单
								}
							} catch (Exception e) {
								Log4jUtil.info("订单id："+orderHeader.getId()+"，系统自动取消订单，stripe退款失败 ");
							}
						}
						
						
						//ystem.out.println("系统取消了信用卡支付的订单");
						orderHeader.setStatus(0);
						if(StringUtil.isNotEmpty(orderHeader.getRejection())){
							orderHeader.setRejection(orderHeader.getRejection()+" The order is more than 15 minutes and untreated, the system automatically cancel the order.");
						}else{
							orderHeader.setRejection("The order is more than 15 minutes and untreated, the system automatically cancel the order.");
						}
						//修改订单
						temp = orderService.updateOrder(orderHeader);
						//修改取消订单成功，需要删除这笔订单的捐款
						if(temp>0){
							temp = orderCharityService.deleteOrderCharity(orderHeader.getId().intValue());
						}
						//清除list中的当前的第一条数据
						orderIdList.remove(0);
						Log4jUtil.info("订单id："+orderHeader.getId()+" 下单超过"+GlobalConstant.TIME_CANCEL_ORDER/60000+"分钟，商家未处理，系统自动取消 ");
						//向用户发送订单自动取消的邮件
						if(StringUtil.isNotEmpty(orderHeader.getEmail())){
							MailUtil.sendMail("Order Canceled", "The order is more than 15 minutes and untreated, the system automatically cancel the order.", orderHeader.getEmail());//1:android 2:ios
						}
						Consumers c = consumersService.getConsumersByUuid(orderHeader.getConsumers().getUuid());
						//Restaurants r = restaurantsService.getRestaurantsById(orderHeader.getRestaurantId());
						//如果用户信息中有设备号，则推送自动取消的信息给用户
						if(StringUtil.isNotEmpty(c.getMobileToken())){
							PushUtil.push(null,"Nomme", "The order is more than 15 minutes and untreated, the system automatically cancel the order.", c.getMobileToken(), Integer.valueOf(c.getMobileType()));
						}
					}else{
						timerTaskOrder();
						currentOrderId=orderHeader.getId();
					}
					/*if(StringUtil.isNotEmpty(orderHeader.getChargeId())){//使用stripe付款的订单
						String refundId = CommonUtil.refundAll(orderHeader.getChargeId());//退款
						Log4jUtil.info("订单id："+orderHeader.getId()+" 下单超过"+GlobalConstant.TIME_CANCEL_ORDER/60000+"分钟，商家未处理，系统自动取消 ");
						if (StringUtil.isNotEmpty(refundId)) {
							orderHeader.setChargeId(refundId);
							long time=  System.currentTimeMillis()- orderHeader.getCreatedate().getTime();
							if(time > GlobalConstant.TIME_CANCEL_ORDER){
								System.out.println("系统取消了信用卡支付的订单");
								orderHeader.setStatus(0);
								if(StringUtil.isNotEmpty(orderHeader.getRejection())){
									orderHeader.setRejection(orderHeader.getRejection()+" The order is more than 15 minutes and untreated, the system automatically cancel the order.");
								}else{
									orderHeader.setRejection("The order is more than 15 minutes and untreated, the system automatically cancel the order.");
								}
								temp = orderService.updateOrder(orderHeader);
								if(temp>0){//取消订单成功，需要删除这笔订单的捐款
									temp = orderCharityService.deleteOrderCharity(orderHeader.getId().intValue());
								}
							}else{
								timerTaskOrder();
								currentOrderId=orderHeader.getId();
							}
							//System.out.println("退款成功 refundId："+refundId);
						}
					}else{
						long time=  System.currentTimeMillis()- orderHeader.getCreatedate().getTime();
						if(time > GlobalConstant.TIME_CANCEL_ORDER){
							orderHeader.setStatus(0);
							System.out.println("系统取消了现金支付的订单");
							//设置更新订单拒绝理由
							if(StringUtil.isNotEmpty(orderHeader.getRejection())){
								orderHeader.setRejection(orderHeader.getRejection()+" The order is more than 15 minutes and untreated, the system automatically cancel the order.");
							}else{
								orderHeader.setRejection("The order is more than 15 minutes and untreated, the system automatically cancel the order.");
							}
							temp = orderService.updateOrder(orderHeader);
							Log4jUtil.info("订单id："+orderHeader.getId()+" 下单超过"+GlobalConstant.TIME_CANCEL_ORDER/60000+"分钟，商家未处理，系统自动取消 ");
							if(temp>0){//取消订单成功，需要删除这笔订单的捐款
								temp = orderCharityService.deleteOrderCharity(orderHeader.getId().intValue());
							}
						}else{
							timerTaskOrder();
							currentOrderId=orderHeader.getId();
						}
					}*/
				}else{
					//如果订单不为空且状态不等于1：未付款，2：已付款，9：现金付款，10：待审核  其中的一个，就清除list中的当前的第一条数据
					orderIdList.remove(0);
				}
			}
		}, GlobalConstant.TIME_CANCEL_ORDER);//延迟多长时间再执行
	 }
	
	@Override
	public void pushOrderid(long currentOrderId) {
		// TODO Auto-generated method stub
		//this.currentOrderId=currentOrderId;
		this.orderIdList.add(currentOrderId);
	}
	
	
	/*@Override
	public void test() {
		//System.out.println("定时任务执行...");
		List<OrderHeader> orderList =  orderService.getUndisposedOrders();
		
		if(orderList!=null && orderList.size()>0){
			System.out.println("需要处理的订单数量："+orderList.size());
			
			System.out.print("处理的订单Id：");
			for (OrderHeader orderHeader : orderList) {
				if(StringUtil.isNotEmpty(orderHeader.getChargeId())){
					int refundTemp = paymentService.refundByOrder(orderHeader.getId());
					
				}
				System.out.print(orderHeader.getId()+",");
			}
			System.out.println("");
			
			int temp = orderDao.updateUndisposedOrders(orderList);
			System.out.println("处理过的订单数量:"+temp);
		}else{
			System.out.println("没有需要设置的订单");
		}
	}*/

}
