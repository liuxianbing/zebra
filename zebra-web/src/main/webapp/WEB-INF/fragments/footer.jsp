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

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/plugins/bootstrap-toastr/toastr.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/plugins/json2.js"></script>
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/assets/plugins/select2/js/select2.full.min.js"></script>
	
<script src="${pageContext.request.contextPath}/assets/scripts/spider.js"></script>

<script
	src="${pageContext.request.contextPath}/assets/plugins/bootbox/bootbox.min.js"
	type="text/javascript"></script>

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
  var href=window.location.href;
  href=href.substring(0,href.lastIndexOf('/'));
  
  var find=false;
  $.each( $(".sidebar-menu").find('a'), function(i, n){
		var inhref=$(this).attr('href');
		if(window.location.href.endsWith(inhref) && !find){
			find=true;
			$(this).parent().addClass('active');
			if($(this).parent().parent().parent().hasClass('treeview')){
				$(this).parent().parent().parent().addClass('active');
			}
		}
	});
  if(!find){
		$.each( $(".sidebar-menu").find('a'), function(i, n){
			var inhref=$(this).attr('href').substring(0,$(this).attr('href').lastIndexOf('/'));
			if(href.endsWith(inhref) && inhref.length>2 && !find){
				$(this).parent().addClass('active');
				if($(this).parent().parent().parent().hasClass('treeview')){
					$(this).parent().parent().parent().addClass('active');
				}
				find=true;
			}
		});
  }
//列表页面点击事件
$('#table_list').on('click', ' tbody tr', function () {	
	if($(this).find('td').length==1){
		return;
	}
   if ( $(this).hasClass('row_selected') ) {		   
		$(this).removeClass('row_selected');
		$(".btn_table").find('.btn').not(".btn-success").addClass("disabled");
	}else {				 
		oTable.$('tr.row_selected').removeClass('row_selected');
		$(this).addClass('row_selected');
		$(".btn_table").find('.btn').removeClass("disabled");
		selectData=oTable.fnGetData(oTable.$(this)[0]);
	}	
});
</script>