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
		      	  用户管理
		        <small>>创建用户</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/user/add" method="post" 
				class="validationform form-horizontal" id="validationform">
					<div class="row" style="margin-top:30px">
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">账号</label>
			                  <div class="col-sm-10">
			                    <input type="text"  class="form-control validate[required,custom[onlyLetterNumber]]" 
			                    name="account" id="account" value="${user.account }" placeholder="登录账号">
			                    <input type="hidden" name="id" value="${user.id }">
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">联系人</label>
			                  <div class="col-sm-10">
			                    <input type="text" class="form-control  validate[required,custom[letterHanzi]]" 
			                    name="userName" id="userName" value="${user.userName }" placeholder="联系人">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">手机号</label>
			                  <div class="col-sm-10">
			                    <input type="text"  class="form-control validate[required,custom[mobile]]" 
			                    name="phone" id="phone" placeholder="手机号" value="${user.phone }">
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">联系地址</label>
			                  <div class="col-sm-10">
			                    <input type="text" class="form-control" value="${user.address }"
			                    name="address" id="address" placeholder="联系地址">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
					    <!-- 
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">用户状态</label>
			                  <div class="col-sm-10">
			                   <label class="radio-inline"> <input type="radio"
										name="status" id="state1" value="1"
										${user.status ==1 ?'checked':''}> 启用
									</label> <label class="radio-inline"> <input type="radio"
										name="status" id="state2" value="0"
										${user.status ==null || user.status ==0 ?'checked':''}> 禁用
									</label>
			                  </div>
			                </div>
						  </div>
						   -->
						   <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">备注</label>
			                  <div class="col-sm-10">
			                     <textarea rows="3" cols="10" style="height:80px" 
								   class="form-control"  name="remark" id="remark">
							     </textarea>
			                  </div>
			                </div>
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