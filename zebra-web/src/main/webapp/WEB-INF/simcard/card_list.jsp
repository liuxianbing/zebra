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
		      	  卡片管理
		        <small>物联卡列表</small>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/simcard/list" method="post" id="searchform">
					<div class="row" style="padding: 10px">
						<div class="col-sm-2 col-md-2"
							style="padding-left: 8px; padding-right: 0px">
							<input type="text" id="search_name" name="keyword"
								class="form-control" placeholder="ICCID或电话号码或备注">
						</div>
						<div class="col-sm-2 col-md-2">
							<select id="state" class="form-control select2" name="type"
								style="float: left;" data-placeholder="卡类型">
								<option value=" " selected="selected">全部卡</option>
								  <option value="1" >单卡</option>
								  <option value="2" >流量卡</option>
							</select>
						</div>
							<div class="col-sm-2 col-md-2">
							<select id="netType" class="form-control select2" name="netType"
								style="float: left;" data-placeholder="网络状态">
								<option value=" " selected="selected">全部网络状态</option>
								  <option value="1" >开启</option>
								  <option value="0" >关闭</option>
							</select>
						</div>
						<div class="col-sm-2 col-md-2">
							<select id="objType" class="form-control select2" name="objType"
								style="float: left;" data-placeholder="设备状态">
								<option value=" " selected="selected">全部设备状态</option>
								  <option value="1" >已停卡</option>
								  <option value="2" >已激活</option>
								  <option value="0" >库存</option>
							</select>
						</div>
						<div class="col-sm-2 col-md-2">
							<select id="uid" class="form-control select2" name="uid"
								style="float: left;" data-placeholder="关联客户">
								<option value=" " selected="selected">全部关联客户</option>
								  <option value="0" >未关联</option>
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.account }-${ul.userName }</option>
								  </c:forEach>
							</select>
						</div>
				<div class="col-sm-2 col-md-2">
					 <button type="button" id="searchButton" onclick="loadTable()" class="btn btn-primary">查询</button>
                </div>
					</div>
				</form:form>
				<p class="btn_table">
				    <button type="button"   class="btn  margin btn-success">划拨</button>
				    <button type="button"  class="btn  margin btn-primary disabled">更改用户</button>
				    <button type="button" class="btn  margin btn-info disabled">用户详细</button>
				    <button type="button"  class="btn  margin btn-danger disabled">删除用户</button>、
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
	options.sAjaxSource="${ctx}/simcard/list";
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "ID", "bVisible":false, "sClass": "center","sWidth":"80","mDataProp": "id"},
		 { "sTitle": "ICCID",  "sClass": "center","sWidth":"80","mDataProp": "iccid"},
		 { "sTitle": "电话号码","sClass": "center" ,"sWidth":"100","mDataProp": "phone"},
		 { "sTitle": "卡类型","sClass": "center" ,"sWidth":"100","mDataProp": "type"},
       { "sTitle": "网络状态",  "sClass": "center" ,"sWidth":"75", "mDataProp": "netType"},
	   { "sTitle": "设备状态",  "sClass": "center","sWidth":"80","mDataProp": "objType"},
	   { "sTitle": "本月用量(MB)",  "sClass": "center","sWidth":"80","mDataProp": "usedFlow"},
	   { "sTitle": "套餐总量(MB)",  "sClass": "center","sWidth":"80","mDataProp": "flow"},
	   { "sTitle": "套餐已用(MB)",  "sClass": "center","sWidth":"80","mDataProp": "packageUsed"},
	   { "sTitle": "套餐剩余(MB)",  "sClass": "center","sWidth":"80","mDataProp": "packageLeft"},
	   { "sTitle": "备注", "sClass": "center" ,"sWidth":"90","mDataProp": "remark"}
		];
	function loadTable(){
		if(oTable){
			 $("#table_list").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#table_list"),options,$("#searchform"));
	}
	
	  $('.btn_table').on('click', 'button', function () {
		        if($(this).hasClass('btn-success')){
		        	if(selectData){
		        		window.location.href='${ctx}/user/add?id='+selectData.id;
		        	}else{
		        		window.location.href='${ctx}/user/add';
		        	}
		        }else if($(this).hasClass('btn-primary')){
		        	window.location.href='${ctx}/user/add?id='+selectData.id;
		        }
			});
	
	 
	 
	loadTable();
  //  $('#example1').dataTable({"bServerSide":false,"bDestroy":true,"aaSorting":[[ 0, "asc" ]]});
</script>