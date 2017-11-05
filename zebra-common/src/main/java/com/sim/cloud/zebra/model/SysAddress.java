package com.sim.cloud.zebra.model;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sim.cloud.zebra.common.util.JackSonUtil;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 下午6:44:43 
* 类说明 
*/
public class SysAddress implements Serializable{
	private static final long serialVersionUID = 121217105175412394L;
	private Long id;
	private Long uid;
	private String userName;
	private Long provinceId;
	private Long cityId;
	private Long disId;
	private String location;
	private String phone;
	private Integer opt;
	private String area;//区域
	
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
	public Long getDisId() {
		return disId;
	}
	public void setDisId(Long disId) {
		this.disId = disId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getOpt() {
		return opt;
	}
	public void setOpt(Integer opt) {
		this.opt = opt;
	}
	@Override
	public String toString() {
		try {
			return JackSonUtil.getObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
		}
		return "{}";
	}
	
}
