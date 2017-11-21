<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.css" />
	
	<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
 <link href="${ctx }/assets/plugins/dropzone/css/dropzone.css" rel="stylesheet"/>
 <link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
	 <link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
	
<style>
#validationform .row{
margin-top:15px
}
.bootbox-alert{
	background: none;
	border:none;
	box-shadow:none;
}
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
		      	  流量池
		        <small>流量池列表</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/flow/list" method="post" id="searchform">
					<div class="row" style="padding: 10px">
						<div class="col-sm-2 col-md-2"
							style="padding-left: 8px; padding-right: 0px">
							<input type="text" id="search_name" name="keyword"
								class="form-control" placeholder="用户手机号或用户名">
						</div>
						<div class="col-sm-2 col-md-2">
							<select id="flow" class="form-control select2" name="flow"
								style="float: left;" data-placeholder="流量大小">
								<option value=" " selected="selected">全部流量</option>
								  <c:forEach items="${flowList }" var="ul" >
								     <option value="${ul }" >${ul }MB</option>
								  </c:forEach>
							</select>
						</div>
						<div class="col-sm-3 col-md-3">
							<select id="cid" class="form-control select2" name="cid"
								style="float: left;" data-placeholder="">
								<option value=" " selected="selected">全部企业</option>
								  <c:forEach items="${companyList }" var="ul" >
								     <option value="${ul }" >${ul.name }</option>
								  </c:forEach>
							</select>
						</div>
				<div class="col-sm-2 col-md-2">
					 <button type="button" id="searchButton" onclick="loadTable()" class="btn btn-primary">查询</button>
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
		
		<script src="${ctx}/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js"
		type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>
		
</body>
</html>
<script>
var dataTableObj;
    $(".select2").select2({ allowClear:false}).on("select2-selecting", function(e) {
		}).on("change", function(e) {
			loadTable();
		})
		
	var options={};
    
     var oTable;
     var selectData;
     options.bServerSide=false;
	options.sAjaxSource="${ctx}/flow/list";
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "流量池规格",  "sClass": "center","sWidth":"220","mDataProp": "flowName","mRender": function ( data, type, full ) {
			 return '<a href="${ctx}/flow/detail?flow='+full.flow+'&cid='+full.cid+'">'+data+'</a>';
			 }
		 },
		 { "sTitle": "流量池总计","sClass": "center" ,"sWidth":"100","mDataProp": "totalPool"},
		 { "sTitle": "已使用流量MB","sClass": "center" ,"sWidth":"100","mDataProp": "usePool"},
		 { "sTitle": "剩余流量MB","sClass": "center" ,"sWidth":"100","mDataProp": "leftPool"},
       { "sTitle": "剩余比例%",  "sClass": "center" ,"sWidth":"75", "mDataProp": "leftPercent"},
	   { "sTitle": "激活卡量",  "sClass": "center","sWidth":"80","mDataProp": "activeNum"},
	   { "sTitle": "停用卡量",  "sClass": "center","sWidth":"80","mDataProp": "blockNum"},
	   { "sTitle": "库存卡量",  "sClass": "center","sWidth":"80","mDataProp": "stockNum"},
	   { "sTitle": "卡总量",  "sClass": "center","sWidth":"80","mDataProp": "allNum"},
	   { "sTitle": "最近同步时间",  "sClass": "center","sWidth":"90","mDataProp": "lastSyncTime"},
	   { "sTitle": "企业名称", "sClass": "center" ,"sWidth":"90","mDataProp": "companyName"}
		];
	
	function loadTable(){
		if(oTable){
			 $("#table_list").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#table_list"),options,$("#searchform"));
		dataTableObj=oTable.api();
	}
	
	 
	loadTable();
</script>