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
label{
	margin-top: 15px;
}
</style>
</head>

<body>
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>

	<div id="custom-toolbar">
		<button type="button" name="add"
			class="btn btn-default glyphicon glyphicon-plus"
			title="add new garnishheader" style="margin-left: 40px;"
			data-toggle="modal" data-target="#myModal">Add</button>
		<button type="button" name="update"
			class="btn btn-default glyphicon glyphicon-pencil"
			title="modify the garnishheader">Edit</button>

	</div>

	<table data-toggle="table" data-url="${ctx}/garnish/dataTableGarnishHeader"
		data-click-to-select="true" data-search="true"
		data-pagination="true" data-show-refresh="true"
		data-toolbar="#custom-toolbar" data-striped="true"
		data-page-list="[5, 10, 20, 50, 100, 200]">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="garnishMenu">Ingredient category name</th>
				<th data-field="showType" data-formatter="typeForMatter">Show type</th>
				<th data-field="ismust" data-formatter="mustForMatter">Required</th>
				<th data-field="count" >Max number</th>
			</tr>
		</thead>
	</table>
	
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #3D9970; border-bottom: 4px solid #307959;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;">Add
						new Ingredient category</h4>
						
				</div>
				<div class="modal-body ">
					<form id="fm">
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 10px;">Ingredient category name</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="garnishMenu" class="form-control" id="garnishMenu"
										placeholder="name" data-placement="right" />
									<input type="hidden" name="id" id="id"/>
								</div>
							</div>
						</div>
					
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Show types</label>
								</div>
								<div class="col-md-9">
									<select  class="form-control" name="showType" id="showType" data-placement="right" style="height: 37px;width: 300px;border: 0px;">
										<option value="0" selected="selected">radio</option>
										<option value="1">checkbox</option>
										<option value="2">select</option>
									</select>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Required</label>
								</div>
								<div class="col-md-9">
									<select  class="form-control" name="ismust" id="ismust" data-placement="right" style="height: 37px;width: 300px;border: 0px;">
										<option value="1" selected="selected">yes</option>
										<option value="0">no</option>
									</select>
								</div>
							</div>
						</div>
						
						<div class="form-group" id="countDisplay" style="display: none;">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 10px;">Max number</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="count" class="form-control" id="count"
										placeholder="number" data-placement="right" value="1"/>
								</div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer" style="margin-top: 0px;">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button id="sub" type="button" class="btn btn-primary"
						data-placement="top">&nbsp;Save&nbsp;</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"
						style="color: red;font-size: large;">Do you want to delete this ingredient category?</h4>
				</div>
				<div class="modal-body">
					<div style="text-align:center;"><h3><span id="attentionInfo">Do you want to delete this ingredient category?</span></h3></div>
				</div>
				<div class="modal-footer">
					<button type="button" name="ok" class="btn btn-primary"
						style="margin-left: 190px;">Yes</button>
					<button type="button" name="no" class="btn btn-primary"
						style="margin-left: 20px;width: 50px;" data-dismiss="modal" >No</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					
				</div>
				<div class="modal-body">
					<div style="text-align:center;"><h3><span id="attentionInfo">This ingredient category has one or more ingredient,please delete ingredient first!</span></h3></div>
				</div>
				<div class="modal-footer">
					<button type="button" name="no" class="btn btn-primary"
						style="margin-left: 20px;width: 50px;" data-dismiss="modal" >Ok</button>
				</div>
			</div>
		</div>
	</div>
	
<jsp:include page="../inputjs.jsp"></jsp:include>
<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
<script type="text/javascript" src="${ctx}/js/restaurant/garnishheader.js"></script>
</body>
</html>
