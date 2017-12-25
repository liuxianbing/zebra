package com.sim.cloud.zebra.model;

import java.io.Serializable;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月27日 下午2:33:48 
* 类说明 
*/
public class LogAudit  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6306901624750506993L;
	private Long id,uid;
	private String userName,createTime,content;
	private String typeStr="划拨卡片";
	
	
	public String getTypeStr() {
		return typeStr;
	}
	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
