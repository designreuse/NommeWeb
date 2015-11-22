package com.camut.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.camut.model.Consumers;
import com.camut.model.GarnishItem;
import com.camut.model.OrderHeader;
import com.camut.model.OrderItem;
import com.camut.model.api.CurrentPageFilter;
import com.camut.model.api.OrderListApiModel;
import com.camut.model.api.OrderMenuApiModel;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageModel;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class OrderServiceTest {

	@Autowired
	private OrderService orderService;
	
	/**
	 * @Title: selectPastOrder
	 * @Description: 用户历史订单列表
	 * @param:  consumerId  orderType
	 * @return: List<OrderListApiModel>
	 */
	//@Test
	/*public void selectPastOrder() throws Exception {
		long consumerId = 1;
		int orderType = 1;
		List<OrderListApiModel> ohamList = orderService.selectPastOrder(consumerId);
		System.out.println(ohamList.size());
		
	}*/
	
	/**
	 * @Title: addOrder
	 * @Description: 用户订单新增
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	//@Test
/*	public void addOrder() throws Exception {
		OrderHeader orderHeader = new OrderHeader();
		orderHeader.setOrderType(1);
		Consumers consumers = new Consumers();
		consumers.setId((long)21);
		orderHeader.setConsumers(consumers);
		orderHeader.setRestaurantId(10);
		orderHeader.setTotal(20);
		orderHeader.setAddress("Mon-Fayette Expressway, Brownsville, PA 15417美国");
		orderHeader.setOrderDate(new Date());
		orderHeader.setNumber(10);
		orderHeader.setZipcode("214200");
		orderHeader.setGst(0.1);
		orderHeader.setPst(0.2);
		orderHeader.setTip(1);
		orderHeader.setLogistics(4);
		Set<OrderItem> orderItems = new HashSet<OrderItem>();
		OrderItem orderItem = new OrderItem();
		orderItem.setDishId(1);
		orderItem.setNum(1);
		List<GarnishItem> GarnishItems = new ArrayList<GarnishItem>();
		for (int i = 0; i < 2; i++) {
			GarnishItem garnishItem = new GarnishItem();
			garnishItem.setId((long)1);
		}
		orderItems.add(orderItem);
		int flag = (int) orderService.addOrder(orderHeader, GarnishItems);
		if(flag == -1){
			System.out.println("添加失败");
		}
	}*/
	
	/**
	 * @Title: getOrder
	 * @Description: 获取菜单信息
	 * @param:  type  restaurantId  menuId
	 * @return: List<OrderMenuApiModel>
	 */
	//@Test
	/*public void getOrder() throws Exception {
		int type = 1;
		int restaurantId = 4;
		long menuId = 12;
		List<OrderMenuApiModel> omamList = orderService.getOrder(type, restaurantId, menuId);
		System.out.println(omamList.size());
	}*/
	
	/**
	 * @Title: getOrdersByRestaurantId
	 * @Description: 获取商家所有订单加载到表格
	 * @param: @param restaurantId
	 * @param: @param pf
	 * @return PageModel  
	 */
	//@Test
	public void getOrdersByRestaurantId() throws Exception {
		int restaurantId = 4;
		PageFilter pf = new PageFilter();
		pf.setOffset(1);
		pf.setLimit(1);
		//PageModel pm = orderService.getOrdersByRestaurantId(restaurantId, pf);
		/*if(pm != null){
			System.out.println("ok");
		}*/
	}
	
	/**
	 * @Title: OrderHeader
	 * @Description:通过id获取订单
	 * @param:  orderHeaderId
	 * @return: OrderHeader
	 */
	@Test
	public void getOrderById() throws Exception {
		long orderHeaderId = 12;
		OrderHeader orderHeader = orderService.getOrderById(orderHeaderId);
		if(orderHeader != null){
			System.out.println(orderHeader.getTotal());
		}
	}
	
	/**
	 * @Title: selectRestaurantOrder
	 * @Description: 店家订单列表
	 * @param:  restaurantId  orderType  createdate status
	 * @return: PageModel
	 */
	//@Test
	/*public void selectRestaurantOrder() throws Exception {
		int restaurantId = 4;
		int orderType = 1;
		Date createdate = new Date(); 
		int status = 1;
		CurrentPageFilter cpf = new CurrentPageFilter();
		cpf.setCurrentPageIndex(1);
		PageModel pm = orderService.selectRestaurantOrder(restaurantId, orderType, createdate, status, cpf);
		if(pm != null){
			System.out.println("ok");
		} 
	}*/
	
}
