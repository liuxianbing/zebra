package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.sim.cloud.zebra.common.util.SpringContextHolder;

/**
 * @author liuxianbing:
 * @version 创建时间：2017年10月14日 下午12:41:09 类说明 用户套餐表
 */
public class Package extends BaseModel {

	private static final long serialVersionUID = 2371027727547341076L;
	private Long uid; // 用户ID
	private Integer packageType; // 套餐类型 1-按月套餐、2-加油包、3-超额流量包、4-自定义套餐包
	private Integer flow; // 对外流量
	private Integer messages; // 短信条数
	private Integer callMinutes; // 通话分钟数
	private Integer term; // 1月 2月。24月 有效期
	private Float platQuote; // 平台价格
	private Float externalQuote; // 对外的价格
	private Integer operator; // 运营商 1-移动、2-电信、3-联通
	private Long planId; // 资费计划ID
	private String account; // 运营商平台账号
	private String remark; // 备注
	private Integer type; // 0:用户套餐 1：系统套餐
	private Integer cardType;
	private Integer realFlow;// 实际流量
	private Long cid;
	
	

	private String name;// 套餐名称
	@TableField(exist = false)
	private String userName;
	
	

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getRealFlow() {
		return realFlow;
	}

	public void setRealFlow(Integer realFlow) {
		this.realFlow = realFlow;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getPackageType() {
		return packageType;
	}

	public void setPackageType(Integer packageType) {
		this.packageType = packageType;
	}

	public Integer getFlow() {
		return flow;
	}

	public void setFlow(Integer flow) {
		this.flow = flow;
	}

	public Integer getMessages() {
		return messages;
	}

	public void setMessages(Integer messages) {
		this.messages = messages;
	}

	public Integer getCallMinutes() {
		return callMinutes;
	}

	public void setCallMinutes(Integer callMinutes) {
		this.callMinutes = callMinutes;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Float getPlatQuote() {
		return platQuote;
	}

	public void setPlatQuote(Float platQuote) {
		this.platQuote = platQuote;
	}

	public Float getExternalQuote() {
		return externalQuote;
	}

	public void setExternalQuote(Float externalQuote) {
		this.externalQuote = externalQuote;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

}
