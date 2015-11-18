
//桌位页面的js
		$(function() {
			//var appPath = "";
			var url;
			$("button[name='add']").click(function() {
				$("#myModal h4").text("Add table information");
				url = appPath+"/table/addRestaurantTable";
			});
			$("button[name='update']").click(function() {
				$("#myModal h4").text("Modify table information");
				url = appPath+"/table/updateRestaurantTable";
				var table = $('table').bootstrapTable('getSelections')[0];
				if (table) {
					$('#myModal').modal('show');
					for ( var t in table) {
						$("#" + t).val(table[t]);
					}
				}

			});
			$('#myModal').on('hidden.bs.modal', function(e) {
				$("#acceptanceNum").val('');
				$("#tableNum").val('');
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

			function validateMyForm() {
				var flag1 = false;
				var flag2 = false;
				if (validateEmpty($("#acceptanceNum"))) {
					if (validateDeceimal($("#acceptanceNum"))) {
						flag1 = true;
					}
				}
				if (validateEmpty($("#tableNum"))) {
					if (validateDeceimal($("#tableNum"))) {
						flag2 = true;
					}
				}
				return flag1 && flag2;
			}
			
			var table;
			$("button[name='delete']").click(function() {
				table = $('table').bootstrapTable('getSelections')[0];
				if (table) {
					$('#myModal1').modal('show');
				}
			});
			
			$("button[name='ok']").click(function() {
				$('#myModal1').modal('hide')
				$("#bg").css("display", "block");
				$("#show").css("display", "block");
				$.ajax({
					type : "POST",
					url : appPath+"/table/deleteRestaurantTable",
					data : {
						id : table.id
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