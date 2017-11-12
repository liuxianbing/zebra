package com.sim.cloud.zebra.model;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.sim.cloud.zebra.common.util.CartCardEnum;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月3日 上午10:19:57 
* 类说明 
*/
public class OrderGoods  extends BaseModel{

	private static final long serialVersionUID = 192121042774098211L;
	
	private Float deliverCost;
	private Float totalCost;
	private Float cardCost;
	private String orderCode;
	private Long uid;
	private Integer status;
	private Integer type;
	private Integer cardCount;
	private Long addrId;
	private String remark;
	private Integer deliverType;
	
	private String orderTime,payTime,auditTime,outerTime,sucTime;
	
	@TableField(exist = false)
	private String deliverStr;
	
	
	public Integer getDeliverType() {
		return deliverType;
	}
	public void setDeliverType(Integer deliverType) {
		this.deliverType = deliverType;
	}
	public String getDeliverStr() {
		if(deliverType==0){
			deliverStr="中通快递";
		}else{
			deliverStr="顺丰快递";
		}
		return deliverStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getAddrId() {
		return addrId;
	}
	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getOuterTime() {
		return outerTime;
	}
	public void setOuterTime(String outerTime) {
		this.outerTime = outerTime;
	}
	public String getSucTime() {
		return sucTime;
	}
	public void setSucTime(String sucTime) {
		this.sucTime = sucTime;
	}
	public void setOrderNextStatus(String orderNextStatus) {
		this.orderNextStatus = orderNextStatus;
	}
	@TableField(exist = false)
	private String records;
	@TableField(exist = false)
	private List<CartCard> contents=new ArrayList<>();
	
	@TableField(exist = false)
	private String orderNextStatus;//订单列表 订单下一步状态
	
	
	
	public String getOrderNextStatus() {
		if(type==CartCardEnum.CHECKFAIL_ORDER.getStatus()){
			orderNextStatus=CartCardEnum.CHECKFAIL_ORDER.getStatusStr();
		}else if(type==CartCardEnum.SUCCESS_ORDER.getStatus()){
			orderNextStatus=CartCardEnum.SUCCESS_ORDER.getStatusStr();
		}else{
			orderNextStatus=CartCardEnum.getEnumByStatus(type+1).getStatusStr();
		}
		return orderNextStatus;
	}
	public List<CartCard> getContents() {
		return contents;
	}
	public void setContents(List<CartCard> contents) {
		this.contents = contents;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public Float getDeliverCost() {
		return deliverCost;
	}
	public void setDeliverCost(Float deliverCost) {
		this.deliverCost = deliverCost;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public Float getCardCost() {
		return cardCost;
	}
	public void setCardCost(Float cardCost) {
		this.cardCost = cardCost;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getCardCount() {
		return cardCount;
	}
	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
	}
	
	

}
