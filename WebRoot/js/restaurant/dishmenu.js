
//菜品分类的js
	$(function(){
		//var appPath = "";
		var url;
		$("#menuName").blur(function() {//焦点离开时验证
			if (validateEmpty($(this))) {
				if (validateMaxLength($(this), 50)) {
					$(this).popover('destroy');
				}
			}
		}).focus(function() {
			$(this).popover('destroy');
		});

		function validateMyForm() {//表单提交时验证
			if (validateEmpty($("#menuName"))) {
				if (validateMaxLength($("#menuName"), 50)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}

		$("button[name='add']").click(function() {
			$("#menuName").popover('destroy');
			$("#myModalLabel").text(" Add new dish category");
			$("input").val('');//重置表单
			url = appPath+"/dish/addRestaurantsMenu";
		});

		$("button[name='update']").click(function() {
			$("#menuName").popover('destroy');
			$("#myModalLabel").text(" Modify the dish category");
			url = appPath+"/dish/updateRestaurantsMenu";
			var dishMenu = $('table').bootstrapTable('getSelections')[0];
			if (dishMenu) {
				for ( var p in dishMenu) {//循环表单注入值
					$("#" + p).val(dishMenu[p]);
				}
				$('#myModal').modal('show');
			}
		});
		
		$('#myModal').on('hidden.bs.modal', function(e) {//模态框消失置空表单
			$("input").val('');//置空表单
			$("textarea").val('');//置空表单
			$("input").popover('destroy');
		})

		var g;
		$("button[name='delete']").click(function() {
			g = $('table').bootstrapTable('getSelections')[0];
			if (g) {
				$.ajax({
					type : "POST",
					url : appPath+"/dish/checkRestaurantMenu",
					data : {
						'id' : g.id
					},
					success : function(msg) {
						if (msg == -1) {
							$('#myModal1').modal('show');
						} else {
							$('#myModal2').modal('show');
						}
					}
				});
			}
		});

		$("button[name='no']").click(function() {
			$('#myModal1').modal('hide');
		});

		$("button[name='ok']").click(function() {
			$.ajax({
				type : "POST",
				url : appPath+"/dish/deleteRestaurantsMenu",
				data : {
					'id' : g.id
				},
				success : function(msg) {
					var msg = jQuery.parseJSON(msg);
					if (!msg.success) {
						validateErrorMsg($("#button[name='ok']"), msg.errorMsg);
					} else {
						$("#myModal1").modal('hide')
						$('table').bootstrapTable('refresh');
					}
				}
			});
		});

		$("#sub").click(function() {
			if (validateMyForm()) {
				$.ajax({
					type : "POST",
					url : url,
					data : $("#fm").serialize(),
					success : function(msg) {
						var msg = jQuery.parseJSON(msg);
						if (!msg.success) {
							validateErrorMsg($("#sub"), msg.errorMsg);
						} else {
							$("#myModal").modal('hide')
							$('table').bootstrapTable('refresh');
						}
					}
				});
			}
		});
	});
		