package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableField;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月18日 下午6:25:50 
* 类说明 
*/
public class SimcardPackageView extends SimCard{

	private static final long serialVersionUID = 5747186188141055863L;

	private Float packageLeft;//剩余流量
	private Integer flow;//套餐总量
	private Integer packageType;//套餐类型
	private Float packageUsed;//套餐使用
	
	@TableField(exist = false)
	private String userInfo;
	
	
	
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public Float getPackageUsed() {
		return packageUsed;
	}
	public void setPackageUsed(Float packageUsed) {
		this.packageUsed = packageUsed;
	}
	
	public Float getPackageLeft() {
		return packageLeft;
	}
	public void setPackageLeft(Float packageLeft) {
		this.packageLeft = packageLeft;
	}
	public Integer getFlow() {
		return flow;
	}
	public void setFlow(Integer flow) {
		this.flow = flow;
	}
	public Integer getPackageType() {
		return packageType;
	}
	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}
	
	
}
