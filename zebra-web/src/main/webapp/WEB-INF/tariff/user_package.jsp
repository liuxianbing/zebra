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
		      	  用户管理	
		        <small>用户套餐-》用户账号:${user.phone },用户名:${user.userName }</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/user/packlist" method="post" id="searchform">
				    <input type="hidden" name="id" value="${id }"/>
				    <div class="row" style="padding: 10px">
						<div class="col-sm-4 col-md-4">
							<select id="planId" class="form-control select2" name="planId"
								style="float: left;" data-placeholder="套餐类型">
								     <option value=" ">全部套餐类型</option>
								  <c:forEach items="${planList }" var="pl" >
								     <option value="${pl.id }" >${pl.name}--${pl.flow }MB</option>
								  </c:forEach>
							</select>
						</div>
						<div class="col-sm-2 col-md-2">
						<select id="status" class="form-control select2" name="status"
								style="float: left;" >
								<option value="0">有效</option>
								<option value="1">无效</option>
								</select>
						</div>
					</div>
				</form:form>
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

     $(".select2").select2({ allowClear:false}).on("select2-selecting", function(e) {
		}).on("change", function(e) {
			loadTable();
		})
	var options={};
     var oTable;
     var selectData;
	options.sAjaxSource="${ctx}/user/packlist";
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "ID", "bVisible":false, "sClass": "center","sWidth":"80","mDataProp": "id"},
		 { "sTitle": "套餐名称",  "sClass": "center","sWidth":"80","mDataProp": "name"},
	/// { "sTitle": "对外价格","sClass": "center" ,"sWidth":"100","mDataProp": "externalQuote"},
		 { "sTitle": "套餐价格","sClass": "center" ,"sWidth":"100","mDataProp": "externalQuote"},
       { "sTitle": "套餐期限（月)",  "sClass": "center" ,"sWidth":"75", "mDataProp": "term"},
       { "sTitle": "联通账号","sClass": "center" ,"sWidth":"100","mDataProp": "account"},
		 { "sTitle": "流量类型",  "sClass": "center" ,"sWidth":"75", "mDataProp": "type","mRender": function ( data, type, full ) {
			 if(data==0) return '独享';
			 return '共享';
		 }
	 },
     { "sTitle": "套餐类型",  "sClass": "center" ,"sWidth":"75", "mDataProp": "packageType","mRender": function ( data, type, full ) {
			 return '按月套餐';
		 }
	 },
     { "sTitle": "运营商",  "sClass": "center","sWidth":"90","mDataProp": "id","mRender": function ( data, type, full ) {
			 return '中国联通';
			 }
		 },
	   { "sTitle": "创建时间",  "sClass": "center","sWidth":"80","mDataProp": "createTime"}
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