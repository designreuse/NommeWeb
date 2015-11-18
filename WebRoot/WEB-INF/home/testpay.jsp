<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML>
<html>
  <head>
   
  </head>
  
  <body>
通过卡号付款
-------------------------------------------------------------------------------------------------

   	<form action="${ctx }/payment/testChargeByCard" method="post">
   		卡号：<input type="text" name="number" value="4242424242424242">
   		过期月：<input type="text" name="exp_month" value="4">
   		过期年：<input type="text" name="exp_year" value="2017">
   		cvc：<input type="text" name="cvc" value="345">
   		金额：<input type="text" name="amount" value="20000">
   		货币：<input type="text" name="currency" value="cad">
   		<input type="submit" value="提交">
   	</form>
保存customer以及当前卡
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testsaveCustomerAndCard" method="post">
   		卡号：<input type="text" name="number" value="4242424242424242">
   		过期月：<input type="text" name="exp_month" value="4">
   		过期年：<input type="text" name="exp_year" value="2017">
   		cvc：<input type="text" name="cvc" value="345">
   		<input type="submit" value="提交">
   	</form>
   	
使用customer默认卡付款
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testchargeByCustomer" method="post">
   		金额：<input type="text" name="amount" value="20000">
   		货币：<input type="text" name="currency" value="cad">
   		customerid：<input type="text" name="customerId" value="cus_6gqYFhXuMvwEHo">
   		<input type="submit" value="提交">
   	</form>
   	
   	为customer添加卡
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testcustomerAddCard" method="post">
   		卡号：<input type="text" name="number" value="4242424242424242">
   		过期月：<input type="text" name="exp_month" value="4">
   		过期年：<input type="text" name="exp_year" value="2017">
   		cvc：<input type="text" name="cvc" value="345">
   		customerid：<input type="text" name="customerId" value="cus_6gqYFhXuMvwEHo">
   		<input type="submit" value="提交">
   	</form>
列出用户所有的卡
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testlistAllCards" method="post">
   		customerid：<input type="text" name="customerId" value="cus_6gqYFhXuMvwEHo">
   		<input type="submit" value="提交">
   	</form>
   	
用指定的卡付款
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testchargeByCardId" method="post">
   		金额：<input type="text" name="amount" value="20000">
   		货币：<input type="text" name="currency" value="cad">
   		customerid：<input type="text" name="customerId" value="cus_6gqYFhXuMvwEHo">
   		cardid：<input type="text" name="cardId" value="card_16TSrmK629Jm0Bi83G1mavYG">
   		<input type="submit" value="提交">
   	</form>
   	
全额退款
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testrefundAll" method="post">
   		chargeId：<input type="text" name="chargeId" value="ch_16TX3qK629Jm0Bi8NcgZPPUI">
   		<input type="submit" value="提交">
   	</form>
部分退款
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testrefundPart" method="post">
   		金额：<input type="text" name="amount" value="1000">
   		chargeId：<input type="text" name="chargeId" value="ch_16TT9RK629Jm0Bi8iRr574eV">
   		<input type="submit" value="提交">
   	</form>
   	
创建餐厅的托管账户
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testcreateManagedAccount" method="post">
   		邮箱：<input type="text" name="email" value="wangpin@qq.com">
   		<input type="submit" value="提交">
   	</form>
   	
托管账户绑定银行账户
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testcreateBankAccount" method="post">
   		银行路由号码：<input type="text" name="routingNumber" value="01031-010">
   		账户号码：<input type="text" name="bankAccountNumber" value="000123456789">
   		托管账户id：<input type="text" name="accountId" value="acct_16VwCBAi31Siw0HM">
   		<input type="submit" value="提交">
   	</form>
   	
  主账户托管账户一起收款
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testchargeForPlatform" method="post">
   		卡号：<input type="text" name="number" value="4242424242424242">
   		过期月：<input type="text" name="exp_month" value="4">
   		过期年：<input type="text" name="exp_year" value="2017">
   		cvc：<input type="text" name="cvc" value="345">
   		金额：<input type="text" name="amount" value="20000">
   		货币：<input type="text" name="currency" value="cad">
   		托管账户id：<input type="text" name="accountNumber" value="acct_16VuiuBTy2qs99c6">
   		<input type="submit" value="提交">
   	</form>
 更新托管账户
-------------------------------------------------------------------------------------------------
   	<form action="${ctx }/payment/testupdateManagedAccount" method="post">
   		context：<input type="text" name="context" value='{
    "first_name": "wang",
    "last_name": "pin",
    "day": 2,
    "month": 3,
    "year": 1988,
    "type": "company",
    "personal_id_number": "23456654a",
    "line1": "03 amy",
    "city": "Calgary",
    "state": "ABdfd",
    "postal_code": "T2N 1N5",
    "ip": "114.228.126.45",
    "date": 1438664756
}'>
   		
   		托管账户id：<input type="text" name="accountNumber" value="acct_16VuiuBTy2qs99c6">
   		<input type="submit" value="提交">
   	</form>
	
  </body>
</html>
