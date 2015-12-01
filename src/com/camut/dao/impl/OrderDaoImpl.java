package com.camut.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.camut.dao.OrderDao;
import com.camut.framework.constant.GlobalConstant;
import com.camut.model.OrderHeader;
import com.camut.model.api.AcceptOrderApiModel;
import com.camut.model.api.OrderHeaderId;
import com.camut.pageModel.PageAdminStatementOrders;
import com.camut.pageModel.PageFilter;
import com.camut.pageModel.PageMessage;
import com.camut.pageModel.PageOrderHeader;
import com.camut.pageModel.PagePastOrderInfo;
import com.camut.pageModel.PageRestaurantOrderStatement;
import com.camut.pageModel.PageSelectItemReservationOrder;
import com.camut.utils.StringUtil;
import com.camut.utils.DateUtil;

/**
 * @dao OrderDaoImpl.java
 * @author zhangyunfei
 * @createtime 6 05, 2015
 * @author
 * @updateTime
 * @memo
 */
@Repository
public class OrderDaoImpl extends BaseDao<OrderHeader> implements OrderDao {
	private int count;//用于保存记录条数
	
	private Double searchAmount = 0.0;//记录查询出来的数据的总销售额
	/**
	 * @Title: getCount
	 * @Description: 获取相应的记录的条数（用于分页）
	 * @param: @return
	 * @return int  
	 */
	@Override
	public int getCount(){
		return this.count;
	}
	
	/**
	 * @Title: selectPastOrder
	 * @Description: 已完成订单列表
	 * @param:  consumerId   
	 * @return: ResultApiModel
	 */
	@Override
	//@Cacheable(value="myCache",key="#consumerUuid", unless="#result == null")
	public List<OrderHeader> selectPastOrder(String consumerUuid, Date localTime) {
		String hql = "from OrderHeader oh where oh.consumers.uuid=:consumerUuid and oh.status in(0,4,6,7) and date_format(oh.orderDate,'%Y-%m-%d')>:startDate and date_format(oh.orderDate,'%Y-%m-%d')<=:endDate order by oh.orderDate desc";
		Map<String, Object> map = new HashMap<String, Object>();
		
		Date startDate = DateUtil.AddDays(localTime, -30);
		map.put("startDate", DateUtil.FormatDate(startDate, DateUtil.HqlDateFormat));
		map.put("endDate", DateUtil.FormatDate(localTime, DateUtil.HqlDateFormat));
		map.put("consumerUuid", consumerUuid);
		List<OrderHeader> ohList = this.find(hql, map);
		
		return ohList;
	}

	/**
	 * @Title: addOrder
	 * @Description: 用户订单新增
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	@Override
	public long addOrder(OrderHeader orderHeader) {
		try {			
			long a = (Long) this.save(orderHeader);
			return a;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * @Title: getOrderById
	 * @Description: 根据订单ID获取对象
	 * @param:  id
	 * @return:  OrderHeader
	 */
	@Override
	public OrderHeader getOrderById(long id) {
		String hql = "from OrderHeader oh where oh.id=:id";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);	
		OrderHeader header=	this.get(OrderHeader.class, id);
		return 	header;
	}

	/**
	 * @Title: selectRestaurantOrder
	 * @Description: 店家订单列表
	 * @param:  consumerId  orderType  createdate
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> selectRestaurantOrder(String restaurantUuid, String orderType, Date createdate) {
		String hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid";
		Map<String, Object> map = new HashMap<String, Object>();
		if(orderType != null){
			int type = Integer.parseInt(orderType);
			hql += " and oh.orderType=:type";
			map.put("type", type);
		}
		if(createdate != null){
			hql += " and oh.createdate>=:createdate";
			map.put("createdate", createdate);
		}
		map.put("restaurantUuid", restaurantUuid);
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}
 
	/**
	 * @Title: getOrder
	 * @Description: 获取菜单信息
	 * @param:  type  restaurantId  menuId
	 * @return: List<OrderMenuApiModel>
	 */
	@Override
	public OrderHeader getOrder(int type, String restaurantUuid, long menuId) {
		String hql = "from OrderHeader oh where oh.id=:menuId and oh.restaurantUuid=:restaurantUuid and oh.orderType=:type";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("restaurantUuid", restaurantUuid);
		map.put("menuId", menuId);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: getOrdersByRestaurantId
	 * @Description: 获取商家所有订单加载到表格
	 * @param: @param restaurantId
	 * @param: @param pf
	 * @return PageModel  
	 */
	public List<OrderHeader> getOrdersByRestaurantUuid(String restaurantUuid, PageFilter pf,String status, String orderDate1){
		int page = (pf.getOffset()/pf.getLimit())+1;//第几页
		int rows = pf.getLimit();//每页行数
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		String hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid";
		if(StringUtil.isNotEmpty(orderDate1)){
			//map.put("orderDate1", orderDate1);
			//hql = "from OrderHeader oh where oh.restaurantId=:restaurantId and DATE_FORMAT(oh.orderDate,'%Y-%m-%d')=:orderDate1";
			hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid and oh.orderDate like '%" + orderDate1 + "%'";
		}
		if (StringUtil.isNotEmpty(status)) {
			hql += " and oh.status="+status;
		}
		String countHql = "select count(*) "+hql;
		hql += " order by oh.orderDate desc";
		count = this.count(countHql, map).intValue();
		return this.find(hql, map, page, rows);
	}

	/**
	 * @Title: selectCurrentOrder
	 * @Description:  未完成订单列表
	 * @param:  consumerId  
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> selectCurrentOrder(String consumerUuid, Date localTime) {
		String hql = "from OrderHeader oh where oh.consumers.uuid=:consumerUuid and oh.status in(1,2,3,8,9,10) and date_format(oh.orderDate,'%Y-%m-%d')>=:dt order by oh.orderDate desc";//
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		String tablename1 = dateFormat1.format(localTime);
		map.put("dt", tablename1);
		map.put("consumerUuid", consumerUuid);
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}

	/**
	 * @Title: cancelOrder
	 * @Description:  取消订单
	 * @param:  orderId  
	 * @return: -1失败   1成功
	 */
	@Override
	public int cancelOrder(OrderHeader orderHeader) {
		try {
			this.update(orderHeader);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	/**
	 * @Title: getUnpaidReservationOrders
	 * @Description: 获取某人某商家的reservation类型的未付款且时间有效的订单
	 * @param: consumerUuid
	 * @param: restaurantUuid
	 * @param: localTime
	 * @return List<PageOrderHeader>
	 */
	@SuppressWarnings("unchecked")
	public List<PageSelectItemReservationOrder> getUnpaidReservationOrders(String consumerUuid, String restaurantUuid, Date localTime){
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localTime);
		String sqlDateFormat = "'%Y-%m-%d %T'";
		
		// Query for all accepted reservations with no items in order.
		String sql = "";
		sql += "select o.id as id, o.order_date as orderDate, o.status as status, DATE_FORMAT(o.order_date,'%Y-%m-%d %T') as strOrderDate, o.number as number, COUNT(i.id) as itemSize ";
		sql += "from dat_order_header o left join dat_order_item i on o.id=i.order_id ";
		sql += "where o.consumer_uuid=:consumerUuid ";
		sql += "and o.restaurant_uuid=:restaurantUuid " ;
		sql += "and o.order_type=" + GlobalConstant.TYPE_RESERVATION + " ";
		sql += "and o.status IN (3,10) ";
		sql += "and (DATE_FORMAT(o.order_date," + sqlDateFormat + ") > DATE_FORMAT('" + currentTime + "'," + sqlDateFormat + ")) ";
		sql += "and (SELECT(COUNT(i.id))=0) ";
		sql += "group by o.id ";
		sql += "order by o.order_date asc;";
		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("consumerUuid", consumerUuid);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setResultTransformer(Transformers.aliasToBean(PageSelectItemReservationOrder.class));
		query.addScalar("id",new org.hibernate.type.IntegerType());
		query.addScalar("orderDate",new org.hibernate.type.TimestampType());
		query.addScalar("strOrderDate",new org.hibernate.type.StringType());
		query.addScalar("number",new org.hibernate.type.IntegerType());
		query.addScalar("itemSize",new org.hibernate.type.IntegerType());
		query.addScalar("status",new org.hibernate.type.IntegerType());
		List<PageSelectItemReservationOrder> list = query.list();
		return list;
	}

	/**
	 * @Title: cancelOrder
	 * @Description: 已取消的订单列表
	 * @param:  restaurantId
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> cancelOrder(String restaurantUuid) {
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = fmt.format(date);
		String hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid and oh.status=0 and date_format(oh.orderDate,'%Y-%m-%d')=:dt";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dt", strDate);
		map.put("restaurantUuid", restaurantUuid);
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}

	/**
	 * @Title: completeOrder
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> completeOrder(String restaurantUuid, String createdate,String orderType) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid and oh.status in (7) and date_format(oh.orderDate,'%Y-%m-%d')=:dt";
		map.put("restaurantUuid", restaurantUuid);
		map.put("dt", createdate);
		if(StringUtil.isNotEmpty(orderType)){
			int type = Integer.parseInt(orderType);
			hql += " and oh.orderType=:type";
			map.put("type", type);
		}		
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}

	/**
	 * @Title: liveOrder
	 * @Description: 当天未处理的订单
	 * @param: restaurantId
	 * @param: localTime
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeaderId> liveOrder(String restaurantUuid, Date localTime) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = fmt.format(localTime);
		String sql = "select o.id as orderId from dat_order_header o "
				+ "where o.restaurant_uuid=:restaurantUuid and (o.status =2 or o.status=9 or o.status=10) and DATE_FORMAT(order_date,'%Y-%m-%d')=DATE_FORMAT(:orderDate,'%Y-%m-%d')";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setParameter("orderDate", strDate);
		query.setResultTransformer(Transformers.aliasToBean(OrderHeaderId.class));
		query.addScalar("orderId",new org.hibernate.type.LongType());
		return query.list();
	}

	/**
	 * @Title: upcomingOrder
	 * @Description: 非当天未处理的订单
	 * @param: restaurantId
	 * @param: localTime
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeaderId> upcomingOrder(String restaurantUuid, Date localTime) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = fmt.format(localTime);
		String sql = "select o.id as orderId from dat_order_header o "
				+ "where o.restaurant_uuid=:restaurantUuid and (o.status =2 or o.status=9 or o.status=10) and DATE_FORMAT(order_date,'%Y-%m-%d')>DATE_FORMAT(:orderDate,'%Y-%m-%d')";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setParameter("orderDate", strDate);
		query.setResultTransformer(Transformers.aliasToBean(OrderHeaderId.class));
		query.addScalar("orderId",new org.hibernate.type.LongType());
		return query.list();
	}

	/**
	 * @Title: acceptOrder
	 * @Description: 当天已处理的订单列表
	 * @param:  restaurantId   
	 * @param:  localTime
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<AcceptOrderApiModel> acceptOrder(String restaurantUuid, Date localTime) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = fmt.format(localTime);
		String sql = "select o.id as orderId, o.createdate as createdate, o.order_date as orderDate, o.phone_number as phone,o.order_type as orderType, "
				+ "c.firstname as firstName, c.lastname as lastName "
				+ "from dat_order_header o "
				+ "left join tbl_consumers c on c.uuid = o.consumer_uuid "
				+ "where o.restaurant_uuid=:restaurantUuid and o.status =3 and DATE_FORMAT(order_date,'%Y-%m-%d')=DATE_FORMAT(:orderDate,'%Y-%m-%d') order by o.order_date ASC";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setParameter("orderDate", strDate);
		query.setResultTransformer(Transformers.aliasToBean(AcceptOrderApiModel.class));
		query.addScalar("orderId",new org.hibernate.type.LongType());
		query.addScalar("createdate",new org.hibernate.type.TimestampType());
		query.addScalar("orderDate",new org.hibernate.type.TimestampType());
		query.addScalar("phone",new org.hibernate.type.StringType());
		query.addScalar("firstName",new org.hibernate.type.StringType());
		query.addScalar("lastName",new org.hibernate.type.StringType());
		query.addScalar("orderType", new org.hibernate.type.IntegerType());
		return query.list();
	}

	/**
	 * @Title: acceptUpcomingOrder
	 * @Description: 已处理非当天的订单列表
	 * @param:  restaurantId   
	 * @param:  localTime
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<AcceptOrderApiModel> acceptUpcomingOrder(String restaurantUuid, Date localTime) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = fmt.format(localTime);
		String sql = "select o.id as orderId, o.createdate as createdate, o.order_date as orderDate, o.phone_number as phone,o.order_type as orderType, "
				+ "c.firstname as firstName, c.lastname as lastName "
				+ "from dat_order_header o "
				+ "left join tbl_consumers c on c.uuid = o.consumer_uuid "
				+ "where o.restaurant_uuid=:restaurantUuid and o.status =3 and DATE_FORMAT(order_date,'%Y-%m-%d')>DATE_FORMAT(:orderDate,'%Y-%m-%d') order by o.order_date ASC";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setParameter("orderDate", strDate);
		query.setResultTransformer(Transformers.aliasToBean(AcceptOrderApiModel.class));
		query.addScalar("orderId",new org.hibernate.type.LongType());
		query.addScalar("createdate",new org.hibernate.type.TimestampType());
		query.addScalar("orderDate",new org.hibernate.type.TimestampType());
		query.addScalar("phone",new org.hibernate.type.StringType());
		query.addScalar("firstName",new org.hibernate.type.StringType());
		query.addScalar("lastName",new org.hibernate.type.StringType());
		query.addScalar("orderType", new org.hibernate.type.IntegerType());
		return query.list();
	}

	/**
	 * @Title: totalAmount
	 * @Description: 当天营业总金额
	 * @param:  restaurantId   
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> totalAmount(String restaurantUuid) {
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String strDate = fmt.format(date);
		String sql = "select o.total as total from dat_order_header o "
				+ "where o.restaurant_uuid=:restaurantUuid and o.status =3 and DATE_FORMAT(order_date,'%Y-%m-%d')=DATE_FORMAT(:orderDate,'%Y-%m-%d')";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setParameter("orderDate", strDate);
		query.setResultTransformer(Transformers.aliasToBean(OrderHeader.class));
		query.addScalar("total",new org.hibernate.type.DoubleType());
		return query.list();
	}

	/**
	 * @Title: updateOrderHeader
	 * @Description: 修改订单
	 * @param:    OrderHeader
	 * @return: int -1失败 1成功
	 */ 
	@Override
	public int updateOrderHeader(OrderHeader orderHeader) {
		try {
			this.update(orderHeader);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * @Title: getDineinOrder
	 * @Description: 返回商家同意的预定订单
	 * @param:    restaurantId
	 * @return: List<OrderHeader>
	 */ 
	@Override
	public List<OrderHeader> getDineinOrder(String restaurantUuid) {
		String hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid and oh.status=3";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("restaurantUuid", restaurantUuid);
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}

	/**
	 * @Title: completeOrderAll
	 * @Description: 已完成的订单列表
	 * @param:  restaurantId   
	 * @return: List<CancelOrderApiModel>
	 */
	@Override
	public List<OrderHeader> completeOrderAll(String restaurantUuid, String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = fmt.format(date);
		String hql = "from OrderHeader oh where oh.restaurantUuid=:restaurantUuid and oh.status in(7) and date_format(oh.orderDate,'%Y-%m-%d')=:dt";
		if(StringUtil.isNotEmpty(status)){
			int type = Integer.parseInt(status);
			hql += " and oh.orderType=:type";
			map.put("type", type);
		}
		map.put("dt", strDate);
		map.put("restaurantUuid", restaurantUuid);
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}

	/**
	 * @Title: getDineIn
	 * @Description: 商家已经审核的订单（预定）
	 * @param: consumerUuid
	 * @param: restaurantUuid
	 * @param: localTime
	 * @return: List<OrderHeader>
	 */
	@Override
	public List<OrderHeader> getDineIn(String consumerUuid, String restaurantUuid, Date localTime) {
		String hql = "from OrderHeader oh where oh.consumers.uuid=:consumerUuid and oh.restaurantUuid=:restaurantUuid and oh.status=3 and oh.orderType=3 and oh.orderDate > :currentDate";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("consumerUuid", consumerUuid);
		map.put("restaurantUuid", restaurantUuid);
		map.put("currentDate", localTime);
		List<OrderHeader> ohList = this.find(hql, map);
		return ohList;
	}

	/**
	 * @Title: getlineup
	 * @Description: 获取lineup订单
	 * @param:  orderHeaderId   
	 * @return: OrderHeader
	 */
	@Override
	public OrderHeader getlineup(long orderId) {
		String hql = "from OrderHeader oh where oh.id=:orderId and oh.status=8";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		return this.get(hql, map);
	}
	
	/**
	 * @Title: savelineup
	 * @Description: 用户确认排队订单
	 * @param:  orderHeader
	 * @return:  -1表示新增失败 ，a表示id 
	 */
	@Override
	public int savelineup(OrderHeader orderHeader) {
		try {
			this.update(orderHeader);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @Title: getPastOrderInfoByConsumerId
	 * @Description: 获取用户的订单简单信息
	 * @param: @param consumerId
	 * @param: @param statusType //订单状态类型 1：当前订单 CurrentOrders 2：历史订单PastOrders
	 * @param: @param pf  分页参数
	 * @param: @return
	 * @return List<PagePastOrderInfo>  
	 */
	public PageMessage getPastOrderInfoByConsumerUuid(String consumerUuid, int statusType, Date localTime, PageFilter pf){
		PageMessage pm = new PageMessage();
		SimpleDateFormat smp = new SimpleDateFormat("yyyy-MM-dd");
		String today = smp.format(localTime);//当天日期
		
		Calendar calendar=Calendar.getInstance(); 
		calendar.add(Calendar.DAY_OF_MONTH, -7);
		Date dateBeforeSevenDay =calendar.getTime();
		String todayBeforeSevenDay = smp.format(dateBeforeSevenDay);//七天前的日期
		
		String sql = "";
		if(statusType == 1){//1：当前订单 CurrentOrders
			sql = "select a.id as id, a.order_no as orderNo, a.createdate as createDate, date_format(a.order_date,'%Y-%m-%d %h:%i %p') as orderDate,"
					+ "a.amount as total, a.status as status, a.order_type as orderType, "
					+ "a.restaurant_uuid as restaurantUuid, b.restaurant_name as restaurantName " 
					+ "from dat_order_header a left join dat_restaurants b on a.restaurant_uuid=b.uuid where a.consumer_uuid=:consumerUuid " 
					+ "and (a.status=1 or a.status=2 or a.status=3 or a.status=8 or a.status=9 or a.status=10) "
					+ "and date_format(a.order_date,'%Y-%m-%d %h:%i %p')>='"+ today +"' "
					+ "order by a.createdate desc limit :beginIndex, :rows";
		}else{//2：历史订单PastOrders
			sql = "select a.id as id, a.order_no as orderNo, a.createdate as createDate, date_format(a.order_date,'%Y-%m-%d %h:%i %p') as orderDate,"
					+ "a.amount as total, a.status as status, a.order_type as orderType, "
					+ "a.restaurant_uuid as restaurantUuid, b.restaurant_name as restaurantName " 
					+ "from dat_order_header a left join dat_restaurants b on a.restaurant_uuid=b.uuid where a.consumer_uuid=:consumerUuid " 
					+ "and (a.status=0 or a.status=4 or a.status=6 or a.status=7) "
					//+ "and date_format(a.order_date,'%Y-%m-%d')<'"+ today +"' "
					+ "and date_format(a.order_date,'%Y-%m-%d')>='"+ todayBeforeSevenDay +"' "
					+ "order by a.createdate desc limit :beginIndex, :rows";
			String hql = "from OrderHeader oh where oh.consumers.id=:consumerId and oh.status in(0,4,6,7) and date_format(oh.orderDate,'%Y-%m-%d')<=:dt order by oh.orderDate desc";
		}
		int page = pf.getOffset();//当前页码
		int rows = pf.getLimit();//每页数量
		int beginIndex = (page-1)*rows;
		
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("consumerUuid", consumerUuid);
		//query.setParameter("strOrderType", strOrderType);
		query.setParameter("beginIndex", beginIndex);
		query.setParameter("rows", rows);
		query.setResultTransformer(Transformers.aliasToBean(PagePastOrderInfo.class));
		query.addScalar("id",new org.hibernate.type.IntegerType());
		query.addScalar("orderNo",new org.hibernate.type.StringType());
		query.addScalar("createDate",new org.hibernate.type.StringType());
		query.addScalar("orderDate",new org.hibernate.type.StringType());
		query.addScalar("total",new org.hibernate.type.DoubleType());
		query.addScalar("status",new org.hibernate.type.IntegerType());
		query.addScalar("orderType",new org.hibernate.type.IntegerType());
		query.addScalar("restaurantUuid",new org.hibernate.type.StringType());
		query.addScalar("restaurantName",new org.hibernate.type.StringType());
		@SuppressWarnings("unchecked")
		List<PagePastOrderInfo> list = query.list();
		
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "";
		if(statusType == 1){
			hql = "select count(*) from OrderHeader oh where oh.consumers.uuid=:consumerUuid and oh.status in(1,2,3,8,9,10) "; 
		}else{
			hql = "select count(*) from OrderHeader oh where oh.consumers.uuid=:consumerUuid and oh.status in(0,4,6,7) "; 
		}
		
		map.put("consumerUuid", consumerUuid);
		count = this.count(hql, map).intValue();
		if(count == 0){//如果没有数据，需要这是数量为1，否则页面有一个计算的分母为0 会出错
			count = 1;
		}
		pm.setList(list);
		pm.setFlag(count);
		return pm;
	}
	
	
	/**
	 * @Title: handleOrder
	 * @Description: 订单处理（pad）
	 * @param:  orderItem
	 * @return:  1：处理成功 -1：处理失败
	 */
	@Override
	public int handleOrder(OrderHeader orderHeader) {
		try {
			this.update(orderHeader);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 获取捐款金额
	 * @param consumerId
	 * @return
	 */
	@Override
	public double getCharityAmount(String consumerUuid) {
		String sql = "select sum(money) from tbl_order_charity a, dat_order_header b where a.consumer_uuid ='"+consumerUuid+"' and a.order_id = b.id and b.status = 7";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		Object object= query.uniqueResult();
		if(object!=null){
			return (double)object;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * @Title: getUndisposedOrders
	 * @Description: 获取下单一个小时后店家尚未处理的订单 
	 * @param: @return
	 * @return List<OrderHeader>  
	 */
	public List<OrderHeader> getUndisposedOrders(){
		Date nowTime = new Date();
		long afterOneHour = nowTime.getTime() - 1000*60*60;
		nowTime = new Date(afterOneHour);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String hql = "from OrderHeader oh where oh.status in(1,2,9,10) and date_format(oh.createdate, '%Y-%m-%d %H:%i') <:strTime";//date_format(oh.orderDate,'%Y-%m-%d')>=:dt"
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("orderId", orderId);
		String strTime = format.format(nowTime);
		System.out.println(strTime);
		map.put("strTime", strTime);
		return this.find(hql, map);
	}

	/**
	 * @Title: updateUndisposedOrders
	 * @Description: 将商家一个小时还未处理的订单设置为取消状态
	 * @param: @param orderList
	 * @param: @return
	 * @return int  
	 */
	public int updateUndisposedOrders(List<OrderHeader> orderList){
		int temp = 0;
		if(orderList!=null && orderList.size()>0){
			//temp = 1;
			for (OrderHeader orderHeader : orderList) {
				orderHeader.setStatus(0);
				try {
					this.update(orderHeader);
					temp++;
				} catch (Exception e) {
					// TODO: handle exception
					temp = -1;
					break;
				}
				
			}
			
		}
		return temp;
	}
	
	/*String hql = "from OrderHeader oh where oh.consumers.id=:consumerId and oh.status in(1,2,3,8,9,10) and date_format(oh.orderDate,'%Y-%m-%d')>=:dt";//
	Map<String, Object> map = new HashMap<String, Object>();
	SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
	String tablename1 = dateFormat1.format(new Date());
	map.put("dt", tablename1);*/
	
	/**
	 * @Title: getStatementAllOrders
	 * @Description: 管理员报表查看所有的订单
	 * @param: searchKey
	 * @param: pf
	 * @return List<PageAdminStatementOrders>  
	 */
	@Override
	public List<PageAdminStatementOrders> getStatementAllOrders(String searchKey, PageFilter pf){
		String startDate = "";//开始时间
		String endDate = "";//结束时间
		if(StringUtil.isNotEmpty(searchKey) && searchKey.trim().length()>3){
			String[] valueArray = searchKey.split("===");
			startDate = valueArray[0];
			endDate = valueArray[1];
		}
		
		String sql = "select a.restaurant_name as restaurantName, ";
		sql += "b.oh_order_type as orderType,";
		sql += "b.oh_payment as paymentType, ";
		sql += "b.oh_total as amount ";
		sql += "from dat_restaurants a INNER JOIN (";
		sql += "select oh.restaurant_uuid as oh_restaurantUuid, ";
		sql += "oh.order_type as oh_order_type, ";
		sql += "oh.payment as oh_payment, ";
		sql += "FORMAT(sum(ifnull(oh.total,0)), 2) as oh_total ";
		sql += "from dat_order_header oh ";
		if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
			if (startDate.equals(endDate)) {
				sql += "where DATE_FORMAT(oh.order_date,'%Y-%m-%d') = '" + startDate + "' ";
			} else {
				sql += "where DATE_FORMAT(oh.order_date,'%Y-%m-%d') >= '" + startDate
						+ "' and DATE_FORMAT(oh.order_date,'%Y-%m-%d') <= '" + endDate + "' ";
			}
		} else if (StringUtil.isNotEmpty(startDate)) {
			sql += "where DATE_FORMAT(oh.order_date,'%Y-%m-%d') = '" + startDate + "' ";
		} else if (StringUtil.isNotEmpty(endDate)) {
			sql += "where DATE_FORMAT(oh.order_date,'%Y-%m-%d') = '" + endDate + "' ";
		}
		sql += "and oh.`status`=7 ";
		sql += "GROUP BY oh.restaurant_uuid, oh.order_type, oh.payment ";
		sql += ") b ON b.oh_restaurantuuid = a.uuid ";
		
		count = this.countBySql("select count(*) from ("+sql+") c").intValue();
		
		String sql2 = "select ifnull(sum(x.amount),0) from ( "+sql+") x";
		SQLQuery query1 = this.getCurrentSession().createSQLQuery(sql2);
		//String c = query1.getQueryString();
		List listAmount = query1.list();
		searchAmount =  Double.parseDouble(listAmount.get(0).toString());
		
		if(StringUtil.isNotEmpty(pf.getSort())){
			if("orderType".equals(pf.getSort())){
				sql += "ORDER BY b.oh_order_type ";				
			}else if("paymentType".equals(pf.getSort())){
				sql += "ORDER BY b.oh_payment ";
			}
		}else{
			sql += "ORDER BY a.restaurant_name,b.oh_order_type ";
		}
		if(StringUtil.isNotEmpty(pf.getOrder())){
			sql += " "+pf.getOrder()+" ";
		}
		//sql += "ORDER BY a.restaurant_name,b.oh_order_type "
		sql += "LIMIT :offset,:rows ";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("offset", pf.getOffset());
		query.setParameter("rows", pf.getLimit());
		query.setResultTransformer(Transformers.aliasToBean(PageAdminStatementOrders.class));
		query.addScalar("restaurantName",new org.hibernate.type.StringType());
		query.addScalar("orderType",new org.hibernate.type.StringType());
		query.addScalar("paymentType",new org.hibernate.type.StringType());
		query.addScalar("amount",new org.hibernate.type.StringType()); 
		return query.list();
		
	}
	
	/**
	 * @Title: getStatementOrdersAmount
	 * @Description: //获取报表搜索出的订单的总金额
	 * @param: @return
	 * @return double  
	 */
	public double getStatementOrdersAmount(){
		return this.searchAmount;
	}
	
	/**
	 * @Title: getRestaurantOrders
	 * @Description: 商家获取订单报表数据加载到表格
	 * @param: searchKey
	 * @param: pf
	 * @param: restaurantUuid
	 * @return List<PageRestaurantOrderStatement>  
	 */
	public List<PageRestaurantOrderStatement> getRestaurantStatement(String searchKey, PageFilter pf,String restaurantUuid){
		String startDate = "";//开始时间
		String endDate = "";//结束时间
		if(StringUtil.isNotEmpty(searchKey) && searchKey.trim().length()>3){
			String[] valueArray = searchKey.split("===");
			startDate = valueArray[0];
			endDate = valueArray[1];
		}
		
		String sql = "select oh.order_type as orderType, ";
		sql += "oh.payment as paymentType, ";
		sql += "count(oh.id) as orderQuantity, ";
		sql += "SUM(oh.total) as subtotal,";
		sql += "round(sum(oh.logistics),2) as deliveryFee, ";
		sql += "sum(oh.tax) as gst, ";
		sql += "sum(oh.tip) as tips, ";
		sql += "round(sum((oh.total*1.05)*0.1),2) as nommeFee, ";
		sql += "if (oh.payment=0,0,round(sum((oh.amount*0.029)+0.3),2)) as stripeFee, ";
		sql += "sum(oh.amount)-round(sum((oh.total*1.05)*0.1),2) - if (oh.payment=0,0,round(sum((oh.amount*0.029)+0.3),2)) as income ";
		sql += "from dat_order_header oh ";
		sql += "where oh.restaurant_uuid=:restaurantUuid ";
		sql += "and oh.status=7 ";
		if(StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)){
			if(startDate.equals(endDate)){
				sql += "and DATE_FORMAT(oh.order_date,'%Y-%m-%d') = '" + startDate+"' ";
			}else{
				sql += "and DATE_FORMAT(oh.order_date,'%Y-%m-%d') >= '"+startDate+"' and DATE_FORMAT(oh.order_date,'%Y-%m-%d') <= '"+endDate+"' ";
			}
		}else if(StringUtil.isNotEmpty(startDate)){
			sql += "and DATE_FORMAT(oh.order_date,'%Y-%m-%d') = '" + startDate+"' ";
		}else if(StringUtil.isNotEmpty(endDate)){
			sql += "and DATE_FORMAT(oh.order_date,'%Y-%m-%d') = '" + endDate+"' ";
		}
		sql += "GROUP BY oh.order_type, oh.payment;";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setParameter("restaurantUuid", restaurantUuid);
		query.setResultTransformer(Transformers.aliasToBean(PageRestaurantOrderStatement.class));
		query.addScalar("orderType",new org.hibernate.type.StringType());
		query.addScalar("paymentType",new org.hibernate.type.StringType());
		query.addScalar("orderQuantity",new org.hibernate.type.StringType());
		query.addScalar("subtotal",new org.hibernate.type.StringType());
		query.addScalar("deliveryFee",new org.hibernate.type.StringType());
		query.addScalar("gst",new org.hibernate.type.StringType());
		query.addScalar("tips",new org.hibernate.type.StringType());
		query.addScalar("nommeFee",new org.hibernate.type.StringType());
		query.addScalar("stripeFee",new org.hibernate.type.StringType());
		query.addScalar("income",new org.hibernate.type.StringType());
		return query.list();
	}

	/*@Override
	public List<OrderHeader> getOrdersByRestaurantUuid(String restaurantUuid,
			PageFilter pf, String status, String orderDate) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
		
}
