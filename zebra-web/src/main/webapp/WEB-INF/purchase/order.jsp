       <%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet" href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
<link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/assets/plugins/bootstrap-modal/css/bootstrap-modal.css" rel="stylesheet" type="text/css" />
<style>
<!--
th {
    background-color: #cddae3;
}
.address {
	height: 40px;
	border: 1px solid #ddd;
	padding: 10px;
	border-radius: 4px;
	cursor: pointer;
	transition: border .5s ease;
	margin-top: 10px
}

.address label {
	cursor: pointer
}

.address-over {
	border: 1px solid #20a0ff;
}

.bootbox-alert {
	background: none;
	border: none;
	box-shadow: none;
}

#validationform2 .row {
	margin-top: 20px
}

.modal {
	top: 30%
}
.cont {
	line-height:30px;
}
.address .default {
	margin-left: 25px
}
thead th{
    background-color:#dce5ea;
    border-right: 1px solid #aaa;
    border-bottom: 1px solid #aaa;
}
.btn-app{
min-width: 120px;
border: 1px solid #ddd;
} 
.kuaidi{
text-align:center;display:block;font-size:14px;color:#222
}
.kcon{
text-align:center;display:block;font-size:10px;color:#999;margin-top:10px
}
.isselected {
    color: #20a0ff!important;
    border-color: #20a0ff!important;
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
			<form action="" id='form' method="post"></form>
			<form:form onsubmit="return false" action="${ctx}/cart/saveOrder" method="post" 
				class="validationform form-horizontal" id="validationform">
				<p class="lead">收货人信息</p>
				<c:forEach items="${addressList }" var="ad">
					<div class="row  address">
						<div class="form-group">
							<label> <input type="radio" name="addrId" class="minimal"
								value=${ad.id } <c:if test="${ad.opt==1 }">checked</c:if>>
								${ad.userName } &nbsp;&nbsp;&nbsp; ${ad.area }
								&nbsp;&nbsp;&nbsp;${ad.location } &nbsp;&nbsp;&nbsp;${ad.phone }
							</label>
							<c:if test="${ad.opt==1 }">
								<label class="default" style="color: #999">默认</label>
							</c:if>
							<a class="del" href="#" style="float: right; display: none">删除</a>
							<a href="#" class="edit"
								style="float: right; margin-right: 30px; display: none">编辑</a>
							<c:if test="${ad.opt==0 }">
								<a href="#" class="def"
									style="float: right; margin-right: 30px; display: none">设置默认</a>
							</c:if>
							<span style="display: none">${ad }</span>
						</div>
					</div>
				</c:forEach>
				<div class="row" id="newadd"
					style="margin-top: 20px; padding-bottom: 30px; border-bottom: 1px solid #ddd;">
					<button type="button" data-target="#addressloc" id="addBtn"
						data-toggle="modal" class="btn btn-default">新增收货地址</button>
					<button type="button" data-target="#addressloc" id="editBtn"
						style="display: none" data-toggle="modal" class="btn btn-default">none</button>
				</div>
			</section>
			<section class="godlist" style="padding:20px">
				<p class="lead">商品清单</p>
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
				<div class="row" style="padding-bottom:20px; border-bottom: 1px solid #ddd;margin-right:0px;margin-left:0px">
				<div class="col-md-1">
						<label for="inputEmail3" class="control-label">订单备注</label>
					</div>
					<div class="col-md-8">
					  <textarea rows="3" cols="10" style="height:80px"  class="form-control" autocomplete="off"  placeholder="本次交易的备注说明"   name="remark" id="remark"></textarea>
					</div>
				</div>
				<div class="row" style="padding-bottom:20px;margin-top:20px; border-bottom: 1px solid #ddd;margin-right:0px;margin-left:0px">
				<div class="col-md-1">
						<label for="inputEmail3" class="control-label">配送方式</label>
					</div>
					<div class="col-md-8">
					 <a class="btn btn-app isselected" lang="0">
			                <span class="kuaidi isselected">中通快递</span>
			                 <span class="kcon isselected">3-5天，4元起</span>
			              </a>
			              <a class="btn btn-app" lang="1">
			                <span class="kuaidi">顺丰快递</span>
			                 <span class="kcon">1-3天，12元起</span>
			              </a>
			              <input type="text" style="margin-left:30px;display:inline;width:180px"
							class="form-control  validate[required,custom[number]]"
							name="deliverCost" onblur="changeDeliver()" id="deliverCost" placeholder="快递价格">
							<input type="hidden" name="deliverType" id="deliverType" value="0" />
					</div>
				</div>
				<div class="row" style="padding-bottom:20px;margin-top:20px; border-bottom: 1px solid #ddd;margin-right:0px;margin-left:0px">
				  <div class="col-md-12 cont" style="text-align: right;">
				   <div class="col-md-10 color-666">总卡数</div>
				   <div class="col-md-2 f-size-l3" id='cardNum'></div>
				   <input type="hidden" name="cardCount" id="cardCount" value="" />
				  </div>
				  <div class="col-md-12 cont" style="text-align: right;">
				  <div class="col-md-10 color-666">总计卡费￥</div>
				   <div class="col-md-2 f-size-l3 " id='cardSpend'></div>
				  </div>
				  <div class="col-md-12 cont" style="text-align: right;">
				   <div class="col-md-10 color-666">运费￥</div>
				   <div class="col-md-2 f-size-l3 "  id='deliverSpend'>0</div>
				  </div>
				   <div class="col-md-12 cont" style="text-align: right;">
				    <div class="col-md-10 color-666">应付金额￥</div>
				    <input type="hidden" name="totalCost" id="totalCost" />
				    <input type="hidden" name="records" id="records" value="${records}" />
				    <input type="hidden" name="uid"  value="${uid}" />
				   <div class="col-md-2 f-size-l3 "  id='totalSpend'></div>
				  </div>
				  <div class="col-md-12 cont" style="text-align: right;">
				  	根据运营商规则,物联网卡一经售出不予退换
				  </div>
				</div>
				<div class="row" style="margin: 10px;">
					  <div class="col-md-12 ctitle" style="text-align: right;">
					   <button type="button" id="submit" class="btn btn-info">提交订单</button>
					  </div>
				</div>
				<div class="row" style="margin: 10px;">
					 <div class="col-md-12 cont" style="text-align: right;">
				  	点击提交订单表示您同意<a href="#" data-target="#xieyi"  data-toggle="modal" >《物联网卡入网使用协议》</a>
						
				  </div>
				</div>
				</form:form>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>


	<div id="addressloc" class="modal" data-keyboard="true"
		data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h4 class="modal-title">新增地址</h4>
		</div>
		<div class="modal-body" style="overflow: hidden;">
			<form:form onsubmit="return false" action="${ctx}/cart/addAddress"
				method="post" class="validationform form-horizontal"
				id="validationform2">
				<div class="row">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label">收货人</label> <input
							type="hidden" name="uid" value="${uid }" /> <input type="hidden"
							name="area" id="area" value="" />
					</div>
					<div class="col-md-8">
						<input type="text"
							class="form-control  validate[required,custom[letterHanzi]]"
							name="userName" id="userName" placeholder="联系人">
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label">手机号</label>
					</div>
					<div class="col-md-8">
						<input type="text"
							class="form-control validate[required,custom[mobile]]"
							name="phone" id="phone" value="" placeholder="手机号">
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label">省市县</label>
					</div>
					<div class="col-md-2">
						<select class="form-control" id="province" name="provinceId"
							style="float: left;" data-placeholder="省份">
							<c:forEach var="ca" items="${provinceList}">
								<option value="${ca.id}">${ca.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-2">
						<select class="form-control" id="city" name="cityId"
							style="float: left;" data-placeholder="城市">
						</select>
					</div>
					<div class="col-md-2">
						<select class="form-control" id="county" name="disId"
							style="float: left;" data-placeholder="区县">
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label">详细地址</label>
					</div>
					<div class="col-md-8">
						<input type="text" class="form-control validate[required]"
							name="location" id="location" placeholder="详细地址">
					</div>
				</div>
				<div class="row">
					<div class="col-md-3">
						<label for="inputEmail3" class="control-label"></label>
					</div>
					<div class="col-md-8">
						<label style="cursor: pointer"> <input type="checkbox"
							class="minimal" id='opt' value="1" name="opt"> 设置为默认收货地址
						</label>
					</div>
				</div>
			</form:form>
		</div>
		<div class="modal-footer">
			<button type="button" id="submitAddr" class="btn btn-primary">提交</button>
			<button type="button" data-dismiss="modal" id="closeAddr" class="btn btn-default">关闭</button>
		</div>
	</div>
	
	
	<div id="xieyi" class="modal" data-keyboard="true"
		data-backdrop="static">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true"></button>
			<h4 class="modal-title">SIMCLOUD 物联网卡入网服务协议</h4>
		</div>
		<div class="modal-body" style="overflow: hidden;">
		  协议内容
		</div>
		<div class="modal-footer">
			<button type="button" data-dismiss="modal" id="closeAddr" class="btn btn-default">关闭</button>
		</div>
	</div>

	<jsp:include page="../fragments/footer.jsp" />
		<script src="${ctx}/assets/plugins/validation/jquery.validationEngine.js" type="text/javascript"></script>
<script src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js" type="text/javascript"></script>
		<script src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modalmanager.js"
		type="text/javascript"></script>
	<script	src="${ctx}/assets/plugins/bootstrap-modal/js/bootstrap-modal.js"
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
function changeDeliver(){
	var tot=parseFloat($("#totalSpend").text())+parseFloat($("#deliverCost").val())
	$("#totalSpend").html(tot.toFixed(2))
	$("#deliverSpend").html($("#deliverCost").val())
}
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
	
	$("#buy").click(function() {
		window.location.href = "${ctx}/cart/buy"
	})

	$('.content').on('mouseout', '.address', function() {
		$(this).removeClass('address-over')
		$(this).find('a').hide()
	});

	$('.content').on('mouseover', '.address', function() {
		$(this).addClass('address-over')
		$(this).find('a').show()
	});

	initCity($("#province").val())

	function initCity(provinceId, cityId, distId) {
		$("#city").empty();
		var options = {};
		options.url = "${ctx}/cart/queryCity?id=" + provinceId + "&type=2";
		options.success = function(e) {
			if ($.isEmptyObject(e)) {
				win.showAlert("您选择的省份没有城市信息");
			} else {
				$("#city").attr('disabled', false);
				$("#city").append("<option value=''>全部城市</option>");
				$.each(e, function(key, value) {
					if (cityId && cityId == value.id) {
						cityId = value.id;
						initCounty(cityId, distId)
					}
					$("#city").append(
							"<option value="+ value.id + ">" + value.name
									+ "</option>");
				});
				if (cityId) {
					$("#city").val(cityId);
				} else {
					$("#city").val($("#city option").first().val());
				}
			}
		}
		SP.ajax($("#form"), options, false);
	}

	function initCounty(cityId, distId) {
		$("#county").empty();
		var options = {};
		options.url = "${ctx}/cart/queryCity?id=" + cityId + "&type=3";
		options.success = function(e) {
			if ($.isEmptyObject(e)) {
				win.showAlert("您选择的城市没有区县信息");
			} else {
				$("#county").attr('disabled', false);
				$("#county").append("<option value=''>全部区县</option>");
				$.each(e, function(key, value) {
					$("#county").append(
							"<option value="+ value.id + ">" + value.name
									+ "</option>");
				});
				if (distId) {
					$("#county").val(distId);
				} else {
					$("#county").val($("#county option").first().val());
				}
			}
		}
		SP.ajax($("#form"), options, false);
	}

	$('#province').change(function() {
		if ($('#province').val() == '') {
			$("#city").empty();
			$("#city").append("<option value=''>全部</option>");
			$("#city").attr('disabled', true);
		} else {
			initCity($('#province').val());
		}
		clearCounty();
	});
	$('#city').change(function() {
		if ($('#city').val() == '') {
			clearCounty()
		} else {
			initCounty($('#city').val());
		}
	});
	
	$("#submit").click(function(){
		var opt = {}
		$("#totalCost").val($("#totalSpend").text())
		opt.success = function(e) {
			toastr.success('操作成功');
			window.location.href="${ctx}/cart/pay?id="+e.id;
		}
		if($('input:radio[name="addrId"]:checked').length==0){
			alert("请选择地址");
			return;
		}
		SP.ajax($("#validationform"),opt);
	});

	$("#submitAddr").click(
			function() {
				var opt = {}
				opt.success = function(e) {
					$(".btn-default").trigger('click');
					if (e.id > 0) {
						toastr.success('操作成功');
						appendAddr(e)
					} else {
						toastr.error(data.msg);
						bootbox.alert(data.msg);
					}
				}
				$("#area").val(
						$("#province").find("option:selected").text() + ' '
								+ $("#city").find("option:selected").text()
								+ ' '
								+ $("#county").find("option:selected").text());
				SP.ajax($("#validationform2"), opt);
			})

	function appendAddr(e) {
		var appendAddress = '<div class="row address"><div class="form-group"><label> <input type="radio" value='+e.id+' name="addrId" class="minimal">'
				+ e.userName
				+ ' '
				+ $("#area").val()
				+ ' '
				+ e.location
				+ ' '
				+ e.phone + '</label>';
		var def = '';
		if ($('#opt').is(':checked')) {
			def = '<label class="default" style="color: #999">默认</label>';
		}
		appendAddress = appendAddress
				+ def
				+ '<a class="del" href="#" style="float: right; display: none">删除</a>'
				+ ' <a href="#" class="edit" style="float: right; margin-right: 30px; display: none">编辑</a>';
		if (def == '') {
			appendAddress = appendAddress
					+ '<a href="#" class="def" style="float: right; margin-right: 30px; display: none">设置默认</a>';
		}
		var span = '<span style="display:none">' + JSON2.stringify(e)
				+ '</span>'
		appendAddress = appendAddress + span + '</div></div>';
		$(appendAddress).insertBefore("#newadd");
	}

	$('.content').on(
			'click',
			'a.def',
			function() {
				var obj = JSON2.parse($(this).parent().find('span').text())
				var myoo = $(this).parent().find('label');
				var mthis = $(this);
				var options = {};
				options.url = "${ctx}/cart/setDefaultAddr?uid=${uid}&id=" + obj.id;
				options.success = function(e) {
					toastr.success('操作成功');
					$('.default').remove();
					mthis.remove();
					$('<label class="default" style="color: #999">默认 </label>')
							.insertAfter(myoo);
					$(":radio[name='add'][value=" + obj.id + "]").prop(
							"checked", "checked")
				}
				SP.ajax($("#form"), options, false);
			});

	$('.content').on('click', 'a.del', function() {
		var obj = JSON2.parse($(this).parent().find('span').text())
		var par = $(this).parent().parent();
		var options = {};
		options.url = "${ctx}/cart/delAddr?id=" + obj.id;
		options.success = function(e) {
			toastr.success('操作成功');
			par.remove();
		}
		SP.ajax($("#form"), options, false);
	});

	$('.content').on('click', 'a.edit', function() {
		var obj = JSON2.parse($(this).parent().find('span').text())
		$("#userName").val(obj.userName)
		$("#province").val(obj.provinceId)
		$("#phone").val(obj.phone)
		initCity(obj.provinceId, obj.cityId, obj.disId);
		$("#location").val(obj.location)
		if (obj.opt == 1) {
			$("#opt").attr('checked', true)
		}
		$("#editBtn").trigger('click')
	})

	$("#addBtn").click(function() {
		$("#userName").val("");
		$("#phone").val("");
		$("#province").val($("#province option").first().val());
		initCity($("#province").val())
		clearCounty()
		$("#location").val("");
		$("#opt").attr('checked', false)
	})

	function clearCounty() {
		$("#county").empty();
		$("#county").append("<option value=''>全部</option>");
		$("#county").val($("#county option").first().val());
		$("#county").attr('disabled', true);
	}
	$(".btn-app").click(function(){
		if($(this).hasClass('isselected')){
			$(this).removeClass('isselected')
			$(this).find('span').removeClass('isselected')
		}else{
			$(".btn-app").removeClass('isselected')
			$(".btn-app").find('span').removeClass('isselected')
			$(this).addClass('isselected')
			$(this).find('span').addClass('isselected')
			$("#deliverType").val($(this).attr("lang"))
		}
	})
</script>