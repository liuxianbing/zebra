package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月5日 下午12:41:15 
* 类说明  消费记录表
*/

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotations.TableField;
import com.sim.cloud.zebra.common.util.JackSonUtil;

public class BillRecord  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7212296645980367873L;
	private Long id;
	private Long uid;
	private Long cid;
	private Float flow;
	private Float cost;
	private String detail;
	private Integer type;
	private Long billId;
	private String month;
	private String iccid;
	
	@TableField(exist = false)
	private String typeStr;
	
	@TableField(exist = false)
	private Map<String,String> shareCards=new HashMap<>();
	
	
	
	public Map<String, String> getShareCards() {
		if(type==TariffPlan.SHARE && StringUtils.isNotBlank(detail)){
			shareCards=JackSonUtil.readValueAsObjFromStr(detail, Map.class);
		}
		return shareCards;
	}
	
	
	




	public String getTypeStr() {
		if(type==TariffPlan.SHARE){
			typeStr="流量池扣费";
		}else{
			typeStr="单卡扣费";
		}
		return typeStr;
	}







	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Float getFlow() {
		return flow;
	}
	public void setFlow(Float flow) {
		this.flow = flow;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getBillId() {
		return billId;
	}
	public void setBillId(Long billId) {
		this.billId = billId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	
}
