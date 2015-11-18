<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'orderDetail.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  	
  <body>
  	<div class="container" style="width:560px;">
	    <div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Order Type:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		<c:if test="${order.orderType == 1}">Delivery</c:if>
				<c:if test="${order.orderType == 2}">Pick Up</c:if>
				<c:if test="${order.orderType == 3}">Reservation/Dine in</c:if>
	    	</div>
		</div>
		
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Order Number:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		${order.orderNo}
	    	</div>
		</div>
		
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Name:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		${order.consumerName}
	    	</div>
		</div>
		
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Email:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		${order.loginname}
	    	</div>
		</div>
		
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Create Date:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		${order.createdate}
	    	</div>
		</div>
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Order Date:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" name="modal-orderDate">
	    		${order.orderDate}
	    	</div>
		</div>
		
		<c:if test="${order.orderType == 1}">
			<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Address:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" style="height: auto;">
	    		${order.address}
	    	</div>
			</div>
		
		</c:if>
		
		<c:if test="${order.orderType == 3}">
			<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		People Number:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" style="height: auto;">
	    		${order.number}
	    	</div>
			</div>
		
		</c:if>
		
		<div class="row" style="height: 15px;" ></div>
		<c:if test="${fn:length(order.pageOrderItems)<=0}">
			<div class="row">
				<div class="col-md-12" align="center" style="font-size: 16px;">No Dishs</div>
			</div>
		</c:if>
		<c:if test="${fn:length(order.pageOrderItems)>0}">
		
		<div class="row">
			<div class="col-md-12 orderModel-field-title">
				<div class="row orderModel-dishlist">
					<div class="col-md-2">
						quantity
					</div>
					<div class="col-md-4">
						Dish Name
					</div>
					<div class="col-md-4">
						Ingredients
					</div>
					<div class="col-md-2">
						Total
					</div>
				</div>
				<c:forEach items="${order.pageOrderItems}" var="orderItem" begin="0"  step="1" varStatus="status">
					<div class="zhucyemian-miaodi"></div>
					<div class="row orderModel-dishlist" style="border-bottom: 1px solid #666666;">
						<div class="col-md-2">
							${orderItem.num}
						</div>
						<div class="col-md-4" style="font-weight: bolder;">
							${orderItem.enName}
						</div>
						<div class="col-md-4 orderModel-dish-ingredientslist">
							<c:if test="${fn:length(orderItem.pageGarnishItemsSet)<=0}">
								<div class="row">
									<div class="col-md-12 " align="center">No Ingredients</div>
								</div>
							</c:if>
							<c:if test="${fn:length(orderItem.pageGarnishItemsSet)>0}">
								<c:forEach items="${orderItem.pageGarnishItemsSet}" var="subItem" begin="0"  step="1" varStatus="status">
									<div class="row">
										<div class="col-md-6 " align="center" style="padding-left: 0px;padding-right: 0px;">
											${subItem.garnishName}
										</div>
										<div class="col-md-6 " align="center" style="padding-left: 0px;padding-right: 0px;">
											${subItem.num}
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>
						<div class="col-md-2">
							<fmt:formatNumber value="${orderItem.price}" type="currency" pattern="$ 0.00"/>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		</c:if>
		<br>
		 <!-- 金额部分 -->
		<div class="row" >
			<div class="col-md-6">
				<div class="row" align="right">
					<div class="col-md-3"></div>
					<div class="col-md-3 orderModel-field-title" align="right">
			    		Subtotal: 
			    	</div>
			    	<div class="col-md-6 orderModel-field-text">
			    		<fmt:formatNumber value="${order.total}" type="currency" pattern="$ 0.00"/>
			    	</div>
				
				</div>
				
				<c:if test="${order.orderType==1}">
					<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-3 orderModel-field-title" align="right">
			    		Delivery: 
			    	</div>
			    	<div class="col-md-6 orderModel-field-text">
			    		<fmt:formatNumber value="${order.logistics}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
				</c:if>
				
			</div>
			<div class="col-md-6" >
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-3 orderModel-field-title" align="right">
			    		Tax: 
			    	</div>
			    	<div class="col-md-6 orderModel-field-text">
			    		<fmt:formatNumber value="${order.tax}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-3 orderModel-field-title" align="right">
			    		Tip: 
			    	</div>
			    	<div class="col-md-6 orderModel-field-text">
			    		<fmt:formatNumber value="${order.tip}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
			</div>
		</div>
		
		
		<div class="row">
			<div class="col-md-6">
			</div>
			<div class="col-md-6" >
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-3 orderModel-field-title" align="right"  style="font-weight: bolder;">
			    		Amount: 
			    	</div>
			    	<div class="col-md-6 orderModel-field-text">
			    		<fmt:formatNumber value="${order.amount}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
			
			</div>
			
		</div>
		<br>
		
		<div class="row green-errorMessage" id="orderModel-errorMessage" style="display:none;">
		<br>
			<div class="col-md-12" align="center">
				<span></span>
			</div>
		</div>
		
		
		
	</div>
    
  </body>
</html>
