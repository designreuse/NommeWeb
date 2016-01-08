<%@ page language="java" import="java.util.*" pageEncoding="Utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nomme - Order Takeout and Delivery from your favourite restaurants online</title>
<meta name="description" content="Order food online from your favourite local restaurants.">
<meta name="keywords" content="Fast,online,order,food,local,restaurant,Calgary,convenient,mobile,app">
<meta name="msvalidate.01" content="1B70D3EFDD4AD611F6224EF9E9C8DF70" />

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

<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-71565761-2', 'auto');
  ga('send', 'pageview');

</script>
</head>

<body onload="initialize()">
<!-- <fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
</fb:login-button>
<div id="status">
</div> -->
<div id="fb-root"></div>
	<div class="container xc-top">
		<div class="row">
			<span class="col-sm-6">
				<a name="logo" href="javascript:void(0)">
					<img src="images/xc-logo.png" alt="Online Food Ordering" />
				</a>
			</span>
			<span class="col-sm-6" align="right">
			
				<span class="social_media_links">
					<a href="https://www.facebook.com/Nomme-1645977468983563/" target="_blank">
						<img alt="Visit Nomme on Facebook" src="images/facebook_logo.png" />
					</a>
					<a href="https://twitter.com/Nomme_app" target="_blank">
						<img alt="Follow Us on Twitter @Nomme_app" src="images/twitter_logo.png" />
					</a>
					<a href="https://www.instagram.com/nomme_nomme/" target="_blank">
						<img alt="Visit Nomme on Instagram" src="images/instagram_logo.png" />
					</a>
				</span>
			
				<span class="xc-top-login" style="display: ${empty sessionScope.consumer?'inline-block':'none'}">
					<li><a href="#myModal" data-toggle="modal" data-target="#myModal" id="signIn0">Sign in</a></li>
				</span>
				<!--登陆后显示-->
				<span class="xc-top-login-in" style="display: ${empty sessionScope.consumer?'none':'inline-block'}">
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
				</span>
				
				<!-- 插入用户帐号相关功能模态框 -->
				<jsp:include page="./inputjsp.jsp"></jsp:include>
			</span>
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
							<img src="images/xc-fdj.png" alt="restaurant food" />
						</button>
					</span>
					
					<!-- xc-biaodan-qian -->
					<!-- id="autocomplete" placeholder="Enter your address"
	             onFocus="geolocate()" -->
				</div>	
			</div>
		</div>
		
		<p>Enjoy extra discounts up to 15% with Nomme</p>
		<p>Officially launched January 10, 2016</p>
	</div>
	
	
	
	<div class="xc-footer-menu container">
		
		<p>
			<a href="#" id = "index-contactus"><span>Contact Us</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-about"><span>About</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-faq"><span>FAQ</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-termofuse"><span>Terms of Use</span></a>
			&nbsp;&nbsp;&#47;&nbsp;&nbsp;<a href="#" id = "index-privacypolicy"><span>Privacy Policy </span></a>
		</p>
		<div style="padding-top:15px">
			<span class="col-sm-3">&nbsp;</span>
			<span class="col-sm-6 app_links" align="center">
				<a href="https://play.google.com/store/apps/details?id=com.canada.nomme">
					<img alt="Get it on Google Play" style="height: 40px;" src="https://play.google.com/intl/en_us/badges/images/apps/en-play-badge.png" />
				</a>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="https://itunes.apple.com/ca/app/nomme/id1044166421">
					<img alt="Download on the App Store" style="height: 40px;" src="images/apple_app_store_badge.png"/>
				</a>
			</span>
			<span class="col-sm-3">&nbsp;</span>
		</div>
		<jsp:include page="./contactUs.jsp"></jsp:include>
		<jsp:include page="./about.jsp"></jsp:include>
		<jsp:include page="./faq.jsp"></jsp:include>
		<jsp:include page="./termOfUse.jsp"></jsp:include>
		<jsp:include page="./privacyPolicy.jsp"></jsp:include>
	</div>

	<div class="xc-fenlei">
		<%-- <ul name="foods">
			<li><a href="#" name="fastfood"><p><img src="${ctx}/index/images/xc-tb01.png"></p>Fast food</a></li>
			<li><a href="#" name="hamburger"><p><img src="${ctx}/index/images/xc-tb02.png"></p>Hamburg</a></li>
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
