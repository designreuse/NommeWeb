<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>Nearby Restaurants - Nomme</title>
<meta name="description" content="Restaurants near your location.">
<meta name="keywords" content="Calgary,Asian,Canadian,Chinese,Coffee,Dinner,Thai,Japanese,Bubbletea">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ceshi</title>
<link rel="stylesheet" href="${ctx}/index/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx}/index/css/search_list.css">
<link rel="stylesheet" href="${ctx}/index/css/commcss.css">
<link rel="stylesheet" href="${ctx}/css/loading.css">

<style type="text/css">
	#map-canvas {
	height:500px;
	margin: 0px;
	padding: 0px
}
</style>

<script src="${ctx}/index/js/jquery.min.js"></script>
<script src="${ctx}/index/js/jquery.cookie.js"></script>
<script src="${ctx}/index/js/bootstrap.min.js"></script>
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true"></script>
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
							<img src="${ctx}/index/images/xc-logo.png" alt="nom logo">
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
					<!-- Modal -->
					<jsp:include page="./inputjsp.jsp"></jsp:include>
					
				</div>
			</div>
		</div>

	</div>


	<div class="zuikuan bgbai">
		<!--zuocekaishi-->
		<div class="search_list-left">
			<div class="bgsearch_list01">
				Cuisine <a href="javascript:void(0)" class="cursorPointer"><span class="search_span cursorPointer" name="clearCuisine">Clear</span></a>
			</div>
			<div class="search_list-left-fuxuan" name="cuisineCheckboxList">
					<c:forEach begin="0" end="4" items="${classifications}" step="1" var="cf">
						<li><label class="checkbox"><input type="checkbox"
						value="${cf.classificationName}">${cf.classificationName}</label></li>
					</c:forEach>
					<div class="collapse" id="collapseExample">
						<c:forEach begin="5" items="${classifications}" step="1" var="cf">
							<li><label class="checkbox"><input type="checkbox"
							value="${cf.classificationName}">${cf.classificationName}</label></li>
						</c:forEach>
				</div>

			</div>
			<div align="center" class="search_shou">

				<a data-toggle="collapse" href="#collapseExample"
					aria-expanded="false" aria-controls="collapseExample"><span style="color: #7FBF2E">See all</span>
				</a>
			</div>

			<div class="bgsearch_list02">
				Ratings <a href="javascript:void(0)" class="cursorPointer"><span class="search_span" name="clearStar" >Clear</span></a>
			</div>

			<div class="search_list-left-fuxuan" style="padding-left: 10px;font-weight: bold;">
				<li name="starGroup">
					<span name="star" title="s1" style="cursor:pointer;margin-right:7px;">☆</span>
					<span name="star" title="s2" style="cursor:pointer;margin-right:7px;">☆</span>
					<span name="star" title="s3" style="cursor:pointer;margin-right:7px;">☆</span>
					<span name="star" title="s4" style="cursor:pointer;margin-right:7px;">☆</span>
					<span name="star" title="s5" style="cursor:pointer;margin-right:7px;">☆</span>
				</li>
			</div>


			<div class="bgsearch_list03">
				Price <a href="javascript:void(0)" class="cursorPointer"><span class="search_span" name="clearDollar">Clear</span></a>
			</div>
			<div class="search_list-left-fuxuan" style="padding-left: 10px;font-weight: bold;">
				<li name="dollarGroup">
					<span name="dollar" title="d1" style="cursor:pointer;margin-right:13px;font-size:16px;">$</span>
					<span name="dollar" title="d2" style="cursor:pointer;margin-right:13px;font-size:16px;">$</span>
					<span name="dollar" title="d3" style="cursor:pointer;margin-right:13px;font-size:16px;">$</span>
					<span name="dollar" title="d4" style="cursor:pointer;margin-right:13px;font-size:16px;">$</span>
					<span name="dollar" title="d5" style="cursor:pointer;margin-right:13px;font-size:16px;">$</span>
				</li>
			</div>

			<div class="bgsearch_list04">
				Features <a href="javascript:void(0)" class="cursorPointer"><span class="search_span" name="clearFeatures">Clear</span></a>
			</div>
			<div class="search_list-left-fuxuan" name="featuresCheckboxList">
				<li><label class="checkbox"><input type="checkbox" id="coupons"
						>Coupons Available</label></li>
				<li><label class="checkbox"><input type="checkbox" id="open"
						>Open Now ${date}</label></li>
				<li><label class="checkbox"><input type="checkbox" id="delivery"
						>Delivery</label></li>
				<li><label class="checkbox"><input type="checkbox" id="reservation"
						>Reservation</label></li>		
			</div>
		</div>
		<!--zuocejieshu-->
		<!--youcekaishi-->
		<div class="search_list-right">
			<div class="search_list-right-top">

				<div role="tabpanel">

					<!-- Nav tabs -->
					<ul class="nav_search bgsearch_list05" role="tablist">
						
						<li role="presentation"><a
							role="menuitem" tabindex="-1" href="#" id="orderbydistance">Distance</a></li>
						<li role="presentation"><a
							role="menuitem" tabindex="-1" href="#" id="orderbya">A-Z</a></li>
						<li role="presentation"><a
							role="menuitem" tabindex="-1" href="#" id="orderbyprice">Price</a></li>
						<li role="presentation"><a
							role="menuitem" tabindex="-1" href="#" id="orderbyrating">Rating</a></li>
							
						<li role="presentation"><a class="showType" href="#showBanner"
							aria-controls="showBanner" role="tab" data-toggle="tab"><img
								src="${ctx}/index/images/search_list-02.png"></a></li>
						<li role="presentation"><a class="showType" href="#showSudoku" 
							aria-controls="showSudoku" role="tab" data-toggle="tab"><img
								src="${ctx}/index/images/search_list-03.png"></a></li>
						<li role="presentation">
							<a class="showType" href="#map" aria-controls="map" role="tab" data-toggle="tab">
								<span>Map View</span>
								<img src="${ctx}/index/images/search_list-04.png">
							</a>
						</li>
					</ul>
					

					<!-- Tab panes -->
					<div class="tab-content">
						<div id="totalRestaurants">
							<!-- 餐厅总数 -->
						</div>
						<div role="tabpanel" class="tab-pane active" id="showBanner">
							<!-- js加载餐厅信息 -->
						</div>
						<div role="tabpanel" class="tab-pane" id="showSudoku">
							<div class="search_list-right-can">
								<ul id="restaurants">
									<!-- js加载餐厅信息 -->
								</ul>
							</div>
						</div>
						<div role="tabpanel" class="tab-pane" id="map">
							<div id="map-canvas"></div>
						</div>
					</div>
					
				</div>
				
			</div>
		</div>
		
		<div class="clear"></div>
		<div id="pp" style="margin-left: 50%">
			<ul id="paginator"></ul>
		</div>
		
	</div>
	
	
	
	<!-- dibu kaishi-->

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
	<form id="fm" method="post">
	<input type="hidden" id="restaurantLng" name="restaurantLng" value="${restaurantLng}"/>
	<input type="hidden" id="restaurantLat" name="restaurantLat" value="${restaurantLat}"/>
	<input type="hidden" id="classificationName" name="classificationName" value="${classification}">
	<input type="hidden" id="score" name="score"/>
	<input type="hidden" id="avgPrice" name="avgPrice">
	<input type="hidden" id="discountNum" name="discountNum">
	<input type="hidden" id="opentime" name="opentime"/>
	<input type="hidden" id="isdelivery" name="isdelivery"/>
	<input type="hidden" id="isreservation" name="isreservation" />
	<input type="hidden" id="only" name="only" value="${classification}"/>
	</form>
	<script src="${ctx}/framework/plugins/paginator/bootstrap-paginator.js"></script>
	<script src="${ctx}/index/js/searchList.js"></script>
	
</body>



</html>
