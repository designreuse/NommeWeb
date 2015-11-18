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
</style>
</head>

<body>
	<div id="custom-toolbar">
		<button type="button" name="add" class="btn btn-default glyphicon glyphicon-plus" title="add new garnishheader"
			style="margin-left: 40px;" data-toggle="modal" data-target="#myModal">Add</button>
		<button type="button" name="update" class="btn btn-default glyphicon glyphicon-pencil"
			title="modify the garnishheader">Edit</button>

	</div>

	<table data-toggle="table" data-url="${ctx}/dish/getAllDishMenu" data-click-to-select="true" data-search="true" data-striped="true"
		data-pagination="true" data-show-refresh="true" data-toolbar="#custom-toolbar" data-page-list="[10, 20, 50, 100, 200]">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="menuName">Dish category</th>
				<th data-field="describe">Dish describe</th>
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
						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Dish
										category</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="menuName" class="form-control" id="menuName" 
										data-placement="right" /> <input type="hidden" name="id" id="id" />
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Description</label>
								</div>
								<div class="col-md-9">
									<!-- <input type="text" name="menuName" class="form-control" id="menuName" placeholder="Dish category"
										data-placement="right" /> -->
									<textarea name="describe" id="describe" style="resize:none;width:300px;height: 100px;" ></textarea>
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
					<h4 class="modal-title" id="myModalLabel" font-size:large;">delete item</h4>
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
					<button type="button" name="no" class="btn btn-primary" style="margin-left: 20px;width: 50px;" data-dismiss="modal">No</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>

				</div>
				<div class="modal-body">
					<div style="text-align:center;">
						<h3>
							<span id="attentionInfo">This dish category has one or more dish,please delete dish first!</span>
						</h3>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" name="no" class="btn btn-primary" style="margin-left: 20px;width: 50px;" data-dismiss="modal">Ok</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/dishmenu.js"></script>
</body>
</html>
