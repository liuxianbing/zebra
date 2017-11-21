<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/assets/plugins/spinner/bootstrap-spinner.css" />
	<link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<<style>
<!--
.label-success {
    position: absolute;
    top: 9px;
    right: 24px;
    text-align:center;
    font-size: 9px;
    padding: 2px 3px;
    line-height: .9;
}
.bootbox-alert{
	background: none;
	border:none;
	box-shadow:none;
}
-->
</style>
</head>

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<jsp:include page="../fragments/menu.jsp" />
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
		 <!-- Content Header (Page header)  <span style="float:right;margin-right:20px">购物车</span> -->
		    <section class="content-header">
		      <h1 >
		      	  商店
		        <small>
		        </small>
		        <a href="${ctx }/cart/list" id="cart"  style="float:right;margin-right:10px;font-size:12px">
             		 <i class="fa fa-shopping-cart" style="font-size:16px"></i>
             		 <!-- 
             		 <span class="label label-success" style="margin-right:45px;">4</span>
             		  -->
             		<font style="font-size:14px">&nbsp;&nbsp;&nbsp;购物车</font> 
            </a>
		      </h1>
		    </section>
			<!-- Main content -->
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/cart/buy" method="post" 
				class="validationform form-horizontal" id="validationform">
				<div class="row" style="margin-top:30px">
						   <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">选择客户</label>
			                  <div class="col-sm-8">
			                   <select id="uid" class="form-control select2  validate[required]" name="uid"
								style="float: left;" data-placeholder="选择客户">
								 <c:forEach items="${userList }" var="ul" >
								     <option  value="${ul.id }" >${ul.userName }-${ul.companyName }</option>
								  </c:forEach>
								</select>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">运营商</label>
			                  <div class="col-sm-8">
			                   <select id="operator" class="form-control select2  validate[required]" name="operator"
								style="float: left;" data-placeholder="运营商">
								 <option value="3">中国联通</option>
								</select>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						   <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">套餐类型</label>
			                  <div class="col-sm-8">
			                   <select id="cardType" class="form-control select2" name="cardType"
								style="float: left;" data-placeholder="套餐类型">
								 <option value="0">单卡</option>
								 <option value="1">流量卡</option>
								</select>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
					<div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">选择套餐</label>
			                  <div class="col-sm-8">
			                    <select id="packageId" class="form-control select2  validate[required]" name="packageId"
								style="float: left;" data-placeholder="选择套餐">
								</select>
								<input type="hidden" name="flow" id='flow' />
								<input type="hidden" name="name" id='name' />
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">套餐价格</label>
			                  <div class="col-sm-3">
			                    <input type="text"  class="form-control" readonly="readonly"
			                    name="price" id="price" placeholder="套餐价格" value="">
			                  </div>
			                </div>
						  </div>
					</div>
						<div class="row" >
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">订购周期(月)</label>
			                  <div class="col-sm-3">
			                   <div class="input-group spinner" data-trigger="spinner">
							          <input type="text" id="term" name="term"  class="form-control text-center validate[required,custom[integer]]" 
							          data-max="36" data-min="1" value="1" data-rule="quantity">
							          <div class="input-group-addon">
							            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
							            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
							          </div>
							        </div>
			                  </div>
			                </div>
						  </div>
					</div>
					<div class="row" >
						  <div class="col-md-12">
						  	 <div class="form-group">
			                  <label for="inputEmail3" class="col-sm-2 control-label">订购数量</label>
			                  <div class="col-sm-3">
			                   <div class="input-group spinner" data-trigger="spinner">
							          <input type="text" id="num" name="num" class="form-control text-center validate[required,custom[integer]]" 
							          data-max="3000" data-min="1" value="1" data-rule="quantity">
							          <div class="input-group-addon">
							            <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
							            <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
							          </div>
							        </div>
			                  </div>
			                </div>
						  </div>
					</div>
					 <!-- /.box-body -->
	              <div class="row">
	                 <div class="col-md-12" style="text-align: center">
	                <button type="button" id="submit" class="btn btn-info">提交</button>
	                </div>
	              </div>
				</form:form>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<div style="display:none" id="allPackage">
    <c:forEach items="${packageList }" var="pl" >
            <a href="${pl.id }" id="pack_${pl.id }" lang="${pl.externalQuote }" tabindex="${pl.flow }" class="${pl.cardType }" >${pl.flow }MB</a>
	</c:forEach>
</div>

<div id="buytip" class="modal"  data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">提示</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
	   成功加入购物车
	</div>
	<div class="modal-footer">
	<button type="button" id="toCartList"   class="btn btn-primary">查看购物车</button>
		<button type="button" data-dismiss="modal" id="buy"
			class="btn btn-default">继续购卡</button>
	</div>
</div>

<button class="btn blue"  id="tip" data-target="#buytip" 
data-toggle="modal"   type='button' style="float:right;display:none">rr</button>
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script src="${ctx}/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>
<script src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js" type="text/javascript"></script>
	<script src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js" type="text/javascript"></script>
		<script src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js" type="text/javascript"></script>
	
		<script src="${ctx}/assets/plugins/spinner/jquery.spinner.js"
		type="text/javascript"></script>
</body>
</html>
<script>
$(".select2").select2({ allowClear:false}).on("change", function(e) {
	if($(this).attr("id")=="uid"){
		initPackSelect($(this).val())
		$("#cart").attr('href',"${ctx}/cart/list?uid="+$(this).val())
	}else if($(this).attr("id")=="packageId"){
		var mmid=$(this).val();
		 $.each(currentPackage, function(key, value) {
			 if(value.id==mmid){
				 $("#price").val( value.externalQuote);
				 $("#flow").val(value.flow);
				 $("#name").val(value.name);
			 }
		});
	}else if($(this).attr("id")=="cardType"){
		initPackSelect($("#uid").val())
	}
});
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
var options={};
options.success=function(e){
	if(e.result=='success'){
		$("#tip").trigger('click')
	}else{
		boot.showAlert(e.msg)
	}
}
$('#submit').click(function(){
	//$("#name").val( $("#name").val())
	SP.ajax($("#validationform"),options);
});

var currentPackage;

initPackSelect($("#uid").val());

$('.spinner').spinner('changed',function(e, newVal, oldVal){
  });
  $("#toCartList").click(function(){
	  window.location.href="${ctx}/cart/list?uid="+$("#uid").val();
  })
    $("#buy").click(function(){
	  //window.location.href="${ctx}/cart/buy"
  })
  
  function initPackSelect(uid){
  var options={}
  options.url="${ctx}/pack/selfUserPacks?uid="+uid
  $("#packageId").empty();
  options.success = function(e) {
	  $.each(e, function(key, value) {
		     if(value.cardType==$("#cardType").val()){
		    	 $("#packageId").append("<option value="+ value.id + ">" + value.name+ "</option>");
		     }
		});
	  currentPackage=e;
	  
		 $.each(e, function(key, value) {
			 if(value.id==$("#packageId").val()){
				 $("#price").val( value.externalQuote);
				 $("#flow").val(value.flow);
				 $("#name").val(value.name);
			 }
		});
  }
  SP.ajax($("#validationform"), options, false);
  }
  $("#cart").attr('href',"${ctx}/cart/list?uid="+$("#uid").val())
</script>