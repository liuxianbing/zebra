package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.sim.cloud.zebra.common.util.CardDeviceStatusEnum;

/**
 * @author liuxianbing:
 * @version 创建时间：2017年10月14日 下午12:26:30 类说明 物联网卡
 */
public class SimCard extends BaseModel {
	
	public static final int ACTIVATED_NAME=4;
	public static final int DEACTIVATED_NAME=5;
	public static final int RETIRED_NAME=6;
	public static final int TEST_READY_NAME=1;
	public static final int INVENTORY_NAME=2;
	public static final int ACTIVATION_READY_NAME=3;
	
	public static final int NET_OPEN=1;
	public static final int NET_CLOSE=0;

	private static final long serialVersionUID = -3969900141839401630L;
	protected String phone;
	protected String iccid;// ICCID信息
	protected Long uid;
	protected Integer type;//1-单卡、2-流量池卡
	protected Integer netType;// 网络状态 
	protected Integer objType;// 设备状态
	protected Float usedFlow;// 使用流量 kb 小数点三位
	protected String remark;
	protected Long packageId;// 套餐ID
	protected String openTime;// 开通时间
	protected String expireTime;// 过期时间
	protected String lastSyncTime;// 最近同步时间
	protected String account;// 账号license
	protected Integer operator;// 运营商      1-移动、2-电信、3-联通	
	private Long cartCardId;//订单ID

	protected Integer usedMessages;//已经使用的短信条数
	protected Integer usedMinutes;//已使用通话分钟数
	protected Long cid;
	
	protected String ip;
	protected String terminalId; // 设备ID
	
	
	@TableField(exist = false)
	protected String netTypeStr;
	@TableField(exist = false)
	protected String objTypeStr;
	@TableField(exist = false)
	String typeStr;
	
	
	
	
	
	public Long getCartCardId() {
		return cartCardId;
	}

	public void setCartCardId(Long cartCardId) {
		this.cartCardId = cartCardId;
	}

	public String getTypeStr() {
		if(type==2){
			typeStr= "流量卡";
		}else{
			typeStr= "单卡";
		}
		return typeStr;
	}

	public String getNetTypeStr() {
		if(netType==1){
			netTypeStr= "开启";
		}else{
			netTypeStr= "关闭";
		}
		return netTypeStr;
	}

	public String getObjTypeStr() {
		if(null!=objType)
			objTypeStr=CardDeviceStatusEnum.getEnumByStatus(objType).getSimStatus();
		return objTypeStr;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

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
		if(openTime!=null && openTime.length()>10){
			openTime=openTime.substring(0, 10);
		}
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getExpireTime() {
		if(expireTime!=null && expireTime.length()>10){
			expireTime=expireTime.substring(0, 10);
		}
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

	public Long getPackageId() {
		return packageId;
	}

	public void setPackageId(Long packageId) {
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

	@Override
	public String toString() {
		return "SimCard [phone=" + phone + ", iccid=" + iccid + ", uid=" + uid + ", type=" + type + ", netType="
				+ netType + ", objType=" + objType + ", usedFlow=" + usedFlow + ", remark=" + remark + ", packageId="
				+ packageId + ", openTime=" + openTime + ", expireTime=" + expireTime + ", lastSyncTime=" + lastSyncTime
				+ ", account=" + account + ", operator=" + operator + ", usedMessages=" + usedMessages
				+ ", usedMinutes=" + usedMinutes + ", cid=" + cid + ", ip=" + ip + ", terminalId=" + terminalId + "]";
	}
	
}
