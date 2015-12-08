$(function(){
	/*点击一个菜品*/
	$("div[name='aDish']").click(function(){
		$("#bg").css("display", "block");
		$("#show").css("display", "block");
		$("#dishDialogContent").empty();
		var str = $(this).attr("title");
		var dishPath = str.split("-");
		$("#currentDishId").val(dishPath[2]);
		//dishPath[0] 商家id, dishPath[1] 菜单分类id, dishPath[2] 菜品id 
		$.ajax({
			type:'post',
			url:appPath+'/restaurantMenu/getOneDish',
			data:{dishId:dishPath[2]},
			success:function(data2){
				$("#dishDialogContent").html(data2);
				$("#myModal1").modal('show');
				$("#bg").css("display", "none");
				$("#show").css("display", "none");
			}
		});
	})
	
	$("#dishMenuSelect").change(function(){
		var menuName = $(this).val();
		console.log($("div[name='"+menuName+"']"));
		$("div[name='"+menuName+"'] a:first-child").click();
		$('html,body').animate({scrollTop:$("div[name='"+menuName+"']").offset().top}, 800);
	})
	
	
	
})