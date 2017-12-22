<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="fragments/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>
<jsp:include page="fragments/meta.jsp" />
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
					<!-- ./col -->
					<div class="col-lg-3 col-xs-6">
						<!-- small box -->
						<div class="small-box bg-yellow">
							<div class="inner">
								<h3>${cardSize }</h3>
								<p>卡片数</p>
							</div>
							<div class="icon">
								<i class="fa fa-cc-mastercard"></i>
							</div>
						</div>
					</div>
						<div class="col-lg-3 col-xs-6">
							<!-- small box -->
							<div class="small-box bg-aqua">
								<div class="inner">
									<h3>${cardActiveSize }</h3>
									<p>激活卡片数</p>
								</div>
								<div class="icon">
									<i class="fa fa-users"></i>
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
									<i class="fa fa-certificate"></i>
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
									<i class="fa fa-paypal"></i>
								</div>
							</div>
						</div>
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="box">
							<div class="box-header with-border">
								<h3 class="box-title">联通卡片状态统计</h3>
							</div>
							<div class="box-body">
								<div class="row">
									<div class="col-md-4" id="cardType" style="min-height: 220px">
									</div>
									<div class="col-md-4" id="cardNet" style="min-height: 220px">
									</div>
									<div class="col-md-4" id="cardDevice" style="min-height: 220px">
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
								<h3 class="box-title">联通流量卡统计</h3>
								<div style="float:right">
								 <select id="account" class="form-control select2 " name="account"
								style="float: left;width:400px" >
								 <c:forEach items="${accounts }" var="ul" >
								     <option value="${ul.flow }" >${ul.name }</option>
								  </c:forEach>
								</select>
								</div>
							</div>
							<div class="box-body">
								<div class="row">
									<div class="col-md-6" id="num" style="min-height: 250px">
									</div>
									<div class="col-md-6" id="flow" style="min-height: 250px">
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
<script type="text/javascript"
	src="${ctx }/assets/plugins/echarts-3.5.4.min.js"></script>

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
	    legend: {
	        orient: 'horizontal',
	        left: 'center',
	        data: []
	    },
	    grid: {
	    	left: '1%',
	        right: '0%',
	        bottom: '1%',
	        containLabel: false
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
	                    show: false
	                },
	                emphasis: {
	                    show: false
	                }
	            },
	            itemStyle: {
	            	normal: {label:{  
	                    show:false,  
	                    formatter:'{b}\n{c} ({d}%)'  
	                },  
	                color: function(params) {
	                	 var colorList = [
	                          '#f39c12','#00c0ef','#00a65a',
	                           '#dd4b39','#9BCA63','#FAD860','#F3A43B','#60C0DD',
	                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
	                        ];
	                        return colorList[params.dataIndex]
	                },
	                labelLine:{show:false}},  
	                  emphasis: {  
	                      label: {  
	                          show: false,  
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
 $.each(JSON2.parse('${cardType}'), function(key, value) {
	 cardOption.legend.data.push(value);
			})
echarts.init(document.getElementById("cardType")).setOption(cardOption);

cardOption.title.text="卡片网络状态统计"; 
cardOption.series[0].name="卡片网络状态";
 $.each(JSON2.parse('${cardNet}'), function(key, value) {
	 cardOption.legend.data.push(value);
			})
			
echarts.init(document.getElementById("cardNet")).setOption(cardOption);

cardOption.title.text="卡片设备状态统计";
cardOption.series[0].name="卡片设备状态";
cardOption.series[0].data=JSON2.parse('${cardDevice}')
 $.each(JSON2.parse('${cardDevice}'), function(key, value) {
	 cardOption.legend.data.push(value);
			})
echarts.init(document.getElementById("cardDevice")).setOption(cardOption);


$(".select2").select2({ allowClear:false}).on("change", function(e) {
	drawCardNums($(this).val())
	drawCardFlow($(this).val())
});
if($("#account").val()!=null){
	drawCardNums($("#account").val())
	drawCardFlow($("#account").val())
}

function drawCardNums(account){
	 var options={}
	 options.url="${ctx}/adminStatis?flow="+account+"&type=0"
	  options.success = function(e) {
		  cardOption.title.text="流量卡激活数量统计";
		  cardOption.series[0].name="激活数量卡片";
		  cardOption.series[0].data=e
		  echarts.init(document.getElementById("num")).setOption(cardOption);
	  }
	  SP.ajax($("#form"), options, false);
}



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
flowOption.legend.data=['已使用','总流量']

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

function drawCardFlow(account){
	 var options={}
	  options.url="${ctx}/adminStatis?flow="+account+"&type=1"
	  options.success = function(e) {
		  flowOption.xAxis[0].data=e.xName
		  $.each(e.series, function(key, value) {
				value['label'] = narmalLable;
				flowOption.series.push(value);
			})
		  echarts.init(document.getElementById("flow")).setOption(flowOption);
	  }
	  SP.ajax($("#form"), options, false);
}

</script>