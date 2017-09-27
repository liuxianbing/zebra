package com.sim.cloud.zebra.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 用户管理
 * </p>
 *
 * @author liuxianbing
 * @since 2017-02-15
 */
@TableName("sys_user")
public class SysUser extends BaseModel {

	private static final long serialVersionUID = 3821528241569575012L;
	public static final int able = 0; // 账户启用
	public static final int disable = 1; // 账户禁用
	public static final String COMMON_PWD = "123456a";

	private String userName;
	private String passwd;
	private String email;
	private String department;

	private String creator = SYSTEM_CREATOR;// 创建者

	private int state = SysUser.able;// 用户的状态

	/** 前端页面传递 **/
	@TableField(exist = false)
	private transient String roles;// 管理员的角色

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passwd=" + passwd + ", email=" + email + ", department="
				+ department + ", state=" + state + "]";
	}
}