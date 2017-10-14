<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
	 <link rel="stylesheet" type="text/css" href="${ctx}/assets/plugins/data-tables/DT_bootstrap.css" />
 <link href="${ctx }/assets/plugins/dropzone/css/dropzone.css" rel="stylesheet"/>
 <link
	href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
<<style>
<!--
.modal-backdrop{
z-index:1
}
-->
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
		 <!-- Content Header (Page header)-->
		    <section class="content-header">
		      <h1>
		      	  用户管理
		        <small>用户认证信息</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/user/add" method="post" 
				class="validationform form-horizontal" id="validationform">
					<div class="row" style="margin-top:30px">
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">企业全称</label>
			                  <div class="col-sm-10">
			                    <input type="text"  class="form-control validate[required]" 
			                    name="name" id="name" value="${company.name }" placeholder="企业全称">
			                    <input type="hidden" name="id" value="${company.id }">
			                    <input type="hidden" name="uid" id="uid" value="${company.uid}" />
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-4 control-label">工商营业执照注册号</label>
			                  <div class="col-sm-8">
			                    <input type="text" class="form-control  validate[required]" 
			                    name="businessCode" id="businessCode" value="${company.businessCode }" placeholder="工商营业执照注册号">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">营业执照</label>
			                  <div class="col-sm-10">
			                      <button class="btn btn-info" data-target="#business" data-toggle="modal"   type='button' >点击按钮上传</button>
			                       <button class="btn btn-danger" id="delBusiness" style="margin-left:80px"   type='button' >删除图片</button>
			                       <input type="hidden" name="businessUrl" id="businessUrl" value="${company.businessUrl}" />
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6 businessCode" >
						    <c:if test="${company.businessUrl != null}">
						    	<img alt="营业执照" src="${ctx }/${company.businessUrl}" width="500px" height="300px"/>
						    </c:if>
						  </div>
					</div>
					
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">企业工商认证</label>
			                  <div class="col-sm-10">
			                   <label class="radio-inline"> <input type="radio"
										name="businessAuth" id="businessAuth1" value="1"
										${company.businessAuth ==1 ?'checked':''}> 是
									</label> <label class="radio-inline"> <input type="radio"
										name="businessAuth" id="businessAuth2" value="0"
										${company.businessAuth==null || company.businessAuth ==0 ?'checked':''}> 否
									</label>
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-4 control-label">企业法人认证</label>
			                  <div class="col-sm-8">
			                     <label class="radio-inline"> <input type="radio"
										name="legalAuth" id="legalAuth1" value="1"
										${company.legalAuth ==1 ?'checked':''}> 是
									</label> <label class="radio-inline"> <input type="radio"
										name="legalAuth" id="legalAuth2" value="0"
										${company.legalAuth==null || company.legalAuth ==0 ?'checked':''}> 否
									</label>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">身份证正面</label>
			                  <div class="col-sm-10">
			                      <button class="btn btn-info" data-target="#positive" data-toggle="modal"   type='button' >点击按钮上传</button>
			                       <button class="btn btn-danger" id="delPositive" style="margin-left:80px"   type='button' >删除图片</button>
			                       <input type="hidden" name="legalPositive" id="legalPositive" value="${company.legalPositive}" />
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6 legalPositive" >
						    <c:if test="${company.legalPositive != null}">
						    	<img  src="${ctx }/${company.legalPositive}" width="500px" height="300px"/>
						    </c:if>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">身份证背面</label>
			                  <div class="col-sm-10">
			                      <button class="btn btn-info" data-target="#backid" data-toggle="modal"   type='button' >点击按钮上传</button>
			                       <button class="btn btn-danger" id="delBack" style="margin-left:80px"   type='button' >删除图片</button>
			                       <input type="hidden" name="legalBack" id="legalBack" value="${company.legalBack}" />
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6 legalBack" >
						    <c:if test="${company.legalBack != null}">
						    	<img  src="${ctx }/${company.legalBack}" width="500px" height="300px"/>
						    </c:if>
						  </div>
					</div>
					 <!-- /.box-body -->
	              <div class="row">
	                 <div class="col-md-6" style="text-align: center">
	                <button type="button" id="submit" class="btn btn-info">提交</button>
	                </div>
	              </div>
				</form:form>
			</section>
			<!-- /.content -->
		</div>
		
			<div id="business" class="modal" style="z-index:5000"    data-keyboard="true" data-backdrop="static">
		  <div class="modal-header" >
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			  <h4 class="modal-title">上传营业执照</h4>
		  </div>
		  <div class="modal-body" style="overflow:hidden;">
			  <form action="${ctx}/user/uploadBusiness" class="dropzone" id="business-dropzone">
			  <div id="dz-message1" class="dz-message" style="margin:0 auto;text-align: center">
													 <h3 >
													  <span style="padding-top:15px;height:60px;">
													  <i class="fa fa-plus" style="font-size:35px;"></i>
													  </span><font style="margin-left:30px">点击区域或者拖动文件</font></h3>
													</div>
			  </form>
		  </div>
		  <div class="modal-footer">
			  <button type="button" data-dismiss="modal" id="closeupload1" class="btn btn-default">关闭</button>
	      </div>
	</div>
	
	<div id="positive" class="modal" style="z-index:5000"    data-keyboard="true" data-backdrop="static">
		  <div class="modal-header" >
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			  <h4 class="modal-title">上传身份证正面</h4>
		  </div>
		  <div class="modal-body" style="overflow:hidden;">
			  <form action="${ctx}/user/uploadPositive" class="dropzone" id="positive-dropzone">
			  <div id="dz-message2" class="dz-message" style="margin:0 auto;text-align: center">
													 <h3 >
													  <span style="padding-top:15px;height:60px;">
													  <i class="fa fa-plus" style="font-size:35px;"></i>
													  </span><font style="margin-left:30px">点击区域或者拖动文件</font></h3>
													</div>
			  </form>
		  </div>
		  <div class="modal-footer">
			  <button type="button" data-dismiss="modal" id="closeupload2" class="btn btn-default">关闭</button>
	      </div>
	</div>
	
	<div id="backid" class="modal" style="z-index:5000"    data-keyboard="true" data-backdrop="static">
		  <div class="modal-header" >
			  <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
			  <h4 class="modal-title">上传身份证背面</h4>
		  </div>
		  <div class="modal-body" style="overflow:hidden;">
			  <form action="${ctx}/user/uploadBack" class="dropzone" id="back-dropzone">
			  <div id="dz-message3" class="dz-message" style="margin:0 auto;text-align: center">
													 <h3 >
													  <span style="padding-top:15px;height:60px;">
													  <i class="fa fa-plus" style="font-size:35px;"></i>
													  </span><font style="margin-left:30px">点击区域或者拖动文件</font></h3>
													</div>
			  </form>
		  </div>
		  <div class="modal-footer">
			  <button type="button" data-dismiss="modal" id="closeupload3" class="btn btn-default">关闭</button>
	      </div>
	</div>
	
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script
	src="${ctx}/assets/plugins/validation/jquery.validationEngine.js"
	type="text/javascript"></script>
<script
	src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js"
	type="text/javascript"></script>
	<script type="text/javascript"  src="${ctx }/assets/plugins/dropzone/dropzone.js"></script>
	<script
	src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
	type="text/javascript"></script>
<script
	src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
	type="text/javascript"></script>
</body>
</html>
<script>
$('#remark').val('${user.remark}')
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
var options={};
$('#submit').click(function(){
	SP.ajax($("#validationform"),options);
});
if('${company.businessUrl}'==''){
	$("#delBusiness").addClass('disabled')
}
if('${company.legalPositive}'==''){
	$("#delPositive").addClass('disabled')
}
if('${company.legalBack}'==''){
	$("#delBack").addClass('disabled')
}
Dropzone.autoDiscover = false;
var dropzone1 = new Dropzone("#business-dropzone",{ acceptedFiles:'image/*',  maxFilesize:2,maxFiles:1});
dropzone1.on("success", function(file, responseText) {
	var fileName = "";
	if(responseText["filePath"]){
		fileName=responseText["filePath"];
		$("#businessUrl").val(fileName)
		$(".businessUrl").html('');
		$(".businessUrl").append('<img src="${ctx}/'+fileName+'" width="500px" height="300px"/> ')
		$("#delBusiness").removeClass('disabled')
	}
	$("#closeupload1").trigger('click')
});

var dropzone2 = new Dropzone("#positive-dropzone",{ acceptedFiles:'image/*',  maxFilesize:2,maxFiles:1});
dropzone2.on("success", function(file, responseText) {
	var fileName = "";
	if(responseText["filePath"]){
		fileName=responseText["filePath"];
		$("#legalPositive").val(fileName)
		$(".legalPositive").html('');
		$(".legalPositive").append('<img src="${ctx}/'+fileName+'" width="400px" height="260px"/> ')
		$("#delPositive").removeClass('disabled')
	}
	$("#closeupload2").trigger('click')
});

var dropzone3 = new Dropzone("#back-dropzone",{ acceptedFiles:'image/*',  maxFilesize:2,maxFiles:1});
dropzone3.on("success", function(file, responseText) {
	var fileName = "";
	if(responseText["filePath"]){
		fileName=responseText["filePath"];
		$("#legalBack").val(fileName)
		$(".legalBack").html('');
		$(".legalBack").append('<img src="${ctx}/'+fileName+'" width="400px" height="260px"/> ')
		$("#delBack").removeClass('disabled')
	}
	$("#closeupload3").trigger('click')
});
$("#delBusiness").click(function(){
	if($("#businessUrl").val().length>2){
		$(".businessUrl").find('img').remove();
		$("#businessUrl").val('')
		$("#delBusiness").addClass('disabled')
	}
})
$("#delBack").click(function(){
	if($("#legalBack").val().length>2){
		$(".legalBack").find('img').remove();
		$("#legalBack").val('')
		$("#delBack").addClass('disabled')
	}
})
$("#delPositive").click(function(){
	if($("#legalPositive").val().length>2){
		$(".legalPositive").find('img').remove();
		$("#legalPositive").val('')
		$("#delPositive").addClass('disabled')
	}
})
</script>