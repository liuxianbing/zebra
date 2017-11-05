package com.sim.cloud.zebra.model;

import java.io.Serializable;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 下午6:33:25 
* 类说明 
*/
public class SysArea  implements Serializable{
	
	public static final Integer DEEP_CITY=2;
	
	public static final Integer DEEP_DISTRICT=3;

	private static final long serialVersionUID = 1721217105175412394L;
	private Long id;
	private String name;
	private Integer deep;
	private Long parentId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDeep() {
		return deep;
	}
	public void setDeep(Integer deep) {
		this.deep = deep;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	
}
