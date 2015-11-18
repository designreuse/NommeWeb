
//配菜的js

		

		function typeForMatter(value, row, index) {
			return value == 0 ? 'radio' : 'checkbox';
		}
		function mustForMatter(value, row, index) {
			return value == 0 ? 'yes' : 'no';
		}
		
		//var appPath = "";
		
		var url;
		
		$.ajax({
			type : "POST",
			url : appPath+"/garnish/dataTableGarnishHeader",
			success : function(msg) {
				var msg = jQuery.parseJSON(msg);
				initQuery(msg);
				$("#garnishHeaderIdQuery").select2({
					placeholder : "Select a Ingredient category"
				});
				for ( var m in msg) {
					$("#garnishHeaderId").append("<option value="+msg[m].id+">" + msg[m].garnishMenu + "</option>");
				}
				$("#garnishHeaderId").select2({
					placeholder : "Select a Ingredient category"
				});
				
			}
		});

		function initQuery(msg) {
			for ( var m in msg) {
				$("#garnishHeaderIdQuery").append(
						"<option value="+msg[m].id+">" + msg[m].garnishMenu + "</option>");
			}
		}
		
		$('#myModal').on('hidden.bs.modal', function(e) {//模态框消失置空表单
			$("input").val('');//置空表单
			$("#garnishHeaderId").select2('val','');
			$("input").popover('destroy');
			$("#garnishHeaderId").popover('destroy');
		})


		function validateMyForm() {//表单提交时验证
			flag1 = false;
			flag2 = false;
			if (validateEmpty($("#garnishName"))) {
				if (validateMaxLength($("#garnishName"), 255)) {
					flag1 = true;
				} 
			} 
			if(validateEmpty($("#garnishHeaderId"))){
				flag2 = true;
			}
			return flag1&&flag2;
		}

		$("button[name='add']").click(function() {
			$("#garnishName").popover('destroy');
			$("#myModalLabel").text(" Add new Ingredient");
			$("input").val('');//重置表单
			url = appPath+"/garnish/addGarnish";
		});

		$("button[name='update']").click(function() {
			$("#garnishName").popover('destroy');
			$("#myModalLabel").text(" modify the Ingredient");
			url = appPath+"/garnish/updateGarnish";
			var garnish = $('table').bootstrapTable('getSelections')[0];
			if (garnish) {
				for ( var p in garnish) {//循环表单注入值
					$("#" + p).val(garnish[p]);
				}
				$("#garnishHeaderId").select2('val',garnish.garnishHeaderId);
				$('#myModal').modal('show');
			}
		});

		var g;
		$("button[name='delete']").click(function() {
			g = $('table').bootstrapTable('getSelections')[0];
			if (g) {
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
				url : appPath+"/garnish/deleteGarnish",
				data : {
					'id' : g.id
				},
				success : function(msg) {
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
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
							validateErrorMsg($("#sub"), msg.errorMsg);
						} else {
							$("#myModal").modal('hide')
							$('table').bootstrapTable('refresh');
						}
					}
				});
			}
		});

		var garnishs;
		$('table').bootstrapTable({
			onLoadSuccess : function() {
				garnishs = $('table').bootstrapTable('getData');
			}
		});

		$("#garnishHeaderIdQuery").change(function() {
			if ($(this).val()) {
				var ghId = $(this).val();
				var gs = new Array();
				for ( var g in garnishs) {
					if (ghId == garnishs[g].garnishHeaderId) {
						gs.push(garnishs[g]);
					}
				}
				$('table').bootstrapTable('load', gs);
			} else {
				$('table').bootstrapTable('load', garnishs);
			}
	});

		