<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.css" />
	
	<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
 <link href="${ctx }/assets/plugins/dropzone/css/dropzone.css" rel="stylesheet"/>
 <link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
	
	 <link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
	
	
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
<style>
#validationform .row{
margin-top:15px
}
.bootbox-alert,.bootbox-confirm{
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
						<div class="col-sm-1 col-md-1">
							<select id="state" class="form-control select2" name="type"
								style="float: left;" data-placeholder="卡类型">
								<option value=" " selected="selected">全部卡</option>
								  <option value="1" >单卡</option>
								  <option value="2" >流量卡</option>
							</select>
						</div>
							<div class="col-sm-1 col-md-1">
							<select id="netType" class="form-control select2" name="netType"
								style="float: left;" data-placeholder="网络状态">
								<option value=" " selected="selected">全部网络</option>
								  <option value="1" >开启</option>
								  <option value="0" >关闭</option>
							</select>
						</div>
						<div class="col-sm-1 col-md-1">
							<select id="objType" class="form-control select2" name="objType"
								style="float: left;" data-placeholder="设备状态">
								<option value=" " selected="selected">全部设备</option>
								<c:forEach items="${deviceStatus }" var="ds">
									<option value="${ds.status }" >${ds.simStatus }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2 col-md-2">
							<select id="uid" class="form-control select2" name="uid"
								style="float: left;" data-placeholder="关联客户">
								<option value=" " selected="selected">全部关联客户</option>
								  <option value="0" >未关联</option>
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.phone }-${ul.userName }</option>
								  </c:forEach>
							</select>
						</div>
				<div class="col-sm-2 col-md-2">
					 <button type="button" id="searchButton" onclick="loadTable()" class="btn btn-primary">查询</button>
                </div>
					</div>
				</form:form>
				<div class="btn_table">
				    <c:if test="${CURRENT_USER.role==0 }">
				    <button type="button" id="huabo"  class="btn  margin btn-success disabled">划拨</button>
				    <button type="button" id="rmBtn"  class="btn  margin btn-primary disabled">备注</button>
				  	<button type="button"  class="btn  margin btn-default disabled">卡重置</button>
				  	 <button type="button"  class="btn  margin btn-info disabled">打开网络</button>
				    <button type="button"   class="btn  margin btn-danger disabled">关闭网络</button>
				  </c:if>
				   <c:if test="${CURRENT_USER.role==1 }">
				   <button type="button"  class="btn  margin btn-waring disabled">卡分配</button>
				   </c:if>
				   
				</div>
				<table id="card_list" class="table table-bordered table-hover">
				</table>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>
	
	<div id="distDiv" class="modal"  data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">卡片分配</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
				<form:form onsubmit="return false" action="${ctx}/simcard/dist" method="post" 
				class="validationform form-horizontal" id="validationform3">
						<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">关联客户</label>
						  </div>
						   <div class="col-md-9">
						     <select id="uidInnerss" class="form-control select2" name="uidss"
								style="float: left;" data-placeholder="关联客户">
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.phone }-${ul.userName }</option>
								  </c:forEach>
							</select>
						  </div>
					</div>
				</form:form>
	</div>
	<div class="modal-footer">
	<button type="button" id="submitDist"   class="btn btn-primary">提交</button>
			
		<button type="button" data-dismiss="modal" id="closeupload3"
			class="btn btn-default">关闭</button>
	</div>
</div>
	
	<div id="remarkDiv" class="modal"  data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">添加备注</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
				<form:form onsubmit="return false" action="${ctx}/simcard/remark" method="post" 
				class="validationform form-horizontal" id="validationform2">
					<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">备注信息</label>
						  </div>
						   <div class="col-md-9">
						      <textarea rows="3" cols="10" style="height:80px" 
								   class="form-control" value=""   name="remark" id="remark">
							     </textarea>
						  </div>
						  <input type="hidden" name="ids" id="remarkIds" />
					</div>
				</form:form>
	</div>
	<div class="modal-footer">
	<button type="button" id="submitRemark"   class="btn btn-primary">提交</button>
			
		<button type="button" data-dismiss="modal" id="closeupload2"
			class="btn btn-default">关闭</button>
	</div>
</div>
	
	
	<div id="business" class="modal" 
	data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">划拨卡片</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
				<form:form onsubmit="return false" action="${ctx}/simcard/alloc" method="post" 
				class="validationform form-horizontal" id="validationform">
					<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">ICCID</label>
						  	   <input type="hidden" name="ids" id="ids" />
						  </div>
						   <div class="col-md-9">
						      <textarea rows="3" cols="10" style="height:80px" 
								   class="form-control" readonly="readonly"  name="iccid" id="iccid">
							     </textarea>
							     <div style="color:#999" id="tips"></div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">关联客户</label>
						  </div>
						   <div class="col-md-9">
						     <select id="uidInner" onchange="initPackSelect(this.value)" class="form-control select2" name="uid"
								style="float: left;" data-placeholder="关联客户">
								  <c:forEach items="${userList }" var="ul" >
								     <option value="${ul.id }" >${ul.phone }-${ul.userName }</option>
								  </c:forEach>
							</select>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">关联套餐</label>
						  </div>
						   <div class="col-md-9">
						     <select id="packageId" class="form-control select2  validate[required]" name="packageId"
								style="float: left;" data-placeholder="关联套餐">
							</select>
						  </div>
					</div>
					<!-- 
					<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">套餐期限</label>
						  </div>
						   <div class="col-md-9">
						     <select id="term" class="form-control select2" name="term"
								style="float: left;" data-placeholder="套餐期限">
								  <c:forEach items="${termList }" var="tm" >
								     <option value="${tm }" >${tm }月</option>
								  </c:forEach>
							</select>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-3">
						  	  <label for="inputEmail3" class="control-label">套餐价格</label>
						  </div>
						   <div class="col-md-3">
							  <input type="text"  class="form-control  validate[required,custom[number]]" 
			                    name="externalQuote" id="externalQuote"  placeholder="自定义套餐价格">
						  </div>
					</div>
					 -->
					
				</form:form>
	</div>
	<div class="modal-footer">
	<button type="button" id="submitIccid"   class="btn btn-primary">提交</button>
			
		<button type="button" data-dismiss="modal" id="closeupload1"
			class="btn btn-default">关闭</button>
	</div>
</div>


<button class="btn blue"  id="hb" data-target="#business" data-toggle="modal"   type='button' style="float:right;display:none">rr</button>
<button class="btn blue"  id="rm" data-target="#remarkDiv" data-toggle="modal"   type='button' style="float:right;display:none">rr</button>
<button class="btn blue"  id="dis" data-target="#distDiv" data-toggle="modal"   type='button' style="float:right;display:none">rr</button>

<div style="display:none" id="allPlan">
    <c:forEach items="${planList }" var="pl" >
            <a href="${pl.id }" class="${pl.type }" >${pl.name}--${pl.flow }MB--￥${pl.cost }</a>
	</c:forEach>
</div>

<form:form onsubmit="return false" action="" method="post" id="oper">
				  <input type='hidden' name="ids" id="myids" />
				</form:form>

	<jsp:include page="../fragments/footer.jsp" />
	<script type="text/javascript"
		src="${ctx }/assets/plugins/datatables.net/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${ctx }/assets/plugins/datatables.net-bs/js/dataTables.bootstrap2.js"></script>
		<script src="${ctx}/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js" type="text/javascript"></script>
		
		
	<script src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
		
		
</body>
</html>
<script>
var dataTableObj;
var ids="";
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
    $("#searchform").find(".select2").select2({ allowClear:false}).on("select2-selecting", function(e) {
		}).on("change", function(e) {
			loadTable();
		})
		
	var options={};
    
    options.success=function(data){
    	 $(".modal").find(".btn-default").trigger('click');
    	  if(data.result=='success'){
    		  toastr.success('操作成功');
    	  }else{
    		  toastr.error(data.msg);
    		  bootbox.alert(data.msg); 
    	  }
    	 oTable.fnDraw();
		  $(".btn_table").find('button').addClass('disabled')
	  };
	  
     var oTable;
     var selectData;
	options.sAjaxSource="${ctx}/simcard/list";
	options.aaSorting=[[ 0, "asc" ]];
	options.aoColumns=[
		 { "sTitle": "ID", "bVisible":false, "sClass": "center","sWidth":"80","mDataProp": "id"},
		 { "sTitle": "ICCID",  "sClass": "center","sWidth":"150","mDataProp": "iccid","mRender": function ( data, type, full ) {
			 return '<a href="${ctx}/simcard/detail?id='+full.id+'">'+data+'</a>';
			 }
		 },
		 { "sTitle": "电话号码","sClass": "center" ,"sWidth":"100","mDataProp": "phone"},
		 { "sTitle": "运营商",  "sClass": "center","sWidth":"90","mDataProp": "operator","mRender": function ( data, type, full ) {
			 return '中国联通';
			 }
		 },
		 { "sTitle": "卡类型","sClass": "center" ,"sWidth":"100","mDataProp": "typeStr"},
		 { "sTitle": "关联用户", "asSorting": [ ],"sClass": "center" ,"sWidth":"100","mDataProp": "userInfo"},
       { "sTitle": "网络状态",  "sClass": "center" ,"sWidth":"90", "mDataProp": "netTypeStr"},
	   { "sTitle": "设备状态",  "sClass": "center","sWidth":"80","mDataProp": "objTypeStr"},
	   { "sTitle": "设备名称",  "sClass": "center","sWidth":"80","mDataProp": "terminalId"},
	   { "sTitle": "本月用量(MB)",  "sClass": "center","sWidth":"95","mDataProp": "usedFlow"},
	   { "sTitle": "套餐价格",  "sClass": "center","sWidth":"80","mDataProp": "externalQuote"},
	   { "sTitle": "套餐总量(MB)",  "sClass": "center","sWidth":"95","mDataProp": "flow"},
	   { "sTitle": "套餐已用(MB)",  "sClass": "center","sWidth":"95","mDataProp": "packageUsed"},
	   { "sTitle": "套餐剩余(MB)",  "sClass": "center","sWidth":"95","mDataProp": "packageLeft"},
	   { "sTitle": "最近同步时间",  "sClass": "center","sWidth":"100","mDataProp": "lastSyncTime"},
	   { "sTitle": "备注", "sClass": "center" ,"sWidth":"90","mDataProp": "remark"}
		];
	function loadTable(){
		if(oTable){
			 $("#card_list").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#card_list"),options,$("#searchform"));
		dataTableObj=oTable.api();
		//oTable.$('tr:odd').css('backgroundColor', 'blue');
	}
	
	$('#card_list').on('click', ' tbody tr', function () {	
        $(this).toggleClass('row_selected');
        if(dataTableObj.rows('.row_selected').data().length>0){
        	$(".btn_table").find('button').removeClass('disabled')
        }else{
        	$(".btn_table").find('button').addClass('disabled')
        }
    } );
	
	  $('.btn_table').on('click', 'button', function () {
				  if($(this).hasClass('disabled')){
					  return;
				  }
		        if($(this).hasClass('btn-success')){
		        	getSelectedData();
		        }else if($(this).hasClass('btn-primary')){
		        	remarkCards();
		        }else if($(this).hasClass('btn-info')){
		        	openNet();
		        }else if($(this).hasClass('btn-danger')){
		        	closeNet();
		        }else if($(this).hasClass('btn-default')){
		        	getIDS();
		        	resetCards();
		        }else if($(this).hasClass('btn-waring')){
		        	getIDS();
		        	 $("#dis").trigger('click')
		        }
			});
	
	 
	loadTable();
	var suc=0;
	var netType=-1;
	 function getSelectedData(){
		 var iccids="";
		 var ids="";
		 suc=0;
		 var cardType=-1;
		 $.each(dataTableObj.rows('.row_selected').data(),function(i,n){
			 if(cardType==-1){
				 cardType=n.type;
			 }else if(cardType!=n.type){
				 cardType=-2;
			 }
			 if(n.netType==0 || n.objType==2){
			 }else{
				 iccids=iccids+n.iccid+",";
				 ids=ids+n.id+",";
				 suc++;
			 }
		 }
		 );
		 if(cardType<0){
			  bootbox.alert("请选择同一类型的物联网卡!"); 
			   return;
		 }
		// initPlanSelect(cardType)
		 $('#iccid').val(iccids.substring(0,iccids.length-1))
		  $('#ids').val(ids.substring(0,ids.length-1))
		 var tips="当前可分配的ICCID"+suc+"个,无法分配的ICCID"+(dataTableObj.rows('.row_selected').data().length-suc)+"个";
		 $("#tips").html(tips);
		 $("#hb").trigger('click')
	 }
	 $("#submitIccid").click(function(){
		 if(suc==0){
			   bootbox.alert("当前没有可分配的物联网卡"); 
			   return;
		 }
		 $('body').modalmanager('loading');
		 SP.ajax($("#validationform"),options);
	 })
	  $("#submitRemark").click(function(){
		  $('body').modalmanager('loading');
		 SP.ajax($("#validationform2"),options);
	 })
	 
	 function remarkCards(){
		 var ids="";
		 $.each(dataTableObj.rows('.row_selected').data(),function(i,n){
			 ids=ids+n.id+",";
		 });
		 $('#remarkIds').val(ids.substring(0,ids.length-1))
		  $("#rm").trigger('click')
	 }
	 //初始化资费计划选择*
	 /*
	 function initPlanSelect(type){
		 $("#planId").empty();
		 $("#allPlan").find("a."+type).each(function (index,domEle){
				$("#planId").append("<option value="+ $(this).attr('href') + ">"+ $(this).text() +"</option>");
		 });
	 }*/
	 
	 function openNet(){
		 if(!closeOrOpenCard()){
			  return ;
		  }
		 bootbox.confirm('是否确认打开网络？', function(result) {
			  if(result){
		 $('body').modalmanager('loading');
			$("#oper").attr('action',"${ctx}/simcard/open");
			SP.ajax($("#oper"),options);
			  }
			});
	}

	function closeNet(){
		if(!closeOrOpenCard()){
			  return ;
		  }
		bootbox.confirm('是否确认关闭网络？', function(result) {
			if(result){
		    $('body').modalmanager('loading');
			$("#oper").attr('action',"${ctx}/simcard/close");
			SP.ajax($("#oper"),options);
			}
		});
	}
	
	$("#submitDist").click(function(){
		options.url="${ctx}/simcard/dist?ids="+ids+"&uid="+$("#uidInnerss").val();
			SP.ajax($("#validationform3"),options);
	})
	
	function resetCards(){
		bootbox.confirm('是否确认重置卡片？', function(result) {
			if(result){
				options.url="${ctx}/simcard/dist?ids="+ids+"&uid=0";
				SP.ajax($("#validationform3"),options);
			}
		});
	}
	
	 function getIDS(){
		 var iccids="";
		  ids="";
		 suc=0;
		 var cardType=-1;
		 $.each(dataTableObj.rows('.row_selected').data(),function(i,n){
			 if(cardType==-1){
				 cardType=n.type;
			 }else if(cardType!=n.type){
				 cardType=-2;
			 }
			 if(n.netType==0 || n.objType==2){
			 }else{
				 iccids=iccids+n.iccid+",";
				 ids=ids+n.id+",";
				 suc++;
			 }
		 } );
		
	 }
	
	function closeOrOpenCard(){
		 netType=-1;
		 var iccids="";
		 $.each(dataTableObj.rows('.row_selected').data(),function(i,n){
			 if(netType==-1 && netType!=-2){
				 netType=n.netType;
			 }else if(netType!=n.netType){
				 netType=-2;
			 }
				 iccids=iccids+n.id+",";
		 }
		 );
		 if(netType==-2){
			  bootbox.alert("请选择同一网络类型的物联网卡!"); 
			   return false;
		 }
		 $('#myids').val(iccids.substring(0,iccids.length-1))
		 return true;
	}
	
	
	function initPackSelect(uid){
		  var options={}
		  options.url="${ctx}/pack/selfPacks?uid="+uid
		  $("#packageId").empty();
		  options.success = function(e) {
			  $.each(e, function(key, value) {
					$("#packageId").append("<option value="+ value.id + ">" + value.name+"--"+value.flow+ "MB</option>");
				});
		  }
		  SP.ajax($("#validationform"), options, false);
		  }
	
	$("#uid").change(function(){
		initPackSelect($(this).val());
		});
	<c:if test="${CURRENT_USER.role==0 }">
	initPackSelect($("#uidInner").val());
	</c:if>
</script>