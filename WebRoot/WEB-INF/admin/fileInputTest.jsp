<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'fileInputTest.jsp' starting page</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
	<link rel="stylesheet" type="text/css" href="${ctx}/index/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/fileinput.css">
	<script src="${ctx}/index/js/jquery.min.js"></script>
	<script src="${ctx}/index/js/bootstrap.min.js"></script>

	
	<script type="text/javascript" src="${ctx}/js/fileinput.js"></script>
	<script src="${ctx}/js/fileinput_locale_LANG.js" type="text/javascript"></script>

  </head>
  
  <body>
    

	<div class="container">
    	<div class="row">
    		<div class="col-md-7">
    			<label class="control-label">Select File</label>
				<input id="input-1a" type="file" name="classification" class="file" data-show-preview="false">
    		</div>
    		<div class="col-md-5" style="border:1px solid red">
    		</div>
    	</div>
    
    </div>
  </body>
</html>

<script type="text/javascript">
	$("#input-1a").fileinput({
	    uploadUrl: '${ctx}/admin/uploadImage', // you must set a valid URL here else you will get an error
	    allowedFileExtensions : ['jpg', 'png','gif'],
	    overwriteInitial: false,
	    maxFileSize: 1000,
	    maxFilesNum: 1,
	    showUpload: true,
	    showCaption: true,
	    
	    //allowedFileTypes: ['image', 'video', 'flash'],
	    slugCallback: function(filename) {
	        return filename.replace('(', '_').replace(']', '_');
	    }
	});
	
	
	
</script>