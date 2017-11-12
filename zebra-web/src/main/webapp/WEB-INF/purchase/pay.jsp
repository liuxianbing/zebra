<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/spinner/bootstrap-spinner.css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
<
<style>
<!--
.ctitle {
	line-height: 60px;
}
 .finish-order__body {
    display: inline-block;
}
.finish-order .finish-order__body .finish-order__symbol {
    margin-top: 40px;
    color: #20a0ff;
}
 .finish-order__detail {
    text-align: left;
    margin-top: 20px;
    margin-bottom: 20px;
}
.finish-order__detail p:first-child {
    margin-bottom: 20px;
}
.margin-bottom10 {
    margin-bottom: 10px!important;
}
.f-size-l3 {
    font-size: 20px!important;
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
			<!-- Main content -->
			<section class="content">
				<form action="" id='form' method="post"></form>
				<div class="row" style="margin-top: 30px; text-align: center;">
					

					<div class="finish-order__body">
						<div class="finish-order__symbol">
							<i class="fa  fa-check"
						style="font-size: 130px; color: #008d4c; font-weight: 10"></i>
						</div>
						<div class="finish-order__detail">
							<p class="f-size-l3">订单提交成功，等待支付！</p>
							<p title="点击进入查看订单详情" class="margin-bottom10">
								订单编号：<a href="${ctx }/cart/detail?id=${order.id}" class="link">${order.orderCode }</a>
							</p>
							<p class="margin-bottom10">
								应付金额：<span class="f-size-l3 color-waring">￥${order.totalCost }元</span>
							</p>
							<p>
								账户余额：￥<span id="bance">${bance }</span>元
								<!---->
							</p>
							<p style="margin-top:20px;margin-left:50px">
							<c:if test="${bance>0.0 }">
							<button type="button" id="pay" class="btn btn-success">确认支付</button>
							</c:if>
							</p>
						</div>
					</div>
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
		<form action="" id='form' method="post"></form>
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine.js"
		type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js"
		type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/spinner/jquery.spinner.js"
		type="text/javascript"></script>
</body>
</html>
<script>
$("#pay").click(function(){
	if($(this).hasClass('disabled')){
		return;
	}
	var opt = {}
	opt.url="${ctx}/cart/pay?id=${order.id}"
	opt.success = function(e) {
		if(e.res=='false'){
			toastr.error('账户余额不足!');
		}else{
			toastr.success('操作成功');
		}
	}
	$("#pay").addClass('disabled')
	SP.ajax($("#form"), opt);
})
</script>