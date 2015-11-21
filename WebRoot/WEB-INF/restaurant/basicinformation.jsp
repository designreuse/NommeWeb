<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<jsp:include page="../inputcss.jsp"></jsp:include>
<link href="${ctx }/css/fileinput.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/framework/select2/select2.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.popover-content {
	color: #FA6A6A;
}

.form-group {
	width: 550px;
}

input,select {
	height: 35px;
	width: 350px;
}

html,body,#map-canvas {
	height: 100%;
	margin: 0px;
	padding: 0px
}
</style>
</head>

<body style="margin-left: 5px;margin-right: 5px;">
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>



	<div class="row">
		<div class="col-md-6">
			<div style="margin-left: 100px;margin-top: 20px;">
				<form id="fm" action="${ctx}/information/updateRestaurant" method="post">

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Restaurant
									name</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="restaurantName" name="restaurantName" class="form-control" data-placement="bottom" /> 
								<input type="hidden" name="id" id="id" />
								<input type="hidden" name="uuid" id="uuid" />
							</div>
						</div>
					</div>


					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Restaurant
									contact</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="restaurantContact" name="restaurantContact" class="form-control" data-placement="bottom" />
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Restaurant
									phone</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="restaurantPhone" name="restaurantPhone" class="form-control" data-placement="bottom" />
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Restaurant
									address</label>
							</div>
							<div class="col-md-8">
								<input type="text" name="restaurantAddress" id="restaurantAddress" class="form-control" data-placement="bottom" />
								<input type="hidden" name="restaurantLng" id="restaurantLng" /> <input type="hidden" name="restaurantLat"
									id="restaurantLat" />
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Delivery Distance(km)</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="distance" name="distance" class="form-control" data-placement="bottom" placeholder="Km"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;text-align:right;">
								Estimate Delivery Time(mins)</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="deliverTime" name="deliverTime" class="form-control" data-placement="bottom" placeholder="minutes"/>
							</div>
						</div>
					</div>
					
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Mini. Deliver price($)</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="deliverPrice" name="deliverPrice" class="form-control" data-placement="bottom" placeholder="$"/>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Avg
									price($)</label>
							</div>
							<div class="col-md-8">
								<input type="text" id="avgPrice" name="avgPrice" class="form-control" data-placement="bottom" placeholder="$0.00"/>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label style="color: #555555;font-size: 16;font-weight: lighter;float: right;">Service type</label>
							</div>
							<div class="col-md-8">
								<label style="color: #555555;"> <input type="checkbox" value="0" name="isdelivery" id="isdelivery"> Delivery
								</label>&nbsp;<label style="color: #555555;"> <input type="checkbox" value="0" name="ispickup" id="ispickup">
									Pick-up
								</label>&nbsp;<label style="color: #555555;"> <input type="checkbox" value="0" name="isreservation" id="isreservation">
									Reservation
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Chain</label>
							</div>
							<div class="col-md-8">
								<select name="chainid" class="form-control" id="chainid" data-placement="bottom">
									<option value="0">N/A</option>
								</select>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Areas</label>
							</div>
							<div class="col-md-8">
								<select name="areasId" class="form-control" id="areasId" data-placement="bottom">
								</select>
							</div>
						</div>
					</div>
					

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Cuisines</label>
							</div>
							<div class="col-md-8">
								<select name="ClassificationId" multiple="multiple" class="form-control" id="ClassificationId"
									data-placement="bottom">
								</select>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Message</label>
							</div>
							<div class="col-md-8">
								<textarea name="notice" id="notice" style="resize:none;height: 70px;width: 355px;"></textarea>
							</div>
						</div>
					</div>

					<div class="form-group">
						<div class="row">
							<div class="col-md-4">
								<label class="control-label" style="float: right;color: #555555;font-size: 16;font-weight: lighter;">Introduction</label>
							</div>
							<div class="col-md-8">
								<textarea name="features" id="features" style="resize:none;height: 100px;width: 220%"></textarea>
							</div>
						</div>
					</div>

				</form>
			</div>
		</div>



		<div class="col-md-6">
			<div style="margin-left: 100px;">
				<div class="form-group" style="margin-top: 60px;width: 400px;">
					<label class="control-label" style="color: #555555;font-size: 16;margin-left: 180px;">Logo</label>
					<div id="inputfm" style="display: none;">
						<form method="post" enctype="multipart/form-data">
							<input type="file" name="logo" id="logo" accept="image/*" data-placement="bottom" />
						</form>
						<br />
						<button id="cancel" class="btn-primary" style="margin-left: 150px;">Cancel</button>
					</div>
					<div id="inputdiv" style="width: 400px;height: 405px;display: none;">
						<div id="imgdiv"></div>
						<br /> <br /> <br /> <br />
						<button id="replace" class="btn-primary" style="margin-left: 150px;">Replace Logo</button>
					</div>
					<br /> <br /> <span style="color: #999999;">prompt:<br />&nbsp;&nbsp;&nbsp; Photo size does not exceed
						1M!<br />&nbsp;&nbsp;&nbsp; Suggested photo size is 800x800
					</span>
				</div>
			</div>
		</div>
	</div>







	<div id="map-canvas" style="width: 91%;height: 75%;margin-left: 20px;"></div>
	<br />
	<button type="button" id="sub" class="btn bg-olive btn-block" data-placement="top"
		style="width: 200px;margin-left: 550px;">Save</button>
	<br />
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true"></script>
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript" src="${ctx}/js/form_validation.js"></script>
	<script type="text/javascript" src="${ctx}/js/fileinput.js"></script>
	<script type="text/javascript" src="${ctx}/js/fileinput_locale_LANG.js"></script>
	<script type="text/javascript" src="${ctx}/framework/select2/select2.js"></script>
	<script type="text/javascript" src="${ctx}/js/restaurant/basicinformation.js"></script>
</body>
</html>
