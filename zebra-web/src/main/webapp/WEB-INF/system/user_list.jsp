<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
		 <!-- Content Header (Page header)-->
		    <section class="content-header">
		    	  <c:if test="${CURRENT_USER.role==0 }">
		      <h1>
		      	  客户管理
		        <small>客户列表</small>
		      </h1>
		      </c:if>
		        <c:if test="${CURRENT_USER.role==1 }">
		      <h1>
		      	  用户管理
		        <small>用户列表</small>
		      </h1>
		      </c:if>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/user/list" method="post" id="searchform">
					<div class="row" style="padding: 10px">
						<div class="col-sm-2 col-md-2"
							style="padding-left: 8px; padding-right: 0px">
							<input type="text" id="search_name" name="keyword"
								class="form-control" placeholder="账号或手机号或姓名">
						</div>
						  <c:if test="${CURRENT_USER.role==1 }">
						  <input type="hidden" name="cid" value="${CURRENT_USER.cid }" />
						  </c:if>
						<!-- 
						<div class="col-sm-2 col-md-2">
							<select id="state" class="form-control select2" name="state"
								style="float: left;" data-placeholder="用户状态">
								<option value=" " selected="selected">全部</option>
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</div>
						 -->
				<div class="col-sm-2 col-md-2">
					 <button type="button" id="searchButton" onclick="loadTable()" class="btn btn-primary">查询</button>
                </div>
					</div>
				</form:form>
				<p class="btn_table">
				    <button type="button"   class="btn  margin btn-success">创建用户</button>
				    <button type="button"  class="btn  margin btn-primary disabled">更改用户</button>
				      <c:if test="${CURRENT_USER.role==0 }">
				    <button type="button" class="btn  margin btn-info disabled">用户认证</button>
				    </c:if>
				<p>
				<table id="table_list" class="table table-bordered table-hover">
				</table>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script type="text/javascript"
		src="${ctx }/assets/plugins/datatables.net/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${ctx }/assets/plugins/datatables.net-bs/js/dataTables.bootstrap2.js"></script>
</body>
</html>
<script>

     $("#state").select2({ allowClear:false}).on("select2-selecting", function(e) {
		}).on("change", function(e) {
			loadTable();
		})
		
		
	var options={};
     var oTable;
     var selectData;
	options.sAjaxSource="${ctx}/user/list";
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "ID",  "sClass": "center","sWidth":"80","mDataProp": "id"},
		 { "sTitle": "创建时间",  "sClass": "center","sWidth":"80","mDataProp": "createTime"},
		 { "sTitle": "联系人","sClass": "center" ,"sWidth":"100","mDataProp": "userName"},
       { "sTitle": "登录手机号",  "sClass": "center" ,"sWidth":"135", "mDataProp": "phone"},
	   { "sTitle": "邮箱",  "sClass": "center","sWidth":"80","mDataProp": "email"}
		];
	
	var ope={ "sTitle": "操作", "sClass": "center" ,"sWidth":"90","mDataProp": "status","mRender": function ( data, type, full ) {
		   return '<a href="${ctx}/user/packlist?id='+full.id+'">查看套餐</a>';
	   }
}
	var auth= { "sTitle": "是否认证",  "sClass": "center","sWidth":"80","mDataProp": "authStr"}
	  <c:if test="${CURRENT_USER.role==0 }">
	  options.aoColumns.push(ope)
	   options.aoColumns.push(auth)
	  </c:if>
	function loadTable(){
		if(oTable){
			 $("#table_list").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#table_list"),options,$("#searchform"));
	}
	
	 $('.btn_table').find('button').on('click', function () {
				 if($(this).hasClass('disabled')){
					  return;
				  }
		        if($(this).hasClass('btn-success')){
		        	if(selectData){
		        		window.location.href='${ctx}/user/add?id='+selectData.id;
		        	}else{
		        		window.location.href='${ctx}/user/add';
		        	}
		        }else if($(this).hasClass('btn-primary')){
		        	window.location.href='${ctx}/user/add?id='+selectData.id;
		        } else if($(this).hasClass('btn-info')){
		        	window.location.href='${ctx}/user/auth?uid='+selectData.id+"&cid="+selectData.cid;
		        }
			});
	
	 
	 
	loadTable();
  //  $('#example1').dataTable({"bServerSide":false,"bDestroy":true,"aaSorting":[[ 0, "asc" ]]});
</script>