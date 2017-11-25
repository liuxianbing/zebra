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
		        <a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
			<jsp:include page="auth_fragment.jsp" />
			</section>
			<!-- /.content -->
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
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
var options={};

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