package com.sim.cloud.zebra.model;

import java.io.Serializable;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月24日 下午4:13:50 
* 类说明 
*/
public class FlowPoolVo implements Serializable {

	private static final long serialVersionUID = 112071029213002250L;
	private Integer totalPool;//流量池总大小
	private Float usePool;//已经使用的流量池大小
	private Float leftPool;
	private int leftPercent;
	private Integer flow;
	
	private Long cid;
	private String userName;
	
	private String flowName;
	
	private long activeNum;
	private long stockNum;
	private long blockNum;
	private long testNum;
	
	private String lastSyncTime;
	
	private int activePercent;
	
	
	private long allNum;
	
	private String companyName;
	
	
	

	
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public void setAllNum(long allNum) {
		this.allNum = allNum;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public long getAllNum() {
		return allNum;
	}

	public long getTestNum() {
		return testNum;
	}

	public void setTestNum(long testNum) {
		this.testNum = testNum;
	}

	public int getActivePercent() {
		if(getAllNum()>0){
		activePercent=(int) (getActiveNum()*100/getAllNum());
		}
		return activePercent;
	}

	public void setActivePercent(int activePercent) {
		this.activePercent = activePercent;
	}

	public Float getLeftPool() {
		leftPool=getTotalPool()-getUsePool();
		return leftPool;
	}

	public void setLeftPool(Float leftPool) {
		this.leftPool = leftPool;
	}

	public int getLeftPercent() {
		if(getTotalPool()>0){
			leftPercent=(int) (getUsePool()*100/getTotalPool());
		}
		return leftPercent;
	}

	public void setLeftPercent(int leftPercent) {
		this.leftPercent = leftPercent;
	}

	public String getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(String lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public long getActiveNum() {
		return activeNum;
	}

	public void setActiveNum(long activeNum) {
		this.activeNum = activeNum;
	}

	public long getStockNum() {
		return stockNum;
	}

	public void setStockNum(long stockNum) {
		this.stockNum = stockNum;
	}

	public long getBlockNum() {
		return blockNum;
	}

	public void setBlockNum(long blockNum) {
		this.blockNum = blockNum;
	}

	public Integer getTotalPool() {
		return totalPool;
	}

	public void setTotalPool(Integer totalPool) {
		this.totalPool = totalPool;
	}

	public Float getUsePool() {
		return usePool;
	}

	public void setUsePool(Float usePool) {
		this.usePool = usePool;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFlowName() {
		flowName="中国联通-"+getFlow()+"MB";
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	
	
}
