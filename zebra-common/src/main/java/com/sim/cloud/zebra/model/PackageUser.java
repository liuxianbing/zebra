package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午12:41:09 
* 类说明  用户套餐表
*/
public class PackageUser  extends BaseModel{

	private static final long serialVersionUID = 2371027727547341076L;
	private Integer uid;
	private Float cost;
	private Integer planId;
	private Integer term;//1月 2月。24月
	
	
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public Integer getPlanId() {
		return planId;
	}
	public void setPlanId(Integer planId) {
		this.planId = planId;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	
	
}
