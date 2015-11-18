
//注册页面 的js

		$(function() {
			//var appPath = "";
			
			$.ajax({
				type : "POST",
				url : appPath+"/information/getAllAreas",
				success : function(msg) {
					var msg = jQuery.parseJSON(msg);
					for ( var i in msg) {
							$("#area").append(
									"<option value="+msg[i].id+">"
											+ msg[i].areaName + "</option>");
					}
				}
			});

			

			$("input[name='restaurantName']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 50)) {
						$.ajax({
							type : "POST",
							url : appPath+"/restaurant/checkRestaurantName",
							data : {
								'restaurantName' : $(this).val()
							},
							success : function(msg) {
								var msg = jQuery.parseJSON(msg);
								if (!msg.success) {
									validateErrorMsg($("input[name='restaurantName']"), msg.errorMsg);
								} else {
									$("input[name='restaurantName']").popover('destroy');
								}
							}
						});
					}
				}
			}).focus(function() {
				$("input[name='restaurantName']").popover('destroy');
			});

			$("input[name='restaurantContact']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 30)) {
						$(this).popover('destroy');
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='restaurantPhone']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 30)) {
						$(this).popover('destroy');
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='restaurantAddress']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 200)) {
						$(this).popover('destroy');
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='code']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateEmail($(this))) {
						$.ajax({
							url : appPath+"/restaurant/checkLoginName",
							type : "POST",
							cache : false,
							data : {
								'code' : $("input[name='code']").val()
							},
							success : function(msg) {
								var msg = jQuery.parseJSON(msg);
								if (!msg.success) {
									validateErrorMsg($("input[name='code']"), msg.errorMsg);
								} else {
									$("input[name='code']").popover('destroy');
								}
							}
						});
					}
				}
			}).focus(function() {
				$("input[name='code']").popover('destroy');
			});

			$("input[name='firstName']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 50)) {
						$(this).popover('destroy');
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='lastName']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 50)) {
						$(this).popover('destroy');
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='password']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 32)) {
						if (validateMinLength($(this), 6)) {
							$(this).popover('destroy');
						}
					}
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='password2']").blur(function() {
				if ($.trim($(this).val()) == "") {
					$(this).popover('destroy');
					$(this).popover({
						content : "Please fill in this field!"
					});
					$(this).popover('show');
				} else {
					if ($("input[name='password']").val() != $("input[name='password2']").val()) {
						$("input[name='password2']").popover('destroy');
						$("input[name='password2']").popover({
							content : "The two passwords don't match!"
						});
						$("input[name='password2']").popover('show');
					}
				}

			}).focus(function() {
				$(this).popover('destroy');
			});

			function validateMyForm() {
				var flag1 = false;
				var flag2 = false;
				var flag3 = false;
				var flag4 = false;
				var flag5 = false;
				var flag6 = false;
				var flag7 = false;
				var flag8 = false;
				var flag9 = false;

				if (validateEmpty($("input[name='restaurantName']"))) {
					if (validateMaxLength($("input[name='restaurantName']"), 50)) {
						flag1 = true;
					}
				}

				if (validateEmpty($("input[name='restaurantContact']"))) {
					if (validateMaxLength($("input[name='restaurantContact']"), 30)) {
						flag2 = true;
					}
				}

				if (validateEmpty($("input[name='restaurantPhone']"))) {
					if (validateMaxLength($("input[name='restaurantPhone']"), 30)) {
						flag3 = true;
					}
				}

				if (validateEmpty($("input[name='restaurantAddress']"))) {
					if (validateMaxLength($("input[name='restaurantAddress']"), 200)) {
						flag4 = true;
					}
				}

				if (validateEmpty($("input[name='code']"))) {
					if (validateEmail($("input[name='code']"))) {
						flag5 = true;
					}
				}

				if (validateEmpty($("input[name='firstName']"))) {
					if (validateMaxLength($("input[name='firstName']"), 50)) {
						flag6 = true;
					}
				}

				if (validateEmpty($("input[name='lastName']"))) {
					if (validateMaxLength($("input[name='lastName']"), 50)) {
						flag7 = true;
					}
				}

				if (validateEmpty($("input[name='password']"))) {
					if (validateMaxLength($("input[name='password']"), 32)) {
						if (validateMinLength($("input[name='password']"), 6)) {
							flag8 = true;
						}
					}
				}

				if ($.trim($("input[name='password2']").val()) == "") {
					$("input[name='password2']").popover('destroy');
					$("input[name='password2']").popover({
						content : "Please fill in this field!"
					});
					$("input[name='password2']").popover('show');
				} else {
					if ($("input[name='password']").val() != $("input[name='password2']").val()) {
						$("input[name='password2']").popover('destroy');
						$("input[name='password2']").popover({
							content : "The two passwords don't match!"
						});
						$("input[name='password2']").popover('show');
					} else {
						flag9 = true;
					}
				}
				return flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7 && flag8 && flag9;

			}

			$("#sub").click(function() {
				$("#bg").css("display", "block");
				$("#show").css("display", "block");
				if (validateMyForm()){
					$.ajax({
						type : "POST",
						url : appPath+"/restaurant/checkRestaurantName",
						data : {
							'restaurantName' : $("input[name='restaurantName']").val()
						},
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							if (!msg.success) {
								$("#bg").css("display", "none");
								$("#show").css("display", "none");
								validateErrorMsg($("input[name='restaurantName']"), msg.errorMsg);
							} else {//商家名称不重复
										$.ajax({
											url : appPath+"/restaurant/checkLoginName",
											type : "POST",
											cache : false,
											data : {
												'code' : $("input[name='code']").val()
											},
											success : function(msg) {
												var msg = jQuery.parseJSON(msg);
												if (!msg.success) {
													$("#bg").css("display", "none");
													$("#show").css("display", "none");
													validateErrorMsg($("input[name='code']"), msg.errorMsg);
												} else {//管理员名称不重复
														$.ajax({
															url : appPath+"/restaurant/restaurantRegister",
															type : "POST",
															cache : false,
															data : $("#fm").serialize(),
															success : function(msg) {
																$("#bg").css("display", "none");
																$("#show").css("display", "none");
																var msg = jQuery.parseJSON(msg);
																if (!msg.success) {
																	validateErrorMsg($("#sub"), msg.errorMsg);
																} else {
																	location = appPath+"/restaurant/toRegisterSuccess";
																}
															}
														});
												}
											}
										});
									
								
							}
						}
					});
				}
				else{
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
				}
			});

		});