
//优惠信息页面的js

		function typeFormatter(value, row, index) {
			if (value == 1) {
				return "coupon";
			} else if (value == 2) {
				return "discount";
			} else if (value == 3) {
				return "free dish";
			}
		}

		function orderTypeFormatter(value, row, index) {
			if (value == 1) {
				return "Delivery";
			} else if (value == 2) {
				return "Pick-up";
			} else if (value == 3) {
				return "Reservation";
			}

		}

		function contentFormatter(value, row, index) {
			if (value.length > 30) {
				return value.substring(0, 29) + "...";
			} else {
				return value;
			}
		}

		$(function() {
			//var appPath = "";
			
			$.ajax({//异步加载所有的菜品
				type : "POST",
				url : appPath+"/dish/getAllAvailableDish",
				success : function(msg) {
					var dishMenu = $.parseJSON(msg);
					for ( var d in dishMenu) {
						$("#dishId").append("<option value="+dishMenu[d].id+">" + dishMenu[d].enName + "</option>");
					}
					$("#dishId").select2({
						placeholder : "Select a dish"
					});
				}
			});

			$("#type").select2({
				placeholder : "Select a discount type"
			});
			$("#orderType").select2({
				placeholder : "Select a order type"
			});

			$("#type").change(function() {
				$(this).popover('destroy');
				if ($(this).val() == 1) {
					$("#displayPrice").css('display', 'block');
					$("#displayDiscount").css('display', 'none');
					$("#discount").val('');
					$("#displayType").css('display', 'none');
					$("#dishId").select2("val", '');
				} else if ($(this).val() == 2) {
					$("#displayPrice").css('display', 'none');
					$("#price").val('');
					$("#displayDiscount").css('display', 'block');
					$("#displayType").css('display', 'none');
					$("#dishId").select2("val", '');
				} else if ($(this).val() == 3) {
					$("#displayPrice").css('display', 'none');
					$("#price").val('');
					$("#displayDiscount").css('display', 'none');
					$("#discount").val('');
					$("#displayType").css('display', 'block');
				}
			});
			
			$("#orderType").change(function(){
				$(this).popover('destroy');
			});

			$('#startTime,#endTime').datetimepicker({
				format : "MM dd,yyyy, hh:ii P",
				weekStart : 1,
				autoclose : 1,
				startView : 2,
				minView : 0,
				maxView : 1,
				forceParse : 0
			});

			var url;

			$("button[name='add']").click(function() {
				$("#myModal h4").text("Add promotion");
				url = appPath+"/discount/addDiscount";
			});
			$("button[name='update']").click(function() {
				$("#myModal h4").text("Modify promotion");
				url = appPath+"/discount/updateDiscount";
				var discount = $('table').bootstrapTable('getSelections')[0];
				if (discount) {
					$('#myModal').modal('show');
					for ( var d in discount) {
						$("#" + d).val(discount[d]);
					}
					$("#type").select2("val", discount.type);
					
					if(discount.price){
						$("#displayPrice").css('display', 'block');
					}
					else if(discount.discount){
						$("#displayDiscount").css('display', 'block');
					}
					else if(discount.dishId){
						$("#displayType").css('display', 'block');
						$("#dishId").select2("val", discount.dishId);
					}
					$("#orderType").select2("val",discount.orderType);
				}

			});
			$('#myModal').on('hidden.bs.modal', function(e) {
				$("input").val('');//置空表单
				$("input").popover('destroy');
				$("#type").select2("val", "");
				$("#dishId").select2("val", "");
				$("#orderType").select2("val", "");
				$("#displayPrice").css('display', 'none');
				$("#displayDiscount").css('display', 'none');
				$("#displayType").css('display', 'none');
				$("textarea").val('');
			})

			$("#sub").click(function() {
				if(validateMyForm()){
					$('#myModal').modal('hide')
					$("#bg").css("display", "block");
					$("#show").css("display", "block");
					$.ajax({
						type : "POST",
						url : url,
						data : $("#fm").serialize(),
						cache: false,
						success : function(msg) {
							$("#bg").css("display", "none");
							$("#show").css("display", "none");
							var msg = $.parseJSON(msg);
							if (!msg.success) {
								alert(msg.errorMsg);
							} else {
								$('table').bootstrapTable('refresh');
							}
						}
					});
				}
			});

			 $("#type,#consumePrice,#startTime,#endTime,#orderType").focus(function() {
				$(this).popover('destroy');
			}); 
			function validateMyForm() {
				var flag1 = false;
				var flag2 = false;
				var flag3 = false;
				var flag4 = false;
				var flag5 = false;
				var flag6 = true;
				var flag7 = true;
				if (validateEmpty($("#type"))) {
					flag1 = true;
				}
				if (validateEmpty($("#consumePrice"))) {
					if (validateDeceimal($("#consumePrice"))) {
						flag2 = true;
					}
				}

				if (validateEmpty($("#startTime"))) {
					flag3 = true;
				}
				if (validateEmpty($("#endTime"))) {
					flag4 = true;
				}
				if (validateEmpty($("#orderType"))) {
					flag5 = true;
				}
				if($("#price").val()!=''){
					if (!validateDeceimal($("#price"))) {//验证现金优惠是否是数字
						flag6 = false;
					}
				}
				if($("#discount").val()!=''){
					if (!validateDeceimal($("#discount"))) {//验证打折优惠是否是数字
						flag7 = false;
					}
				}
				
				return flag1 && flag2 && flag3 && flag4 && flag5 && flag6 && flag7;
			}

			$("button[name='delete']").click(function() {
				var discount = $('table').bootstrapTable('getSelections')[0];
				if (discount) {
					$('#myModal1').modal('show');
				}
			});
			
			$("button[name='ok']").click(function() {
				var discount = $('table').bootstrapTable('getSelections')[0];
				$('#myModal1').modal('hide')
				$("#bg").css("display", "block");
				$("#show").css("display", "block");
				$.ajax({
					type : "POST",
					url : appPath+"/discount/deleteDiscount",
					data : {
						id:discount.id
					},
					cache: false,
					success : function(msg) {
						$("#bg").css("display", "none");
						$("#show").css("display", "none");
						var msg = $.parseJSON(msg);
						if (!msg.success) {
							alert(msg.errorMsg);
						} else {
							$('table').bootstrapTable('refresh');
						}
					}
				});
			});
		});