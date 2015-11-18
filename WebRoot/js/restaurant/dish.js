
//菜品表格的js
	
			//var appPath = "";
	
		/*function roleForMatter(value, row, index) {
			var role = value.toString(2);
			var str = '';
			if (role.length == 1) {
				role = '00' + role;
			} else if (role.length == 2) {
				role = '0' + role;
			}

			if (role.charAt(2) == 1) {
				str = str + 'Delivery ';
			}
			if (role.charAt(1) == 1) {
				str = str + 'Pick-up ';
			}
			if (role.charAt(0) == 1) {
				str = str + 'Resvertion';
			}

			return str;
		}*/

		function photoForMatter(value, row, index) {
			if (value) {
				return "<img src="+value+" width='25px' onerror=javascript:this.src='"+appPath+"/images/no-picture.jpg'>";
			}
			else{
				return "<img src='"+appPath+"/images/no-picture.jpg' width='25px'>";
			}
		}

		function spicyForMatter(value, row, index) {
			if (value == 0) {
				return "NO spice";
			} else if (value == 1) {
				return "mild";
			} else if (value == 2) {
				return "medium";
			} else if (value == 3) {
				return "rxtra";
			}
		}

		function msgForMatter(value, row, index) {
			if (value == 0) {
				return "No";
			} else if (value == 1) {
				return "Yes";
			}
		}

		function isStarchForMatter(value, row, index) {
			if (value == 0) {
				return "No";
			} else if (value == 1) {
				return "Yes";
			}
		}
		function shelvesForMatter(value, row, index) {
			if (value == 0) {
				return "Yes";
			} else {
				return "No";
			}
		}



		$("button[name='add']").click(function() {
			location = appPath+"/dish/toDishManager?type=add";
		});

		$("button[name='update']").click(function() {
			var dish = $('table').bootstrapTable('getSelections')[0];
			if (dish) {
				location = appPath+"/dish/toDishManager?type=update&id=" + dish.id;
			}
		});
		
		var dish;
		$("button[name='delete']").click(function(){
			dish = $('table').bootstrapTable('getSelections')[0];
			if(dish){
				$("#myModal1").modal('show');
				$("#status").val(dish.status);
			}
		});
		
		$('#myModal1').on('hidden.bs.modal', function(e) {
			$("#display").css('display','none');
		})
		
		$("#status").change(function(){
			if($(this).val()==1){//调整为下架状态，判断
				if(dish.status==0){//判断菜品原来是否是上架状态
					$.ajax({
						type : "POST",
						url : appPath+"/discount/getDiscountByDishId",
						data : {
							'dishId' : dish.id
						},
						success : function(msg) {
							if(msg==1){
								$("#display").css('display','block');
							}
							else{
								$("#display").css('display','none');
							}
						}
					});
				}
			}
			else{
				$("#display").css('display','none');
			}
		});
		
		
		$("button[name='ok']").click(function(){
			$('#myModal1').modal('hide')
			$("#bg").css("display", "block");
			$("#show").css("display", "block");
			$.ajax({
				type : "POST",
				url : appPath+"/dish/deleteDish",
				data : {
					'id' : dish.id,
					'status':$("#status").val()
				},
				success : function(msg) {
					$("#bg").css("display", "none");
					$("#show").css("display", "none");
					var msg = jQuery.parseJSON(msg);
					if (!msg.success) {
						alert(msg.errorMsg);
					} else {
						$("#myModal1").modal('hide')
						$('table').bootstrapTable('refresh');
					}
				}
			});
		});

	
		$.ajax({
			type : "POST",
			url : appPath+"/dish/getAllDishMenu",
			success : function(msg) {
				var msg = jQuery.parseJSON(msg);
				for(var m in msg){
					$("#dishMenuQuery").append(
							"<option value="+msg[m].id+">" + msg[m].menuName + "</option>");
				}
				$("#dishMenuQuery").select2({
					placeholder : "Select a dish category"
				});
				
			}
		});
		
		var dish;
		$('table').bootstrapTable({
			onLoadSuccess : function() {
				dish = $('table').bootstrapTable('getData');
			}
		});
		
		$("#dishMenuQuery").change(function(){
			if ($(this).val()) {
				var dishs = [];
				for ( var d in dish) {
					if ($(this).val() == dish[d].dishMenuId) {
						dishs.push(dish[d]);
					}
				}
				$('table').bootstrapTable('load', dishs);
			}
		
	});

		
		