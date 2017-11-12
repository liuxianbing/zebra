<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
		 <!-- Content Header (Page header)-->
		    <section class="content-header">
		      <h1>
		      	  财务管理
		        <small>客户充值</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/finance/add" method="post" 
				class="validationform form-horizontal" id="validationform">
					<div class="row" style="margin-top:30px">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">客户账号</label>
			                  <div class="col-sm-6">
			                    <select id="uidInner" class="form-control select2" name="uid"
								style="float: left;" data-placeholder="客户账号">
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.phone }-${ul.userName }</option>
								  </c:forEach>
								</select>
			                  </div>
			                </div>
					</div>
					<div class="row" >
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">充值金额</label>
			                  <div class="col-sm-6">
			                    <input type="text" class="form-control validate[required,custom[integer]]" 
			                    name="money" id="money" placeholder="充值金额">
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
</script>