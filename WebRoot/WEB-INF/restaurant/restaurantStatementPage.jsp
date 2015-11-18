<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'restaurantStatementPage.jsp' starting page</title>
    <link href="${ctx}/css/datatimepicker/bootstrap-datetimepicker.css">
    <jsp:include page="../inputcss.jsp"></jsp:include>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/restaurant/restaurantStatementPage.js"></script>
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <style>
  	.totalTable{
  		border: 1px solid red;
  	}
  	.totalTable-td{
  		border: 1px solid red;
  	}
  	
  </style>
  <body>
	<div id="order-toolbar">
    	<div class="row" style="width:1200px;">
    		<div class="col-md-7" style="width:550px;">
		    	<div style="margin-top: 4px;margin-left: 5px;margin-right: 5px;" class="row">
		    		<div class="col-md-2" style="font-size: 16px;font-weight: bold; padding-left:0px; padding-right:5px;width:100px;" align="right">
		    			Start Time:
		    		</div>
		    		<div class="col-md-3" style="padding: 0px;width:150px;">
				    	<input size="16" type="text" id="startSearchTime" value="" readonly style="height: 32px; line-height:1em; padding-left: 20px;font-size: 20px;width:150px;">
		    		</div>
		    		<div class="col-md-2" style="font-size: 16px;font-weight: bold; padding-left:0px; padding-right:5px;width:100px;" align="right">
		    			End Time:
		    		</div>
		    		<div class="col-md-3" style="padding: 0px;width:150px;">
			    		<input size="16" type="text" id="endSearchTime" value="" readonly class="form_datetime" style="height: 32px; line-height:1em; padding-left: 20px;font-size: 20px;width:150px; ">
		    		</div>
		    	</div>
		    </div>
		    <div class="col-md-1" style="padding-left:0px;">
		    	<button type="button" id="searchByTime" class="btn btn-default mybt"  data-method="refresh"  style="width:86px;margin-top: 4px;">
		       		<i class="fa fa-search"></i> Search
				</button>
		    </div>
		    <!-- <div class="col-md-3" style="padding-left:10px;font-size: 18px;font-weight: bold;padding-top: 4px;">
		    	<span>
		    		Amount: $</span><span id="amount">0.00</span>
		    </div> -->
		    <div class="col-md-1">
		    </div>
		</div>
	</div>
	
	<div>
     	<table id="restaurantStatementTable" data-toggle="table" 
     		<c:if test="${!empty sessionScope.startDate && !empty sessionScope.endDate}">
     			data-url="${ctx}/restaurantStatement/getRestaurantOrders?searchKey=${sessionScope.startDate}===${sessionScope.endDate}";
     		</c:if>
     		data-click-to-select="false"
     		data-pagination="false"
			data-side-pagination="server"
     		data-toolbar="#order-toolbar"
     		data-page-size="10"
     		data-page-list="[10,20,50,100]">
     		<thead>
     			<tr data-height="15">
			        <th data-field="orderType" data-formatter="orderTypeFormatter" data-align="center" data-width="80px">Order Type</th>
			        <th data-field="paymentType" data-formatter="paymentFormatter" data-align="right" data-width="80px">Payment Type</th>
     				<th data-field="orderQuantity" data-align="center" data-width="50px" data-align="right">Quantity</th>
     				<th data-field="subtotal" data-formatter="moneyFormatter" data-width="50px" data-align="right">Subtotal</th>
     				<th data-field="deliveryFee" data-formatter="moneyFormatter" data-width="50px" data-align="right">Delivery Fee</th>
     				<th data-field="gst" data-formatter="moneyFormatter" data-width="50px" data-align="right">GST</th>
     				<th data-field="tips" data-formatter="moneyFormatter" data-width="50px" data-align="right">Tips</th>
     				<th data-field="nommeFee" data-formatter="moneyFormatter" data-width="50px" data-align="right">Nomme Collected Fee</th>
     				<th data-field="stripeFee" data-formatter="moneyFormatter" data-width="50px" data-align="right">Stripe Processing Fee</th>
     				<th data-field="income" data-formatter="moneyFormatter" data-width="50px" data-align="right">Net Deposit</th>
     			</tr>
     		</thead>
		</table>
		
	</div>
	
  </body>
</html>