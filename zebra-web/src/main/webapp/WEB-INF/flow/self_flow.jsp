<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
<script type="text/javascript"
	src="${ctx }/assets/plugins/echarts-3.5.4.min.js"></script>
<style>
.row{
padding:15px
}
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    border-top: none;
}
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
					我的流量池 <small>流量池列表</small>
				</h1>
			</section>
			<section class="content">
				 <div class="row">
				 	 <div class="col-md-3 col-sm-6 col-xs-12">
			          <div class="info-box bg-gray">
			
			            <div class="info-box-content">
			              <span class="info-box-text">Bookmarks11</span>
			              <span class="info-box-number">41,410</span>
			
			              <div class="progress">
			                <div class="progress-bar" style="width: 90%;height:6px"></div>
			              </div>
			                  <span class="progress-description">
			                    70% Increase in 30 Days
			                  </span>
			            </div>
			            <!-- /.info-box-content -->
			          </div>
			          <!-- /.info-box -->
			        </div>
				 </div>
			</section>
		</div>
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine.js"
		type="text/javascript"></script>
	<script
		src="${ctx}/assets/plugins/validation/jquery.validationEngine-cn.js"
		type="text/javascript"></script>
</body>
</html>
<script>
$('#remark').val('${user.remark}')
$(".validationform").validationEngine({ relative: true, relativePadding:false,
	overflownDIV: ".form", promptPosition:"bottomRight" });
var options={};
$('#submit').click(function(){
	SP.ajax($("#validationform"),options);
});

var cardOption = {
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	    	 top:80,
	        data: ['已用流量','剩余流量']
	    },
	    grid: {
	    	left: '1%',
	        right: '0%',
	        top:-50,
	        bottom: '1%',
	        containLabel: true
	    },
	    series : [
	        {
	            name: '套餐分布',
	            type: 'pie',
	            radius : '48%',
	            center: ['22%', '50%'],
	            data:[
	                {value:'${card.packageUsed}', name:'已用流量'},
	                {value:'${card.packageLeft}', name:'剩余流量'}
	            ],
	            lableLine: {
	                normal: {
	                    show: true
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            itemStyle: {
	            	normal: {label:{  
	                    show:true,  
	                    formatter:'{b}\n{c}MB ({d}%)'  
	                },  
	                color: function(params) {
	                	 var colorList = [
	                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
	                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
	                        ];
	                        return colorList[params.dataIndex]
	                },
	                labelLine:{show:true}},  
	                  emphasis: {  
	                      label: {  
	                          show: true,  
	                          formatter: "{b}\n{c}MB ({d}%)",  
	                          position: 'center',  
	                          textStyle: {  
	                              fontSize: '14',  
	                              fontWeight: 'bold'  
	                          }  
	                      }  
	                  }  
	            }
	        }
	    ]
	};
var saasChart = echarts.init(document.getElementById("cardStatis"));
saasChart.setOption(cardOption);
</script>