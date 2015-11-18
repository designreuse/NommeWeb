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
  	用户登陆
   	<form action="${ctx }/api/consumer/login" method="post">
   		邮箱地址：<input type="text" name="email" value="">
   		密码：<input type="password" name="password" value="">
   		登陆类型：<input type="text" name="loginType" value="4">
   		第三方token：<input type="text" name="otherCode" value="1459795887668324">
   		移动设备编号：<input type="text" name="mobileToken" value="d0b60c9cc35fbd8850944b742ac3c1e2be54a056198b96af0e3deeb220b5c667">
   		移动设备类型：<input type="text" name="mobileType" value="1">
   		昵称：<input type="text" name="nickname" value="Youlin Yuan">
   		<input type="submit" value="提交">
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户注册
   	<form action="${ctx }/api/consumer/register" method="post">
   		Fristname：<input type="text" name="firstName" value="z">
   		Lastname：<input type="text" name="lastName" value="f">
   		邮箱地址：<input type="text" name="email" value="asd@qq.com">
   		密码：<input type="password" name="password" value="123123">
   		<input type="submit" value="提交">
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户信息修改
   	<form action="${ctx }/api/consumer/update" method="post">
   		Fristname：<input type="text" name="firstName" value="Yo">
   		Lastname：<input type="text" name="lastName" value="Ki">
   		邮箱地址：<input type="text" name="email" value="892981821@qq.com">
   		Phone：<input type="text" name="phone" value="1234">
   		原始密码：<input type="password" name="password" value="123123">
   		新密码：<input type="password" name="newpassword" value="">
   		用户ID：<input type="text" name="consumerId" value="1">
   		<input type="submit" value="提交">
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	忘记密码
   	<form action="${ctx }/api/consumer/forget" method="post">
   		邮箱地址：<input type="text" name="email" value="123456@qq.com">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	忘记密码验证
   	<form action="${ctx }/api/consumer/forget/verify" method="post">
   		邮箱地址：<input type="text" name="email" value="123456@qq.com">
   		验证码：<input type="text" name="validateCode">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	新密码输入
   	<form action="${ctx }/api/consumer/forget/new" method="post">
   		邮箱地址：<input type="text" name="email" value="qqqqqqqasd@qq.com">
   		新密码：<input type="password" name="newpassword" value="111">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户地址列表
   	<form action="${ctx }/api/consumer/address/list" method="post">
   		用户ID：<input type="text" name="consumerId" value="54">
   		商家ID：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	添加用户地址
   	<form action="${ctx }/api/consumer/address/add" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		省：<input type="text" name="province" value="jiangshu">
   		城市：<input type="text" name="city" value="changzhou">
   		街道：<input type="text" name="street" value="新北区 万达广场">
   		楼：<input type="text" name="floor" value="fff">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	<%-- 修改用户地址
   	<form action="${ctx }/api/consumer/address/update" method="post">
   		地址ID：<input type="text" name="addressId" value="34">
   		省：<input type="text" name="province" value="AB">
   		城市：<input type="text" name="city" value="cv">
   		街道：<input type="text" name="street" value="1">
   		楼：<input type="text" name="floor" value="11">
   		是否默认：<input type="text" name="isdefault" value="1">
   		<input type="submit" value="提交" >
   	</form> --%>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	<%-- 清除用户地址
   	<form action="${ctx }/api/consumer/address/delete" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		<input type="submit" value="提交" >
   	</form> --%>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	<%-- 用户银行卡列表
   	<form action="${ctx }/api/consumer/paycard/list" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		<input type="submit" value="提交" >
   	</form> --%>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	<%-- 添加用户银行卡
   	<form action="${ctx }/api/consumer/paycard/add" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		银行卡token：<input type="text" name="paycardToken" value="1">
   		<input type="submit" value="提交" >
   	</form> --%>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	<%-- 删除用户银行卡
   	<form action="${ctx }/api/consumer/paycard/deleteall" method="post">
   		银行卡卡号：<input type="text" name="card" value="444444">
   		<input type="submit" value="提交" >
   	</form> --%>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	已完成订单列表
   	<form action="${ctx }/api/consumer/order/history/list" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		<!-- 订单类型：<input type="text" name="orderType" value="1"> -->
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	未完成订单列表
   	<form action="${ctx }/api/consumer/order/current/list" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	订单详情
   	<form action="${ctx }/api/consumer/order/history/detail" method="post">
   		订单ID：<input type="text" name="orderId" value="12">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户订单新增
   	<form action="${ctx }/api/consumer/order/add" method="post">
   		订单内容：<input type="text" name="context" value='
   		{
    "firstName": "z",
    "lastName": "x",
    "phone": "123",
    "email": "123@qq.com",
    "orderType": 1,
    "consumerId": "46",
    "restaurantId": "16",
    "addressId": 27,
    "orderDate": "2015/06/29 20:30",
    "number": 10,
    "zipcode": "214200",
    "tax": 2.1,
    "tip": 1,
    "logistics": 4,
    "charityId": 1,
    "discountId": 49,
    "payment": 1,
    "cardId": "card_16YAOkK629Jm0Bi8Xslr3qwT",
    "meno": "hao",
    "item": [
        {
            "dishId": 1,
            "num": 1,
            "unitprice": 1,
            "subItem": [
                {
                    "garnishItemId": 1,
                    "garnishNum": 2
                },
                {
                    "garnishItemId": 4,
                    "garnishNum": 4
                }
            ]
        }
    ]
}'> 
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户添加收藏
   	<form action="${ctx }/api/consumer/collection/add" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		店铺ID：<input type="text" name="restaurantId" value="4">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户删除收藏
   	<form action="${ctx }/api/consumer/collection/delete" method="post">
   		收藏ID：<input type="text" name="favoriteId" value="4">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户收藏列表
   	<form action="${ctx }/api/consumer/collection/list" method="post">
   		用户ID：<input type="text" name="consumerId" value="4">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	发布评论
   	<form action="${ctx }/api/consumer/review/add" method="post">
   		用户ID：<input type="text" name="consumerId" value="1">
   		店铺ID：<input type="text" name="restaurantId" value="4">
   		订单ID：<input type="text" name="orderHeaderId" value="949">
   		评分：<input type="text" name="score" value="4">
   		评论内容：<input type="text" name="content" value="zxcteatccq">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店铺登陆（pad）
   	<form action="${ctx }/api/restaurant/login" method="post">
   		店铺登陆名：<input type="text" name="code" value="timli@qq.com">
   		密码：<input type="password" name="password" value="123123">
   		设备编号：<input type="text" name="token" value="1439650654055">
   		设备类型：<input type="text" name="devicetype" value="0">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	营业时间显示
   	<form action="${ctx }/api/restaurant/opentime" method="post">
   		店铺ID：<input type="text" name="restaurantId" value="16">
   		类型：<input type="text" name="type" value="1">
   		年月日：<input type="text" name="date" value="2015-8-14">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店家订单列表（pad）
   	<form action="${ctx }/api/restaurant/order" method="post">
	   	店铺ID：<input type="text" name="restaurantId" value="16">
	   	时间：<input type="text" name="createdate" value="2015-10-17">
	   	类型：<input type="text" name="orderType" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	订单处理（pad）
   	<form action="${ctx }/api/restaurant/handle" method="post">
	   	订单ID：<input type="text" name="orderId" value="13">
	   	处理状态：<input type="text" name="status" value="2">
	   	备注信息：<input type="text" name="instruction" value="hot...hot...">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	菜品信息
   	<form action="${ctx }/api/restaurant/dish" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	类型：<input type="text" name="type" value="3">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店铺详情
   	<form action="${ctx }/api/restaurant/detail" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	用户ID：<input type="text" name="consumerId" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店铺评论
   	<form action="${ctx }/api/restaurant/comment" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="21">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店铺优惠信息
   	<form action="${ctx }/api/restaurant/discount" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	订单类型：<input type="text" name="orderType" value="3">
	  	消费金额：<input type="text" name="consumePrice" value="50.00">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取配送费
   	<form action="${ctx }/api/restaurant/distanceprice" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	<!-- 总金额：<input type="text" name="total" value="4"> -->
	  	<!-- 距离：<input type="text" name="distance" value="4"> -->
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	菜品分类
   	<form action="${ctx }/api/restaurant/garnishheader" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	类型：<input type="text" name="type" value="3">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取商家桌位数量
   	<form action="${ctx }/api/restaurant/tablecount" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	预定时间：<input type="text" name="orderDate" value="2015-06-13 9:20">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取菜单信息
   	<form action="${ctx }/api/restaurant/dish/menu" method="post">
	  	店铺ID：<input type="text" name="restaurantId" value="4">
	  	类型：<input type="text" name="type" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	配菜类型
   	<form action="${ctx }/api/restaurant/garnish/menu" method="post">
	  	菜品ID：<input type="text" name="dishId" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	配菜信息
   	<form action="${ctx }/api/restaurant/garnish/item" method="post">
	  	菜品ID：<input type="text" name="dishId" value="60">
	  	菜品分类ID：<input type="text" name="garnishMenuId" value="19">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	搜索
   	<form action="${ctx }/api/search" method="post">
	  	经度：<input type="text" name="restaurantLng" value="0.0">
	  	纬度：<input type="text" name="restaurantLat" value="0.0">
	  	人均排序：<input type="text" name="avgPrice" value="0">
	  	距离排序：<input type="text" name="distance" value="0">
	  	评分排序：<input type="text" name="stars" value="0">
	  	首字母排序：<input type="text" name="letter" value="0">
	  	餐厅类型：<input type="text" name="classification" value="null">
	  	是否可以在线预订：<input type="text" name="isDiscount" value="">
	  	是否在营业：<input type="text" name="isOpen" value="">
	  	是否有优惠：<input type="text" name="isReservation" value="">
	  	是否提供外送：<input type="text" name="isDelivery" value="">
	  	第几页：<input type="text" name="currentPageIndex" value="1">
	  	菜系ID：<input type="text" name="classificationId" value="1"> 
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	<%-- 获取商家数量
   	<form action="${ctx }/api/search/count" method="post">
	  	经度：<input type="text" name="restaurantLng" value="-114.06415">
	  	纬度：<input type="text" name="restaurantLat" value="51.04500">
	  	餐厅类型：<input type="text" name="classificationName" value="Italian,Pizza,Sea food">
	  	是否有优惠：<input type="text" name="discountNum" value="1">
	  	是否在营业：<input type="text" name=opentime value="1">
	  	是否可以在线预订：<input type="text" name="isdelivery" value="1">
	  	是否提供外送：<input type="text" name="isDelivery" value="1">
   		<input type="submit" value="提交" >
   	</form> --%>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  	商家分类
   	<form action="${ctx }/api/consumer/classification" method="post">
   		<input type="submit" value="提交" >
   	</form>
  	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  	登陆获取购物车有无信息
   	<form action="${ctx }/api/consumer/cart/have" method="post">
   		设备token：<input type="text" name="mobileToken" value="abc">
   		<input type="submit" value="提交" >
   	</form>
   	---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  	删除购物车信息
   	<form action="${ctx }/api/consumer/cart/delete" method="post">
   		设备token：<input type="text" name="mobileToken" value="abc">
   		用户Id：<input type="text" name="consumerId" value="">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	添加购物车信息
   	<form action="${ctx }/api/consumer/cart/add" method="post">
   		设备token：<input type="input" name="context" value='{
    "orderType": "1",
    "mobileToken": "D2FF3F09-4A79-495B-8A8E-9F056A88D4DB",
    "consumerId": "45",
    "restaurantId": "31",
    "restaurantLng": 0.4,
    "restaurantLat": 0.1,
    "type": 2,
    "item": {
        "dishId": 72,
        "num": 1,
        "unitprice": 9.75,
        "instruction": "\\U6d3b\\U8131\\U8131",
        "subItem": [
            {
                "garnishItemId": 1,
                "count": 2
            },
            {
                "garnishItemId": 4,
                "count": 5
            }
        ]
    }
}'>
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取购物车信息
   	<form action="${ctx }/api/consumer/cart/getInfo" method="post">
   		设备token：<input type="text" name="mobileToken" value="EC-55-F9-C4-7D-4C">
   		用户id：<input type="text" name="consumerId" value="45">
   		<!-- 经度：<input type="text" name="consumerLng" value="-114.12186">
   		纬度：<input type="text" name="consumerLat" value="51.08358"> -->
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取快捷菜单
   	<form action="${ctx }/api/consumer/index/type" method="post">
   		用户id：<input type="text" name="consumerId" value="21">
   		设备类型：<input type="text" name="type" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	取消订单
   	<form action="${ctx }/api/consumer/order/cancel" method="post">
   		订单id：<input type="text" name="orderId" value="13">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	重复下订单
   	<form action="${ctx }/api/consumer/repeat" method="post">
   		用户id：<input type="text" name="consumerId" value="21">
   		订单id：<input type="text" name="orderId" value="56">
   		设备token：<input type="text" name="mobiletoken" value="reth21453wesrg">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	删除购物车中单个菜品
   	<form action="${ctx }/api/consumer/cart/deleteone" method="post">
   		设备号：<input type="text" name="mobileToken" value="21">
   		菜品id：<input type="text" name="dishId" value="56">
   		用户id：<input type="text" name="consumerId" value="76">	
   		经度：<input type="text" name="consumerLng" value="-114.12186">
   		纬度：<input type="text" name="consumerLat" value="51.08358"> 	
   		<input type="submit" value="提交">
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店家已取消的订单
   	<form action="${ctx }/api/restaurant/cancelorder" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">   		
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店家已完成的订单
   	<form action="${ctx }/api/restaurant/completeorder" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">
   		订单类型：<input type="text" name="status" value="1">   		
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	店家当天未处理的订单id
   	<form action="${ctx }/api/restaurant/live" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	非当天未处理的订单id
   	<form action="${ctx }/api/restaurant/upcoming" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	当天已处理的订单列表
   	<form action="${ctx }/api/restaurant/liveaccept" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	非当天已处理的订单列表
   	<form action="${ctx }/api/restaurant/upcomingaccept" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	当天营业总金额
   	<form action="${ctx }/api/restaurant/totalAmount" method="post">
   		店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	购物车选择优惠
   	<form action="${ctx }/api/consumer/cart/sDiscount" method="post">
   		设备编号 <input type="text" name="mobileToken" value="yyl123456789" >
   		纬度restaurantLat<input type="text" name="restaurantLat" value="51.18751"   >
   		经度restaurantLng<input type="text" name="restaurantLng" value="-114.13954" >
   		折扣ID <input type="text" name="discountId" value="43" >   
   		店家id：<input type="text" name="restaurantId" value="16">
   		客户id： <input type="text" name="consumerId" value="76" >   			
   		<input type="submit" value="提交" >
   	</form>
	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	保存信用卡信息
   	<form action="${ctx }/api/consumer/stripe/addCard" method="post">
   		客户id： <input type="text" name="consumerId" value="1" >   		
   		卡号：<input type="text" name="number" value="4242424242424242">
   		过期月： <input type="text" name="exp_month" value="5" >
   		过期年：<input type="text" name="exp_year" value="2017"   >
   		cvc<input type="text" name="cvc" value="123" >
   		<input type="submit" value="提交" >
   	</form>
	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	列出客户所有的信用卡
   	<form action="${ctx }/api/consumer/stripe/listAllCards" method="post">
   		客户id： <input type="text" name="consumerId" value="1" >   		
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	指定的卡付款
   	<form action="${ctx }/api/consumer/stripe/chargeByCardId" method="post">
   		客户id： <input type="text" name="consumerId" value="1" >   	
   		卡id： <input type="text" name="cardId" value="card_16YALSK629Jm0Bi8SZCbxIWc" >   	
   		餐厅id： <input type="text" name="restaurantId" value="1" >   	
   		金额： <input type="text" name="amount" value="2000" >   
   		订单id： <input type="text" name="orderId" value="12" >   	
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	显示慈善机构
   	<form action="${ctx }/api/search/charity" method="post">
   		订单id：<input type="text" name="orderId" value="172" >
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	保存用户与慈善机构的方法
   	<form action="${ctx }/api/save/ordercharity" method="post">
	   	订单id：<input type="text" name="orderId" value="172" >
	   	机构id：<input type="text" name="charityId" value="1" >
	   	用户id：<input type="text" name="consumerId" value="7" >
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	通过商家id查找商家详细信息（more）
   	<form action="${ctx }/api/restaurant/shopinformore" method="post"> 
	   	店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	返回商家同意的预定订单
   	<form action="${ctx }/api/restaurant/dineinorder" method="post"> 
	   	店家id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	取消订单
   	<form action="${ctx }/api/consumer/order/cancel" method="post"> 
	   	店家id：<input type="text" name="orderId" value="172">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	外卖距离是否超过了商家最大外卖距离
   	<form action="${ctx }/api/restaurant/outOfDistance" method="post"> 
	   	店家Id：<input type="text" name="restaurantId" value="16">
	   	地址Id：<input type="text" name="addressId" value="46">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	平台Email
   	<form action="${ctx }/api/nommeemail" method="post"> 
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	商家已经审核的订单（预定）
   	<form action="${ctx }/api/consumer/dineinorder" method="post"> 
   		用户Id：<input type="text" name="consumerId" value="1">
   		店家Id：<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取订单所有的费用
   	<form action="${ctx }/api/consumer/cartordermoney" method="post"> 
   		购物车Id：<input type="text" name="cartHeaderId" value="143">
   		地址Id：<input type="text" name="addressId" value="27">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	用户确认排队订单
   	<form action="${ctx }/api/consumer/savelineup" method="post"> 
   		订单Id：<input type="text" name="orderId" value="167">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	商家登录
   	<form action="${ctx }//api/restaurant/login" method="post"> 
   		邮箱地址：<input type="text" name="code" value="timli@qq.com">
   		密码：<input type="text" name="password" value="123123">
   		设备编号：<input type="text" name="token" value="1439650654055">
   		设备类型：<input type="text" name="devicetype" value="0">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	商家地址
   	<form action="${ctx }//api/restaurant/raddress" method="post">    		
   		商家ID:<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	重复支付                                            
   	<form action="${ctx }/api/consumer/stripe/chargeByCardId/repeat" method="post">    		
   		订单Id:<input type="text" name="orderId" value="12">
   		银行卡Id:<input type="text" name="cardId" value="">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	退款                                   
   	<form action="${ctx }/payment/refundByOrder" method="post">    		
   		订单Id:<input type="text" name="orderId" value="280">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	显示默认的用户地址                                 
   	<form action="${ctx }/api/consumer/address/default" method="post">    		
   		用户Id:<input type="text" name="consumerId" value="54">
   		商家Id:<input type="text" name="restaurantId" value="16">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取营业时间                                 
   	<form action="${ctx }/api/getOpenTime" method="post">    		
   		订单时间:<input type="text" name="orderDate" value="">
   		商家Id:<input type="text" name="restaurantId" value="16">
   		订单类型:<input type="text" name="type" value="1">
   		<input type="submit" value="提交" >
   	</form>
   	--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   	获取常用的慈善机构（3个）                               
   	<form action="${ctx }/api/search/oftencharity" method="post">    		
   		<input type="submit" value="提交" >
   	</form>
   	
  </body>
</html>
