

//商家基础信息的js

		$(function() {
			//var appPath = "";

			function initMap(restaurant) {//加载地图
				var geocoder = new google.maps.Geocoder();
				var mapOptions = {
					zoom : 12,
					center : new google.maps.LatLng(restaurant.restaurantLat, restaurant.restaurantLng),
					mapTypeId : google.maps.MapTypeId.ROADMAP
				};

				var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

				var marker = new google.maps.Marker({
					position : map.getCenter(),
					draggable : true,
					//animation: google.maps.Animation.BOUNCE,
					map : map
				});

				google.maps.event.addListener(map, 'click', function(event) {
					marker.setMap(null);
					placeMarker(event.latLng);
					$("#restaurantLng").val(event.latLng.lng());
					$("#restaurantLat").val(event.latLng.lat());
					var latlng = new google.maps.LatLng(event.latLng.lat(), event.latLng.lng());
					geocoder.geocode({
						'latLng' : latlng
					}, function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							if (results[1]) {
								$("#restaurantAddress").val(results[0].formatted_address);
							}
						}
					});
				});

				function placeMarker(location) {
					marker = new google.maps.Marker({
						position : location,
						draggable : true
					});
					marker.setMap(map);
				}
			}

			$("#replace").click(function() {
				$("#inputdiv").css("display", "none");
				$("#inputfm").css("display", "block");
			});

			$("#cancel").click(function() {
				$("#inputdiv").css("display", "block");
				$("#inputfm").css("display", "none");
			});

			function fileInput(restaurant) {//加载input
				if (restaurant.logourl) {
					$("#inputdiv").css("display", "block");
					$("#imgdiv").append(
							"<img alt='logo' src="+restaurant.logourl+"  onerror=javascript:this.src='"+appPath+"/images/no-picture.jpg' style='width: 300px;margin-left: 50px;' >");
					$('#logo').fileinput({//加载已经有logo的fileinput
						language : 'es',
						uploadAsync : true,
						uploadUrl : appPath+'/information/uploadLogo',
						allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
						maxFileSize : 1024,
						maxFilesNum : 1
					}).on('fileuploaded', function(event, data, previewId, index) {
						window.location = appPath+"/information/information";
					});
				} else {//加载没有logo的fileinput
					$("#inputfm").css("display", "block");
					$("#cancel").css("display", "none");
					$('#logo').fileinput({
						language : 'es',
						uploadAsync : true,
						uploadUrl : appPath+'/information/uploadLogo',
						allowedFileExtensions : [ 'jpg', 'png', 'gif' ],
						maxFileSize : 1000,
						maxFilesNum : 1,
						dropZoneTitle : 'You have none logo'
					}).on('fileuploaded', function(event, data, previewId, index) {
						window.location = appPath+"/information/information";
					});
				}
			}

			//加载页面数据
			$.ajax({
				type : "POST",
				url : appPath+"/information/getPageRestaurant",
				success : function(msg) {
					restaurant = jQuery.parseJSON(msg);
					fileInput(restaurant);//加载input
					initMap(restaurant);//加载地图
					for ( var p in restaurant) {
						$("#" + p).val(restaurant[p]);
					}
					if(restaurant.isdelivery==1){
						$("#isdelivery").iCheck('check');
					}
					if(restaurant.ispickup==1){
						$("#ispickup").iCheck('check');
					}
					if(restaurant.isreservation==1){
						$("#isreservation").iCheck('check');
					}
					
					//加载所有的连锁店
					$.ajax({
						type : "POST",
						url : appPath+"/information/getAllChain",
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							for ( var i in msg) {
								if (restaurant.chainid == msg[i].id) {
									$("#chainid").append(
											"<option value="+msg[i].id+" selected='selected' >"
													+ msg[i].chainname + "</option>");
								} else {
									$("#chainid").append(
											"<option value="+msg[i].id+">" + msg[i].chainname + "</option>");
								}
							}
							$("#chainid").select2();
						}
					});

					//加载所有的区域
					$.ajax({
						type : "POST",
						url : appPath+"/information/getAreas",
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							for ( var i in msg) {
								if (restaurant.areasId == msg[i].areaId) {
									$("#areasId").append(
											"<option value="+restaurant.areasId+" selected='selected' >"
													+ msg[i].areaName + "</option>");
								} else {
									$("#areasId")
											.append("<option value="+msg[i].areaId+">" + msg[i].areaName + "</option>");
								}
							}
							$("#areasId").select2();
						}
					});

					$.ajax({//加载所有的餐厅分类
						type : "POST",
						url : appPath+"/information/getAllClassification",
						success : function(msg) {
							var msg = jQuery.parseJSON(msg);
							for ( var i in msg) {
								var appendStr = "<option value="+msg[i].id+">" + msg[i].classificationName
										+ "</option>";
								if (restaurant.classificationId.length > 0) {
									for ( var r in restaurant.classificationId) {//双层循环遍历餐厅已经有的分类
										if (restaurant.classificationId[r] == msg[i].id) {
											appendStr = "<option value="+msg[i].id+" selected='selected' >"
													+ msg[i].classificationName + "</option>";
										}
									}
								}
								$("#ClassificationId").append(appendStr);
							}
							$("#ClassificationId").select2({
								placeholder : "Select a classification"
							});
						}
					});
				}
			});

			$("input[name='restaurantName']").blur(function() {
				if (validateEmpty($(this))) {
					if (validateMaxLength($(this), 50)) {
						$.ajax({
							type : "POST",
							url : appPath+"/restaurant/checkRestaurantName",
							data : {
								'restaurantName' : $(this).val(),
								'id' : restaurant.id
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
				$(this).popover('destroy');
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

			$("input[name='distance']").blur(function() {
				if (validateDeceimal($(this))) {
					$(this).popover('destroy');
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='deliverPrice']").blur(function() {
				if (validateDeceimal($(this))) {
					$(this).popover('destroy');
				}
			}).focus(function() {
				$(this).popover('destroy');
			});

			$("input[name='avgPrice']").blur(function() {
				if (validateDeceimal($(this))) {
					$(this).popover('destroy');
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

			function validateMyForm() {
				var flag2 = false;
				var flag3 = false;
				var flag4 = false;
				var flag5 = false;
				var flag6 = false;
				
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

					if (validateDeceimal($("input[name='distance']")) && validateDeceimal($("input[name='deliverPrice']"))
							&& validateDeceimal($("input[name='avgPrice']"))) {
						flag4 = true;
					}
					
					if (validateEmpty($("input[name='restaurantAddress']"))) {
						if (validateMaxLength($("input[name='restaurantAddress']"), 200)) {
							flag5 = true;
						}
					}
					if(validateNumber($("input[name='deliverTime']"))){
						flag6 = true;
					}
					return flag2 && flag3 && flag4 && flag5 && flag6;

				
			}

			$("#sub").click(function() {
				$("#bg").css("display", "block");
				$("#show").css("display", "block");
				if (validateEmpty($("input[name='restaurantName']"))) {
					if (validateMaxLength($("input[name='restaurantName']"), 50)) {
						$.ajax({
							type : "POST",
							url : appPath+"/restaurant/checkRestaurantName",
							data : {
								'restaurantName' : $("input[name='restaurantName']").val(),
								'id' : restaurant.id
							},
							success : function(msg) {
								var msg = jQuery.parseJSON(msg);
								if (!msg.success) {
									validateErrorMsg($("input[name='restaurantName']"), msg.errorMsg);
								} else {//餐厅名称没有重复
									if (validateMyForm()) {
										$.ajax({
											type : "POST",
											url : appPath+"/information/updateRestaurant",
											data : $("#fm").serialize(),
											success : function(msg) {
												$("#bg").css("display", "none");
												$("#show").css("display", "none");
												var msg = jQuery.parseJSON(msg);
												if (!msg.success) {
													validateErrorMsg($("#sub"), msg.errorMsg);
												} else {
													window.location = appPath+"/information/information";
												}
											}
										});
									}
									else{
										$("#bg").css("display", "none");
										$("#show").css("display", "none");
									}
								}
							}
						});
					}
					else{
						$("#bg").css("display", "none");
						$("#show").css("display", "none");
					}
				}
				else{
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
				}
				
			});

		});