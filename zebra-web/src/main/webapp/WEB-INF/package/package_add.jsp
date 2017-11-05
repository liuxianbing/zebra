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
		        <c:if test="${pack.id==null }">创建系统套餐</c:if>
		        <c:if test="${pack.id!=null }">更新系统套餐</c:if>
		        	</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/pack/add" method="post" 
				class="validationform form-horizontal" id="validationform">
					<div class="row" style="margin-top:30px">
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">套餐类型</label>
			                  <div class="col-sm-8">
			                   <select id="planId" class="form-control select2" name="planId"
								style="float: left;" data-placeholder="套餐类型">
								  <c:forEach items="${planList }" var="pl" >
								     <option value="${pl.id }" 
								     <c:if test="${pack.planId==pl.id }">selected</c:if>
								      >${pl.name}--${pl.flow }MB</option>
								  </c:forEach>
								</select>
			                    <input type="hidden" name="id" value="${pack.id }">
			                     <input type="hidden" name="type" value="1">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						   <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">对外价格</label>
			                  <div class="col-sm-8">
			                    <input type="text" class="form-control validate[required,custom[number]]" value="${pack.externalQuote }"
			                    name="externalQuote" id="externalQuote" placeholder="对外价格">
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
					<div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">套餐备注</label>
			                  <div class="col-sm-8">
			                     <textarea rows="3" cols="10" style="height:80px" 
								   class="form-control"   name="remark" >
							     </textarea>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">短信数</label>
			                  <div class="col-sm-8">
			                    <input type="text"  class="form-control validate[custom[integer]]" 
			                    name="messages" id="messages" placeholder="短信数" value="${pack.messages }">
			                  </div>
			                </div>
						  </div>
					</div>
						<div class="row" >
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">通话分钟数</label>
			                  <div class="col-sm-8">
			                    <input type="text"  class="form-control validate[custom[integer]]" 
			                    name="callMinutes" id="callMinutes" placeholder="通话分钟数" value="${pack.callMinutes }">
			                  </div>
			                </div>
						  </div>
					</div>
					 <!-- /.box-body -->
	              <div class="row">
	                 <div class="col-md-12" style="text-align: center">
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
$("#remark").val('${pack.remark}')
$(".select2").select2({ allowClear:false});
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
var options={};
$('#submit').click(function(){
	SP.ajax($("#validationform"),options);
});
</script>