<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/data-tables/DT_bootstrap.css" />
<link href="${ctx }/assets/plugins/dropzone/css/dropzone.css"
	rel="stylesheet" />
<link
	href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header)-->
			<section class="content-header">
				<h1>账户设置<a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a></h1>
				
			</section>
			<!-- Main content -->
			<section class="content">

				<div class="nav-tabs-custom">
					<ul class="nav nav-tabs">
						<li class="active"><a href="#activity" data-toggle="tab">基本信息</a></li>
						<li><a href="#timeline" data-toggle="tab">修改密码</a></li>
						<c:if test="${CURRENT_USER.role==1 }">
						  <li><a href="#settings" data-toggle="tab">企业信息</a></li>
						</c:if>
					</ul>
					<div class="tab-content">
						<div class="active tab-pane" id="activity">
							<form:form onsubmit=" return false;" action="${ctx}/user/add"
								method="post" class="validationform form-horizontal"
								id="validationform">
								<div class="row" style="margin-top: 30px">
									<div class="col-md-5">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
											<div class="col-sm-10">
												<input type="text"
													class="form-control validate[required,custom[mobile]]"
													name="phone" id="phone" placeholder="手机号"
													value="${user.phone }">
													<input type="hidden" name="id"
													value="${user.id }">
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">联系人</label>
											<div class="col-sm-10">
												<input type="text"
													class="form-control  validate[required,custom[letterHanzi]]"
													name="userName" id="userName" value="${user.userName }"
													placeholder="联系人">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-5">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">邮箱</label>
											<div class="col-sm-10">
												<input type="text"
													class="form-control validate[custom[email]]"
													name="email" id="email" placeholder="邮箱"
													value="${user.email }">
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">联系地址</label>
											<div class="col-sm-10">
												<input type="text" class="form-control"
													value="${user.address }" name="address" id="address"
													placeholder="联系地址">
											</div>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-5">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">用户状态</label>
											<div class="col-sm-10">
												<label class="radio-inline"> <input type="radio"
													name="state" id="state1" value="1"
													${user.status ==1 ?'checked':''}> 启用
												</label> <label class="radio-inline"> <input type="radio"
													name="state" id="state2" value="0"
													${user.status ==0 ?'checked':''}> 禁用
												</label>
											</div>
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label for="inputEmail3" class="col-sm-2 control-label">备注</label>
											<div class="col-sm-10">
												<textarea rows="3" cols="10" style="height: 80px"
													class="form-control" name="remark" id="remark">
												     </textarea>
											</div>
										</div>
									</div>
								</div>
								<!-- /.box-body -->
								<div class="row">
									<div class="col-md-6" style="text-align: center">
										<button type="button" id="submit" class="btn btn-info">修改</button>
									</div>
								</div>
							</form:form>
						</div>
						<div class="tab-pane" id="timeline">
							<form:form onsubmit=" return false;"
								action="${ctx}/user/modifyPwd" method="post"
								class="validationform form-horizontal" id="validationform2">
								<div class="row" style="margin-top: 30px">
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">原始密码</label>
										<div class="col-sm-4">
											<input type="password"
												class="form-control  validate[required,maxSize[16],minSize[6]]"
												name="oldPwd" id="oldPwd" placeholder="原始密码">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">新密码</label>
										<div class="col-sm-4">
											<input type="password"
												class="form-control  validate[required,maxSize[16],minSize[6]]"
												id="newPwd" name="newPwd" placeholder="新密码  至少6位">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">重复新密码</label>
										<div class="col-sm-4">
											<input type="password"
												class="form-control  validate[required,maxSize[16],minSize[6]]"
												id="againPwd" name="againPwd" placeholder="新密码">
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6" style="text-align: center">
										<button type="button" id="submit2" class="btn btn-info">提交</button>
									</div>
								</div>
							</form:form>
						</div>
						<c:if test="${CURRENT_USER.role==1 }">
						<div class="tab-pane" id="settings">
							<jsp:include page="auth_fragment.jsp" />
						</div>
						</c:if>
					</div>
				</div>


			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script src="${ctx}/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js"
		type="text/javascript"></script>

	<script type="text/javascript"
		src="${ctx }/assets/plugins/dropzone/dropzone.js"></script>
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
$("#activity").find('input').attr('disabled','disabled')
$("#submit").click(function(){
	if($("#activity").find('#phone').attr('disabled')=='disabled'){
	$("#submit").html('提交');
	$("#activity").find('input').attr('disabled',false)
	$("#account").attr('disabled',true)
	}else{
		SP.ajax($("#validationform"),options);
	}
})

$("#submit2").click(function(){
	if( $("#newPwd").val()!=$("#againPwd").val() ) {
		alert("两次输入新密码不一致,请重新输入");
		return;
	}
	SP.ajax($("#validationform2"),options);
});


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
}).on("complete", function(file) {
	dropzone1.removeFile(file);
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
}).on("complete", function(file) {
	dropzone2.removeFile(file);
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
}).on("complete", function(file) {
	dropzone3.removeFile(file);
});
</script>