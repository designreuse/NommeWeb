<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ceshi</title>
<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/index/css/search_list.css">
<link rel="stylesheet" href="${ctx}/index/css/commcss.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/jquery.cookie.js"></script>
<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script src="${ctx}/index/js/commjs.js"></script>
<script src="${ctx}/index/js/regist.js"></script>

</head>

<body>
	<div id="bg"></div>
	<div id="show">
		<img src="${ctx}/images/loader.gif"><span style="color: #eeeeee;font-size: large;">&nbsp;Loading...</span>
	</div>

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
								<li><a href="${ctx}/index/user?flag=1">Profile</a></li>
								<li><a href="${ctx}/index/user?flag=2">Addresses</a></li>
								<li><a href="${ctx}/index/user?flag=3">Payments</a></li>
								<li><a href="${ctx}/index/user?flag=4">Current Orders</a></li>
								<li><a href="${ctx}/index/user?flag=5">Order History</a></li>
								<li><a href="${ctx}/index/user?flag=6">Favorites</a></li>
								<li><a href="${ctx}/index/signOut">Sign Out</a></li>
							</ul>
						</div>
					</li>
				
				</div>
					<!-- 插入用户帐号相关功能模态框 -->
					<jsp:include page="./inputjsp.jsp"></jsp:include>
				</div>
			</div>
		</div>

	</div>
	<!-- center kai-->

	<div class="container bgbai">
		<div class="row">
			<div class="col-md-8" style="padding:10px;">
				<h3 align="center">Contact Information</h3>
				<!-- <div class="row zhucyemian-sh-10">
					<div class="col-md-2 zhucyemian-p" align="right">address: </div>
					<div class="col-md-8">
						<select  class="form-control" id="addressList1" placeholder="select an address">
						</select>
					</div>
				</div> -->
				<div style="padding-left: 50px;">
					<div class="row zhucyemian-sh-10">
						<div class="col-md-2 zhucyemian-p" align="right">Name:*</div>
						<div class="col-md-8">
							<input type="text" class="form-control" id="address-name"
								placeholder="Your Name" value="${sessionScope.consumer.firstName} ${sessionScope.consumer.lastName}">
						</div>
						
						
					</div>
					<div class="row zhucyemian-sh-10">
						<div class="col-md-2 zhucyemian-p" align="right">Email:*</div>
						<div class="col-md-8">
							<input type="email" class="form-control" id="address-email"
								placeholder="*****@***.com" value="${sessionScope.consumer.email}" >
						</div>
	
					</div>
					<div class="row zhucyemian-sh-10">
						<div class="col-md-2 zhucyemian-p" align="right">Phone:*</div>
						<div class="col-md-8">
							<input type="text" class="form-control" id="address-phone"
								placeholder="（***）***-****" value="${sessionScope.consumer.phone}">
						</div>
					</div>
				</div>
				<div id="deliveryAddressContent"  style="display:none;">
					<div class="zhucyemian-miaodi"></div>
						<h3 align="center">Address</h3>
					<div style="padding-left: 50px;">
						<div class="row zhucyemian-sh-10">
							<div class="col-md-9 col-md-offset-1">
								<button type="button" class="btn xc-btn-default" id="newAddress">New address</button>
								<button type="button" class="btn xc-btn-default" id="selectAddress" style="display:none;">Select address</button>
							</div>
						</div>
						<div class="row zhucyemian-sh-10">
							<div class="col-md-9 col-md-offset-1">
								<select  class="form-control" id="addressList" placeholder="select an address">
								</select>
							</div>
						</div>
						
						<div class="row zhucyemian-sh-10">
							<div class="col-md-9 col-md-offset-1">
								<input type="input" class="form-control" id="address-street" style="cursor:text;"
									placeholder="Street (Required)" value="" readonly="readonly">
							</div>
						</div>
						<div class="row zhucyemian-sh-10">
							<div class="col-md-9 col-md-offset-1">
								<input type="input" class="form-control" id="address-floor" style="cursor:text;"
									placeholder="Apartment, Suite # (optional)" value="" readonly="readonly">
							</div>
						</div>
						
						<div class="row zhucyemian-sh-10">
							<div class="col-md-5 col-md-offset-1">
								<input type="input" class="form-control" id="address-city" style="cursor:text;"
									placeholder="City (Required)" value="" readonly="readonly">
							</div>
							<div class="col-md-4">
								<input type="input" class="form-control" id="address-province" style="cursor:text;"
									placeholder="Province (Required)" value="" readonly="readonly">
							</div>
						</div>
					<!-- 	<div class="row zhucyemian-sh-10">
							<div class="col-md-9 col-md-offset-1">
								<input type="input" class="form-control" id="address-address"
									placeholder="e.g. 150 6 Ave SW, Calgary, AB, Canada (Required)" value="">
							</div>
						</div> -->
						
						<div class="row zhucyemian-sh-10" id="save-address-div" style="display:none;">
							<div class="col-md-9 col-md-offset-1">
								<div class="checkbox">
									<label> <input type="checkbox" id="saveAddress"
										 aria-label="Save Address">Save Address
									</label>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="zhucyemian-miaodi"></div>
				<h3 align="center">
					Special requests
				</h3>
				<div class="row zhucyemian-sh-10" style="padding-left: 50px;">
					<div class="col-md-9 col-md-offset-1">
						<textarea class="form-control" rows="3" maxlength="300" id="memo";
							style="border-radius:4px; text-align: left;"  
							placeholder="Any allergies? Need a phone call notification before delivery? Or other requests?">${sessionScope.consumer.memo}</textarea>
					</div>
					
				</div>
				<div class="row" style="height:20px;">
				</div>
				<div class="row">

					<div class="col-md-10 col-md-offset-1" align="center">
						<button type="button" name="gotoPay" class="btn xc-btn-success btn-lg">Choose your payment method next</button>
					</div>

				</div>
				<div class="row">

					<div class="col-md-10 col-md-offset-1" align="center" style="color:red;height:20px;">
						<span id ="over-range-message" style="display:none;">Your place is over range of this restaurant delivery distance</span>
					</div>

				</div>
			</div>


			<div class="col-md-4 rest-leftdi" style="padding:0px;height: auto;">

				<%-- <h3 align="center">Orde from ${restaurant.restaurantName}</h3><!-- Villaggio Bistro Grill --> --%>
				<div class="rest-lef-zykuan">
					<div id="cartContent" name=""><!-- 此处的name属性保存的是订单类型，默认是pickup 2  /  Devlivery 1 ; Pick Up 2 ;Reservation 3-->
					<!-- 插入购物车相关功能页面-->
					</div>
					<div align="center" style="margin-bottom: 20px;">
						<button type="button" name="changeOrder" class="btn xc-btn-success navbar-inverse ">Change	Your Order</button>
					</div>
				</div>



			</div>
		</div>
	</div>
	
	<!-- center jieshu-->
	<c:if test="${!empty sessionScope.discountId}">
		<input type="hidden" id="parentPageDiscountId" value="${discountId}">
	</c:if>
	<!-- dibu kaishi-->
	<div style="display:none;" id="sessionCartAddress">${sessionScope.cartAddress}</div>
	<input type="hidden" id = "currentConsumerUuid" value="${sessionScope.consumer.uuid}">
	<input type="hidden" id = "checkOutOrderType" value="${sessionScope.checkOutOrderType}"><!-- 订购单类型 -->
	<div class="search-bottom" align="center">
		<a href="#" id = "index-contactus"><span>Contact Us</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-about"><span>About</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-faq"><span>FAQ</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-termofuse"><span>Terms of Use</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-privacypolicy"><span>Privacy Policy </span></a>
	</div>
	<div>
		<jsp:include page="./contactUs.jsp"></jsp:include>
		<jsp:include page="./about.jsp"></jsp:include>
		<jsp:include page="./faq.jsp"></jsp:include>
		<jsp:include page="./termOfUse.jsp"></jsp:include>
		<jsp:include page="./privacyPolicy.jsp"></jsp:include>
	</div>
	
</body>
</html>
