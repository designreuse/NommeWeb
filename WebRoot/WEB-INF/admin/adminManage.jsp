<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>

<title>My JSP 'adminManagePage.jsp' starting page</title>
<jsp:include page="../inputjs.jsp"></jsp:include>
<jsp:include page="../inputcss.jsp"></jsp:include>
<style type="text/css">
/* .modal-header {
	background-color: #3D9970;
	border-bottom: 4px solid #307959; border-radius : 5px 5px 0px 0px;
	color: #ffffff;
	border-radius: 5px 5px 0px 0px;
}

.modal-body {
	background-color: #EAEAEC;
}

.modal-footer {
	margin-top: 0px;
}

.modal-content {
	width: 560px;
}

.mybt {
	width: 100px;
	height: 32px;
	margin-left: 20;
}

.modal input {
	width: 300px;
	height: 37px;
	margin-top: 10px;
	padding-left: 8PX;
	border: 0px solid;
}

.modalField {
	float: right;
	font-weight: lighter;
	font-size:16;
	text-align: left;
	margin-top: 15px;
}

*/
</style>

</head>

<body>
	
	<div id="custom-toolbar">
		<button type="button" id="addAdmin" class="btn btn-default mybt"
			data-toggle="modal" data-target="#addAdminModal" title="New Admin">
			<i class="fa fa-plus "></i><span class="toolDescribe">Add</span>
		</button>
		<button type="button" id="modifyAdmin" class="btn btn-default mybt"
			title="Modify Admin">
			<i class="fa fa-pencil "></i><span class="toolDescribe">Edit</span>
		</button>
		<button type="button" id="deleteAdmin" class="btn btn-default mybt"
				 title="Delete Admin">
				<i class="fa fa-minus"></i> Delete
			</button>
		<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh"
			title="refresh">
       		<i class="fa fa-refresh"></i><span class="toolDescribe">Refresh</span>
	 	</button>
	</div>
		
	<table data-toggle="table" id="adminTable"
		data-url="${ctx}/admin/getadminusers"
		data-click-to-select="true"
		data-pagination="true"
		data-toolbar="#custom-toolbar"
		data-striped="true"
		data-page-size="20"
		data-page-list="[10, 20, 50, 100]">
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="loginname" data-sortable="true">Login name</th>
				<th data-field="name" data-sortable="true">Name</th>
				<th data-field="usertype" data-formatter="typeForMatter" data-sortable="true">User Type</th>
			</tr>
		</thead>
	</table>
	
	<!-- 删除用户的模态框 -->
	<div class="modal" id="deleteAdminModal" tabindex="-2" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Delete User</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="adminId" id="adminId"><!-- 区域ID -->
						
						<div style="text-align:center;"><h3><span id="attentionInfo">Do you want to delete this user ?</span></h3></div>
						<div id="info" style="text-align:center; color:#FA6A6A;"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<button type="button" id="subDelete" class="btn bg-olive" style="width:80px;">Delete</button>
					</div>
				</div>
		</div>
	</div>
		
	<!-- 新增用户的模态框 -->
	<div class="modal" id="addAdminModal" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<form id="adminForm" action="" method="POST">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">New Admin</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="id" id="adminId" value="0"><!-- 管理员ID -->
						<div class="row userTypeDiv">
							<div class="col-md-3"><label class="modalField">User Type:</label></div>
							<div class="col-md-9 ">
								<div class="userTypeGroup" style="margin-top: 12px;margin-left: 20px;">
									<span class="disposePatterns" id="parentPatternA"><input type="radio" name="userType" id="userTypeA" value="0">&nbsp;Admin</span>
									<span style="display:block;width:25px;height:10px;float: left;"></span>
									<span class="disposePatterns" id="parentPatternB"><input type="radio" name="userType" id="userTypeB" value="1">&nbsp;Staff</span>
								</div>
								<!-- <sapn ><input type="radio" name="userType" ></sapn>
								<sapn>Admin</sapn>
								<sapn><input type="radio" name="userType" ></sapn>
								<sapn>Staff</sapn> -->
							</div>
						</div>
						<div class="row">
							<div class="col-md-3"><label class="modalField">Login Name:</label></div>
							<div class="col-md-9">
								<input type="text" name="loginname" id="loginname">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3"><label class="modalField">Password:</label></div>
							<div class="col-md-9">
								<input type="password" name="password" id="password">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3"><label class="modalField">First Name:</label></div>
							<div class="col-md-9">
								<input type="text" name="firstName" id="firstName">
							</div>
						</div>
						<div class="row">
							<div class="col-md-3"><label class="modalField">Last Name:</label></div>
							<div class="col-md-9">
								<input type="text" name="lastName" id="lastName">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<button type="button" id="sub" class="btn bg-olive"
							style="width:80px;">Save</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>

	<script type="text/javascript">
	
		function typeForMatter(value,row,index){
			return value == 0 ? 'Admin' : 'Staff';
		}
		
		$(function(){
			flag1 = false;//用户名
			flag2 = false;//密码
			flag3 = false;//firstName
			flag4 = false;//lastName
			var addOrMdifyUrl;//提交表单的URL
			var currentLineUser;//前端分页的当前用户
			var oldPassword;//原来的密码（MD5加密的字符串）
			var newUserType;
			//var currentUsername = "${adminUserLoginname.loginname}";//获取当前用户的登录邮箱
			var currentUserType = "${adminUserLoginname.usertype}";//获取当前登录用户的类型  0:超级管理员admin  1：管理员staff
			var currentUserId = "${adminUserLoginname.id}";//获取当前登录用户的id
			if(currentUserType==1){//如果用户类型是一般管理员就隐藏删除按钮
				$("#deleteAdmin").hide();
			}
			
			$(".userTypeGroup input[name='userType']").on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定 
				newUserType = event.target.defaultValue;
			});
			
			$("#refresh").click(function () {//表格工具栏刷新按钮
				$("#adminTable").bootstrapTable('refresh', {
                    url: '${ctx}/admin/getadminusers'
                });
            });
			
			function showPopover(ele, data,direction){//显示提示信息的方法
				ele.popover('destroy');
				ele.popover({
					content: data,
					placement: direction
				});
				ele.popover('show');
			}
			
			$("#addAdmin").click(function() {//页面加号按钮点击事件
				$("input").popover('destroy');//取消所有的提示信息
				$("#adminForm")[0].reset();//重置表单
				$(".modal-title").empty();
				$(".modal-title").append("New Admin");
				addOrMdifyUrl = "${ctx}/admin/adduser";
				$("#adminId").val("");
				if(currentUserType == 1){//这说明当前的用户是普通管理员
					$(".userTypeDiv").hide();
					$(".userTypeGroup input[value='1']").iCheck('check');//如果普通管理员修改的是普通管理员，设置类型为1
				}
				
				
			});
			$("#modifyAdmin").click(function() {//页面加号按钮点击事件
				$("input").popover('destroy');//取消所有的提示信息
				$("#adminForm")[0].reset();//重置表单
				$(".modal-title").empty();
				$(".modal-title").append("Eidt Admin/Staff");
				addOrMdifyUrl = "${ctx}/admin/modifyuser";
				currentLineUser = $("#adminTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
				oldPassword = currentLineUser.password;
				if(currentLineUser!=undefined){
					$("#adminId").val(currentLineUser.id);
					$("#loginname").val(currentLineUser.loginname);
					$("#password").val(currentLineUser.password);
					$("#firstName").val(currentLineUser.firstName);
					$("#lastName").val(currentLineUser.lastName);
					if(currentUserId != currentLineUser.id){
						if(currentUserType==1 && currentLineUser.usertype==1){// 0:超级管理员admin  1：普通管理员staff
							//如果当前用户是普通管理员并且点击修改的是普通管理员账户才不给修改，否则不给修改
							//data-toggle="modal" data-target="#deleteAdminModal"
							$(".userTypeDiv").hide();
							$(".userTypeGroup input[value='1']").iCheck('check');//如果普通管理员修改的是普通管理员，设置类型为1
							$("#addAdminModal").modal('show');
						}else if(currentUserType == 0){
							$(".userTypeDiv").show();
							if(currentLineUser.usertype == 0){
								$(".userTypeGroup input[value='0']").iCheck('check');
							}else if(currentLineUser.usertype == 1){
								$(".userTypeGroup input[value='1']").iCheck('check');
							}
							$("#addAdminModal").modal('show');
						}
						
					}
				}
			});
			
			$("#deleteAdmin").click(function(){
				$("#info").empty();
				currentLineUser = $("#adminTable").bootstrapTable('getSelections')[0];//行点击时获取出行和数据
				$("#deleteAdminModal").modal('show');//显示删除模态框
			});
			
			$("#subDelete").click(function(){
				$("#info").empty();
				$.ajax({
					type: "post",
					url: "${ctx}/admin/deleteuser",
					async: false,
					data:{id:currentLineUser.id},
					success: function(data){
						var msg = jQuery.parseJSON(data);
						if(msg.success){//说明成功了
							$("#deleteAdminModal").modal('hide');
							$("#adminTable").bootstrapTable('refresh',{ //刷新表格数据
			                    url: '${ctx}/admin/getadminusers'
			                });
						}else{//出错，可能是删除出错
							var arrorMessage = msg.errorMsg;
							$("#info").append("Delete Error!");
						}
						
					}
				
				});
			});

			$("#loginname,#password,#firstName,#lastName").focus(function() {
				$(this).popover('destroy');
				this.select();
			});
			
			$("#loginname").blur(function(){//验证用户名是否可用
				var adminId = $("#adminId").val();
				var loginname = $.trim($("#loginname").val());
				if(loginname==""){
					flag1 = false;
					showPopover($("#loginname"),"Required","right");
				}else if(/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(loginname)){//如果格式是一个Email
					if(adminId!=""){//如果ID有值说明是修改
						$.ajax({
							type: "post",
							url: "${ctx}/admin/loginnameisusedinotherid",
							data: {loginname:loginname,id:adminId},
							success: function(data){
								var msg = jQuery.parseJSON(data);
								if(msg.success){//如果该邮箱没有没使用过
									flag1 = true;
									$("#loginname").popover('destroy');
								}else{//如果该邮箱已经被注册
									flag1 = false;
									showPopover($("#loginname"),msg.errorMsg,"right");
								}
							}
						});
					}else{//如果ID没值说明是新增
						$.ajax({
							type: "post",
							url: "${ctx}/admin/loginnameexist",
							data: {loginname:loginname},
							success: function(data){
								var msg = jQuery.parseJSON(data);
								if(msg.success){//如果该邮箱没有没使用过
									flag1 = true;
									$(this).popover('destroy');
								}else{//如果该邮箱已经被注册
									flag1 = false;
									showPopover($("#loginname"),msg.errorMsg,"right");
								}
							}
						});
						
					}
				}else{//如果格式不是一个Email
					flag1 = false;
					showPopover($(this),"Invalid Email","right");
				}
			});
			$("#password").blur(function(){
				var p1 = $.trim($("#password").val());
				if(p1 != oldPassword){
					if(p1.length<6 || p1.length>18){//长度不合理
						flag2 = false;
						showPopover($("#password"),"Please typing 6--18 characters","right");
					}else if(/^[A-Za-z0-9]+$/.test(p1)){//验证密码成功
						flag2 = true;
						$("#password").popover('destroy');
					}else{//有除了数字和字母的其他的支付
						flag2 = false;
						showPopover($("#password"),"Allow only letters and Numbers","right");
					}
				}
			});
			
			$("#firstName").blur(function(){
				var firstName = $.trim($("#firstName").val());
				if(firstName==""){
					flag3 = false;
					showPopover($("#firstName"),"Required","right");
				}else{
					flag3 = true;
					$("#firstName").popover('destroy');
				}
			});
			$("#lastName").blur(function(){
				var lastname = $.trim($("#lastName").val());
				if(lastname==""){
					flag4 = false;
					showPopover($("#lastName"),"Required","right");
				}else{
					flag4 = true;
					$("#lastName").popover('destroy');
				}
			});
			
			$("button[type='reset']").click(function(){//重置按钮点击事件
				$(":input").popover('destroy');
			});
			
			$("#sub").blur(function() {
				$(this).popover('destroy');
			});
			
			$("#sub").click(function() {//新增或修改用户的提交表单
				
				if(!flag1){
					var adminId = $("#adminId").val();
					var loginname = $.trim($("#loginname").val());
					if(loginname==""){
						flag1 = false;
						showPopover($("#loginname"),"Required","right");
					}else if(/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(loginname)){//如果格式是一个Email
						if(adminId!=""){//如果ID有值说明是修改
							$.ajax({
								type: "post",
								async: false,
								url: "${ctx}/admin/loginnameisusedinotherid",
								data: {loginname:loginname,id:adminId},
								success: function(data){
									var msg = jQuery.parseJSON(data);
									if(msg.success){//如果该邮箱没有没使用过
										flag1 = true;
										$("#loginname").popover('destroy');
									}else{//如果该邮箱已经被注册
										flag1 = false;
										showPopover($("#loginname"),msg.errorMsg,"right");
									}
								}
							});
						}else{//如果ID没值说明是新增
							$.ajax({
								type: "post",
								async: false,
								url: "${ctx}/admin/loginnameexist",
								data: {loginname:loginname},
								success: function(data){
									var msg = jQuery.parseJSON(data);
									if(msg.success){//如果该邮箱没有没使用过
										flag1 = true;
										$(this).popover('destroy');
									}else{//如果该邮箱已经被注册
										flag1 = false;
										showPopover($("#loginname"),msg.errorMsg,"right");
									}
								}
							});
							
						}
					}else{//如果格式不是一个Email
						flag1 = false;
						showPopover($("#loginname"),"Invalid Email","right");
					}
				}
				if(!flag2){
					var p1 = $.trim($("#password").val());
					if(p1 != oldPassword){
						if(p1.length<6 || p1.length>18){//长度不合理
							flag2 = false;
							showPopover($("#password"),"Please typing 6--18 characters","right");
						}else if(/^[A-Za-z0-9]+$/.test(p1)){//验证密码成功
							flag2 = true;
							$("#password").popover('destroy');
						}else{//有除了数字和字母的其他的支付
							flag2 = false;
							showPopover($("#password"),"Allow only letters and Numbers","right");
						}
					}else{
						flag2 = true;
					}
				}
				if(!flag3){
					var firstName = $.trim($("#firstName").val());
					if(firstName==""){
						flag3 = false;
						showPopover($("#firstName"),"Required","right");
					}else{
						flag3 = true;
						$("#firstName").popover('destroy');
					}
				}
				if(!flag4){
					var lastname = $.trim($("#lastName").val());
					if(lastname==""){
						flag4 = false;
						showPopover($("#lastName"),"Required","right");
					}else{
						flag4 = true;
						$("#lastName").popover('destroy');
					}
				}
				
				if(flag1 && flag2 && flag3 && flag4){
					var id = $("#adminId").val();
					var password = $("#password").val();
					var loginname = $("#loginname").val();
					var firstName = $("#firstName").val();
					var lastName = $("#lastName").val();
					$.ajax({
						url : addOrMdifyUrl,
						type : "POST",
						data : {id:id, usertype:newUserType, password:password, loginname:loginname, firstName:firstName, lastName:lastName},
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							if (msg.success) {
								$("#sub").popover('destroy');
								$("#sub").popover({
									content: "Saved OK !",
									placement: "top"
								});
								$("#sub").popover('show');
								
								setTimeout(function(){
									$("#addAdminModal").modal('hide');
									$("#sub").popover('destroy');
								},1000);
								
								$("button[type='reset']").trigger("click");//触发reset按钮
								$("#adminTable").bootstrapTable('refresh');//触发刷新表格
							} else {
								var errorMessage = msg.errorMsg;
							
								$("#sub").popover('destroy');
								$("#sub").popover({
									content: errorMessage,
									placement: "top"
								});
								$("#sub").popover('show');
							}
						}
					});
				}
			});
		});
	</script>

</html>
