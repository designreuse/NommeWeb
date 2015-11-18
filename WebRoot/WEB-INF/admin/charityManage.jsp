<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'charityManage.jsp' starting page</title>

	<jsp:include page="../inputcss.jsp"></jsp:include>
  </head>
  
  <body>
    <div id="toolbar">
		<button type="button" id="addCharity" class="btn btn-default mybt"
			data-toggle="modal" data-target="#charityModal" title="New Chain">
			<i class="fa fa-plus "></i> Add
		</button>
		<button type="button" id="editCharity" class="btn btn-default mybt"
			>
			<i class="fa fa-pencil "></i> Edit
		</button>
		<button type="button" id="deleteCharity" class="btn btn-default mybt"
			>
			<i class="fa fa-minus"></i> Delete
		</button>
		<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh"
			title="refresh">
        	<i class="fa fa-refresh"></i> Refresh
   		</button>
	</div>
	
	
    <div>
     	<table id="charityTable" data-toggle="table" 
     		data-url="${ctx}/admin/getCharityList"
     		data-click-to-select="true"
     		data-pagination="true"
			data-side-pagination="server"
     		data-toolbar="#toolbar"
     		data-page-size="10"
     		data-page-list="[10,20,50,100]">
     		<thead>
     			<tr data-height="15">
     				<th data-field="state" data-radio="true"></th>
			        <th data-field="charityName" data-width="300" data-sortable="true" >Charity Name</th>
			        <th data-field="phone" data-sortable="true" data-align="center" data-width="150">Phone</th>
			        <th data-field="address" data-sortable="true" data-align="center" data-width="150">Address</th>
			        <th data-field="status" data-formatter="statusFormatter" data-width="100" data-align="right">Status</th>
			        <th data-field="description"data-width="100" data-align="right">Description</th>
     			</tr>
     		</thead>
		</table>
	</div>
	
    <table id="table-chain"  data-toolbar="#custom-toolbar">
	    <!-- <thead>
	        <tr>
	            <th data-field="state" data-checkbox="true"></th>
	            <th data-field="id" data-align="right" data-sortable="true">chainname</th>
	           
	        </tr>
	    </thead> -->
	</table>
	<!-- 删除慈善机构失败的提示信息模态框 -->
	<div class="modal" id="deleteCharityModal" tabindex="-2" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Delete Filed Info</h4>
					</div>
					<div class="modal-body">
						<div style="text-align:center;"><h3><span id="attentionInfo"></span></h3></div>
						<div id="info" style="text-align:center; color:#FA6A6A;"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<!-- <button type="button" id="subDelete" class="btn bg-olive" style="width:80px;">Delete</button> -->
					</div>
				</div>
		</div>
	</div>
	<!-- 新增连锁店名称的模态框 -->
		<div class="modal" id="charityModal" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<form id="chainForm" action="" method="POST">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" style="font-weight: 400;" name="modal-title">New Charity</h4>
						</div>
						<div class="modal-body">
							<input type="hidden" name="id" id="chainid"><!-- 连锁店ID -->
							<div class="row" id= "formElements">
								<div class="col-md-3"><label class="modalField">Charity Name:</label></div>
								<div class="col-md-9">
									<input type="text" name="charityName" id="charityName" maxlength="30">
								</div>
								<div class="col-md-3"><label class="modalField">Phone:</label></div>
								<div class="col-md-9">
									<input type="text" name="phone" id="phone" maxlength="20">
								</div>
								<div class="col-md-3"><label class="modalField">Address:</label></div>
								<div class="col-md-9">
									<input type="text" name="address" id="address" maxlength="100">
								</div>
								<div class="col-md-3"><label class="modalField">description:</label></div>
								<div class="col-md-9">
									<input type="text" name="description" id="description" maxlength="200">
								</div>
								<div class="col-md-3"><label class="modalField">status:</label></div>
								<div class="col-md-9" style="padding-top:15px;" name="status"> &nbsp;&nbsp;
									<input type="radio" name="status" value="1" checked="checked"> Active &nbsp;&nbsp;
									<input type="radio" name="status" value="0"> Disabled
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
							<button type="button" id="sub" class="btn bg-olive"
								style="width:80px;">Save</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script src="${ctx}/js/admin/charityManage.js" type="text/javascript"></script>
  </body>

</html>
