<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML >
<html>
  <head>
  </head>
  
  <body>
	  	<div  class="tab-pane" ><!-- style="margin-top: 500px;" -->
			<div align="center" class="rest-miaodi rest-miaodi-gao"></div>
			<div style="margin-left: 10px;margin-right: 10px;">
		  	<div class="row" align="center">
		  		<!-- <div class="col-md-3"><strong>Order</div> -->
		  		<div class="col-md-2"><strong>Restaurant</strong></div>
		  		<div class="col-md-2"><strong>Order Type</strong></div>
		  		<div class="col-md-3"><strong>Order Date</strong></div>
		  		<div class="col-md-1"><strong>Status</strong></div>
		  		<div class="col-md-2"><strong>Total</strong></div>
		  		<div class="col-md-2">
		  			<!-- <button class="btn btn-success-hui btn-block" name="orderDetail">Order Details</button> -->
		  			<strong>Details</strong>
		  		</div>
		  		
		  	</div>
		  	<!-- <div class="zhucyemian-miaodi"></div> -->
		  	<c:if test="${fn:length(ordersInfo.list)<=0}">
				<input type="hidden" name="orderType" value="0">
				<div class="zhucyemian-miaodi"></div>
				<div class="row">
					<div class="col-md-12" align="center">
						Empty
					</div>
					
				</div>
				<div class="zhucyemian-miaodi"></div>
			</c:if>
			<c:if test="${fn:length(ordersInfo.list)>0}">
				<c:forEach items="${ordersInfo.list}" var="order" begin="0"  step="1" varStatus="status">
				<div class="zhucyemian-miaodi"></div>
					<div class="row" align="center">
				  		<%-- <div class="col-md-3">${order.orderNo}</div> --%>
				  		<div class="col-md-2" >${order.restaurantName}</div>
				  		<div class="col-md-2">
				  			<c:if test="${order.orderType == 1}">Delivery</c:if>
				  			<c:if test="${order.orderType == 2}">Pick Up</c:if>
				  			<c:if test="${order.orderType == 3}">Reservation/Dine in</c:if>
				  		</div>
				  		<div class="col-md-3">
				  			${order.orderDate}
				  		</div>
				  		<div class="col-md-1">
				  			<div name ="orderStatus" value="${order.status}">
				  				<c:if test="${order.status == 0}">Cancelled</c:if>
				  				<c:if test="${order.status == 1}">UnPaid</c:if>
				  				<c:if test="${order.status == 2}">Paid</c:if>
				  				<c:if test="${order.status == 3}">Accepted</c:if>
				  				<c:if test="${order.status == 4}">Rejected</c:if>
				  				<%-- <c:if test="${order.status == 5}">Chargeback</c:if> --%>
				  				<c:if test="${order.status == 6}">Refund</c:if>
				  				<c:if test="${order.status == 7}">Completed</c:if>
				  				<c:if test="${order.status == 8}">Line up </c:if>
				  				<c:if test="${order.status == 9}">Pay Cash</c:if>
				  				<c:if test="${order.status == 10}">Pending</c:if>
				  			</div>
				  		</div>
				  		
				  		<div class="col-md-2">
				  			$ <fmt:formatNumber value="${order.total}" type="currency" pattern="#0.00"/>
				  		</div>
				  		<div class="col-md-2">
				  			<button class="btn btn-success-hui btn-block" name="orderDetail" value="${order.id}">Order Details</button>
				  		</div>
				  	</div>
				</c:forEach>
			</c:if>
			</div>	
		</div>
  		
  		<div class="row">
  			<div class="col-md-11">
		  		<div id="paging" style="float:right;"></div>
  			</div>
  			<div class="col-md-11"></div>
  		</div>
  		<input type="hidden" value="${ordersInfo.flag}" id="total1">
  </body>
</html>
