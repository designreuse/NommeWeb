<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../inputcss.jsp"></jsp:include>
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

.popover-content {
	color: #FA6A6A;
	width: 300px;
}

#fm input {
	border: 0px;
	height: 37px;
	width: 300px;
}

.modal-body {
	background-color: #EAEAEC;
}

label {
	margin-top: 15px;
}
</style>
</head>

<body style="margin-left: 5px;margin-right: 5px;">
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>
	
	<input type="hidden" id="storeId" value="${sessionScope.restaurantsUser.uuid}">
	<div id="custom-toolbar">
		<button type="button" name="add" class="btn btn-default glyphicon glyphicon-plus" title="add new employee"
			style="margin-left: 40px;" data-toggle="modal" data-target="#myModal">
			<span style="margin-left: 8px;">Add</span>
		</button>
		<button type="button" name="update" class="btn btn-default glyphicon glyphicon-pencil" title="modify the employee">
			<span style="margin-left: 8px;">Edit</span>
		</button>
		<button type="button" name="delete" class="btn btn-default glyphicon glyphicon-minus" title="delete the employee"
			data-placement="right">
			<span style="margin-left: 8px;">Delete</span>
		</button>

	</div>

	<table data-toggle="table" data-url="${ctx}/restaurant/getAllRestaurantUser" data-click-to-select="true"
		data-pagination="true" data-show-refresh="true" data-search="true" data-toolbar="#custom-toolbar" data-striped="true"
		data-page-list="[20, 50, 100, 200]">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="code">Employee code</th>
				<th data-field="firstName">First name</th>
				<th data-field="lastName">Last name</th>
				<th data-field="createtime">Created time</th>
				<th data-field="lastLoginTime">Last login time</th>
				<th data-field="type" data-formatter="typeForMatter">Type</th>
				<th data-field="role" data-formatter="roleForMatter">Responsibilities</th>
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
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;">Add new employee</h4>
				</div>
				<div class="modal-body">
					<form id="fm">
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Type</label>
								</div>
								<div class="col-md-9">
									<select id="addSelect" class="form-control" name="type" data-placement="right"
										style="height: 37px;width: 300px;border: 0px;">
										<option value="2" selected="selected">Employee</option>
										<option value="1">Administrator</option>

									</select>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Employee
										ID</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="code" class="form-control" placeholder="Employee ID" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">First
										name</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="firstName" class="form-control" placeholder="First name" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Last
										name</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="lastName" class="form-control" placeholder="Last name" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">password</label>
								</div>
								<div class="col-md-9">
									<input type="password" name="password" class="form-control" placeholder="Password" data-placement="right" /> <input
										type="hidden" name="uuid">
								</div>
							</div>
						</div>

						<div>
							<div class="row">
								<div class="col-md-3">
									<label style="color: #555555;font-size: 16;float: right;font-weight: lighter;">Responsibilities</label>
								</div>
								<div class="col-md-9">
									<label style="color: #555555;"> <input type="checkbox" name="role1">  Delivery
									</label> <label style="color: #555555;"> <input type="checkbox" name="role2"> Pick-up
									</label> <label style="color: #555555;"> <input type="checkbox" name="role3"> Reservation
									</label>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" style="margin-top: 0px;">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
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
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 400;">Delete Employee</h4>
				</div>
				<div class="modal-body">
					<div style="text-align:center;">
						<h3>
							<span id="attentionInfo">Do you want to delete this admin/employee?</span>
						</h3>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" name="ok" class="btn btn-primary" data-dismiss="modal" style="margin-left: 190px;">Yes</button>
					<button type="button" name="no" class="btn btn-primary" style="margin-left: 20px;width: 50px;">No</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/staffmanagement.js"></script>
</body>
</html>
