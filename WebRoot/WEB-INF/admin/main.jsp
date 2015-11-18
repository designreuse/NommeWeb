<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nomme System | Manager</title>
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

<!-- <script type="text/javascript">
	//iframe自适应高度的函数
	var oTime = null;
	function resize() {
	    if (oTime) {
	        clearTimeout(oTime);
	    }
	    oTime = setTimeout(reset, 200);
	}
	
	//iframe自适应高度的函数
	function reset() {
	    var frame = document.getElementById("iframe1");
	    var outHeight = frame.offsetHeight;
	    var inHeight = frame.contentWindow.document.body.scrollHeight;
	    if (outHeight < inHeight) {
	        frame.style.height = (inHeight + 10) + "px";
	    }else if(inHeight>650){
	        frame.style.height=(inHeight + 10) + "px";
	    }else{
	        frame.style.height="750px";
	    }
	}
</script> -->

<body class="skin-blue">
	<!-- header logo: style can be found in header.less -->
	<header class="header">
		<a href="${ctx}/admin/main" class="logo"> <!-- Add the class icon to your logo image or logo icon to add the margining -->
			nomme
		</a>
		<!-- Header Navbar: style can be found in header.less -->
		<nav class="navbar navbar-static-top" role="navigation">
			<!-- Sidebar toggle button-->
			<a href="#" class="navbar-btn sidebar-toggle" data-toggle="offcanvas"
				role="button"> <span class="sr-only">Toggle navigation</span> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
			</a>
			<div class="navbar-right">
				<ul class="nav navbar-nav">
					<!-- Messages: style can be found in dropdown.less-->
					<%-- <li class="dropdown messages-menu">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
							<i class="fa fa-envelope"></i> 
							<span class="label label-success">4</span>
						</a>
						<ul class="dropdown-menu">
							<li class="header">You have 4 messages</li>
							<li>
								<!-- inner menu: contains the actual data -->
								<ul class="menu">
									<li>
										<!-- start message --> <a href="#">
											<div class="pull-left">
												<img src="${ctx }/img/avatar3.png" class="img-circle"
													alt="User Image" />
											</div>
											<h4>
												Support Team <small><i class="fa fa-clock-o"></i> 5
													mins</small>
											</h4>
											<p>Why not buy a new awesome theme?</p>
									</a>
									</li>
									<!-- end message -->
									<li><a href="#">
											<div class="pull-left">
												<img src="${ctx }/img/avatar2.png" class="img-circle"
													alt="user image" />
											</div>
											<h4>
												AdminLTE Design Team <small><i class="fa fa-clock-o"></i>
													2 hours</small>
											</h4>
											<p>Why not buy a new awesome theme?</p>
									</a></li>
									<li><a href="#">
											<div class="pull-left">
												<img src="${ctx }/img/avatar.png" class="img-circle"
													alt="user image" />
											</div>
											<h4>
												Developers <small><i class="fa fa-clock-o"></i>
													Today</small>
											</h4>
											<p>Why not buy a new awesome theme?</p>
									</a></li>
									<li><a href="#">
											<div class="pull-left">
												<img src="${ctx }/img/avatar2.png" class="img-circle"
													alt="user image" />
											</div>
											<h4>
												Sales Department <small><i class="fa fa-clock-o"></i>
													Yesterday</small>
											</h4>
											<p>Why not buy a new awesome theme?</p>
									</a></li>
									<li><a href="#">
											<div class="pull-left">
												<img src="${ctx }/img/avatar.png" class="img-circle"
													alt="user image" />
											</div>
											<h4>
												Reviewers <small><i class="fa fa-clock-o"></i> 2
													days</small>
											</h4>
											<p>Why not buy a new awesome theme?</p>
									</a></li>
								</ul>
							</li>
							<li class="footer"><a href="#">See All Messages</a></li>
						</ul></li> --%>
					<!-- Notifications: style can be found in dropdown.less -->

					<!-- Tasks: style can be found in dropdown.less -->

					<!-- User Account: style can be found in dropdown.less -->
					<li class="dropdown user user-menu"><a href="#"
						class="dropdown-toggle" data-toggle="dropdown"> <i
							class="glyphicon glyphicon-user"></i>
							${sessionScope.adminUserLoginname.firstName} ${sessionScope.adminUserLoginname.lastName}
							<span><i class="caret"></i></span>
					</a>
						<ul class="dropdown-menu">
							
							<!-- Menu Footer-->
							<li class="user-footer">
								
								<div class="pull-right">
									<a href="${ctx}/admin/signout" class="btn btn-default btn-flat" id="signOut">Sign out</a>
								</div>
							</li>
						</ul></li>
				</ul>
			</div>
		</nav>
	</header>
	<div class="wrapper"><!--  row-offcanvas row-offcanvas-left -->
		<!-- Left side column. contains the logo and sidebar -->
		<aside class="left-side sidebar-offcanvas">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li><a href="${ctx}/admin/adminmanagepage" target="iframe1">
							<i class="glyphicon glyphicon-user" id="admin"></i> <span>System Admin</span>
					</a></li>
					<li><a href="${ctx}/admin/areaandtaxpage" target="iframe1"> <i
							class="glyphicon glyphicon-map-marker"></i> <span>Area And Tax Rate </span>
					</a></li>
					<li><a href="${ctx}/admin/restaurantauditpage" target="iframe1"> <i
							class="glyphicon glyphicon-check"></i> <span>Restaurant Verify</span>
					</a></li>
					<li><a href="${ctx}/admin/restaurantadminauditpage" target="iframe1"> <i
							class="fa fa-group"></i> <span>Restaurant user</span>
					</a></li>
					<li><a href="${ctx}/admin/chainmanagepage" target="iframe1"> <i
							class="glyphicon glyphicon-retweet"></i> <span>Chain Store</span>
					</a></li>
					<li><a href="${ctx}/admin/cuisinepage" target="iframe1"> <i
							class="glyphicon glyphicon-cutlery"></i> <span>Cuisine</span>
					</a></li>
					<li><a href="${ctx}/admin/charityPage" target="iframe1"> <i
							class="glyphicon glyphicon-heart-empty"></i> <span>Charity</span>
					</a></li>
					<li><a href="${ctx}/adminStatement/userStatementPage" target="iframe1"> <i
							class="glyphicon glyphicon-list-alt"></i> <span>User Statement</span>
					</a></li>
					<li><a href="${ctx}/adminStatement/restaurantStatementPage" target="iframe1"> <i
							class="glyphicon glyphicon-list-alt"></i> <span>Restaurants Statement</span>
					</a></li>
					<li><a href="${ctx}/adminStatement/donationStatementPage" target="iframe1"> <i
							class="glyphicon glyphicon-list-alt"></i> <span>Donation Statement</span>
					</a></li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Right side column. Contains the navbar and content of the page -->
		<aside class="right-side sidebar-offcanvas" style="height: auto;">
			
			<section class="sidebar">
				<div id="page-content"></div>
			
				<iframe id="iframe1" name="iframe1" frameborder="0" style="width:100%;" ></iframe>
				<!-- onreadystatechange="resize()" onload="resize()" -->
			</section><!-- /.content -->
		</aside>
		<!-- /.right-side -->
	</div>
	<!-- ./wrapper -->

	<!-- add new calendar event modal -->


	<jsp:include page="../inputjs.jsp"></jsp:include>
<style>

</style>
<script type="text/javascript">
	$(function(){
		$("ul[class='sidebar-menu'] li").click(function(){//左侧菜单栏点击后改变颜色
			$("ul[class='sidebar-menu'] li").attr("style","background-color:#f4f4f4");
			$(this).attr("style","background-color:#dddddd");
		});
	
	});
	
</script>

</body>
</html>


