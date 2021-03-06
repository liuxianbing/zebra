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
					 <a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a>
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
									<div><c:if test="${order.type>=5 }">${order.auditUser}</c:if></div>
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
							<div class="el-step__title is-wait">完成划拨</div>
							<div class="el-step__description is-wait">
								<div class="step__description--content">
									<div><c:if test="${order.type>=7 }">${ fn:substring( order.allocTime ,0,10)}</c:if></div>
									<div><c:if test="${order.type>=7 }">${ fn:substring( order.allocTime,10,fn:length(order.allocTime)-2)}</c:if></div>
									<div><c:if test="${order.type>=7 }">${order.allocUser}</c:if></div>
								</div>
							</div>
						</div>
					</div>
					<div class="el-step is-horizontal"
						style="width: 200px; margin-right: 0px;" id="step9">
						<div class="el-step__head is-wait is-text">
							<div class="el-step__line is-horizontal"
								style="margin-right: 0px;">
								<i class="el-step__line-inner"
									style="transition-delay: -450ms; border-width: 0px; width: 0%;"></i>
							</div>
							<span class="el-step__icon"><div class="nums">5</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-wait">包裹发出</div>
							<div class="el-step__description is-wait">
								<div class="step__description--content">
									<div><c:if test="${order.type>=9 }">${ fn:substring( order.outerTime ,0,10)}</c:if></div>
									<div><c:if test="${order.type>=9 }">${ fn:substring( order.outerTime,10,fn:length(order.outerTime)-2)}</c:if></div>
									<div><c:if test="${order.type>=9 }">${order.outerUser}</c:if></div>
								</div>
							</div>
						</div>
					</div>
					<div class="el-step is-horizontal" style="width: 200px;" id="step11">
						<div class="el-step__head is-wait is-text">
							<span class="el-step__icon"><div class="nums">6</div></span>
						</div>
						<div class="el-step__main" style="margin-left: 0px;">
							<div class="el-step__title is-wait">完成</div>
							<div class="el-step__description is-wait">
								<div class="step__description--content">
									<div><c:if test="${order.type>=11 }">${ fn:substring( order.sucTime ,0 ,10)}</c:if></div>
									<div><c:if test="${order.type>=11 }">${ fn:substring( order.sucTime,10,fn:length(order.sucTime)-2)}</c:if></div>
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
				<c:if test="${order.type>=5 }">
					 <p class="lead" style="margin-top:15px">购卡清单</p>
					 <div  class="row" style="max-height:400px;overflow-y:auto">
					 <table class="table table-bordered table-hover">
				   <thead>
				     <tr>
				      <th width="30%">ICCID</th>
				       <th width="20%">运营商</th>
				       <th width="25%">卡片类型</th>
				        <th  width="25%">套餐规格</th>
				         </tr>
				   </thead>
				   <tbody>
				   <c:forEach items="${ cards}" var="cs">
				   <tr>
				   <td>${cs.iccid }</td>
				   <td>中国联通</td>
				   <td>
				   <c:if test="${cs.cardType==0 }">
				   单卡
				   </c:if>
				     <c:if test="${cs.cardType==1 }">
				     流量池卡
				   </c:if>
				   </td>
				   <td>${cs.flow }MB套餐</td>
				    </tr>
				   </c:forEach>
				   </tbody>
				</table>
				</div>
				</c:if>
				 <div class="row" style="padding:20px;border-bottom:1px solid #ccc">
               <p>订单备注：${order.remark }</p>
                 <p> &nbsp;</p>
                  <p>快递信息：${order.deliverStr }</p>
                   <c:if test="${order.type>=7 }"> <p> &nbsp;</p> <p >运单号： ${order.deliverCode }</p></c:if>
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
					   <c:if test="${(CURRENT_USER.type==1 || CURRENT_USER.type==-1) && order.type==3 }">
					    <button type="button" lang="5" class="btn btn-success">审核通过</button>
					     <button type="button" lang="-1" class="btn btn-default">审核拒绝</button>
					   </c:if>
					   <c:if test="${(CURRENT_USER.type==2 || CURRENT_USER.type==-1)  && order.type==5 }">
					     <a href="${ctx }/simcard/list" >开始划拨</a>
					     <button type="button"  lang="7" style="margin-left:30px" class="btn btn-primary">完成划拨</button>
					    </c:if>
					    <c:if test="${(CURRENT_USER.type==3 || CURRENT_USER.type==-1)   && order.type==7 }">
					     <button type="button"  lang="9" class="btn btn-primary">发货完成</button>
					    </c:if>
					     <c:if test="${order.type==9 }">
					     <button type="button"  lang="11" class="btn btn-warning">确认收货</button>
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

<div id="distDiv" class="modal" data-keyboard="true"
		data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h4 class="modal-title">发货信息</h4>
		</div>
		<div class="modal-body" style="overflow: hidden;">
			<form:form onsubmit="return false" action="${ctx}/cart/updateStatus"
				method="post" class="validationform form-horizontal"
				id="validationform3">
				<div class="row">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label">运单号</label>
					</div>
					<div class="col-md-5">
					 <input type="text" class="form-control" 
			                    name="deliverCode" id="deliverCode"  placeholder="订单号">
					</div>
				</div>
				<input type="hidden" name="id" value="${order.id }" />
				<input type="hidden" name="type" id="otype" />
				<div class="row" style="margin-top:15px">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label">订单备注</label>
					</div>
					<div class="col-md-5">
					<textarea rows="3" cols="10" style="height: 80px" class="form-control" value="" name="remark" id="remark">${order.remark }</textarea>
					</div>
				</div>
			</form:form>
		</div>
		<div class="modal-footer">
			<button type="button" id="submitDist" class="btn btn-primary">提交</button>

			<button type="button" data-dismiss="modal" id="closeupload3"
				class="btn btn-default">关闭</button>
		</div>
	</div>
	
	
	<button class="btn blue" id="hb" data-target="#distDiv"
		data-toggle="modal" type='button' style="float: right; display: none">rr</button>

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
			if(mtype==9){
				$("#hb").trigger('click')
				return;
			}
			doPost(con,mtype);
	
});

$("#submitDist").click(function(){
	doPost("发货",9)
})

function doPost(con,mtype){
	bootbox.confirm('是否确认'+con, function(result) {
		  if(result){
			  var opt = {}
				//opt.url="${ctx}/cart/updateStatus?id=${order.id}&type="+mtype
				opt.success = function(e) {
					if(e.res=='false'){
						toastr.error('账户余额不足!');
					}else{
						console.log(e)
						toastr.success('操作成功');
						 window.location.reload();
					}
				}
				$("#otype").val(mtype);
				SP.ajax($("#validationform3"),opt,false);
		  }
		});
}


</script>