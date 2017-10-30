<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../fragments/taglibs.jsp"%>
<jsp:include page="../fragments/meta.jsp" />
	<link rel="stylesheet" type="text/css"
	href="${ctx}/assets/plugins/datatables.net-bs/css/dataTables.bootstrap.css" />
<style>
.row{
padding:15px
}
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    border-top: none;
}
.cardnodata{
font-size:30px;
text-align: center;
color:#999;
display:block
line-height:150px;
margin-top:80px
}
.nodata {
    height: 190px;
    width: 190px;
    margin: 20px auto;
    border-radius: 50%;
    overflow: hidden;
    background-color: #e5e5e5;
    position: relative;
}
.charttitle{
    height: 20px;
    text-align: center;
    font-size: 13px;
    color: #666;
    margin-top: 20px;
    margin-bottom: -10px;
}
.chartprofile{
    position: relative;
    text-align: center;
    padding: 0 0% 0 0;
    font-size: 14px;
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
				    <p class="lead">中国联通${flow }MB,&nbsp;&nbsp;&nbsp;关联客户:${cardflow.phone }&nbsp;&nbsp;&nbsp;最近同时间:${cardflow.lastSyncTime }</p>
				    <div class="col-xs-6">
				        <div class="charttitle">卡片使用情况</div>
				    	<div id="card" style="min-height:190px">
				    		 <div class="nodata">
				    		 <span style="position:absolute;top:43%;left:36%">暂无数据</span>
				    		 </div>
				    	</div>
				    	<div class="chartprofile">总卡量:${cardflow.keyword }</div>
				    </div>
				    <div class="col-xs-6">
				    	 <div class="charttitle">流量使用情况</div>
				    	<div id="flow" style="min-height:230px">
				    		 <div class="nodata">
				    		 <span style="position:absolute;top:43%;left:36%">暂无数据</span>
				    		 </div>
				    	</div>
				    	<div class="chartprofile">总流量:${cardflow.flow }MB</div>
				    </div>
				</div>
			</section>
			<section class="content">
				<form:form onsubmit="return false" action="${ctx}/flow/cardlist" method="post" id="searchform">
					<div class="row" style="padding-left:20px">
						<div class="col-sm-3 col-md-3"
							style="padding-left: 8px; padding-right: 0px">
							<input type="hidden" name="uid" value="${cardflow.uid }" />
							<input type="hidden" name='flow' value="${flow }" />
						 <div class="input-group input-group-sm">
                <input type="text" class="form-control" name="iccid" style="height:40px" placeholder="请输入ICCID">
                    <span class="input-group-btn">
                      <button type="button" class="btn btn-info btn-flat" style="height:40px">
                      <i class="fa fa-fw fa-search" onclick="loadTable()" style="font-size:18px"></i>
                      </button>
                    </span>
              </div>
						</div>
					</div>
				</form:form>
				<p class="btn_table" style='margin-left:5px'>
				    <button type="button" id="huabo"  class="btn  margin btn-success disabled">打开网络</button>
				    
				    <button type="button" id="rmBtn"  class="btn  margin btn-primary disabled">关闭网络</button>
				<p>
				<table id="card_list" class="table table-bordered table-hover">
				</table>
			</section>
		</div>
		<!-- /.content-wrapper -->
		<jsp:include page="../fragments/bottom.jsp" />
	</div>

	<jsp:include page="../fragments/footer.jsp" />
	<script type="text/javascript"
	src="${ctx }/assets/plugins/echarts-3.5.4.min.js"></script>
	<script type="text/javascript"
		src="${ctx }/assets/plugins/datatables.net/js/jquery.dataTables.js"></script>
	<script type="text/javascript"
		src="${ctx }/assets/plugins/datatables.net-bs/js/dataTables.bootstrap2.js"></script>
</body>
</html>
<script>
var dataXname='';
var xName=[];
var seriData=[];
if('${cardflow.remark}'!=''){
$.each(JSON2.parse('${cardflow.remark}'),function(i,n){
	xName.push(i)
	var cardObj={};
	cardObj['name']=i;
	cardObj['value']=n
	seriData.push(cardObj)
});
}
var cardOption = {
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
	            radius : '80%',
	            center: ['50%', '50%'],
	            data:seriData,
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
if('${cardflow.remark}'!=''){
	$("#card").css("min-height","230px");
var cardChart = echarts.init(document.getElementById("card"));
cardChart.setOption(cardOption);
}

seriData=[];
var cardObj={};
cardObj['name']="剩余流量";
cardObj['value']="${cardflow.packageLeft}"
if("${cardflow.packageLeft}"!='0.0'){
	seriData.push(cardObj)
}
	
	cardObj={};
cardObj['name']="已使用流量";
cardObj['value']="${cardflow.packageUsed}"
	if("${cardflow.packageUsed}"!='0.0'){
		seriData.push(cardObj)
	}
	cardOption.series[0].data=seriData;
cardOption.series[0].name="流量统计";
var flowChart = echarts.init(document.getElementById("flow"));
flowChart.setOption(cardOption);

var dataTableObj;
var oTable;
var selectData;
var options={}
options.bServerSide=false;
options.sAjaxSource="${ctx}/flow/cardlist";
options.aaSorting=[[ 0, "asc" ]];
options.aoColumns=[
	 { "sTitle": "ID", "bVisible":false, "sClass": "center","sWidth":"80","mDataProp": "id"},
	 { "sTitle": "ICCID",  "sClass": "center","sWidth":"220","mDataProp": "iccid","mRender": function ( data, type, full ) {
		 return '<a href="${ctx}/simcard/detail?id='+full.id+'">'+data+'</a>';
		 }
	 },
	 { "sTitle": "电话号码","sClass": "center" ,"sWidth":"100","mDataProp": "phone"},
  { "sTitle": "网络状态",  "sClass": "center" ,"sWidth":"75", "mDataProp": "netType"},
  { "sTitle": "设备状态",  "sClass": "center","sWidth":"80","mDataProp": "objType"},
  { "sTitle": "本月用量(MB)",  "sClass": "center","sWidth":"80","mDataProp": "usedFlow"},
  { "sTitle": "套餐价格",  "sClass": "center","sWidth":"80","mDataProp": "externalQuote"},
  { "sTitle": "套餐总量(MB)",  "sClass": "center","sWidth":"80","mDataProp": "flow"},
  { "sTitle": "套餐已用(MB)",  "sClass": "center","sWidth":"80","mDataProp": "packageUsed"},
  { "sTitle": "套餐剩余(MB)",  "sClass": "center","sWidth":"80","mDataProp": "packageLeft"},
  { "sTitle": "最近同步时间",  "sClass": "center","sWidth":"80","mDataProp": "lastSyncTime"},
  { "sTitle": "备注", "sClass": "center" ,"sWidth":"90","mDataProp": "remark"}
	];
function loadTable(){
	if(oTable){
		 $("#card_list").empty();
		 oTable.fnDestroy();
	}
	oTable=SP.loadTableInfo($("#card_list"),options,$("#searchform"));
	dataTableObj=oTable.api();
	//oTable.$('tr:odd').css('backgroundColor', 'blue');
}

$('#card_list').on('click', ' tbody tr', function () {	
   $(this).toggleClass('row_selected');
   if(dataTableObj.rows('.row_selected').data().length>0){
   	$(".btn_table").find('button').removeClass('disabled')
   }else{
   	$(".btn_table").find('button').addClass('disabled')
   }
} );
loadTable();
var netType=-1;
$('.btn_table').on('click', 'button', function () {
	  if($(this).hasClass('disabled')){
		  return;
	  }
  if($(this).hasClass('btn-success') && netType==0){
  	openNet();
  }else if($(this).hasClass('btn-primary') && netType==1){
  	closeNet();
  }
});

function getSelectedData(){
	 var iccids="";
	 $.each(dataTableObj.rows('.row_selected').data(),function(i,n){
		 if(netType==-1 && netType!=-2){
			 netType=n.netType;
		 }else if(netType!=n.netType){
			 netType=-2;
		 }
			 iccids=iccids+n.iccid+",";
	 }
	 );
	 if(cardType==-1){
		  bootbox.alert("请选择同一网络类型的物联网卡!"); 
		   return false;
	 }
	 $('#iccid').val(iccids.substring(0,iccids.length-1))
	 return true;
}

</script>