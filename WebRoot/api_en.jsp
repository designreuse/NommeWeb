<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>API TEST</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
  	Login Test
   	<form action="${ctx }/api/consumer/login" method="post">
   		email：<input type="text" name="email">
   		password：<input type="password" name="password">
   		login type：<input type="text" name="loginType" value="1">
   		third party token：<input type="text" name="otherCode">
   		mobileToken(移动设备编号?)：<input type="text" name="mobileToken">
   		<input type="submit" value="submit">
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	Sign Up (create your account)
   	<form action="${ctx }/api/consumer/register" method="post">
   		Firstname：<input type="text" name="firstName">
   		Lastname：<input type="text" name="lastName">
   		email：<input type="text" name="email">
   		password：<input type="password" name="password">
   		<input type="submit" value="submit">
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	modify account information
   	<form action="${ctx }/api/consumer/update" method="post">
   		Firstname：<input type="text" name="firstName">
   		Lastname：<input type="text" name="lastName">
   		email：<input type="text" name="email">
   		password：<input type="password" name="password">
   		ID：<input type="text" name="id">
   		<input type="submit" value="submit">
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	forget password
   	<form action="${ctx }/api/consumer/forget" method="post">
   		email：<input type="text" name="email">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	sent a validation code to reset password (忘记密码验证)
   	<form action="${ctx }/api/consumer/forget/verify" method="post">
   		email：<input type="text" name="email">
   		validation code：<input type="text" name="validationcode">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	input a new password(新密码输入)
   	<form action="${ctx }/api/consumer/forget/new" method="post">
   		email：<input type="text" name="email">
   		password：<input type="password" name="password">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	list of a consumer's addresses(consumer地址列表)
   	<form action="${ctx }/api/consumer/address/list" method="post">
   		ID：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	add a new address(添加consumer地址)
   	<form action="${ctx }/api/consumer/address/add" method="post">
   		consumer's ID：<input type="text" name="id">
   		phone：<input type="text" name="phone">
   		name(收货人)：<input type="text" name="consignee">
   		street：<input type="text" name="street">
   		is default(是否默认)：<input type="text" name="isdefault">
   		unit NO.(楼)：<input type="text" name="floor">
   		city：<input type="text" name="city">
   		province：<input type="text" name="province">
   		postal code：<input type="text" name="zipcode">
   		memo：<input type="text" name="">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	modify address(修改consumer地址)
   	<form action="${ctx }/api/consumer/address/update" method="post">
   		address ID(地址ID??)：<input type="text" name="id">
   		phone：<input type="text" name="phone">
   		name(收货人)：<input type="text" name="consignee">
   		street：<input type="text" name="street">
   		is default(是否默认)：<input type="text" name="isdefault">
   		unit NO.(楼)：<input type="text" name="floor">
   		city：<input type="text" name="city">
   		province：<input type="text" name="province">
   		postal code：<input type="text" name="zipcode">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	remove consumer's address (清除consumer地址)
   	<form action="${ctx }/api/consumer/address/delete" method="post">
   		consumerID：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	list of credit cards (consumer银行卡列表)
   	<form action="${ctx }/api/consumer/paycard/list" method="post">
   		consumer ID：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	add a credit card (添加consumer银行卡)
   	<form action="${ctx }/api/consumer/paycard/add" method="post">
   		consumer ID：<input type="text" name="id">
   		card No.：<input type="text" name="card">
   		expiratioin：<input type="text" name="expiratioin">
   		cvv：<input type="text" name="cvv">
   		is Default(是否默认)：<input type="text" name="isDefault">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	Favorites(收藏列表)
   	<form action="${ctx }/api/consumer/collection/list" method="post">
   		consumer ID：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	add to favorites(添加收藏)
   	<form action="${ctx }/api/consumer/collection/add" method="post">
   		consumer ID：<input type="text" name="rid">
   		restaurantsID：<input type="text" name="cid">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	remove a favorite(删除收藏)
   	<form action="${ctx }/api/consumer/collection/delete" method="post">
   		favorite ID(收藏ID??)：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	Write review(发布评论)
   	<form action="${ctx }/api/consumer/review/add" method="post">
   		consumer ID：<input type="text" name="consumerId">
   		restaurants ID：<input type="text" name="Id">
   		rating：<input type="text" name="score">
   		content：<input type="text" name="content">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	list of past orders(历史订单列表)
   	<form action="${ctx }/api/consumer/order/history/list" method="post">
   		consumerID：<input type="text" name="consumerId">
   		order type：<input type="text" name="orderType">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	details of a past order(历史订单详情)
   	<form action="${ctx }/api/consumer/order/history/detail" method="post">
   		order ID：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	add a new order (订单新增)
   	<form action="${ctx }/api/consumer/order/add" method="post">
   		order detail：<input type="text" name="context" value='{"orderType":1,"consumerId":"21","Id":"10","total":20,"address":"Mon-Fayette Expressway, Brownsville, PA 15417 US","orderDate":"2015/06/2920:30","number":10,"zipcode":"214200","tax":2.1,"tip":1,"logistics":4,"item":[{"dishId":1,"num":1,"unitprice":1,"subItem":[{"garnishItemId":1},{"garnishItemId":2}]}]}'> 
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	restaurants login(登陆)（pad）
   	<form action="${ctx }/api//login" method="post">
   		login ID(登陆名???)：<input type="text" name="code">
   		password：<input type="password" name="password">
   		mobile token(设备编号)：<input type="text" name="token">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	opening times (营业时间显示)
   	<form action="${ctx }/api//opentime" method="post">
   		restaurants ID：<input type="text" name="Id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	order list(订单列表)（pad）?
   	<!-- <form action="${ctx }/api//order" method="post">
	   	restaurantsID：<input type="text" name="Id">
	   	时间：<input type="text" name="createdate">
	   	类型：<input type="text" name="orderType">
	   	订单状态：<input type="text" name="status">
   		<input type="submit" value="submit" >
   	</form> -->
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	handle order(订单处理)（pad）
   	<form action="${ctx }/api//handle" method="post">
	   	order ID：<input type="text" name="id">
	   	status：<input type="text" name="status">
	   	instruction：<input type="text" name="instruction">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	Dish information (菜品信息)
   	<form action="${ctx }/api//dish" method="post">
	  	restaurantsID：<input type="text" name="Id">
	  	type/category(类型??)：<input type="text" name="type">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	restaurants information(餐厅详情)
   	<form action="${ctx }/api//detail" method="post">
	  	restaurants ID：<input type="text" name="id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	restaurants  (餐厅评论？?)
   	<form action="${ctx }/api//comment" method="post">
	  	restaurants ID：<input type="text" name="Id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	coupon and discount(优惠信息)
   	<form action="${ctx }/api//discount" method="post">
	  	restaurants ID：<input type="text" name="Id">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	ingredient type(配菜类型??)
   	<form action="${ctx }/api/all/discounts" method="post">
	  	Dish ID：<input type="text" name="menuId">
   		<input type="submit" value="submit" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  </body>
</html>
