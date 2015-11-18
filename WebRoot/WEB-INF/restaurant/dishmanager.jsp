<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../inputcss.jsp"></jsp:include>
<link href="${ctx }/css/fileinput.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.form-group {
	width: 550px;
}

/* input,select {
	height: 35px;
	width: 400px;
} */
</style>
</head>

<body>
<div id="bg"></div>
<div id="show"><img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span></div>

	<div style="margin-top: 20px;">
		<div class="row">
			<div class="col-md-7">
				<div style="margin-left: 100px;">
					<form id="fm" method="post" enctype="multipart/form-data">

						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Dish
										name</label>
								</div>
								<div class="col-md-9">
									<input type="text" id="enName" name="enName"
										class="form-control" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Second
										name</label>
								</div>
								<div class="col-md-9">
									<input type="text" id="chName" name="chName"
										class="form-control" data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Price</label>
								</div>
								<div class="col-md-9">
									<input type="text" id="priceShow" name="priceShow" class="form-control" value="$0.00"
										data-placement="right" />
									<input type="hidden" id="price" name="price" class="form-control" value="0.00"
										data-placement="right" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Spicy</label>
								</div>
								<div class="col-md-9">
									<select id="spicy" name="spicy" class="form-control">
										<option value="0">No spice</option>
										<option value="1">mild</option>
										<option value="2">medium</option>
										<option value="3">extra</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">MSG</label>
								</div>
								<div class="col-md-9">
									<select id="isMsg" name="isMsg" class="form-control">
										<option value="0">No</option>
										<option value="1">Yes</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Gluten</label>
								</div>
								<div class="col-md-9">
									<select name="isStarch" id="isStarch" class="form-control">
										<option value="0">No</option>
										<option value="1">Yes</option>
									</select>
								</div>
							</div>
						</div>
						<!-- <div>
							<div class="row">
								<div class="col-md-3">
									<label
										style="color: #555555;font-size: 16;font-weight: lighter;margin-left: 40px;">permissions</label>
								</div>
								<div class="col-md-9">
									<label style="color: #555555;"> <input type="checkbox"
										value="1" name="role1"> Delivery
									</label> <label style="color: #555555;"> <input type="checkbox"
										value="2" name="role2"> Pick-up
									</label> <label style="color: #555555;"> <input type="checkbox"
										value="4" name="role3"> Reservation
									</label>
								</div>
							</div>
						</div> -->
						<br/>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Dish
										category</label>
								</div>
								<div class="col-md-9">
									<select name="dishMenuId" id="dishMenuId" class="form-control"
										data-placement="right">
										<option></option>
									</select>
								</div>
							</div>
						</div>
					
						<div class="form-group">
							<div class="row">
								<div class="col-md-3">
								</div>
								<div class="col-md-9">
									<button type="button" id="garnish" class="btn btn-primary"
									title="garnish manager" 
									data-toggle="modal" data-target="#myModal">Ingredients</button>
								</div>
							</div>
						</div>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group" style="margin-right: 100px;width: 400px;">
					<div id="inputfm">
						<input type="file" name="photo" id="photo" accept="image/*"
							data-placement="bottom" /> <br /> <input type="button"
							id="cancel" class="btn-primary" value="Cancel"
							style="width: 60px;margin-left: 130px;height: 30px;" />
					</div>
					<div id="inputdiv"
						style="width: 400px;height: 350px;display: none;">
						<div id="imgdiv"></div>
						<br /> <br /> <input type="button" id="replace"
							class="btn-primary" value="Replace photo"
							style="width: 110px;margin-left: 70px;height: 30px;" />
					</div>
					<span style="color: #999999;">prompt:<br />&nbsp;&nbsp;&nbsp;
						Photo size does not exceed 1M!<br />&nbsp;&nbsp;&nbsp; Suggested
						photo size is 800x800
					</span>
				</div>
			</div>
		</div>
			<div id="garnishtable" style="margin-left: 245px;margin-right: 128px;">
				<div id="custom-toolbar">
				<button type="button" name="delete"
				class="btn btn-default glyphicon glyphicon-minus"
				><span style="margin-left: 8px;">Delete</span></button>
			    </div>
				<table id="table" data-toggle="table" data-toolbar="#custom-toolbar"
				data-click-to-select="true">
					<thead>
						<tr>
						 	<th data-field="state" data-radio="true"></th>
							<th data-field="name">Ingredient</th>
							<th data-field=menu>Ingredient Category</th>
							<th data-field="addprice">Add Price</th>
						</tr>
					</thead>
				</table>
			</div>
			<br />
			<div id="enIntroDiv" class="form-group"
				style="margin-top: 40px;margin-left: 100px;">
				<div class="row">
					<div class="col-md-3">
						<label class="control-label"
							style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Description</label>
					</div>
					<div class="col-md-9">
						<textarea name="enIntro" id="enIntro"
							style="width: 200%;height: 90px;resize:none;"></textarea>
						<input type="hidden" name="photoUrl" id="photoUrl" /> <input
							type="hidden" name="id" id="id" />
					</div>
				</div>
			</div>
			</form>
			<button name="back" id="back"
				style="width: 80px;height: 30px;margin-left: 500px;">Back</button>
			<button class="btn-primary" name="save" id="save"
				data-placement="bottom"
				style="width: 80px;height: 30px;margin-left: 100px;">Save</button>
		
		
		
	</div>
	
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #3D9970; border-bottom: 4px solid #307959;">
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;">Ingredients</h4>
				</div>
				<div class="modal-body ">
						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Ingredient Category</label>
								</div>
								<div class="col-md-9">
									<select name="garnishMenu" id="garnishMenu" class="form-control">
										<option></option>
									</select>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Ingredient</label>
								</div>
								<div class="col-md-9" id="garnishDiv">
									
								</div>
							</div>
						</div>
				</div>
				<div class="modal-footer" style="margin-top: 0px;">
					<button type="button" id="cancel1" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button id="sub" type="button" class="btn btn-primary"
						data-placement="top">&nbsp;Ok&nbsp;</button>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="type" value="${type}">
	<input type="hidden" id="mDishId" value="${modelDishId}">
	<span style="vertical-align:middle;"></span>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/fileinput.js"></script>
	<script type="text/javascript" src="${ctx}/js/fileinput_locale_LANG.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/dishmanager.js"></script>
</body>
</html>
