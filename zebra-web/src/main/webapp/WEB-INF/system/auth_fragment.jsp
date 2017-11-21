<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ include file="../fragments/taglibs.jsp"%>

<form:form onsubmit="return false" action="${ctx}/user/auth"
	method="post" class="validationform form-horizontal"
	id="validationformBusiness">
	 <div class="box-header">
              <h3 class="box-title">工商信息</h3>
            </div>
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">企业工商认证</label>
				<div class="col-sm-8">
				    <c:if test="${CURRENT_USER.role==1 }">
				      <c:choose>
				      <c:when test="${company.businessAuth ==1 }"><i class="fa fa-fw fa-check-circle bg-green" style="width:110px;line-height:23px">已认证</i></c:when>
				      <c:otherwise><i class="fa fa-fw fa-check-circle bg-yellow" style="width:110px;line-height:23px">未认证</i></c:otherwise>
				      </c:choose>
				  </c:if>
				     <c:if test="${CURRENT_USER.role==0 }">
					<label class="radio-inline"> <input type="radio"
						name="businessAuth" id="businessAuth1" value="1"
						${company.businessAuth ==1 ?'checked':''}> 已认证
					</label> <label class="radio-inline"> <input type="radio"
						name="businessAuth" id="businessAuth2" value="0"
						${company.businessAuth==null || company.businessAuth ==0 ?'checked':''}>
						未认证
					</label>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row" >
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">企业全称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control validate[required]"
						name="name" id="name" value="${company.name }" placeholder="企业全称">
					<input type="hidden" name="id" value="${company.id }"> <input
						type="hidden" name="uid" id="uid" value="${uid}" />
				</div>
			</div>
		</div>
		
	</div>
	<div class="row">
	<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">企业工商营业执照</label>
				<div class="col-sm-8">
					<input type="text" class="form-control  validate[required]"
						name="businessCode" id="businessCode"
						value="${company.businessCode }" placeholder="请输入15位工商营业执照注册号，或三证合一后18位的统一社会信用代码">
				</div>
			</div>
		</div>
	</div>
	<div class="row">
	<c:if test="${company.businessAuth==null || company.businessAuth==0 }">
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">营业执照</label>
				<div class="col-sm-8">
					<button class="btn btn-info" data-target="#business"
						data-toggle="modal" type='button'>点击按钮上传</button>
					<button class="btn btn-danger" id="delBusiness"
						style="margin-left: 80px" type='button'>删除图片</button>
						 <div style="color:#999">原件照片、扫描件或者加盖公章的复印件，支持.jpg .jpeg .bmp .gif .png等格式照片，大小不超过2M</div>
				</div>
			</div>
		</div>
		</c:if>
		<input type="hidden" name="businessUrl" id="businessUrl"
						value="${company.businessUrl}" />
		<div class="col-md-6 businessUrl">
		   
			<c:if test="${ fn:length(company.businessUrl)>2  }" >
				<img alt="营业执照" src="${ctx }/${company.businessUrl}" width="400px"
					height="260px" />
			</c:if>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6" style="text-align: center">
			<button type="button" id="submitBusiness" class="btn btn-info">提交</button>
			<button type="button" id="resetBusiness" style="margin-left:50px" class="btn">取消</button>
		</div>
	</div>
	</form:form>
	
	   <div class="box-header">
              <h3 class="box-title">法人信息</h3>
            </div>
<form:form onsubmit="return false" action="${ctx}/user/auth"
	method="post" class="validationform form-horizontal"
	id="validationformLegal">
	<div class="row" style="margin-top:30px">
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">企业法人认证</label>
				<div class="col-sm-8">
				   <c:if test="${CURRENT_USER.role==1 }">
				      <c:choose>
				      <c:when test="${company.legalAuth ==1 }"><i class="fa fa-fw fa-check-circle bg-green" style="width:110px;line-height:23px">已认证</i></c:when>
				       <c:otherwise><i class="fa fa-fw fa-check-circle bg-yellow" style="width:110px;line-height:23px">未认证</i></c:otherwise>
				      </c:choose>
				  </c:if>
				     <c:if test="${CURRENT_USER.role==0 }">
					<label class="radio-inline"> <input type="radio"
						name="legalAuth" id="legalAuth1" value="1"
						${company.legalAuth ==1 ?'checked':''}> 已认证
					</label> <label class="radio-inline"> <input type="radio"
						name="legalAuth" id="legalAuth2" value="0"
						${company.legalAuth==null || company.legalAuth ==0 ?'checked':''}>
						未认证
					</label>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">法人身份证号码</label>
				<div class="col-sm-8">
				<input type="text" class="form-control  validate[required]"
						name="legalCode" id="legalCode"
						value="${company.legalCode }" placeholder="法人身份证号码">
				</div>
			</div>
		</div>
	</div>
	
		<input type="hidden" name="id" id="mmid" value="${company.id }"> <input
						type="hidden" name="uid" id="uids" value="${uid}" />
	
	<div class="row">
	<c:if test="${company.legalAuth==null || company.legalAuth==0 }">
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">法人身份证照片(正面)</label>
				<div class="col-sm-8">
					<button class="btn btn-info" data-target="#positive"
						data-toggle="modal" type='button'>点击按钮上传</button>
					<button class="btn btn-danger" id="delPositive"
						style="margin-left: 80px" type='button'>删除图片</button>
						 <div style="color:#999"></div>
				</div>
			</div>
		</div>
		</c:if>
		<input type="hidden" name="legalPositive" id="legalPositive"
						value="${company.legalPositive}" />
		<div class="col-md-6 legalPositive">
		
			<c:if test="${fn:length(company.legalPositive)>2  }">
				<img src="${ctx }/${company.legalPositive}" width="500px"
					height="300px" />
			</c:if>
		</div>
	</div>
	<div class="row">
	<c:if test="${company.legalAuth==null || company.legalAuth==0 }">
		<div class="col-md-6">
			<div class="form-group">
				<label for="inputEmail3" class="col-sm-4 control-label">法人身份证照片(反面)</label>
				<div class="col-sm-8">
					<button class="btn btn-info" data-target="#backid"
						data-toggle="modal" type='button'>点击按钮上传</button>
					<button class="btn btn-danger" id="delBack"
						style="margin-left: 80px" type='button'>删除图片</button>
				</div>
			</div>
		</div>
		</c:if>
			<input type="hidden" name="legalBack" id="legalBack"
						value="${company.legalBack}" />
		<div class="col-md-6 legalBack">
			<c:if test="${fn:length(company.legalBack)>2  }">
				<img src="${ctx }/${company.legalBack}" width="500px" height="300px" />
			</c:if>
		</div>
	</div>
	<!-- /.box-body -->
	<div class="row">
		<div class="col-md-6" style="text-align: center">
			<button type="button" id="submitLegal" class="btn btn-info">提交</button>
			<button type="button" id="resetLegal" style="margin-left:50px" class="btn">取消</button>
		</div>
	</div>

</form:form>



<div id="business" class="modal" style="z-index: 5000"
	data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">上传营业执照</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
		<form action="${ctx}/user/uploadBusiness" class="dropzone"
			id="business-dropzone">
			<div id="dz-message1" class="dz-message"
				style="margin: 0 auto; text-align: center">
				<h3>
					<span style="padding-top: 15px; height: 60px;"> <i
						class="fa fa-plus" style="font-size: 35px;"></i>
					</span><font style="margin-left: 30px">点击区域或者拖动文件</font>
				</h3>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" id="closeupload1"
			class="btn btn-default">关闭</button>
	</div>
</div>

<div id="positive" class="modal" style="z-index: 5000"
	data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">上传身份证正面</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
		<form action="${ctx}/user/uploadPositive" class="dropzone"
			id="positive-dropzone">
			<div id="dz-message2" class="dz-message"
				style="margin: 0 auto; text-align: center">
				<h3>
					<span style="padding-top: 15px; height: 60px;"> <i
						class="fa fa-plus" style="font-size: 35px;"></i>
					</span><font style="margin-left: 30px">点击区域或者拖动文件</font>
				</h3>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" id="closeupload2"
			class="btn btn-default">关闭</button>
	</div>
</div>

<div id="backid" class="modal" style="z-index: 5000"
	data-keyboard="true" data-backdrop="static">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true"></button>
		<h4 class="modal-title">上传身份证背面</h4>
	</div>
	<div class="modal-body" style="overflow: hidden;">
		<form action="${ctx}/user/uploadBack" class="dropzone"
			id="back-dropzone">
			<div id="dz-message3" class="dz-message"
				style="margin: 0 auto; text-align: center">
				<h3>
					<span style="padding-top: 15px; height: 60px;"> <i
						class="fa fa-plus" style="font-size: 35px;"></i>
					</span><font style="margin-left: 30px">点击区域或者拖动文件</font>
				</h3>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" id="closeupload3"
			class="btn btn-default">关闭</button>
	</div>
</div>
<script src="${pageContext.request.contextPath}/assets/plugins/jquery.min.js"></script>
<script>
$('#submitLegal').click(function(){
	var options={}
	options.success=function(data){
		 toastr.success('操作成功');
		 $("#mmid").val(data.id)
	}
	SP.ajax($("#validationformLegal"),options);
});
$('#submitBusiness').click(function(){
	var options={}
	options.success=function(data){
		 toastr.success('操作成功');
		 $("#mmid").val(data.id)
	}
	SP.ajax($("#validationformBusiness"),options);
});
if('${company.businessUrl}'==''){
	$("#delBusiness").addClass('disabled')
}
if('${company.legalPositive}'==''){
	$("#delPositive").addClass('disabled')
}
if('${company.legalBack}'==''){
	$("#delBack").addClass('disabled')
}

$("#delBusiness").click(function(){
	if($("#businessUrl").val().length>2){
		$(".businessUrl").find('img').remove();
		$("#businessUrl").val('')
		$("#delBusiness").addClass('disabled')
	}
})
$("#delBack").click(function(){
	if($("#legalBack").val().length>2){
		$(".legalBack").find('img').remove();
		$("#legalBack").val('')
		$("#delBack").addClass('disabled')
	}
})
$("#delPositive").click(function(){
	if($("#legalPositive").val().length>2){
		$(".legalPositive").find('img').remove();
		$("#legalPositive").val('')
		$("#delPositive").addClass('disabled')
	}
})

</script>