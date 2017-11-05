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

.ordertitle {
	background-color: #dce5ea;
	height: 40px;
	text-align: left;
	padding: 7px;
	border: 1px solid #a4bfce
}
-->
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header)-->
			<section class="content-header">
				<h1>购卡记录</h1>
			</section>
			<!-- Main content -->
			<section class="content" style="padding: 20px">
				<div class="row otitle" style="margin-bottom: 15px">
					<div class="col-md-3">订单类型</div>
					<div class="col-md-2">套餐价格</div>
					<div class="col-md-1">订购周期</div>
					<div class="col-md-1">单张卡费</div>
					<div class="col-md-1">卡片数量</div>
					<div class="col-md-2">订单价格</div>
					<div class="col-md-1">订单状态</div>
					<div class="col-md-1">操作</div>
				</div>
				<c:forEach items="${page.records }" var="pr">
				<div class="row ordertitle">${pr.createTime }
					订单号:${pr.orderCode }</div>
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
								       <li class="paginate_button"> <a href="#" id="btn_${i }"  tabindex="${i }">${i }</a></li>
								   </c:forEach>
								</c:if>
								<c:if test="${page.current>6 }">
								   <c:forEach var="i" begin="${page.current-5 }" end="${page.current}" step="1">  
								       <li class="paginate_button"> <a href="#" id="btn_${i }"  tabindex="${i }">${i }</a></li>
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
		if($(this).hasClass('previous')){
			window.location.href="${ctx}/cart/record?iDisplayStart=${(page.current-1)*10}"
		}else if($(this).hasClass('next')){
			window.location.href="${ctx}/cart/record?iDisplayStart=${page.current*10}"
		}else{
			
		}
	})
</script>