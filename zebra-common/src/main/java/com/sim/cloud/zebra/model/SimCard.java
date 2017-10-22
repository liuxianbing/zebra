package com.sim.cloud.zebra.model;

/**
 * @author liuxianbing:
 * @version 创建时间：2017年10月14日 下午12:26:30 类说明 物联网卡
 */
public class SimCard extends BaseModel {

	private static final long serialVersionUID = -3969900141839401630L;
	protected String phone;
	protected String iccid;// ICCID信息
	protected Long uid;
	protected Integer type;//1-单卡、2-流量池卡
	protected Integer netType;// 网络状态 
	protected Integer objType;// 设备状态
	protected Float usedFlow;// 使用流量 kb 小数点三位
	protected String remark;
	protected Integer packageId;// 套餐ID
	protected String openTime;// 开通时间
	protected String expireTime;// 过期时间
	protected String lastSyncTime;// 最近同步时间
	protected String account;// 账号license
	protected Integer operator;// 运营商      1-移动、2-电信、3-联通	

	protected Integer usedMessages;//已经使用的短信条数
	protected Integer usedMinutes;//已使用通话分钟数
	private Long cid;
	private String terminalId; // 设备ID
	
	
	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getUsedMessages() {
		return usedMessages;
	}

	public void setUsedMessages(Integer usedMessages) {
		this.usedMessages = usedMessages;
	}

	public Integer getUsedMinutes() {
		return usedMinutes;
	}

	public void setUsedMinutes(Integer usedMinutes) {
		this.usedMinutes = usedMinutes;
	}

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

	public Integer getPackageId() {
		return packageId;
	}

	public void setPackageId(Integer packageId) {
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

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	
	
}
