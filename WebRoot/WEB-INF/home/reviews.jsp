<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'reviews.jsp' starting page</title>

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
  <c:forEach items="${reviewsList}" var="evaluate" begin="0"  step="1" varStatus="status">
    <div class="row evaluate_content" >
    	
		<div class="col-md-8" style="padding:10px;" >
			${evaluate.content}
		</div>
		<div class="col-md-4" style="padding:10px;">
			<%-- <div style="color:#7DD700">
				<c:if test="${evaluate.score}==1.0">★☆☆☆☆</c:if>
				<c:if test="${evaluate.score}==2.0">★★☆☆☆</c:if>
				<c:if test="${evaluate.score}==3.0">★★★☆☆</c:if>
				<c:if test="${evaluate.score}==4.0">★★★★☆</c:if>
				<c:if test="${evaluate.score}==5.0">★★★★★</c:if>
			</div> --%>
			<div style="width:50px;text-align:right;float:left;padding-right:10px;">Score:</div>${evaluate.score}<br>
			<div style="width:50px;text-align:right;float:left;padding-right:10px;" >Date:</div>${evaluate.createtime}<br>
			<div style="width:50px;text-align:right;float:left;padding-right:10px;" >name:</div>${evaluate.firstName}
		</div>
		<div class="zhucyemian-miaodi"></div>
	</div>
	</c:forEach>
  </body>
</html>
