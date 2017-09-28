<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<!-- jQuery 3 -->
<script src="${pageContext.request.contextPath}/assets/plugins/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.7 -->
<script src="${pageContext.request.contextPath}/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/plugins/lte/js/adminlte.min.js"></script>

<script>
if (typeof String.prototype.startsWith != 'function'){   
    String.prototype.startsWith = function (str){  
       return this.slice(0, str.length) == str;  
    };  
  }  
  if (typeof String.prototype.endsWith != 'function') {  
    String.prototype.endsWith = function (str){  
       return this.slice(-str.length) == str;  
    };  
  }  
$.each( $(".sidebar-menu").find('a'), function(i, n){
	if($(this).attr('href').endsWith('/index')){
		$(this).parent().addClass('active');
		if($(this).parent().parent().hasClass('treeview-menu')){
			$(this).parent().parent().addClass('active');
		}
	}
});

</script>