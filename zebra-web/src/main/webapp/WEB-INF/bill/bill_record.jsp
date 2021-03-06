<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" type="text/css" href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.css" />
	
<style>
<!--
.otitle {
	background-color: #dce5ea;
	height: 40px;
	text-align: center
}

.sing{
line-height: 45px;
text-align: center;
border-bottom:1px solid #a4bfce;
border-left:1px solid #a4bfce;
border-right:1px solid #a4bfce
}

.ordertitle {
	background-color: #dce5ea;
	height: 40px;
	text-align: left;
	padding: 7px;
	border: 1px solid #a4bfce;
	margin-top:10px
}
.otitle div{
padding-top:10px
}
.otherdivr{
margin:0:auto;
padding-top:5px;border-right:1px solid #a4bfce;
text-align:center;height:100%;border-bottom:1px solid #a4bfce
}
-->
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini" style="height: 100%;">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header)-->
			<section class="content-header">
				<h1>扣费记录
				 <a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a>
				</h1>
			</section>
			<!-- Main content -->
			<section class="content" style="padding: 20px">
			 <c:if test="${CURRENT_USER.role==0 }">
			     <div class="row">
			        <div class="col-md-3">
			        	 <select id="uid" class="form-control select2 " name="uid"
								style="float: left;" data-placeholder="选择客户">
								  <option value=" ">全部</option>
									  <c:forEach items="${userList }" var="ul" >
									 <option value="${ul.id }" <c:if test="${ul.id==uid }">
									   selected </c:if>>${ul.userName }-${ul.companyName }</option>
								  </c:forEach>
								</select>
			        </div>
			     </div>
			     </c:if>
			     <c:if test="${CURRENT_USER.role>=1 }">
			       <input type="hidden" name="uid" id="uid" value="${CURRENT_USER.id }" />
			     </c:if>
				<div class="row otitle" style="margin-bottom: 15px;margin-top:15px">
					<div class="col-md-2">扣费类型</div>
					<div class="col-md-3">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ICCID</div>
					<div class="col-md-3">卡片流量</div>
					<div class="col-md-1">小计流量</div>
					<div class="col-md-1">扣费金额</div>
					<div class="col-md-2">操作</div>
				</div>
				<c:forEach items="${page.records }" var="pr">
				<div class="row ordertitle">${pr.month }月  总流量:${pr.flow }MB 总费用:${pr.cost }元
				 客户名称:${pr.userInfo }</div>
				<div class="row"  style="height:${pr.len*46}px">
					<div class="col-md-8 ">
					  <c:forEach items="${pr.contents}" var="pc"> 
					     <div class="row sing">
					     <div class="col-md-3" >${pc.typeStr }</div>
					      <c:if test="${pc.type==1 }">
					          <div class="col-md-9">
					            <c:forEach items="${pc.shareCards }" var="sc">
					            <div class="col-md-6" style="width:51%" >${sc.key }</div>
					              <div class="col-md-6"  style="width:49%" >${sc.value }MB</div>
					            </c:forEach>
					          </div>
					      </c:if>
					       <c:if test="${pc.type==0 }">
					        <div class="col-md-5">${pc.iccid }</div>
							<div class="col-md-4" style="width:28%" >${pc.flow }MB</div>
					       </c:if>
						
						</div>
					  </c:forEach>
					</div>
					<div class="col-md-1 otherdivr" >${pr.flow }MB</div>
					<div class="col-md-1 otherdivr" >${pr.cost }元</div>
					<div class="col-md-2 otherdivr" > 操作 </div>
					</div>
					
				</c:forEach>
				

				<div class="row">
					<div class="col-sm-4">
						<div class="dataTables_info" id="card_list_info" role="status" style="margin-top:25px"
							aria-live="polite">当前数据为从第  ${((page.current-1)*10)+1 } 到第   ${page.current*10 } 条数据；总共有 ${page.total } 条记录</div>
					</div>
					<div class="col-sm-5">
						<div class="dataTables_paginate paging_simple_numbers"
							id="card_list_paginate">
							<ul class="pagination">
								<li class="paginate_button previous" id="order_list_previous"><a
									href="#"  data-dt-idx="0" tabindex="0">前页</a></li>
								<c:if test="${page.current<=6 }">
								   <c:forEach var="i" begin="1" end="${page.pages}" step="1">  
								       <li class="paginate_button" tabindex="${i }"> <a href="#" id="btn_${i }"  >${i }</a></li>
								   </c:forEach>
								</c:if>
								<c:if test="${page.current>6 }">
								   <c:forEach var="i" begin="${page.current-5 }" end="${page.current}" step="1">  
								       <li class="paginate_button" tabindex="${i }"> <a href="#" id="btn_${i }"  >${i }</a></li>
								   </c:forEach>
								</c:if>
								<li class="paginate_button next" id="order_list_next"><a
									href="#"  >后页</a></li>
							</ul>
						</div>
					</div>
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
</body>
</html>
<script>
$(".select2").select2({ allowClear:false}).on("change", function(e) {
	window.location.href="${ctx}/bill/record?uid="+$("#uid").val()
})

$("#btn_${page.current}").parent().addClass('active')
	if('${page.current}'==1){
		$("#order_list_previous").addClass('disabled')
	}
	if('${page.current}'=='${page.pages}'){
		$("#order_list_next").addClass('disabled')
	}
	$(".pagination").on('click','li',function(){
		if($(this).hasClass('disabled')){
			return;  
		}
		var ind=parseInt($(this).attr("tabindex"))*10-10;
		
		if($(this).hasClass('previous')){
			window.location.href="${ctx}/bill/record?iDisplayStart=${(page.current-2)*10}&uid="+$("#uid").val()
		}else if($(this).hasClass('next')){
			window.location.href="${ctx}/bill/record?iDisplayStart=${page.current*10}&uid="+$("#uid").val()
		}else{
			window.location.href="${ctx}/bill/record?iDisplayStart="+ind+"&uid="+$("#uid").val()
		}
	})
</script>