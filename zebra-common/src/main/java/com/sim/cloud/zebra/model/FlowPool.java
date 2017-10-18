package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月17日 下午7:28:32 
* 类说明  流量池
*/
public class FlowPool extends BaseModel {

	private static final long serialVersionUID = 3997729610952331338L;
	private String poolName;
	private Integer totalPool;//流量池总大小
	private Integer usePool;//已经使用的流量池大小
	private Integer addPool;//新增流量
	private Integer operator;
	private Integer uid;
	private Integer cid;
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public Integer getTotalPool() {
		return totalPool;
	}
	public void setTotalPool(Integer totalPool) {
		this.totalPool = totalPool;
	}
	public Integer getUsePool() {
		return usePool;
	}
	public void setUsePool(Integer usePool) {
		this.usePool = usePool;
	}
	public Integer getAddPool() {
		return addPool;
	}
	public void setAddPool(Integer addPool) {
		this.addPool = addPool;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
	
	
}
