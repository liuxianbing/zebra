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
		      	  资费计划管理	
		        <small>资费计划列表</small>
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
				<form:form onsubmit="return false" action="${ctx}/tariffplan/list" method="post" id="searchform">
				</form:form>
				<p class="btn_table">
				    <button type="button"   class="btn  margin btn-success">创建计划</button>
				    <button type="button"  class="btn  margin btn-primary disabled">更改计划</button>
				    <button type="button"  class="btn  margin btn-danger disabled">删除计划</button>、
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
	options.sAjaxSource="${ctx}/tariffplan/list";
	options.bServerSide=false;
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "计划名称",  "sClass": "center","sWidth":"80","mDataProp": "name"},
		 { "sTitle": "流量类型",  "sClass": "center" ,"sWidth":"75", "mDataProp": "type","mRender": function ( data, type, full ) {
			 if(data==0) return '独享';
			 return '共享';
		 }
	 },
	 { "sTitle": "联通账号","sClass": "center" ,"sWidth":"100","mDataProp": "account"},
		 { "sTitle": "流量","sClass": "center" ,"sWidth":"100","mDataProp": "flow"},
       { "sTitle": "费用",  "sClass": "center" ,"sWidth":"75", "mDataProp": "cost"},
	   { "sTitle": "短信数",  "sClass": "center","sWidth":"80","mDataProp": "messages"},
	   { "sTitle": "通话分钟数",  "sClass": "center","sWidth":"80","mDataProp": "callMinutes"},
	   { "sTitle": "运营商",  "sClass": "center","sWidth":"90","mDataProp": "operator","mRender": function ( data, type, full ) {
			 return '中国联通';
			 }
		 }
		];
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
		        	//if(selectData){
		        		//window.location.href='${ctx}/tariffplan/add?id='+selectData.id;
		        	//}else{
		        		window.location.href='${ctx}/tariffplan/add';
		        	//}
		        }else if($(this).hasClass('btn-primary')){
		        	window.location.href='${ctx}/tariffplan/add?id='+selectData.id;
		        }else if($(this).hasClass('btn-danger')){
		        	delPlan();
		        }
			});
	
	 
	 
	loadTable();
	
	function delPlan(){
		bootbox.confirm('是否确认删除'+selectData.name, function(result) {
			  if(result){
				  var opt={};
				  opt.url="${ctx}/tariffplan/del?id="+selectData.id;
				  opt.success=function(data){
						 toastr.success('操作成功');
						  oTable.fnDraw();
						  $(".btn_table").find('.btn').not(".btn-success").addClass("disabled");
					  };
					 SP.ajax($("#searchform"),opt);
			  }
	      });
	}
	
	  
  //  $('#example1').dataTable({"bServerSide":false,"bDestroy":true,"aaSorting":[[ 0, "asc" ]]});
</script>