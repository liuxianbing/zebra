package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableField;
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

	public static final int ROLE_SYS=0;
	public static final int ROLE_MANAGER=1;
	public static final int ROLE_USER=2;
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
	
	private Integer role;//用户角色 0:超级管理员 1：普通管理员 2:普通用户

	private Integer auth;//是否认证
	private Integer status = SysUser.able;// 用户的状态
	private Long cid;

	@TableField(exist = false)
	private String authStr;
	
	@TableField(exist = false)
	private String companyName;

	
	
	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getAuthStr() {
		if(auth==1){
			authStr="认证通过";
		}else{
			authStr="未认证";
		}
		return authStr;
	}


	public Integer getAuth() {
		return auth;
	}

	public void setAuth(Integer auth) {
		this.auth = auth;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

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