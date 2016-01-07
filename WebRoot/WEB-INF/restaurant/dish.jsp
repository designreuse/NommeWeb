<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
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
		<button type="button" name="add" class="btn btn-default glyphicon glyphicon-plus" title="add new garnishheader"
			style="margin-left: 40px;" data-toggle="modal" data-target="#myModal"> Add</button>
		<button type="button" name="update" class="btn btn-default glyphicon glyphicon-pencil"
			title="modify the garnishheader"> Edit</button>
		<button type="button" name="delete" class="btn btn-default glyphicon glyphicon-minus"> Active</button>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <select id="dishMenuQuery"
			style="height: 37px;width: 300px;border: 0px;">
			<option></option>
		</select>
	</div>

	<table data-toggle="table" data-url="${ctx}/dish/getAllDish" data-click-to-select="true" data-search="true" data-striped="true"
		data-pagination="true" data-show-refresh="true" data-toolbar="#custom-toolbar" data-page-list="[10, 20, 50, 100, 200]"
		>
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="enName">English name</th>
				<th data-field="chName">Second name</th>
				<th data-field="dishMenuName"  data-sortable="true">Dish category</th>
				<th data-field="createdate">Created date</th>
				<th data-field="price">Price</th>
				<th data-field="spicy" data-formatter="spicyForMatter">Spicy</th>
				<th data-field="isMsg" data-formatter="msgForMatter">MSG</th>
				<th data-field="isStarch" data-formatter="isStarchForMatter">Gluten</th>
				<!-- <th data-field="isPickup" data-formatter="roleForMatter">Type</th> -->
				<th data-field="status" data-formatter="shelvesForMatter">Active</th>
				<th data-field="photoUrl" data-formatter="photoForMatter">Photo</th>
			</tr>
		</thead>
	</table>
	
	<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="font-size: large;">Dish active</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Active</label>
								</div>
								<div class="col-md-9">
									<select name="status" id="status" class="form-control"
										data-placement="bottom">
										<option value="0">Yes</option>
										<option value="1">No</option>
									</select>
								</div>
							</div>
						</div>
						
						<div id="display" style="display: none;">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
								</div>
								<div class="col-md-9">
									<span style="color: red;">This item is used as a free dish in the coupon/discount. Please delete or edit the coupon/discount before making this item inactive.
									</span>
								</div>
							</div>
						</div>
				</div>
				<div class="modal-footer">
					<button type="button" name="no" class="btn btn-default" style="margin-left: 180px;width: 70px;"  data-dismiss="modal">Cancel</button>
					<button type="button" name="ok" class="btn btn-primary" style="margin-left: 20px;width: 70px;" disabled="true">Save</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/framework/select2/select2.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/dish.js"></script>
</body>
</html>
