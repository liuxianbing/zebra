<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
<link
	href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"
	rel="stylesheet" type="text/css" />
<link
	href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css"
	rel="stylesheet" type="text/css" />
<style>
<!--
.bootbox-alert,.bootbox-confirm{
	background: none;
	border:none;
	box-shadow:none;
}
th {
    background-color: #cddae3;
}
.el-step__description {
    font-size: 12px;
    font-weight: 400;
    line-height: 14px;
}
.el-step .el-step__description {
    color: #999;
}
.el-badge__content, .el-message__group p, .el-steps.is-horizontal, .el-tabs__nav, .el-tag, .el-time-spinner, .el-tree-node, .el-upload-cover__title {
    white-space: nowrap;
}

.el-step.is-horizontal, .el-step.is-vertical .el-step__head, .el-step.is-vertical .el-step__main, .el-step__line {
    display: inline-block;
}

.el-step {
    margin-right: 1px;
    position: relative;
    vertical-align: top;
}

.el-steps {
    font-size: 0;
}

.el-step__head.is-text.is-finish {
    color: #fff;
    background-color: #20a0ff;
    border-color: #20a0ff;
}

.is-fail {
    color: #fff;
    background-color: #9c1111;
    border-color: #9c1111;
}

.el-step__head.is-text {
    font-size: 14px;
    border-width: 2px;
    border-style: solid;
}
.el-step__head.is-finish {
    color: #20a0ff;
    border-color: #20a0ff;
}

.el-step .el-step__head.is-text.is-wait {
    background-color: #bfcbd9;
}

.el-step__head.is-text.is-wait {
    color: #bfcbd9;
    background-color: #fff;
    border-color: #bfcbd9;
}
.el-step__head.is-text {
    font-size: 14px;
    border-width: 2px;
    border-style: solid;
}
.el-step__head.is-process, .el-step__head.is-wait {
    color: #bfcbd9;
    border-color: #bfcbd9;
}
.el-step__head {
    width: 28px;
    height: 28px;
    border-radius: 50%;
    background-color: transparent;
    line-height: 28px;
    font-size: 28px;
    vertical-align: top;
    transition: all .15s;
}
.el-step__head, .el-steps.is-horizontal.is-center {
    text-align: center;
}
 .el-step__main {
    text-align: center;
    transform: translateX(-50%);
    padding-left: 33px;
    white-space: normal;
    padding-right: 10px;
}
 .el-step .el-step__line.is-horizontal {
    top: 11px;
    right: 4px;
}
.el-step__line.is-horizontal {
    top: 15px;
    height: 2px;
    left: 28px;
    right: 0;
}
.el-step__line {
    position: absolute;
    border-color: inherit;
    background-color: #bfcbd9;
}
.el-step__line-inner {
    display: block;
    border-width: 1px;
    border-style: solid;
    border-color: inherit;
    transition: all .15s;
    box-sizing: border-box;
    width: 0;
    height: 0;
}
.el-step__title {
    font-size: 14px;
    line-height: 32px;
    display: inline-block;
}
 .el-step .el-step__title {
    color: #333;
    font-weight: 400;
}
.el-step.is-horizontal, .el-step.is-vertical .el-step__head, .el-step.is-vertical .el-step__main, .el-step__line {
    display: inline-block;
}
.nums{
color:#666
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
				<h1>
					购买正式卡 <small> 提交订单 </small>
				</h1>
			</section>
			<!-- Main content -->
			<section class="content" style="padding: 30px">
               <div style="margin:0 auto;text-align: center">
				<div class="el-steps is-horizontal">
					<div class="el-step is-horizontal"
						style="width: 200px; margin-right: 0px;" id="step1">
						<div class="el-step__head is-finish is-text">
							<div class="el-step__line is-horizontal"
								style="margin-right: 0px;">
								<i class="el-step__line-inner"
									style="transition-delay: 0ms; border-width: 1px; width: 50%;"></i>
							</div>
							<span class="el-step__icon"><div class="nums">1</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-finish">提交订单</div>
							<div class="el-step__description is-finish">
								<div class="step__description--content">
									<div>${ fn:substring( order.orderTime ,0,10)}</div>
									<div>${ fn:substring( order.orderTime,10,fn:length(order.orderTime)-2)}</div>
								</div>
							</div>
						</div>
					</div>
					<div class="el-step is-horizontal"
						style="width: 200px; margin-right: 0px;" id="step3">
						<div class="el-step__head is-process is-text">
							<div class="el-step__line is-horizontal"
								style="margin-right: 0px;">
								<i class="el-step__line-inner"
									style="transition-delay: -150ms; border-width: 0px; width: 0%;"></i>
							</div>
							<span class="el-step__icon"><div class="nums">2</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-process">支付成功</div>
							<div class="el-step__description is-process">
								<div class="step__description--content">
									<div>
									<c:if test="${order.type>=3 }">${ fn:substring( order.payTime ,0,10)}</c:if>
									</div>
									<div><c:if test="${order.type>=3 }">${ fn:substring( order.payTime,10,fn:length(order.payTime)-2)}</c:if></div>
								</div>
							</div>
						</div>
					</div>
					<div class="el-step is-horizontal"
						style="width: 200px; margin-right: 0px;" id="step5">
						<div class="el-step__head is-wait is-text">
							<div class="el-step__line is-horizontal"
								style="margin-right: 0px;">
								<i class="el-step__line-inner"
									style="transition-delay: -300ms; border-width: 0px; width: 0%;"></i>
							</div>
							<span class="el-step__icon"><div class="nums">3</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-wait">审核成功</div>
							<div class="el-step__description is-wait">
								<div class="step__description--content">
									<div><c:if test="${order.type>=5 }">${ fn:substring( order.auditTime ,0,10)}</c:if></div>
									<div><c:if test="${order.type>=5 }">${ fn:substring( order.auditTime,10,fn:length(order.auditTime)-2)}</c:if></div>
								</div>
							</div>
						</div>
					</div>
					<div class="el-step is-horizontal"
						style="width: 200px; margin-right: 0px;" id="step7">
						<div class="el-step__head is-wait is-text">
							<div class="el-step__line is-horizontal"
								style="margin-right: 0px;">
								<i class="el-step__line-inner"
									style="transition-delay: -450ms; border-width: 0px; width: 0%;"></i>
							</div>
							<span class="el-step__icon"><div class="nums">4</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-wait">包裹发出</div>
							<div class="el-step__description is-wait">
								<div class="step__description--content">
									<div><c:if test="${order.type>=7 }">${ fn:substring( order.outerTime ,0,10)}</c:if></div>
									<div><c:if test="${order.type>=7 }">${ fn:substring( order.outerTime,10,fn:length(order.outerTime)-2)}</c:if></div>
								</div>
							</div>
						</div>
					</div>
					<div class="el-step is-horizontal" style="width: 200px;" id="step8">
						<div class="el-step__head is-wait is-text">
							<span class="el-step__icon"><div class="nums">5</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-wait">完成</div>
							<div class="el-step__description is-wait">
								<div class="step__description--content">
									<div><c:if test="${order.type>=8 }">${ fn:substring( order.sucTime ,0 ,10)}</c:if></div>
									<div><c:if test="${order.type>=8 }">${ fn:substring( order.sucTime,10,fn:length(order.sucTime)-2)}</c:if></div>
								</div>
							</div>
						</div>
					</div>
				</div>
              </div>
              <div class="row" style="padding:20px;border-bottom:1px solid #ccc">
                 <p>订单号码： ${order.orderCode }</p>
                 <p> &nbsp;</p>
                 <p id="myst">订单状态： ${order.orderNextStatus }</p>
              </div>
               <div class="row" style="padding:20px;border-bottom:1px solid #ccc">
               <p>收货人信息： ${addr.area }&nbsp;&nbsp;&nbsp; ${addr.location }&nbsp;&nbsp;&nbsp; ${addr.userName }&nbsp;&nbsp;&nbsp; ${addr.phone }</p>
               </div>
               
               <p class="lead" style="margin-top:15px">商品清单</p>
				<table class="table table-bordered table-hover">
				   <thead>
				     <tr>
				      <th width="30%">商品详情</th>
				       <th width="10%">套餐价格</th>
				        <th  width="20%">订购周期</th>
				         <th  width="20%">卡片数量</th>
				         <th  width="20%">小计</th>
				         </tr>
				   </thead>
				   <tbody>
				   <c:forEach items="${ goodsList}" var="gl">
				   <tr>
				   <td>${gl.name }</td>
				   <td>${gl.price }</td>
				   <td>${gl.term }月</td>
				   <td class="num">${gl.num }</td>
				   <td class="sum"><fmt:formatNumber value="${gl.price*gl.num }" pattern="#0.00" /></td>
				    </tr>
				   </c:forEach>
				   </tbody>
				</table>
				 <div class="row" style="padding:20px;border-bottom:1px solid #ccc">
               <p>订单备注：${order.remark }</p>
                 <p> &nbsp;</p>
                  <p>快递信息：${order.deliverStr }</p>
               </div>
               
               <div class="row" style="padding-bottom:20px;margin-top:20px; border-bottom: 1px solid #ddd;margin-right:0px;margin-left:0px">
				  <div class="col-md-12 " style="text-align: right;">
				   <div class="col-md-10 color-666">总卡数</div>
				   <div class="col-md-2 f-size-l3" id='cardNum'></div>
				   <input type="hidden" name="cardCount" id="cardCount" value="" />
				  </div>
				  <div class="col-md-12 " style="text-align: right;">
				  <div class="col-md-10 color-666">总计卡费￥</div>
				   <div class="col-md-2 f-size-l3 " id='cardSpend'></div>
				  </div>
				  <div class="col-md-12 " style="text-align: right;">
				   <div class="col-md-10 color-666">运费￥</div>
				   <div class="col-md-2 f-size-l3 "  id='deliverSpend'>${order.deliverCost }</div>
				  </div>
				   <div class="col-md-12 " style="text-align: right;">
				    <div class="col-md-10 color-666">应付金额￥</div>
				    <input type="hidden" name="uid"  value="${uid}" />
				   <div class="col-md-2 f-size-l3 "  id='totalSpend1'>${order.totalCost }</div>
				  </div>
				  <div class="col-md-12 " style="text-align: right;">
				  	根据运营商规则,物联网卡一经售出不予退换
				  </div>
				</div>
				
				<div class="row btnList" style="margin: 10px;">
					  <div class="col-md-12 ctitle" style="text-align: right;">
					  <c:if test="${order.type==1 }">
					   <button type="button" lang="3" class="btn btn-info">支付订单</button>
					   </c:if>
					   <c:if test="${CURRENT_USER.role==0 && order.type==3 }">
					    <button type="button" lang="5" class="btn btn-success">审核通过</button>
					     <button type="button" lang="-1" class="btn btn-default">审核拒绝</button>
					   </c:if>
					    <c:if test="${CURRENT_USER.role==0 && order.type==5 }">
					     <button type="button"  lang="7" class="btn btn-primary">发货完成</button>
					    </c:if>
					     <c:if test="${order.type==7 }">
					     <button type="button"  lang="8" class="btn btn-warning">确认收货</button>
					    </c:if>
					  </div>
				</div>
				
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

<form action="" id='form' method="post"></form>

	<jsp:include page="../fragments/footer.jsp" />
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine.js"
		type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js"
		type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
		type="text/javascript"></script>

</body>
</html>
<script>
function initTotalPrice(){
	  var tao=0;
	  $.each($(".sum"),function(i,n){
			tao=parseFloat($(this).text())+tao;
		})
	var num=0;
	  $.each($(".num"),function(i,n){
		  num=parseInt($(this).text())+num;
		})
		$("#cardNum").html(num)
		$("#cardCount").val(num)
		$("#cardSpend").html(tao.toFixed(2))
		$("#totalSpend").html(tao.toFixed(2))
}
initTotalPrice();
drawIt('${order.type}')
function drawIt(sta){
	for(var i=1;i<=sta;i++){
		$("#step"+i).find('.el-step__head').removeClass('is-wait').addClass('is-finish')
		$("#step"+i).find('.el-step__title').removeClass('is-wait').addClass('is-finish')
		$("#step"+i).find('.el-step__description').removeClass('is-wait').addClass('is-finish')
		if(i<sta){
			$("#step"+i).find('.el-step__line-inner').css("width","100%").css("border-width","1px");
		}else{
				$("#step"+i).find('.el-step__description').removeClass('is-finish').addClass('is-process')
				$("#step"+i).find('.el-step__line-inner').css("width","50%").css("border-width","1px");
		}
	}
}

if('${order.type}'==-1){
	drawIt(5);
	$("#step5").find('.el-step__head').removeClass('is-finish').addClass('is-fail')
	$("#step5").find('.el-step__title').removeClass('is-finish')
	$("#step5").find('.el-step__description').removeClass('is-process').addClass('is-fail')
	$("#step5").find('.el-step__title').html("审核未通过")
}

$('.btnList').on(
		'click',
		'button',
		function(){
			if($(this).hasClass('disabled')){
				return;
			}
			var con=$(this).text();
			var mtype=$(this).attr("lang");
			bootbox.confirm('是否确认'+con, function(result) {
				  if(result){
					  var opt = {}
						opt.url="${ctx}/cart/updateStatus?id=${order.id}&type="+mtype
						opt.success = function(e) {
							if(e.res=='false'){
								toastr.error('账户余额不足!');
							}else{
								toastr.success('操作成功');
								 window.location.reload();
							}
						}
						$(this).addClass('disabled')
						SP.ajax($("#form"),opt);
				  }
				});
	
});


</script>