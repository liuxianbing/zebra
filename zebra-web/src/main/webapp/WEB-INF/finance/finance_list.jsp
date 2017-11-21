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
		      <h1>
		      	  账户余额	
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/finance/list" 
				method="post" id="searchform">
				  <c:if test="${CURRENT_USER.role==1 }">
				   <input type="hidden" name="uid" value="${CURRENT_USER.id }" />
				  </c:if>
				   <c:if test="${CURRENT_USER.role==0 }">
				   <div class="row">
				   <div class="col-sm-3">
			                    <select id="uidInner" class="form-control select2" name="uid"
								style="float: left;" data-placeholder="客户账号">
								     <option value=" ">全部客户</option>
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.userName }-${ul.companyName }</option>
								  </c:forEach>
								</select>
			                  </div>
			             </div>
				   </c:if>
				</form:form>
				 <div class="row" style="padding:20px;border-bottom:1px solid #ccc">
               			<p>当前余额 ：${bance }</p>
               </div>
				 <p class="lead" style="margin-top:15px">交易记录</p>
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
$(".select2").select2({ allowClear:false}).on("change", function(e) {
	loadTable()
})
	var options={};
     var oTable;
     var selectData;
	options.sAjaxSource="${ctx}/finance/list";
	options.aaSorting=[[ 0, "desc" ]];
	options.aoColumns=[
		 { "sTitle": "交易时间",  "sClass": "center","sWidth":"80","mDataProp": "createTime"},
		 { "sTitle": "交易类型","sClass": "center" ,"sWidth":"100","mDataProp": "typeStr"},
		 { "sTitle": "用户信息","sClass": "center" ,"sWidth":"180","mDataProp": "userInfo"},
       { "sTitle": "交易编号", "asSorting":[],"sClass": "center","sWidth":"120",
    	   "mDataProp": "orderCode","mRender": function ( data, type, full ) {
    		   if(data==null) return '无';
			 return '<a href="${ctx}/cart/detail?id='+full.orderId+'">'+data+'</a>';
			 }
		 },
       { "sTitle": "金额","sClass": "center" ,"sWidth":"100","mDataProp": "money"},
	   { "sTitle": "账号余额",  "sClass": "center","sWidth":"80","mDataProp": "balance"}
		];
	function loadTable(){
		if(oTable){
			 $("#table_list").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#table_list"),options,$("#searchform"));
	}
	loadTable();
	
</script>