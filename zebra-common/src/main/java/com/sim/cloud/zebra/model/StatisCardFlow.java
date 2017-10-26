package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月22日 下午2:44:19 
* 类说明  每个卡的流量情况
*/

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@TableName("sim_card_flow")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisCardFlow implements Serializable {

	private static final long serialVersionUID = 7759420252886559644L;
	@TableId(value = "id")
	private Long id;
	private String day;//流量天
	private String iccid;
	private Float flow;//流量 kb
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public Float getFlow() {
		return flow;
	}
	public void setFlow(Float flow) {
		this.flow = flow;
	}
	
	
}