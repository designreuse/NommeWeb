<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function(){
		  $("div[name='parent']").click(function(){
			  window.location = "${ctx}/index/restaurantmenu?restaurantId="+$(this).children("#id").val();
		}); 
	});
</script>
<c:forEach items="${viewRestaurants.rows}" var="vr">
	<div class="row lcx-m-sx lcx-bian-bottom lcx-p-b" name="parent" style="cursor: pointer;" align="left">
		<input type="hidden" id="id" value="${vr.id}"/>
		<div class="col-md-2 lcx-p-0">
			<c:choose>
				<c:when test="${empty vr.logourl}">
					<img src="${ctx}/images/no-picture.jpg" style="max-width:100%;border-radius: 3px;">
				</c:when>
				<c:otherwise>
					<img src="${vr.logourl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" style="max-width:100%;border-radius: 3px;">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="col-md-3">
			<h4 style="color: #666666;">${vr.restaurantName}</h4>
			<h6 style="color: #999999;">${vr.classificationName}</h6>
			<c:if test="${!empty vr.deliverTime}">
				<h6 style="color: #999999;">${vr.deliverTime}minutes</h6>
			</c:if>
		</div>
		<div class="col-md-1"><br/>
				<c:if test="${!empty vr.avgPrice}">
					<c:if test="${vr.avgPrice<=20}"><span style="color: #7FBF2E">$</span><span style="color: #cccccc">$$$$</span></c:if>
					<c:if test="${vr.avgPrice<=30&&vr.avgPrice>20}"><span style="color: #7FBF2E">$$</span><span style="color: #cccccc">$$$</span></c:if>
					<c:if test="${vr.avgPrice<=40&&vr.avgPrice>30}"><span style="color: #7FBF2E">$$$</span><span style="color: #cccccc">$$</span></c:if>
					<c:if test="${vr.avgPrice<=50&&vr.avgPrice>40}"><span style="color: #7FBF2E">$$$$</span><span style="color: #cccccc">$</span></c:if>
					<c:if test="${vr.avgPrice>50}"><span style="color: #7FBF2E">$$$$$</span></c:if>
				</c:if>
		</div>
		
		<div class="col-md-2" >
			<h5 style="color: #666666;">${vr.isdelivery==1?'Delivery':''}<br>${vr.ispickup==1?' Pick-up':''}<br>${vr.isreservation==1?' Reservation':''}</h5>
		</div>
		
		<div class="col-md-2"><br/>
			<c:if test="${!empty vr.score}">
				<c:if test="${vr.score==5}">
					<span style="color: #7FBF2E">★★★★★</span>
				</c:if>
				<c:if test="${vr.score==4}">
					<span style="color: #7FBF2E">★★★★</span><span style="color: #cccccc">☆</span>
				</c:if>
				<c:if test="${vr.score==3}">
					<span style="color: #7FBF2E">★★★</span><span style="color: #cccccc">☆☆</span>
				</c:if>
				<c:if test="${vr.score==2}">
					<span style="color: #7FBF2E">★★</span><span style="color: #cccccc">☆☆☆</span>
				</c:if>
				<c:if test="${vr.score==1}">
					<span style="color: #7FBF2E">★</span><span style="color: #cccccc">☆☆☆☆</span>
				</c:if>
			</c:if>
			<c:if test="${empty vr.score}">
				<span style="color: #cccccc">☆☆☆☆☆</span>
			</c:if>
			<br/>
			<span style="color: #999999;font-size: xx-small;">(${empty vr.scoreCount?'0':vr.scoreCount} Ratings)</span>
		</div>
		<div class="col-md-2">
			<c:if test="${!empty vr.apart}">
				<br/><h6>${vr.apart}km</h6>
			</c:if>
		</div>
		<input type="hidden" id="distance" value="${vr.apart}">
	</div>
	<hr style="margin: 0px;" />
</c:forEach>

<input type="hidden" id="total" value="${viewRestaurants.total}"/>
