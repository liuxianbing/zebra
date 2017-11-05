package com.sim.cloud.zebra.model;
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
	private String token;
	private Integer status;
	private Integer type;
	private Integer cardCount;
	
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
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
