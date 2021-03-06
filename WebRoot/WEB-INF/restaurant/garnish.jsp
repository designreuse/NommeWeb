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
		<button type="button" name="add" class="btn btn-default glyphicon glyphicon-plus" title="add new garnish"
			style="margin-left: 40px;" data-toggle="modal" data-target="#myModal">Add</button>
		<button type="button" name="update" class="btn btn-default glyphicon glyphicon-pencil" title="modify the garnish">
			Edit</button>
		<button type="button" name="delete" class="btn btn-default glyphicon glyphicon-minus" title="delete the garnish"
			data-placement="right">Delete</button>
		&nbsp;&nbsp;&nbsp;&nbsp;  <select id="garnishHeaderIdQuery"
			style="height: 37px;width: 300px;border: 0px;">
			<option></option>
		</select>
	</div>

	<table data-toggle="table" data-url="${ctx}/garnish/dataTableGarnish" data-click-to-select="true" data-search="true"
		data-pagination="true" data-show-refresh="true" data-toolbar="#custom-toolbar"
		data-page-list="[5, 10, 20, 50, 100, 200]" data-striped="true">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="garnishName">Ingredient name</th>
				<th data-field="garnishHeaderName">Ingredient category name</th>
				<th data-field="showType" data-formatter="typeForMatter">Show type</th>
				<th data-field="ismust" data-formatter="mustForMatter">Required</th>
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
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;">Add new Ingredient</h4>
				</div>
				<div class="modal-body ">
					<form id="fm">
						<div class="form-group">
							<div class="row">
								<div class="col-md-4">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 15px;">Ingredient
										name</label>
								</div>
								<div class="col-md-8">
									<input type="text" name="garnishName" class="form-control" id="garnishName" 
										data-placement="right" /> <input type="hidden" name="id" id="id" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-4">
									<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Ingredient category</label>
								</div>
								<div class="col-md-8">
									<select class="form-control" name="garnishHeaderId" id="garnishHeaderId" data-placement="right"
										style="height: 37px;width: 300px;border: 0px;">
										<option></option>
									</select>
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
					<h4 class="modal-title" id="myModalLabel" style="font-size: large;">delete the item</h4>
				</div>
				<div class="modal-body">
					<div style="text-align:center;">
						<h3>
							<span id="attentionInfo">Do you want to delete this item?</span>
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
	<script type="text/javascript" src="${ctx}/framework/select2/select2.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/garnish.js"></script>
</body>
</html>
