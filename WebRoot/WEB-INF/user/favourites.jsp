<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'favourites.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <!-- huadong6-->
	<div role="tabpanel" class="tab-pane" id="Favorites">
		<div align="center" class="rest-miaodi rest-miaodi-gao"></div>
		<input type="hidden" id = "favouritesTotal" value="${total}">
		<c:if test="${fn:length(favouritesList)<=0}">
			<div class="userpadd">
				<div class="row">
					<div class="col-md-12" align="center" style="padding-top: 20px;padding-bottom: 20px;font-size: 30px;">
						No favourites !
					</div>
				</div>
			</div>
		
		</c:if>
		<c:if test="${fn:length(favouritesList)>0}">
			
			<c:forEach items="${favouritesList}" var="fav" begin="0"  step="1" varStatus="status">
				<div class="userpadd">
					<div class="row">
						<div class="col-md-2">
							<img src="${fav.imageUrl}" class="thumbnail" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" style="width: 120px;">
							 <!-- onerror="noFindImage();" -->
						</div>
						<div class="col-md-7">
							<p><h4 class="aDishButton"><span name="favourite-restaurantName" id="${fav.restaurantUuid}">${fav.restaurantName}</span></h4></p>
							<p >
								<h4>
								<c:if test="${fav.avgStars!=1 && fav.avgStars!=2 && fav.avgStars!=3 && fav.avgStars!=4 && fav.avgStars!=5}">
									<span style="color:#7DD700">☆☆☆☆☆</span>
								</c:if>
								<c:if test="${fav.avgStars==1}">
									<span style="color:#7DD700">★☆☆☆☆</span>
								</c:if>
								<c:if test="${fav.avgStars==2}">
									<span style="color:#7DD700">★★☆☆☆</span>
								</c:if>
								<c:if test="${fav.avgStars==3}">
									<span style="color:#7DD700">★★★☆☆</span>
								</c:if>
								<c:if test="${fav.avgStars==4}">
									<span style="color:#7DD700">★★★★☆</span>
								</c:if>
								<c:if test="${fav.avgStars==5}">
									<span style="color:#7DD700">★★★★★</span>
								</c:if>
								</h4>
							</p>
							
						
							<p></p>
							<p>
								<c:if test="${fav.isPickup==1}">Pick Up &nbsp;&nbsp; </c:if>
								<c:if test="${fav.isDelivery==1}">Delivery &nbsp;&nbsp; </c:if>
								<c:if test="${fav.isReservation==1}">reservation &nbsp;&nbsp; </c:if>
							</p>
							<p>
								Min. Delivery Order: <fmt:formatNumber value="${fav.deliveryPrice}" type="currency" pattern="$ 0.00"/>
							</p>
						</div>
						<div class="col-md-3">
							<p style="height:40px;"></p>
							<button class="btn btn-success-hui btn-block" type="submit" name="deleteFavourite" value="${fav.favouritesId}">Delete</button>
						</div>
					</div>
				</div>
			
			</c:forEach>
		</c:if>
			
		



		<%-- <div class="userpadd">
			<div class="row">
				<div class="col-md-2">
					<div>
						<img src="${ctx}/index/images/user01.jpg" class="thumbnail" style="width: 120px;">
					</div>
				</div>
				<div class="col-md-7">
					<p></p>
					<p>★★★ ★★★★ ★★★★ ２２ Ｒatings</p>
					<p>American,Dinner (5 more)</p>
					<p>$$$$$ | Min:$20 | Free Delivery!</p>
				</div>
				<div class="col-md-3">
					<p style="height:40px;"></p>

					<button class="btn btn-success-hui btn-block" type="submit">Cancle</button>
				</div>
			</div>
		</div> --%>

	</div>
	<!-- huadong6 out -->
  </body>
</html>
