<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML >
<html>
  <head>

    <title>My JSP 'dishesCategoryPage.jsp' starting page</title>
    <jsp:include page="../inputjs.jsp"></jsp:include>
	<jsp:include page="../inputcss.jsp"></jsp:include>
	<%-- <link rel="stylesheet" type="text/css" href="${ctx}/css/fileinput.css"> --%>

	<script type="text/javascript" src="${ctx}/js/appPath.js"></script>
	<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
	
	<%-- <script type="text/javascript" src="${ctx}/js/fileinput.js"></script>
	<script src="${ctx}/js/fileinput_locale_LANG.js" type="text/javascript"></script> --%>
	
	<style>  
		#uploadImg{ font-size:12px; overflow:hidden; position:absolute}  
		#file{ position:absolute; z-index:100; margin-left:-180px; font-size:60px;opacity:0;filter:alpha(opacity=0); margin-top:-5px;}  
		.dropdown-menu{
			height:62px;
		}
	
		.div1{
			float: left;
			height: 33px;
			background: #ffffff;
			border: 0px;
			border-radius: 4px;
			width: 320px;
			position: relative;
		}
		.div2{
			text-align:center;
			padding-top:4px;
			padding-left: 5px;
		    padding-right: 5px;
			font-size:15px;
			font-weight:800;
			float:left;
			border: 1px solid #bbbbbb;
			border-radius:4px;
		    background-color:#eeeeee;
		    height: 33px;
		    width: 106px;
		}
		.div3{
			text-align: left;
			padding-top:0px;
			padding-left:5px;
			padding-fight:5px;
			margin-top:5px;
			font-size:14px;
			width: 210px;
			float:right;
			height: 33px;
		}
		.inputstyle{
		    width: 290px;
		    height: 30px;
		    cursor: pointer;
		    font-size: 30px;
		    outline: medium none;
		    position: absolute;
		    filter:alpha(opacity=0);
		    -moz-opacity:0;
		    opacity:0; 
		    left:0px;
		    top: 0px;
		}
	</style>

	
  </head>
  <body>
  	<div id="custom-toolbar">
		<button type="button" id="addCuisine" class="btn btn-default mybt"
			data-toggle="modal" data-target="#cuisineModal" title="New Cuisine">
			<i class="fa fa-plus "></i><span class="toolDescribe"> Add</span>
		</button>
		<button type="button" id="modifyCuisine" class="btn btn-default mybt" title="Modify Cuisine">
			<i class="fa fa-pencil"></i><span class="toolDescribe"> Edit</span>
		</button>
		<button type="button" id="modifyImage" class="btn btn-default mybt" title="Modify Cuisine" style="width:150px;">
			<i class="glyphicon glyphicon-picture"></i><span class="toolDescribe">Maintain Image</span>
		</button>
		<button type="button" id="deleteCuisine" class="btn btn-default mybt" title="Delete Cuisine">
			<i class="fa fa-minus"></i> Delete
		</button>
		<button type="button" id="refresh" class="btn btn-default mybt"  data-method="refresh" title="refresh">
       		<i class="fa fa-refresh"></i><span class="toolDescribe"> Refresh</span>
	 	</button>
	</div>
    <table data-toggle="table" id="cuisineTable"
		data-url="${ctx}/admin/getallclassification"
		data-click-to-select="true"
		data-pagination="true"
		data-toolbar="#custom-toolbar"
		data-striped="true"
		data-page-size="10"
		data-page-list="10,20"
		>
		<thead>
			<tr>
				<th data-field="state" data-radio="true"></th>
				<th data-field="classificationName" data-sortable="true">Cuisine Name</th>
			</tr>
		</thead>
	</table>
	<!-- 删除菜系的模态框 -->
	<div class="modal" id="deleteCuisineModal" tabindex="-2" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Delete Cuisine</h4>
					</div>
					<div class="modal-body">
						<div style="text-align:center;"><h3><span id="attentionInfo">Do you want to delete this cuisine ?</span></h3></div>
						<div id="info" style="text-align:center; color:#FA6A6A;"></div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<button type="button" id="subDelete" class="btn bg-olive" style="width:80px;">Delete</button>
					</div>
				</div>
		</div>
	</div>
	<!-- 新增菜系名称的模态框 -->
	<div class="modal" id="cuisineModal" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<form id="cuisineForm" action="" method="POST">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">New Chain</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="classificationId" id="classificationId"><!-- 连锁店ID -->
						<div class="row" id= "selectElement">
							<div class="col-md-3"><label class="modalField">Cuisine Name:</label></div>
							<div class="col-md-9">
								<input type="text" name="classificationName" id="classificationName">
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
						<button type="button" id="sub" class="btn bg-olive" style="width:80px;">Save</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
	<!-- 维护菜系图片模态框 -->
	<div class="modal" id="cuisineImageModal" tabindex="-1" role="dialog"
		aria-labelledby="mySmallModalLabel" aria-hidden="true">
		<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
						</button>
						<h4 class="modal-title" style="font-weight: 400;">Upload Images</h4>
					</div>
					<div class="modal-body">
						<input type="hidden" name="image-classificationId" id="image-classificationId"><!-- 连锁店ID -->
						<div class="row" id= "selectElement" style="font-weight: bold;margin-bottom: 10px;">
							<div class="col-md-3" align="right">Cuisine Name:</div>
							<div class="col-md-1" ></div>
							<div class="col-md-6" >
								<div id="image-classificationName"></div>
								<!-- <input type="text" name="image-classificationName" id="image-classificationName" readonly="readonly"> -->
							</div>
							<div class="col-md-2" ></div>
						</div>
						
						<div class="row">
							<div class="col-md-3" align="right" style="font-weight: bold;">IOS:</div>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-5" align="center">
										<span>Image</span>
										<input type="radio" name="classification" id="ios-image" value="1-1">
										<p>
											<div class="show-classification-Image-div" name="show-ios-image">
											</div>
										</p>
									</div>
									<div class="col-md-5" align="left">
										<span>Hover image</span>
										<input type="radio" name="classification" id="ios-hover-image" value="1-2">
										<p>
											<div class="show-classification-Image-div" name="show-ios-hover-image">
											</div>
										</p>
									</div>
									<div class="col-md-2"></div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3" align="right" style="font-weight: bold;">Android:</div>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-5" align="center">
										<span>Image</span>
										<input type="radio" name="classification" id="android-image" value="2-1">
										<p>
											<div class="show-classification-Image-div" name="show-android-image">
											</div>
										</p>
									</div>
									<div class="col-md-5" align="left">
										<span>Hover image</span>
										<input type="radio" name="classification" id="android-hover-image" value="2-2">
										<p>
											<div class="show-classification-Image-div" name="show-android-hover-image">
											</div>
										</p>
									</div>
									<div class="col-md-2"></div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-3" align="right" style="font-weight: bold;">Web :</div>
							<div class="col-md-9">
								<div class="row">
									<div class="col-md-5" align="center">
									
										<span>Image</span>
										<input type="radio" name="classification" id="web-image" value="3-1">
										<p>
											<div class="show-classification-Image-div" name="show-web-image">
											</div>
										</p>
										
									</div>
									<div class="col-md-5" align="left">
									</div>
									 <div class="col-md-2">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-1" align="right" ></div>
							<div class="col-md-7" style="margin-left:0px;">
							<form method="post" enctype="multipart/form-data">
								<div class="div1">
								    <div class="div2">Select Image</div>
								    <div class="div3" id = "show-file-name"></div>
								  <input class="inputstyle" name='classification-image' id='input-image' type='file'/>
								</div>
							</form>
							</div>
							<div class="col-md-1" align="right"  style="margin-left: 20px;">
								<button class="btn btn-primary" id="submitImage">Submit</button>
							</div>
						</div>
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" style="width:80px;">Close</button>
					</div>
				</div>
			
		</div>
	</div>
	
	<script src="${ctx}/js/admin/cuisine.js"></script>


  </body>
  
</html>
