<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ceshi</title>
<link rel="stylesheet" href="${ctx}/index/css/bootstrap-combined.min.css">
<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/index/css/search_list.css">
<link rel="stylesheet" href="${ctx}/index/css/commcss.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script src="${ctx}/index/js/commjs.js"></script>
<script src="${ctx}/framework/plugins/paginator/bootstrap-paginator.js"></script>
<script src="${ctx}/index/js/user.js"></script>


<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
<script type="text/javascript" src="${ctx}/framework/plugins/creditcard/jquery.creditCardValidator.js"></script>
</head>

<body>
	<div id="bg" style="z-index: 5000"></div>
	<div id="show" style="z-index: 5001">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>

	<input type="hidden" id ="currentConsumerUuid" value="${consumer.uuid}">
	<input type="hidden" id ="clickFlag" value="${flag}">
	<div class="search_top">
		<div class="container">
			<div class="row">
				<div class="col-md-5">
					<h4>
					<a name="logo" href="#">
						<img src="${ctx}/index/images/xc-logo.png">
						
					</a>
					</h4>
				</div>
				<div class="col-md-7">
					<div class="xc-top-login" style="display: ${empty sessionScope.consumer?'block':'none'}">
						<li><a href="#myModal" data-toggle="modal" data-target="#myModal" id="signIn0">Sign in</a></li>
					</div>
					<!--登陆后显示-->
					<div class="xc-top-login-in" style="display: ${empty sessionScope.consumer?'none':'block'}">
						<li>
							<div class="dropdown">
								<button class="dropdown-toggle" type="button" id="dropdownMenu1"
									data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
									<span name="currentLoginUserName"></span>
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
									<!-- <li><a href="#">Profile</a></li>
									<li><a href="#">Addresses</a></li>
									<li><a href="#">Payments</a></li>
									<li><a href="#">Order History</a></li>
									<li><a href="#">Favourites</a></li> -->
									<li><a href="${ctx}/index/signOut">Sign Out</a></li>
								</ul>
							</div>
						</li>
					</div>
				</div>
				<!-- 插入用户帐号相关功能模态框 -->
				<jsp:include page="./inputjsp.jsp"></jsp:include>
			</div>
		</div>
	</div>
	
	<div class="container bgbai userContentDiv">
		<div class="row ">
			<div class="col-md-2 youmian-miaobian" style="padding:0;">
				<div align="center" class="rest-miaodi rest-miaodi-gao">Your Account</div>
				<div role="tabpanel">
					<!-- Nav tabs -->
					<!-- <ul class="user-xiala user-xiala-jg" role="tablist">  原来css切换的代码
						<li role="presentation" class="active"><a href="#Profile"
							aria-controls="Profile" role="tab" data-toggle="tab">Profile</a></li>
						<li role="presentation"><a href="#Addresses"
							aria-controls="Addresses" role="tab" data-toggle="tab">Addresses</a></li>
						<li role="presentation"><a href="#Payments"
							aria-controls="Payments" role="tab" data-toggle="tab">Payments</a></li>
						<li role="presentation"><a href="#orers"
							aria-controls="orers" role="tab" data-toggle="tab">Past orers</a></li>
						<li role="presentation"><a href="#Reservations"
							aria-controls="Reservations" role="tab" data-toggle="tab">Past Reservations</a></li>
						<li role="presentation"><a href="#Favorites"
							aria-controls="Favorites" role="tab" data-toggle="tab">Favorites</a></li>
					</ul>  -->
					<div name="ul-content">
					<ul class="user-xiala user-xiala-jg" id="tabs">
						<li role="presentation" class="active" ><a href="#Profile" aria-controls="Profile" role="tab" data-toggle="tab" style="cursor:pointer;">Profile</a></li>
						<li role="presentation" ><a href="#Addresses" aria-controls="Addresses" role="tab" data-toggle="tab" style="cursor:pointer;">Addresses</a></li>
						<li role="presentation" ><a href="#Payments" aria-controls="Payments" role="tab" data-toggle="tab" style="cursor:pointer;">Payments</a></li>
						<li role="presentation" ><a href="#Reservations" aria-controls="Reservations" role="tab" data-toggle="tab" style="cursor:pointer;">Current Orders</a></li>
						<li role="presentation" ><a href="#orders" aria-controls="orders" role="tab" data-toggle="tab" style="cursor:pointer;">Order History</a></li>
						<li role="presentation" ><a href="#Favourites" aria-controls="Favourites" role="tab" data-toggle="tab" style="cursor:pointer;">Favourites</a></li>
						<li role="presentation" >
							<div style="border:1xp solid #555555;padding:10px;font-size: 20px;font-weight: bold;">
								<span style="font-size: 18px;">You have donated </span><br>
								<span style="color:#7FBF2D">
									<fmt:formatNumber value="${donatedMoney}" type="currency" pattern="$#0.00"/>
								</span>
							</div>
							
						</li>
					
					</ul>
					</div>
					<!-- Tab panes -->
					
				</div>
				<div role="tabpanel">
					
				</div>	
			</div>
			
			
			<!-- <div id='bp-3-element-test'></div> -->
			<!-- <div id="pp">
				<ul id="paging">ddd</ul>
			</div> -->
			<!-- <div id="paging"></div> -->
			<!-- <div id="paging1" class="page"></div> -->
			<div class="col-md-10" style="padding:0;" id="pageContent">
				<div id="paging-div-content" style="display:none;">
					<div id="sun-page-div">
						
					</div>
					<div class="row">
			  			<div class="col-md-11">
							<div id="paging" style="float:right;"></div>
			  			</div>
			  			<div class="col-md-1"></div>
			  		</div>
				</div>
				<div id="no-paging-div-content" style="display:none;">
				</div>
				<!-- Nav tabs -->
		</div>
	</div>
	
	<!-- reservation  -->
	<div class="modal fade" id="orderDetailModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" name="orderDetailModal-2" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="myModalLabel-orderDetails" align="center">Order Details</h4>
				</div>
				<div class="modal-body">
					<div name="ordreDetailDiv">
					
					</div>
					<div class="container" style="width:760px;"  id="opererate-info">
						
					</div>
					
					<!-- <h4>
						<div class="controls" name="clearCartTip" style="width:428; margin-left: 15px;margin-bottom: 20px;">
							
						</div>
					</h4>
					<div style=" padding:0 15px 15px;">
						<button class="btn btn-default btn-block" type="button" id="closeOrderDetailModal"
							style="height:45px; border:#6C9C46 2px solid; color:#6C9C46;">Close</button>
					</div> -->
				</div>
			</div>
		</div>
	</div>
	<!-- 用于确认操作的模态框 -->
	<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="myModalLabel-confirmOperation" align="center">Confirm Operation</h4>
				</div>
				<div class="modal-body">
					<div class="container" style="width:760px;">
						<div class="row" >
							<div class="col-md-12" align="center">
							<h4>
								<span name="operationInfoDiv"></span>
							</h4>
							</div>
						</div>
						<div class="row" style="height:20px;"></div>
						<!-- <input type="hidden" id = > -->
						<div class="row">
							<div class="col-md-6" align="center">
								<button class="btn btn-success-hui btn-lg btn-block" name="confirmModal-confirm" id="confirmModal-confirm">OK</button>
							</div>
							<div class="col-md-6" align="center">
								<button class="btn btn-success-hui btn-lg btn-block" name="confirmModal-cancel">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 用于处理lineup 排队 状态的模态框   接收或拒绝 -->
	<!-- <div class="modal fade" id="lineupModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="myModalLabel" align="center">Accept the queue number</h4>
				</div>
				<div class="modal-body">
					<div class="container" style="width:760px;">
						<div class="row" >
							<div class="col-md-12" align="center">
							<h4>
								<span name="operationInfoDiv"></span>
								Please confirm your acceptance of the queue number or cancel this order!
							</h4>
							</div>
						</div>
						<div class="row" style="height:20px;"></div>
						<input type="hidden" id = >
						<div class="row">
							<div class="col-md-6" align="center">
								<button class="btn btn-success-hui btn-lg btn-block" name="lineupModal-accept">Accept</button>
							</div>
							<div class="col-md-6" align="center">
								<button class="btn btn-success-hui btn-lg btn-block" name="lineupModal-reject">Cancel Order</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div> -->
	
	<!-- 用于发布评论的模态框 -->
	<div class="modal fade" id="ratingModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:800px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 id="myModalLabel-review" align="center">Write a Review</h4>
				</div>
				<div class="modal-body">
					<div class="container" style="width:760px;">
						<div class="row" >
								<div class="col-md-12">
									<h4>Overall Rating</h4>
								</div>
						</div>
						<div class="row">
							<div class="col-md-12" align="center">
								<li name="starGroup">
									<span name="star" alt="1" style="cursor:pointer;margin-right:7px;font-size: 26px;"></span>
									<span name="star" alt="2" style="cursor:pointer;margin-right:7px;font-size: 26px;"></span>
									<span name="star" alt="3" style="cursor:pointer;margin-right:7px;font-size: 26px;"></span>
									<span name="star" alt="4" style="cursor:pointer;margin-right:7px;font-size: 26px;"></span>
									<span name="star" alt="5" style="cursor:pointer;margin-right:7px;font-size: 26px;"></span>
								</li>
							</div>
						</div>
						<div class="row" style="height:20px;">
							<div class="col-md-12">
								<h4>Review</h4>
							</div>
						</div>
						<div class="row">
							<div class="col-md-8">
								<textarea rows="10" id="rating_text"  maxlength="300" style="border-radius:4px;border:1px solid #ccc; 
									margin-bottom: 20px; width:725px;"></textarea>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6" align="center">
								<button class="btn btn-success-hui btn-lg btn-block" name="ratingModal-submit" id="ratingModal-submit">Submit</button>
							</div>
							<div class="col-md-6" align="center">
								<button class="btn btn-success-hui btn-lg btn-block" name="ratingModal-cancel"data-dismiss="modal"
											aria-label="Close">Cancel
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	
	
	<!-- 修改地址的复用DIV -->
		<div class="userpadd" name="address-div" style="display:none">
			<h3 align="left" class="rest-miaodi">Edit Address</h3>
			<input type="hidden" name="input-addressId">
			<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<p>Name (Requried)<p>
					<p>
						<input type="text" class="form-control" name="input-name">
					</p>
					
					<p>Apartment, Suite #: (Optional)<p>
					<p>
						<input type="text" class="form-control" name="input-floor">
					</p>
					<div class="row">
						<div class="col-md-6">
							<p>Street (Required)</p>
							<p>
							<input type="text" class="form-control" name="input-street">
							</p>
						</div>
						<div class="col-md-6">
							<p>City (Required)</p>
							<p>
							<input type="text" class="form-control" name="input-city">
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<p>Province (Required)</p>
							<p>
							<input type="text" class="form-control" name="input-province">
							</p>
						</div>
						<div class="col-md-6">
							<p>Phone (Required)</p>
							<p>
							<input type="text" class="form-control" name="input-phone">
							</p>
						</div>
					</div>
					<div class="row">
						<div class="col-md-3">
								Default Address&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox"  name="input-defaultAddress">
						</div>
						<div class="col-md-9"></div>
					</div>
					<div class="row">
						<div class="col-md-3"></div>
						<div class="col-md-3">
							<button class="btn btn-success-hui btn-lg btn-block" name="edit-address-cancel"
								type="submit" alt="new">Cancel</button>
						</div>
						<div class="col-md-3">
							<button class="btn btn-success-hui btn-lg btn-block" name="edit-address-save"
								type="submit" alt="new">Save</button>
						</div>
					</div>
					<div class="row" >
						<div class="col-md-12" align="center" class = "">
							<div name="save-address-errorInfo" style="font-size: 20px;font-weight: bold;color:#7DD700;display:none;">
								Save or update error !
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>

	<form action="${ctx}/index/restaurantmenu" method="post" id="repeat-goto-restaurantMenu">
		<input type="hidden" name="restaurantUuid" id="restaurantUuid">
		<!-- <div style="display:none;">
			<input type="submit" id ="submitRepeat">
		</div> -->
	</form>

	<!-- dibu kaishi-->

	<div class="search-bottom" align="center">
		<a href="#"><span>Contact Us</span></a>　　About　　FAQ　　Terms of Use　　Privacy policy
	</div>
</body>
</html>
