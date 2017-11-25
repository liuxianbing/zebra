<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="fragments/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="fragments/meta.jsp"/>
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
    <a href="#"><b>SIM CLOUDS</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg"></p>

    <form:form onsubmit="return subForm()" action="${ctx }/login" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" name="phone" id="phone" placeholder="用户手机号">
        <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" name="passwd" id="passwd" placeholder="密码">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
          <div class="checkbox icheck">
            <label>
             <font id="msg" name="msg" color="red" >${msg}</font>
            </label>
          </div>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" onclick="subForm()" class="btn btn-primary btn-block btn-flat">登录</button>
        </div>
        <!-- /.col -->
      </div>
    </form:form>

    <!-- /.social-auth-links 

    <a href="#">I forgot my password</a><br>
    <a href="register.html" class="text-center">Register a new membership</a>
-->
  </div>
  <!-- /.login-box-body -->
</div>
	<jsp:include page="fragments/footer.jsp"/>
	 
</body>

<script>
function subForm() {
	var flag = false;
	if ( $('#account').val().length==0) {
   		$('#msg').html("请输入用户账号!");
   		flag = false;
   	}  else if($('#passwd').val().length==0  ) {
   		$('#msg').html("请输入密码!");
   		flag = false;
   	} else {
   		flag = true;
   	}
	if (!flag) {
		setTimeout(function() {
			$('#msg').html("");
		}, 2000);
	}else{
		var pw = $('#passwd').val().trim();
		$('#passwd').val(pw)
	}
	return flag;
}

String.prototype.trim=function()
{
     return this.replace(/(^\s*)|(\s*$)/g,'');
}

</script>
</html>
