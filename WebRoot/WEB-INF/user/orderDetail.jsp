<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
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
  	
  		<input type="hidden" value="${order.orderType}" name="modal-orderType">
  		<input type="hidden" value="${order.orderId}" name="modal-orderId">
  		<input type="hidden" value="${order.restaurantUuid}" name="modal-restaurantUuid">
  		<input type="hidden" value="${haveEvaluate}" name="modal-haveEvaluate">
	    <div class="row">
	    	<div class="col-md-3 orderModel-field-title">
	    		Order from:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		${order.restaurantName}
	    	</div>
	    </div>
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
	    		Created time:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text">
	    		${order.createdate}
	    	</div>
		</div>
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Service time:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" name="modal-orderDate">
	    		${order.orderDate}
	    		<input type="hidden" id = "orderDetail-orderDate" value="${order.orderDateStr}">
	    	</div>
		</div>
		<c:if test="${order.total > 0}">
			<div class="row">
				<div class="col-md-3 orderModel-field-title">
		    		Payment:
		    	</div>
		    	<div class="col-md-9 orderModel-field-text" name="modal-orderDate">
		    		${order.payment==0 ? ' Cash':' Credit Card'}
		    	</div>
			</div>
		</c:if>
		
		<%-- <c:if test="${order.orderType == 3}">
			<div class="row">
				<div class="col-md-3 orderModel-field-title">
		    		People Number:
		    	</div>
		    	<div class="col-md-9 orderModel-field-text" name="modal-orderDate">
		    		${order.orderDate}
		    	</div>
			</div>
		</c:if> --%>
		<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Address:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" style="height: auto;">
	    		<c:if test="${order.orderType == 1}">${order.consumersAddress}</c:if><!-- 外卖 -->
				<c:if test="${order.orderType == 2}">${order.restaurantAddress}</c:if><!-- 自取 -->
				<c:if test="${order.orderType == 3}">${order.restaurantAddress}</c:if><!-- 堂食 -->
	    	</div>
		</div>
		<c:if test="${! empty order.intruction}">
			<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Intruction:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" style="height: auto; word-wrap:break-word;word-break:break-all;">
	    		${order.intruction}
	    	</div>
		</div>
		</c:if>
		<c:if test="${! empty order.rejection}">
			<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Rejection:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" style="height: auto; color:#7FBF2D;">
	    		${order.rejection}
	    	</div>
		</div>
		</c:if>
		
		<c:if test="${! empty order.discountText}">
			<div class="row">
			<div class="col-md-3 orderModel-field-title">
	    		Coupon:
	    	</div>
	    	<div class="col-md-9 orderModel-field-text" style="height: auto;">
	    		${order.discountText}
	    	</div>
		</div>
		</c:if>
		
		<!-- <div class="zhucyemian-miaodi"></div> -->
		<div class="row" style="height: 15px;" ></div>
		<c:if test="${fn:length(order.item)<=0}">
			<div class="row">
				<div class="col-md-12" align="center" style="font-size: 16px;">No Dishes</div>
			</div>
		</c:if>
		<c:if test="${fn:length(order.item)>0}">
		<!-- <div class="row">
			<div class="col-md-3 orderModel-field-title">
				Dishs:
			</div>
	    	<div class="col-md-9 orderModel-field-text"></div>
		</div> -->
		
		<div class="row">
			<div class="col-md-12 orderModel-field-title">
				<div class="row orderModel-dishlist">
					<div class="col-md-2" style="padding-left:0px;padding-right: 0px;">
						Qty Dish
					</div>
					<div class="col-md-2">
						Items
					</div>
					<div class="col-md-3">
						Ingredients
					</div>
					<div class="col-md-3">
						Instruction
					</div>
					<div class="col-md-2">
						Total
					</div>
				</div>
				<c:forEach items="${order.item}" var="cartItem" begin="0"  step="1" varStatus="status">
					<div class="zhucyemian-miaodi"></div>
					<div class="row orderModel-dishlist">
						<div class="col-md-2">
							${cartItem.num}
						</div>
						<div class="col-md-2">
							${cartItem.dishName}
						</div>
						<div class="col-md-3 orderModel-dish-ingredientslist">
							<c:if test="${fn:length(cartItem.subItem)<=0}">
								<div class="row">
									<div class="col-md-12 " align="center">&nbsp;</div>
								</div>
							</c:if>
							<c:if test="${fn:length(cartItem.subItem)>0}">
								<c:forEach items="${cartItem.subItem}" var="subItem" begin="0"  step="1" varStatus="status">
									<div class="row">
										<div class="col-md-6 " align="center" style="padding-left: 0px;padding-right: 0px;">
											${subItem.garnishName}
										</div>
										<div class="col-md-6 " align="center" style="padding-left: 0px;padding-right: 0px;">
											${subItem.count}
										</div>
									</div>
								</c:forEach>
							</c:if>
						</div>
						<div class="col-md-3 orderModel-dish-ingredientslist " style="word-wrap:break-word;word-break:break-all;">
							${cartItem.instruction}
						</div>
						<div class="col-md-2" align="right" style="text-align: right; padding-right: 20px;">
							<fmt:formatNumber value="${cartItem.unitprice}" type="currency" pattern="$ 0.00"/>
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
					<div class="col-md-4 orderModel-field-title" align="right">
			    		Subtotal: 
			    	</div>
			    	<div class="col-md-5 orderModel-field-text" align="right" style="text-align: right; padding-right: 20px;">
			    		<fmt:formatNumber value="${order.total}" type="currency" pattern="$ 0.00"/>
			    	</div>
				
				</div>
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-4 orderModel-field-title" align="right">
			    		Delivery: 	
			    	</div>
			    	<div class="col-md-5 orderModel-field-text" align="right" style="text-align: right; padding-right: 20px;">
			    		<fmt:formatNumber value="${order.deliveryfee}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
				
			</div>
			<div class="col-md-6" >
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-4 orderModel-field-title" align="right">
			    		Tax: 
			    	</div>
			    	<div class="col-md-5 orderModel-field-text" align="right" style="text-align: right; padding-right: 20px;">
			    		<fmt:formatNumber value="${order.tax}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-4 orderModel-field-title" align="right">
			    		Tip: 
			    	</div>
			    	<div class="col-md-5 orderModel-field-text" align="right" style="text-align: right; padding-right: 20px;">
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
					<div class="col-md-4 orderModel-field-title" align="right">
			    		Total: 
			    	</div>
			    	<div class="col-md-5 orderModel-field-text" align="right" style="text-align: right; padding-right: 20px;">
			    		<fmt:formatNumber value="${order.amount}" type="currency" pattern="$ 0.00"/>
			    	</div>
				</div>
			
			</div>
			
		</div>
		<br>
		<div class="row">
			<c:if test="${order.status==1}"><!-- 1:未付款 -->
				<div class="col-md-6" align="center">
					<!-- <button class="btn btn-success-hui btn-lg btn-block" name="modal-repay">Repay</button> -->
				</div>
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel">Close</button>
				</div>
			</c:if>
			<c:if test="${order.status==2}"><!--2:已付款  -->
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-refund" id="modal-refund">Cancel Order</button>
				</div>
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel">Close</button>
				</div>
			</c:if>
			<c:if test="${order.status==3}"><!--3：商家已接单  操作： 退款并取消订单  退出 -->
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-refund">Cancel Order</button>
				</div>
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel">Close</button>
				</div>
			</c:if>
			<c:if test="${order.status==7}"><!-- 7：完成的订单 -->
				<c:if test="${haveEvaluate==0}">
					<div class="col-md-6" align="center">
						<c:if test="${order.total > 0}">
							<button class="btn btn-success-hui btn-lg btn-block" name="modal-repeat">Repeat</button>
						</c:if>
					</div>
					<div class="col-md-6" align="center">
						<button class="btn btn-success-hui btn-lg btn-block" name="modal-rating">Review</button>
					</div>
				</c:if>
				<c:if test="${haveEvaluate!=0}"><!-- 该订单已经有评论了 -->
					<div class="col-md-6" align="center">
						<c:if test="${order.total>0}">
							<button class="btn btn-success-hui btn-lg btn-block" name="modal-repeat">Repeat</button>
						</c:if>
					</div>
					<div class="col-md-6" align="center">
						<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel">Close</button>
					</div>
				</c:if>
			</c:if>
			<c:if test="${order.status==8}"><!-- 8:Line up  确认排队Line up订单  和 取消 -->
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-confirm-lineup">Confirm</button>
				</div>
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel-lineup">Cancel</button>
				</div>
			</c:if>
			<c:if test="${order.status==9 || order.status==10}"><!--  9：现金付款 10:待审核 -->
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-refund">Cancel Order</button>
				</div>
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel">Close</button>
				</div>
			</c:if>
			<!-- 0:订单取消状态  4:拒绝接单  6:已退款-->
			<c:if test="${order.status==0 || order.status==4 || order.status==6}">
				<div class="col-md-6" align="center">
				</div>
				<div class="col-md-6" align="center">
					<button class="btn btn-success-hui btn-lg btn-block" name="modal-cancel">Close</button>
				</div>
			</c:if>
		</div>
		
		<div class="row green-errorMessage" id="orderModel-errorMessage" style="display:none;">
		<br>
			<div class="col-md-12" align="center">
				<span></span>
			</div>
		</div>
		
		
		
	
	
    
  </body>
</html>
