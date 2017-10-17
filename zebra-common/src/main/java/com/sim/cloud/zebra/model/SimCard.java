package com.sim.cloud.zebra.model;

/**
 * @author liuxianbing:
 * @version 创建时间：2017年10月14日 下午12:26:30 类说明 物联网卡
 */
public class SimCard extends BaseModel {

	private static final long serialVersionUID = -3969900141839401630L;
	private String phone;
	private String iccid;// ICCID信息
	private Long uid;
	private Integer type;
	private Integer netType;// 网络状态
	private Integer objType;// 设备状态
	private Float usedFlow;// 使用流量 kb 小数点三位
	private String remark;
	private int packageId;// 套餐ID
	private String openTime;// 开通时间
	private String expireTime;// 过期时间
	private String lastSyncTime;// 最近同步时间
	private Integer creator;// 创建人ID
	private String account;// 账号license
	private Integer operator;// 运营商      1-移动、2-电信、3-联通	

	public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Float getUsedFlow() {
		return usedFlow;
	}

	public void setUsedFlow(Float usedFlow) {
		this.usedFlow = usedFlow;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public String getLastSyncTime() {
		return lastSyncTime;
	}

	public void setLastSyncTime(String lastSyncTime) {
		this.lastSyncTime = lastSyncTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getOperator() {
		return operator;
	}

	public void setOperator(Integer operator) {
		this.operator = operator;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getPackageId() {
		return packageId;
	}

	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

	public Integer getType() {
		return type;
	}

	public Integer getNetType() {
		return netType;
	}

	public Integer getObjType() {
		return objType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}
}
