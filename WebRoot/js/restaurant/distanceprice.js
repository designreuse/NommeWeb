
//送餐费页面的js

		$(function() {
			//var appPath = "";
			var url;
			$("button[name='add']").click(function() {
				$("#myModal h4").text("Add delivery role");
				url = appPath+"/distancePrice/addDistancePrice";
			});
			$("button[name='update']").click(function() {
				$("#myModal h4").text("Modify delivery role");
				url = appPath+"/distancePrice/updateDistancePrice";
				var distancePrice = $('table').bootstrapTable('getSelections')[0];
				if (distancePrice) {
					$('#myModal').modal('show');
					for ( var d in distancePrice) {
						$("#" + d).val(distancePrice[d]);
					}

				}

			});
			$('#myModal').on('hidden.bs.modal', function(e) {
				$("input").val('');//置空表单
				$("#startDistance,#endDistance,#orderPrice,#upPrice,#notupPrice").popover('destroy');
			})

			$("#sub").click(function() {
				if (validateMyForm()) {
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

			$("#startDistance,#endDistance,#orderPrice,#upPrice,#notupPrice").blur(function() {
				if(validateEmpty($(this))){
					if (validateDeceimal($(this))) {
						$(this).popover('destroy');
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
				
				if (validateEmpty($("#startDistance"))) {
					if (validateDeceimal($("#startDistance"))) {
						flag1 = true;
					}
				}
				
				if (validateEmpty($("#endDistance"))) {
					if (validateDeceimal($("#endDistance"))) {
						flag2 = true;
					}
				}

				
				
				if (validateEmpty($("#orderPrice"))) {
					if (validateDeceimal($("#orderPrice"))) {
						flag3 = true;
					}
				}
				
				if (validateEmpty($("#upPrice"))) {
					if (validateDeceimal($("#upPrice"))) {
						flag4 = true;
					}
				}
				
				if (validateEmpty($("#notupPrice"))) {
					if (validateDeceimal($("#notupPrice"))) {
						flag5 = true;
					}
				}
				
				return flag1 && flag2 && flag3 && flag4 && flag5;
			}

			$("button[name='delete']").click(function() {
				var distanPrice = $('table').bootstrapTable('getSelections')[0];
				if (distanPrice) {
					$('#myModal1').modal('show');
				}
			});
			
			$("button[name='ok']").click(function() {
				var distanPrice = $('table').bootstrapTable('getSelections')[0];
				$('#myModal1').modal('hide')
				$("#bg").css("display", "block");
				$("#show").css("display", "block");
				$.ajax({
					type : "POST",
					url : appPath+"/distancePrice/deleteDistancePrice",
					data : {
						id : distanPrice.id
					},
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