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
		      	  资费计划管理
		        <small>
		        <c:if test="${card.id==null }">创建资费计划</c:if>
		        <c:if test="${card.id!=null }">更新资费计划</c:if>
		        	</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/tariffplan/add" method="post" 
				class="validationform form-horizontal" id="validationform">
					<div class="row" style="margin-top:30px">
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">计划名称</label>
			                  <div class="col-sm-10">
			                    <input type="text"  class="form-control validate[required]" 
			                    name="name" id="name" value="${card.name }" placeholder="计划名称">
			                    <input type="hidden" name="id" value="${card.id }">
			                  </div>
			                </div>
						  </div>
						  <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">计划类型</label>
			                  <div class="col-sm-10">
			                   <label class="radio-inline"> <input type="radio"
										name="type" id="type1" value="1"
										${card.type ==1 ?'checked':''}> 共享
									</label> <label class="radio-inline"> <input type="radio"
										name="type" id="type2" value="0"
										${card.type ==0 ?'checked':''}> 独享
									</label>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">流量</label>
			                  <div class="col-sm-10">
			                    <input type="text"  class="form-control validate[required,custom[integer]]" 
			                    name="flow" id="flow" placeholder="流量MB(数字)" value="${card.flow }">
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">费用</label>
			                  <div class="col-sm-10">
			                    <input type="text" class="form-control validate[required,custom[number]]" value="${card.cost }"
			                    name="cost" id="cost" placeholder="费用(元)">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">计划ID</label>
			                  <div class="col-sm-10">
			                    <input type="text"  class="form-control validate[required,custom[integer]]" 
			                    name="planId" id="planId" placeholder="计划ID" value="${card.planId }">
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">版本ID</label>
			                  <div class="col-sm-10">
			                      <input type="text"  class="form-control validate[required,custom[integer]]" 
			                    name="versionId" id="versionId" placeholder="版本ID" value="${card.versionId }">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-5">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">所属账号</label>
			                  <div class="col-sm-10">
			                    <select id="account" class="form-control" name="account"
									style="float: left;" data-placeholder="所属账号">
									<c:forEach var="sns" items="${accounts}">
										<option value="${sns}"
											<c:if test="${card.account== sns}">
													    selected="selected"
													    </c:if>>${sns}</option>
									</c:forEach>
								</select>
			                  </div>
			                </div>
						  </div>
						   <div class="col-md-6">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">运营商</label>
			                  <div class="col-sm-10">
			                     <select id="carrier" class="form-control" name="carrier"
									style="float: left;" data-placeholder="所属账号">
									<c:forEach var="sns" items="${carries}">
										<option value="${sns}"
											<c:if test="${card.carrier== sns}">
													    selected="selected"
													    </c:if>>${sns}</option>
									</c:forEach>
								</select>
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
$(".select2").select2({ allowClear:false});
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
var options={};
$('#submit').click(function(){
	SP.ajax($("#validationform"),options);
});
</script>