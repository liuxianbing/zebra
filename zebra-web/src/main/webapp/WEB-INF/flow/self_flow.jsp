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
.info-box-content {
	padding: 5px 10px;
	margin-left: 10px;
	margin-right: 5px
}

.info-box .progress {
    background: rgba(0,0,0,0.2);
    margin: 5px 0px 5px 0px;
    height: 2px;
}

.info-box .progressMax {
	height: 12px;
}

.info-box .progress .progress-bar {
	height: 12px;
	background: #00a65a;
	line-height: 12px;
}
.info-box .progress .progress-bar-red {
	height: 12px;
	background: #dd4b39;
	line-height: 12px;
}

.progress-wrapper .progress-bar2.thin {
	z-index: 1;
	background-color: #fff;
	overflow: hidden;
	height: 14px;
}

.progress-wrapper .progress-bar2 {
	height: 14px;
	width: 100%;
	border-radius: 100px;
	background-color: #e0e6ed;
	overflow: hidden;
	position: relative;
	box-sizing: content-box;
}

.progress-wrapper .progress-bar2 .seg-none {
	background-color: #999;
	width:100%
}

.progress-wrapper .progress-bar2 .seg-first {
	background-color: #ffbf00;
}

.progress-wrapper .progress-bar2 .seg-second {
	background-color: #20a0ff;
}

.progress-wrapper .progress-bar2 .seg-three {
	background-color: #fa5e5b;;
}

.progress-wrapper .progress-bar2 .seg-four {
	background-color: #4dd0e1;
}

.progress-wrapper .progress-bar2 span {
	position: absolute;
	display: inline-block;
	height: 100%;
}

.tipso_style {
	cursor: default;
	border-bottom: none;
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
					 <a href="Javascript:history.go(-1);void(0);" style="float:right;font-size:12px">
		       <button type="button" class="btn btn-box-tool" >
             		 <i class="btn fa fa-chevron-left" style="font-size:16px"></i>
             		 <font style="font-size:14px;margin-left:-12px">后退</font> 
             		 </button>
           		 </a>
				</h1>
			</section>
			<section class="content">
			
			   <c:if test="${list==null || fn:length(list) == 0}">
			      <div class="row" style="text-align: center;font-size:30px;color:#888;margin-top:300px">
			        暂无流量池数据
			      </div>
			   </c:if>

				<c:forEach var="pool" items="${list}" varStatus="cou">
					<c:if test="${cou.count eq 1 || (cou.count-1) % 4 eq 0}">
						<div class="row">
					</c:if>
					<div class="col-md-3">
						<div class="info-box bg-gray">
							<div class="info-box-content">
								<span class="info-box-number" style="display:inline">${pool.flowName}</span>
								<a href="${ctx }/flow/detail?flow=${pool.flow}&cid=${CURRENT_USER.cid}" class="small-box-footer" 
								style="float:right;font-size:10px">更多详情<i style="margin-left:5px;font-size:14px" class="fa fa-arrow-circle-right"></i>
                				 </a>
								 <span
									class="info-box-text">剩余${pool.leftPool }MB</span>

								<div class="progress progressMax">
								   <c:if test="${pool.leftPercent>=95}">
									<div class="progress-bar progress-bar-red" 
									style="width: ${pool.leftPercent}%;">${pool.leftPercent}%</div>
									</c:if>
									<c:if test="${pool.leftPercent<95}">
										<div class="progress-bar" 
									style="width: ${pool.leftPercent}%;">${pool.leftPercent}%</div>
									</c:if>
								</div>
								<span class="info-box-text" style="color:#777">卡片总数:${pool.allNum}</span> <span
									class="info-box-text">已激活${pool.activeNum} | 库存
									${pool.stockNum} | 已停卡 ${pool.blockNum}| 测试期${pool.testNum} </span>
								<div class="progress-wrapper">
									<div class="progress-bar2 thin" >
									     <c:if test="${pool.allNum>0 }">
										<span title="已激活${pool.activeNum}"
											class="seg-first el-tooltip item" style="width:${pool.activeNum*100/pool.allNum}%;">
										</span> <span  title="库存${pool.stockNum}" class="seg-second el-tooltip item"
											style="left: ${pool.activeNum*100/pool.allNum}%; width: ${pool.stockNum*100/pool.allNum}%;"></span> <span
											title="已停卡${pool.blockNum}" class="seg-three el-tooltip item"
											style="left: ${(pool.activeNum+pool.stockNum)*100/pool.allNum}%; width: ${pool.blockNum*100/pool.allNum}%;"></span> <span
											class="seg-four el-tooltip item"
											style="left: 100%; width: 0%;"></span>
											</c:if>
											 <c:if test="${pool.allNum==0 }">
											  <span  class="seg-none el-tooltip item" title="暂无卡片"></span>
											 </c:if>
									</div>
								</div>

								<span class="progress-description">
									最近同步时间：${pool.lastSyncTime }</span>
							</div>
							<!-- /.info-box-content -->
						</div>
						<!-- /.info-box -->
					</div>
					     <c:if test="${cou.count % 4 eq 0 || cou.count eq 4}">   
						</div>
					</c:if>
		</c:forEach>
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
$('.el-tooltip').tipso({
	background        : '#555',
	useTitle: true
	});
</script>