package com.sim.cloud.zebra.model;
/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午4:11:57 
* 类说明  企业信息
*/
public class Company extends BaseModel{

	private static final long serialVersionUID = 3049249128657459562L;
	private String businessCode;
	private String name;
	private String businessUrl;
	private Integer businessAuth;//工商认证
	private String legalName;
	private String legalCode;
	private String legalPositive;//身份证正面
	private String legalBack;//身份证背面
	private Integer legalAuth;//法人认证
	private Long uid;//用户ID
	
	
	public Integer getBusinessAuth() {
		return businessAuth;
	}
	public void setBusinessAuth(Integer businessAuth) {
		this.businessAuth = businessAuth;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getBusinessCode() {
		return businessCode;
	}
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBusinessUrl() {
		return businessUrl;
	}
	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getLegalCode() {
		return legalCode;
	}
	public void setLegalCode(String legalCode) {
		this.legalCode = legalCode;
	}
	public String getLegalPositive() {
		return legalPositive;
	}
	public void setLegalPositive(String legalPositive) {
		this.legalPositive = legalPositive;
	}
	public String getLegalBack() {
		return legalBack;
	}
	public void setLegalBack(String legalBack) {
		this.legalBack = legalBack;
	}
	public Integer getLegalAuth() {
		return legalAuth;
	}
	public void setLegalAuth(Integer legalAuth) {
		this.legalAuth = legalAuth;
	}
	
	
}
