//登陆页面的js

	$(function() {
		//var appPath = "";
		
		
			var flag1 = false;
			var flag2 = false;
			var flag3 = false;
			$("#openModal").click(function() {//打开模态框清除输入框提示
				$("#sendEmail").popover('destroy');
			});

			function showPopover(ele, data, direction) {//显示提示信息的方法
				ele.popover('destroy');
				ele.popover({
					content : data,
					placement : direction
				});
				ele.popover('show');
			}

			$("input[name='code']").blur(function() {
				if ($.trim($(this).val()) == "") {
					$(this).popover('destroy');
					$(this).popover({
						content : "Please fill in this field!"
					});
					$(this).popover('show');
				} else {
					if (!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test($(this).val())) {
						$(this).popover('destroy');
						$(this).popover({
							content : "Please enter the correct email!"
						});
						$(this).popover('show');
					} else {
						$(this).popover('destroy');
						flag1 = true;
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='password']").blur(function() {
				if ($.trim($(this).val()) == "") {
					$(this).popover('destroy');
					$(this).popover({
						content : "Please fill in this field!"
					});
					$(this).popover('show');
				} else {
					$(this).popover('destroy');
					flag2 = true;
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			function validateMyForm() {
				flag1 = false;
				flag2 = false;
				if (validateEmpty($("input[name='code']"))) {
					if (validateEmail($("input[name='code']"))) {
						flag1 = true;
					}
				}

				if (validateEmpty($("input[name='password']"))) {
					if (validateMaxLength($("input[name='password']"), 32)) {
						if (validateMinLength($("input[name='password']"), 6)) {
							flag2 = true;
						}
					}
				}

				return flag1 && flag2;
			}

			$("#sub").click(function() {
				if (validateMyForm()) {
					$.ajax({
						url : appPath+"/restaurant/restaurantLogin",
						type : "POST",
						cache : false,
						data : $("#fm").serialize(),
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							if (!msg.success) {
								validateErrorMsg($("#sub"), msg.errorMsg);
							} else {
								location = appPath+"/restaurant/main";
							}
						}
					});
				}
			});

			/*if ("${errorMsg}" != "") {
				$("#sub").popover({
					content : "${errorMsg}"
				});
				$("#sub").popover('show');
			}*/
			$("input[name='code']").focus(function() {
				$("#sub").popover('destroy');
			});
			$("input[name='password']").focus(function() {
				$("#sub").popover('destroy');
			});

			//$("input[name='code']").val("${code}");
			//$("input[name='password']").val("${password}");

			$("#send").blur(function() {
				$(this).popover('destroy');
			});

			$("#sendEmail").blur(function() {
				var emailAddress = $.trim($(this).val());
				if (emailAddress == "") {
					flag3 = false;
					showPopover($(this), "Required", "right");
				} else if (/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(emailAddress)) {
					flag3 = true;
				} else {
					flag3 = false;
					showPopover($(this), "Invalid Email", "right");
				}
			});

			$("#openModal").click(function() {
				$("#sendEmail").popover('destroy');
				$("#sendEmail").val("");
				var emailAddress = $("input[name='code']").val();
				//如果输入的是一个邮箱，在打开模态框的时候就将之前输入的Email填到模态框中
				if (/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(emailAddress)) {
					$("#sendEmail").val(emailAddress);
				}
			});

			$("#send").click(function() {//请求后台发送邮件
				var address = $("#sendEmail").val();

				if (address == "") {
					flag3 = false;
					showPopover($("#sendEmail"), "Required", "right");
				}

				if (!flag3) {
					var emailAddress = $.trim($("#sendEmail").val());
					if (emailAddress == "") {
						flag3 = false;
						showPopover($("#sendEmail"), "Required", "right");
					} else if (/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test(emailAddress)) {
						flag3 = true;
					} else {
						flag3 = false;
						showPopover($("#sendEmail"), "Invalid Email", "right");
					}
				}

				if (flag3) {

					$.ajax({
						type : "POST",
						data : {
							emailAddress : address
						},
						url : appPath+'/restaurant/sendemail',
						success : function(data) {
						}
					});
					//不等待后台发送Email完成，先直接跳转到重置密码页面
					$("#send").popover({
						content : "Email send success. Please Ckeck Your Email!",
						placement : "top"
					});
					$("#send").popover('show');
					setTimeout(function() {
						window.location = appPath+"/restaurant/resetPassword?email=" + address;
					}, 2000);
				}

			});

		});