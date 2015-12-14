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
	
	// Expand the coupons menu on page load.
	$(document).ready(function() {
		$(".panel-default a[href=#collapseOne]").removeClass("collapsed");
		$("#collapseOne").removeClass("collapsed").addClass("in").css("height","auto");
	});
	
	$("#dishMenuSelect").change(function(){
		var menuName = $(this).val();
		console.log($("div[name='"+menuName+"']"));
		$("div[name='"+menuName+"'] a:first-child").click();
		$('html,body').animate({scrollTop:$("div[name='"+menuName+"']").offset().top}, 800);
	})
	
	// Expand all the menu sections.
	$("#expandAllMenuSections").click(function(){
		$("#accordion [data-parent=#accordion]").removeClass("collapsed");
		$("#accordion .panel-collapse").removeClass("collapsed").addClass("in").css("height","auto");
	});
})