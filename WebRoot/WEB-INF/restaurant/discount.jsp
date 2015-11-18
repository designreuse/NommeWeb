<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="bg-black">
<head>
<meta charset="UTF-8">
<jsp:include page="../inputcss.jsp"></jsp:include>
<link href="${ctx }/framework/select2/select2.css" rel="stylesheet" type="text/css" />
<style type="text/css">
#custom-toolbar button {
	width: 100px;
	height: 32px;
	margin-left: 20px;
}

button[name="refresh"] {
	margin-right: 20px;
	width: 60px;
}
</style>
</head>

<body>
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>

	<div id="custom-toolbar">
		<button type="button" name="add" class="btn btn-default glyphicon glyphicon-plus" style="margin-left: 40px;"
			data-toggle="modal" data-target="#myModal">Add</button>
		<button type="button" name="update" class="btn btn-default glyphicon glyphicon-pencil">Edit</button>
		<button type="button" name="delete" class="btn btn-default glyphicon glyphicon-minus">Delete</button>

	</div>

	<table data-toggle="table" data-url="${ctx}/discount/getAllDiscount" data-click-to-select="true" data-search="true" data-striped="true"
		data-pagination="true" data-show-refresh="true" data-toolbar="#custom-toolbar" data-page-list="[10, 20, 50, 100, 200]">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="type" data-formatter="typeFormatter">Type</th>
				<th data-field="price">Price($)</th>
				<th data-field="discount">Discount(%)</th>
				<th data-field="dishName">Dish</th>
				<th data-field="consumePrice">Order price($)</th>
				<th data-field="startTime">Start time</th>
				<th data-field="endTime">End time</th>
				<th data-field="orderType" data-formatter="orderTypeFormatter">Order type</th>
				<th data-field="content" data-formatter="contentFormatter">Content</th>
			</tr>
		</thead>
	</table>

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header" style="background-color: #3D9970; border-bottom: 4px solid #307959;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;"></h4>
				</div>
				<div class="modal-body ">
					<form id="fm">
						<div class="form-group" style="margin-bottom: 5px;">
							<div class="row">
								<div class="col-md-3" style="margin-top: 5px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Type</label>
								</div>
								<div class="col-md-9">
									<select name="type" id="type" class="form-control">
										<option></option>
										<option value="1">coupon</option>
										<option value="2">discount</option>
										<option value="3">free dish</option>
									</select>
								</div>
							</div>
						</div>

						<div class="form-group" id="displayPrice" style="display: none">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Amount</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="price" class="form-control" id="price" placeholder="$" data-placement="right" />
								</div>
							</div>
						</div>

						<div class="form-group" id="displayDiscount" style="display: none;">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Percent</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="discount" class="form-control" id="discount" placeholder="%" data-placement="right" />
								</div>
							</div>
						</div>

						<div class="form-group" id="displayType" style="margin-bottom: 5px;display: none;margin-top: 15px;">
							<div class="row">
								<div class="col-md-3" style="margin-top: 5px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Dish</label>
								</div>
								<div class="col-md-9">
									<select name="dishId" id="dishId" class="form-control">
										<option></option>
									</select>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Order Price</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="consumePrice" class="form-control" id="consumePrice" placeholder="$"
										data-placement="right" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 0px;">Start time</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="startTime" class="form-control" placeholder="Starttime" id="startTime" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 0px;">End time</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="endTime" class="form-control" placeholder="Endtime" id="endTime" />
								</div>
							</div>
						</div>
						<input type="hidden" name="id" id="id" />

						<div class="form-group" style="margin-bottom: 5px;">
							<div class="row">
								<div class="col-md-3" style="margin-top: 5px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Order type</label>
								</div>
								<div class="col-md-9">
									<select name="orderType" id="orderType" class="form-control">
										<option></option>
										<option value="1">Delivery</option>
										<option value="2">Pick-up</option>
										<option value="3">Reservation</option>
									</select>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Content</label>
								</div>
								<div class="col-md-9">
									<textarea style="resize:none;height: 80px;width: 300px;" class="form-control" placeholder="Content"
										name="content" id="content"></textarea>
								</div>
							</div>
						</div>


					</form>
				</div>
				<div class="modal-footer" style="margin-top: 0px;">
					<button type="button" id="close" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button id="sub" type="button" class="btn btn-primary" data-placement="top">&nbsp;Save&nbsp;</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"font-size:large;">delete item</h4>
				</div>
				<div class="modal-body">
					<div style="text-align:center;">
						<h3>
							<span id="attentionInfo">Sure to delete the item?</span>
						</h3>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" name="ok" class="btn btn-primary" data-dismiss="modal" style="margin-left: 190px;">Yes</button>
					<button type="button" name="no" class="btn btn-primary" data-dismiss="modal" style="margin-left: 20px;width: 50px;">No</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/framework/select2/select2.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/discount.js"></script>

</body>
</html>
