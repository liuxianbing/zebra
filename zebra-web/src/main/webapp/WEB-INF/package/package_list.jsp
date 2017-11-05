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
		        <small>系统套餐列表</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/pack/list" method="post" id="searchform">
				    <input type="hidden" name="id" value="${id }"/>
				    <div class="row" style="padding: 10px">
						<div class="col-sm-2 col-md-2">
							<select id="planId" class="form-control select2" name="planId"
								style="float: left;" data-placeholder="套餐类型">
								     <option value=" ">全部套餐类型</option>
								  <c:forEach items="${planList }" var="pl" >
								     <option value="${pl.id }" >${pl.name}--${pl.flow }MB</option>
								  </c:forEach>
							</select>
							<input type="hidden" name="type" value="1" />
						</div>
					</div>
				</form:form>
				<p class="btn_table">
				    <button type="button"   class="btn  margin btn-success">创建套餐</button>
				    <button type="button"  class="btn  margin btn-primary disabled">更改套餐</button>
				    <button type="button"  class="btn  margin btn-danger disabled">删除套餐</button>、
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

     $(".select2").select2({ allowClear:false}).on("select2-selecting", function(e) {
		}).on("change", function(e) {
			loadTable();
		})
	var options={};
     var oTable;
     var selectData;
	options.sAjaxSource="${ctx}/pack/list";
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "ID", "bVisible":false, "sClass": "center","sWidth":"80","mDataProp": "id"},
		 { "sTitle": "套餐流量(MB)",  "sClass": "center","sWidth":"80","mDataProp": "flow"},
		 { "sTitle": "对外价格","sClass": "center" ,"sWidth":"100","mDataProp": "externalQuote"},
		 { "sTitle": "平台价格","sClass": "center" ,"sWidth":"100","mDataProp": "platQuote"},
       { "sTitle": "套餐类型",  "sClass": "center" ,"sWidth":"75", "mDataProp": "packageType"},
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
	
	$('.btn_table').find('button').on('click', function () {
		  if($(this).hasClass('disabled')){
			  return;
		  }
      if($(this).hasClass('btn-success')){
      		window.location.href='${ctx}/pack/add';
      	}else if($(this).hasClass('btn-primary')){
        	window.location.href='${ctx}/pack/add?id='+selectData.id;
        }else if($(this).hasClass('btn-danger')){
      	delPackage();
      }
	});
	
	function delPackage(){
		bootbox.confirm('是否确认删除'+selectData.flow+"MB流量套餐", function(result) {
			  if(result){
				  var opt={};
				  opt.url="${ctx}/pack/del?id="+selectData.id;
				  opt.success=function(data){
						 toastr.success('操作成功');
						  oTable.fnDraw();
						  $(".btn_table").find('.btn').not(".btn-success").addClass("disabled");
					  };
					 SP.ajax($("#searchform"),opt);
			  }
	      });
	}
</script>