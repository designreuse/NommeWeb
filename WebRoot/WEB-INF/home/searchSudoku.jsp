<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$("div[name='restaurant']").click(function(){
		window.location = "${ctx}/index/restaurantmenu?restaurantId="+$(this).children("#id").val();
	});
</script>
<ul>
	<c:forEach items="${viewRestaurants.rows}" var="vr">
		<Li>
		<div name="restaurant" style="cursor: pointer;">
		<input type="hidden" id="id" value="${vr.id}"/>
		<p>
			<c:choose>
				<c:when test="${empty vr.logourl}">
					<img src="${ctx}/images/no-picture.jpg" width="245px" height="245" style="border-radius: 5px;">
				</c:when>
				<c:otherwise>
					<img src="${vr.logourl}" onerror="javascript:this.src='${ctx}/images/no-picture.jpg'" width="245px" height="245" style="border-radius: 5px;">
				</c:otherwise>
			</c:choose>
			
		</p>
		<h5 class="text-center">${vr.restaurantName}</h5>
		<input type="hidden" id="distance" value="${vr.apart}">
		</div>
		<dl class="search_list-right-canshu sxmbian">
			<dt>Distance</dt>
			<dd>&nbsp;&nbsp;&nbsp;&nbsp;${vr.apart}&nbsp;Km</dd>
			<dt>Estimated&nbsp;time</dt>
			<dd>&nbsp;&nbsp;&nbsp;&nbsp;${vr.deliverTime}&nbsp;minutes</dd>
			<div class="clear"></div>
		</dl>
	</Li>
	</c:forEach>
</ul>
<input type="hidden" id="total" value="${viewRestaurants.total}"/>