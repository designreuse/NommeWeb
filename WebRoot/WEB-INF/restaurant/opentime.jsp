<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html class="bg-black">
<head>
<meta charset="UTF-8">
<jsp:include page="../inputcss.jsp"></jsp:include>
<style type="text/css">
.popover-content {
	color: #FA6A6A;
}

input {
	width: 500px;
	margin-top: 8px;
}

label {
	margin-top: 9px;
}
button{
	margin-top: 4px;
}
#d1,#d2,#d3,#d4,#d5,#d6,#d7,#p1,#p2,#p3,#p4,#p5,#p6,#p7,#r1,#r2,#r3,#r4,#r5,#r6,#r7{
	padding-top: 10px;
}

hr{
	margin-top: 5px;
	margin-bottom: 0px;
}
</style>
</head>
<body style="overflow: hidden;">
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span
			style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>
	<button type="button" name="add"
		class="btn-primary glyphicon glyphicon-plus"
		title="add new garnishheader"
		style="width: 70px;height: 30px;" data-toggle="modal"
		data-target="#myModal">Add</button>
	<fieldset>
		<div id="delivery" style="display: none;">
			<legend>delivery</legend>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Monday</label>
				<div id="d1" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Tuesday</label>
				<div id="d2" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Wednesday</label>
				<div id="d3" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Thursday</label>
				<div id="d4" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Friday</label>
				<div id="d5" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Saturday</label>
				<div id="d6" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Sunday</label>
				<div id="d7" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
		</div>
		<div id="pickup" style="display: none;">
			<legend>pickup</legend>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Monday</label>
				<div id="p1" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Tuesday</label>
				<div id="p2" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Wednesday</label>
				<div id="p3" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Thursday</label>
				<div id="p4" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Friday</label>
				<div id="p5" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Saturday</label>
				<div id="p6" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Sunday</label>
				<div id="p7" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
		</div>
		<div id="reservation" style="display: none;">
			<legend>reservation</legend>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Monday</label>
				<div id="r1" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Tuesday</label>
				<div id="r2" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Wednesday</label>
				<div id="r3" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Thursday</label>
				<div id="r4" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Friday</label>
				<div id="r5" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Saturday</label>
				<div id="r6" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
			<hr>
			<div class="row">
				<label class="col-md-3" style="text-align: right;">Sunday</label>
				<div id="r7" class="col-md-6">
				</div>
				<div class="col-md-3">
					<button class="btn btn-default glyphicon glyphicon-minus">
						delete</button>
				</div>
			</div>
		</div>
	</fieldset>


	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header"
					style="background-color: #3D9970; border-bottom: 4px solid #307959;">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel" style="font-weight: 600;">Add business information</h4>
				</div>
				<div class="modal-body">
					<form id="fm" class="form-horizontal">
						<div class="form-group">
							<div class="row">
								<div class="col-md-12" style="margin-left: 15px;">
									<input type="checkbox" name="week" value="1">Monday 
									<input type="checkbox" name="week" value="2">Tuesday 
									<input type="checkbox" name="week" value="3">Wednesday 
									<input type="checkbox" name="week" value="4">Thursday 
									<input type="checkbox" name="week" value="5">Friday 
									<input type="checkbox" name="week" value="6">Saturday 
									<input type="checkbox" name="week" value="7">Sunday 
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 0px;">Start
										time</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="starttime" class="form-control"
										id="starttime" />
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="row">
								<div class="col-md-3" style="margin-top: 15px;">
									<label class="control-label"
										style="float: right;color: #555555;font-size: 16;font-weight: lighter;margin-top: 0px;">End
										time</label>
								</div>
								<div class="col-md-9">
									<input type="text" name="endtime" class="form-control"
										id="endtime" />
								</div>
							</div>
						</div>
						<div>
						<div class="row">
							<div class="col-md-3">
								<label
									style="color: #555555;font-size: 16;float: right;font-weight: lighter;">permissions</label>
							</div>
							<div class="col-md-9">
								<label style="color: #555555;"> <input type="checkbox"
									name="role1" value="1">Delivery
								</label> <label style="color: #555555;"> <input type="checkbox"
									name="role2" id="role2" value="2">Pick-up
								</label> <label style="color: #555555;"> <input type="checkbox"
									name="role3" id="role3" value="3">Reservation
								</label>
							</div>
						</div>
					</div>
					</form>
					
				</div>
				<div class="modal-footer" style="margin-top: 0px;">
					<button type="button" id="close" class="btn btn-default"
						data-dismiss="modal">Cancel</button>
					<button id="sub" type="button" class="btn btn-primary"
						data-placement="top">&nbsp;Save&nbsp;</button>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/opentime.js"></script>

</body>
</html>
