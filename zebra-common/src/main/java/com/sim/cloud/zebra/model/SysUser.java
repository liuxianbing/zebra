package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户管理
 * </p>
 * @author liuxianbing
 * @since 2017-02-15
 */
@TableName("sys_user")
public class SysUser extends BaseModel {

	private static final long serialVersionUID = 3821528241569575012L;
	public static final int able = 1; // 账户启用
	public static final int disable = 0; // 账户禁用
	public static final String COMMON_PWD = "123456a";

	private String userName;
	private String passwd;
	private String email;
	private String phone;
	private String address;
	private String remark;//备注

	private Integer status = SysUser.able;// 用户的状态
	private Long cid;


	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}



}