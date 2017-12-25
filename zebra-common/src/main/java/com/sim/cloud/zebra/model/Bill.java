package com.sim.cloud.zebra.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月5日 下午12:43:04 
* 类说明 
*/
public class Bill  implements Serializable {

	private static final long serialVersionUID = -3527423161526763111L;
	private Long id;
	private String month;
	private Float cost;
	private Integer status;
	private Long uid;
	private Long cid;
	private Float flow;
	
	@TableField(exist = false)
	private String userInfo;
	
	@TableField(exist = false)
	private List<BillRecord> contents=new ArrayList<>();
	
	@TableField(exist = false)
	private Integer len;//
	
	
	
	
	public Integer getLen() {
		if(getContents().size()>0){
			len=getContents().stream().mapToInt(m->{return m.getShareCards().size()>0?m.getShareCards().size():1;}).sum();
		}
		return len;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	public List<BillRecord> getContents() {
		return contents;
	}
	public void setContents(List<BillRecord> contents) {
		this.contents = contents;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Float getCost() {
		return cost;
	}
	public void setCost(Float cost) {
		this.cost = cost;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Float getFlow() {
		return flow;
	}
	public void setFlow(Float flow) {
		this.flow = flow;
	}
	
	
}
