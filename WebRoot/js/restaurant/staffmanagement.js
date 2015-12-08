

function typeForMatter(value, row, index) {
			return value === 1 ? 'administrator' : 'employees';
		}
		function roleForMatter(value, row, index) {
			var role = value.toString(2);
			var str = '';
			if (role.length == 1) {
				role = '00' + role;
			} else if (role.length == 2) {
				role = '0' + role;
			}

			if (role.charAt(0) == 1) {
				str = str + 'Reservation ';
			}
			if (role.charAt(1) == 1) {
				str = str + 'Pick-up ';
			}
			if (role.charAt(2) == 1) {
				str = str + 'Delivery ';
			}

			return str;
		}

$(function(){
	

	//员工管理的js
	//var appPath = "";
		var url;

		$("select").change(
				function() {
					if ($(this).val() == 1) {
						$("#fm input[type='checkbox']").iCheck('check');
						$("#fm input[type='checkbox']").iCheck('disable');
						if ($.trim($("input[name='code']").val()) != '') {
							if (!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test($(
									"input[name='code']").val())) {
								$("input[name='code']").popover('destroy');
								$("input[name='code']").popover({
									content : "Please enter the correct email!"
								});
								$("input[name='code']").popover('show');
							}
						}
					} else {
						$("#fm input[type='checkbox']").iCheck('uncheck');
						$("#fm input[type='checkbox']").iCheck('enable');
						$("input[name='code']").popover('destroy');
					}

				});

		

		$("input[name='code']").blur(
				function() {
					if ($.trim($(this).val()) == "") {
						$(this).popover('destroy');
						$(this).popover({
							content : "Please fill in this field!"
						});
						$(this).popover('show');
					} else {
						if ($(this).lenth >= 200) {
							$(this).popover('destroy');
							$(this).popover({
								content : "The length is not greater than 200!"
							});
							$(this).popover('show');
						} else {
							if ($("select").val() == 1) {//选择管理员，验证邮箱
								if (!/^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/.test($(
										"input[name='code']").val())) {
									$("input[name='code']").popover('destroy');
									$("input[name='code']").popover({
										content : "Please enter the correct email!"
									});
									$("input[name='code']").popover('show');
								} else {
									$.ajax({
										url : appPath+"/restaurant/checkLoginName",
										type : "POST",
										cache : false,
										data : {
											'code' : $("input[name='code']").val(),
											'id' : $("input[name='id']").val(),
											'type' : $("select").val()
										},
										success : function(msg) {
											var msg = jQuery.parseJSON(msg);
											if (!msg.success) {
												$("input[name='code']").popover({
													content : msg.errorMsg
												});
												$("input[name='code']").popover('show');
											} else {
												flag1 = true;
												$("input[name='code']").popover('destroy');
											}
										}
									});
								}
							} else {//选择普通员工
								$.ajax({
									url : appPath+"/restaurant/checkCodeForEmployee",
									type : "POST",
									cache : false,
									data : {
										'code' : $("input[name='code']").val(),
										'id' : $("input[name='id']").val(),
										'type' : $("select").val()
									},
									success : function(msg) {
										var msg = jQuery.parseJSON(msg);
										if (!msg.success) {
											$("input[name='code']").popover({
												content : msg.errorMsg
											});
											$("input[name='code']").popover('show');
										} else {
											flag1 = true;
											$("input[name='code']").popover('destroy');
										}
									}
								});
							}

						}

					}
				}).focus(function() {
			$("input[name='code']").popover('destroy');
		});

		$("input[name='firstName']").blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).popover('destroy');
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				if ($(this).val().length >= 50) {
					$(this).popover('destroy');
					$(this).popover({
						content : "The length is not greater than 50!"
					});
					$(this).popover('show');
				} else {
					flag2 = true;
					$(this).popover('destroy');
				}
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("input[name='lastName']").blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).popover('destroy');
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				if ($(this).val().length >= 50) {
					$(this).popover('destroy');
					$(this).popover({
						content : "The length is not greater than 50!"
					});
					$(this).popover('show');
				} else {
					flag3 = true;
					$(this).popover('destroy');
				}

			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("input[name='password']").blur(function() {
			if ($.trim($(this).val()) == "") {
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				if ($.trim($(this).val()).length < 6 || $(this).val().length > 32) {
					$(this).popover({
						content : "Password length of 6 to 32!"
					});
					$(this).popover('show');
				} else {
					flag4 = true;
					$(this).popover('destroy');
				}

			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		$("select").blur(function() {
			if ($(this).val() == '') {
				$(this).popover({
					content : "Please fill in this field!"
				});
				$(this).popover('show');
			} else {
				flag5 = true;
				$(this).popover('destroy');
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		function validateMyForm() {
			var flag1 = false;
			var flag2 = false;
			var flag3 = false;
			var flag4 = false;

			if ($("select").val() == 1) {//选择管理员
				if (validateEmpty($("input[name='code']"))) {
					if (validateMaxLength($("input[name='code']"), 200)) {
						if (validateEmail($("input[name='code']"))) {
							$.ajax({
								url : appPath+"/restaurant/checkLoginName",
								async : false,
								type : "POST",
								cache : false,
								data : {
									'code' : $("input[name='code']").val(),
									'id' : $("input[name='id']").val(),
									'type' : $("select").val()
								},
								success : function(msg) {
									var msg = jQuery.parseJSON(msg);
									if (!msg.success) {
										$("input[name='code']").popover({
											content : msg.errorMsg
										});
										$("input[name='code']").popover('show');
									} else {
										flag1 = true;
									}
								}
							});
						}
					}
				}
			} else {//选择普通员工
				if (validateEmpty($("input[name='code']"))) {
					if (validateMaxLength($("input[name='code']"), 200)) {
							$.ajax({
								url : appPath+"/restaurant/checkCodeForEmployee",
								async : false,
								type : "POST",
								cache : false,
								data : {
									'code' : $("input[name='code']").val(),
									'id' : $("input[name='id']").val(),
									'type' : $("input[name='type']").val()
								},
								success : function(msg) {
									var msg = jQuery.parseJSON(msg);
									if (!msg.success) {
										$("input[name='code']").popover({
											content : msg.errorMsg
										});
										$("input[name='code']").popover('show');
									} else {
										flag1 = true;
									}
								}
							});
					}
				}
			}

			if (validateEmpty($("input[name='firstName']"))) {
				if (validateMaxLength($("input[name='firstName']"), 50)) {
					flag2 = true;
				}
			}

			if (validateEmpty($("input[name='lastName']"))) {
				if (validateMaxLength($("input[name='lastName']"), 50)) {
					flag3 = true;
				}
			}
			if (validateEmpty($("input[name='password']"))) {
				if (validateMaxLength($("input[name='password']"), 32)) {
					if (validateMinLength($("input[name='password']"), 6)) {
					}
					flag4 = true;
				}
			}
			return flag1 && flag2 && flag3 && flag4;
		}

		$("#sub").click(function() {
			if (validateMyForm()) {
				$("select").attr('disabled', false);
				if ($("#fm input[name='role1']").prop('checked')) {
					$("#fm input[name='role1']").val(1);
				}
				if ($("#fm input[name='role2']").prop('checked')) {
					$("#fm input[name='role2']").val(2);
				}
				if ($("#fm input[name='role3']").prop('checked')) {
					$("#fm input[name='role3']").val(4);
				}
				$('#myModal').modal('hide')
				$("#bg").css("display", "block");
				$("#show").css("display", "block");

				$.ajax({
					type : "POST",
					url : url,
					data : $("#fm").serialize(),
					success : function(msg) {
						$("#bg").css("display", "none");
						$("#show").css("display", "none");
						var msg = jQuery.parseJSON(msg);
						if (!msg.success) {
							alert(msg.errorMsg);
							$(this).popover('show');
						} else {
							$('table').bootstrapTable('refresh');
						}
					}
				});
			}

		});

		$("button[name='add']").click(function() {
			$("#fm input[type='checkbox']").iCheck('enable');
			$("#myModal h4").text("Add new employee");
			$("#fm input").val('');//重置表单
			$("select").val(2);//默认普通员工
			$("select").attr("disabled", false);
			$("#fm input[type='checkbox']").iCheck('uncheck');
			$("input").popover('destroy');//去除错误提示
			url = appPath+"/restaurant/addRestaurantUser";
		});

		$("button[name='update']").click(function() {
			$("#fm input[type='checkbox']").iCheck('enable');
			$("#myModal h4").text("Modify emplyee");
			$("#fm input").val('');//重置表单
			$("#fm input[type='checkbox']").iCheck('uncheck');
			$("input").popover('destroy');//去除错误提示
			var user = $('table').bootstrapTable('getSelections')[0];
			if (user != undefined) {
				$('#myModal').modal('show');
				$("input[name='code']").val(user.code);
				$("input[name='firstName']").val(user.firstName);
				$("input[name='lastName']").val(user.lastName);
				$("input[name='password']").val(user.password);
				$("input[name='uuid']").val(user.uuid);
				$("select").val(user.type);
				$("select").attr("disabled", true);

				var role1 = user.role.toString(2);
				if (role1.length == 1) {
					role1 = '00' + role1;
				} else if (role1.length == 2) {
					role1 = '0' + role1;
				}
				if (role1.charAt(0) == 1) {
					$("#fm input[name='role3']").iCheck('check');
				}
				if (role1.charAt(1) == 1) {
					$("#fm input[name='role2']").iCheck('check');
				}
				if (role1.charAt(2) == 1) {
					$("#fm input[name='role1']").iCheck('check');
				}

				if (user.type == 1) {//判断是否是管理员
					$("#fm input[type='checkbox']").iCheck('disable');
				}
			}
			url = appPath+"/restaurant/updateRestaurantUser";

		});

		var user1;
		$("button[name='delete']").click(function() {
			user1 = $('table').bootstrapTable('getSelections')[0];
			$("button[name='delete']").popover('destroy');
			if (user1 && user1.uuid!=$("#storeId").val()) {
				$('#myModal1').modal('show');
			}
		});
		$("button[name='no']").click(function() {
			$('#myModal1').modal('hide');
		});
		$("button[name='ok']").click(function() {
			$('#myModal1').modal('hide')
			$("#bg").css("display", "block");
			$("#show").css("display", "block");
			$.ajax({
				type : "POST",
				url : appPath+"/restaurant/deleteEmployee",
				data : {
					'restaurantUserUuid' : user1.uuid
				},
				success : function(msg) {
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
					var msg = jQuery.parseJSON(msg);
					if (!msg.success) {
						alert(msg.errorMsg);
					} else {
						$('table').bootstrapTable('refresh');
					}
				}
			});
		});
});