<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="../fragments/meta.jsp"/>
</head>
<body class="hold-transition skin-blue sidebar-mini" style="text-align:center">

 <!-- Main content -->
    <section class="content">

      <div class="error-page" style="margin-top:300px">
      
        <div style="float:left">
           </div>
           <div style="float:left;margin-left:40px">
           <h3 style="margin-top:60px" ><i class="fa fa-warning text-red" ></i> ${msg }</h3>
           </div>
             
      </div>
      <!-- /.error-page -->

    </section>
    
	<jsp:include page="../fragments/footer.jsp"/>
	 
</body>

</html>
