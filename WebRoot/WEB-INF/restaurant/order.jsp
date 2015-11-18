<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<title>Nomme</title>
<jsp:include page="../inputcss.jsp"></jsp:include>
<jsp:include page="../inputjs.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/js/restaurant/order.js"></script>
<link href="${ctx}/css/datatimepicker/bootstrap-datetimepicker.css">
</head>

<body>

	<table id="orderTable" data-toggle="table" data-url="${ctx}/order/datatable" 
		data-striped="true" 
		data-click-to-select="true" 
		data-pagination="true"
		data-side-pagination="server" 
		data-toolbar="#custom-toolbar" 
		data-page-size="10">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="consumerName">Consumer Name</th>
				<th data-field="loginname">Email</th>
				<th data-field="phone">Phone</th>
				<th data-field="address">Address</th>
				<th data-field="orderType" data-formatter="orderTypeFormatter">Order Type</th>
				<th data-field="total" data-formatter="totalFormatter">Total</th>
				<th data-field="orderDate">Order Date</th>
				<th data-field="status" data-formatter="statusFormatter">Status</th>
				<th data-field="createdate" >Create Date</th>
			</tr>
		</thead>
	</table>
	<!-- 表格工具栏 -->
	<div id="custom-toolbar">
		<button type="button" id="dispose" class="btn btn-default mybt" title="Audit" style="float:left;">
			<i class="fa fa-legal "></i> Details
		</button>
		<div style="margin-left: 20px;margin-right: 10px;float:left;">
    		<div style="float:left;margin-right: 10px;">Search Time:</div>
		    <input size="14" type="text" id="startSearchTime" value="" readonly style="height: 32px; line-height:1em; padding-left: 20px;font-size: 16px;width:150px;">
		</div>
		
		<span style="margin-left: 10px;">Order status:</span> <select id="status" style="height: 32px;">
			<option value="">All status</option>
			<option value="0">Cancel</option>
			<option value="1">Unpaid</option>
			<option value="2">Paid</option>
			<option value="3">Accepted</option>
			<option value="4">Rejected</option>
			<option value="6">Refund</option>
			<option value="7">Complete</option>
			<option value="8">Line up</option>
			<option value="9">Cash</option>
			<option value="10">Pending</option>
		</select>
		<button type="button" id="clearCondition" class="btn btn-default mybt" title="Audit" style="width: 156px;margin-left: 10px;margin-bottom: 3px;">
			<i class="glyphicon glyphicon-remove "></i> Clear Conditions
		</button>
	</div>
	<!-- 处理订单的模态框 -->
	<div class="modal" id="orderModal" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px;">
			<div class="modal-content" style="width: 900px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" style="font-weight: 400;">Order Details</h4>
				</div>
				<div class="modal-body">
					<div id="item"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" style="width:80px;" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

</body>

</html>
