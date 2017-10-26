package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 上午11:36:09 
* 类说明  资费计划
*/
public class TariffPlan extends BaseModel {

	public static final Integer SHARE=1;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4083531698000494926L;
	private String name;
	private Integer type;//1:共享 0:独享
	private Integer flow;//流量
	private Float cost;//资费价格
	private Integer versionId;//版本
	private String account;//账号license
	
	private Integer messages;//短信数
	private Integer callMinutes;//通话分钟数
	private Integer operator;
	private Integer isMService;//订阅短信服务
	private Integer isCallService;//订阅通话服务
	private Integer uid;
	private Integer isFlowService;//定义流量服务
	
	
	
	
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
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public Integer getIsMService() {
		return isMService;
	}
	public void setIsMService(Integer isMService) {
		this.isMService = isMService;
	}
	public Integer getIsCallService() {
		return isCallService;
	}
	public void setIsCallService(Integer isCallService) {
		this.isCallService = isCallService;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public Integer getIsFlowService() {
		return isFlowService;
	}
	public void setIsFlowService(Integer isFlowService) {
		this.isFlowService = isFlowService;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getVersionId() {
		return versionId;
	}
	public void setVersionId(Integer versionId) {
		this.versionId = versionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getFlow() {
		return flow;
	}
	public void setFlow(Integer flow) {
		this.flow = flow;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	
	
}
