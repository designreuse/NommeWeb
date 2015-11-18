<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE HTML>
<html>
  <head>
    <title>restaurantStatement.html</title>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<jsp:include page="../inputcss.jsp"></jsp:include>
	
	<script type="text/javascript" src="${ctx}/js/admin/statementRestaurant.js"></script>
	
	<link href="${ctx}/css/datatimepicker/bootstrap-datetimepicker.css">
  </head>
  
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
		    <!-- <div class="col-md-2" style="padding-left:20px;">
		    	<button type="button" id="orderByOrderType" class="btn btn-default mybt" title="Verify" style="width:136px;">
					<i class="fa fa-refresh"></i>Sort By Order Type
				</button>
		    </div>
		    <div class="col-md-2" style="padding-left:20px;">
		    	<button type="button" id="orderByPaymentType" class="btn btn-default mybt"  data-method="refresh"  style="width:116px;">
		       		<i class="fa fa-refresh"></i>Sort By Payment
				</button>
		    </div> -->
		    <div class="col-md-1" style="padding-left:0px;">
		    	<button type="button" id="searchByTime" class="btn btn-default mybt"  data-method="refresh"  style="width:86px;margin-top: 4px;">
		       		<i class="fa fa-search"></i> Search
				</button>
		    </div>
		    <div class="col-md-3" style="padding-left:10px;font-size: 18px;font-weight: bold;padding-top: 4px;">
		    	<span>
		    		Amount: $</span><span id="amount">0.00</span>
		    </div>
		    <div class="col-md-1">
		    </div>
		</div>
	</div>
  
    <div>
     	<table id="restaurantStatementTable" data-toggle="table" 
     		<%-- data-url="${ctx}/adminStatement/getStatementAllOrders" --%>
     		<c:if test="${!empty sessionScope.startDate && !empty sessionScope.endDate}">
     			data-url="${ctx}/adminStatement/getStatementAllOrders?searchKey=${sessionScope.startDate}===${sessionScope.endDate}";
     			
     		</c:if>
     		
     		data-click-to-select="true"
     		data-pagination="true"
			data-side-pagination="server"
     		data-toolbar="#order-toolbar"
     		data-page-size="10"
     		data-page-list="[10,20,50,100]">
     		<thead>
     			<tr data-height="15">
     				<!-- <th data-field="state" data-radio="true"></th> -->
     				<!-- <th data-formatter="numberFormatter" data-width="50">No.</th> -->
			        <th data-field="restaurantName" data-width="300" data-sortable="true" >Restaurant</th>
			        <th data-field="orderType" data-formatter="orderTypeFormatter" data-sortable="true" data-align="center" data-width="150">Order Type</th>
			        <th data-field="paymentType" data-formatter="paymentFormatter" data-sortable="true" data-align="center" data-width="150">Payment</th>
			        <th data-field="amount" data-formatter="moneyFormatter" data-width="100" data-align="right">Amount</th>
     			</tr>
     		</thead>
		</table>
	</div>
	<!-- <div>
		<table id="table-javascript"></table>
	</div> -->
  </body>
</html>
