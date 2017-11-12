<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
<link rel="stylesheet"
	href="${ctx }/assets/plugins/validation/validationEngine.jquery.css" />
<link rel="shortcut icon" href="${ctx}/assets/img/fav.ico" />
	<link rel="stylesheet" type="text/css"
	 href="${ctx}/assets/plugins/bootstrap-daterangepicker/daterangepicker.css" />
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
					卡片列表 <small>卡片详情</small>
				</h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
				    <p class="lead">ICCID : ${card.iccid }</p>
					<div class="col-xs-6">
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th style="width:30%">运营商:</th>
									<td>中国联通</td>
								</tr>
								<tr>
									<th>电话号码:</th>
									<td>${card.phone }</td>
								</tr>
								<tr>
									<th>设备状态:</th>
									<td>${card.objTypeStr }</td>
								</tr>
								<tr>
									<th>网络状态:</th>
									<td>${card.netTypeStr }</td>
								</tr>
								<tr>
									<th>IP地址:</th>
									<td>${card.ip }</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th style="width:30%">当前套餐:</th>
									<td>${pack.flow }M/月</td>
								</tr>
								<tr>
									<th>套餐周期:</th>
									<td>${pack.term }月</td>
								</tr>
								<tr>
									<th>套餐起止时间:</th>
									<td>${card.openTime }---${card.expireTime }</td>
								</tr>
							</table>
							<div id="cardStatis" style="min-height: 230px"/>
						</div>
					</div>
				</div>
				
				<div class="row">
				    <p class="lead">设备与用户</p>
					<div class="col-xs-6">
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th style="width:30%">关联客户:</th>
									<td>${user.phone }</td>
								</tr>
								<tr>
									<th>备注信息:</th>
									<td>${card.remark }</td>
								</tr>
								<tr>
									<th>认证状态:</th>
									<td>
									<c:if test="${company.legalAuth==null || company.legalAuth==0  }">未认证</c:if>
									<c:if test="${company.legalAuth==1  }">已认证</c:if>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="table-responsive">
							<table class="table">
								<tr>
									<th style="width:30%">实名用户:</th>
									<td>${user.userName }</td>
								</tr>
								<tr>
									<th>企业名称:</th>
									<td>${company.name }</td>
								</tr>
								<tr>
									<th>证件信息:</th>
									<td>
									${company.legalCode }
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<!--  
					<div class="row">
				    <p class="lead">历史套餐</p>
					<div class="col-xs-12">
							<table >
							  <thead><tr><th>生成时间</th><th>套餐价格</th><th>套餐周期</th><th>套餐流量</th></tr></thead>
							  <tbody>
							    
							  </tbody>
							</table> 
					</div>
				</div>
				-->
				<div class="row">
					<div class="col-md-4">
											<div id="reportrange" class="btn bg-aqua">
												<i class="fa fa-calendar"></i> &nbsp;<span></span> <b
													class="fa fa-angle-down"></b>
											</div>
					</div>
				</div>
				<div class="row">
				  <div id="flowStatis" style="min-height:250px"/>
				</div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
		<script type="text/javascript"
	src="${ctx}/assets/plugins/bootstrap-daterangepicker/daterangepicker-new.js"></script>
	<script type="text/javascript"
	src="${ctx }/assets/plugins/echarts-3.5.4.min.js"></script>
</body>
</html>
<script>
var startDay,endDay;
endDay = moment().subtract('days', 1).format('YYYY-MM-DD');
startDay = moment().subtract('days',30).format('YYYY-MM-DD');
$('#reportrange').daterangepicker(range,function(start, end) {
	startDay = start.format('YYYY-MM-DD');
	endDay = end.format('YYYY-MM-DD');
	loadChart();
});
var res={};
res['iccid']='${card.iccid}'
$('#reportrange span').html(startDay + ' - ' + endDay);
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
	if('${card.packageUsed}'==0 && '${card.packageLeft}'==0){
		$("#cardStatis").html("还没有绑定套餐,暂无流量统计信息")
	}else{
		echarts.init(document.getElementById("cardStatis")).setOption(cardOption);
	}
	
var flowChart = echarts.init(document.getElementById("flowStatis"));

var flowOption = {
	   tooltip : {
	        trigger: 'axis',
	        formatter: '{b0}<br/>{a0}: {c0}MB'
	    },
	legend : {
		data : []
	},
	grid : {
		left : '1%',
		right : '1%',
		containLabel : true
	},
	xAxis : [ {
		type : 'category',
		data : [],
		axisLabel : {
			interval : 0,
			rotate:45,
			textStyle : {
				fontSize : 12
			}
		}
	} ],
	yAxis : [ {
		type : 'value',
		 name: '使用流量(MB)',
		min : 0,
		axisLabel : {
			formatter : '{value} '
		}
	} ],
	series : []
};
loadChart()
function loadChart(){
	var options = {};
	options.success = function(e) {
		flowOption.series = [];
		flowOption.legend.data = [];
		flowOption.xAxis[0].data = [];
		if (e.xName != null) {
			$.each(e.series, function(key, value) {
				var tt = {};
				tt['type'] = 'line';
				tt['data'] = value.data;
				tt['name'] = value.name;
				flowOption.legend.data.push(value.name)
				flowOption.series.push(tt);
			});
			flowOption.xAxis[0].data = e.xName;
			flowChart.setOption(flowOption);
		}
	}
	options.url = '${ctx}/simcard/flow';
	res['begin']=startDay;
	res['end']=endDay;
	options.data=JSON2.stringify(res);
	SP.ajax($("#searchform"), options, false);
}
</script>