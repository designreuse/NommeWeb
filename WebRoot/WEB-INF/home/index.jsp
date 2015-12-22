<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme - Online Restaurant Ordering</title>
<meta name="description" content="Order food online from your favourite local restaurants.">
<meta name="keywords" content="Fast,online,order,food,local,restaurant,Calgary,convenient,mobile,app">

<link href="${ctx }/css/datatables/bootstrap-table.css" rel="stylesheet" type="text/css"/>

<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/index/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${ctx}/index/css/xc.css">
<link rel="stylesheet" href="${ctx}/index/css/commcss.css">

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/index/js/jquery.placeholder.js"></script>

<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&sensor=true&libraries=places"></script>
<script src="${ctx}/index/js/commjs.js"></script>
</head>

<body onload="initialize()" style="overflow: hidden;">
<!-- <fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
</fb:login-button>
<div id="status">
</div> -->
<div id="fb-root"></div>
	<div class="container xc-top">
		<div class="row">
			<div>
				<a name="logo" href="javascript:void(0)">
					<img src="images/xc-logo.png">
				</a>
			</div>
			<div>
			
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

	<div class="xc-js container" align="center">

		<h1>Not for me, it’s for everyone</h1>
		<p>For every order you make, we donate 5% to your choice of one of our listed non-profit organizations</p>
		
		<div align="center" class="container zuikuan">
			<div class="row">
	
				<div id = "search_bar" class="input-prepend input-append">
					<span style="">
						<input id="xc-biaodan" type="text" placeholder="Cuisine (Optional)">
					</span>
					<span style="">
						<input id="autocomplete" type="text" placeholder="Enter your address">
					</span>
					<span style="">
						<button class="btn xc-btn-success " name="searchBtn" type="button">
							<img src="images/xc-fdj.png">
						</button>
					</span>
					
					<!-- xc-biaodan-qian -->
					<!-- id="autocomplete" placeholder="Enter your address"
	             onFocus="geolocate()" -->
				</div>
			</div>
		</div>
	</div>
	
	<div class="xc-footer-menu container" align="center">
		<p>
			<a href="#" id = "index-contactus"><span>Contact Us</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-about"><span>About</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-faq"><span>FAQ</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-termofuse"><span>Terms of Use</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-privacypolicy"><span>Privacy Policy </span></a>
		</p>
		<jsp:include page="./contactUs.jsp"></jsp:include>
		<jsp:include page="./about.jsp"></jsp:include>
		<jsp:include page="./faq.jsp"></jsp:include>
		<jsp:include page="./termOfUse.jsp"></jsp:include>
		<jsp:include page="./privacyPolicy.jsp"></jsp:include>
	</div>

	<div class="xc-fenlei">
		<%-- <ul name="foods">
			<li><a href="#" name="fastfood"><p><img src="${ctx}/index/images/xc-tb01.png"></p>Fast food</a></li>
			<li><a href="#" name="hamburg"><p><img src="${ctx}/index/images/xc-tb02.png"></p>Hamburg</a></li>
			<li><a href="#" name="pizza"><p><img src="${ctx}/index/images/xc-tb03.png"></p>Pizza</a></li>
			<li><a href="#" name="drinks"><p><img src="${ctx}/index/images/xc-tb04.png"></p>Drinks</a></li>
			<li><a href="#" name="hotdog"><p><img src="${ctx}/index/images/xc-tb05.png"></p>Hot Dog</a></li>
		</ul> --%>
		<%-- 
		<ul>
			<c:forEach items="${foodClassification}" var="food" begin="0" step="1" varStatus="status">
				<li><a href="#" title="foodClassification"  name="${food.classification}" value="${food.classificationId}"><p><img src="${food.imageUrl}"></p>${food.classification}</a></li>
			
			
			</c:forEach>
		
		</ul>
		--%>
		<div class="clear"></div>

	</div>

	<form id="search-restaurants-form" action="${ctx}/index/searchlist" method="post">
		<input type="hidden" name="restaurantLng"/>
		<input type="hidden" name="restaurantLat"/>
		<input type="hidden" name="classificationName"/>
	</form>

	<script src="${ctx}/index/js/index.js"></script>
</body>
</html>
