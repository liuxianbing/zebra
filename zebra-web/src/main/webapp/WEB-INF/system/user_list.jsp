<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.css" />
	<link
	href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
	<style>
	.bootbox-alert, .bootbox-confirm {
	background: none;
	border: none;
	box-shadow: none;
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
		    	  <c:if test="${CURRENT_USER.role==0 }">
		      <h1>
		      	  客户管理
		        <small>客户列表</small>
		        <a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a>
		      </h1>
		      </c:if>
		        <c:if test="${CURRENT_USER.role==1 }">
		      <h1>
		      	  用户管理
		        <small>用户列表</small>
		        <a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a>
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
						  <input type="hidden" name="status" value="1" />
						  <c:if test="${CURRENT_USER.role==1 }">
						  <input type="hidden" name="cid" value="${CURRENT_USER.cid }" />
						  </c:if>
						  <c:if test="${CURRENT_USER.role==0 }">
						<div class="col-sm-2 col-md-2">
							<select id="auth" class="form-control select2" name="auth"
								style="float: left;" data-placeholder="企业认证状态">
								<option value=" " selected="selected">全部</option>
								<option value="1">已认证</option>
								<option value="0">未认证</option>
							</select>
						</div>
						 </c:if>
				<div class="col-sm-2 col-md-2">
					 <button type="button" id="searchButton" onclick="loadTable()" class="btn btn-primary">查询</button>
                </div>
					</div>
				</form:form>
				<p class="btn_table">
				 <c:if test="${CURRENT_USER.role==0 || CURRENT_USER.auth==1  }">
				    <button type="button"   class="btn  margin btn-success">创建用户</button>
				    </c:if>
				    <button type="button"  class="btn  margin btn-primary disabled">更改用户</button>
				    <button type="button"  class="btn  margin btn-warning disabled">重置密码</button>
				    <button type="button"  class="btn  margin btn-danger disabled">删除用户</button>
				      <c:if test="${CURRENT_USER.role==0 }">
				    <button type="button" class="btn  margin btn-info disabled">用户认证</button>
				    </c:if>
				<p>
				<table id="user_list" width="100%" class="table table-bordered table-hover">
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
		
		<script
		src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>
</body>
</html>
<script>
$("#selectAll").click(function(e) {
	$("#user_list").find('tbody').find('tr').trigger('click')
})
     $(".select2").select2({ allowClear:false}).on("select2-selecting", function(e) {
		}).on("change", function(e) {
			loadTable();
		})
		
	var dataTableObj;
	var options={};
     var oTable;
     var selectData;
	options.sAjaxSource="${ctx}/user/list";
	options.aaSorting=[[ 1, "desc" ]];
	options.aoColumns=[
		{
			"sTitle" : "选择",
			"asSorting" : [],
			"sClass" : "center",
			"sWidth" : "50",
			"mDataProp" : "id",
			"mRender" : function(data, type, full) {
				return '<input type="checkbox" name="sks" value='+data+' />';
			}
		},
		 { "sTitle": "ID",  "sClass": "center","sWidth":"80","mDataProp": "id"},
		 { "sTitle": "创建时间",  "sClass": "center","sWidth":"80","mDataProp": "createTime"},
		 { "sTitle": "联系人","sClass": "center" ,"sWidth":"100","mDataProp": "userName"},
       { "sTitle": "登录手机号",  "sClass": "center" ,"sWidth":"135", "mDataProp": "phone"},
	   { "sTitle": "邮箱",  "sClass": "center","sWidth":"80","mDataProp": "email"},
	   { "sTitle": "用户角色", "sClass": "center" ,"sWidth":"90","mDataProp": "type","mRender": function ( data, type, full ) {
		   if(data==-1)return '超级管理员';
		   if(data==1) return '审核员';
		   if(data==2) return '划拨员';
		   if(data==3) return '发货员';
		   if(full.role==1) return '企业管理员';
		    return '普通用户';
	     }
		}
		];
	
	var comname={ "sTitle": "企业名称",  "sClass": "center","sWidth":"80","mDataProp": "companyName"}
	
	var ope={ "sTitle": "操作", "sClass": "center" ,"sWidth":"90","mDataProp": "status","mRender": function ( data, type, full ) {
		   return '<a href="${ctx}/user/packlist?id='+full.id+'">查看套餐</a>';
	   }
}
	var auth= { "sTitle": "是否认证",  "sClass": "center","sWidth":"80","mDataProp": "authStr"}
	  <c:if test="${CURRENT_USER.role==0 }">
	  options.aoColumns.push(comname)
	  options.aoColumns.push(ope)
	   options.aoColumns.push(auth)
	  </c:if>
	function loadTable(){
		if(oTable){
			 $("#user_list").empty();
			 oTable.fnDestroy();
		}
		oTable=SP.loadTableInfo($("#user_list"),options,$("#searchform"));
		dataTableObj = oTable.api();
	}
	
	

	$('#user_list').on('click', ' tbody tr', function() {
		$(this).toggleClass('row_selected');
		if ($(this).hasClass('row_selected')) {
			$(this).find("input[name='sks']").attr("checked", "true");
		} else {
			$(this).find("input[name='sks']").attr("checked", false);
		}
		if (dataTableObj.rows('.row_selected').data().length ==1) {
			$(".btn_table").find('button').removeClass('disabled')
			selectData=dataTableObj.rows('.row_selected').data();
		}else if(dataTableObj.rows('.row_selected').data().length > 1){
			$(".btn_table").find('button').removeClass('disabled')
			$(".btn_table").find('.btn-primary').addClass('disabled')
			$(".btn_table").find('.btn-info').addClass('disabled')
		}else{
			$(".btn_table").find('button').not(".btn-success").addClass('disabled')
		}
	});
	
	
	 $('.btn_table').find('button').on('click', function () {
				 if($(this).hasClass('disabled')){
					  return;
				  }
		        if($(this).hasClass('btn-success')){
		        		window.location.href='${ctx}/user/add';
		        }else if($(this).hasClass('btn-primary')){
		        	selectData=dataTableObj.rows('.row_selected').data()[0];
		        	window.location.href='${ctx}/user/add?id='+selectData.id;
		        } else if($(this).hasClass('btn-info')){
		        	selectData=dataTableObj.rows('.row_selected').data()[0];
		        	window.location.href='${ctx}/user/auth?uid='+selectData.id+"&cid="+selectData.cid;
		        } else if($(this).hasClass('btn-warning')){
		        	resetUser();
		        } else if($(this).hasClass('btn-danger')){
		        	delUser();
		        }
			});
	 
	 options.success=function(e){
		 toastr.success('操作成功');
		 oTable.fnDraw();
	 }
	 
	 function resetUser(){
			bootbox.confirm('是否确认重置密码？', function(result) {
				if(result){
					var ids = "";
					$.each(dataTableObj.rows('.row_selected').data(), function(i, n) {
						ids = ids + n.id + ",";
					});
					options.url="${ctx}/user/reset?uid="+ids;
					SP.ajax($("#searchform"),options);
				}
			});
		}
	 
		function delUser(){
			bootbox.confirm('是否确认删除用户？', function(result) {
				if(result){
					var ids = "";
					$.each(dataTableObj.rows('.row_selected').data(), function(i, n) {
						ids = ids + n.id + ",";
					});
					options.url="${ctx}/user/del?uid="+ids;
					SP.ajax($("#searchform"),options);
				}
			});
		}
	 
	 
	loadTable();
</script>