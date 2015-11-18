<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nomme</title>
<meta
	content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no'
	name='viewport'>
<jsp:include page="../inputcss.jsp"></jsp:include>

<style type="text/css">
iframe {
	width: 100%;
	height: 690px;
	overflow: auto;
}
</style>
</head>
<body class="skin-blue">
	<!-- header logo: style can be found in header.less -->
	<header class="header">

		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top" role="navigation"
			style="margin-left: 0px;">
			<!-- Sidebar toggle button-->

			<div class="navbar-right">
				<ul class="nav navbar-nav">
					<!-- Messages: style can be found in dropdown.less-->
					<!-- User Account: style can be found in dropdown.less -->
					<li class="dropdown user user-menu"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="glyphicon glyphicon-user"></i> <span>${sessionScope.restaurantsUser.firstName}
								${sessionScope.restaurantsUser.lastName} <i class="caret"></i>
						</span>
					</a>
						<ul class="dropdown-menu">
							<!-- User image -->
							<!-- Menu Footer-->
							<li class="user-footer">
								<div >
									<a href="${ctx}/restaurant/signOut" class="btn btn-default btn-flat">Sign out</a>
								</div>
							</li>
						</ul></li>
				</ul>
			</div>
		</nav>
	</header>
	<div class="wrapper">
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="left-side sidebar-offcanvas">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">

				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li><a href="${ctx}/restaurant/staffmanagement" name="nav-btn-employees"
						target="iframe"> <i class="glyphicon glyphicon-user"></i> <span>Employees
								</span>
					</a></li>
					<li><a href="${ctx}/information/information" target="iframe">
							<i class="glyphicon glyphicon-wrench"></i> <span>Restaurant
								information</span>
					</a></li>
					<li><a href="javascript:void(0)" onclick="menu()"><i
							class="glyphicon glyphicon-cutlery"></i> <span>Ingredient</span></a>
						<ul id="menu" style="list-style: none;background-color: #eeeeee;display: none;">
							<li style="margin-bottom: 5px;"><a href="${ctx}/garnish/toGarnishHeader" target="iframe">Ingredient category</a></li>
							<li><a href="${ctx}/garnish/toGarnish" target="iframe">Ingredient</a></li>
						</ul></li>
					<li><a href="javascript:void(0)" onclick="menu1()"><i
							class="glyphicon glyphicon-leaf"></i> <span>Dish</span></a>
						<ul id="menu1" style="list-style: none;background-color: #eeeeee;display: none;">
							<li style="margin-bottom: 5px;"><a href="${ctx}/dish/toDishMenu" target="iframe">Dish category</a></li>
							<li><a href="${ctx}/dish/toDish" target="iframe">Dish</a></li>
						</ul></li>
					<li><a href="${ctx}/table/toTable"  target="iframe"> <i
							class="glyphicon glyphicon-th"></i> <span>Table</span>
					</a></li>
					<li><a href="${ctx}/opentime/toOpenTime"  target="iframe"> <i
							class="glyphicon glyphicon-dashboard"></i> <span>Business hours</span> 
					</a></li>
					<li><a href="${ctx}/order/toordermanagementpage"  target="iframe"> <i
							class="glyphicon glyphicon-shopping-cart"></i> <span>Order</span> 
					</a></li>
					<li><a href="${ctx}/distancePrice/toDistancePrice"  target="iframe"> <i
							class="glyphicon glyphicon-resize-full"></i> <span>Delivery Rules</span> 
					</a></li>
					<li><a href="${ctx}/discount/toDiscount"  target="iframe"> <i
							class="glyphicon glyphicon-usd"></i> <span>Promotion</span> 
					</a></li>
					<li><a href="${ctx}/stripe/getStripeAccount"  target="iframe"> <i
							class="glyphicon glyphicon-usd"></i> <span>Stripe account</span> 
					</a></li>
					<li><a href="${ctx}/restaurantStatement/getRestaurantStatementPage"  target="iframe"> <i
							class="glyphicon glyphicon-list-alt"></i> <span>Order Statement</span> 
					</a></li>

				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Right side column. Contains the navbar and content of the page -->
		<aside class="right-side sidebar-offcanvas">
			<section class="sidebar">
				<iframe name="iframe" style="border: 0px;"></iframe>
			</section>
			<!-- /.content -->
		</aside>
		<!-- /.right-side -->
	</div>
	<!-- ./wrapper -->

	<!-- add new calendar event modal -->
	<jsp:include page="../inputjs.jsp"></jsp:include>
	<script type="text/javascript">
		function menu(){
			$("#menu").toggle(300);
		}
		function menu1(){
			$("#menu1").toggle(300);
		}
		
		$(".sidebar li").click(function(){
			$(this).css('background-color','#dddddd');
			$(".sidebar li").not($(this)).css('background-color','#F4F4F4');
		});
		
		$(function(){
			$("iframe").attr("src", "${ctx}/restaurant/staffmanagement"); 
		});
		
	</script>
</body>

</html>


