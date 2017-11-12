package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.sim.cloud.zebra.common.util.FinanceEnum;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月11日 上午11:48:51 
* 类说明 
*/
public class Finance extends BaseModel{

	private static final long serialVersionUID = -4600044197132443580L;
	
	private Integer type;
	private Long cid,uid;
	private Float money,balance;
	private String orderCode;
	
	@TableField(exist = false)
	private String typeStr;
	
	
	
	public String getTypeStr() {
		typeStr=FinanceEnum.getEnumByStatus(type).getTypeStr();
		return typeStr;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Float getMoney() {
		return money;
	}
	public void setMoney(Float money) {
		this.money = money;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	

}
