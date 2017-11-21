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
					管理 <small> 购物车 </small>
				</h1>
			</section>
			<!-- Main content -->
			<section class="content">
			<c:if test="${goodsList== null || fn:length(goodsList) == 0}">
			   <div class="row" style="margin: 10px;">
					  <div class="col-md-12 ctitle" style="text-align: center;">
					   <button type="button" id="buy" class="btn btn-default ">马上购卡</button>
					  </div>
				</div>
			</c:if>
			<c:if test="${goodsList!= null && fn:length(goodsList) > 0}">
				<div class="row" style="margin: 10px;border-bottom: 1px solid #ddd">
					<div class="col-md-3 ctitle" style="margin-left: 10px">商品详情
					</div>
					<div class="col-md-1 ctitle">套餐价格</div>
					<div class="col-md-1 ctitle">订购周期</div>
					<div class="col-md-1 ctitle">单张卡费</div>
					<div class="col-md-2 ctitle">卡片数量</div>
					<div class="col-md-2 ctitle">小计</div>
					<div class="col-md-1 ctitle" style="margin-right: 10px">操作</div>
				</div>
				
				<c:forEach items="${ goodsList}" var="gl">
				<div class="row" id="row${gl.id }" style="margin: 10px;border-bottom: 1px solid #ddd">
					<div class="col-md-3 cont" style="margin-left: 10px">${gl.name }</div>
					<div class="col-md-1 cont single">${gl.price }</div>
					<div class="col-md-1 cont">${gl.term }月</div>
					<div class="col-md-1 cont ">0</div>
					<div class="col-md-2  " style="padding-top:20px" >
						<div class="input-group spinner" data-trigger="spinner">
				          <input type="text" alt="${gl.term }" lang="${gl.id }" class="form-control text-center" 
				          data-max="500" data-min="1" value="${gl.num }" data-rule="quantity">
				          <div class="input-group-addon">
				            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
				            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
				          </div>
				        </div>
					</div>
					<div class="col-md-2 cont sum"><fmt:formatNumber value="${gl.price*gl.num*gl.term }" pattern="#0.00" /></div>
					<div class="col-md-1 cont" style="margin-right: 10px">
						<button type="button" onclick="removeRecord(${gl.id})" class="btn btn-box-tool" data-widget="remove">
						<i class="fa fa-remove"></i></button>
					</div>
				</div>
				</c:forEach>
				
				<div class="row" style="margin: 10px;border-bottom: 1px solid #ddd">
				  <div class="col-md-12 cont" style="text-align: right;">
				  <span class="color-666 f-bold">总计（不含运费）￥</span>
				  <span class="f-size-l3 f-bold" id="total"></span>
				  </div>
				</div>
				<div class="row" style="margin: 10px;">
					  <div class="col-md-12 ctitle" style="text-align: right;">
					   <button type="button" id="continue" class="btn btn-default ">继续购卡</button>
					    &nbsp;&nbsp;&nbsp;&nbsp;
					   <button type="button" id="settlment" class="btn btn-info">去结算</button>
					  </div>
				</div>
				</c:if>
				
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
$('.spinner').spinner('changed',function(e, newVal, oldVal){
	var sng=$(this).parent().parent().parent().find('.single').text();
	var term=parseInt($(this).attr('alt'))
	$(this).parent().parent().parent().find('.sum').html((parseFloat(sng).toFixed(2)*newVal*term).toFixed(2))
	initTotalPrice()
  });
initTotalPrice();
  function initTotalPrice(){
	  var tao=0;
	  $.each($(".sum"),function(i,n){
			tao=parseFloat($(this).text())+tao;
		})
		$("#total").html(tao.toFixed(2))
		if($("#total").text()=='0.00'){
			 $("#settlment").addClass('disabled');
		}
  }
  $("#continue").click(function(){
	  window.location.href="${ctx}/cart/buy"
  })
   $("#settlment").click(function(){
	   if($(this).hasClass('disabled')==false){
		   saveCardNums();
	   }
  })
  
  function removeRecord(mid){
	  var options={};
	  options.url="${ctx}/cart/delete?id="+mid;
	    options.success=function(data){
	    		  toastr.success('操作成功');
	    		  $("#row"+mid).remove();
	    		  initTotalPrice();
		  };
		 SP.ajax($("#form"),options);
  }
  function saveCardNums(){
	  var num="";
	 $.each( $(".content").find('input'),function(i,n){
		 num=$(this).attr("lang")+"_"+$(this).val()+","+num;
	 });
	 
	 var options={};
	  options.url="${ctx}/cart/setlement?ids="+num;
	    options.success=function(data){
	    		  window.location.href="${ctx}/cart/order?uid=${user.id}"
		  };
		 SP.ajax($("#form"),options);
  }
</script>