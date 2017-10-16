package com.sim.cloud.zebra.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sim.cloud.zebra.common.util.DateUtil;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BaseModel implements Serializable {

	@TableId(value = "id")
	protected Long id;
	protected String createTime;
	protected String updateTime=DateUtil.getDateTime();
    protected Long createUserId;         // 创建人ID
    protected Integer status;            // 删除状态  0-未删除  1-删除
    


	@TableField(exist = false)
	protected String keyword;


	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * @return the createTime
	 */
	public String getCreateTime() {
		if(StringUtils.isNotBlank(createTime) && createTime.endsWith(".0")){
			createTime=createTime.substring(0, createTime.length()-2);
		}
		return createTime;
	}

	/**
	 * @param createTime
	 *            the createTime to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public String getUpdateTime() {
		if(StringUtils.isNotBlank(updateTime) && updateTime.endsWith(".0")){
			updateTime=updateTime.substring(0, updateTime.length()-2);
		}
		return updateTime;
	}

	/**
	 * @param updateTime
	 *            the updateTime to set
	 */
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

  public Long getCreateUserId() {
    return createUserId;
  }

  public void setCreateUserId(Long createUserId) {
    this.createUserId = createUserId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
