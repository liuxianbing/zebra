<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="fragments/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="fragments/meta.jsp"/>
</head>
<body class="hold-transition skin-blue">
	<div class="wrapper">

		<jsp:include page="fragments/menu.jsp" />
		<div class="content-wrapper">
			<section class="content-header">
				<h1></h1>
			</section>
			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-aqua">
							<div class="inner">
								<h3>${customerSize }</h3>
								<p>用户数</p>
							</div>
							<div class="icon">
								<i class="ion ion-bag"></i>
							</div>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-green">
							<div class="inner">
								<h3>${authSize }</h3>
								<p>认证企业数</p>
							</div>
							<div class="icon">
								<i class="ion ion-stats-bars"></i>
							</div>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-yellow">
							<div class="inner">
								<h3>${cardSize }</h3>
								<p>卡片数</p>
							</div>
							<div class="icon">
								<i class="ion ion-person-add"></i>
							</div>
						</div>
					</div>
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-red">
							<div class="inner">
								<h3>${orderSize }</h3>

								<p>订单数</p>
							</div>
							<div class="icon">
								<i class="ion ion-pie-graph"></i>
							</div>
						</div>
					</div>
					<!-- ./col -->
				</div>
				
				 <div class="row">
				 		<div class="col-md-12">
				 				 <div class="box">
				 				 	<div class="box-header with-border">
				 				 	 	<h3 class="box-title">联通卡片状态统计</h3>
				 				 	 </div>
				 				 	   <div class="box-body">
				 				 	   		 <div class="row">
				 				 	   		 	 <div class="col-md-3" id="cardType" style="min-height:220px">
				 				 	   		 	 </div>
				 				 	   		 	 <div class="col-md-3" id="cardNet" style="min-height:220px">
				 				 	   		 	 </div>
				 				 	   		 	 <div class="col-md-3"  id="cardDevice" style="min-height:220px">
				 				 	   		 	 </div>
				 				 	   		 	 <div class="col-md-3"  id="cardFlow" style="min-height:220px">
				 				 	   		 	 </div>
				 				 	   		 </div>
				 				 	   </div>
				 				 </div>
				 		</div>
				 </div>
				 
				  <div class="row">
				 		<div class="col-md-12">
				 				 <div class="box">
				 				 	<div class="box-header with-border">
				 				 	 	<h3 class="box-title">联通流量统计</h3>
				 				 	 </div>
				 				 	   <div class="box-body">
				 				 	   		 <div class="row">
				 				 	   		 	 <div class="col-md-6" id="shareFlow" style="min-height:250px">
				 				 	   		 	 </div>
				 				 	   		 	 <div class="col-md-6" id="singleFlow" style="min-height:250px">
				 				 	   		 	 </div>
				 				 	   		 </div>
				 				 	   </div>
				 				 </div>
				 		</div>
				 </div>
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="fragments/bottom.jsp" />
	</div>

	<jsp:include page="fragments/footer.jsp" />
</body>
</html>
	<script type="text/javascript" src="${ctx }/assets/plugins/echarts-3.5.4.min.js"></script>
	
<script>
var cardOption = {
		 title: {
		        text: '天气情况统计',
		        bottom: 5,
		        left:'center',
		        textStyle :{
		        	color:'#999',
		        	fontSize:14
		        }
		    },
		tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    grid: {
	    	left: '1%',
	        right: '0%',
	        bottom: '1%',
	        containLabel: true
	    },
	    series : [
	        {
	            name: '卡片统计',
	            type: 'pie',
	            radius : '60%',
	            center: ['50%', '50%'],
	            data:[],
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
	                    formatter:'{b}\n{c} ({d}%)'  
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
	                          formatter: "{b}\n{c} ({d}%)",  
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
cardOption.title.text="卡片类型统计";
cardOption.series[0].name="卡片类型";
cardOption.series[0].data=JSON2.parse('${cardType}')
echarts.init(document.getElementById("cardType")).setOption(cardOption);

cardOption.title.text="卡片网络状态统计"; 
cardOption.series[0].name="卡片网络状态";
cardOption.series[0].data=JSON2.parse('${cardNet}')
echarts.init(document.getElementById("cardNet")).setOption(cardOption);

cardOption.title.text="卡片设备状态统计";
cardOption.series[0].name="卡片设备状态";
cardOption.series[0].data=JSON2.parse('${cardDevice}')
echarts.init(document.getElementById("cardDevice")).setOption(cardOption);

cardOption.title.text="流量池卡片统计";
cardOption.series[0].name="流量池卡片";
cardOption.series[0].data=JSON2.parse('${sharedCardFlow}')
echarts.init(document.getElementById("cardFlow")).setOption(cardOption);

var flowOption = {
		 title: {
		        text: '天气情况统计',
		        left: 'center',
		        top:2,
		        textStyle :{
		        	color:'#999',
		        	fontSize:14
		        }
		    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	    	 top:-70,
	        data: []
	    },
	    grid: {
	    	left: '1%',
	        right: '0%',
	        top:20,
	        bottom: '1%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data :[]
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : []
	};
flowOption.title.text="共享流量池统计";
flowOption.xAxis[0].data=JSON2.parse('${shareFlow}').xName
flowOption.legend.data=['已使用','剩余流量']

var narmalLable={
		normal : {
			show : true,
			position : 'inside',
			textStyle : {
				fontSize : 12,
				color : '#333'
			},
			formatter : function(params) {
				if (params.value == 0) {
					return ''
				} else {
					return params.value;
				}
			}
		}
	}
$.each(JSON2.parse('${shareFlow}').series, function(key, value) {
	value['label'] = narmalLable;
	flowOption.series.push(value);
})
echarts.init(document.getElementById("shareFlow")).setOption(flowOption);

flowOption.title.text="独享流量使用占比统计";
flowOption.series=[
	{
		label:narmalLable,
		type:'bar',
		name:'',
		data:[]
	}
]
flowOption.xAxis[0].data=[];
var sf=JSON2.parse('${singleFlow}');
$.each(sf,function(i,n){
	flowOption.xAxis[0].data.push(n.name)
})
flowOption.series[0].name="独享流量使用占比";
flowOption.series[0].data=sf;
echarts.init(document.getElementById("singleFlow")).setOption(flowOption);
</script>