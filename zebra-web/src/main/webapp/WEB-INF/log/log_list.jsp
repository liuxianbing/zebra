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
		      	  审计管理	
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
				<form:form onsubmit="return false" action="${ctx}/audit/list" method="post" id="searchform">
				  <div class="row">
				  <input type="hidden" name="zebraLike" value="content" />
				  <div class="col-sm-2 col-md-2"
							style="padding-left: 8px; padding-right: 0px">
							<input type="text" id="search_name" name="content"
								class="form-control" placeholder="操作内容">
						</div>
				   <div class="col-sm-3">
			                    <select id="uidInner" class="form-control select2" name="uid"
								style="float: left;" data-placeholder="全部用户">
								     <option value=" ">全部用户</option>
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.userName }</option>
								  </c:forEach>
								</select>
			                  </div>
			                  <div class="col-sm-2 col-md-2">
							<button type="button" id="searchButton" onclick="loadTable()"
								class="btn btn-primary">查询</button>
						</div>
			             </div>
				</form:form>
				<table id="table_list1" width="100%" class="table table-bordered table-hover">
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
	options.sAjaxSource="${ctx}/audit/list";
	options.bServerSide=false;
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		{ "sTitle": "操作时间",  "sClass": "center" ,"sWidth":"100", "mDataProp": "createTime"},
		 { "sTitle": "操作人",  "sClass": "center","sWidth":"50","mDataProp": "userName"},
	     { "sTitle": "操作类型","sClass": "center" ,"sWidth":"100","mDataProp": "typeStr"},
       { "sTitle": "操作内容","sClass": "center" ,"sWidth":"600","mDataProp": "content"}
		];
	function loadTable(){
		if(oTable){
			 $("#table_list1").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#table_list1"),options,$("#searchform"));
	}
	
	 
	 
	loadTable();
	
	  
  //  $('#example1').dataTable({"bServerSide":false,"bDestroy":true,"aaSorting":[[ 0, "asc" ]]});
</script>